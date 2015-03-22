package anyviewj.callstack.jtreetable.node;

import com.sun.jdi.Value;

public class ClassFilter {
	static String[] names ={"Boolean", "Byte", "Character", "Double", "Float", "Integer", "Long", "Short"};
	public static boolean filte(Value v){
		if(v==null) return false;
		String typename = v.type().name();
		for(int i=0; i< names.length; i++){
			String tmp = "java.lang."+names[i];
			if(typename.equals(tmp)){
				return true;
			}
		}
		
		return false;
	}
}
//		Boolean b = new Boolean(true);
//		Byte byte1 = new Byte((byte) 1);
//		Character c = new Character('a');
//		Double d = new Double(1.2);
//		Float f = new Float(1.2);
//		Integer in = new Integer(1);
//		Long l = new Long(1);
//		Short s = new Short((short) 1);