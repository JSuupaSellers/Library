package dBHelpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * In this example, we encapsulate the database connection code
 * into a single class, and add a bit of error checking.
 * 
 * This class uses the "Singleton" pattern. Only one instance 
 * of Connection is ever created. If the instance already exists
 * that reference is returned. If no instance exists, one is created.
 */
public class MyDbConnection {
	private static final String URL = "jdbc:mysql://database-2.crq8qugucc80.us-east-1.rds.amazonaws.com/scif_library";
    private static final String USERNAME = "?user=admin";
    private static final String PASSWORD = "&password=Beachside";
    
    private static String urlauth = URL+USERNAME+PASSWORD;
	
	public static Connection getConnection() throws SQLException{
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DriverManager.getConnection(urlauth);	
	}	
}
