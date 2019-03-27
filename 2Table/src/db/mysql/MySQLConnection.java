package db.mysql;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import entity.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class MySQLConnection {
	private Connection conn;
	
	public MySQLConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = (Connection) DriverManager.getConnection(MySQLDBUtil.URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String rNameTorID(String rName) {
		if (conn == null) {
			return null;
		}
		//translate r_accont to restaurantID
		try {
			String sql = "SELECT Restaurant_idRestaurant from Restuarant WHERE Rname = ?";
			PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
			statement.setString(1, rName);
			ResultSet rs = statement.executeQuery();
			if (rs.next())
				return rs.getString("Restaurant_idRestaurant");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public List<rv_Item> getRvList(String rID, LocalDateTime time) {
		if (conn == null) {
			return null;
		}
		List<rv_Item> rvList = new ArrayList<>();
		String sql = "";
		if (time == null) {
			try {
				sql = "SELECT * from Reservations WHERE Restaurant_idRestaurant = ?";
				PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
				statement.setString(1, rID);
				ResultSet rs = statement.executeQuery();
				rv_Item.rvItemBuilder builder = new rv_Item.rvItemBuilder();
				while (rs.next()) {
					builder.setrItemID(rs.getString("Restaurant_idRestaurant"));
					builder.setRvItemID(rs.getString("idReservation"));
					builder.setRSize(rs.getInt("psize"));
					builder.setuItemID(rs.getString("User_idUser"));
					builder.setRvTime(rs.getTimestamp("pdatetime").toLocalDateTime());
					rvList.add(new rv_Item(builder));
				}
			}catch (Exception e) {
					System.out.println(e.getMessage());
				}
		}else {
			try {
				java.text.SimpleDateFormat sdf = 
					    new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				String currentTime = sdf.format(time);
				sql = "SELECT * from Reservations (Restaurant_idRestaurant, pdatetime) VALUES (?, ?)";
				PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
				statement.setString(1, rID);
				statement.setString(2, currentTime);
				ResultSet rs = statement.executeQuery();
				rv_Item.rvItemBuilder builder = new rv_Item.rvItemBuilder();
				while (rs.next()) {
					builder.setrItemID(rs.getString("Restaurant_idRestaurant"));
					builder.setRvItemID(rs.getString("idReservation"));
					builder.setRSize(rs.getInt("psize"));
					builder.setuItemID(rs.getString("User_idUser"));
					builder.setRvTime(rs.getTimestamp("pdatetime").toLocalDateTime());
					rvList.add(new rv_Item(builder));
				}
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return rvList;
	}
	
	public List<String> getContactInfo(String uID) {
		if (conn == null) {
			return null;
		}
		List<String> cList = new ArrayList<>();
		try {
			String sql = "SELECT * from User WHERE User_idUser = ?";
			PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
			statement.setString(1, uID);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				cList.add(rs.getString("Uname"));
				cList.add(rs.getString("Uphone"));
				cList.add(rs.getString("Uemail"));
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return cList;
	}
	
	public boolean setCapacity(String rID, int capacity, String time) {
		if (conn == null) {
			return false;
		}
		try {
			String sql = "REPLACE INTO Capacity (Restaurant_idRestaurant, pdatetime, capacity) VALUES (?, ?, ?)";
			PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
			statement.setString(1, rID);
			statement.setString(2, time);
			statement.setInt(3, capacity);
			statement.execute();
		}catch (SQLException e) {
            e.printStackTrace();
		}
		return true;
	}
}
