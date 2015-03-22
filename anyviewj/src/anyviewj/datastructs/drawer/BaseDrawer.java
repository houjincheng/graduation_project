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

    void removeValueOfNbMap(String key);//AlwaysMai:添加方法，用以删除没用的指针
    void clearNbMap(); //AlwaysMai:添加方法，用以清空drawer的nbMap
    void clearBeforeNbMap();
    void clearLocalVar();
    void RecevieData(T data);
    void RecevieData(String name,T data);
    void GetChoice(Color para_color, Color node_color, Color arrow_color, int node_size);
}

