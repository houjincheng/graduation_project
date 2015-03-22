package anyviewj.interfaces.ui.panel;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.ArrayReference;
import com.sun.jdi.ClassType;
import com.sun.jdi.InterfaceType;
import java.util.List;
import com.sun.jdi.Type;

/**
 * 
 * @author Administrator
 */
public class LVObjectReferenceChecker {
	private static final int Binary_MODE = 1;
	private static final int LNode_MODE = 2;
	private static final int DNode_MODE = 3;
	private static final int Vertex_MODE = 4;
	private static final int SQNode_MODE = 5; // lmk
	private static final int GLNode_MODE = 6; // lmk
	private static final int CTNode_MODE = 7;// Wcb
	private static final int Entry_MODE = 8;// Wcb
	private static final int CSTNode_MODE = 9;// wcb
	private static final int GLNode2_MODE = 10;
	private static final int PTNode_MODE = 11;
	private static final String BinTNode = "com.bluemarsh.jswat.datastruct.baseclass.bintree.BinTNode";
	private static final String BinTNodeAdapter = "com.bluemarsh.jswat.datastruct.baseclass.bintree.BinTNodeAdapter";
	private static final String LNode = "com.bluemarsh.jswat.datastruct.baseclass.linklist.LNode";
	private static final String DNode = "com.bluemarsh.jswat.datastruct.baseclass.linklist.DNode";
	private static final String Vertex = "com.bluemarsh.jswat.datastruct.baseclass.graph.Vertex";
	private static final String SQNode = "com.bluemarsh.jswat.datastruct.baseclass.staque.SQNode";
	private static final String GLNode = "com.bluemarsh.jswat.datastruct.baseclass.glist.GLNode";
	private static final String CTNode = "com.bluemarsh.jswat.datastruct.baseclass.tree.CTNode";
	private static final String CTNodeAdapter = "com.bluemarsh.jswat.datastruct.baseclass.tree.CTNodeAdapter";
	private static final String TreeNode = "com.bluemarsh.jswat.dataclass.baseclass.tree.TreeNode";
	private static final String TreeNodeAdapter = "com.bluemarsh.jswat.datastruct.baseclass.tree.TreeNodeAdapter";
	private static final String Entry = "com.bluemarsh.jswat.datastruct.baseclass.hashtable.Entry";
	private static final String CSTNode = "com.bluemarsh.jswat.datastruct.baseclass.tree.CSTNode";
	private static final String CSTNodeAdapter = "com.bluemarsh.jswat.datastruct.baseclass.tree.CSTNodeAdapter";
	private static final String PTNode = "com.bluemarsh.jswat.datastruct.baseclass.tree.CTree$PTNode";
	private static final String GLNode2 = "com.bluemarsh.jswat.datastruct.baseclass.glist.GLNode2";
	private static final String GLNode2Adapter = "com.bluemarsh.jswat.datastruct.baseclass.glist.GLNode2Adapter";

	public static int getObjectReferenceType(ObjectReference or) {
		if (or instanceof ArrayReference) {
			return 0;
		}
		if (or == null)
			return 0;
		ClassType curclass = (ClassType) or.referenceType();
		ClassType superclass = ((ClassType) or.referenceType()).superclass();
		// 如果无父类
		if (superclass == null && curclass == null)
			return 0;
		String superclassName = "";
		String curclassName = "";
		if (superclass != null)
			superclassName = superclass.name();
		if (curclass != null)
			curclassName = curclass.name();
		// 获取它的直接父类
		List<InterfaceType> interfaces = ((ClassType) or.referenceType())
				.allInterfaces();
		// 获取它所有的接口
		String interfaceName = new String("");
		for (InterfaceType in : interfaces) {
			interfaceName = interfaceName.concat(in.name());
		}

		if ((interfaceName.indexOf(BinTNode) >= 0)
				|| (superclassName.compareTo(BinTNodeAdapter) == 0)) {
			return Binary_MODE;
		}
		if ((curclassName.compareTo(LNode) == 0)) {
			return LNode_MODE;
		}
		if ((curclassName.compareTo(DNode) == 0)) {
			return DNode_MODE;
		}
		if ((curclassName.compareTo(Vertex) == 0)) {
			return Vertex_MODE;
		}
		if ((curclassName.compareTo(SQNode) == 0)) {
			return SQNode_MODE;
		}
		if ((superclassName.compareTo(GLNode) == 0)) {
			System.out.println("coming into GLNode MODE");
			return GLNode_MODE;
		}
		if ((curclassName.compareTo(CTNode) == 0)
				|| (superclassName.compareTo(CTNodeAdapter) == 0)) {
			System.out.println("come into CTNode_MODE");
			return CTNode_MODE;
		}
		if ((curclassName.compareTo(Entry) == 0)) {
			System.out.println("come into Entry_MODE");
			return Entry_MODE;

		}
		if ((curclassName.compareTo(CSTNode) == 0)
				|| (superclassName.compareTo(CSTNodeAdapter) == 0)) {
			System.out.println("come into CSTNode_MODE");
			return CSTNode_MODE;
		}
		if ((curclassName.compareTo(PTNode) == 0)) {
			System.out.println("come into PTNode_MODE");
			return PTNode_MODE;
		}
		if ((curclassName.compareTo(GLNode2) == 0)
				|| (superclassName.compareTo(GLNode2Adapter) == 0)) {
			System.out.println("come into GLNode2_MODE");
			return GLNode2_MODE;
		}
		return 0;
	}

	public static int getObjectReferenceType1(Type t) {
		ClassType curclass = (ClassType) t;
		ClassType superclass = curclass.superclass();
		// 如果无父类
		if (superclass == null && curclass == null)
			return 0;
		String superclassName = "";
		String curclassName = "";
		if (superclass != null)
			superclassName = superclass.name();
		if (curclass != null)
			curclassName = curclass.name();
		List<InterfaceType> interfaces = curclass.allInterfaces();
		// 获取它所有的接口
		String interfaceName = new String("");
		for (InterfaceType in : interfaces) {
			interfaceName = interfaceName.concat(in.name());
		}
		if ((interfaceName.indexOf(BinTNode) >= 0)
				|| (superclassName.compareTo(BinTNodeAdapter) == 0)) {
			return Binary_MODE;
		}
		if ((curclassName.compareTo(LNode) == 0)) {
			return LNode_MODE;
		}
		if ((curclassName.compareTo(DNode) == 0)) {
			return DNode_MODE;
		}
		if ((curclassName.compareTo(Vertex) == 0)) {
			return Vertex_MODE;
		}
		if ((curclassName.compareTo(SQNode) == 0)) {
			return SQNode_MODE;
		}
		if ((superclassName.compareTo(GLNode) == 0)) {
			System.out.println("coming into GLNode MODE");
			return GLNode_MODE;
		}
		if ((curclassName.compareTo(CTNode) == 0)
				|| (superclassName.compareTo(CTNodeAdapter) == 0)) {
			System.out.println("come into CTNode_MODE");
			return CTNode_MODE;
		}
		if ((curclassName.compareTo(Entry) == 0)) {
			System.out.println("come into Entry_MODE");
			return Entry_MODE;
		}
		if ((curclassName.compareTo(CSTNode) == 0)
				|| (superclassName.compareTo(CSTNodeAdapter) == 0)) {
			System.out.println("come into CSTNode_MODE");
			return CSTNode_MODE;
		}
		if ((curclassName.compareTo(PTNode) == 0)) {
			System.out.println("come into PTNode_MODE");
			return PTNode_MODE;
		}
		if ((curclassName.compareTo(GLNode2) == 0)
				|| (superclassName.compareTo(GLNode2Adapter) == 0)) {
			System.out.println("come into GLNode2_MODE");
			return GLNode2_MODE;
		}

		return 0;
	}
}