package entity;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class r_Item {
	private String rItemID;
	private String rName;
	private String address;
	private Set<String> categories;
	private String Rphone;
	private String Remail;
	private String password;
	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	int capacity;
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public String getRphone() {
		return Rphone;
	}
	public String getRemail() {
		return Remail;
	}
	public String getrItemID() {
		return rItemID;
	}

	public String getrName() {
		return rName;
	}

	public String getAddress() {
		return address;
	}

	public Set<String> getCategories() {
		return categories;
	}

	public void setrItemID(String rItemID) {
		this.rItemID = rItemID;
	}

	public void setrName(String rName) {
		this.rName = rName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCategories(Set<String> categories) {
		this.categories = categories;
	}

	public r_Item(rItemBuilder builder) {
		this.rItemID = builder.rItemID;
		this.rName = builder.rName;
		this.address = builder.address;
		this.categories = builder.categories;
		this.Remail = builder.Remail;
		this.Rphone = builder.Rphone;
		this.url = builder.url;
		this.password = builder.password;
		this.capacity = builder.capacity;
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("restaurant", rName);
			obj.put("address", address);
			obj.put("email", Remail);
			obj.put("phone", Rphone);
			obj.put("id", rItemID);
			obj.put("url", url);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	//nested static class for builder
	public static class rItemBuilder {
		private String url;
		private String rItemID;
		private String rName;
		private double rating;
		private String address;
		private Set<String> categories;
		private String Rphone;
		private String Remail;
		int capacity;
		private String password;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public int getCapacity() {
			return capacity;
		}
		public void setCapacity(int capacity) {
			this.capacity = capacity;
		}
		public void setRphone(String Rphone) {
			this.Rphone = Rphone;
		}
		public void setRemail (String Remial) {
			this.Remail = Remial;
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

	}
}
