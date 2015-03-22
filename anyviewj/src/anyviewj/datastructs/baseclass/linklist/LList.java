
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package anyviewj.datastructs.baseclass.linklist;
/**
 *
 * @author Administrator
 */
public class LList implements List {  //--- 采用单链表存储结构实现线性表
   private	LNode	head;  	// 表头指针
   private 	LNode	tail;  	// 表尾指针
   protected	LNode	curr;  	// 当前元素位置
   private 	int  elemNum;	// 表中元素个数
   public LList() { init(); } 	// 构??器：空表
   private void init() {     	
       head = tail = curr = new LNode(" ",null);
       elemNum = 0;
   }
   @Override
public void clear(){
       head.setNext(null);
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
public LNode first(){
       curr = head;
       return head;
   }
   @Override
public LNode prev(){
       LNode p = head.next();
       while( p != null ){
           if( p.next() != null && p.next() == curr )
           {  curr = p ;     }
       }
       return curr;
   }
   @Override
public void setPos(int i){
        curr = head.next();
  		for(int j = 1;  curr != null && j < i;  j++)
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
       LNode test = new LNode(null);
       LNode hp = new LNode(null);
       LNode cur = new LNode(null);
       LNode p = new LNode(e, null);
       if( curr != null ){
           p.setNext(curr.next());
           curr.setNext(p);
           if( curr == tail )
               tail = p;
           elemNum++;
       }
   }

   @Override
public void append(Object e){
       LNode pp = null;
       LNode cur = new LNode("S",null);
       LNode p = new LNode(e, null);
       pp = p;
       tail.setNext(p);
       tail = p;
       elemNum++;
   }
   @Override
public Object delete(){
       if( curr != null ){
           elemNum--;
           return curr.data();
       }
       else
           return null;
   }
   @Override
public boolean locate(Object e){
       curr = head.next();
       while( curr != null ){
           if( curr.data().equals(e))
               break;
       }
       if( curr != null )
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


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//package com.bluemarsh.jswat.datastruct.baseclass;
/**
 *
 * @author Administrator
 */
/*
public class LList implements List {  //--- 采用单链表存储结构实现线性表
   private	LNode	head;  	// 表头指针
   private 	LNode	tail;  	// 表尾指针
   protected	LNode	curr;  	// 当前元素位置
   private 	int  elemNum;	// 表中元素个数（表长度??
   public LList() { init(); } 	// 构??器：空表
   private void init() {     	// 实际初始??       head = tail = curr = new LNode(null); // 建立头结??       elemNum = 0;
   }
   public void clear(){
       head.setNext(null);
       elemNum = 0;
   }
   public boolean isEmpty(){
       if(elemNum == 0)
         return true ;
       else
         return false;
   }
   public int length(){
       return elemNum;
   }
   public boolean isInList(){
       if( curr != null )
          return true;
       else
          return false;
   }
   public void first(){
       curr = head.next();
   }
   public void next(){
       curr = curr.next();
   }
   public void prev(){
       LNode p = head.next();
       while( p != null ){
           if( p.next() != null && p.next() == curr )
           {  curr = p ;  return;   }
       }
   }
   public void setPos(int i){
        int j;
        curr = head;
  		for(j=0;  curr!=null && j<i;  ++j)
    		curr = curr.next();
   }
   public void setElem(Object e){
       if( curr != null )
           curr.setElem(e);
   }
   public Object currElem(){
       if( curr != null )
           return curr.data();
       else
           return null;
   }
   public void insert(Object e){
       //Assert.notNull(curr, "No current element");
		curr.setNext(new LNode(e, curr.next()));
  		if (tail == curr)           // 若是在表尾插??    		tail = curr.next();
		elemNum++;
   }

   public void append(Object e){
       LNode p = new LNode(e, null);
       tail.setNext(p);
       tail = p;
       elemNum++;
   }
   public Object delete(){
       if( curr != null ){
           elemNum--;
           return curr.data();
       }
       else
           return null;
   }
   public boolean locate(Object e){
       curr = head.next();
       while( curr != null ){
           if( curr.data().equals(e))
               break;
       }
       if( curr != null )
           return true;
       else
           return false;
   }
}
*/
