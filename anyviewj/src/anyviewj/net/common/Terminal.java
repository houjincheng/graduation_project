package anyviewj.net.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

public abstract class Terminal implements CommunicationProtocol{


	public Terminal() {
		// ��Ҫ��һЩ���ڵļ�鹤��
	}
	
	/**
	 * ��xml�ĵ�ת��Ϊ�����������ݣ�
	 * ������һ�˷�����һ��
	 * @param doc     Ҫ���͵�����
	 * @param socket  ��·����
	 * @return
	 */
	public int sendData( Document doc, Socket socket )
	{
		BufferedWriter out = null;
		byte[] data = null;
		char[] tmp = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try
		{
//			��XMLתΪ�ֽ�����
			data = transferByteArray( doc );
//			�������ַ�����ʽд�룬��Ϊ�����£�����ת���ַ�������
			tmp = new char[data.length];
			for ( int i=0; i<data.length; i++ )
			{
				tmp[i] = ( char )( data[i] );
			}
			out.write( tmp );
			out.flush();
			
			System.out.println( "sendData = " + new String( tmp )
			+ "\ntmpLen = " + tmp.length );
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return 0;
		}
		return 0;
	}
	/**
	 * ����·������һ�˽����ֽ����ݣ���ת��ΪXML
	 * @param socket
	 * @return
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 */
	public Document receiveData(Socket socket) {
		
		BufferedReader in = null;
		char[] tmp = new char[10240000];
		byte[] data = null;
		int len = -1;
		Document doc = null;
		try {

			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), "UTF-8"));
			if ( ( len = in.read( tmp ) ) != -1 )
			{
				
			}
		} catch (UnsupportedEncodingException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
//		����ͨ��ת�����ַ����ķ�ʽ����ֽ����飬��Ϊת���ַ������ı��������
		data = new byte[len];
		for ( int i=0; i<len; i++ )
		{
			data[i] = ( byte )( tmp[i] );
		}
		
		System.out.println( "receiveData = " + new String( data ) 
		+ "\nlen = " + data.length );
		try {
			doc = transferDocument( data );
		} catch (TransformerFactoryConfigurationError e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return doc;
	}
	/**
	 * ��XMLתΪ�ֽ�����
	 * @param doc
	 * @return
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 */
	protected byte[] transferByteArray( Document doc ) 
			throws TransformerFactoryConfigurationError,
			TransformerException 
	{
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		StreamResult streamResult = new StreamResult(bos);
		transformer.transform(new DOMSource(doc), streamResult);
		return bos.toByteArray();
	}
	/**
	 * ���ֽ�����תΪXML��ʽ
	 * @param data
	 * @return
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 */
	protected Document transferDocument(byte[] data) 
			throws TransformerFactoryConfigurationError, 
			TransformerException {
		DOMResult docResult = new DOMResult();
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
		ByteArrayInputStream bai = new ByteArrayInputStream(data);
		StreamSource ss = new StreamSource( bai );
		transformer.transform( ss, docResult);

		Document doc = (Document) docResult.getNode();
		
		return doc;
	}



//	protected int sendData( Document doc, DataOutputStream dos  )
//	{
//		byte[] data = null;
//		
//		try
//		{
//			data = transferByteArray( doc );
//			System.out.println( "sendData = " + new String( data )
//			+ "\nlength = " + data.length );
//			dos.write( new String( data ).getBytes() );
//		}
//		catch( Exception e )
//		{
//			e.printStackTrace();
//			return 0;
//		}
//		return 0;
//		
//	}
//	/**
//	 * ����������
//	 * 1�����Ķ���
//	 * 2����������
//	 * @param dis
//	 * @return
//	 * @throws TransformerFactoryConfigurationError
//	 * @throws TransformerException
//	 */
//	protected Document receiveData( DataInputStream dis  )
//			throws TransformerFactoryConfigurationError, TransformerException
//	{
//		System.out.println( "receiveData" );
//		byte[] data = new byte[1024];
////		StringBuffer sb = new StringBuffer();
//		StringBuffer sb = new StringBuffer();
//		int length = -1;
//		try {
//			while ( ( ( length = dis.read( data ) ) != -1 ) )
//			{
//				sb.append( new String( data, 0, length ) );
//				if ( dis.available() <= 0 )
//				{
//					break;
//				}
//			}
//		} catch (IOException e) {
//			// TODO �Զ����ɵ� catch ��
//			e.printStackTrace();
//		}
//		data =  new String( sb ).getBytes();
//		System.out.println( "receiveData = " + new String( data ) + "\nlength = " + data.length );
//		return transferDocument(  data );
//	}
	
}
