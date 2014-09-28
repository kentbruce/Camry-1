package com.camray.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.prefs.Preferences;

public class DBConnection {
	
	private static Connection connection = null;
	//private final String URL="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + "E:\\hotel\\camray.accdb";
	private final String URL="jdbc:db2://localhost:50000/";
	private final String DBNAME="camry";
	private final String USERNAME= "db2admin";
	private final String PASSWORD= "db2admin";
	private final String DRIVER= "com.ibm.db2.jcc.DB2Driver";
	
	public Connection acquireConnection()
	{
		
		try {
			
			//Driver loaded
			Class.forName(DRIVER);
		connection = DriverManager.getConnection(URL+DBNAME,USERNAME,PASSWORD);
	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	public String getadminpass(){
		String pass = null ;
		DBConnection db=new DBConnection();
		connection =db.acquireConnection();
		String inst_bill_itm_lst="select password from camry.user_list where user_name ='"+"admin"+"'";
			try {
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(inst_bill_itm_lst);
				if (rs.next()){
					pass=rs.getString("password");
					connection.close();
				}
				}catch(Exception e){
					
				}
		
		return pass;
	}

}
