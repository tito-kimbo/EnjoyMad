package es.ucm.fdi.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import java.sql.PreparedStatement;


import java.util.ArrayList;
import java.util.Date;

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
			con = DriverManager.getConnection(
					"jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7235942",
					"sql7235942", "ZuYxbPsXjH");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Closes connection to the database.
	 * 
	 */
	private void closeConnection() {
		try {
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
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

			ResultSet rs = st
					.executeQuery("SELECT * FROM Users where id=" + '\'' + id + '\'');

			if (rs.next())
				// String id, String user, String pass, String email, String
				// name, LocalDate bday
				user = new UserPOJO(rs.getString("id"),
						rs.getString("username"),
						rs.getString("password"), //
						rs.getString("email"),
						rs.getString("name"),
						rs.getDate("birth_date").toLocalDate());
			st.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		finally {
			closeConnection();
		}
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<UserPOJO> getUsers() {
		createConnection();
		List<UserPOJO> listUsers = new ArrayList<UserPOJO>();
		UserPOJO user;

		try {
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery("SELECT * FROM Users");
			while (rs.next()) {
				//String id, String nickname, String pass, String email, String name, LocalDate bday
				user = new UserPOJO(rs.getString("id"),
						rs.getString("username"),
						rs.getString("password"),
						rs.getString("email"), 
						rs.getString("name"), 
						rs.getDate("birth_date").toLocalDate());

				listUsers.add(user);
			}
			st.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
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
			ResultSet rs = statement
					.executeQuery("select id from Users where id=" + '\'' + id + '\'');
			return rs.next();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		finally {
			closeConnection();
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */

	public boolean addUser(UserPOJO user) {
		createConnection();

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
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		finally {
			closeConnection();
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */

	public boolean removeUser(String id) {
		createConnection();

		try {
			Statement st = con.createStatement();

			st.executeUpdate("delete from Users where id =" + '\'' + id + '\'');
			st.executeUpdate("delete from Opinion where user_id =" + '\'' + id + '\'');

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		finally {
			closeConnection();
		}
		return true;
	}
}