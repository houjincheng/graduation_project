package anyviewj.net.table;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.Document;

import anyviewj.net.client.ClientTerminal;
import anyviewj.net.common.CommunicationProtocol;
import anyviewj.net.common.Terminal;

public class TableTerminal extends Terminal{

	private String studentID = "";
	private static final TableTerminal tt = new TableTerminal();
	private static final String serverIP = 
			CommunicationProtocol.SERVER_TERMINAL_IP;
	private static final int serverPort = 
			CommunicationProtocol.SERVER_TERMINAL_ACCEPTEDPORT;
	private Socket socket = null;
	
	public TableTerminal(){
		
	} 
	
	public static synchronized TableTerminal getInstance()
	{
		return tt;
	}
	
	private boolean connect()
	{
		try {
			this.socket = new Socket( serverIP, serverPort );
			return true;
		} catch (UnknownHostException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return false;
	}
	
	public Document server( Document doc ) 
	{
		Document result = null;
		connect();
		sendData( doc, socket );
		try {
			result = receiveData( socket );
			
			this.socket.close();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		this.socket = null;
		return result;
	}
	
	public String getStudentID() {
		return studentID;
	}
	
	public void setStudentID(String userAccount) {
		this.studentID = userAccount;
		System.out.println( "setStudentID = " + this.studentID );
	}
	
	
}
