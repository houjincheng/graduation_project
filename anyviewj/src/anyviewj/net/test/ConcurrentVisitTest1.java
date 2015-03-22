package anyviewj.net.test;

import anyviewj.net.client.LoginRequest;
import anyviewj.net.common.CommunicationProtocol;

public class ConcurrentVisitTest1 implements Runnable{

	int index = -1;
	
	public static void main(String[] args) {
		
		for ( int i=0; i<10; i++ )
		{
			new Thread( new ConcurrentVisitTest1( i ) ).start();
		}
	}
	
	public ConcurrentVisitTest1( int index ) {
		
		this.index = index;
	}
	@Override
	public void run() {
		
		String studentID = "¼Æ1101";
		String password = "123";
		

		int result = CommunicationProtocol.RESOLVE_FAILE;
		LoginRequest lg = new LoginRequest( studentID, password );
		
		result = lg.resolve();
		
		System.out.println( index + "--result = " + result );
		
	}

}
