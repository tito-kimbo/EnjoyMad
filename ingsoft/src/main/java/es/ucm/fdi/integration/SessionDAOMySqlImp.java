package es.ucm.fdi.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import es.ucm.fdi.integration.data.SessionPOJO;

public class SessionDAOMySqlImp implements SessionDAO {
	private Connection createConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7235942",
					"sql7235942", "ZuYxbPsXjH");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return con;
	}

	private void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	public synchronized List<SessionPOJO> getSessions() {
		Connection con = createConnection();
		List<SessionPOJO> listSessions = new ArrayList<SessionPOJO>();
		
		try {
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery("SELECT * FROM Sessions");
			while(rs.next()){
				java.util.Date date;
				Timestamp timestamp = rs.getTimestamp("creation_time");
				date = new java.util.Date(timestamp.getTime());
				LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
				
				SessionPOJO session = new SessionPOJO(rs.getString("id"), ldt);
				Timestamp last = rs.getTimestamp("last_accessed_time");
				if(last != null)
					session.setLastAccessedTime(LocalDateTime.ofInstant(new java.util.Date(last.getTime()).toInstant(), ZoneId.systemDefault()));
				listSessions.add(session);
			} 
			
			st.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			closeConnection(con);
		}
		return listSessions;
	}
	public synchronized boolean exist(String id) {
		Connection con = createConnection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement
					.executeQuery("select id from Sessions where id=" + '\'' + id + '\'');
			return rs.next();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		finally {
			closeConnection(con);
		}
		return false;
	}

	public synchronized void addSession(SessionPOJO session) {
		if(exist(session.getID()))
			return;
		
		Connection con = createConnection();
		
		try {
			java.sql.Timestamp creation = java.sql.Timestamp.valueOf(session.getCreationTime());
			
			java.sql.Timestamp last = null;
			if(session.getLastAccessedTime() != null)
				last = java.sql.Timestamp.valueOf(session.getLastAccessedTime());
			
			// the mysql insert statement
			if(last == null) {
		      String query = " insert into Sessions (id, creation_time, last_accessed_time)"
		        + " values (?, ?, ?)";

		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = con.prepareStatement(query);
		      preparedStmt.setString (1, session.getID());
		      preparedStmt.setTimestamp(2, creation);
		      preparedStmt.setTimestamp(3, last);
		      
		      preparedStmt.executeUpdate();
			}else {
				String query = " insert into Sessions (id, creation_time)"
				        + " values (?, ?)";

		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = con.prepareStatement(query);
		      preparedStmt.setString (1, session.getID());
		      preparedStmt.setTimestamp (2, creation);
		      
		      preparedStmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			closeConnection(con);
		}
	}

	public synchronized void removeSession(String id) {
		Connection con = createConnection();

		try {
			Statement st = con.createStatement();

			st.executeUpdate("delete from Sessions where id =" + '\'' + id + '\'');

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		finally {
			closeConnection(con);
		}
	}

	public synchronized SessionPOJO getSession(String id) {
		Connection con = createConnection();
		SessionPOJO session = null;

		try {
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery("SELECT * FROM Sessions where id=" + '\'' + id + '\'');

			if (!rs.next())
				return null;
			
			java.util.Date date;
			Timestamp timestamp = rs.getTimestamp("creation_time");
			date = new java.util.Date(timestamp.getTime());
			LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
			
			session = new SessionPOJO(id, ldt);
			session.setLastAccessedTime(LocalDateTime.ofInstant(new java.util.Date(rs.getTimestamp("last_accessed_time").getTime()).toInstant(), ZoneId.systemDefault()));
			
			st.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			closeConnection(con);
		}
		return session;
	}
}