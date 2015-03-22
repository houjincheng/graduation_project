/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package anyviewj.datastructs.baseclass.linklist;

/**
 *
 * @author Administrator
 */
public class CList implements List {  //--- ���õ�����洢�ṹʵ�����Ա�
   private	LNode	head;  	// ��ͷָ��
   private 	LNode	tail;  	// ��βָ��
   protected	LNode	curr;  	// ��ǰԪ��λ��
   private 	int  elemNum;	// ����Ԫ�ظ���������??
   public CList() { init(); } 	// ��??�����ձ�
   private void init() {     	// ʵ�ʳ�ʼ??
       head = tail = curr = new LNode(" ",head); // ����ͷ��??
       elemNum = 0;
   }
   @Override
public void clear(){
       head.setNext(head);  //��??????�յ�ѭ������
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
       if( curr != tail.next() )
          return true;
       else
          return false;
   }
   @Override
public LNode first(){
       curr = head;
       return head;
   }
   @Override
public LNode prev(){
       LNode p = head.next();
       while( p != tail.next() ){
           if( p.next() != tail.next() && p.next() == curr )  //��������ѭ������
           {  curr = p ;     }
       }
       return curr;
   }
   @Override
public void setPos(int i){
        curr = head.next();
  		for(int j = 1;  curr != tail.next() && j < i;  j++)
    		curr = curr.next();
   }
   @Override
public void setElem(Object e){
       if( curr != tail.next() )
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
       LNode p = new LNode(e, null);
//       if( curr != tail.next() ){
           p.setNext(curr.next());  //�ڵ�ǰ���ĺ�һ��λ���ϲ�����P
           curr.setNext(p);
           if( curr == tail )
               tail = p;
           elemNum++;
//       }
   }

   @Override
public void append(Object e){
       LNode cur = new LNode("S", null);
       LNode p = new LNode(e, null);
//       LNode nup = null;
       cur = head;
       p.setNext(tail.next());
       tail.setNext(p);
       while(cur.next() != null)
       {
           cur = cur.next();
       }      
       tail = p;
       LNode pp = new LNode("Q", p);
       elemNum++;
       
   }
   @Override
public Object delete(){
       if( curr != tail.next() ){
           elemNum--;
           return curr.data();
       }
       else
           return null;
   }
   @Override
public boolean locate(Object e){
       curr = head.next();
       while( curr != tail.next() ){
           if( curr.data().equals(e))
               break;
       }
       if( curr != tail.next() )
           return true;
       else
           return false;
   }
   //modified
   @Override
public LNode next(){
      return curr.next();
   }
   //add
    @Override
	public LNode curr(){
       return curr;
    }
    //add
    public final String getDataText() {
        return curr.getDataText();
    }
}