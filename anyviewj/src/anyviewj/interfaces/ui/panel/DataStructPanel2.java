package anyviewj.interfaces.ui.panel;

import java.util.HashMap;
import java.util.Map;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JComponent;
import javax.swing.JPanel;

import anyviewj.debug.manager.ContextManager;
import anyviewj.debug.session.Session;
import anyviewj.debug.session.event.SessionEvent;
import anyviewj.debug.session.event.SessionListener;
import anyviewj.debug.source.event.ContextChangeEvent;
import anyviewj.debug.source.event.ContextListener;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassType;
import com.sun.jdi.Field;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.Mirror;
import com.sun.jdi.ObjectCollectedException;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.StackFrame;
import com.sun.jdi.Value;

import datastruct.detect.TypeDetectTest;
import datastruct.ui.JDataStructPanel;
import datastruct.ui.JListDataStructPanel;

public class DataStructPanel2 extends JPanel implements ContextListener, SessionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Session ownSession;
	ContextManager contextManager=null;
	StackFrame frame;
	TypeDetectTest typeDetect;
	HashMap<Entry<Mirror,ObjectReference>, JComponent> compMap= new HashMap<Entry<Mirror,ObjectReference>, JComponent>();
	
	public DataStructPanel2(){

	}
	
	void refresh(){
		if(frame==null){
			try {
				frame = ((ContextManager)ownSession.getManager(ContextManager.class)).getCurrentStackFrame();
			} catch (IndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ObjectCollectedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IncompatibleThreadStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(frame!=null){
			refreshPanel();
			repaint();
		}
	}
	
	void refreshPanel(){
		Map<Mirror, ObjectReference> corMap = findFieldStruct();
		corMap.putAll(localStruct());
		for(Entry<Mirror, ObjectReference> entr: corMap.entrySet()){
			ClassType ct = (ClassType) entr.getValue().referenceType();
			if(typeDetect.isSingleList(ct)||typeDetect.isSingleNode(ct)){
				JDataStructPanel jpanel = (JDataStructPanel) compMap.get(entr);
				if(jpanel!=null) jpanel.refresh(frame);
				else {
					String name = "";
					Mirror key = entr.getKey();
					if(key instanceof Field){
						name = ((Field)key).name();
					}else if(key instanceof LocalVariable){
						name = ((LocalVariable) key).name();
					}else{
						name = "this";
					}
					jpanel = (JDataStructPanel) typeDetect.getListUI(name, entr.getValue(), frame);
					compMap.put(entr, jpanel);
					add(jpanel);
					revalidate();
				}
			}
		}
	}

	Map<Mirror, ObjectReference> findFieldStruct(){
		Map<Mirror, ObjectReference> refMap = new HashMap<Mirror, ObjectReference>();
		if(thisObject()==null){
			ReferenceType thisType= thisType();
			for(Field f:thisType.allFields()){
				if(f.isStatic()){
					Value v = thisType.getValue(f);
					if(v!=null&& v instanceof ObjectReference){
						refMap.put(f,(ObjectReference) v);
					}
				}
			}
			
		}else{
			ObjectReference or = thisObject();
			if(or instanceof ObjectReference)
				refMap.put(or.referenceType(), (ObjectReference) or);
			ReferenceType rt = or.referenceType();
			for(Field f: rt.allFields()){
				Value v = or.getValue(f);
				if(v!=null && v instanceof ObjectReference){
					refMap.put(f, (ObjectReference) v);
				}
			}
		}
		
		if(refMap.size()>0){
			for(Entry<Mirror, ObjectReference> entr : refMap.entrySet()){
				if(!typeDetect.isDoubleStruct((ClassType) entr.getValue().referenceType())
						&&!typeDetect.isSingleList((ClassType) entr.getValue().referenceType())
						&&!typeDetect.isSingleNode((ClassType) entr.getValue().referenceType())
						&&!typeDetect.isDoubleNode((ClassType) entr.getValue().referenceType())){
					refMap.remove(entr.getKey());
				}
			}
		}
		
		return refMap;
	}
	
	Map<Mirror, ObjectReference> localStruct(){
		Map<Mirror, ObjectReference> localMap = new HashMap<Mirror, ObjectReference>();
		
		try {
			for(LocalVariable lv : frame.visibleVariables()){
				Value v = frame.getValue(lv);
				if(v instanceof ObjectReference){
					ObjectReference cor = (ObjectReference) v;
					if(typeDetect.isSingleNode((ClassType) cor.type())
							||typeDetect.isDoubleNode((ClassType) cor.type())){
						localMap.put(lv,cor);
					}
				}
			}
		} catch (AbsentInformationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return localMap;
	}
	
	ObjectReference thisObject(){
		return frame.thisObject();
	}
	
	ReferenceType thisType(){
		return frame.location().declaringType();
	}

	@Override
	public void contextChanged(ContextChangeEvent cce) {
		// TODO Auto-generated method stub
		contextManager = (ContextManager)cce.getSource();
		if(!cce.isBrief()){
			try {
				frame = contextManager.getCurrentStackFrame();
				typeDetect = new TypeDetectTest();
				refresh();
			} catch (IndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ObjectCollectedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IncompatibleThreadStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void activated(SessionEvent sevt) {
		// TODO Auto-generated method stub
		ContextManager ctxtMgr = (ContextManager) sevt.getSession().getManager(
				ContextManager.class);
		ctxtMgr.addContextListener(this);
		contextManager = ctxtMgr;
	}

	@Override
	public void closing(SessionEvent sevt) {
		// TODO Auto-generated method stub
		ownSession = null;
	}

	@Override
	public void deactivated(SessionEvent sevt) {
		// TODO Auto-generated method stub
		ContextManager ctxtMgr = (ContextManager) sevt.getSession().getManager(
				ContextManager.class);
		ctxtMgr.removeContextListener(this);
	}

	@Override
	public void opened(Session session) {
		// TODO Auto-generated method stub
		ownSession = session;
	}

	@Override
	public void resuming(SessionEvent sevt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suspended(SessionEvent sevt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSession(Session e) {
		// TODO Auto-generated method stub
		ownSession=e;
		ownSession.addListener(this);
	}
	
	
}
