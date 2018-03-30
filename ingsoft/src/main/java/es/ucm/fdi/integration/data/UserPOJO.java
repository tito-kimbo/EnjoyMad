package es.ucm.fdi.integration.data;

import java.time.LocalDate;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class UserPOJO extends DataPOJO{
	//Requires refactor -> Use java libs
	
	String password, email, name;
	Date birthday;

	public UserPOJO(String id, String pass, String email, String name, int day, int month, int year){
		super(id);
		setPassword(pass);
		setEmail(email);
		setName(name);
		Date bday = new Date(day, month, year);
		setBirthday(bday);
	}
  
	/**
	 * Returns whether the; email address is valid.
	 * @return valid
	 */
	
	public static boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
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
	
	public void setEmail(String email){
		if(isValidEmailAddress(email)){
			this.email = email;
		}
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
	 * Returns the birthday
	 * @return birthday
	 */
	public Date getBirthday() {
		return birthday;
	}
	
	/**
	 * Sets the birthday
	 * @param birthday
	 */
	
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}