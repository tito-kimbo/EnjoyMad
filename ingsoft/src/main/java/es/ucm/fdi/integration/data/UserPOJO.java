package es.ucm.fdi.integration.data;

import es.ucm.fdi.util.DataPOJO;

public class UserPOJO extends DataPOJO{
	
	class Date{
		int day, month, year;
		
		public Date(int day, int month, int year) {
			this.day = day;
			this.month = month;
			this.year = year;
		}
		
		public int getDay() {
			return day;
		}

		public void setDay(int day) {
			this.day = day;
		}

		public int getMonth() {
			return month;
		}

		public void setMonth(int month) {
			this.month = month;
		}

		public int getYear() {
			return year;
		}

		public void setYear(int year) {
			this.year = year;
		}
	}
	
	String password, email, name;
	Date birthday;

	public UserPOJO(String id, String pass, String email, String name){
		super(id);
		password = pass;
		this.email = email;
		this.name = name; //MIGHT NEED CHECK
		//WHAT ABOUT BIRTHDAY?
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public void setBirthday(int day, int month, int year) {
		this.birthday = new Date(day,month,year);
	}
}
