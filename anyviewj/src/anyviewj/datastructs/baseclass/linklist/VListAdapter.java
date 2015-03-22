/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package anyviewj.datastructs.baseclass.linklist;

import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class VListAdapter extends Vector{

    private int curr; //指向当前的元素位置
    public VListAdapter(){
         super();
    }
    public VListAdapter(int elemNum, int inc){// 构造器2：指定容量、增量//
         super(elemNum,inc);
    }

    @Override
    public boolean isEmpty(){
       return true;
   }   // 返回true,若是空表//

    @Override
   public void clear() {// 删去栈中全部元素//

   }

   public int length() {
       return 0;
   }  // 返回顺序表的长度//

   /*在当前位置插入结点*/
   public void insert(Object e) {
   }

   public Object delete() {
       return null;
   }

    public boolean isInList() {
      return false;
    }

	public Object currElem() {     	// 返回当前元素
        return null;
	}

    public void first() {
    }

    public void next() {

    }

      public void setPos(int i) {
    }

      public Object getElem(VList L,int i){
          return null;
     }

        public String[] getElementString()
     {
        int  capacity = this.capacity();
         String elemString[] = new String[capacity];
         for(int i = 0; i<capacity;i++){
              if(elementData[i] != null) {
                 elemString[i] = elementData[i].toString();
              }
              else{
                 elemString[i] = "";
             }
        }
         return elemString;
     }
}
