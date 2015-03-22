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
			
			setTitle( "密码修改" );
			Image image = this.getToolkit().getImage(getClass().getResource("/anyviewj/net/client/resource/AV1.gif" ) );
			this.setIconImage(image);
//			addWindowListener( new LogInFrameWindowListener() );
			setBounds( getLocation().x, getLocation().y, 340, 244 );
//			置中
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
		 * 初始化学号输入框。
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
		 * 初始化旧密码输入框。
		 * 没有限定输入密码的长度
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
		 * 初始化新密码输入框
		 */
		private void initNewPasswordField()
		{
			newPasswordField.setBounds( 106, 92, 143, 20 );
			newPasswordField.setBorder( BorderFactory.createLoweredBevelBorder() );
			newPasswordField.setEditable( true );
			newPasswordField.setEnabled( true );
		}
		/**
		 * 初始化第二个新密码输入框
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
			changeButton.setText( "修改" );
			
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
		 * 初始化添加到frame上的组件
		 */
		private void initContentPane()
		{
			Container container = getContentPane();
			
			JLabel studentIDLabel = 
					new JLabel( "学号：", SwingConstants.RIGHT );
			JLabel oldPasswordLabel = 
					new JLabel( "旧密码：", SwingConstants.RIGHT );
			JLabel newPasswordLabel = 
					new JLabel( "新密码：", SwingConstants.RIGHT );
			JLabel newPasswordLabel2 = 
					new JLabel( "密码确认：", SwingConstants.RIGHT );

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
						showMessage( "修改密码成功", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE );
						ChangePswDialog.this.dispose();
//							更新保存在客户端的密码，方便再次改密码或者重新登录时使用
						ClientTerminal.getInstance().
						setPassword( new String( newPasswordField.getPassword() ) );
					}
					else
					{
						showMessage( "修改密码失败", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE );
					}
				}
			}
			private boolean verifyMessage()
			{
				String errorMessage = "";
				if ( ( studentIDArea.getText().length() == 0 ) ) 
//				密码可以是空，但学号一定不能是空
				{
					errorMessage = "请输入学号";
				}
				if ( ClientTerminal.getInstance().getPassword()
						.equals( new String( oldPasswordField.getPassword() ) ) == false )
				{
					errorMessage = "密码错误";
				}
				if ( ( newPasswordField.getPassword().length
						- new String( newPasswordField.getPassword() ).trim().length()  ) > 0 )
//				新密码中包含非法字符
				{
					errorMessage = "密码不能包含特殊字符";
				}
				
				if ( new String( newPasswordField2.getPassword() )
						.equals( new String( newPasswordField.getPassword() ) ) == false )  
//				输入的两组新密码不相同
				{
					errorMessage = "密码确认不一致";
					
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
			(ChangePswDialog.this, obj, "密码修改", optionType, messageType );
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
//				// TODO 自动生成的方法存根
//				
//			}
//
//			@Override
//			public void windowDeiconified(WindowEvent e) {
//				// TODO 自动生成的方法存根
//				
//			}
//
//			@Override
//			public void windowActivated(WindowEvent e) {
//				// TODO 自动生成的方法存根
//				
//			}
//
//			@Override
//			public void windowDeactivated(WindowEvent e) {
//				// TODO 自动生成的方法存根
//				
//			}
//			
//		}

	}
}
