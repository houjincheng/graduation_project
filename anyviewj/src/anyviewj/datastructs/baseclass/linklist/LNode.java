
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
    private Object data;   	// ����ʾ������Ԫ??
    public LNode next;        	// ָ��ֱ�Ӻ�̽���λ??
    public LNode(Object e, LNode nextNode)     	//lmk modified to public, for new a LNode in test
    { data = e;  next = nextNode; }
	public LNode(LNode nextNode) { next = nextNode; } 	// ��????��δ����Ԫ��
	public Object data() { return data; }	// ����Ԫ��??
    public Object setElem(Object e) 	// ��Ԫ��??
	{ return data = e; }
	public LNode next() { return next; }  	// ����ֱ�Ӻ�̽���λ??
    public LNode setNext(LNode nextNode) 	// ����ֱ�Ӻ�̽��
	{ return next = nextNode; }
    public final String getDataText() {   //final
        return data.toString();
    }
}
