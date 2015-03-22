package anyviewj.net.client;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import anyviewj.AnyviewJApp;
import anyviewj.net.common.CommunicationProtocol;
/**
 * ���ڷ��ʵĶ˿ڣ��Ѿ���д��Э�����ˣ������Ҫ�޸� 2013��9��15��11:11:32
 * @author hou
 *
 */
public class LoginFrame extends JFrame{

	private JComboBox studentIDBox = new JComboBox();
	private JPasswordField passwordField = new JPasswordField();
	private JComboBox iPBox = new JComboBox();
	private JComboBox portBox = new JComboBox();
	
	private JButton loginButton = new JButton();
	private JButton cancelButton = new JButton();
	
	
	public LoginFrame() {
		
		super( Bundle.getString( "frameTitle" ) );
		Image image = this.getToolkit().getImage(getClass().getResource("resource/AV1.gif" ) );
		this.setIconImage(image);
		addWindowListener( new LogInFrameWindowListener() );
		setBounds( getLocation().x, getLocation().y, 340, 244 );
//		����
		setLocationRelativeTo( null );
		
		initContentPane();
		
		try {
//          System.out.println(UIManager.getSystemLookAndFeelClassName());
          UIManager.setLookAndFeel(UIManager.
                                   getSystemLookAndFeelClassName());
          //com.sun.java.swing.plaf.windows.WindowsLookAndFeel
          SwingUtilities.updateComponentTreeUI( this );
      } catch (Exception exception) {
          exception.printStackTrace();
      }
		setResizable( false );

		setVisible( true );
		
		passwordField.requestFocusInWindow();
	}
	/**
	 * ��ʼ��ѧ�������
	 * �������ļ�����༶��Ϣ��Ϊ�����б���ѡ��Ĭ�ϵİ༶����
	 */
	private void initStudentIDBox()
	{
		String studentIDItems = Bundle.getString( "studentIDItems" );
		String defaultStudentID = 
				Bundle.getString( "defaultStudentID" );
		
		for ( String str : studentIDItems.split( "\\|" ) )
		{
			studentIDBox.addItem( str );
			if ( str.compareTo( defaultStudentID ) == 0 )
			{
				defaultStudentID = str;
			}
		}

		studentIDBox.setBounds( 106, 12, 143, 20 );
		studentIDBox.setBorder( BorderFactory.createLoweredBevelBorder() );
		
		studentIDBox.setSelectedItem( defaultStudentID );
		studentIDBox.setEditable( true );
		studentIDBox.setEnabled( true );
	}
	/**
	 * ��ʼ�����������
	 * û���޶���������ĳ���
	 */
	private void initPasswordField()
	{
		passwordField.setBounds( 106, 52, 143, 20 );
		passwordField.setBorder( BorderFactory.createLoweredBevelBorder() );
		
//		passwordField.setColumns( 15 );
		passwordField.setEditable( true );
		passwordField.setEnabled( true );
	}
	/**
	 * ��ʼ��IP��ַ�����б�
	 */
	private void initIPBox()
	{
		String defaultIP = String.valueOf( CommunicationProtocol.SERVER_TERMINAL_IP );
		String iPItems = Bundle.getString( "IPItems" );
		boolean flag = false;
		
		for ( String str : iPItems.split( "\\|" ) )
		{
			iPBox.addItem( str );
			
			if ( str.equals( defaultIP ) )
			{
				flag = true;
			}
		}
		if ( flag )
		{
			String tmp = "Ĭ��";
			iPBox.addItem( tmp );
			iPBox.setSelectedItem( tmp );
			iPBox.setEditable( false );
			iPBox.setEnabled( false );
		}
		else
		{
			iPBox.setEditable( true );
			iPBox.setEnabled( true );
		}
		iPBox.setBounds( 106, 92, 143, 20 );
		iPBox.setBorder( BorderFactory.createLoweredBevelBorder() );
	}
	/**
	 * ��ʼ���˿������б�
	 */
	private void initPortBox()
	{
		String defaultPort = String.valueOf( CommunicationProtocol.SERVER_TERMINAL_ACCEPTEDPORT );
		String portItems = Bundle.getString( "portItems" );
		boolean flag = false;
		
		for ( String str : portItems.split( "\\|" ) )
		{
			portBox.addItem( str );
			
			if ( str.equals( defaultPort ) )
			{
				flag = true;
			}
		}
		if ( flag )
		{
			String tmp = "Ĭ��";
			portBox.addItem( tmp );
			portBox.setSelectedItem( tmp );
			portBox.setEditable( false );
			portBox.setEnabled( false );
		}
		else
		{
			portBox.setEditable( true );
			portBox.setEnabled( true );
		}
		portBox.setBounds( 106, 132, 143, 20 );
		portBox.setBorder( BorderFactory.createLoweredBevelBorder() );
	}
	
	private void initLoginButton()
	{
		ImageIcon icon = new ImageIcon( "src/anyviewj/net/client/resource/login.gif" );
		loginButton.setIcon( icon );
		loginButton.setText( Bundle.getString( "loginButtonText" ) );
		
		loginButton.setBorder( BorderFactory.createRaisedBevelBorder() );
		loginButton.setBounds( 56, 175, 78, 25 );
		loginButton.setDefaultCapable( true );
		loginButton.addActionListener(  new LoginButtonActionListener() );
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
				System.exit( 0 );
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
				new JLabel( Bundle.getString( "studentIDText" ), SwingConstants.RIGHT );
		JLabel passwordLabel = 
				new JLabel( Bundle.getString( "passwordText" ), SwingConstants.RIGHT );
		JLabel iPLabel = 
				new JLabel( Bundle.getString( "IPText" ), SwingConstants.RIGHT );
		JLabel portLabel = 
				new JLabel( Bundle.getString( "portText" ), SwingConstants.RIGHT );

		studentIDLabel.setBounds( 1, 12, 105, 20 );
		passwordLabel.setBounds( 1, 52, 105, 20 );
		iPLabel.setBounds( 1, 92, 105, 20 );
		portLabel.setBounds( 1, 132, 105, 20 );
		
		initStudentIDBox();
		initPasswordField();
		initIPBox();
		initPortBox();
		
		initLoginButton();
		initCancelButton();
		
		setLayout( null );

		container.add( studentIDLabel );
		container.add( studentIDBox );
		
		container.add( passwordLabel );
		container.add( passwordField );
		
		container.add( iPLabel );
		container.add( iPBox );
		
		container.add( portLabel );
		container.add( portBox );
		
		container.add( loginButton );
		container.add( cancelButton );

	}


	private class LoginButtonActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {

			String serverHost = (String) iPBox.getSelectedItem();
			String serverPort = ( String )portBox.getSelectedItem();
			String studentID = ( String )studentIDBox.getSelectedItem();
			String password = new String( passwordField.getPassword() );
			
			if ( serverHost == "Ĭ��" )
			{
				serverHost = String.valueOf( CommunicationProtocol.SERVER_TERMINAL_IP );
			}
			
			if ( serverPort == "Ĭ��")
			{
				serverPort = String.valueOf
						( CommunicationProtocol.SERVER_TERMINAL_ACCEPTEDPORT);
			}
			
			if ( studentID.length() == 0 )
//				��Ҫ����Ϊ�գ�������û������
			{
//				showMessage( Bundle.getString( "emptyStudentIDError" ),
//						JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE );
			}
			
			loginButton.setEnabled( false );
			

			int result = execute( studentID, password );
			if ( result == CommunicationProtocol.RESOLVE_SUCCESSED )
			{
				AnyviewJApp.main( null );
				LoginFrame.this.setVisible( false );
			}
			else
			{
				loginButton.setEnabled( true );
			}

		}
		private int execute( String studentID, String password) {
			
			int result = CommunicationProtocol.RESOLVE_FAILE;
			LoginRequest lg = new LoginRequest( studentID, password );
			
			result = lg.resolve();
			String errorMessage = "";
			switch ( result )
			{
			case CommunicationProtocol.CONNECT_ERROR :
				errorMessage = "���Ӳ���������";
				break;
			case CommunicationProtocol.STUDENTID_NOTFIND :
				errorMessage = "�˺Ŵ���";
				break;
			case CommunicationProtocol.PASSWORD_ERROR :
				errorMessage = "�������";
				break;
			default : return result;
			}
			showMessage( errorMessage, JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE );
			return result;
		}
		
	}
	

	public int showMessage( Object obj, int optionType, int messageType  )
	{
		return JOptionPane.showConfirmDialog
		( LoginFrame.this, obj, "AnyviewJ", optionType, messageType );
	}
	
	private class LogInFrameWindowListener extends WindowAdapter
	{

		@Override
		public void windowOpened(WindowEvent e) {
			
			LoginFrame.this.getRootPane().setDefaultButton( loginButton );

		}

		@Override
		public void windowClosing(WindowEvent e) {

			setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
			System.out.println( "closing" );
		}

		@Override
		public void windowClosed(WindowEvent e) {
			System.exit( 0 );
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO �Զ����ɵķ������
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO �Զ����ɵķ������
			
		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO �Զ����ɵķ������
			
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO �Զ����ɵķ������
			
		}
		
	}
}
