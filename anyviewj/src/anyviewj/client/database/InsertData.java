package anyviewj.client.database;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertData {
	
	public static void main(String[] args) throws Exception{
		Connection ct = null;
		ct = Sqlite.getConnection();
		if( ct == null) return;
		
		Statement s = ct.createStatement();
		while(true){
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String clause = input.readLine();
			
			s.execute(clause);
			
		}
	}
}
