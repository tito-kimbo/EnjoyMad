package es.ucm.fdi.integration;

import java.sql.*;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import es.ucm.fdi.integration.data.Location;
import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;
/**
 * This class is a Club data access object that implements {@link ClubDAOMySqñ}.
 * 
 * @version 03.05.2018
 * @author Carlijn
 */
public class ClubDAOMySqlImp implements ClubDAO {
    Connection con = null;
    PreparedStatement statement = null;
	ResultSet result = null;

	/**
	 * Creates connection to the database.
	 * 
	 */
	
	private void createConnection() {
		try {
		    con = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7235942", "sql7235942", "ZuYxbPsXjH");
		    statement = null;
			result = null;
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
            statement.close();
            result.close();
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
		
		ClubPOJO club = new ClubPOJO("","","",0f, new Location(0, 0), new HashSet<TagPOJO>(), null, 0, null);
		club.setID(id);
	    try {
	        statement = con.prepareStatement("select * from Clubs where id="+id);
	        result = statement.executeQuery();
	        /* sin terminar. falta revisar constructor clubpojo club = new ClubPOJO(result.getString(1),
	        result.getString(2),result.getString(3),result.getFloat(4),new Location(result.getDouble(5),
	        result.getDouble(6)),result.getFloat(7),result.getString(8));*/
	        result.next();
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
		List<ClubPOJO> listClubs = new ArrayList<ClubPOJO>();
		
		createConnection();
		
		ClubPOJO club = new ClubPOJO("","","",0f, new Location(0, 0), new HashSet<TagPOJO>(), null, 0, null);
		    try {
		        statement = con.prepareStatement("select * from Clubs");
		        result = statement.executeQuery();
				while(!result.isAfterLast()) {
					/* sin terminar. falta revisar constructor club = new ClubPOJO
					(result.getString(1),result.getString(2),result.getString(3),result.getFloat(4),
							new Location(result.getDouble(5),result.getDouble(6)),result.getFloat(7),
							result.getString(8));*/
					listClubs.add(club);
					result.next();
				}
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
	        statement = con.prepareStatement("select id from Clubs where id="+id);
	        result = statement.executeQuery();
	        if(result.getString(1) == id)
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
		
		try {
	    	//missing numbers 8, 9 and 10 of the columns in the db. Waiting for ClubPOJO updates.
	        statement = con.prepareStatement("insert into Clubs values ("+club.getID()+","
	        		+club.getCommercialName()+","+club.getAddress()+","+club.getPrice()+","
	        		+club.getLatitude()+","+club.getLongitude()+","+club.getRating()+","+club.getAddress());
	        result = statement.executeQuery();
	        if(result.rowInserted())
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
	        statement = con.prepareStatement("delete from Clubs where id="+id);
	        result = statement.executeQuery();
	        if(result.rowDeleted())
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
