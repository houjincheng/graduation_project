package anyviewj.net.server;

import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

public class LoginedClientMap{

	private final HashMap<String, Long > clients = new HashMap<String, Long>( );
	
	/**
	 * ��Ϊ�ڴ�ʵ������������ͻ��˿����쳣�رգ���ص��¿ͻ��˲��ܼ�ʱ֪ͨ��������������½��Ϣ��
	 * ����ʱ����Ļ�����פ���ڴ��У��Է������˵���������ʱ�����
	 */
	public LoginedClientMap()
	{
		Timer timer = new Timer();
		//���������Ӧ��֪���ҹ�������ϻ����ô��룬�����������ҿ��Գ���7�찡�������ߣ��
		Date date = new Date( 2013, 10, 1 ); 
		
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				
				for ( Entry< String, Long> e : clients.entrySet() )
				{
					synchronized ( e ) {
						
						if ( ( e.getValue() )< ( System.currentTimeMillis() - 3600000 * 24 ) )
//						�ÿͻ��˵����һ����������24Сʱ��ǰ����Ϊ���������������
						{
							clients.remove( e.getKey() );
						}
					}
				}
			}
		};
//		ÿ��24Сʱ�������Ч�Ŀͻ�����Ϣ�������ѡ���賿ʱ��
		timer.schedule( task, date, 3600000 * 24 );
	}
	/**
	 * �������Ӧ����һ���µ��û��ɹ���½�󱻵��ã��Լ�¼����û��Ѿ��ɹ���½��������֤��һ�����Կͻ��˵�����
	 * 
	 * @param client ����Ψһ��ʾһ���ͻ��˵��ַ��������磺�û���
	 * @return  �ɹ���ӷ���true�����򷵻�false
	 */
	public synchronized boolean add( String client )
	{
		if ( client != null )
		{
			long currentTime = new Date().getTime();
			this.clients.put( client, currentTime );
			
			System.out.println( "currentTime = " + currentTime );
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * ����ָ���ͻ��˵�ʱ���
	 * @param client
	 * @return
	 */
	public Long getToken( String client ) 
	{
		return this.clients.get( client );
	}
	/**
	 * ������ǲ���һ���ɹ���½�Ŀͻ��ˣ����Ǹ�������ע����е�ʱ�����Ȼ�󷵻�true�����򷵻�false
	 * @param client Ψһ��ʾһ���ͻ��˵���Ϣ
	 * @return       ����¼���и���Ϣ���ʾ�ÿͻ����Ѿ��ɹ���½������true�����򷵻�falses
	 */
	public boolean contain( String client )
	{
		if ( client == null )
		{
			return false;
		}
		
		if ( clients.containsKey( client ) )
		{
//			long currentTime = new Date().getTime();
//			this.clients.put( client, currentTime );
//			
//			System.out.println( "currentTime = " + currentTime );
			return true;
		}
		return false;
	}
	
}
