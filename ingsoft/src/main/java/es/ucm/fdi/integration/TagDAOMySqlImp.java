package es.ucm.fdi.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import es.ucm.fdi.integration.data.TagPOJO;

public class TagDAOMySqlImp implements TagDAO {
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
	private synchronized void closeConnection(Connection con) {
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
	public synchronized void saveTags(List<TagPOJO> list) {
		Connection con = createConnection();
		try {
			Statement statement = con.createStatement();
			List<TagPOJO> tags = loadTags();
			for(TagPOJO tag : tags)
				if(!list.contains(tag))
					statement.executeUpdate("DELETE FROM Tags where tag=\'" + tag.getTag() + '\'');
				
			for(TagPOJO tag : list)
				addTag(tag);
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			closeConnection(con);
		}
	}
	
 	/**
	 * {@inheritDoc}
	 */
	public synchronized List<TagPOJO> loadTags() {
		Connection con = createConnection();
		List<TagPOJO> listTags = new ArrayList<TagPOJO>();
		
	    try {
	        Statement st = con.createStatement();
	        
	        ResultSet rs = st.executeQuery("SELECT * FROM Tags");
	        while(rs.next()) {	    
		        listTags.add(new TagPOJO(rs.getString("tag")));
	        }
	        st.close();
	    }
	    catch (SQLException ex) {
	    	ex.printStackTrace();
	    }finally{
	    	closeConnection(con);
	    }
	    
	    return listTags;
	}

 	/**
	 * {@inheritDoc}
	 */
	synchronized private void addTag(TagPOJO tag) {
		if(exists(tag.getTag()))
			return;
		Connection con = createConnection();
		
		try { // Unchecked queries
	        Statement st = con.createStatement();
	        String str = "INSERT INTO Tags VALUES"
	        		+ " (\'"+tag.getTag()+"\')";
	        st.executeUpdate(str);
	    }
	    catch (SQLException ex) {
	    	ex.printStackTrace();
	    }
		   
	    finally{
	    	closeConnection(con);
	    }
	}

	private synchronized boolean exists(String tag) {
		Connection con = createConnection();
		boolean hasNext = true;
		try {
	        Statement st = con.createStatement();
	        
	        ResultSet rs = st.executeQuery("SELECT * FROM Tags where tag=\'" + tag + '\'');
	        hasNext = rs.next();
	        st.close();
	    }
	    catch (SQLException ex) {
	    	ex.printStackTrace();
	    }finally{
	    	closeConnection(con);
	    }
	    return hasNext;
	}

}
