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

	private String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // ����JDBC����
	private String dbUrl = "jdbc:sqlserver://localhost:1433; DatabaseName=anyviewj"; // ���ӷ����������ݿ�test
	private String userName = "sa"; // Ĭ���û���
	private String password = "sa"; // ����
	private int minConnSize = 5;
	private int maxConnSize = 20;
	/** ���Ա������ӳ���ȫ�������ӣ�ֵ���Ա����������Ƿ�����ռ�ã�false��ʾû��ռ��*/
	private final HashMap< Connection, Boolean > conns = 
			new HashMap<Connection, Boolean>( );
	/**
	 * 
	 * @param dbUrl        ���ݿ��λ��
	 * @param driverName       ��������
	 * @param id           �˺�
	 * @param password     �˺�����
	 * @param minConnSize   ��������С������
	 * @param maxConnSize  ���������ӵ������
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
		// TODO �Զ����ɵĹ��캯�����
	}
	/**
	 * �����ӳ��л�ȡһ�����ݿ������
	 * @return �ɹ��������ӣ�����Ż�null
	 */
	public Connection getConnection()
	{
		Connection idleConnection = null;
		Set<Entry<Connection, Boolean>> set = this.conns.entrySet();
		Entry<Connection, Boolean> entryTmp = null;
		
		for ( Iterator<Entry<Connection, Boolean>> it = set.iterator(); it.hasNext(); )
//		���������ҵ�һ��û�б�ռ�õ��������ǲ������������
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
//		���ӳ��е�����ȫ����ռ�ã�����û�дﵽ���������ޣ����½�һ��
		{
			idleConnection = buildConnection();
			this.conns.put( idleConnection, true );
		}
		return idleConnection;
	}
	/**
	 * �ҵ�Ҫ���·Żص����Ӳ������Ϊ����״̬
	 * @param conn Ҫ�Ż����ӳص�����
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
			
			if ( tmp != null )
			{
				conns.put( tmp, false );
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
//			conns.put( tmp,  false );
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return tmp;
	}









}