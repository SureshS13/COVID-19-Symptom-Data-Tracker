package MySQLDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.Types;

public class StudentStoredProcedure {

	static final String databasePrefix ="cs366-2221_sureshs13";
    static final String netID ="sureshs13"; // Please enter your netId
    static final String hostName ="washington.uww.edu"; //140.146.23.39 or washington.uww.edu
    static final String databaseURL ="jdbc:mariadb://"+hostName+"/"+databasePrefix;
    static final String password="ss4234"; // please enter your own password
     		    
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	public void Connection(){

		try {
			Class.forName("org.mariadb.jdbc.Driver");
		    System.out.println("databaseURL"+ databaseURL);
			connection = DriverManager.getConnection(databaseURL, netID, password);
			System.out.println("Successfully connected to the database in Student");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	} // end of Connection
}