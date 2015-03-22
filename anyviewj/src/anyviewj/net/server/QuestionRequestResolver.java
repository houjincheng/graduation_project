package anyviewj.net.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import sun.misc.BASE64Encoder;
import anyviewj.net.common.RequestResolver;
import anyviewj.net.server.database.DBConnectionPool;

public class QuestionRequestResolver extends RequestResolver{

	@Override
	public Document execute(Document doc) {
		// TODO Auto-generated method stub
		Document result = null;

		result = createFormattedResultDocment();

		Element root = doc.getDocumentElement();
		Element questNode = (Element) root.getElementsByTagName(
				QUESTION_REQUEST).item(0);

		String type = questNode.getAttribute(QUESTION_TYPE);
		String chapter = questNode.getAttribute(QUESTION_CHAPTER);
		String question = questNode.getAttribute(QUESTION_NAME);
		
		System.out.println("请求题目原题？ : " + type);
		System.out.println("章名 : " + chapter);
		System.out.println("题名 : " + question);
		
		

		int value = RESOLVE_FAILE;
		value = process(result, type, chapter, question);
		
		signResult(result, String.valueOf(value));
		return result;
	}
	
	private int process(Document result, String type, String chapter, String question){
		int state = RESOLVE_FAILE;
		
		String dirpath = SERVER_FILE_PATH;
		
		DBConnectionPool connPool = ServerTerminal.getInstance()
				.getConnectionPool();
		Connection conn = connPool.getConnection();
		
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return RESOLVE_FAILE;
		}
		/*
//		String sql = "select english from dbo.题目  where Chinese=" + "\'单向链表\'";
		String sql = "select english from 题目 where Chinese='" + question+"'";
		try {
			resultSet = statement.executeQuery( sql );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return RESOLVE_FAILE;
		}
		Object o=null;
		try {
			while(resultSet.next()){
				o = resultSet.getObject(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return RESOLVE_FAILE;
		}
		if (o==null){
			System.out.println("题目表中不存在Chinese="+question+"的题");
			return RESOLVE_FAILE;
		}
		String filename=null;
		filename=o.toString();
		System.out.print("filename: "+filename);
		*/
		String filename = question;
		File file = new File(dirpath+chapter+'/'+filename+".zip");
		
		if(file.isFile()){
			state=RESOLVE_SUCCESSED;
			int len = (int) file.length();
			byte[] filein = new byte[len];
			String tmp=null;
			try {
				FileInputStream a = new FileInputStream(file);
				a.read(filein);
				tmp=new BASE64Encoder().encode(filein);
			}catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return RESOLVE_FAILE;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return RESOLVE_FAILE;
			}
			
			Element dataNode= (Element) result.getElementsByTagName(REQUEST_DOCUMENT_DATANODE).item(0);
			Text textNode = result.createTextNode(tmp);
			dataNode.appendChild(textNode);
			dataNode.setAttribute(QUESTION_FILE,filename);
		}else{
			System.out.println(file.toString()+ " 文件不存在");
		}
		
		return state;
	};
	
}
