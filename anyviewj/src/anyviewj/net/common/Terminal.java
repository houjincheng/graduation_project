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
		// 需要做一些先期的检查工作
	}
	
	/**
	 * 将xml文档转换为二进制流数据，
	 * 从连接一端发到另一端
	 * @param doc     要发送的数据
	 * @param socket  网路链接
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
//			将XML转为字节数组
			data = transferByteArray( doc );
//			不能以字符串形式写入，因为中文下，包含转义字符等问题
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
	 * 从链路的另外一端接收字节数据，并转换为XML
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
//		不能通过转换成字符串的方式获得字节数组，因为转义字符、中文编码等问题
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return doc;
	}
	/**
	 * 把XML转为字节数组
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
	 * 把字节数组转为XML形式
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
//	 * 这里有问题
//	 * 1、流的堵塞
//	 * 2、传输中文
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
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
//		data =  new String( sb ).getBytes();
//		System.out.println( "receiveData = " + new String( data ) + "\nlength = " + data.length );
//		return transferDocument(  data );
//	}
	
}
