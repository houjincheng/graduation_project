/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package anyviewj.datastructs.drawer;

//import com.bluemarsh.jswat.datastruct.baseclass.List;
import anyviewj.console.ConsoleCenter;
import anyviewj.datastructs.baseclass.linklist.Node;
import anyviewj.debug.manager.ContextManager;
import anyviewj.debug.session.Session;
import anyviewj.interfaces.ui.panel.DSObjectReferenceChecker;

//import com.bluemarsh.jswat.datastruct.baseclass.linklist.Node;
//import com.bluemarsh.jswat.core.context.ContextProvider;
//import com.bluemarsh.jswat.core.context.DebuggingContext;
//import com.bluemarsh.jswat.core.session.Session;
//import com.bluemarsh.jswat.core.session.SessionManager;
//import com.bluemarsh.jswat.core.session.SessionProvider;


import java.util.List;

import com.sun.jdi.StringReference;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.Value;
import com.sun.jdi.Field;
import com.sun.jdi.InvalidTypeException;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.InvocationException;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.Method;

import java.util.ArrayList;
import javax.swing.JPanel;

import java.awt.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import javax.swing.SwingUtilities;

public class LinkListDrawer extends JPanel implements BaseDrawer<ObjectReference> {
    private ArrayList<String> Data;
    private ArrayList<ObjectReference> Or;//zhang:(08-07)存储所有链表上的结点的引用
    private LNode hNode;
    private int DataNumber;
    private boolean Round;
    private boolean DubFlag;
    private int Radius;
    private double circleRadius;
    private int ARadius;
    private Point LocalVarPos;   // the position of local variables
    private ArrayList<Point> LeftPoint;
    private ArrayList<String> LocalVarName;
    private ArrayList<LNode> LocalVarNode;
    private ArrayList<ObjectReference> LocalVarOr;
    private ArrayList<Dimension> Location;
    private int SQLength;
    private int LineLength;
    private int panelwidth;
    private int panelheight;
    private int SQHight;    
    private Font F;   
    private Map<ObjectReference,LNode> olMap;//Map from or to node
    private Map<LNode,String> vnMap;//Map from node to local variable's name
    private Map<String, LNode> nlMap;  //Map from variable's name to node
    private Map<String, LNode> preNlMap;   //previous Map from variable's name to node
    private Map<Point, Point> ntMap;  //corrow node position to tail position
    private Map<Point, Point> nhMap;  //corrow node position to head position
    private ArrayList<String> nullPointName ;
    private LinkVariableDrawer pd;
    private Color parac;
    private Color nodec;
    private Color arrowc;
    private Color variac;
    private int nodesize;
    private int varH;


    public LinkListDrawer() {
        
        LeftPoint = new ArrayList<Point>();
        LocalVarName = new ArrayList<String>();  //存放局部变量的动态链表 load local variables
        LocalVarNode = new ArrayList<LNode>();   //存放局部变量所new出的结点（用以在画图器上面画出局部变量）
        LocalVarOr = new ArrayList<ObjectReference>();
        Data = new ArrayList();
        Or = new ArrayList();
        circleRadius =17.5;
        SQLength = 64;
        SQHight = 48;
        LineLength = 30;
        panelwidth = 0;
        panelheight =0;
        LocalVarPos = new Point(0,0);
        F = new Font("Serif", Font.BOLD, 18);
        olMap = new HashMap<ObjectReference, LNode>();
        nlMap = new HashMap<String, LNode>();
        vnMap = new HashMap<LNode, String>();
        preNlMap = new HashMap<String, LNode>();
        ntMap = new HashMap<Point, Point>();  //map from arrow node's position to arrow tail's position
        nhMap = new HashMap<Point, Point>();  //map from arrow node's position to arrow head's position
        nullPointName =new ArrayList();
      
    }



    @Override
	public void RecevieData(ObjectReference or) {

        int DSType = DSObjectReferenceChecker.getObjectReferenceType(or);
        final int panelX = WIDTH;
        final int panelY = HEIGHT;
        switch (DSType)
        {
            case 5: Round = false; DubFlag = false;  hNode = buildLinkList(or,null); break;
            case 6: Round = true; DubFlag = false; hNode = buildCLinkList(or); break;
            case 7: Round = false; DubFlag = true; hNode = buildLinkList(or,null); break;
            case 8: Round = true; DubFlag = true; hNode = buildCLinkList(or); break;
            default: break;
        }
                final ArrayList arr = SetData(hNode);
        
            SwingUtilities.invokeLater(new Runnable() {

                @Override
				public void run() {
                  
                    Data = arr;
                    DataNumber = Data.size();
                  
                    repaint();
                }
            });
        } 
       @Override
    public Dimension getPreferredSize() {//wcb:初始化画图板大小
        return new Dimension(DataNumber * nodesize * 2 + 2000, DataNumber * nodesize * 2 + 2000);
}

   
    


    @Override
	public void RecevieData(String name, ObjectReference or) {
      
    
        if(or!=null){

             Set<ObjectReference> keys = olMap.keySet();            
             for(ObjectReference cout:keys){
                 vnMap.put(olMap.get(or), name);
                 nlMap.put(name,olMap.get(or));                               
             }
        }
    
        else
             {
                 nullPointName.remove(name);
                 nullPointName.add(name);
                 return;
             }
        
    }

     private LNode buildLocalNode(ObjectReference or){

      LNode localNode = null;
      StringReference Data_v = null;
      /*lmk : 07-27 invosionExpection*/
      //Data_v = (StringReference)invokeMethod1((ObjectReference)or, "getDataText");
      if(Data_v != null){ /*lmk modified 07-27*/
          if(DubFlag == true){
              localNode = new LNode(null);
              localNode.data = Data_v.value();
          }
          else{
              localNode = new LNode(null,null);
             
          }
      }
      return localNode;
    }

     private void buildLccalNode1(LNode node,ObjectReference or){
         StringReference data_v = (StringReference)this.invokeMethod1(or, "getDataText");
         if(data_v!=null){///wcb 2012922
         LNode currNode = new LNode(null);
         currNode.data = data_v.value();
         node.setNext(currNode);
         Value next_V = this.invokeMethod1(or, "next");
         if(next_V != null)
             this.buildLccalNode1(currNode, (ObjectReference)next_V);
         }
     }

     private LNode buildCLinkList(ObjectReference or) /*构造循环链表*/
    {
        List<Field> fields = or.referenceType().allFields();
        StringReference Data_v = null;
        LNode prevNode = null;
        LNode headNode = null;
        LNode newNode = null;
        Value head_v = null;
        Value curr_v = null;
        Value next_v = null;
        head_v = this.invokeMethod1(or, "first");  //得到头结点的引用
        curr_v = head_v;
        Data_v = (StringReference)this.invokeMethod1((ObjectReference)curr_v, "getDataText");
        if(Data_v==null)
            return null;
        if(DubFlag == true){
            headNode = new LNode(null,null);
            headNode.setNext(headNode);
            headNode.setPrev(headNode);
        }else{
            headNode = new LNode(null);
            headNode.setNext(headNode);
        }
        headNode.data = Data_v.value();
        prevNode = headNode;  //构造头结点 build head node
        headNode.prevflag = true;
        olMap.put((ObjectReference) head_v, prevNode);//wcb:加入fist()节点
        curr_v = this.invokeMethod1((ObjectReference)curr_v, "next");  //invoke LNode method "next"
        while(curr_v != head_v && curr_v != null){
                Data_v = (StringReference)this.invokeMethod1((ObjectReference)curr_v, "getDataText");
                if(DubFlag == true){
                    next_v  = this.invokeMethod1((ObjectReference)curr_v, "next");  //获得当前结点的下一个结点的引用
                    newNode = new LNode(prevNode.next(),prevNode);
                    newNode.data = Data_v.value();
                    //插入新结点 insert new node
                    prevNode.setNext(newNode);                  
                    if(next_v != null){
                        if(this.invokeMethod1((ObjectReference)next_v, "prev") == curr_v){
                           newNode.setPrev(prevNode);
                           newNode.prevflag = true;
                        }
                    }
                    prevNode = prevNode.next();
                }
                else{
                    newNode = new LNode(prevNode.next());
                    newNode.data = Data_v.value();
                    //插入新结点 insert new node
                    prevNode.setNext(newNode);
                    prevNode = prevNode.next();
                }

               olMap.put((ObjectReference)curr_v, prevNode);
               System.out.println("**********KJHKKK"+curr_v+"kkkkkkkkk"+prevNode);
                curr_v = this.invokeMethod1((ObjectReference)curr_v, "next");  //invoke LNode method "next"
        }
        return headNode;   //返回该链表的头结点
    }


    private LNode buildLinkList(ObjectReference or, LNode preNode)
    {
        List<Field> fields = or.referenceType().allFields();
        Value curr_v = null;
        Value next_v = null;
        LNode headNode = null;
        Or.clear();
        curr_v = this.invokeMethod1(or, "first");  //得到头结点的引用
        while(curr_v != null)
        {
            next_v = this.invokeMethod1((ObjectReference)curr_v, "next");
            StringReference Data_v = (StringReference)this.invokeMethod1((ObjectReference)curr_v, "getDataText");
            if(Data_v==null)
             return null;///////////////////////////
            else if(DubFlag == true){
                // LNode newNode = new LNode(Data_v.value().toString(),null);
                LNode newNode = new LNode(null,null);
                newNode.data = Data_v.value();

                if(preNode != null)   //插入新结点
                {
                    preNode.setNext(newNode);
                    if(next_v != null){
                        if(this.invokeMethod1((ObjectReference)next_v, "prev") == curr_v){
                           newNode.setPrev(preNode);
                           newNode.prevflag = true;
                        }
                    }

                    preNode = preNode.next();
                }
                else{
                    preNode = headNode = newNode;  //头结点（只赋值一次）
                    headNode.prevflag = true;
                }
                    

                olMap.put((ObjectReference)curr_v, preNode);
                Or.add((ObjectReference)curr_v);
                curr_v = this.invokeMethod1((ObjectReference)curr_v, "next");  //invoke LNode method "next"
            }
            else{
                // LNode newNode = new LNode(Data_v.value().toString(),null);
                LNode newNode = new LNode(null);
                newNode.data = Data_v.value();

                if(preNode != null)   //插入新结点
                {
                    preNode.setNext(newNode);
                    preNode = preNode.next();
                }
                else
                    preNode = headNode = newNode;  //头结点（只赋值一次）

                olMap.put((ObjectReference)curr_v, preNode);
                Or.add((ObjectReference)curr_v);
                curr_v = this.invokeMethod1((ObjectReference)curr_v, "next");  //invoke LNode method "next"
            }
         }
        
        return headNode;   //返回该链表的头结点
    }

     //将链表中的值取出来并储存在一动态数组中//
     public ArrayList SetData(LNode hNode) {
        int number = 1;
        LNode currNode = null;
        ArrayList arr = null;
        if(hNode!=null)////wcb20121116
        currNode = hNode.next();    //相当于一个指向链表表头的指针
        while(currNode != null && currNode != hNode)     //得到结点的个数(单链表和循环链表都适用)
        {    number++;   currNode = currNode.next();    }
        arr = new ArrayList(number);
        currNode = hNode;
        if(currNode==null)//wcb:(2013.1.24)
            return null;
        for (int cal = 0; cal < number; cal++) {
             arr.add(currNode.data.toString());
             currNode = currNode.next();
        }

        return arr;
    }

    @Override
	public void clearNbMap(){
        if(nlMap != null && nlMap.size() >= 0)
            this.nlMap.clear();
     if(nullPointName!=null&&nullPointName.size()>=0){
            this.nullPointName.clear();
        }
    }
    @Override
	public void clearLocalVar(){
        if(this.LocalVarName != null && this.LocalVarName.size() > 0)
            this.LocalVarName.clear();          //delete local variables' name
        if(this.LocalVarNode != null && this.LocalVarNode.size() > 0)
            this.LocalVarNode.clear();          //delete local variables' node
        if(this.LocalVarOr != null && this.LocalVarOr.size() > 0)
            this.LocalVarOr.clear();
        if(vnMap != null && vnMap.size() >= 0)
            this.vnMap.clear();
    }
    @Override
	public void clearBeforeNbMap(){   //清空所有的 preNlMap
        if(preNlMap != null && preNlMap.size() >= 0)
            this.preNlMap.clear();
    }
    @Override
	public void removeValueOfNbMap(String key) {
        if(nlMap != null && nlMap.size() >= 0)
            this.nlMap.remove(key);
    }
    //该方法存在问题，在多次快速地单步执行时，会出现空指针异常
     public Value invokeMethod1(ObjectReference or,String methodName){
        
//        SessionManager sm = SessionProvider.getSessionManager();
//        Session session = sm.getCurrent();
//        DebuggingContext dc = ContextProvider.getContext(session);
//        ThreadReference thread = dc.getThread();
        
        Session session = ConsoleCenter.getCurrentSession();
		ContextManager conman = (ContextManager) session
				.getManager(ContextManager.class);
		ThreadReference thread = conman.getCurrentThread();
		
        if(or==null)
            return null;
        ReferenceType clazz = or.referenceType();
        List<String> argumentTypes = new ArrayList<String>();
        List<Value> arguments = new ArrayList<Value>();
        try{
            List<Method> methods = clazz.methodsByName(methodName);
            Value value = or.invokeMethod(thread, methods.get(0), arguments, 0);
            return value;
        }catch(InvalidTypeException e){
            e.printStackTrace();
        }catch(ClassNotLoadedException e){
             e.printStackTrace();
        }catch(IncompatibleThreadStateException e){
            e.printStackTrace();
        }catch(InvocationException e){
            e.printStackTrace();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
       return null;
    }

    private void CalculateLocal(LNode hNode, int x, int y){////计算单链表局部变量位置
        int width = this.getWidth();
        int height = this.getHeight();
       LocalVarPos.x =width + nodesize*1/2;//new 的节点
       LocalVarPos.y =2 * height / 3;
        LNode curNode = hNode;
        while(curNode != null)
        {
              curNode.local = new Dimension(x, y);
              
              curNode = curNode.next();
              x= x + 17/4*nodesize;
        }
    }

     //used to draw a single linklist
     private void drawLNode(Graphics g, String text, int x, int y) {
        Graphics2D g2 = (Graphics2D) g;
        NodeDrawer.RectDividedNode(g2,nodec, x, y,nodesize , 2);
        NodeDrawer.NodeContent(g2,parac, text, x-nodesize*1/4, y, nodesize, 1);///////////////////////wcb 2012923
    }

    //used in the situation of round linklist
    private void Calculation() {////计算循环链表位置

        
        double angle, tempAngle;
        int halfwidth;
        int halfheight;
        int tempX, tempY, arrowTX, arrowTY, arrowHX, arrowHY;
        halfwidth = 200+DataNumber*nodesize*2;//this.getWidth() / 2;
        halfheight =80+DataNumber*nodesize+2*nodesize;//this.getnlHeight() / 2;
        LocalVarPos.x = halfwidth / 2;  //the position where local variable drew;
        LocalVarPos.y = halfheight;
        angle = Math.PI * 2 / Data.size();
        tempAngle = angle;
        Location = new ArrayList<Dimension>(Data.size());  //存放各个结点的位置 nodes' position
        Radius = (int) (Data.size() * (circleRadius+nodesize)/Math.PI)*2;///////////////直径 ////////////////////////////
        if (Radius < 50) {
            Radius = 50;
        }

        ARadius =  Radius +2*nodesize;//箭头 40;
        if (ARadius < 50) {
            ARadius = 50;
        }

        for (int cal = 0; cal < Data.size(); cal++) {
            tempX = (int) (Radius * Math.cos(tempAngle) + halfwidth+nodesize);////圆心X
            tempY = (int) (halfheight - Radius * Math.sin(tempAngle)+nodesize);///圆心Y

             if(tempX < halfwidth)   //将左半边的所有结点依次存放到动态链表里面
            {
                 Point temp = new Point(tempX, tempY);
                 LeftPoint.add(temp);
            }

            arrowTX = (int) ((ARadius) * Math.cos(tempAngle) + halfwidth);  //箭头尾部的位置
            arrowTY = (int) (halfheight - (ARadius) * Math.sin(tempAngle));

            arrowHX = (int) ((ARadius-nodesize) * Math.cos(tempAngle) + halfwidth);   //箭头开始的位置
            arrowHY = (int) (halfheight - (ARadius-nodesize) * Math.sin(tempAngle));
//            arrowTX = (int) (3*nodesize * Math.cos(tempAngle) + tempX); //箭头尾部的位置
//            arrowTY = (int) (tempY - 3*nodesize * Math.sin(tempAngle));
//
//            arrowHX = (int)(nodesize * Math.cos(tempAngle) + tempX);   //箭头开始的位置
//            arrowHY = (int) (tempY - nodesize * Math.sin(tempAngle));

            ntMap.put(new Point(tempX, tempY), new Point(arrowTX, arrowTY));    //构造映像
            nhMap.put(new Point(tempX, tempY), new Point(arrowHX, arrowHY));
           // nhMap.put(new Point(tempX, tempY), new Point(tempX, tempY));

            Location.add(new Dimension(tempX, tempY));

            tempAngle = tempAngle + angle;
        }
        
    }
    @Override
    protected void paintChildren(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        drawData(g2);
        pd = new LinkVariableDrawer(this,g2,parac,nodec,arrowc,nodesize,nlMap, preNlMap,vnMap);   //vnMap在画结点时用到
        pd.insertVariable();
        pd.createPsMap(Round);
        if(Round == true){
           
           pd.drawCListVar(ntMap,nhMap,LeftPoint,LocalVarPos);
        }
        else{
             
            pd.drawVariable(LocalVarPos);
            for(LNode node:LocalVarNode){
                pd.drawVariable1(g, node, vnMap.get(node));
            }
        }
            varH = 0;  //zhang:这句是将varH强制置零
            
            for(String name:nullPointName){  //zhang:画出LNode e = null的情况
                int x = 200 +varH;//2012-8-1 Sun_Wei
                int star_y = 80;//
                NodeDrawer.NodeContent(g2, parac, "null", x-16,star_y-20-nodesize, nodesize, 1);
//                g.drawString("null", x-16, star_y-20);
                ComArrow.Com_Draw_Ptr(g,arrowc, x+nodesize*1/4, star_y+nodesize, x+nodesize*1/4, star_y-20,false, 10, 15);
                NodeDrawer.NodeContent(g2, parac, name, x-name.length()/2*8,star_y+15+nodesize,nodesize, 1);
//                g.drawString(name, x-name.length()/2*8, star_y+15);
                varH+=100;
            }


        int size = nlMap.size();
        if(size>0){
            Set<String> key = nlMap.keySet();
            String[] name = new String[size];
            key.toArray(name);
            for(int i = 0; i < size; i++){
                if(nlMap.get(name[i]) != null){
                    preNlMap.put(name[i], nlMap.get(name[i]));
                }else{

                }
            }

        }
        repaint();
    }
    protected void drawData(Graphics2D g) {
       
        int x = 20;
        int y = 100+nodesize*25/8;//this.getHeight() / 3;
       
        String text;
        double a;
        double circleRadius1=nodesize*1/2;

        if (Round == false) {
            //draw variable
            if( hNode != null )
            this.CalculateLocal(hNode,20, y);//节点位置
            
            LNode curNode = hNode;
            for (int cal = 0; cal < DataNumber; cal++) {
                text = Data.get(cal);
                //wcb:2013.1.22
                if(curNode==null)
                    return;
                 
               
                drawLNode(g, text, curNode.local.width, curNode.local.height);
                if (cal < DataNumber - 1) {
                  
                   NodeDrawer.drawArrow(g, arrowc, curNode.local.width+nodesize*2, curNode.local.height+nodesize*1/2, 0, nodesize,false);
                    ///双向
                    if(DubFlag == true && curNode.prevflag == true)
                        NodeDrawer.drawArrow(g, arrowc, curNode.local.width+nodesize*2, curNode.local.height+nodesize*1/2, 0, nodesize,true);
                     
                 }
                else if(cal== DataNumber-1&&curNode.local!=null){
                    NodeDrawer.NodeContent(g,parac, "∧",curNode.local.width,curNode.local.height,nodesize,2);  //x+nodesize*1/8+temp, y, nodesize, 2);/////////////////////wcb
                    }
              
                curNode = curNode.next();
            }

        } ////循环链表
          else if (Round == true) {

            LNode curNode = hNode;   
            Dimension first, next;
           
         
            Calculation();

            for (int cal = 0; cal < DataNumber && curNode != null; cal++) {
                //储存各个结点的位置
                curNode.local = Location.get(cal);   
               
                NodeDrawer.ballNode(g, nodec,(int) (curNode.local.getWidth() - 2*nodesize),(int) (curNode.local.getHeight()-2* nodesize),nodesize);
              
                NodeDrawer.ballNodeContent(g, parac, Data.get(cal), (int) (curNode.local.getWidth()- 2*nodesize), (int) (curNode.local.getHeight()- 2*nodesize),
                        nodesize);
                 curNode = curNode.next();
            }
            
            curNode = hNode;
            
            for (int i = 0, j = 0; i < DataNumber && curNode != null; i++) {
                first = Location.get(i);  
             
                j = i + 1;
                if (j >= DataNumber) {
                    j = 0;
                }
                next = Location.get(j);
              a = Math.atan((first.getHeight() - next.getHeight()) /
                              (first.getWidth() - next.getWidth()));
              
                if (first.getWidth() > next.getWidth()) {
                  ComArrow.Com_Draw_Ptr(g,arrowc, (int) (first.getWidth()-nodesize-nodesize*Math.cos(a)),
                                          (int) (first.getHeight()-nodesize-nodesize*Math.sin(a)),
                                          (int) (next.getWidth()-nodesize+nodesize*Math.cos(a)),
                                          (int) (next.getHeight()-nodesize+nodesize*Math.sin(a)), false, 10, 15);////上部分的箭头
//                    System.out.println("curNode.next().next().prev == curNode.next()-----"+(curNode.next().next().prev == curNode.next()));
                    if(DubFlag == true && curNode.prevflag == true)/////双向
                         ComArrow.Com_Draw_Ptr(g,arrowc, (int) (next.getWidth()-nodesize-nodesize*Math.cos(a)),
                                          (int) (next.getHeight()-nodesize-nodesize*Math.sin(a)),
                                          (int) (first.getWidth()-nodesize+nodesize*Math.cos(a)),
                                          (int) (first.getHeight()-nodesize+nodesize*Math.sin(a)), true, 10, 15);
                    
                } else if (first.getWidth() < next.getWidth()) {
                    ComArrow.Com_Draw_Ptr(g,arrowc, (int) (first.getWidth()-nodesize+nodesize*Math.cos(a)),
                                          (int) (first.getHeight()-nodesize+nodesize*Math.sin(a)),
                                          (int) (next.getWidth()-nodesize-nodesize*Math.cos(a)),
                                          (int) (next.getHeight()-nodesize-nodesize*Math.sin(a)), false, 10, 15);/////下部分箭头
                    if(DubFlag == true && curNode.prevflag == true)////双向
                        ComArrow.Com_Draw_Ptr(g,arrowc, (int) (next.getWidth()-nodesize+nodesize*Math.cos(a)),
                                          (int) (next.getHeight()-nodesize+nodesize*Math.sin(a)),
                                          (int) (first.getWidth()-nodesize-nodesize*Math.cos(a)),
                                          (int) (first.getHeight()-nodesize-nodesize*Math.sin(a)),true, 10, 15);
                } else {
                     ComArrow.Com_Draw_Ptr(g,arrowc, (int) (first.getWidth()-nodesize),
                                          (int) (first.getHeight()-nodesize-nodesize*Math.sin(a)),
                                          (int) (next.getWidth()-nodesize),
                                          (int) (next.getHeight()-nodesize+nodesize*Math.sin(a)), false, 10, 15);
                    if(DubFlag == true && curNode.prevflag == true)
                         ComArrow.Com_Draw_Ptr(g,arrowc, (int) (next.getWidth()-nodesize),
                                          (int) (next.getHeight()-nodesize-nodesize*Math.sin(a)),(int) (first.getWidth()-nodesize),
                                          (int) (first.getHeight()-nodesize+nodesize*Math.sin(a)), true, 10, 15);
                } //end else
                curNode = curNode.next();
            }//end for
        }//end for
    }//end else

    @Override
	public void GetChoice(Color para_color, Color node_color, Color arrow_color, int node_size) {
       this.parac = para_color;
       this.nodec = node_color;
       this.arrowc = arrow_color;
       this.nodesize = node_size;
    }
   public class LNode extends Node{
    private Object data;   	// 结点表示的数据元素
    private LNode next;        	// 指向直接后继结点的位置
    private LNode prev;         //后继结点的指针，循环链表使用
    private boolean prevflag = false;   //结点的后继结点是否有前驱指针指向该结点的标识（双向链表中使用）
    LNode(Object e, LNode nextNode)     	//单链表 构造器1：给定元素
    { data = e;  next = nextNode; }
	LNode(LNode nextNode) { next = nextNode; } 	//单链表 构造器2：未给定元素
    LNode(Object e, LNode n, LNode p)           //循环链表 构造器1：给定元素
    { data = e;  next = n; prev = p; }
	LNode(LNode n,LNode p)                    //循环链表 构造器2：未给定元素
    { next = n; prev = p; }

	LNode next() { return next; }  	// 返回直接后继结点的位置
	LNode setNext(LNode nextNode) 	// 链接直接后继结点
	{ return next = nextNode; }

    LNode prev() { return prev; }  	// 返回直接后继结点的位置
    LNode setPrev( LNode prevNode ) 	// 链接直接后继结点
    { return prev = prevNode; }

    public String getDataText() {
        return data.toString();
    }
  } 
}