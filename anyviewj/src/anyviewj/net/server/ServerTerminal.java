package anyviewj.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import anyviewj.net.common.CommunicationProtocol;
import anyviewj.net.common.RequestResolver;
import anyviewj.net.common.Terminal;
import anyviewj.net.server.database.DBConnectionPool;

public class ServerTerminal extends Terminal{
	

	
	private static final ServerTerminal serverTerminal = new ServerTerminal();
	private  int acceptedPort = 
			CommunicationProtocol.SERVER_TERMINAL_ACCEPTEDPORT;
	
	private final LoginedClientMap loginedClientMap = new LoginedClientMap();

	public final static int THREADPOOL_SIZE = 20;
	private final Executor exec = Executors.newFixedThreadPool( THREADPOOL_SIZE );
	
//	private final String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // ����JDBC����
//	private final String dbUrl = "jdbc:sqlserver://localhost:1433; DatabaseName=anyviewj"; // ���ӷ����������ݿ�test
//	private final String userName = "sa"; // Ĭ���û���
//	private final String password = "sa"; // ����
//	private final int minConnSize = 5;
//	private final int maxConnSize = 20;
	
	private DBConnectionPool connectionPool = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ServerTerminal serverTerminal = ServerTerminal.getInstance();
		serverTerminal.actived();
	}
	private ServerTerminal() {}
	private void actived()
	{
		System.out.println( "Server start..." );
		
//		connectionPool = new DBConnectionPool
//				( dbUrl, driverName, userName, password, minConnSize, maxConnSize );
		connectionPool = new DBConnectionPool();
		try {
			ServerSocket ss = new ServerSocket( acceptedPort );
			
			while ( true )
			{
				final Socket socket = ss.accept();
				
				socket.setSoTimeout( 10000 );
				Runnable task = new Runnable() {
					
					@Override
					public void run() {
						server( socket );
					}
				};
				
				exec.execute( task );
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	public static ServerTerminal getInstance()
	{
		return serverTerminal;
	}
	public int server( Socket socket )
	{
		Document doc = receiveData( socket );
		
		if ( verify( doc ) )
		{
			doc = fireChangeResolver( doc );
		}
		
		sendData( doc, socket );
		return 0;
	}
	private Document fireChangeResolver( Document doc )
	{
//		��Ҫ��������
		String resolverName = 
				doc.getDocumentElement().
				getAttribute( CommunicationProtocol.REQUEST_DOCUMENT_REQUSETNODE_RESOLVER );
		RequestResolver resolver = null;
		Document result = null;
		try {
			resolver = ( RequestResolver )( Class.forName(resolverName).newInstance() );
			
			result = resolver.execute( doc );
			
			
		} catch (InstantiationException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * ���һ����������ʣ������һ���ɹ���½�Ŀͻ��˵����󷵻�true,
	 * ���������һ��û�гɹ���½�Ŀͻ��˵�������ôֻ���ڸ������ǵ�½�����ǲŻ᷵��true,
	 * �����������false
	 * @param doc
	 * @return
	 */
	private boolean verify( Document doc )
	{
		System.out.println( "Server verify..." );
		
		Element requestElement = 
				( Element )doc.getElementsByTagName( REQUEST_DOCUMENT_REQUESTNODE ).item( 0 );
		
		String loginToken = requestElement.getAttribute( REQYEST_DOCUMENT_LOGINTOKEN );
		String studentID = requestElement.getAttribute( REQYEST_DOCUMENT_STUDENTID );
		String requestResolver = requestElement.getAttribute( REQUEST_DOCUMENT_REQUSETNODE_RESOLVER );
		
		if ( requestResolver.equals( LoginRequestResolver.class.getName() ) )
//		����һ����½����
		{
			return true;
		}
		else
//		�ⲻ��һ����½����
		{
			if ( ( loginedClientMap.getToken( studentID ) != null )
					&& ( String.valueOf( loginedClientMap.getToken( studentID ) ) ).equals( loginToken ) )
//			���������Գɹ���½�Ŀͻ���
			{
				return true;
			}
			return false;
		}
	}
	public DBConnectionPool getConnectionPool() {
		return connectionPool;
	}
	public LoginedClientMap getLoginedClientMap() {
		return loginedClientMap;
	}
}
