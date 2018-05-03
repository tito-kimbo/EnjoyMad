package es.ucm.fdi.integration;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import es.ucm.fdi.integration.data.Location;
import es.ucm.fdi.integration.data.UserPOJO;

/**
 * Implementation of <code>UserDAO</code> that works with a database.
 * 
 * @version 22.04.2018
 */
public class UserDAOMySqlImp implements UserDAOMySql {
    Connection con = null;
    PreparedStatement statement = null;
	ResultSet result = null;	
	
	
	/**
	 * {@inheritDoc}
	 */
	public UserPOJO getUser(String id) {
		try {
		    con = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7235942", "sql7235942", "ZuYxbPsXjH");
		    statement = null;
			result = null;
		}
	    catch (SQLException ex) {
	    	System.exit(1);
	    }
		
		//Not recognizing LocalDate. Will figure out tomorrow.
		LocalDate date = new LocalDate(1980,1,1);
		UserPOJO user = new UserPOJO(id,"","","","", date);
		user.setID(id);
	    try {
	        statement = con.prepareStatement("select * from Users where id="+id);
	        result = statement.executeQuery();
	        /* sin terminar. falta revisar constructor UserPOJO user = new UserPOJO(result.getString(1),
	        result.getString(2),result.getString(3),result.getFloat(4),new Location(result.getDouble(5),
	        result.getDouble(6)),result.getFloat(7),result.getString(8));*/
	        result.next();
	    }
	    catch (SQLException ex) {
	    	System.exit(1);
	    }
		    
	    finally{
	        try{
	            con.close();
	            statement.close();
	            result.close();
	        }
	        catch (SQLException ex) {
	        	System.exit(1);
	        }
	    }
	    return user;
	}
	
  	/**
	 * {@inheritDoc}
	 */
	public boolean addUser(UserPOJO user) {
		try {
		    con = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7235942", "sql7235942", "ZuYxbPsXjH");
		    statement = null;
			result = null;
		}
	    catch (SQLException ex) {
	    	System.exit(1);
	    }
		
		try {
	    	//missing numbers of the columns in the db. Waiting for UserPOJO updates.
	        statement = con.prepareStatement("insert into Users values ("+user.getID()+",");
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
	        try{
	            con.close();
	            statement.close();
	            result.close();
	        }
	        catch (SQLException ex) {
	        	System.exit(1);
	        }
        }
		return false;
	}
	
 	/**
	 * {@inheritDoc}
	 */
	public boolean exist(String id) {
		try {
		    con = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7235942", "sql7235942", "ZuYxbPsXjH");
		    statement = null;
			result = null;
		}
	    catch (SQLException ex) {
	    	System.exit(1);
	    }
		
		try {
	        statement = con.prepareStatement("select id from Users where id="+id);
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
	        try{
	            con.close();
	            statement.close();
	            result.close();
	        }
	        catch (SQLException ex) {
	        	System.exit(1);
	        }
        }
		return false;
	}
	
 	/**
	 * {@inheritDoc}
	 */
	public boolean removeUser(String id) {
		try {
		    con = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7235942", "sql7235942", "ZuYxbPsXjH");
		    statement = null;
			result = null;
		}
	    catch (SQLException ex) {
	    	System.exit(1);
	    }
		
		try {
	        statement = con.prepareStatement("delete from Users where id="+id);
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
	        try{
	            con.close();
	            statement.close();
	            result.close();
	        }
	        catch (SQLException ex) {
	        	System.exit(1);
	        }
        }
		return false;
	}


	/**
	 * {@inheritDoc}
	 */
	public List<UserPOJO> getUsers() {
		List<UserPOJO> listUsers = new ArrayList<UserPOJO>();
		
		try {
		    con = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7235942", "sql7235942", "ZuYxbPsXjH");
		    statement = null;
			result = null;
		}
	    catch (SQLException ex) {
	    	System.exit(1);
	    }
		
		//Not recognizing LocalDate. Will figure out tomorrow.
		LocalDate date = new LocalDate(1980,1,1);
		UserPOJO user = new UserPOJO("","","","","", date);
		try {
	        statement = con.prepareStatement("select * from Users");
	        result = statement.executeQuery();
				while(!result.isAfterLast()) {
					/* sin terminar. falta revisar constructor user = new UserPOJO
					(result.getString(1),result.getString(2),result.getString(3),result.getFloat(4),
							new Location(result.getDouble(5),result.getDouble(6)),result.getFloat(7),
							result.getString(8));*/
					listUsers.add(user);
					result.next();
				}
		    }
		    catch (SQLException ex) {
		    	System.exit(1);
		    }
		    
	    finally{
	        try{
	            con.close();
	            statement.close();
	            result.close();
	        }
	        catch (SQLException ex) {
	        	System.exit(1);
	        }
        }
	    return listUsers;
	}
}
