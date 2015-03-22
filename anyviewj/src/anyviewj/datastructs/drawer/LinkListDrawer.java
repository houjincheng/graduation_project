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
    private ArrayList<ObjectReference> Or;//zhang:(08-07)�洢���������ϵĽ�������
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
        LocalVarName = new ArrayList<String>();  //��žֲ������Ķ�̬���� load local variables
        LocalVarNode = new ArrayList<LNode>();   //��žֲ�������new���Ľ�㣨�����ڻ�ͼ�����滭���ֲ�������
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
    public Dimension getPreferredSize() {//wcb:��ʼ����ͼ���С
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

     private LNode buildCLinkList(ObjectReference or) /*����ѭ������*/
    {
        List<Field> fields = or.referenceType().allFields();
        StringReference Data_v = null;
        LNode prevNode = null;
        LNode headNode = null;
        LNode newNode = null;
        Value head_v = null;
        Value curr_v = null;
        Value next_v = null;
        head_v = this.invokeMethod1(or, "first");  //�õ�ͷ��������
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
        prevNode = headNode;  //����ͷ��� build head node
        headNode.prevflag = true;
        olMap.put((ObjectReference) head_v, prevNode);//wcb:����fist()�ڵ�
        curr_v = this.invokeMethod1((ObjectReference)curr_v, "next");  //invoke LNode method "next"
        while(curr_v != head_v && curr_v != null){
                Data_v = (StringReference)this.invokeMethod1((ObjectReference)curr_v, "getDataText");
                if(DubFlag == true){
                    next_v  = this.invokeMethod1((ObjectReference)curr_v, "next");  //��õ�ǰ������һ����������
                    newNode = new LNode(prevNode.next(),prevNode);
                    newNode.data = Data_v.value();
                    //�����½�� insert new node
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
                    //�����½�� insert new node
                    prevNode.setNext(newNode);
                    prevNode = prevNode.next();
                }

               olMap.put((ObjectReference)curr_v, prevNode);
               System.out.println("**********KJHKKK"+curr_v+"kkkkkkkkk"+prevNode);
                curr_v = this.invokeMethod1((ObjectReference)curr_v, "next");  //invoke LNode method "next"
        }
        return headNode;   //���ظ������ͷ���
    }


    private LNode buildLinkList(ObjectReference or, LNode preNode)
    {
        List<Field> fields = or.referenceType().allFields();
        Value curr_v = null;
        Value next_v = null;
        LNode headNode = null;
        Or.clear();
        curr_v = this.invokeMethod1(or, "first");  //�õ�ͷ��������
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

                if(preNode != null)   //�����½��
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
                    preNode = headNode = newNode;  //ͷ��㣨ֻ��ֵһ�Σ�
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

                if(preNode != null)   //�����½��
                {
                    preNode.setNext(newNode);
                    preNode = preNode.next();
                }
                else
                    preNode = headNode = newNode;  //ͷ��㣨ֻ��ֵһ�Σ�

                olMap.put((ObjectReference)curr_v, preNode);
                Or.add((ObjectReference)curr_v);
                curr_v = this.invokeMethod1((ObjectReference)curr_v, "next");  //invoke LNode method "next"
            }
         }
        
        return headNode;   //���ظ������ͷ���
    }

     //�������е�ֵȡ������������һ��̬������//
     public ArrayList SetData(LNode hNode) {
        int number = 1;
        LNode currNode = null;
        ArrayList arr = null;
        if(hNode!=null)////wcb20121116
        currNode = hNode.next();    //�൱��һ��ָ�������ͷ��ָ��
        while(currNode != null && currNode != hNode)     //�õ����ĸ���(�������ѭ����������)
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
	public void clearBeforeNbMap(){   //������е� preNlMap
        if(preNlMap != null && preNlMap.size() >= 0)
            this.preNlMap.clear();
    }
    @Override
	public void removeValueOfNbMap(String key) {
        if(nlMap != null && nlMap.size() >= 0)
            this.nlMap.remove(key);
    }
    //�÷����������⣬�ڶ�ο��ٵص���ִ��ʱ������ֿ�ָ���쳣
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

    private void CalculateLocal(LNode hNode, int x, int y){////���㵥����ֲ�����λ��
        int width = this.getWidth();
        int height = this.getHeight();
       LocalVarPos.x =width + nodesize*1/2;//new �Ľڵ�
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
    private void Calculation() {////����ѭ������λ��

        
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
        Location = new ArrayList<Dimension>(Data.size());  //��Ÿ�������λ�� nodes' position
        Radius = (int) (Data.size() * (circleRadius+nodesize)/Math.PI)*2;///////////////ֱ�� ////////////////////////////
        if (Radius < 50) {
            Radius = 50;
        }

        ARadius =  Radius +2*nodesize;//��ͷ 40;
        if (ARadius < 50) {
            ARadius = 50;
        }

        for (int cal = 0; cal < Data.size(); cal++) {
            tempX = (int) (Radius * Math.cos(tempAngle) + halfwidth+nodesize);////Բ��X
            tempY = (int) (halfheight - Radius * Math.sin(tempAngle)+nodesize);///Բ��Y

             if(tempX < halfwidth)   //�����ߵ����н�����δ�ŵ���̬��������
            {
                 Point temp = new Point(tempX, tempY);
                 LeftPoint.add(temp);
            }

            arrowTX = (int) ((ARadius) * Math.cos(tempAngle) + halfwidth);  //��ͷβ����λ��
            arrowTY = (int) (halfheight - (ARadius) * Math.sin(tempAngle));

            arrowHX = (int) ((ARadius-nodesize) * Math.cos(tempAngle) + halfwidth);   //��ͷ��ʼ��λ��
            arrowHY = (int) (halfheight - (ARadius-nodesize) * Math.sin(tempAngle));
//            arrowTX = (int) (3*nodesize * Math.cos(tempAngle) + tempX); //��ͷβ����λ��
//            arrowTY = (int) (tempY - 3*nodesize * Math.sin(tempAngle));
//
//            arrowHX = (int)(nodesize * Math.cos(tempAngle) + tempX);   //��ͷ��ʼ��λ��
//            arrowHY = (int) (tempY - nodesize * Math.sin(tempAngle));

            ntMap.put(new Point(tempX, tempY), new Point(arrowTX, arrowTY));    //����ӳ��
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
        pd = new LinkVariableDrawer(this,g2,parac,nodec,arrowc,nodesize,nlMap, preNlMap,vnMap);   //vnMap�ڻ����ʱ�õ�
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
            varH = 0;  //zhang:����ǽ�varHǿ������
            
            for(String name:nullPointName){  //zhang:����LNode e = null�����
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
            this.CalculateLocal(hNode,20, y);//�ڵ�λ��
            
            LNode curNode = hNode;
            for (int cal = 0; cal < DataNumber; cal++) {
                text = Data.get(cal);
                //wcb:2013.1.22
                if(curNode==null)
                    return;
                 
               
                drawLNode(g, text, curNode.local.width, curNode.local.height);
                if (cal < DataNumber - 1) {
                  
                   NodeDrawer.drawArrow(g, arrowc, curNode.local.width+nodesize*2, curNode.local.height+nodesize*1/2, 0, nodesize,false);
                    ///˫��
                    if(DubFlag == true && curNode.prevflag == true)
                        NodeDrawer.drawArrow(g, arrowc, curNode.local.width+nodesize*2, curNode.local.height+nodesize*1/2, 0, nodesize,true);
                     
                 }
                else if(cal== DataNumber-1&&curNode.local!=null){
                    NodeDrawer.NodeContent(g,parac, "��",curNode.local.width,curNode.local.height,nodesize,2);  //x+nodesize*1/8+temp, y, nodesize, 2);/////////////////////wcb
                    }
              
                curNode = curNode.next();
            }

        } ////ѭ������
          else if (Round == true) {

            LNode curNode = hNode;   
            Dimension first, next;
           
         
            Calculation();

            for (int cal = 0; cal < DataNumber && curNode != null; cal++) {
                //�����������λ��
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
                                          (int) (next.getHeight()-nodesize+nodesize*Math.sin(a)), false, 10, 15);////�ϲ��ֵļ�ͷ
//                    System.out.println("curNode.next().next().prev == curNode.next()-----"+(curNode.next().next().prev == curNode.next()));
                    if(DubFlag == true && curNode.prevflag == true)/////˫��
                         ComArrow.Com_Draw_Ptr(g,arrowc, (int) (next.getWidth()-nodesize-nodesize*Math.cos(a)),
                                          (int) (next.getHeight()-nodesize-nodesize*Math.sin(a)),
                                          (int) (first.getWidth()-nodesize+nodesize*Math.cos(a)),
                                          (int) (first.getHeight()-nodesize+nodesize*Math.sin(a)), true, 10, 15);
                    
                } else if (first.getWidth() < next.getWidth()) {
                    ComArrow.Com_Draw_Ptr(g,arrowc, (int) (first.getWidth()-nodesize+nodesize*Math.cos(a)),
                                          (int) (first.getHeight()-nodesize+nodesize*Math.sin(a)),
                                          (int) (next.getWidth()-nodesize-nodesize*Math.cos(a)),
                                          (int) (next.getHeight()-nodesize-nodesize*Math.sin(a)), false, 10, 15);/////�²��ּ�ͷ
                    if(DubFlag == true && curNode.prevflag == true)////˫��
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
    private Object data;   	// ����ʾ������Ԫ��
    private LNode next;        	// ָ��ֱ�Ӻ�̽���λ��
    private LNode prev;         //��̽���ָ�룬ѭ������ʹ��
    private boolean prevflag = false;   //���ĺ�̽���Ƿ���ǰ��ָ��ָ��ý��ı�ʶ��˫��������ʹ�ã�
    LNode(Object e, LNode nextNode)     	//������ ������1������Ԫ��
    { data = e;  next = nextNode; }
	LNode(LNode nextNode) { next = nextNode; } 	//������ ������2��δ����Ԫ��
    LNode(Object e, LNode n, LNode p)           //ѭ������ ������1������Ԫ��
    { data = e;  next = n; prev = p; }
	LNode(LNode n,LNode p)                    //ѭ������ ������2��δ����Ԫ��
    { next = n; prev = p; }

	LNode next() { return next; }  	// ����ֱ�Ӻ�̽���λ��
	LNode setNext(LNode nextNode) 	// ����ֱ�Ӻ�̽��
	{ return next = nextNode; }

    LNode prev() { return prev; }  	// ����ֱ�Ӻ�̽���λ��
    LNode setPrev( LNode prevNode ) 	// ����ֱ�Ӻ�̽��
    { return prev = prevNode; }

    public String getDataText() {
        return data.toString();
    }
  } 
}