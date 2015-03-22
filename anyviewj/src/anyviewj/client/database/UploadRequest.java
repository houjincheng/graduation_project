package anyviewj.client.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import sun.misc.BASE64Encoder;
import anyviewj.interfaces.ui.JavaProject;
import anyviewj.net.client.ClientTerminal;
import anyviewj.net.common.Request;

public class UploadRequest extends Request{
	
	private JavaProject uploadjp;
	
	public UploadRequest(JavaProject jp){
		this.uploadjp=jp;
	}

	@Override
	public int resolve() throws NullPointerException {
		// TODO Auto-generated method stub
		int state=0;
		
		Document doc=null;
		doc = buildFileList();
		
		Document result = null;
		ClientTerminal ct = ClientTerminal.getInstance();
		result = ct.server( doc );
		
		state=transferFile(result);
		
		return state;
	}

	private Document buildFileList() {
		// TODO Auto-generated method stub
		Document doc = super.createFormattedRequsetDocment();
		
		Element questNode = doc.createElement(UPLOAD_PROJECT);
		String proName=uploadjp.shortName;
		proName=proName.substring(0,proName.indexOf('.'));
		questNode.setAttribute(UPLOAD_PROJECT_NAME, proName);
		questNode.setAttribute(UPLOAD_STATE, UPLOAD_STATE_NO);
		buildfileNode(uploadjp.projectPath, questNode, doc);
		
		super.appendChildToDataNode( doc, questNode );
		
		
		signResolver( "anyviewj.net.server.UploadRequestResolver", doc );
		return doc;
	}
	
	private void buildfileNode(String path, Element parent, Document doc){
		if(!path.endsWith(File.separator))
			path=path+File.separator;
		File f = new File(path);
		File temp;
		String[] flist = f.list();
		for(int i=0; i<flist.length;i++){
			String str = flist[i];
            if(str.equals(".")||str.equals("..")) continue;
            temp=new File(path+str);
            String md=null;
            if(temp.isFile()){
            	Element fileNode=doc.createElement(UPLOAD_FILE);
            	fileNode.setAttribute(UPLOAD_NAME,str);
            	fileNode.setAttribute(UPLOAD_STATE, UPLOAD_STATE_NO);
            	md=FilefingerPrint.getMD5(temp);
            	fileNode.setAttribute(FILEPRINT, md);
            	parent.appendChild(fileNode);
            }else{
            	Element dirNode=doc.createElement(UPLOAD_DIR);
            	dirNode.setAttribute(UPLOAD_NAME, str);
            	dirNode.setAttribute(UPLOAD_STATE, UPLOAD_STATE_NO);
            	parent.appendChild(dirNode);
            	buildfileNode(path+str+File.separator,dirNode,doc);
            }
		}
	}
	
	private int transferFile(Document doc){
		int state=1;
		
		Document transferDoc=null;
		
		Element proNode = (Element) doc.getElementsByTagName(UPLOAD_PROJECT).item(0);
		
		transferDoc=createFormattedRequsetDocment();
		
		Element transfileNode = transferDoc.createElement(UPLOAD_PROJECT);
		transfileNode.setAttribute(UPLOAD_PROJECT_NAME, proNode.getAttribute(UPLOAD_PROJECT_NAME));
		super.appendChildToDataNode( transferDoc, transfileNode );
		
		signResolver( "anyviewj.net.server.UploadRequestAccept", transferDoc );
		
		state=recursionTrans(transferDoc,uploadjp.projectPath,proNode,transfileNode);
		
		return state;
	}
	
	private int recursionTrans(Document doc,String path,Element parent1,Element parent2){
		int state=1;
		if(!path.endsWith(File.separator))
			path=path+File.separator;
		NodeList nl=parent1.getChildNodes();
		for(int i=0; i<nl.getLength(); i++){
			if(nl.item(i).getAttributes()==null) continue;
			Element node=(Element) nl.item(i);
			String fiName=null;
			String abpath=null;
			if(node.getTagName().equals(UPLOAD_FILE) && node.getAttribute(UPLOAD_STATE).equals(UPLOAD_STATE_NO)){
				fiName=node.getAttribute(UPLOAD_NAME);
				abpath=path+fiName;
				File f= new File(abpath);
				int len = (int) f.length();
				byte[] filein = new byte[len];
				String tmp=null;
				try {
					FileInputStream a = new FileInputStream(f);
					a.read(filein);
					tmp=new BASE64Encoder().encode(filein);
				}catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return RESOLVE_FAILE;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return RESOLVE_FAILE;
				}
				Element fileNode=doc.createElement(UPLOAD_FILE);
				fileNode.setAttribute(FILEPRINT, node.getAttribute(FILEPRINT));
				fileNode.setAttribute(UPLOAD_NAME,fiName);
				Text textNode = doc.createTextNode(tmp);
				fileNode.appendChild(textNode);
				parent2.appendChild(fileNode);
				
				ClientTerminal ct = ClientTerminal.getInstance();
				Document result = ct.server( doc);
				String value = ((Element) result.getElementsByTagName(REQUESTRESOLVER_RESULTNODE).item(0)).getAttribute(REQUESTRESOLVER_RESULTNODE_ATTRIBUTE);
				if(Integer.valueOf(value)==RESOLVE_FAILE){
					return RESOLVE_FAILE;
				}
				parent2.removeChild(fileNode);
				
			}else if(node.getTagName().equals(UPLOAD_DIR)){
				String dirname=node.getAttribute(UPLOAD_NAME);
				String dirpath=path+dirname+File.separator;
				Element dirNode = doc.createElement(UPLOAD_DIR);
				dirNode.setAttribute(UPLOAD_NAME, dirname);
				parent2.appendChild(dirNode);
				state=recursionTrans(doc, dirpath, node,dirNode);
				parent2.removeChild(dirNode);
				if(state==RESOLVE_FAILE){
					return RESOLVE_FAILE;
				}
			}
			
		}
		
		
		return state;
	}

}
