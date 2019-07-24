package Home;

import java.sql.Connection;
import java.sql.DriverManager;

/*
 * SQL Connection class
 */
public final class SQLConn {
	
	private SQLConn() {}
	
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String connection = "jdbc:mysql://localhost:3306/scms?serverTimezone=UTC";
    private static String user = "root";
    private static String password = "1234";
    private static Connection con;
    
    
	protected static Connection getConSetup() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(connection, user, password); 			
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;  
	}
    
    
    

}
