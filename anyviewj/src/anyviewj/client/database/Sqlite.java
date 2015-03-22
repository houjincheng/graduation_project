package anyviewj.client.database;

import java.sql.*;
import java.lang.Exception;
import SQLite.*;
import SQLite.JDBC2x.*;

public class Sqlite {
	
	static Connection getConnection(){
		Connection ct = null;
		try{
			Class.forName("SQLite.JDBCDriver");
			ct = DriverManager.getConnection(
					"jdbc:sqlite:/./questbank.db",
					"",""
					);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return ct;
	} 
}
