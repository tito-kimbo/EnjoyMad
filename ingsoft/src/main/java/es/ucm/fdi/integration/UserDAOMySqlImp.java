package es.ucm.fdi.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.List;
import java.util.ArrayList;

import es.ucm.fdi.integration.data.UserPOJO;

/**
 * Implementation of <code>UserDAO</code> that works with a database.
 * 
 * @version 22.04.2018
 */
public class UserDAOMySqlImp implements UserDAO {
	Connection con = null;
	/**
	 * Creates connection to the database.
	 * 
	 */
	private void createConnection() {
		try {
		    con = DriverManager.
                            getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7235942",
                                    "sql7235942", "ZuYxbPsXjH");
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
	public UserPOJO getUser(String id) {
		createConnection();
		UserPOJO user = null;
		
	    try {
	        Statement st = con.createStatement();
	        
	        ResultSet rs = st.executeQuery("SELECT * FROM Users where id="+id);
	        
	        if(rs.next()) 
//String id, String user, String pass, String email, String name, LocalDate bday
	        	user = new UserPOJO(rs.getString("id"), 
	        			 rs.getString("nickname"), 
	        			 rs.getString("password"), //
	        			 rs.getString("address"), 
	        			 rs.getString("email"), 
	        			 rs.getDate("birthday").toInstant().
                                                 atZone(ZoneId.systemDefault()).toLocalDate());
	        st.close();
	    }
	    catch (SQLException ex) {
	    	System.exit(1);
	    }
		    
	    finally{
	    	closeConnection();
	    }
	    return user;
	}
	
 	/**
	 * {@inheritDoc}
	 */
	public List<UserPOJO> getUsers(){
		createConnection();
		List<UserPOJO> listUsers = new ArrayList<UserPOJO>();
		UserPOJO user;
		
	    try {
	        Statement st = con.createStatement();
	        
	        ResultSet rs = st.executeQuery("SELECT * FROM Users");
	        while(rs.next()) {	    
        		user = new UserPOJO(rs.getString("id"), 
	        			 rs.getString("nickname"), 
	        			 rs.getString("password"), //
	        			 rs.getString("address"), 
	        			 rs.getString("email"), 
	        			 rs.getDate("birthday").toInstant().
                                                 atZone(ZoneId.systemDefault()).toLocalDate());
		        
	        	listUsers.add(user);
	        }
	        st.close();
	    }
	    catch (SQLException ex) {
	    	System.exit(1);
	    }
		    
	    finally{
	    	closeConnection();
	    }
	    return listUsers;
	}

 	/**
	 * {@inheritDoc}
	 */
	
	public boolean exists(String id) {
		createConnection();
		
		try {
	        Statement statement = con.createStatement();
	        ResultSet rs = statement.executeQuery("select id from Users where id="+id);
                    return rs.next();
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
	
	public boolean addUser(UserPOJO user) {
		createConnection();
		
                //String id, String user, String pass, String email, String name, LocalDate bday
		try { 
	        Statement st = con.createStatement();
	        ResultSet rs = st.executeQuery("insert into Users values ("+user.getID()+","
	        		+user.getNickname()+","+user.getPassword()+","+user.getName()+","+
	        		user.getBirthday().toString() + ")");	
	        
                // Doesn't check if the other insertions went wrong, just the club
                    return rs.rowInserted(); 
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
	
	public boolean removeUser(String id) {
		createConnection();
		
		try {
	        Statement st = con.createStatement();
	        
	        ResultSet rs = st.executeQuery("delete from Users where id="+id);
	        st.executeQuery("delete from Opinions where user_id="+id);
	        
                    return rs.rowDeleted();
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
