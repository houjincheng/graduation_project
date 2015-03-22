package anyviewj.net.client.actions;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import anyviewj.net.client.Bundle;
import anyviewj.net.client.ChangePasswordRequest;
import anyviewj.net.client.ClientTerminal;

public class ChangePasswordAction extends AbstractAction{

//	private JFrame mainFrame = null;
	@Override
	public void actionPerformed(ActionEvent e) {

		new ChangePswDialog();
	}

	private class ChangePswDialog extends JDialog
	{


		private JTextArea studentIDArea = new JTextArea();
		private JPasswordField oldPasswordField = new JPasswordField();
		private JPasswordField newPasswordField = new JPasswordField();
		private JPasswordField newPasswordField2 = new JPasswordField();
		
		private JButton changeButton = new JButton();
		private JButton cancelButton = new JButton();
		
		
		public ChangePswDialog() {
			
			super( null, ModalityType.APPLICATION_MODAL );
			
			setTitle( "�����޸�" );
			Image image = this.getToolkit().getImage(getClass().getResource("/anyviewj/net/client/resource/AV1.gif" ) );
			this.setIconImage(image);
//			addWindowListener( new LogInFrameWindowListener() );
			setBounds( getLocation().x, getLocation().y, 340, 244 );
//			����
			setLocationRelativeTo( null );
			getRootPane().setDefaultButton( changeButton );
			initContentPane();
			
			try {
//	          System.out.println(UIManager.getSystemLookAndFeelClassName());
	          UIManager.setLookAndFeel(UIManager.
	                                   getSystemLookAndFeelClassName());
	          //com.sun.java.swing.plaf.windows.WindowsLookAndFeel
	          SwingUtilities.updateComponentTreeUI( this );
	      } catch (Exception exception) {
	          exception.printStackTrace();
	      }
			setResizable( false );
			setVisible( true );
			
			oldPasswordField.requestFocusInWindow(); 
		}
		/**
		 * ��ʼ��ѧ�������
		 */
		private void initStudentIDArea()
		{

			studentIDArea.setBounds( 106, 12, 143, 20 );
			studentIDArea.setBorder( BorderFactory.createLoweredBevelBorder() );
			studentIDArea.setText( ClientTerminal.getInstance().getStudentID() );
			
			studentIDArea.setEditable( false );
			studentIDArea.setEnabled( false );
		}
		/**
		 * ��ʼ�������������
		 * û���޶���������ĳ���
		 */
		private void initOldPasswordField()
		{
			oldPasswordField.setBounds( 106, 52, 143, 20 );
			oldPasswordField.setBorder( BorderFactory.createLoweredBevelBorder() );
			
//			oldPasswordField.setColumns( 15 );

			oldPasswordField.setEditable( true );
			oldPasswordField.setEnabled( true );
		}
		/**
		 * ��ʼ�������������
		 */
		private void initNewPasswordField()
		{
			newPasswordField.setBounds( 106, 92, 143, 20 );
			newPasswordField.setBorder( BorderFactory.createLoweredBevelBorder() );
			newPasswordField.setEditable( true );
			newPasswordField.setEnabled( true );
		}
		/**
		 * ��ʼ���ڶ��������������
		 */
		private void initNewPasswordField2()
		{
			newPasswordField2.setBounds( 106, 132, 143, 20 );
			newPasswordField2.setBorder( BorderFactory.createLoweredBevelBorder() );
			newPasswordField.setEditable( true );
			newPasswordField.setEnabled( true );
		}
		
		private void initChangePswButton()
		{
			ImageIcon icon = new ImageIcon( "src/anyviewj/net/client/resource/login.gif" );
			changeButton.setIcon( icon );
			changeButton.setText( "�޸�" );
			
			changeButton.setBorder( BorderFactory.createRaisedBevelBorder() );
			changeButton.setBounds( 56, 175, 78, 25 );
			changeButton.setDefaultCapable( true );
			changeButton.addActionListener(  new changePswButtonActionListener() );
		}
		private void initCancelButton()
		{
			ImageIcon icon = new ImageIcon( "src/anyviewj/net/client/resource/cancel.gif" );
			cancelButton.setIcon( icon );
			cancelButton.setText( Bundle.getString( "cancelButtonText" ) );
			
			cancelButton.setBounds( 204, 175, 78, 25 );
			cancelButton.setBorder( BorderFactory.createRaisedBevelBorder() );
			
			cancelButton.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ChangePswDialog.this.dispose();
				}
			} );
		}
		
		/**
		 * ��ʼ����ӵ�frame�ϵ����
		 */
		private void initContentPane()
		{
			Container container = getContentPane();
			
			JLabel studentIDLabel = 
					new JLabel( "ѧ�ţ�", SwingConstants.RIGHT );
			JLabel oldPasswordLabel = 
					new JLabel( "�����룺", SwingConstants.RIGHT );
			JLabel newPasswordLabel = 
					new JLabel( "�����룺", SwingConstants.RIGHT );
			JLabel newPasswordLabel2 = 
					new JLabel( "����ȷ�ϣ�", SwingConstants.RIGHT );

			studentIDLabel.setBounds( 1, 12, 105, 20 );
			oldPasswordLabel.setBounds( 1, 52, 105, 20 );
			newPasswordLabel.setBounds( 1, 92, 105, 20 );
			newPasswordLabel2.setBounds( 1, 132, 105, 20 );
			
			initStudentIDArea();
			initOldPasswordField();
			initNewPasswordField();
			initNewPasswordField2();
			
			initChangePswButton();
			initCancelButton();
			
			setLayout( null );

			container.add( studentIDLabel );
			container.add( studentIDArea );
			
			container.add( oldPasswordLabel );
			container.add( oldPasswordField );
			
			container.add( newPasswordLabel );
			container.add( newPasswordField );
			
			container.add( newPasswordLabel2 );
			container.add( newPasswordField2 );
			
			container.add( changeButton );
			container.add( cancelButton );

		}


		private class changePswButtonActionListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if ( verifyMessage() )
				{
					ChangePasswordRequest request = new ChangePasswordRequest
					(studentIDArea.getText(), 
							new String( oldPasswordField.getPassword() ),
							new String( newPasswordField.getPassword() ) );
					if ( request.resolve() == ChangePasswordRequest.CHANGE_PASSWORD_SUCCESSED )
					{
						showMessage( "�޸�����ɹ�", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE );
						ChangePswDialog.this.dispose();
//							���±����ڿͻ��˵����룬�����ٴθ�����������µ�¼ʱʹ��
						ClientTerminal.getInstance().
						setPassword( new String( newPasswordField.getPassword() ) );
					}
					else
					{
						showMessage( "�޸�����ʧ��", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE );
					}
				}
			}
			private boolean verifyMessage()
			{
				String errorMessage = "";
				if ( ( studentIDArea.getText().length() == 0 ) ) 
//				��������ǿգ���ѧ��һ�������ǿ�
				{
					errorMessage = "������ѧ��";
				}
				if ( ClientTerminal.getInstance().getPassword()
						.equals( new String( oldPasswordField.getPassword() ) ) == false )
				{
					errorMessage = "�������";
				}
				if ( ( newPasswordField.getPassword().length
						- new String( newPasswordField.getPassword() ).trim().length()  ) > 0 )
//				�������а����Ƿ��ַ�
				{
					errorMessage = "���벻�ܰ��������ַ�";
				}
				
				if ( new String( newPasswordField2.getPassword() )
						.equals( new String( newPasswordField.getPassword() ) ) == false )  
//				��������������벻��ͬ
				{
					errorMessage = "����ȷ�ϲ�һ��";
					
				}
				if ( errorMessage == "" )
				{
					return true;
				}
				else
				{
					showMessage( errorMessage,
							JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE );
					return false;
				}
			
			}
		}


		private int showMessage( Object obj, int optionType, int messageType  )
		{
			return JOptionPane.showConfirmDialog
			(ChangePswDialog.this, obj, "�����޸�", optionType, messageType );
		}
		
//		private class LogInFrameWindowListener extends WindowAdapter
//		{
//
//			@Override
//			public void windowOpened(WindowEvent e) {
//				
//				LoginFrame.this.getRootPane().setDefaultButton( changeButton );
//
//			}
//
//			@Override
//			public void windowClosing(WindowEvent e) {
//
//				setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
//				System.out.println( "closing" );
//			}
//
//			@Override
//			public void windowClosed(WindowEvent e) {
//				System.exit( 0 );
//			}
//
//			@Override
//			public void windowIconified(WindowEvent e) {
//				// TODO �Զ����ɵķ������
//				
//			}
//
//			@Override
//			public void windowDeiconified(WindowEvent e) {
//				// TODO �Զ����ɵķ������
//				
//			}
//
//			@Override
//			public void windowActivated(WindowEvent e) {
//				// TODO �Զ����ɵķ������
//				
//			}
//
//			@Override
//			public void windowDeactivated(WindowEvent e) {
//				// TODO �Զ����ɵķ������
//				
//			}
//			
//		}

	}
}
