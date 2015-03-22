/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package anyviewj.datastructs.baseclass.linklist;

import java.awt.Dimension;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class Node {
    public Dimension local; //储存该结点在画图的位置
    public ArrayList<String> VList = new ArrayList<String>();
    public void addVList(String variableName){
            if(VList.contains(variableName)) return;
            VList.add(variableName);
    }
}
