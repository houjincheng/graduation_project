/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package anyviewj.datastructs.baseclass.linklist;

/**
 *
 * @author Administrator
 */
public class DuCList implements List {  //--- 采用单链表存储结构实现线性表
   private	DNode	head;  	// 表头指针
   private 	DNode	tail;  	// 表尾指针
   protected	DNode	curr;  	// 当前元素位置
   private 	int  elemNum;	// 表中元素个数（表长度??
   public DuCList() { init(); } 	// 构??器：空表
   private void init() {     	// 实际初始??
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
  		if (tail == curr)           // 是在表尾插入
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
  		Object e = curr.next().data();  	// 记住被删元素
  		if (curr.next().next() != null)
    		curr.next().next().setPrev(curr);
  		else tail = curr;  		// 是删除表尾元?? 置tail
  		curr.setNext(curr.next().next());   	// 删除
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

