/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package anyviewj.datastructs.drawer;

import java.awt.Color;

/**
 *
 * @author lenovo
 */
public interface BaseDrawer<T> {

    void removeValueOfNbMap(String key);//AlwaysMai:��ӷ���������ɾ��û�õ�ָ��
    void clearNbMap(); //AlwaysMai:��ӷ������������drawer��nbMap
    void clearBeforeNbMap();
    void clearLocalVar();
    void RecevieData(T data);
    void RecevieData(String name,T data);
    void GetChoice(Color para_color, Color node_color, Color arrow_color, int node_size);
}

