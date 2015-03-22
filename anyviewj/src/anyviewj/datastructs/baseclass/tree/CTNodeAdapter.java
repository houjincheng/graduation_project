/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package anyviewj.datastructs.baseclass.tree;

import anyviewj.datastructs.baseclass.linklist.LList;

/**
 *
 * @author talentsun
 */
public class CTNodeAdapter extends TreeNodeAdapter{
     public Object data;
     public LList children= new LList();///º¢×ÓÁ´±í

    @Override
    public Object value(){
      return null;
   }
    @Override
    public boolean isLeaf(){
      return false;
    }

    @Override
    public void setValue(Object v){

    }
      @Override
    public CTNode firstChild(){
      return null;
    }
    public CTNode firstChild(CTNode child){
      return null;
    }
    public String getDataString(){
    return data.toString();
    }
    public LList getList(){
     return children;
}
}
