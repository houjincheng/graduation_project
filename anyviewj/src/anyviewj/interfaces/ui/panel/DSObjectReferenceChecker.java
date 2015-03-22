package anyviewj.interfaces.ui.panel;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.ClassType;
import com.sun.jdi.InterfaceType;
import java.util.List;
/**
 *
 * @author Administrator
 */
public class DSObjectReferenceChecker {
     
     private static final int Binary_MODE = 1;
     private static final int Graph_MODE = 2;
     private static final int HashTable_MODE = 3;
     private static final int Heap_MODE = 4;

     private static final int LList_MODE =5;
     private static final int CList_MODE = 6;
     private static final int DuLList_MODE = 7;
     private static final int DuCList_MODE = 8;
          
     private static final int LStack_MODE = 9;
     private static final int VStack_MODE = 10;
     private static final int LQueue_MODE = 11;
     private static final int VQueue_MODE = 12;

     private static final int GList_MODE = 13;

     private static final int BiThreadTree_MODE = 14;
     private static final int CLTree_MODE = 15;
     private static final int LinearHashTable_MODE = 16;
     private static final int MGraph_MODE = 17;
     private static final int VList_MODE = 18;
     private static final int CSTree_MODE = 19;
     private static final int CTree_MODE = 20;
     private static final int GList2_MODE = 21;

     private static final String BINARYTREEADAPTER = "com.bluemarsh.jswat.datastruct.baseclass.bintree.BinTreeAdapter";
     private static final String BINARYTREE = "com.bluemarsh.jswat.datastruct.baseclass.bintree.BinTreee";
     private static final String GRAPHADAPTER = "com.bluemarsh.jswat.datastruct.baseclass.graph.GraphAdapter";
     private static final String GRAPH = "com.bluemarsh.jswat.datastruct.baseclass.graph.Graph";
     private static final String HASHTABLE = "com.bluemarsh.jswat.datastruct.baseclass.hashtable.HashTable";
     private static final String HEAP = "com.bluemarsh.jswat.datastruct.baseclass.hashtable.Heap";
     private static final String LLIST = "com.bluemarsh.jswat.datastruct.baseclass.linklist.LList";
     private static final String CLIST = "com.bluemarsh.jswat.datastruct.baseclass.linklist.CList";
     private static final String DULLIST = "com.bluemarsh.jswat.datastruct.baseclass.linklist.DuLList";
     private static final String DUCLIST = "com.bluemarsh.jswat.datastruct.baseclass.linklist.DuCList";
     private static final String QUEUE = "com.bluemarsh.jswat.datastruct.baseclass.staque.Queue";
     private static final String STACK = "com.bluemarsh.jswat.datastruct.baseclass.staque.Stack";
     private static final String VSTACKADAPTER = "com.bluemarsh.jswat.datastruct.baseclass.staque.VStackAdapter";
     private static final String VSTACK = "com.bluemarsh.jswat.datastruct.baseclass.staque.VStack";
     private static final String LSTACKADAPTER = "com.bluemarsh.jswat.datastruct.baseclass.staque.LStackAdapter";
     private static final String LSTACK = "com.bluemarsh.jswat.datastruct.baseclass.staque.LStack";
     private static final String LQUEUEADAPTER = "com.bluemarsh.jswat.datastruct.baseclass.staque.LQueueAdapter";
     private static final String LQUEUE = "com.bluemarsh.jswat.datastruct.baseclass.staque.LQueue";
     private static final String VQUEUEADAPTER = "com.bluemarsh.jswat.datastruct.baseclass.staque.VQueueAdapter";
     private static final String VQUEUE = "com.bluemarsh.jswat.datastruct.baseclass.staque.VQueue";
     private static final String GLISTADAPTER = "com.bluemarsh.jswat.datastruct.baseclass.glist.GListAdapter";
     private static final String GLIST = "com.bluemarsh.jswat.datastruct.baseclass.glist.GList";
     private static final String HASHTABLEADAPTER = "com.bluemarsh.jswat.datastruct.baseclass.hashtable.HashTableAdapter";
     private static final String BITHREADTREE = "com.bluemarsh.jswat.datastruct.baseclass.bithreadtree.BiThreadTree";
     private static final String BITHREADTREEADAPTER = "com.bluemarsh.jswat.datastruct.baseclass.bithreadtree.BiThreadTreeAdapter";
     private static final String TREENODEADAPTER = "com.bluemarsh.jswat.datastruct.baseclass.tree.TreeNodeAdapter";
     private static final String TREE = "com.bluemarsh.jswat.datastruct.baseclass.tree.Tree";
     private static final String LINEARHASHTABLEADAPTER = "com.bluemarsh.jswat.datastruct.baseclass.hashtable.LinearHashTableAdapter";
     private static final String LINEARHASHTABLE ="com.bluemarsh.jswat.datastruct.baseclass.hashtable.LinearHashTable";
     private static final String CLTREE="com.bluemarsh.jswat.datastruct.baseclass.tree.CLTree";
     private static final String CLTREEADAPTER = "com.bluemarsh.jswat.datastruct.baseclass.tree.CLTreeAdapter";
     private static final String CSTREE ="com.bluemarsh.jswat.datastruct.baseclass.tree.CSTree";
     private static final String CSTREEADAPTER = "com.bluemarsh.jswat.datastruct.baseclass.tree.CSTreeAdapter";
     private static final String CTREE = "com.bluemarsh.jswat.datastruct.baseclass.tree.CTree";
     private static final String CTREEADAPTER = "com.bluemarsh.jswat.datastruct.baseclass.tree.CTreeAdapter";
     private static final String MGRAPH = "com.bluemarsh.jswat.datastruct.baseclass.graph.MGraph";
     private static final String MGRAPHADAPTER = "com.bluemarsh.jswat.datastruct.baseclass.graph.MGraphAdapter";
     private static final String VLIST = "com.bluemarsh.jswat.datastruct.baseclass.linklist.VList";
     private static final String VLISTADAPTER = "com.bluemarsh.jswat.datastruct.baseclass.linklist.VListAdapter";
     private static final String GLIST2 = "com.bluemarsh.jswat.datastruct.baseclass.glist.GList2";
     private static final String GLIST2ADAPTER = "com.bluemarsh.jswat.datastruct.baseclass.glist.GList2Adapter";
     public static int getObjectReferenceType(ObjectReference or){
        ClassType superclass = ((ClassType) or.referenceType()).superclass();
		ClassType curclass = (ClassType) or.referenceType();//mk
//        System.out.println("curclass:  " + curclass);
        //如果无父类
        if ( superclass == null && curclass == null)  return 0;

        String superclassName = "";
		String curclassName = "";
        if(superclass != null)
            superclassName = superclass.name();
        if(curclass != null)
            curclassName = curclass.name();
        //获取它的直接父类
        List<InterfaceType> interfaces =((ClassType) or.referenceType()).allInterfaces();
        //获取它所有的接口
        String interfaceName = new String("");
        for(InterfaceType in:interfaces){            
            interfaceName = interfaceName.concat(in.name());
        }
        if((superclassName.compareTo(GLIST2ADAPTER)==0)||(curclassName.compareTo(GLIST2)==0)){
            System.out.println("come into GList2_MODE");
            return GList2_MODE;

        }

        if ((interfaceName.indexOf(BINARYTREE) >=0 ) || (superclassName.compareTo(BINARYTREEADAPTER) == 0) || (curclassName.compareTo(BINARYTREEADAPTER) == 0)) {
            return Binary_MODE;
        }
        if ((superclassName.compareTo(GRAPHADAPTER) == 0) || (curclassName.compareTo(GRAPHADAPTER) == 0)) {
            return Graph_MODE;
        }
        if ((interfaceName.indexOf(HASHTABLE) >=0 )) {
            return HashTable_MODE;
        }
        if ((interfaceName.indexOf(HEAP) >=0 )) {
            return Heap_MODE;
        }
        if ((curclassName.compareTo(LLIST) == 0)) {
            return LList_MODE;
        }
        if ((curclassName.compareTo(CLIST) == 0)) {
            return CList_MODE;
        }
        if ((curclassName.compareTo(DULLIST) == 0)) {
            return DuLList_MODE;
        }
        if ((curclassName.compareTo(DUCLIST) == 0)) {
            return DuCList_MODE;
        }
        if ((superclassName.compareTo(LSTACKADAPTER) == 0) || (curclassName.compareTo(LSTACK) == 0)) {
            System.out.println("coming into --- LStack_MODE");
            return LStack_MODE;
        }
        if ((superclassName.compareTo(VSTACKADAPTER) == 0) || (curclassName.compareTo(VSTACK) == 0)) {
            System.out.println("coming into --- VStack_MODE");
            return VStack_MODE;
        }
        if ((superclassName.compareTo(LQUEUEADAPTER) == 0) || (curclassName.compareTo(LQUEUE) == 0)) {
            System.out.println("coming into --- LQueue_MODE");
            return LQueue_MODE;
        }
        /*实现接口，继承适配器，使用已实现的类*/
        if ((superclassName.compareTo(VQUEUEADAPTER) == 0) || (curclassName.compareTo(VQUEUE) == 0)) {
            System.out.println("coming into --- VQueue_MODE");
            return VQueue_MODE;
        }
        if((superclassName.compareTo(GLISTADAPTER) == 0) || (curclassName.compareTo(GLIST)) == 0) {
            System.out.println("coming into --- GList_MODE");
            return GList_MODE;
        }
        if ((superclassName.compareTo(BITHREADTREEADAPTER) == 0)|| (curclassName.compareTo(BITHREADTREE) == 0)) {
            System.out.println("come in to BiThreadTree");
            return BiThreadTree_MODE;
        }
        if((curclassName.compareTo(CLTREE)==0)||(superclassName.compareTo(CLTREEADAPTER)==0)){
            System.out.println("come into CLTree");
            return CLTree_MODE;
        }
        if((superclassName.compareTo(LINEARHASHTABLEADAPTER)==0)||(curclassName.compareTo(LINEARHASHTABLE)==0)){
            System.out.println("come into LinearHashTable");
            return LinearHashTable_MODE ;
        }
        if((superclassName.compareTo(CSTREEADAPTER)==0)||(curclassName.compareTo(CSTREE)==0)){
            System.out.println("come into CSTree");
            return CSTree_MODE ;
        }
        if((superclassName.compareTo(CTREEADAPTER)==0)||(curclassName.compareTo(CTREE)==0)){
           System.out.println("come in to CTree");
           return CTree_MODE;
        }
        if((superclassName.compareTo(MGRAPHADAPTER)==0)||(curclassName.compareTo(MGRAPH)==0)){
           System.out.println("come in to MGraph");
           return MGraph_MODE;
        }
        if((superclassName.compareTo(VLISTADAPTER)==0)||(curclassName.compareTo(VLIST)==0)){
           System.out.println("come in to VList");
           return VList_MODE;
        }



         return 0;
     }
     
}