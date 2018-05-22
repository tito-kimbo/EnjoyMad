package es.ucm.fdi.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import es.ucm.fdi.integration.data.SessionPOJO;

public class SessionDAPOMySqlImp implements SessionDAO {
	Connection con = null;

	private void createConnection() {
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7235942",
					"sql7235942", "ZuYxbPsXjH");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private void closeConnection() {
		try {
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	public List<SessionPOJO> getSessions() {
		createConnection();
		List<SessionPOJO> listSessions = new ArrayList<SessionPOJO>();
		try {
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery("SELECT * FROM Users");
			while(rs.next()){
				java.util.Date date;
				Timestamp timestamp = rs.getTimestamp("creation");
				date = new java.util.Date(timestamp.getTime());
				LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
				
				listSessions.add( new SessionPOJO(rs.getString("id"), ldt));
			} 
			
			st.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			closeConnection();
		}
		return listSessions;
	}
	public boolean exist(String id) {
		createConnection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement
					.executeQuery("select id from Sessions where id=" + '\'' + id + '\'');
			return rs.next();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		finally {
			closeConnection();
		}
		return false;
	}

	public void addSession(SessionPOJO session) {
		createConnection();

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				java.sql.Date creation = new java.sql.Date(formatter.parse(session.getCreationTime().toString()).getTime());
				java.sql.Date last = new java.sql.Date(formatter.parse(session.getLastAccessedTime().toString()).getTime());
				
				// the mysql insert statement
			      String query = " insert into Sessions (id, creation_time, last_used)"
			        + " values (?, ?, ?)";

			      // create the mysql insert preparedstatement
			      PreparedStatement preparedStmt = con.prepareStatement(query);
			      preparedStmt.setString (1, session.getID());
			      preparedStmt.setDate (2, creation);
			      preparedStmt.setDate (3, last);
			      
			      preparedStmt.executeUpdate();
					
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		finally {
			closeConnection();
		}
	}

	public void removeSession(String id) {
		createConnection();

		try {
			Statement st = con.createStatement();

			st.executeUpdate("delete from Sessions where id =" + '\'' + id + '\'');

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		finally {
			closeConnection();
		}
	}

	public SessionPOJO getSession(String id) {
		createConnection();
		SessionPOJO session = null;

		try {
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery("SELECT * FROM Sessions where id=" + '\'' + id + '\'');

			if (!rs.next())
				return null;
			
			java.util.Date date;
			Timestamp timestamp = rs.getTimestamp("creation");
			date = new java.util.Date(timestamp.getTime());
			LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
			
			session = new SessionPOJO(id, ldt);
			
			st.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			closeConnection();
		}
		return session;
	}
}