package anyviewj.net.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import anyviewj.net.server.ServerTerminal;
/**
 * ���ݿ����ӳء�
 * �½����ݿ����ӳ�ʱ����ͨ�����������Ĺ����������½������ӳ�Ĭ�������ӵ���anyviewj���ݿ�,
 * ���򣬽���������������ӵ�ָ�����ݿ⡣
 * 
 * ͨ�� getConnectionX()�����������ӳ���ȡ�����ݿ������
 * �Ϲ�freeConnection( Connection ),��ָ�����ӷŻ����ӳ�
 * 
 * @author hou
 *
 */
public class DBConnectionPool{

	private String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // ����JDBC����
	private String dbUrl = "jdbc:sqlserver://localhost:1433; DatabaseName=anyviewj"; // ���ӷ����������ݿ�test
	private String userName = "sa"; // Ĭ���û���
	private String password = "123"; // ����
	private int minConnSize = 5;
	/** ���ݿ���������ֱ�����߳ǳ��߳���������� */
	private int maxConnSize = ServerTerminal.THREADPOOL_SIZE;
	/** ���û�п������� */
	private final ArrayBlockingQueue< Connection > queue 
	= new ArrayBlockingQueue<Connection>( maxConnSize );
	/** �½������ݿ����ӣ����������úͿ��е� */
	private int connectionCount = 0;
	/**
	 * ������ָ�����ݿ������
	 * @param dbUrl        ���ݿ��λ��
	 * @param driverName       ��������
	 * @param id           �˺�
	 * @param password     �˺�����
	 * @param minConnSize   ��������С������
	 * @param maxConnSize  ���������ӵ������
	 */
	public DBConnectionPool
	( String dbUrl, String driver, String id, String password,
			int minConnSize, int maxConnSize ) {

		
		this.dbUrl = dbUrl;
		this.driverName = driver;
		this.userName = id;
		this.password = password;
		this.minConnSize = minConnSize;
		this.maxConnSize = maxConnSize;
		
		initPool();
	}
	/**
	 * ������Ĭ�����ݿ������
	 */
	public DBConnectionPool() 
	{
		initPool();
	}
	/**
	 * �����ӳ��л�ȡһ�����ݿ������
	 * @return �ɹ��������ӣ�����һ���ӷ���null
	 */
	public Connection getConnection()
	{
		return getConnection( 1000 * 60 );
	}
	/**
	 * ��ָ��ʱ���ڻ�ȡһ�����ݿ����ӣ����򷵻�null
	 * @param timeout
	 * @return
	 */
	public Connection getConnection( long timeout )
	{
		Connection idleConnection = getConnectionImmediately();
		try {
			 
			if ( idleConnection == null )
			{
				idleConnection = queue.poll(timeout, TimeUnit.MILLISECONDS );
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return idleConnection;
	}
	/**
	 * �����������һ�����ݿ�����
	 * �����л��п��е����ӣ���ֱ�ӷ���һ��ȡ�������ӣ�
	 * ���û�п�������ӣ������½�һ���µ������ڴ���ȡ��һ�����ӷ���
	 * @return ����Ϊnull
	 */
	public Connection getConnectionImmediately()
	{
		if ( queue.size() > 0 )
		{
			return queue.poll();
		}

		for ( int i=0; 
				( i<minConnSize ) && ( connectionCount < maxConnSize ); i++ )
		{
			Connection conn = buildConnection();
			try {
				queue.put( conn );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
		return queue.poll();
	}
	/**
	 * ��ָ�����ӷŻ����ӳ�
	 * @param conn Ҫ�Ż����ӳص�����
	 */
	public void freeConntion( Connection conn )
	{
		try {
			
			if ( ( conn != null ) && ( queue.contains( conn ) == false ) )
			{
				queue.put( conn );
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ��ʼ�����ӳأ�����ָ����Ŀ������
	 */
	private void initPool()
	{
		Connection tmp = null;
		if ( ( minConnSize <=0 ) || ( maxConnSize <= 0 )
				|| ( maxConnSize < minConnSize ) )
		{
			return;
		}
		
		for ( int i=0; i< minConnSize; i++ )
		{
			tmp = buildConnection();
			
			assert( tmp != null );

			try {
				queue.put( tmp );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * ����һ��ָ�������ݿ�����
	 * @return �ɹ����ؽ��������ӣ����򷵻�null
	 */
	private Connection buildConnection()
	{
		Connection tmp = null;
		try {
			Class.forName( driverName );
			tmp = DriverManager.getConnection( dbUrl, userName, password );
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if ( tmp != null )
		{
			connectionCount++;
		}
		return tmp;
	}

	private void destoryConnection( Connection conn )
	{
		
	}

	private void close()
	{
		
	}
//	public static void main(String[] args) {
//
//		final DBConnectionPool pool = new DBConnectionPool();
//		final Connection[] conn = new Connection[50];
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				for (int i = 0; i < 15; i++) {
//					conn[i] = pool.getConnection();
//
//					System.out.println("conn" + i + " = " + conn);
//				}
//			}
//		}).start();
//
//		new Thread(new Runnable() {
//
//			Connection[] tmp = new Connection[30];
//			@Override
//			public void run() {
//				for (int i = 0; i < 30; i++) {
//					tmp[i] = pool.getConnection();
//
//					System.out.println("tmp" + i + " = " + conn);
//				}
//			}
//		}).start();
//		
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			// TODO �Զ����ɵ� catch ��
//			e.printStackTrace();
//		}
//
//		System.out.println("-----------------------------------------------");
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				for (int i = 0; i < 10; i++) {
//
//					int index = (int) Math.random() * 10;
//
//					pool.freeConntion(conn[index]);
//
//					System.out.println(" free conn" + i + " = " + conn);
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						// TODO �Զ����ɵ� catch ��
//						e.printStackTrace();
//					}
//				}
//			}
//		}).start();
//	}


}