package db.mysql;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import entity.*;
import entity.u_Item.uItemBuilder;

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
			String sql = "SELECT Restaurant_idRestaurant from Restaurant WHERE Rname = ?";
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
	
	public String getUrl(String ID, boolean isUser) {
		if (conn == null) {
			return null;
		}
		try {
			if (isUser) {
				String sql = "SELECT url from User WHERE User_idUser = ?";
				PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
				statement.setString(1, ID);
				ResultSet rs = statement.executeQuery();
				if (rs.next())
					return rs.getString("url");
			}else {
				String sql = "SELECT url from Restaurant WHERE Restaurant_idRestaurant = ?";
				PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
				statement.setString(1, ID);
				ResultSet rs = statement.executeQuery();
				if (rs.next())
					return rs.getString("url");
			}
		}catch (Exception e) {
				System.out.println(e.getMessage());
		}
			return null;
	}
	
	public String getName(String ID, boolean isUser) {
		if (conn == null) {
			return null;
		}
		//translate r_accont to restaurantID
		try {
			if (isUser) {
				String sql = "SELECT Uname from User WHERE User_idUser = ?";
				PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
				statement.setString(1, ID);
				ResultSet rs = statement.executeQuery();
				if (rs.next())
					return rs.getString("Uname");
			}else {
				String sql = "SELECT Rname from Restaurant WHERE Restaurant_idRestaurant = ?";
				PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
				statement.setString(1, ID);
				ResultSet rs = statement.executeQuery();
				if (rs.next())
					return rs.getString("Rname");
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public List<r_Item> getRandRsList() {
		if (conn == null) {
			return null;
		}
		List<r_Item> rsList = new ArrayList<>();

		String sql = "SELECT * from Restaurant ORDER BY RAND() LIMIT 50";
		try {
			PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			r_Item.rItemBuilder builder = new r_Item.rItemBuilder();
			while (rs.next()) {
				builder.setAddress(rs.getString("Rloc"));
				builder.setrItemID(rs.getString("Restaurant_idRestaurant"));
				builder.setRemail(rs.getString("Remail"));
				builder.setRphone(rs.getString("Rphone"));
				builder.setrName(rs.getString("Rname"));
				builder.setUrl(rs.getString("url"));
				rsList.add(new r_Item(builder));
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rsList;
	}
	
	public r_Item getResInfoByID(String ID) {
		if (conn == null) {
			return null;
		}
		String sql = "SELECT * from Restaurant WHERE Restaurant_idRestaurant = ?";
		r_Item.rItemBuilder builder = new r_Item.rItemBuilder();
		try {
			PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
			statement.setString(1, ID);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				builder.setAddress(rs.getString("Rloc"));
				builder.setrItemID(rs.getString("Restaurant_idRestaurant"));
				builder.setRemail(rs.getString("Remail"));
				builder.setRphone(rs.getString("Rphone"));
				builder.setrName(rs.getString("Rname"));
				builder.setUrl(rs.getString("url"));
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new r_Item(builder);
	}
	
	public List<rv_Item> getRvList(String ID, LocalDateTime time, boolean isRes) {
		if (conn == null) {
			return null;
		}
		List<rv_Item> rvList = new ArrayList<>();
		String sql = "";
		if (time == null) {
			try {
				if (isRes) {
					sql = "SELECT * from Reservations WHERE Restaurant_idRestaurant = ?";
				}else {
					sql = "SELECT * from Reservations WHERE User_idUser = ?";
				}
					PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
					statement.setString(1, ID);
					ResultSet rs = statement.executeQuery();
					rv_Item.rvItemBuilder builder = new rv_Item.rvItemBuilder();
					while (rs.next()) {
						builder.setrItemID(rs.getString("Restaurant_idRestaurant"));
						builder.setRvItemID(rs.getString("idReservation"));
						builder.setRSize(rs.getInt("psize"));
						builder.setuItemID(rs.getString("User_idUser"));
						builder.setRvTime(rs.getTimestamp("pdatetime").toLocalDateTime());
						builder.setR(getResInfoByID(rs.getString("Restaurant_idRestaurant")));
						builder.setU(getUserInfo(rs.getString("User_idUser")));
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
				statement.setString(1, ID);
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
	
	public u_Item getUserInfo(String uID) {
		if (conn == null) {
			return null;
		}
		uItemBuilder builder = new uItemBuilder();
		try {
			String sql = "SELECT * from User WHERE User_idUser = ?";
			PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
			statement.setString(1, uID);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				builder.setName(rs.getString("Uname"));
				builder.setPhone(rs.getString("Uphone"));
				builder.setEmail(rs.getString("Uemail"));
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new u_Item(builder);
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
	
	public boolean setCapacity(String rID, int capacity) {
		if (conn == null) {
			return false;
		}
		try {
			String sql = "REPLACE INTO Restaurant (Restaurant_idRestaurant,  capacity) VALUES (?, ?)";
			PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
			statement.setString(1, rID);
			statement.setInt(2, capacity);
			statement.execute();
		}catch (SQLException e) {
            e.printStackTrace();
		}
		return true;
	}
	public int getCapacity(String rID) {
		if (conn == null) {
			return 0;
		}
		try {
			String sql = "SELECT  capacity from Restaurant WHERE Restaurant_idRestaurant = ?";
			PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
			statement.setString(1, rID);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return rs.getInt("capacity");	
			}
		}catch (SQLException e) {
            e.printStackTrace();
		}
		return 0;
	}
	public boolean verifyLogin(String Id, String password, boolean isUser) {
		if (conn == null) {
			return false;
		}
		try {
			String sql;
			if (isUser) {
				sql = "SELECT User_idUser from User WHERE User_idUser = ? and pwd = ?";
			}else {
				sql = "SELECT Restaurant_idRestaurant from Restaurant WHERE Restaurant_idRestaurant = ? and pwd = ?";
			}
			PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
			statement.setString(1, Id);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return true;	
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public boolean setReservation(String uId, String rId, int psize, String pdatetime) {
		if (conn == null) {
			return false;
		}
		try {
			//check capacity
			String checkCp = "SELECT psize from Reservations WHERE Restaurant_idRestaurant = ? and pdatetime = ?";
			PreparedStatement statement = (PreparedStatement) conn.prepareStatement(checkCp);
			statement.setString(1, rId);
			statement.setString(2, pdatetime);
			ResultSet res = statement.executeQuery();
			int sum = 0;
			while (res.next()) {
			      int c = res.getInt(1);
			      sum = sum + c;
			    }
			if (sum + psize > getCapacity(rId)) {
				return false;
			}
			String sql = "INSERT INTO Reservations VALUES (?, ?, ?, ?, ?)";
			statement = (PreparedStatement) conn.prepareStatement(sql);
			statement.setString(1, UUID.randomUUID().toString());
			statement.setString(2, uId);
			statement.setString(3, rId);
			statement.setInt(4, psize);
			statement.setString(5, pdatetime);
			statement.execute();
		}catch (SQLException e) {
            e.printStackTrace();
		}
		return true;
	}

	public boolean registerU(u_Item user) {
		if (conn == null) {
			return false;
		}
		try {
			String sql = "INSERT INTO  User VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
			statement.setString(1, user.getUserID());
			statement.setString(2, user.getName());
			statement.setString(3, user.getPhone());
			statement.setString(4, user.getEmail());
			statement.setString(5, user.getPassword());
			statement.setString(6, user.getUrl());
			statement.execute();
		}catch (SQLException e) {
            e.printStackTrace();
		}
		return true;
	}
	
	public boolean registerR(r_Item res) {
		if (conn == null) {
			return false;
		}
		try {
			String sql = "INSERT INTO  Restaurant VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
			statement.setString(1, res.getrItemID());
			statement.setString(2, res.getrName());
			statement.setString(3, res.getAddress());
			statement.setString(4, res.getRphone());
			statement.setString(5, res.getRemail());
			statement.setInt(6, res.getCapacity());
			statement.setString(7, res.getPassword());
			statement.setString(8, res.getUrl());
			statement.execute();
		}catch (SQLException e) {
            e.printStackTrace();
		}
		return true;
	}
}
