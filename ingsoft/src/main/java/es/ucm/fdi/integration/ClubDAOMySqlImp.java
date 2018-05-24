package es.ucm.fdi.integration;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;
import java.util.ArrayList;

import es.ucm.fdi.integration.data.Location;
import es.ucm.fdi.integration.data.TagPOJO;
import es.ucm.fdi.integration.data.ReviewPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;
/**
 * This class is a Club data access object that implements {@link ClubDAOMySqñ}.
 * 
 * @version 03.05.2018
 */
public class ClubDAOMySqlImp implements ClubDAO {
	/**
	 * Creates connection to the database.
	 * 
	 */
	private Connection createConnection() {
		Connection con = null;
		try {
		    con = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7235942", "sql7235942", "ZuYxbPsXjH");
		}
	    catch (SQLException ex) {
	    	ex.printStackTrace();
	    }
		return con;
	}
	
	/**
	 * Closes connection to the database.
	 * 
	 */
	private void closeConnection(Connection con) {
        try{
            con.close();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        }
	}
	
	/**
	 * {@inheritDoc}
	 */
	public ClubPOJO getClub(String id) {
		Connection con = createConnection();
		ClubPOJO club = null;

	    try {
	        Statement st = con.createStatement();
	        
	        ResultSet rs = st.executeQuery("SELECT * FROM Clubs where id="+id);
	        
	        if(rs.next()) {
	        	
	        	club = new ClubPOJO(rs.getString("id"), //ID
	        			 rs.getString("commercial_name"), //
	        			 rs.getString("address"), 
	        			 rs.getFloat("price"), 
		        		 new Location(rs.getDouble("latitude"), rs.getDouble("longitude")), 
		        		 rs.getFloat("average_rating"));
	        
		        rs = st.executeQuery("SELECT * FROM ClubTags where club_id ="+'\'' + id + '\'');
		      
		        while(rs.next()) {
		        	club.addTag(new TagPOJO(rs.getString("tag")));
		        }
		        
		        rs = st.executeQuery("Select * FROM Opinion where club_id =" +'\'' + id + '\'');
		        while(rs.next()) {
		        	club.addUserReview(rs.getString("user_id"), new ReviewPOJO(rs.getString("opinion"), rs.getFloat("rating")));
		        }
	        }
	        st.close();
	    }
	    catch (SQLException ex) {
	    	ex.printStackTrace();
	    }
		    
	    finally{
	    	closeConnection(con);
	    }
	    return club;
	}
	
 	/**
	 * {@inheritDoc}
	 */
	public List<ClubPOJO> getClubs(){
		Connection con = createConnection();
		List<ClubPOJO> listClubs = new ArrayList<ClubPOJO>();
		
	    try {
	        Statement st = con.createStatement();
	        Statement aux = con.createStatement();
	        
	        ResultSet rs = st.executeQuery("SELECT * FROM Clubs");
	        while(rs.next()) {	    
	        	String id = rs.getString("id");
	        	ClubPOJO club = new ClubPOJO(id, //ID
	        			 rs.getString("commercial_name"), //
	        			 rs.getString("address"), 
	        			 rs.getFloat("price"), 
		        		 new Location(rs.getDouble("latitude"), rs.getDouble("longitude")), 
		        		 rs.getFloat("average_rating"));
		        
		        ResultSet auxS = aux.executeQuery("SELECT * FROM ClubTags where club_id = \'"+id+"\'");
		        while(auxS.next()) {
		        	club.addTag(new TagPOJO(auxS.getString("tag")));
		        }
		        
		        auxS = aux.executeQuery("Select * FROM Opinion where club_id ="+ '\'' + id + '\'');
		        while(auxS.next()) {
		        	club.addUserReview(auxS.getString("user_id"), new ReviewPOJO(auxS.getString("opinion"), auxS.getFloat("rating")));
		        }
		        
		        listClubs.add(club);
	        }
	        st.close();
	    }
	    catch (SQLException ex) {
	    	ex.printStackTrace();
	    }finally{
	    	closeConnection(con);
	    }
	    
	    return listClubs;
	}

 	/**
	 * {@inheritDoc}
	 */
	
	public boolean exists(String id) {
		Connection con = createConnection();
		
		try {
	        Statement statement = con.createStatement();
	        ResultSet rs = statement.executeQuery("select id from Clubs where id="+id+";");
	        if(rs.next())
	        	return true;
	        else 
	        	return false;
	    }
	    catch (SQLException ex) {
	    	ex.printStackTrace();
	    }
		    
	    finally{
	    	closeConnection(con);
	    }
		return false;
	}

 	/**
	 * {@inheritDoc}
	 */
	
	public synchronized void addClub(ClubPOJO club) {
		if(exists(club.getID()))
			return;
		
		Connection con = createConnection();
		
		try { // Unchecked queries
	        Statement st = con.createStatement();
	        String str = "INSERT INTO Clubs VALUES"
	        		+ " (\'"+club.getID()+"\',\'"
	        		+club.getCommercialName()+"\',\'"
	        		+club.getAddress()+"\',"
	        		+club.getPrice()+","
	        		+club.getLatitude()+","
	        		+club.getLongitude()+","
	        		+club.getRating()+")";
	        st.executeUpdate(str);
	        
	        for(TagPOJO tp : club.getTags()) {
	        	st.executeUpdate("insert into ClubTags values (\'" + club.getID() + "\',\'" + tp.getTag() + "\')");
	        }
	        @SuppressWarnings("rawtypes")
			Iterator it = club.getReviews().entrySet().iterator();
	        while (it.hasNext()) {
	            @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();
	            st.executeUpdate("insert into Opinions values (\'"+ pair.getKey() +"\',\'"+ club.getID() +"\'," +((ReviewPOJO) pair).getRating() +",\'"+ ((ReviewPOJO) pair.getValue()).getOpinion() + "\')");
	            it.remove(); // avoids a ConcurrentModificationException
	        }
	    }
		catch(java.sql.SQLNonTransientConnectionException ex) {
			final ClubPOJO Fclub = club;
			ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		    executorService.scheduleAtFixedRate(
		    		new Runnable() {
			    		public void run() {
			    			addClub(Fclub);
			    		}
		    		}, 0, 1, TimeUnit.SECONDS);
			addClub(club); // Retry
		}
	    catch (SQLException ex) {
	    	ex.printStackTrace();
	    }
		   
	    finally{
	    	closeConnection(con);
	    }
	}

  	/**
	 * {@inheritDoc}
	 */
	
	public void removeClub(String id) {
		Connection con = createConnection();
		
		try {
	        Statement st = con.createStatement();
	        
	        st.executeUpdate("delete from Clubs where id ="+'\'' + id + '\'');
	        st.executeUpdate("delete from ClubTags where club_id ="+'\'' + id + '\'');
	        st.executeUpdate("delete from Opinion where club_id ="+'\'' + id + '\'');
	    }
	    catch (SQLException ex) {
	    	ex.printStackTrace();
	    }
		    
	    finally{
	    	closeConnection(con);
	    }
	}
}