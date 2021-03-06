package entity;

import org.json.JSONException;
import org.json.JSONObject;

public class u_Item {
	private String UserID;
	private String name;
	private String phone;
	private String email;
	private String password;
	private String url;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserID() {
		return UserID;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public static class uItemBuilder {
		String url;
		String UserID;
		String name;
		String phone;
		String email;
		String password;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public void setUserID(String userID) {
			UserID = userID;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
	}
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("name", name);
			obj.put("phone", phone);
			obj.put("email", email);
			obj.put("phone", phone);
			obj.put("id", UserID);
			obj.put("url", url);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	public u_Item(uItemBuilder builder) {
		this.UserID = builder.UserID;
		this.name = builder.name;
		this.email = builder.email;
		this.phone = builder.phone;
		this.password = builder.password;
		this.url = builder.url;
	}
}
