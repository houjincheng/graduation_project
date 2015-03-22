package anyviewj.net.client.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import anyviewj.net.client.ClientTerminal;
import anyviewj.net.client.LoginRequest;
import anyviewj.net.common.CommunicationProtocol;
/**
 * 重新登录功能的实现
 * 
 * 重新登录功能的作用并不大：
 * 因为客户端与服务器端的连接是一种短连接短连接
 * 用户成功登陆以后，其账号、密码、登录指纹都会被保存在单例的ClientTerminal中，
 * 当用户在登录以后，向服务端发出请求时，客户端会自动将这些信息添加到发送信息的信息头，以便顺利通过服务端的验证。
 * 因为不是长连接，所以不存在连接断开情况。
 * 其真正的作用是：同一账号在每一次登录时，都会获得来自服务端的一个登陆指纹。
 * 假如同一账号通过1个以上的客户端程序发出请求时，只有持有正确登录指纹的客户端发出的请求会被处理。
 * 所以，需要重新登录获取一个新的指纹，以便发出请求，相应的，这个账号的其他客户端的指纹将过期。
 * 
 * 
 * 重新登录功能的实现方案：
 * 重新登录请求创建一个标准XML文件，这个文件自动包装了来自ClientTerminal的账号和密码，事实上还有过期的登录指纹，
 * 然后在文件中指定请求解析器问LoginRequestResolver。
 * 因为账号和密码都是正确的（如果其密码没有其他客户端），所以登录请求可以不需要指纹而通过验证。
 * 服务端在解析这个请的过程中，会添加一个以账号为key，新指纹为value的记录，这个记录将覆盖就得记录，
 * 并将这个新的指纹发回。
 * 服务端在接收到这个新的指纹后，应该及时更新客户端的登录指纹。
 * @author hou
 *
 */
public class ReloginAction extends AbstractAction{

	private static final long serialVersionUID = 851118409721345385L;

	@Override
	public void actionPerformed(ActionEvent e) {
		
		ClientTerminal ct = ClientTerminal.getInstance();
		String studentID = ct.getStudentID();
		String password = ct.getPassword();
		
		
		LoginRequest loginRequest = new LoginRequest( studentID, password );
		String message = "登录成功";
		
		( ( AbstractButton )e.getSource() ).setEnabled( false );
		if ( loginRequest.resolve() != CommunicationProtocol.RESOLVE_SUCCESSED )
		{
			message = "登录失败";
		}
		
		JOptionPane.showConfirmDialog
		( null, message, "重新登录", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE );
		( ( AbstractButton )e.getSource() ).setEnabled( true );
	}
	
}