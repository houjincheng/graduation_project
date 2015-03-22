package anyviewj.net.server;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import anyviewj.net.common.RequestResolver;

public class UploadRequestResolver extends RequestResolver{

	@Override
	public Document execute(Document doc) {
		// TODO Auto-generated method stub
		Document result = null;

		result = createFormattedResultDocment();

		Element root = doc.getDocumentElement();
		Element uploadNode = (Element) root.getElementsByTagName(
				UPLOAD_PROJECT).item(0);
		
		Element requestElement = 
				( Element )doc.getElementsByTagName( REQUEST_DOCUMENT_REQUESTNODE ).item( 0 );
		String studentID = requestElement.getAttribute( REQYEST_DOCUMENT_STUDENTID );
		
		Element projectNode = (Element) result.importNode(uploadNode,true);
		appendChildToDataNode( result, projectNode);
		
		String path=SERVER_UPLOAD_FILE_PATH;
		String projectName=projectNode.getAttribute(UPLOAD_PROJECT_NAME);
		
		String dirpath=path+studentID+File.separator+projectName+File.separator;
		File f=new File(dirpath);
		int value = RESOLVE_SUCCESSED;
		if(f.exists()){
			value = process(studentID, projectNode, dirpath);
		}
		signResult(result, String.valueOf(value));
		return result;
	}
	
	private int process(String sno, Element parent, String path){
		int value=1;
		
		File f=null;
		NodeList nl=parent.getChildNodes();
		for(int i=0; i<nl.getLength(); i++){
			if(nl.item(i).getAttributes()==null) continue;
			Element node= ((Element) nl.item(i));
			String name = node.getAttribute(UPLOAD_NAME);
			String TagName = node.getTagName();
			f=new File(path+name);
			if(f.exists()){
				if(TagName.equals(UPLOAD_FILE)){
					String fp1 = node.getAttribute(FILEPRINT);
					String fp2 = FilePrintserver.getFileprint(sno, f);
					if(fp1.equals(fp2)){
						node.setAttribute(UPLOAD_STATE,UPLOAD_STATE_YES);
					}	
				}
				if(TagName.equals(UPLOAD_DIR)){
					node.setAttribute(UPLOAD_STATE,UPLOAD_STATE_YES);
					process(sno, node, path+name+File.separator);
				}
			}
		}
		
		return value;
	}

}
