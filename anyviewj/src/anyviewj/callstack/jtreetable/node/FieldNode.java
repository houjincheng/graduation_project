
package anyviewj.callstack.jtreetable.node;

import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.Field;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.PrimitiveType;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.StackFrame;
import com.sun.jdi.Type;
import com.sun.jdi.Value;

public class FieldNode extends AbstractNode{
	
	private Value varValue=null;
	
	public FieldNode( StackFrame frame, LocalVariable variable ) 
	{
		setName( variable.name() );
		try {
			setType( variable.type() );
		} catch (ClassNotLoadedException e) {
			e.printStackTrace();
		}
		
		Value value = frame.getValue( variable );
		boolean needfilte = ClassFilter.filte(value);
		if(needfilte){
			 Field vf=((ObjectReference) value).referenceType().fieldByName("value");
			 Value vv = ((ObjectReference) value).getValue(vf);
			 varValue = vv;
			 String tmp = vv.toString();
			 setValue(tmp);
			 setLeaf(true);
		}else{
			varValue = value;
			String tmp = value==null? "" : value.toString();
		
			if ( tmp.contains( "(id=" ) )
			{
				tmp  = tmp.substring( tmp.indexOf( "(id=" ) + 1, tmp.indexOf( ")" ) );
			}
			setValue( tmp );
			if((getType() instanceof PrimitiveType) || getType()==null){
				setLeaf(true);
			}
		}
//		this.varValue = value;
//		String tmp = value==null? "" :value.toString();
//		
//		if ( tmp.contains( "(id=" ) )
//		{
//			tmp  = tmp.substring( tmp.indexOf( "(id=" ) + 1, tmp.indexOf( ")" ) );
//		}
//		setValue( tmp );
//		
//		//add by ydl
//		if((getType() instanceof PrimitiveType)|| getType()==null){
//			setLeaf(true);
//		}
	}
	/**
	 * 这是一种特殊的域节点。
	 * 将本对象this作为一个全局变量，this变量下的全局变量作为其孩子节点
	 * @param thisObj
	 */
	public FieldNode( ObjectReference thisObj ) 
	{
		setName( "this" );
		
		ReferenceType rft = thisObj.referenceType();
		
		for ( Field f: rft.visibleFields() )
		{
			addChild( new FieldNode( thisObj, f ) );
		}
	}
	
	//add by ydl
	//用于添加数组的元素
	public FieldNode(int i, Value value, Type t, String tName){
		
		setName(String.valueOf(i));
		if(t!=null)
			setType(t);
		else
			setTypeName(tName);
		
		boolean needfilte = ClassFilter.filte(value);
		if(needfilte){
			 Field vf=((ObjectReference) value).referenceType().fieldByName("value");
			 Value vv = ((ObjectReference) value).getValue(vf);
			 varValue = vv;
			 String tmp = vv.toString();
			 setValue(tmp);
			 setLeaf(true);
		}else{
			varValue = value;
			String tmp = value==null? "" : value.toString();
		
			if ( tmp.contains( "(id=" ) )
			{
				tmp  = tmp.substring( tmp.indexOf( "(id=" ) + 1, tmp.indexOf( ")" ) );
			}
			setValue( tmp );
			if((getType() instanceof PrimitiveType) || getType()==null){
				setLeaf(true);
			}
		}
		
//		String tmp = v==null? "" :v.toString();
//		
//		if ( tmp.contains( "(id=" ) )
//		{
//			tmp  = tmp.substring( tmp.indexOf( "(id=" ) + 1, tmp.indexOf( ")" ) );
//		}
//		setValue( tmp );
//		
//		if((getType() instanceof PrimitiveType) || getType()==null){
//			setLeaf(true);
//		}
	}
	
	public FieldNode(ObjectReference thisObj, Field f) 
	{
		setName( f.name() );
		try {
			setType( f.type() );
		} catch (ClassNotLoadedException e) {

			//注释部分试图将未加载的类加载到虚拟机，但是失败了
			//change by ydl
/*
			List<Value> arguments = new ArrayList<Value>();
			Value arg1 =thisObj.virtualMachine().mirrorOf(e.className());
			arguments.add(arg1);
			ClassLoaderReference clr =((ReferenceType) thisObj.type()).classLoader();
			List<Method> alm = ((ReferenceType) clr.type()).methods();
			List<Method> lm=((ReferenceType) clr.type()).methodsByName("loadClass");
			List<ThreadReference> tre = thisObj.virtualMachine().allThreads();
			ThreadReference thread=null;
			for(ThreadReference tr: tre){
				if(tr.isAtBreakpoint()){
					thread=tr;
				}
			}
			Value v=clr.invokeMethod(thread, lm.get(1), arguments, 0);
*/			
			
			setTypeName( f.typeName() );
		}
		
		Value value = thisObj.getValue( f );
		boolean needfilte = ClassFilter.filte(value);
		if(needfilte){
			 Field vf=((ObjectReference) value).referenceType().fieldByName("value");
			 Value vv = ((ObjectReference) value).getValue(vf);
			 varValue = vv;
			 String tmp = vv.toString();
			 setValue(tmp);
			 setLeaf(true);
		}else{
			varValue = value;
			String tmp = value==null? "" : value.toString();
		
			if ( tmp.contains( "(id=" ) )
			{
				tmp  = tmp.substring( tmp.indexOf( "(id=" ) + 1, tmp.indexOf( ")" ) );
			}
			setValue( tmp );
			if((getType() instanceof PrimitiveType) || getType()==null){
				setLeaf(true);
			}
		}
		
		
	}
	
	public Value getVarValue(){
		return varValue;
	}

}

