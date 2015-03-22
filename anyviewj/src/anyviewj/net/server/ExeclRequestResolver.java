package anyviewj.net.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import anyviewj.net.common.CommunicationProtocol;
import anyviewj.net.common.RequestResolver;
import anyviewj.net.server.database.DBConnectionPool;

public class ExeclRequestResolver extends RequestResolver {

	@Override
	public Document execute(Document doc) {
		// TODO Auto-generated method stub
		Document result = null;

		result = createFormattedResultDocment();

		Element root = doc.getDocumentElement();
		Element tableNode = (Element) root.getElementsByTagName(
				EXECLREQUEST_TABLE).item(0);

		String id = tableNode.getAttribute("id");

		System.out.println("id: " + id);

		int value = RESOLVE_FAILE;
		try {
			value = process(result, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		signResult(result, String.valueOf(value));
		return result;
	}

	private int process(Document result, String tableName) throws SQLException {

		DBConnectionPool connPool = ServerTerminal.getInstance()
				.getConnectionPool();
		Connection conn = connPool.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		statement = conn.createStatement();
		
		String sql = "select * from " + tableName;
		resultSet = statement.executeQuery( sql );

		System.out.println("free conn");
		connPool.freeConntion(conn);
		
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int cols = rsmd.getColumnCount();
		
		Element table = result.createElement("table");
		table.setAttribute("id", tableName);
		Element data = (Element) result.getElementsByTagName(REQUESTRESOLVER_RESULTNODE_DATACHILD).item(0);
		data.appendChild(table);
		
		Element tr = result.createElement("tr");
		table.appendChild(tr);
		
		Element th = null;
		String cH = null;
		String cTN = null;
		Text tv = null;
		
		for(int i=0; i<cols; i++){           
			th = result.createElement("th");
			cH = rsmd.getColumnName(i+1);
			cTN = rsmd.getColumnTypeName(i+1);
			th.setAttribute("type", cTN);
			tv = result.createTextNode(cH);
			th.appendChild(tv);
			tr.appendChild(th);
			
			
		}
		
		Element td = null;
		Object to = null;
		for(; resultSet.next();){
			tr = result.createElement("tr");
			table.appendChild(tr);
			for(int j=0; j<cols; j++){
				td = result.createElement("td");
				to = resultSet.getObject(j+1);
				if (to == null){
					tv = result.createTextNode("");
				}
				else{
					tv = result.createTextNode(to.toString());
				}
				tr.appendChild(td);
				td.appendChild(tv);
			}
		}
		
		int state = RESOLVE_SUCCESSED;
		return state;
	}
}
