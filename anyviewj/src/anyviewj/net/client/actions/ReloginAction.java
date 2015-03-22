package anyviewj.net.client.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import anyviewj.net.client.ClientTerminal;
import anyviewj.net.client.LoginRequest;
import anyviewj.net.common.CommunicationProtocol;
/**
 * ���µ�¼���ܵ�ʵ��
 * 
 * ���µ�¼���ܵ����ò�����
 * ��Ϊ�ͻ�����������˵�������һ�ֶ����Ӷ�����
 * �û��ɹ���½�Ժ����˺š����롢��¼ָ�ƶ��ᱻ�����ڵ�����ClientTerminal�У�
 * ���û��ڵ�¼�Ժ������˷�������ʱ���ͻ��˻��Զ�����Щ��Ϣ��ӵ�������Ϣ����Ϣͷ���Ա�˳��ͨ������˵���֤��
 * ��Ϊ���ǳ����ӣ����Բ��������ӶϿ������
 * �������������ǣ�ͬһ�˺���ÿһ�ε�¼ʱ�����������Է���˵�һ����½ָ�ơ�
 * ����ͬһ�˺�ͨ��1�����ϵĿͻ��˳��򷢳�����ʱ��ֻ�г�����ȷ��¼ָ�ƵĿͻ��˷���������ᱻ����
 * ���ԣ���Ҫ���µ�¼��ȡһ���µ�ָ�ƣ��Ա㷢��������Ӧ�ģ�����˺ŵ������ͻ��˵�ָ�ƽ����ڡ�
 * 
 * 
 * ���µ�¼���ܵ�ʵ�ַ�����
 * ���µ�¼���󴴽�һ����׼XML�ļ�������ļ��Զ���װ������ClientTerminal���˺ź����룬��ʵ�ϻ��й��ڵĵ�¼ָ�ƣ�
 * Ȼ�����ļ���ָ�������������LoginRequestResolver��
 * ��Ϊ�˺ź����붼����ȷ�ģ����������û�������ͻ��ˣ������Ե�¼������Բ���Ҫָ�ƶ�ͨ����֤��
 * ������ڽ��������Ĺ����У������һ�����˺�Ϊkey����ָ��Ϊvalue�ļ�¼�������¼�����Ǿ͵ü�¼��
 * ��������µ�ָ�Ʒ��ء�
 * ������ڽ��յ�����µ�ָ�ƺ�Ӧ�ü�ʱ���¿ͻ��˵ĵ�¼ָ�ơ�
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
		String message = "��¼�ɹ�";
		
		( ( AbstractButton )e.getSource() ).setEnabled( false );
		if ( loginRequest.resolve() != CommunicationProtocol.RESOLVE_SUCCESSED )
		{
			message = "��¼ʧ��";
		}
		
		JOptionPane.showConfirmDialog
		( null, message, "���µ�¼", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE );
		( ( AbstractButton )e.getSource() ).setEnabled( true );
	}
	
}