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

    private int curr; //ָ��ǰ��Ԫ��λ��
    public VListAdapter(){
         super();
    }
    public VListAdapter(int elemNum, int inc){// ������2��ָ������������//
         super(elemNum,inc);
    }

    @Override
    public boolean isEmpty(){
       return true;
   }   // ����true,���ǿձ�//

    @Override
   public void clear() {// ɾȥջ��ȫ��Ԫ��//

   }

   public int length() {
       return 0;
   }  // ����˳���ĳ���//

   /*�ڵ�ǰλ�ò�����*/
   public void insert(Object e) {
   }

   public Object delete() {
       return null;
   }

    public boolean isInList() {
      return false;
    }

	public Object currElem() {     	// ���ص�ǰԪ��
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
