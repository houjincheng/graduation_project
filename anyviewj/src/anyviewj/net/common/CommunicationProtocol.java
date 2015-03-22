package anyviewj.net.common;


public interface CommunicationProtocol {

	public final static String SERVER_FILE_PATH= "d:/anyviewj/questionBank/";
	public final static String SERVER_UPLOAD_FILE_PATH = SERVER_FILE_PATH+"upload/";

	public final static String SERVER_TERMINAL_IP = Bundle.getString( "serverTerminalIP" );
	public final static int SERVER_TERMINAL_ACCEPTEDPORT = Integer.
			valueOf( Bundle.getString( "serverTerminalAcceptedPort" ) );
//	Request请求信息的XML标准格式
	public final static String REQUEST_DOCUMENT_REQUESTNODE = "request";
	public final static String REQUEST_DOCUMENT_REQUSETNODE_RESOLVER = "requestResolver";
	public final static String REQUEST_DOCUMENT_DATANODE = "data";
	public final static String REQYEST_DOCUMENT_STUDENTID = "studentID";
	public final static String REQYEST_DOCUMENT_LOGINTOKEN = "loginToken";
	public final static int BUILD_XMLFILE_FAIL = 0;
	
//	RequestResolver解析结果的XML标准格式
	public final static String REQUESTRESOLVER_RESULTNODE = "requestResolverResult";
	public final static String REQUESTRESOLVER_RESULTNODE_ATTRIBUTE = "result";
	public final static String REQUESTRESOLVER_RESULTNODE_DATACHILD = "data";
	
	public final static int RESOLVE_FAILE = 0;
	public final static int RESOLVE_SUCCESSED = 1;
	public final static int CONNECT_ERROR = -1;
	public final static int DATA_ERROR = -2;
	public final static int DATABASE_ERROR = -3;
	//	有关登录功能
	public final static String LOGINREQUEST_STUDENTID = "studentID";
	public final static String LOGINREQUEST_PASSWORD = "password";
	public final static String LOGINTOKEN_NODE = "loginToken";
	public final static String LOGINREQUEST_RESOLVER
	= "anyviewj.net.server.LoginRequestResolver";
	public final static int STUDENTID_NOTFIND = 11;
	public final static int PASSWORD_ERROR = 12;

//	修改密码
	public final static String CHANGEPASSWORDREQUEST_RESOLVER 
	= "anyviewj.net.server.ChangePasswordRequestResolver";
	public final static String CHANGEPASSWORDREQUEST_STUDENTIDNODE = "studentID";
	public final static String CHANGEPASSWORDREQUEST_OLDPASSWORDNODE = "oldPassword";
	public final static String CHANGEPASSWORDREQUEST_NEWPASSWORDNODE = "newPassword";
	
	public final static String TABLESERVERREQUEST_RESOLVER = "anyviewj.net.table.TableServerRequestResolver";
	public final static String TABLESERVERREQUEST_STUDENTIDNODE = "studentID";
	public final static String TABLESERVERREQUEST_TABLE = "refresh_table";
	
	public final static String EXECLREQUEST_TABLE = "refresh_table";
	
	public final static String QUESTION_REQUEST = "request_question";
	public final static String QUESTION_TYPE = "question_type";
	public final static String QUESTION_RAW = "raw";
	public final static String QUESTION_CHANGED = "changed";
	public final static String QUESTION_CHAPTER = "chapter";
	public final static String QUESTION_NAME = "name";
	public final static String QUESTION_FILE = "filename";
	
	public final static String UPLOAD_PROJECT="upload_project";
	public final static String UPLOAD_PROJECT_NAME="upload_project_name";
	public final static String UPLOAD_FILE="file_node";
	public final static String UPLOAD_DIR="directory_node";
	public final static String UPLOAD_NAME="name";
	public final static String UPLOAD_STATE="server_have";
	public final static String UPLOAD_STATE_YES="yes";
	public final static String UPLOAD_STATE_NO="no";
	
	public final static String FILEPRINT = "MD5";
	
}
