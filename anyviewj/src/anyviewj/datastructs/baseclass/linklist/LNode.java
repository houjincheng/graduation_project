
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package anyviewj.datastructs.baseclass.linklist;

/**
 *
 * @author Administrator
 */
public class LNode extends Node{
    private Object data;   	// 结点表示的数据元??
    public LNode next;        	// 指向直接后继结点的位??
    public LNode(Object e, LNode nextNode)     	//lmk modified to public, for new a LNode in test
    { data = e;  next = nextNode; }
	public LNode(LNode nextNode) { next = nextNode; } 	// 构????：未给定元素
	public Object data() { return data; }	// 返回元素??
    public Object setElem(Object e) 	// 置元素??
	{ return data = e; }
	public LNode next() { return next; }  	// 返回直接后继结点的位??
    public LNode setNext(LNode nextNode) 	// 链接直接后继结点
	{ return next = nextNode; }
    public final String getDataText() {   //final
        return data.toString();
    }
}
