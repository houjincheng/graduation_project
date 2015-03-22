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
	 * ������ͨ������������ʱ�����ص�xml�ļ���������������������µĽڵ㣬
	 * ����ͼ��ȡ��Щ�����ڵĽڵ�ʱ�����׳���ָ���쳣
	 * @return
	 * @throws NullPointerException
	 */
	public abstract int resolve() throws NullPointerException;

	/**
	 * �涨Request���Դ��ݵ�XML��Ϣ��ʽ
	 * 
	 * ��Ϣͷָ���������ݰ���RequestResolver, ������������Ҫ����������
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
		
//		��Ƿ���������˺�
		request.setAttribute(REQYEST_DOCUMENT_STUDENTID, ClientTerminal.getInstance()
				.getStudentID());
//		��Ǹ��˺ŵĵ�¼ָ��
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
