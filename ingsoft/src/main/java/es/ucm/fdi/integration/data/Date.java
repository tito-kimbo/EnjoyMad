package es.ucm.fdi.integration.data;

/**
 * Class that represents the birthday.
 * @author Fco Borja
 * @author Carlijn
 */
public class Date{
	int day, month, year;
	
	/**
	 * Class constructor.
	 * @param day day
	 * @param month month
	 * @param year year
	 */
	
	public Date(int day, int month, int year) {
		setDay(day);
		setMonth(month);
		setYear(year);
	}
	
	/**
	 * Returns the day.
	 * @return day
	 */
	
	public int getDay() {
		return day;
	}
	
	/**
	 * Sets the day.
	 * @param day day
	 */
	
	public void setDay(int day) {
		if(day >= 1 && day <= 31){
			this.day = day;
		}
	}
	
	/**
	 * Returns the month.
	 * @return month
	 */
	
	public int getMonth() {
		return month;
	}
	
	/**
	 * Sets the month.
	 * @param month month
	 */
	
	public void setMonth(int month) {
		if(month >= 1 && month <= 12) {
			this.month = month;
		}
	}
	
	/**
	 * Returns the year.
	 * @return year
	 */
	
	public int getYear() {
		return year;
	}
	
	/**
	 * Sets the year.
	 * @param year year
	 */
	
	public void setYear(int year) {
		if(year >= 1900 && year <= 1999){
			this.year = year;
		}
	}
}