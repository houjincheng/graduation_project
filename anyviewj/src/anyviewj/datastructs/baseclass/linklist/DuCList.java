/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package anyviewj.datastructs.baseclass.linklist;

/**
 *
 * @author Administrator
 */
public class DuCList implements List {  //--- ���õ�����洢�ṹʵ�����Ա�
   private	DNode	head;  	// ��ͷָ��
   private 	DNode	tail;  	// ��βָ��
   protected	DNode	curr;  	// ��ǰԪ��λ��
   private 	int  elemNum;	// ����Ԫ�ظ���������??
   public DuCList() { init(); } 	// ��??�����ձ�
   private void init() {     	// ʵ�ʳ�ʼ??
       head = tail = curr = new DNode(" ",null,null); //build head node
       head.setNext(head);
       head.setPrev(head);
       elemNum = 0;
   }
   @Override
public void clear(){
       head.setNext(head);
       elemNum = 0;
   }
   @Override
public boolean isEmpty(){
       return elemNum == 0;
   }
   @Override
public int length(){
       return elemNum;
   }
   @Override
public boolean isInList(){
       if( curr != null )
          return true;
       else
          return false;
   }
   @Override
public DNode first(){
       curr = head;
       return head;
   }
   @Override
public Node prev() {
        return curr.prev();
   }
   @Override
public DNode next(){
      return curr.next();
   }

   @Override
public void setPos(int i){
        curr = head.next();
  		for(int j = 1;  curr != head && j < i;  j++)
    		curr = curr.next();
   }
   @Override
public void setElem(Object e){
       if( curr != null )
           curr.setElem(e);
   }
   @Override
public Object currElem(){
       if( curr != null )
           return curr.data();
       else
           return null;
   }
   @Override
public void insert(Object e){
       DNode p = new DNode(e, curr.next(), curr);
       curr.setNext(p);
  		if (curr.next().next() != null)
    		curr.next().next().setPrev(curr.next());
  		if (tail == curr)           // ���ڱ�β����
    		tail = curr.next();
   		elemNum++;
   }

   @Override
public void append(Object e){
       DNode cur = new DNode("S",null,null);
       DNode p = new DNode(e, curr.next(), curr);
       curr.setNext(p);
  		if (curr.next().next() != null)
    		curr.next().next().setPrev(curr.next());
  		if (tail == curr)          
    		tail = curr.next();
   		elemNum++;
   }
   @Override
public Object delete(){
       if (!isInList()) return null;
  		Object e = curr.next().data();  	// ��ס��ɾԪ��
  		if (curr.next().next() != null)
    		curr.next().next().setPrev(curr);
  		else tail = curr;  		// ��ɾ����βԪ?? ��tail
  		curr.setNext(curr.next().next());   	// ɾ��
   		elemNum--;
  		return e;                            	
   }
   @Override
public boolean locate(Object e){
       curr = head.next();
       while( curr != head ){
           if( curr.data().equals(e))
               break;
       }
       if( curr != null )
           return true;
       else
           return false;
   }

   @Override
public DNode curr(){
       return curr;
   }
    //add
    public final String getDataText() {
        return curr.getDataText();
    }

}

