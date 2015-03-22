package anyviewj.net.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 
 * @author hou
 *
 */
@Deprecated
public class DBConnectionPoolOld{

	private String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
	private String dbUrl = "jdbc:sqlserver://localhost:1433; DatabaseName=anyviewj"; // 连接服务器和数据库test
	private String userName = "sa"; // 默认用户名
	private String password = "sa"; // 密码
	private int minConnSize = 5;
	private int maxConnSize = 20;
	/** 用以保存连接池内全部的连接，值用以标记这个连接是否正被占用，false表示没有占用*/
	private final HashMap< Connection, Boolean > conns = 
			new HashMap<Connection, Boolean>( );
	/**
	 * 
	 * @param dbUrl        数据库的位置
	 * @param driverName       连接驱动
	 * @param id           账号
	 * @param password     账号密码
	 * @param minConnSize   建立的最小连接数
	 * @param maxConnSize  建立的连接的最大数
	 */
	public DBConnectionPoolOld
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
	public DBConnectionPoolOld() {
		// TODO 自动生成的构造函数存根
	}
	/**
	 * 从连接池中获取一个数据库的连接
	 * @return 成功返回连接，否则放回null
	 */
	public Connection getConnection()
	{
		Connection idleConnection = null;
		Set<Entry<Connection, Boolean>> set = this.conns.entrySet();
		Entry<Connection, Boolean> entryTmp = null;
		
		for ( Iterator<Entry<Connection, Boolean>> it = set.iterator(); it.hasNext(); )
//		遍历，若找到一个没有被占用的连接则标记并返回这个连接
		{
			entryTmp = it.next();
			synchronized ( entryTmp ) 
			{
				if ( entryTmp.getValue() )
				{
					continue;
				}
				entryTmp.setValue( true );
				return entryTmp.getKey();	
			}
		}
		if ( conns.size() < maxConnSize )
//		连接池中的连接全部被占用，若还没有达到连接数上限，则新建一个
		{
			idleConnection = buildConnection();
			this.conns.put( idleConnection, true );
		}
		return idleConnection;
	}
	/**
	 * 找到要重新放回的连接并标记其为空闲状态
	 * @param conn 要放回连接池的连接
	 */
	public void freeConntion( Connection conn )
	{
		for ( Entry<Connection, Boolean> tmp : conns.entrySet() )
		{
			if ( tmp.getKey().equals( conn ) )
			{
				synchronized( tmp )
				{
					tmp.setValue( false );
				}
			}
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
			
			if ( tmp != null )
			{
				conns.put( tmp, false );
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
//			conns.put( tmp,  false );
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return tmp;
	}









}