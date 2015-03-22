/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package anyviewj.datastructs.baseclass.linklist;

import anyviewj.datastructs.baseclass.BaseDataStruct;

/**
 *
 * @author Administrator
 */
public interface List extends BaseDataStruct{
   void clear();
   boolean isEmpty();
   int length();
   boolean isInList();
   Node first();  //modify return
   Node next();
   Node prev();
   void setPos(int i);
   void setElem(Object e);
   Object currElem();
   void insert(Object e);
   void append(Object e);
   Object delete();
   boolean locate(Object e);
   Node curr();
 }

