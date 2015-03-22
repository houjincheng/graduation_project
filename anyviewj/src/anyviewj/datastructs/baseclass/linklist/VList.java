/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package anyviewj.datastructs.baseclass.linklist;

import java.util.Vector;

import anyviewj.datastructs.baseclass.tree.CTNode;

/**
 *
 * @author Administrator
 */
public class VList extends Vector {
    public int curr;//��ǰλ��
    public int elemNum;//Ԫ�ظ���
  //  public CTNode vlist[];
    public VList(){
       super();
       elemNum = curr = 0;
    }
    public VList(int elemNum, int inc){// ������2��ָ������������//
        
       super(elemNum,inc);
       elemNum = curr = 0;
    }

    @Override
    public boolean isEmpty(){
       return elemNum == 0;
   }   // ����true,���ǿձ�//

    @Override
   public void clear() {  // ɾȥջ��ȫ��Ԫ��//
       elemNum = curr = 0 ;

       super.clear();
   }

   public int length() {
       return elemNum;
   }  // ����˳���ĳ���//

   /*�ڵ�ǰλ�ò�����*/
   public void insert(Object e) {
      ensureCapacity(elemNum+1);
      if(curr>=0 && curr<=elemNum){

         for(int i= elemNum;i>curr;i--){
            elementData[i] = elementData[i-1];
         }
          elementData[curr] = e;
        elemNum++;
      }
   //    System.out.println("VList curr varible is " + curr);
   }

   public Object delete() {
       if(!isEmpty() && isInList()){
          Object e = elementData[curr];  	// �ݴ汻ɾԪ��
  		  for(int i=curr; i<elemNum-1; i++){ 	// �ƶ�Ԫ���Ը��Ǳ�ɾԪ��
    		 elementData[i] = elementData[i+1];
          }
  		  elemNum--;                  	// ���ȼ�1
  		  return e;
       }
       return null;
   }
    public boolean isInList() {
        return ((curr >= 0) && (curr < elemNum));
    }

	public Object currElem() {     	// ���ص�ǰԪ�ص�ֵ
  		if(isInList()){
           return elementData[curr];
        }
        return null;
	}

    public void first() {
          curr = 0;
    }

    public void next() {
        curr++;
    }

      public void setPos(int i) {
         // L.setpos(i);
            curr = i;
    }
      
      public Object getElem(VList L,int i){
        L.setPos(i);
        if(L.isInList()){
          return L.currElem();
       }
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
   public CTNode getElem1(int i){
        if(isInList()){
      
           return (CTNode)elementData[i];
        }
        return null;
    

    }

    public CTNode[] toArray1(){
       CTNode[] vlist;
       vlist = new CTNode[elemNum];
      for(int i=0; i<elemNum; i++){
         vlist[i] = (CTNode) elementData[i];
      }
         return vlist;
    }




    public Node prev() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

  
    public void setElem(Object e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public void append(Object e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

     public Node curr() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   public boolean locate(Object e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

  /*  public void insert(Object e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
   */

   




}
