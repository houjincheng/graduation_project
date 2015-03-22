/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package anyviewj.datastructs.baseclass.tree;

/**
 *
 * @author Administrator
 */
public class TreeNodeAdapter implements TreeNode{

    @Override
	public Object value() {
       return null;
    }


    @Override
	public boolean isLeaf() {
        return true;

    }

    @Override
	public int degree() {
        return 0;
    }

    @Override
	public TreeNode parent() {

        return null;
    }

    @Override
	public TreeNode firstChild() {
        return null;
    }

    @Override
	public void setFirstChild() {

    }

    @Override
	public TreeNode child(int i) {
        return null;
    }

    @Override
	public TreeNode rightSibling(TreeNode c) {
        return null;
    }

    @Override
	public void setRightSibling(TreeNode c) {

    }

    @Override
	public void insertChild(int i, TreeNode c) {

    }

    @Override
	public void deleteChild(int i) {

    }

    @Override
	public String getValueString() {
        return this.value().toString();
    }

    @Override
	public void setValue(Object i) {

    }

}
