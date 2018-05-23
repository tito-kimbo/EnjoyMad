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
	    	ex.printStackTrace();
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
        	ex.printStackTrace();
        }
	}
	/**
	 * {@inheritDoc}
	 */
	public void saveTags(List<TagPOJO> list) {
		try {
			createConnection();
			Statement statement = con.createStatement();
			statement.executeUpdate("DELETE FROM Tags");
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		 
		for(TagPOJO tag : list)
			addTag(tag);
	}
	
 	/**
	 * {@inheritDoc}
	 */
	public List<TagPOJO> loadTags() {
		createConnection();
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
	    	closeConnection();
	    }
	    
	    return listTags;
	}

 	/**
	 * {@inheritDoc}
	 */
	private boolean addTag(TagPOJO tag) {
		createConnection();
		
		try { // Unchecked queries
	        Statement st = con.createStatement();
	        String str = "INSERT INTO Tags VALUES"
	        		+ " (\'"+tag.getTag()+"\')";
	        st.executeUpdate(str);
	    }/*
		catch(java.sql.SQLNonTransientConnectionException ex) {
			final TagPOJO tagF = tag;
			ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		    executorService.scheduleAtFixedRate(
		    		new Runnable() {
			    		public void run() {
			    			addTag(tagF);
			    		}
		    		}, 0, 1, TimeUnit.SECONDS);
		    tag = tagF;
		}*/
	    catch (SQLException ex) {
	    	ex.printStackTrace();
	    }
		   
	    finally{
	    	closeConnection();
	    }
		return false;
	}

}
