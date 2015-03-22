package anyviewj.net.server;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import anyviewj.client.database.FilefingerPrint;
import anyviewj.net.server.database.DBConnectionPool;

public class FilePrintserver {
	public static FilePrintserver fp= new FilePrintserver();
	Connection conn = null;
	
	static String createsql="create table filefingerprint(sno varchar(max), filename varchar(max), filepath varchar(max), fingerprint varchar(max))";
	static String selectsql ="select fingerprint from filefingerprint where sno=? and filename=? and filepath=?";
	static String updatesql = "update filefingerprint set fingerprint=? where sno=? and filename=? and filepath=?";
	static String insertsql = "insert into filefingerprint values(?, ?, ?, ?)";
	
	FilePrintserver(){
		ServerTerminal st = ServerTerminal.getInstance();
		DBConnectionPool dbp= st.getConnectionPool();
		conn = dbp.getConnection();
	}
	
	static String getFileprint(String sno,File f){
		FilePrintserver fp = FilePrintserver.fp;
		String fingerprint=null;
		
		fingerprint=fp.selectfromdb(sno, f);
		
		if(fingerprint==null){
			fingerprint=FilefingerPrint.getMD5(f);
		}
		
		return fingerprint;
	}
	
	private String selectfromdb(String sno,File f){
		String fingerprint=null;
		ResultSet rs=null;
		
		try {
			PreparedStatement preparestmt = conn.prepareStatement(selectsql);
			preparestmt.setString(1, sno);
			preparestmt.setString(2, f.getName());
			preparestmt.setString(3, f.getAbsolutePath());
			rs=preparestmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			int er=e.getErrorCode();
			e.printStackTrace();
		}
		
		if(rs!=null){
			try {
				while(rs.next()){
					fingerprint=rs.getString(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return fingerprint;
	}
	
	public static Boolean setFileprint(String sno, String md5, File f){
		Boolean state=false;
		FilePrintserver fp = FilePrintserver.fp;
		
		//受 sql 语句影响的行数
		int count;
		
		try {
			PreparedStatement preparestmt = fp.conn.prepareStatement(updatesql);
			preparestmt.setString(1, md5);
			preparestmt.setString(2, sno);
			preparestmt.setString(3, f.getName());
			preparestmt.setString(4, f.getAbsolutePath());
			count=preparestmt.executeUpdate();
			if(count!=0){
				state=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//sqlserver 表不存在的错误代码
			if(e.getErrorCode()==208){
				try {
					PreparedStatement preparestmt = fp.conn.prepareStatement(createsql);
					Boolean success=preparestmt.execute();
					if(success==false){
						System.out.println("无法创建表，创建语句为：" + createsql);
					}else{
						state=setFileprint(sno, md5, f);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			e.printStackTrace();
		}
		
		//数据库 表中没这个条目
		if(state==false){
			PreparedStatement preparestmt;
			try {
				preparestmt = fp.conn.prepareStatement(insertsql);
				preparestmt.setString(1, sno);
				preparestmt.setString(2, f.getName());
				preparestmt.setString(3, f.getAbsolutePath());
				preparestmt.setString(4, md5);
				count =preparestmt.executeUpdate();
				if(count!=0){
					state=true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return state;
	}
	
	
}
