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
    public Dimension local; //����ý���ڻ�ͼ��λ��
    public ArrayList<String> VList = new ArrayList<String>();
    public void addVList(String variableName){
            if(VList.contains(variableName)) return;
            VList.add(variableName);
    }
}
