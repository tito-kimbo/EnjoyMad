package es.ucm.fdi.integration.data;

import java.time.LocalDate;

public class UserPOJO extends DataPOJO{
	//Requires refactor -> Use java libs
	
	String password, email, name;
	LocalDate birthday;

	

	public UserPOJO(String id, String pass, String email, String name, LocalDate bday){
		super(id);
		password = pass;
		this.email = email;
		this.name = name; //MIGHT NEED CHECK
		birthday = bday;
	}
  
	/**
	 * Returns the password.
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Sets the password.
	 * @param password password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Returns the email.
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Sets the email.
	 * @param email email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Returns the name.
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	
	public void setBirthday(int day, int month, int year) {
		this.birthday = LocalDate.of(day,month,year); //MONTH IS AN ENUM IN THE FUNCTIOn
	}
}
