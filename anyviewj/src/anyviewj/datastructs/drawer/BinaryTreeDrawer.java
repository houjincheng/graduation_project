package anyviewj.datastructs.drawer;


import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import anyviewj.console.ConsoleCenter;
import anyviewj.debug.manager.ContextManager;
import anyviewj.debug.session.Session;

import com.sun.jdi.StringReference;
import com.sun.jdi.Value;
import com.sun.jdi.Method;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.ThreadReference;

//import com.bluemarsh.jswat.core.session.SessionManager;
//import com.bluemarsh.jswat.core.session.SessionProvider;
//import com.bluemarsh.jswat.core.session.Session;
//import com.bluemarsh.jswat.core.context.ContextProvider;
//import com.bluemarsh.jswat.core.context.DebuggingContext;
import java.util.Set;

import javax.swing.SwingUtilities;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class BinaryTreeDrawer extends JPanel implements BaseDrawer<ObjectReference> {

    private List<BTree> nodes;//放置要画某个结点的引用
    private BTree btree;
    private int varH;
    private int DataNumber;
    private int Weight,  Hight; //设置总体的大小
    private int Xweight = 0,  Yhight = 0; //设置单位长度
    private int DEPT;
    private Font F;
    private double radius; //结点半径    
    private Map<ObjectReference, BTree> obMap;//引用对象到某个结点
    private Map<String, BTree> nbMap;//局部变量到对应结点的映射
    private Map<String, BTree> beforeNbMap = new HashMap<String, BTree>();//AlwaysMai:变空之前的局部变量到对应结点的映射
    private Map<String, Object> lvMap = new HashMap<String, Object>();///////null局部变量
    private ArrayList<String> nullPointName = new ArrayList();
    private BTVariableDrawer pd;//局部变量画图器
    private Color nodec;
    private Color parac;
    private Color arrowc;
    private int nodesize;

    public BinaryTreeDrawer() {
        btree = new BTree();
        obMap = new HashMap<ObjectReference, BTree>();
        nbMap = new HashMap<String, BTree>();//AlwaysMai
    }

    @Override
	public void clearNbMap() {
        if (nbMap != null && nbMap.size() >= 0) {
            this.nbMap.clear();
        }
        if (nullPointName != null && nullPointName.size() >= 0) {
            this.nullPointName.clear();
        }
    }

    @Override
	public void clearBeforeNbMap() {
        if (beforeNbMap != null && beforeNbMap.size() >= 0) {
            this.beforeNbMap.clear();
        }
    }

    @Override
	public void removeValueOfNbMap(String key) {
        if (nbMap != null && nbMap.size() >= 0) {
            this.nbMap.remove(key);
        }

    }

    @Override
	public void RecevieData(String name, ObjectReference or) {

        if (or != null) {
            nbMap.put(name, obMap.get(or));//AlwaysMai:添加进入nbMap记录指针位置的HashMap                                    
        } else {
            nullPointName.remove(name);
            nullPointName.add(name);

            return;
        }
    }

    //测试用的，遍历二叉树，可删除
    public void travelData(BTree btree) {
        if (btree != null) {
            System.out.print(btree.key + " ");
            travelData(btree.LeftChild);
            travelData(btree.RightChild);
        }
    }
    //接收新的二叉树信息

    @Override
	public void RecevieData(ObjectReference or) {
        Value root_v = null;
        root_v = this.invokeMethod1(or, "root");
        if (root_v == null) {
            return;
        }
        btree = buildBinaryTree(null, (ObjectReference) root_v, -1);//建立二叉树    
        DEPT = Depth(btree);
        Hight = DEPT * 6 * Yhight;
        SwingUtilities.invokeLater(new Runnable() {

            @Override
			public void run() {
                repaint();
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {//wcb:()初始化画图板大小
        return new Dimension(2000, 2000 + Hight);
    }

    public Value invokeMethod1(ObjectReference or, String methodName) {
//        SessionManager sm = SessionProvider.getSessionManager();
//        Session session = sm.getCurrent();
//        DebuggingContext dc = ContextProvider.getContext(session);
//        ThreadReference thread = dc.getThread();

    	Session session = ConsoleCenter.getCurrentSession();
		ContextManager conman = (ContextManager) session
				.getManager(ContextManager.class);
		ThreadReference thread = conman.getCurrentThread();
    	
        ReferenceType clazz = or.referenceType();
        List<String> argumentTypes = new ArrayList<String>();
        List<Value> arguments = new ArrayList<Value>();

        try {
            List<Method> methods = clazz.methodsByName(methodName);
            Value value = or.invokeMethod(thread, methods.get(0), arguments, 0);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int Depth(BTree bt) {
        int depL = 0, depR = 0;
        if (bt == null) {
            return 0;
        }
        depL = Depth(bt.LeftChild);
        depR = Depth(bt.RightChild);
        return depL > depR ? depL + 1 : depR + 1;

    }

    private BTree buildBinaryTree(BTree parent, ObjectReference or, int flag) {
        StringReference key_v = (StringReference) this.invokeMethod1(or, "getValueString");
        if (key_v == null) {//wcb:2012-8-13
            return null;
        }

//        or.referenceType().allMethods().get(0).name();

        Value left_v = null;
        left_v = this.invokeMethod1(or, "leftChild");

        Value right_v = null;
        right_v = this.invokeMethod1(or, "rightChild");

        BTree bt = new BTree();

        bt.key = key_v.value();

        obMap.put(or, bt);//建立映射关系

        if (parent != null) {
            if (flag == 0) {
                parent.LeftChild = bt;
                parent.LeftChild.LR = parent.LR.concat("L");//wcb
            } else {
                parent.RightChild = bt;
                parent.RightChild.LR = parent.LR.concat("R");//wcb
            }
        }//左右孩子标记
        if (left_v != null) {
            buildBinaryTree(bt, (ObjectReference) left_v, 0);
        }
        if (right_v != null) {
            buildBinaryTree(bt, (ObjectReference) right_v, 1);
        }
        return bt;
    }

    public void CalculateLocal(BTree p, int parentX, int parentY, int dept,
            boolean leftchild,
            boolean bleft, Dimension maxpos) {
        if (p == null) {
            return;
        }
        int wt = parentX + 2 * Xweight, ht = parentY + 2 * Yhight;
        if (bleft) {
            if (p.LeftChild == null) {
                p.local = new Dimension(parentX, ht); //最左结点位置
                if (maxpos.width < parentX) {
                    maxpos.width = parentX;
                }
                if (maxpos.height < parentY) {///////////////////////2012-8-1Wcb
                    maxpos.height = parentY;
                }
            } else {
                //定位左子树的位置
                CalculateLocal(p.LeftChild, parentX, ht, dept + 1, true, true,
                        maxpos);
                //定位自身位置               
                maxpos.width = maxpos.width + Xweight;//当前节点
                wt = maxpos.width;
                p.local = new Dimension(wt, ht);//确定位置
            }
        } else {
            //确定自身位置      
            //遍历左子树
            if (p.LeftChild != null) {
                CalculateLocal(p.LeftChild, parentX, ht, dept + 1, true, false,
                        maxpos);
                maxpos.width = maxpos.width + Xweight;
                maxpos.height = maxpos.height + Yhight;///////////////////////wei2012731
            } else {
                maxpos.width = parentX + Xweight;
            }
            p.local = new Dimension(maxpos.width, ht);
        }
        //遍历右子树      
        if (p.RightChild != null) {
            CalculateLocal(p.RightChild, p.local.width, p.local.height,
                    dept + 1, false, false, maxpos);
        }
    }

    @Override
    protected void paintChildren(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        CalculateLocal(btree, 50, 1, 1, true, true, new Dimension(50, 50));
        drawData(btree, g);////
        pd = new BTVariableDrawer(this, g2, arrowc, parac, nodesize, nbMap, beforeNbMap);
        pd.insertVariable();
        pd.createPsMap();
        pd.createbMap();//wcb 2012-7-25
        pd.drawVariable();
        varH = 0;  //zhang:这句是将varH强制置零
        for (String name : nullPointName) {  //zhang:画出LNode e = null的情况
            int x = 200 + btree.local.width + varH + 3 * nodesize;//2012-8-1 wcb
            int star_y = btree.local.height;
            NodeDrawer.NodeContent(g2, parac, "null", x - 16, star_y - 20 - nodesize, nodesize, 1);
            ComArrow.Com_Draw_Ptr(g, arrowc, x + nodesize * 1 / 4, star_y + nodesize, x + nodesize * 1 / 4, star_y - 20, false, 10, 15);
            NodeDrawer.NodeContent(g2, parac, name, x - name.length() / 2 * 8, star_y + nodesize, nodesize, 1);
            varH += 100;
        }
        int size = nbMap.size();
        if (size > 0) {
            Set<String> key = nbMap.keySet();
            String[] name = new String[size];
            key.toArray(name);
            int nullKeyNum = 0;
            for (int i = 0; i < size; i++) {
                if (nbMap.get(name[i]) != null) {
                    beforeNbMap.put(name[i], nbMap.get(name[i]));
                } else {
                }
            }

        }

    }

    private void drawData(BTree parent, Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        double a; //两个圆心间的距离的角度
        if (parent != null && parent.key != null) {
            DrawRegtage(parent.local, g2, parent);////////wcb 2012-7-23
            DrawKey(parent.key, parent.local, g2);
            if (parent.LeftChild != null) {
                a = Math.atan((parent.local.getHeight() -
                        parent.LeftChild.local.getHeight()) /
                        (parent.local.getWidth() -
                        parent.LeftChild.local.getWidth()));
                ComArrow.Com_Draw_Ptr(g2, arrowc,
                        (int) (parent.local.getWidth() + nodesize * 1 / 2),
                        (int) (parent.local.getHeight() + nodesize),
                        (int) (parent.LeftChild.local.getWidth() + nodesize * 7 / 4),///////////////201281
                        (int) (parent.LeftChild.local.getHeight() - nodesize * 1 / 4),
                        false, 10, 15);//左孩子

            }

            if (parent.LeftChild != null && parent.RightChild == null) {////wcb 2012-7-23
                NodeDrawer.RectDividedNode(g2, nodec, (int) (parent.local.getWidth()), (int) (parent.local.getHeight()),
                        nodesize, 3);
                DrawKey(parent.key, parent.local, g2);
                NodeDrawer.DividedNodeContent(g2, parac, "∧", (int) (parent.local.getWidth()), (int) (parent.local.getHeight()),
                        nodesize, 3);
                DrawRegtage(parent.LeftChild.local, g2, parent.LeftChild);
            }
            drawData(parent.LeftChild, g2);
            if (parent.RightChild != null) {
                a = Math.atan((parent.local.getHeight() -
                        parent.RightChild.local.getHeight()) /
                        (parent.local.getWidth() -
                        parent.RightChild.local.getWidth()));
                ComArrow.Com_Draw_Ptr(g2, arrowc,
                        (int) (parent.local.getWidth() + nodesize * 5 / 2),////////////////////
                        (int) (parent.local.getHeight() + nodesize),
                        (int) (parent.RightChild.local.getWidth() + nodesize * 7 / 4),
                        (int) (parent.RightChild.local.getHeight() - nodesize * 1 / 4),
                        false, 10, 15);
            }
            //右孩子
            if (parent.RightChild != null && parent.LeftChild == null) {///wcb 2012-7-23
                NodeDrawer.RectDividedNode(g2, nodec, (int) (parent.local.getWidth()), (int) (parent.local.getHeight()),
                        nodesize, 3);
                DrawKey(parent.key, parent.local, g2);
                NodeDrawer.DividedNodeContent(g2, parac, "∧", (int) (parent.local.getWidth()), (int) (parent.local.getHeight()),
                        nodesize, 1);
                DrawRegtage(parent.RightChild.local, g2, parent.RightChild);
            }
            drawData(parent.RightChild, g2);
        }
    }

    private void DrawRegtage(Dimension d, Graphics g, BTree bt) {//wcb 2012-7-23
        if (d == null && nodec == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g;
        if (nodec != null) {
            NodeDrawer.RectDividedNode(g2, nodec, (int) (d.getWidth()), (int) (d.getHeight()), nodesize, 3);
        }
        if (bt.LeftChild == null && bt.RightChild == null) {
            NodeDrawer.DividedNodeContent(g2, parac, "∧", (int) (d.getWidth()), (int) (d.getHeight()),
                    nodesize, 1);
            NodeDrawer.DividedNodeContent(g2, parac, "∧", (int) (d.getWidth()), (int) (d.getHeight()),
                    nodesize, 3);

        }
    }

    private void DrawKey(String key, Dimension d, Graphics g) {
        if (d == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g;
        NodeDrawer.DividedNodeContent(g2, parac, key, d.width, d.height, nodesize, 2);
    }

    @Override
	public void clearLocalVar() {
    }

    @Override
	public void GetChoice(Color para_color, Color node_color, Color arrow_color, int node_size) {
        if (para_color == null && node_color == null && arrow_color == null && node_size == 0) {
            return;
        }
        this.nodec = node_color;
        this.parac = para_color;
        this.arrowc = arrow_color;
        this.nodesize = node_size;
        this.Xweight = 3 * nodesize;
        this.Yhight = 2 * nodesize;
    }

    /**
     *
    构造内部类用于储存传入的二叉树数据
     */
    public class BTree { //AlwaysMai:原private修改为public

        BTree LeftChild;
        BTree RightChild;
        String key;
        String LR = ""; //AlwaysMai:为显示悬空指针设置，存储该结点为左孩子或者是右孩子
        Dimension local; //储存该结点在画图的位置
        ArrayList<String> VList = new ArrayList<String>();//AlwaysMai:存储变量名

        public BTree() {
        }

        public BTree(String key, BTree left, BTree right) {
            this.key = key;
            LeftChild = left;
            RightChild = right;
        }

        public String getKey() {
            return key;
        }

        public BTree LeftChild() {
            return LeftChild;
        }

        public BTree RightChild() {
            return RightChild;
        }

        public void addVList(String variableName) {
            if (VList.contains(variableName)) {
                return;
            }
            VList.add(variableName);
        }

        public String getLR() {
            return LR;
        }
    }
}

