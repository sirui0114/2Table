package entity;

import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class rv_Item {
	private String rvItemID;
	private String rItemID;
	private String uItemID;
	private LocalDateTime rvTime;
	private int rSize;
	private r_Item r;
	public r_Item getR() {
		return r;
	}
	public u_Item getU() {
		return u;
	}
	public void setR(r_Item r) {
		this.r = r;
	}
	public void setU(u_Item u) {
		this.u = u;
	}
	private u_Item u;
	
	public int getRSize() {
		return rSize;
	}
	public String getRvItemID() {
		return rvItemID;
	}
	public String getrItemID() {
		return rItemID;
	}
	public String getuItemID() {
		return uItemID;
	}
	public LocalDateTime getRvTime() {
		return rvTime;
	}
	public void setRvItemID(String rvItemID) {
		this.rvItemID = rvItemID;
	}
	public void setrItemID(String rItemID) {
		this.rItemID = rItemID;
	}
	public void setuItemID(String uItemID) {
		this.uItemID = uItemID;
	}
	public void setRvTime(LocalDateTime rvTime) {
		this.rvTime = rvTime;
	}
	public void setRSize(int rSize) {
		this.rSize = rSize;
	}
	public rv_Item(rvItemBuilder builder) {
		this.rvItemID = builder.rvItemID;
		this.rItemID = builder.rItemID;
		this.uItemID = builder.uItemID;
		this.rvTime = builder.rvTime;
		this.rSize = builder.rSize;
		this.r = builder.r;
		this.u = builder.u;
	}
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("restaurant_Id", rItemID);
			obj.put("reservation_Id", rvItemID);
			obj.put("user_Id", uItemID);
			obj.put("reservation_Time", rvTime);
			obj.put("r_item", r.toJSONObject());
			obj.put("u_item", u.toJSONObject());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	public static class rvItemBuilder {
		private r_Item r;
		private u_Item u;
		public r_Item getR() {
			return r;
		}
		public u_Item getU() {
			return u;
		}
		public void setR(r_Item r) {
			this.r = r;
		}
		public void setU(u_Item u) {
			this.u = u;
		}
		private String rvItemID;
		private String rItemID;
		private String uItemID;
		private LocalDateTime rvTime;
		private int rSize;
		public void setRSize(int rSize) {
			this.rSize = rSize;
		}
		public void setRvItemID(String rvItemID) {
			this.rvItemID = rvItemID;
		}
		public void setrItemID(String rItemID) {
			this.rItemID = rItemID;
		}
		public void setuItemID(String uItemID) {
			this.uItemID = uItemID;
		}
		public void setRvTime(LocalDateTime rvTime) {
			this.rvTime = rvTime;
		}
	}
}
