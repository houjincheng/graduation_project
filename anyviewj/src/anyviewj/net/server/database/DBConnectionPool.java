package anyviewj.net.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import anyviewj.net.server.ServerTerminal;
/**
 * 数据库连接池。
 * 新建数据库连接池时，若通过不带参数的构造器，则新建的连接池默认是连接到到anyviewj数据库,
 * 否则，将根据输入参数连接到指定数据库。
 * 
 * 通过 getConnectionX()方法，从连接池中取得数据库的连接
 * 拖过freeConnection( Connection ),将指定连接放回连接池
 * 
 * @author hou
 *
 */
public class DBConnectionPool{

	private String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
	private String dbUrl = "jdbc:sqlserver://localhost:1433; DatabaseName=anyviewj"; // 连接服务器和数据库test
	private String userName = "sa"; // 默认用户名
	private String password = "123"; // 密码
	private int minConnSize = 5;
	/** 数据库连接数量直接与线城池线程数量相关连 */
	private int maxConnSize = ServerTerminal.THREADPOOL_SIZE;
	/** 存放没有空闲连接 */
	private final ArrayBlockingQueue< Connection > queue 
	= new ArrayBlockingQueue<Connection>( maxConnSize );
	/** 新建的数据库连接，包括被引用和空闲的 */
	private int connectionCount = 0;
	/**
	 * 建立到指定数据库的连接
	 * @param dbUrl        数据库的位置
	 * @param driverName       连接驱动
	 * @param id           账号
	 * @param password     账号密码
	 * @param minConnSize   建立的最小连接数
	 * @param maxConnSize  建立的连接的最大数
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
	 * 建立到默认数据库的连接
	 */
	public DBConnectionPool() 
	{
		initPool();
	}
	/**
	 * 从连接池中获取一个数据库的连接
	 * @return 成功返回连接，超过一分钟返回null
	 */
	public Connection getConnection()
	{
		return getConnection( 1000 * 60 );
	}
	/**
	 * 在指定时间内获取一个数据库连接，否则返回null
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
	 * 尝试立即获得一个数据库连接
	 * 若池中还有空闲的连接，则直接返回一个取出的连接，
	 * 如果没有空余的连接，则尝试新建一批新的连接在从中取得一个连接返回
	 * @return 可以为null
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
	 * 将指定连接放回连接池
	 * @param conn 要放回连接池的连接
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
	 * 初始化连接池，建立指定数目的连接
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
	 * 建立一个指定的数据库连接
	 * @return 成功返回建立的连接，否则返回null
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
//			// TODO 自动生成的 catch 块
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
//						// TODO 自动生成的 catch 块
//						e.printStackTrace();
//					}
//				}
//			}
//		}).start();
//	}


}