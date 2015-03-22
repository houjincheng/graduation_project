package anyviewj.net.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.Document;

import anyviewj.net.common.CommunicationProtocol;
import anyviewj.net.common.Terminal;

public class ClientTerminal extends Terminal{

	
	private static final ClientTerminal ct = new ClientTerminal();
	private String loginToken = "";
	private String studentID = "";
	private String password = "";



	private static final String serverIP = 
			CommunicationProtocol.SERVER_TERMINAL_IP;
	private static final int serverPort = 
			CommunicationProtocol.SERVER_TERMINAL_ACCEPTEDPORT;
	private Socket socket = null;
	
	public static void main( String[] args )
	{
		LoginFrame frame = new LoginFrame();
		ClientTerminal clientTerminal = ClientTerminal.getInstance();
	}
	
	private ClientTerminal(){}
	
	public static synchronized ClientTerminal getInstance()
	{
		return ct;
	}
	
	private boolean connect()
	{
		try {
//			System.out.println( "serverIP = " + serverIP + "\nserverPort = " + serverPort );
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
	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String token) {
		this.loginToken = token;
	}
	
	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String userAccount) {
		this.studentID = userAccount;
		System.out.println( "setStudentID = " + this.studentID );
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
