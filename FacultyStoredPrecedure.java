package MySQLDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.Types;
import java.sql.ResultSetMetaData;
import java.sql.*;

import java.util.*;  

public class FacultyStoredPrecedure 
{
	
	/*
	 * Sachin Test Two - Files System
	 */
/*
	static final String databasePrefix ="cs366-2221_sureshs13";
    static final String netID ="sureshs13"; // Please enter your netId
    static final String hostName ="washington.uww.edu"; //140.146.23.39 or washington.uww.edu
    static final String databaseURL ="jdbc:mariadb://"+hostName+"/"+databasePrefix;
    static final String password="ss4234"; // please enter your own password
*/ 		  
	static String databasePrefix ="cs366-2221_sureshs13";
    static String netID = "sureshs13"; // Please enter your netId
    static final String hostName ="washington.uww.edu"; //140.146.23.39 or washington.uww.edu
    static final String databaseURL ="jdbc:mariadb://"+hostName+"/"+databasePrefix;
    static String password = "ss4234"; // please enter your own password
    
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;

	public Boolean Connection()
	{

		try 
		{
			Class.forName("org.mariadb.jdbc.Driver");
		    System.out.println("databaseURL"+ databaseURL);
			connection = DriverManager.getConnection(databaseURL, netID, password);
			System.out.println("Successfully connected to the database");
			return true;
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			return false;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			return false;
		}
	} // end of Connection
	
	public int patientsProcedure()
	{
		/*
		 
		delimiter $$
		drop procedure if exists getTotalPatients;
		create procedure getTotalPatients(INOUT total int) 
		begin 
		      SELECT COUNT(PatientID) into total
		      from Patients;

		end $$
		delimiter ; 

		*/
			// the space after the delimiter is NECESsARY
			
		/*
		 * type in the following after you copy paste the delimiter:
		 * 
		set @total = 0;
		 
		call getTotalPatients(@total);
		 
		SELECT @total;
		
		
		+--------+
		| @total |
		+--------+
		|  63360 |
		+--------+


		 */
		int total = 0;

		try 
		{
			statement = connection.createStatement();
			total =0;
			CallableStatement myCallStmt = connection.prepareCall("{call "+"getTotalPatients"+"(?)}");
	        myCallStmt.registerOutParameter(1,Types.BIGINT);
	        myCallStmt.execute();
	        total = myCallStmt.getInt(1);
	        System.out.println("Total Amount of Patients = "+ total);
	        return total;

		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return total;
	}
	
	public void facultyProcedure(String spName) 
	{
		/*
		 
		delimiter $$
		drop procedure if exists getTotalFaculty;
		create procedure getTotalFaculty(INOUT total int) 
		begin 
		      SELECT COUNT(*) into total
		      from Faculty;

		end $$
		delimiter ; 

		*/
			// the space after the delimiter is NECESsARY
			
		/*
		 * type in the following after you copy paste the delimiter:
		 * 
		set @total = 0;
		 
		call getTotalFaculty(@total);
		 
		SELECT @total;
		
		
		+--------+
		| @total |
		+--------+
		|     15 |
		+--------+

		 */

		try 
		{
			statement = connection.createStatement();
			int total =0;
			CallableStatement myCallStmt = connection.prepareCall("{call "+spName+"(?)}");
	        myCallStmt.registerOutParameter(1,Types.BIGINT);
	        myCallStmt.execute();
	        total = myCallStmt.getInt(1);
	        System.out.println("The total faculty = "+ total);

		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void studentProcedure(String spName)
	{
		/*
		 * 	DELIMITER CODE:
		 * -------------
		 * 
		 	delimiter $$
			drop procedure if exists getStudentInfo;
			create procedure getStudentInfo(IN studentID VARCHAR(255)) 
			begin 
			      SELECT *
       			  FROM Student
       			  WHERE snum = studentID;
			end $$
			delimiter ; 
			
		* -------------
		* 
		* TESTING THE DELIMITTER IN CONSOLE (REMEMBER TO PICK AN ID TO TEST FIRST):
		* 
		* set @studentID = [INSERT ID HERE];
		 
		  call getStudentInfo(@studentID);
		 			
			
		 */
		
		try 
		{
			statement = connection.createStatement();
			// int total = 0;
			String plSql = "{call getStudentInfo(?)}";
			CallableStatement myCallStmt = connection.prepareCall(plSql);
			myCallStmt.setString(1, spName);
	        // myCallStmt.registerOutParameter(1,Types.BIGINT);
	        resultSet = myCallStmt.executeQuery();
	        // total = myCallStmt.getInt(1);
	        // System.out.println("The total faculty = "+ total);
	        while (resultSet.next())
			{
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String major = resultSet.getString(3);
				String level = resultSet.getString(4);
				int age = resultSet.getInt(5);

				/*
				 * Display values
				 */
				System.out.print("ID: " + id);
				System.out.print(", Name: " + name);
				System.out.print(", Major: " + major);
				System.out.print(", Level: " + level);
				System.out.println(", Age: " + age);
			}

		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
	}
	
	public void testProcedure()
	{
		/*
		 
 	delimiter $$
	drop procedure if exists getCountries;
   	create procedure getCountries()
    	begin
   		SELECT DISTINCT(Country)
    	FROM Patients;
    end $$
    delimiter ;
	
		 */
		
		try 
		{
			statement = connection.createStatement();
			String plSql = "{call getCountries()}";
			CallableStatement myCallStmt = connection.prepareCall(plSql);
	        Boolean resultTest = myCallStmt.execute();
	        if (resultTest == true)
			{
	        	ResultSet res = myCallStmt.getResultSet();
	        	System.out.println("Countries Are:");
	        	
	        	while (res.next())
	        	{
	        		System.out.println(res.getString("Country"));
	        	}
	        	System.out.println("\n");

			}
	        
	        myCallStmt.close();

		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	public void simpleQuery(String sqlQuery) {
	    
    	try {
    		statement = connection.createStatement();
    		resultSet = statement.executeQuery(sqlQuery);

    		ResultSetMetaData metaData = resultSet.getMetaData();
    		int columns = metaData.getColumnCount();

    		for (int i=1; i<= columns; i++) 
    		{
    			System.out.print(metaData.getColumnName(i)+"\t");
    		}

    		System.out.println();

    		while (resultSet.next()) 
    		{
       
    			for (int i=1; i<= columns; i++) 
    			{
    				System.out.print(resultSet.getObject(i)+"\t\t");
    			}
    			System.out.println();
    		}
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    } // end of simpleQuery method
	
	public static void main(String args[]) 
	{  
		
		
		FacultyStoredPrecedure demoObj = new FacultyStoredPrecedure();
		demoObj.Connection();
		demoObj.testProcedure();
				
		// HERE
		
		try 
		{
			Connection testC = DriverManager.getConnection(databaseURL, netID, password);
			PreparedStatement pst = testC.prepareStatement("SELECT * FROM Symptoms;");
			ResultSet s = pst.executeQuery();
			while(s.next()){
	             String symptomid = s.getString("SymptomID");
	             String symptomd = s.getString("SymptomDescription");

	             //Printing Results
	             System.out.println(symptomid + " " + symptomd);
	        }
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		// DONE HERE
		// REF USED: https://www.tutorialsfield.com/how-to-connect-mysql-database-in-java-using-eclipse/
		
		String sqlQuery = "SELECT P.PatientID, S.SeverityDescription FROM Patients P, Severities S WHERE P.PatientID = 1 AND P.SeverityID = S.SeverityID;";
    	demoObj.simpleQuery(sqlQuery);
    	
    	
    	 
    	
		/*
		FacultyStoredPrecedure demoObj = new FacultyStoredPrecedure();
		demoObj.Connection();
		String spName ="getTotalFaculty";
		demoObj.facultyProcedure(spName);
		
		Scanner scanner = new Scanner(System.in);
		while (true)
		{
			System.out.print("Enter StudentID: ");
			String studentID = scanner.nextLine();

			if (studentID.equals("exit"))
			{
				break;
			}

			demoObj.studentProcedure(studentID);

		}
		scanner.close();
		*/
		
		
		
	}
}

