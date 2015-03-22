package anyviewj.net.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import sun.misc.BASE64Decoder;
import anyviewj.net.common.RequestResolver;

public class UploadRequestAccept extends RequestResolver{

	@Override
	public Document execute(Document doc) {
		// TODO Auto-generated method stub
		int value=0;
		Document result = null;

		result = createFormattedResultDocment();
		
		Element projectNode =(Element) doc.getElementsByTagName(UPLOAD_PROJECT).item(0);
		
		String path=SERVER_UPLOAD_FILE_PATH;
		String projectName=projectNode.getAttribute(UPLOAD_PROJECT_NAME);
		
		Element requestElement = 
				( Element )doc.getElementsByTagName( REQUEST_DOCUMENT_REQUESTNODE ).item( 0 );
		String studentID = requestElement.getAttribute( REQYEST_DOCUMENT_STUDENTID );
		
		String dirpath=path+studentID+File.separator+projectName+File.separator;
		
		NodeList nolist= projectNode.getChildNodes();
		Element dirNode=null;
		for(int i=0;i<nolist.getLength();i++){
			if(nolist.item(i).getAttributes()!=null) dirNode=(Element) nolist.item(i);
		}
		
		while(dirNode.getTagName().equals(UPLOAD_DIR)){
			dirpath=dirpath+dirNode.getAttribute(UPLOAD_NAME)+File.separator;
			nolist= dirNode.getChildNodes();
			for(int i=0;i<nolist.getLength();i++){
				if(nolist.item(i).getAttributes()!=null) dirNode=(Element) nolist.item(i);
			}
		}
		Element fileNode=dirNode;
		String filepath=dirpath+fileNode.getAttribute(UPLOAD_NAME);
		String tmp = ((Text)fileNode.getFirstChild()).getWholeText();
		try {
			byte[] a = new BASE64Decoder().decodeBuffer(tmp);
			File file = new File(filepath);
			if(file.exists()){
				file.delete();
			}
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
			FileOutputStream fileout = new FileOutputStream(file);
			fileout.write(a);
			fileout.flush();
			fileout.close();
			Boolean guard=false;
			//可能这里需要文件校验
			guard=FilePrintserver.setFileprint(studentID, fileNode.getAttribute(FILEPRINT), file);
			if(guard==true){
				value=1;
			}
			
		}catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.getMessage();
		}
		
		signResult(result, String.valueOf(value));
		return result;
	}

}
