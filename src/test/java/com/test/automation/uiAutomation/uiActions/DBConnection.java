package com.test.automation.uiAutomation.uiActions;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;


public class DBConnection {
		     public static void main(String[] args) throws ClassNotFoundException, SQLException {
				   
		    	// Connect to database
		         String hostName = "wsc-sqlserver1.database.windows.net";
		         String dbName = "IPM1_Stage";
		         String user = "wscAdmin";
		         String password = "AdminPWD!234";
		         String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);
		         Connection connection = null;

		         try {
		                 connection = DriverManager.getConnection(url);
		                 String schema = connection.getSchema();
		                 System.out.println("Successful connection - Schema: " + schema);

		                 System.out.println("Query data example:");
		                 System.out.println("=========================================");

		                          
		         }
		         catch (Exception e) {
		                 e.printStackTrace();
		         }
		         finally{
		        	 if(connection!=null)
		        	 {
		        		 connection.close();
		        		 System.out.println("DB Connection closed successfully");
		        	 }
		         }
		     }
}


