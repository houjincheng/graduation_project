/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package anyviewj.datastructs.baseclass.tree;

/**
 *
 * @author Administrator
 */
public interface TreeNode {
        Object value();
        void setValue(Object i);

        boolean isLeaf();
        int degree();
        TreeNode parent();
        TreeNode firstChild();
        void setFirstChild();
        TreeNode child(int i);
        TreeNode rightSibling(TreeNode c);
        void setRightSibling(TreeNode c);
        void insertChild(int i, TreeNode c);
        void deleteChild(int i);
        String getValueString();
}
