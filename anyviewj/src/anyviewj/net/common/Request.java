package anyviewj.net.common;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import anyviewj.net.client.ClientTerminal;

public abstract class Request implements CommunicationProtocol {

	/**
	 * 当不能通过服务器端验时，返回的xml文件将不含有正常解析结果下的节点，
	 * 当试图获取这些不存在的节点时，将抛出空指针异常
	 * @return
	 * @throws NullPointerException
	 */
	public abstract int resolve() throws NullPointerException;

	/**
	 * 规定Request可以传递的XML信息格式
	 * 
	 * 信息头指定解析数据包的RequestResolver, 其他部分是需要解析的内容
	 * 
	 * @return
	 * @throws ParserConfigurationException
	 */
	protected Document createFormattedRequsetDocment(){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Document doc = db.newDocument();

		Element request = doc.createElement(REQUEST_DOCUMENT_REQUESTNODE);
		Element data = doc.createElement(REQUEST_DOCUMENT_DATANODE);
		
//		标记发出请求的账号
		request.setAttribute(REQYEST_DOCUMENT_STUDENTID, ClientTerminal.getInstance()
				.getStudentID());
//		标记该账号的登录指纹
		request.setAttribute(REQYEST_DOCUMENT_LOGINTOKEN, ClientTerminal.getInstance()
				.getLoginToken());
		
		doc.appendChild(request);
		request.appendChild(data);

		return doc;
	}

	protected void appendChildToDataNode(Document doc, Element e) {
		Element root = doc.getDocumentElement();

		Element data = (Element) root.getElementsByTagName(
				REQUEST_DOCUMENT_DATANODE).item(0);

		data.appendChild(e);
	}

	public boolean signResolver(String resolverName, Document doc) {

		if (doc != null) {
			Element requestNode = ( Element )doc.getElementsByTagName( REQUEST_DOCUMENT_REQUESTNODE ).item( 0 );
			requestNode.setAttribute(REQUEST_DOCUMENT_REQUSETNODE_RESOLVER,
					resolverName);
			return true;
		}
		return false;
	}
	public int showMessage( Object obj, int optionType, int messageType  )
	{
		return JOptionPane.showConfirmDialog
		( null, obj, "AnyviewJ", optionType, messageType );
	}
}
