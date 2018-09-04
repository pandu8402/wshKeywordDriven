package com.test.automation.uiAutomation.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.test.automation.uiAutomation.Utils.Resources;

public class DBConnect extends Resources{
	
	public static String hostName = Config.getProperty("hostname");
    public static String dbName = Config.getProperty("dbname");
    public static String user = Config.getProperty("user");
    public static String password = Config.getProperty("password");
    
    static Connection connection = null;

	public static void connectDB() throws SQLException
    {				   
 	// Connect to database      
      String query = Config.getProperty("queryname");
      String column = Config.getProperty("ColumnName");
      String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);
      

      try {
              connection = DriverManager.getConnection(url);
              
              System.out.println("Query data example:");
              System.out.println("=========================================");
              
              if(connection!=null)
	        	 {
             	 Statement stmt = connection.createStatement();
             	 String SQL = query;
                  ResultSet rs = stmt.executeQuery(SQL);
                  while(rs.next())
                       System.out.println(rs.getString(column));
	        	 }		                          
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
