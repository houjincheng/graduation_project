package anyviewj.client.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import sun.misc.BASE64Decoder;
import anyviewj.net.client.ClientTerminal;
import anyviewj.net.common.Request;

public class QuestionRequest extends Request{

	private String ChapterName=null;
	private String QuestionName=null;
	//原题 还是 做过的题
	private String Change = null;
	
	public QuestionRequest(String chapter, String question, String change){
		ChapterName=chapter;
		QuestionName=question;
		if(change=="raw"){
			Change=QUESTION_RAW;
		}else{
			Change=QUESTION_CHANGED;
		}
	}
	
	@Override
	public int resolve() throws NullPointerException {
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
			Element dataNode =(Element) result.getElementsByTagName(REQUEST_DOCUMENT_DATANODE).item(0);
			String zipName = dataNode.getAttribute(QUESTION_FILE);
			String tmp = ((Text)dataNode.getFirstChild()).getWholeText();
			try {
				byte[] a = new BASE64Decoder().decodeBuffer(tmp);
				File file = new File(zipName+".zip");
				if(file.exists()){
					file.delete();
				}
				file.createNewFile();
				FileOutputStream fileout = new FileOutputStream(file);
				fileout.write(a);
				fileout.flush();
				fileout.close();
				unzip(file);
				file.delete();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
		}
		return 1;
	}
	
//	public static void main(String[] argv){
//		QuestionRequest q = new QuestionRequest("","","");
//		File f = new File("E:/workspace/anyviewj/a.zip");
//		q.unzip(f);
//	}
	
	private void unzip(File zip){
		try {
			ZipFile zipFile = new ZipFile(zip);
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zip));
			ZipEntry zipEntry = null;
			while((zipEntry = zipInputStream.getNextEntry())!= null){
				if(!zipEntry.isDirectory()){
					String fileName = zipEntry.getName();
					File temp = new File(Bundle.getString("projectpath")+fileName);
					//System.out.println("d:/anyviewj/project/"+fileName);
					if(!temp.getParentFile().exists()){
						temp.getParentFile().mkdirs();
					}
					OutputStream os = new FileOutputStream(temp);
					InputStream is = zipFile.getInputStream(zipEntry);
					int len=0;
					while((len = is.read())!=-1){
						os.write(len);
					}
					os.close();
					is.close();
				}
			}
			zipInputStream.close();
			zipFile.close();
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private Document buildData() throws ParserConfigurationException {
		// TODO Auto-generated method stub
		Document doc = super.createFormattedRequsetDocment();
		
		Element questNode = doc.createElement(QUESTION_REQUEST);
		questNode.setAttribute(QUESTION_TYPE, Change);
		questNode.setAttribute(QUESTION_CHAPTER, ChapterName);
		questNode.setAttribute(QUESTION_NAME, QuestionName);
		
		super.appendChildToDataNode( doc, questNode );
		
		
		signResolver( "anyviewj.net.server.QuestionRequestResolver", doc );
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

}
