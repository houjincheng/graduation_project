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
    private Object data;   	// ����ʾ������Ԫ��ֵ
    private DNode next;        	// ָ��ֱ�Ӻ�̽���λ��//
    private DNode prev;
    DNode(Object e, DNode n, DNode p)
    { data = e;  next = n; prev = p; }
	DNode(DNode n,DNode p)
    { next = n; prev = p; }
	Object data() { return data; }
    Object setElem(Object e)
	{ return data = e; }
	DNode next() { return next; }  	// ����ֱ�Ӻ�̽���λ��
    DNode setNext( DNode nextNode ) 	// ����ֱ�Ӻ�̽��
	{ return next = nextNode; }
    DNode prev() { return prev; }  	// ����ֱ�Ӻ�̽���λ��
    DNode setPrev( DNode prevNode ) 	// ����ֱ�Ӻ�̽��
	{ return prev = prevNode; }
    public final String getDataText() {   //final
        return data.toString();
    }

}