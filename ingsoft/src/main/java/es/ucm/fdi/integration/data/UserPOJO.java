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
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

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
