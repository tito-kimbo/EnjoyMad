package es.ucm.fdi.datos;

import java.util.List;

public class ClubPOJO extends Data {
	String location;
	float price;
	List<String> tags;
	
	public ClubPOJO(String location, float price, List<String> tags){
		this.location = location;
		this.price = price;
		this.tags = tags;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
