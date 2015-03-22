/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package anyviewj.datastructs.baseclass.tree;

import anyviewj.datastructs.baseclass.linklist.LList;

//import com.bluemarsh.jswat.datastruct.baseclass.tree.LList;




/**
 *
 * @author Administrator
 */
public class CTNode extends CTNodeAdapter{
  
    public Object data;
  
  
    public CTNode firstChild;
    public LList children= new LList();///º¢×ÓÁ´±í
    public CTNode(Object d){
      this.data=d;
      children= new LList();
   }
    public CTNode(Object d ,LList c){
       this.data=d;this.children=c;
   }
    @Override
    public Object value(){
      return data;
   }
    @Override
    public boolean isLeaf(){
      return children== null;
    }
    @Override
    public void setValue(Object v){
      this.data = v;
    }
   /* public CTNode  root(){
      return root;
     }
    public CTNode root(CTNode r){
      return this.root=r;
     }*/
    @Override
    public CTNode firstChild(){
      return this.firstChild;
    }
    @Override
	public CTNode firstChild(CTNode child){
      return this.firstChild=child;
    }
    @Override
	public String getDataString(){
    return data.toString();
    }
    @Override
	public LList getList(){
     return children;
}
 /*    public String getDataText(){
     return children.currElem().toString();
    }
  */
}
