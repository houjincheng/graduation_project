/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package anyviewj.datastructs.baseclass.linklist;

/**
 *
 * @author Administrator
 */
public class CList implements List {  //--- 采用单链表存储结构实现线性表
   private	LNode	head;  	// 表头指针
   private 	LNode	tail;  	// 表尾指针
   protected	LNode	curr;  	// 当前元素位置
   private 	int  elemNum;	// 表中元素个数（表长度??
   public CList() { init(); } 	// 构??器：空表
   private void init() {     	// 实际初始??
       head = tail = curr = new LNode(" ",head); // 建立头结??
       elemNum = 0;
   }
   @Override
public void clear(){
       head.setNext(head);  //构??????空的循环链表
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
           if( p.next() != tail.next() && p.next() == curr )  //遍历整个循环链表
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
           p.setNext(curr.next());  //在当前结点的后一个位置上插入结点P
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