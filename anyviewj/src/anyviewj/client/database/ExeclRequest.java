package anyviewj.client.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import anyviewj.net.client.ClientTerminal;
import anyviewj.net.common.Request;

public class ExeclRequest extends Request{

	String table = null;
	
	public ExeclRequest(String name){
		this.table = name;
	}
	@Override
	public int resolve() {
		// TODO Auto-generated method stub
		
		Document doc=null;
		try {
			doc = buildData();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document result = null;
		ClientTerminal ct = ClientTerminal.getInstance();
		
		result = ct.server( doc );
		String value = ((Element) result.getElementsByTagName(REQUESTRESOLVER_RESULTNODE).item(0)).getAttribute(REQUESTRESOLVER_RESULTNODE_ATTRIBUTE);
		if(Integer.valueOf(value)==RESOLVE_SUCCESSED){
			alterSqlite(result);
		}
		return 0;
	}

	private Document buildData() throws ParserConfigurationException {
		// TODO Auto-generated method stub
		Document doc = super.createFormattedRequsetDocment();
		Element tableNode = doc.createElement(EXECLREQUEST_TABLE);
		
		Element requestElement = 
				( Element )doc.getElementsByTagName( REQUEST_DOCUMENT_REQUESTNODE ).item( 0 );
		requestElement.setAttribute( REQYEST_DOCUMENT_LOGINTOKEN ,ClientTerminal.getInstance().getLoginToken());
		requestElement.setAttribute( REQYEST_DOCUMENT_STUDENTID ,ClientTerminal.getInstance().getStudentID());
		
		tableNode.setAttribute("id", table);
		super.appendChildToDataNode( doc, tableNode );
		
		
		signResolver( "anyviewj.net.server.ExeclRequestResolver", doc );
		return doc;
	}
	@Override
	public boolean signResolver(String resolverName, Document doc) {
		// TODO Auto-generated method stub
		if ( doc != null )
		{
			Element requestNode = doc.getDocumentElement();
			requestNode.setAttribute
			( REQUEST_DOCUMENT_REQUSETNODE_RESOLVER, resolverName );
			return true;
		}
		return false;
	}

	private void alterSqlite(Document result){
		Element table = (Element) result.getElementsByTagName("table").item(0);

		String tableName = table.getAttribute("id");
		NodeList trlist = table.getElementsByTagName("tr"); 
		Element thead = (Element) trlist.item(0);
		NodeList thlist = thead.getElementsByTagName("th");
		Element th = null;
		Text thv = null;
		
		//创建表
		String createSql = "create table " + tableName + "(";
		int thlen = thlist.getLength();
		for(int i=0; i<thlen-1 ;i++){
			th = (Element) thlist.item(i);
			thv = (Text) th.getFirstChild();
			createSql = createSql + thv.getWholeText();
			createSql = createSql + " " + th.getAttribute("type") + ",";
		}
		th = (Element) thlist.item(thlen-1);
		thv = (Text) th.getFirstChild();
		createSql = createSql + thv.getWholeText();
		createSql = createSql + " " + th.getAttribute("type") + ");";
		
		Connection conn = Sqlite.getConnection();
		Statement s=null;
		try {
			s = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			s.execute("drop table "+ tableName+ " ;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				s.execute(createSql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//插入数据
		String insertSql = "insert into " + tableName + " values( ";
		for(int i=0; i<thlen-1; i++){
			insertSql = insertSql +"?,";
		}
		insertSql = insertSql + "?);";
		
		PreparedStatement pres = null;
		try {
			pres = conn.prepareStatement(insertSql);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		NodeList tdlist = null;
		for(int i=1; i< trlist.getLength();i++){
			Element tr = (Element) trlist.item(i);
			tdlist = tr.getElementsByTagName("td");
			Element td = null;
			Text tdv = null;
			for(int j=0; j<thlen; j++){
				td = (Element) tdlist.item(j);
				tdv = (Text) td.getFirstChild();
				if(tdv == null){
					try {
						pres.setNull(j+1,Types.VARCHAR);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					try {
						pres.setString(j+1,tdv.getWholeText());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			try {
				pres.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pres.clearParameters();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	};
}
