package es.ucm.fdi.integration.data;

/**
 * This class represents a user.
 * @author Fco Borja
 */
public class UserPOJO extends DataPOJO{
	//Requires refactor -> Use java libs
	String password, email, name;
	Date birthday;
	/**
	 * Class constructor.
	 * @param id identification
	 * @param pass password
	 * @param email email
	 * @param name name
	 */
	public UserPOJO(String id, String pass, String email, String name, int day, int month, int year){
		super(id);
		password = pass;
		this.email = email;
		this.name = name; 
		this.birthday = new Date(day,month,year);
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
	
	/**
	 * Sets the birthday
	 * @param birthday birthday
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * Returns the birthday.
	 * @return 
	 * @return birthday
	 */
	public Date getBirthday() {
		return this.birthday;
	}
}
