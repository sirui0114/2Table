package db.mysql;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class MySQLTableCreation {
	// Run this as Java application to reset db schema.
	public static void main(String[] args) {
		try {
			// Ensure the driver is imported.
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// This is java.sql.Connection. Not com.mysql.jdbc.Connection.
			Connection conn = null;

			// Step 1 Connect to MySQL.
			try {
				System.out.println("Connecting to \n" + MySQLDBUtil.URL);
				conn = DriverManager.getConnection(MySQLDBUtil.URL);
			} catch (SQLException e) {
				System.out.println("SQLException " + e.getMessage());
				System.out.println("SQLState " + e.getSQLState());
				System.out.println("VendorError " + e.getErrorCode());
			}
			if (conn == null) {
				return;
			}

			// Step 2 Drop tables in case they exist.
			Statement stmt = conn.createStatement();

			String sql = "DROP TABLE IF EXISTS Capacity";
			stmt.executeUpdate(sql);
			
			sql = "DROP TABLE IF EXISTS Reservations";
			stmt.executeUpdate(sql);

			sql = "DROP TABLE IF EXISTS Restuarant";
			stmt.executeUpdate(sql);

		    sql = "DROP TABLE IF EXISTS User";
			stmt.executeUpdate(sql);
			
			// Step 3. Create new tables.

			sql = "CREATE TABLE Restuarant " + "(Restaurant_idRestaurant VARCHAR(255) NOT NULL, " + " Rname VARCHAR(255) NOT NULL, " + "Rloc VARCHAR(255) NOT NULL," 
					+ "Rphone VARCHAR(45)," + "Remail VARCHAR(255),"
					+ " PRIMARY KEY ( Restaurant_idRestaurant))" ;
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE Capacity " + "(Restaurant_idRestaurant VARCHAR(255) NOT NULL, " + "  pdatetime DATETIME NOT NULL, "
					+ " capacity INT NOT NULL," + " PRIMARY KEY ( Restaurant_idRestaurant,  pdatetime)," + "FOREIGN KEY (Restaurant_idRestaurant) REFERENCES Restuarant(Restaurant_idRestaurant))";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE User " + "(User_idUser VARCHAR(255) NOT NULL , " + "Uname  VARCHAR(255) NOT NULL, "
					+ "Uphone VARCHAR(225), " + "Uemail VARCHAR(225)," + " PRIMARY KEY (User_idUser))";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE Reservations " + "(idReservation VARCHAR(255) NOT NULL, " + "User_idUser VARCHAR(255), " + "Restaurant_idRestaurant VARCHAR(255),"
					+ "psize INT(11), " + "pdatetime DATETIME,"
					+ " PRIMARY KEY ( idReservation )," + "FOREIGN KEY (User_idUser) REFERENCES User(User_idUser)," + "FOREIGN KEY (Restaurant_idRestaurant) REFERENCES Restuarant(Restaurant_idRestaurant))";
			stmt.executeUpdate(sql);

			// Step 4: insert data
			// Create a fake restaurant
			sql = "INSERT INTO User " + "VALUES (\"1111\", \"Joe\", \"3478653212\", \"fa@cooper.edu\")";

			System.out.println("Executing query:\n" + sql);
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO User " + "VALUES (\"1112\", \"Mary\", \"3479023333\", \"mary@cooper.edu\")";

			System.out.println("Executing query:\n" + sql);
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO Restuarant " + "VALUES (\"2222\", \"Amelie Pizza\", \"103 West 8th Street\", \"3478763298\", \"Amelie@gmail.com\")";
			System.out.println("Executing query:\n" + sql);
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO Restuarant " + "VALUES (\"2223\", \"Mcdonalds\", \"102 1st Avenue\", \"3472763322\", \"mc@gmail.com\")";
			System.out.println("Executing query:\n" + sql);
			stmt.executeUpdate(sql);

			sql = "INSERT INTO Reservations " + "VALUES (\"3333\", \"1111\", \"2222\", \"3\", \"2019-03-26 19:00:00\")";
			System.out.println("Executing query:\n" + sql);
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO Reservations " + "VALUES (\"3334\", \"1112\", \"2222\", \"5\", \"2015-03-27 18:00:00\")";
			System.out.println("Executing query:\n" + sql);
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO Reservations " + "VALUES (\"3335\", \"1111\", \"2223\", \"10\", \"2015-03-29 19:00:00\")";
			System.out.println("Executing query:\n" + sql);
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO Capacity " + "VALUES (\"2222\", \"2015-11-05 19:00:00\",  \"25\")";
			System.out.println("Executing query:\n" + sql);
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO Capacity " + "VALUES (\"2223\", \"2015-11-05 19:00:00\",  \"20\")";
			System.out.println("Executing query:\n" + sql);
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO Capacity " + "VALUES (\"2223\", \"2015-11-05 18:00:00\",  \"15\")";
			System.out.println("Executing query:\n" + sql);
			stmt.executeUpdate(sql);
			
			System.out.println("Import is done successfully.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
}

