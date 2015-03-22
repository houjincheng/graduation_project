package anyviewj.net.server;

import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

public class LoginedClientMap{

	private final HashMap<String, Long > clients = new HashMap<String, Long>( );
	
	/**
	 * 因为在从实际情况来看，客户端可能异常关闭，这回导致客户端不能及时通知服务器端清除其登陆信息，
	 * 不及时清理的话，长驻在内存中，以服务器端的连续运行时间而定
	 */
	public LoginedClientMap()
	{
		Timer timer = new Timer();
		//看到这里，你应该知道我国庆节晚上还在敲代码，不过接下来我可以出游7天啊，你继续撸吧
		Date date = new Date( 2013, 10, 1 ); 
		
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				
				for ( Entry< String, Long> e : clients.entrySet() )
				{
					synchronized ( e ) {
						
						if ( ( e.getValue() )< ( System.currentTimeMillis() - 3600000 * 24 ) )
//						该客户端的最后一次请求是在24小时以前，视为废弃连接予以清除
						{
							clients.remove( e.getKey() );
						}
					}
				}
			}
		};
//		每隔24小时，清除无效的客户端信息，最好是选择凌晨时分
		timer.schedule( task, date, 3600000 * 24 );
	}
	/**
	 * 这个方法应该在一个新的用户成功登陆后被调用，以记录这个用户已经成功登陆，方便验证下一次来自客户端的请求。
	 * 
	 * @param client 可以唯一标示一个客户端的字符串。例如：用户名
	 * @return  成功添加返回true，否则返回false
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
	 * 返回指定客户端的时间戳
	 * @param client
	 * @return
	 */
	public Long getToken( String client ) 
	{
		return this.clients.get( client );
	}
	/**
	 * 检查这是不是一个成功登陆的客户端，若是更新其在注册表中的时间戳，然后返回true，否则返回false
	 * @param client 唯一标示一个客户端的信息
	 * @return       若记录中有该信息则表示该客户端已经成功登陆，返回true，否则返回falses
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
