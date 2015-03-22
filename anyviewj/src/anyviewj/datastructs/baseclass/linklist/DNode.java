/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package anyviewj.datastructs.baseclass.linklist;

/**
 *
 * @author Administrator
 */
public class DNode extends Node{
    private Object data;   	// 结点表示的数据元的值
    private DNode next;        	// 指向直接后继结点的位置//
    private DNode prev;
    DNode(Object e, DNode n, DNode p)
    { data = e;  next = n; prev = p; }
	DNode(DNode n,DNode p)
    { next = n; prev = p; }
	Object data() { return data; }
    Object setElem(Object e)
	{ return data = e; }
	DNode next() { return next; }  	// 返回直接后继结点的位置
    DNode setNext( DNode nextNode ) 	// 链接直接后继结点
	{ return next = nextNode; }
    DNode prev() { return prev; }  	// 返回直接后继结点的位置
    DNode setPrev( DNode prevNode ) 	// 链接直接后继结点
	{ return prev = prevNode; }
    public final String getDataText() {   //final
        return data.toString();
    }

}