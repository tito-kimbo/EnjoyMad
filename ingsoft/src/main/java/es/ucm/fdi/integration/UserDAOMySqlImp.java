package es.ucm.fdi.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.sql.PreparedStatement;


import java.util.ArrayList;
import java.util.Date;

import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.TagPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

/**
 * Implementation of <code>UserDAO</code> that works with a database.
 * 
 * @version 22.04.2018
 */
public class UserDAOMySqlImp implements UserDAO {

	/**
	 * Creates connection to the database.
	 * 
	 */
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

	/**
	 * Closes connection to the database.
	 * 
	 */
	private synchronized void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public synchronized UserPOJO getUser(String id) {
		Connection con = createConnection();
		UserPOJO user = null;

		try {
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery("SELECT * FROM Users where id=" + '\'' + id + '\'');

			if (!rs.next())
				return null;
			
			// String id, String user, String pass, String email, String
			// name, LocalDate bday
			user = new UserPOJO(rs.getString("id"),
					rs.getString("username"),
					rs.getString("password"), //
					rs.getString("email"),
					rs.getString("name"),
					rs.getDate("birth_date").toLocalDate());
			
			rs = st.executeQuery("SELECT * FROM Preferences where user_id=" + '\'' + id + '\'');
			
			ClubDAOMySqlImp users = new ClubDAOMySqlImp();
			while(rs.next()) {
				user.getPreferencesList().add(users.getClub(rs.getString("id")));
			}
			
			rs = st.executeQuery("SELECT * FROM TagPreferences where user_id=" + '\'' + id + '\'');
			while(rs.next()) {
				user.getValueTags().put(new TagPOJO(rs.getString("tag")), rs.getInt("value"));
			}
			
			rs = st.executeQuery("SELECT * FROM Opinion where user_id=" + '\'' + id + '\'');
			while(rs.next()) {
				user.getReviewedClubs().add(rs.getString("club_id"));
			}
			
			st.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		finally {
			closeConnection(con);
		}
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	public synchronized List<UserPOJO> getUsers() {
		Connection con = createConnection();
		List<UserPOJO> listUsers = new ArrayList<UserPOJO>();
		UserPOJO user;

		try {
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery("SELECT * FROM Users");
			while(rs.next()){
				// String id, String user, String pass, String email, String, name, LocalDate bday
				String id = rs.getString("id");
				user = new UserPOJO(id,
						rs.getString("username"),
						rs.getString("password"), 
						rs.getString("email"),
						rs.getString("name"),
						rs.getDate("birth_date").toLocalDate());
				
				ResultSet aux = con.createStatement().executeQuery("SELECT * FROM Preferences where user_id=" + '\'' + id + '\'');
				ClubDAOMySqlImp users = new ClubDAOMySqlImp();
				while(aux.next()) {
					user.getPreferencesList().add(users.getClub(rs.getString("id")));
				}
				
				aux = con.createStatement().executeQuery("SELECT * FROM TagPreferences where user_id=" + '\'' + id + '\'');
				while(aux.next()) {
					user.getValueTags().put(new TagPOJO(aux.getString("tag")), aux.getInt("value"));
				}
				
				aux = con.createStatement().executeQuery("SELECT * FROM Opinion where user_id=" + '\'' + id + '\'');
				while(aux.next()){
					user.getReviewedClubs().add(aux.getString("club_id"));
				}
				listUsers.add(user);
			} 
			
			st.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			closeConnection(con);
		}
		return listUsers;
	}

	/**
	 * {@inheritDoc}
	 */

	public synchronized boolean exists(String id) {
		Connection con = createConnection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement
					.executeQuery("select id from Users where id=" + '\'' + id + '\'');
			return rs.next();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		finally {
			closeConnection(con);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */

	public synchronized void addUser(UserPOJO user) {
		if(exists(user.getID()))
			return;
		
		Connection con = createConnection();

		// String id, String user, String pass, String email, String name,
		// LocalDate bday
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date myDate;
			try {				
				myDate = formatter.parse(user.getBirthday().toString());
				java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
				/*
				st.executeUpdate("insert into Users values (\'"
						+ user.getID() + "\',\'" + user.getNickname() + "\',\'"
						+ user.getEmail() + "\',\'" + user.getName() + "\',"
						+ sqlDate + ",\'" + user.getPassword() + "\')");
						*/
				// the mysql insert statement
			      String query = " insert into Users (id, username, email, name, birth_date, password)"
			        + " values (?, ?, ?, ?, ?, ?)";

			      // create the mysql insert preparedstatement
			      PreparedStatement preparedStmt = con.prepareStatement(query);
			      preparedStmt.setString (1, user.getID());
			      preparedStmt.setString (2, user.getUsername());
			      preparedStmt.setString (3, user.getEmail());
			      preparedStmt.setString (4, user.getName());
			      preparedStmt.setDate   (5, sqlDate);
			      preparedStmt.setString (6, user.getHashedPassword());
			      
			      preparedStmt.executeUpdate();
			      
			      // Insert tag preferences
			      query = " insert into TagPreferences (user_id, tag, value)"
					        + " values (?, ?, ?)";
			      preparedStmt = con.prepareStatement(query);
			      for(Map.Entry<TagPOJO,Integer> e : user.getValueTags().entrySet()) {
			    	  preparedStmt.setString (1, user.getID());
				      preparedStmt.setString (2, e.getKey().getTag());
				      preparedStmt.setInt (3, e.getValue());
				      preparedStmt.executeUpdate();
			      }
			      
			      

			      // Insert club preferences (supposes the clubs exist)
			      query = " insert into Preferences (user_id, club_id)"
					        + " values (?, ?)";
			      preparedStmt = con.prepareStatement(query);
			      for(ClubPOJO c : user.getPreferencesList()) {
			    	  preparedStmt.setString (1, user.getID());
				      preparedStmt.setString (2, c.getID());
				      preparedStmt.executeUpdate();
			      }
			      
			      // (Reviews should be already inserted)
					
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		finally {
			closeConnection(con);
		}
	}

	/**
	 * {@inheritDoc}
	 */

	public synchronized void removeUser(String id) {
		Connection con = createConnection();

		try {
			Statement st = con.createStatement();

			st.executeUpdate("delete from Users where id =" + '\'' + id + '\'');
			st.executeUpdate("delete from Opinion where user_id =" + '\'' + id + '\'');
			st.executeUpdate("delete from TagPreferences where user_id =" + '\'' + id + '\'');
			st.executeUpdate("delete from Preferences where user_id =" + '\'' + id + '\'');

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		finally {
			closeConnection(con);
		}
	}
}