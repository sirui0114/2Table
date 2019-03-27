package entity;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class r_Item {
	private String rItemID;
	private String rName;
	private double rating;
	private String address;
	private Set<String> categories;
	private double distance;
	
	
	public String getrItemID() {
		return rItemID;
	}

	public String getrName() {
		return rName;
	}

	public double getRating() {
		return rating;
	}

	public String getAddress() {
		return address;
	}

	public Set<String> getCategories() {
		return categories;
	}

	public double getDistance() {
		return distance;
	}

	public void setrItemID(String rItemID) {
		this.rItemID = rItemID;
	}

	public void setrName(String rName) {
		this.rName = rName;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCategories(Set<String> categories) {
		this.categories = categories;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	private r_Item(rItemBuilder builder) {
		this.rItemID = builder.rItemID;
		this.rName = builder.rName;
		this.rating = builder.rating;
		this.address = builder.address;
		this.categories = builder.categories;
		this.distance = builder.distance;
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("restaurant_Id", rItemID);
			obj.put("restaurant", rName);
			obj.put("rating", rating);
			obj.put("address", address);
			obj.put("categories", new JSONArray(categories));
			obj.put("distance", distance);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	//nested static class for builder
	public static class rItemBuilder {
		private String rItemID;
		private String rName;
		private double rating;
		private String address;
		private Set<String> categories;
		private double distance;
		public void setrItemID(String rItemID) {
			this.rItemID = rItemID;
		}

		public void setrName(String rName) {
			this.rName = rName;
		}

		public void setRating(double rating) {
			this.rating = rating;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public void setCategories(Set<String> categories) {
			this.categories = categories;
		}

		public void setDistance(double distance) {
			this.distance = distance;
		}
	}
}
