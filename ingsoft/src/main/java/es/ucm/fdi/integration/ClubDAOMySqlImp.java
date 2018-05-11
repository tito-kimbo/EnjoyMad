package es.ucm.fdi.integration;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;

import es.ucm.fdi.integration.data.Location;
import es.ucm.fdi.integration.data.ReviewPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;
/**
 * This class is a Club data access object that implements {@link ClubDAOMySqñ}.
 * 
 * @version 03.05.2018
 * @author Carlijn
 */
public class ClubDAOMySqlImp implements ClubDAO {
    Connection con = null;
	/**
	 * Creates connection to the database.
	 * 
	 */
	private void createConnection() {
		try {
		    con = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7235942", "sql7235942", "ZuYxbPsXjH");
		}
	    catch (SQLException ex) {
	    	System.exit(1);
	    }
	}
	
	/**
	 * Closes connection to the database.
	 * 
	 */
	private void closeConnection() {
        try{
            con.close();
        }
        catch (SQLException ex) {
        	System.exit(1);
        }
	}
	
	/**
	 * {@inheritDoc}
	 */
	public ClubPOJO getClub(String id) {
		createConnection();
		ClubPOJO club = null;
		
	    try {
	        Statement st = con.createStatement();
	        
	        ResultSet rs = st.executeQuery("SELECT * FROM Clubs where id="+id);
	        
	        if(rs.next())
	        	club = new ClubPOJO(rs.getString("id"), //ID
	        			 rs.getString("commercial_name"), //
	        			 rs.getString("address"), 
	        			 rs.getFloat("price"), 
		        		 new Location(rs.getDouble("latitude"), rs.getDouble("longitud")), 
		        		 rs.getFloat("average_rating"));
	        
	        rs = st.executeQuery("SELECT * FROM Tags where id="+id);
	        while(rs.next()) {
	        	club.addTag(rs.getString("tag"));
	        }
	        
	        rs = st.executeQuery("Select * FROM Opinions where club_id ="+ id);
	        while(rs.next()) {
	        	club.addUserReview(rs.getString("user_id"), new ReviewPOJO(rs.getString("opinion"), rs.getFloat("rating")));
	        }
	        st.close();
	    }
	    catch (SQLException ex) {
	    	System.exit(1);
	    }
		    
	    finally{
	    	closeConnection();
	    }
	    return club;
	}
	
 	/**
	 * {@inheritDoc}
	 */
	
	public List<ClubPOJO> getClubs(){
		createConnection();
		List<ClubPOJO> listClubs = new ArrayList<ClubPOJO>();
		
	    try {
	        Statement st = con.createStatement();
	        
	        ResultSet rs = st.executeQuery("SELECT * FROM Clubs");
	        while(rs.next()) {	    
	        	ClubPOJO club = new ClubPOJO(rs.getString("id"), //ID
	        			 rs.getString("commercial_name"), //
	        			 rs.getString("address"), 
	        			 rs.getFloat("price"), 
		        		 new Location(rs.getDouble("latitude"), rs.getDouble("longitud")), 
		        		 rs.getFloat("average_rating"));
		        
		        ResultSet auxS =st.executeQuery("SELECT * FROM Tags where id="+rs.getString("id"));
		        while(auxS.next()) {
		        	club.addTag(auxS.getString("tag"));
		        }
		        
		        auxS = st.executeQuery("Select * FROM Opinions where club_id ="+ rs.getString("id"));
		        while(auxS.next()) {
		        	club.addUserReview(auxS.getString("user_id"), new ReviewPOJO(auxS.getString("opinion"), auxS.getFloat("rating")));
		        }
	        }
	        st.close();
	    }
	    catch (SQLException ex) {
	    	System.exit(1);
	    }
		    
	    finally{
	    	closeConnection();
	    }
	    return listClubs;
	}

 	/**
	 * {@inheritDoc}
	 */
	
	public boolean exist(String id) {
		createConnection();
		
		try {
	        Statement statement = con.createStatement();
	        ResultSet rs = statement.executeQuery("select id from Clubs where id="+id);
	        if(rs.next())
	        	return true;
	        else 
	        	return false;
	    }
	    catch (SQLException ex) {
	    	System.exit(1);
	    }
		    
	    finally{
	    	closeConnection();
	    }
		return false;
	}

 	/**
	 * {@inheritDoc}
	 */
	
	public boolean addClub(ClubPOJO club) {
		createConnection();
		
		try { // Unchecked queries
	        Statement st = con.createStatement();
	        ResultSet rs = st.executeQuery("insert into Clubs values ("+club.getID()+","
	        		+club.getCommercialName()+","+club.getAddress()+","+club.getPrice()+","
	        		+club.getLatitude()+","+club.getLongitude()+","+club.getRating()+","+club.getAddress() + ")");
	        
	        for(String tag : club.getTags()) {
	        	st.executeQuery("insert into Tags values (" + club.getID() + "," + tag + ")");
	        }
	        @SuppressWarnings("rawtypes")
			Iterator it = club.getReviews().entrySet().iterator();
	        while (it.hasNext()) {
	            @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();
	            st.executeQuery("insert into Opinions values ("+ pair.getKey() +","+ club.getID() +"," +((ReviewPOJO) pair).getRating() +","+ ((ReviewPOJO) pair.getValue()).getOpinion() + ")");
	            it.remove(); // avoids a ConcurrentModificationException
	        }
	        
	        if(rs.rowInserted()) // Doesn't check if the other insertions went wrong, just the club
	        	return true;
	        else 
	        	return false;
	    }
	    catch (SQLException ex) {
	    	System.exit(1);
	    }
		    
	    finally{
	    	closeConnection();
	    }
		return false;
	}

  	/**
	 * {@inheritDoc}
	 */
	
	public boolean removeClub(String id) {
		createConnection();
		
		try {
	        Statement st = con.createStatement();
	        
	        ResultSet rs = st.executeQuery("delete from Clubs where id="+id);
	        st.executeQuery("delete from Tags where club_id="+id);
	        st.executeQuery("delete from Opinions where club_id="+id);
	        
	        if(rs.rowDeleted())
	        	return true;
	        else 
	        	return false;
	    }
	    catch (SQLException ex) {
	    	System.exit(1);
	    }
		    
	    finally{
	    	closeConnection();
	    }
		return false;
	}
}
