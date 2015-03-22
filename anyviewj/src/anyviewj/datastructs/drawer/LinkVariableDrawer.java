/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package anyviewj.datastructs.drawer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import anyviewj.datastructs.baseclass.linklist.Node;
import anyviewj.datastructs.drawer.LinkListDrawer.LNode;

public class LinkVariableDrawer {
    BaseDrawer bd = null;
    Graphics2D g = null;
    private int halfPicHeight;
    private HashMap<String, Node> nlMap = null;  //receive the map of name to LNode
    private HashMap<LNode,String> vnMap;
    private HashMap<String, LNode> preNlMap = null;  //receive the previous map of name to LNode
    private HashMap<Point, String> psMap;  //map from position to local variable
    private HashMap<Point, Point> ntMap;
    private HashMap<Point, Point> nhMap;
    private ArrayList<Point> LeftPoint;   //�ö�̬�����д��ѭ�������л������ߵĽ��
    private ArrayList<String> LocalVarName;
    private String nullVarName;
    private String tailString;
    private Point LocalVarPos;
    private LNode node;  //����ָ��ӷǿձ�Ϊ�յľֲ�����
    private boolean Round;
    private int top;
    private int lineLength=33;
    private Color parac;
    private Color nodec;
    private Color arrowc;
    private int nodesize;
    public LinkVariableDrawer(BaseDrawer bd, Graphics2D g,Color paracc,Color nodecc,Color arrowcc,int nodesizee, Map<String, ? extends Node> nlMap, Map<String, LNode> preNlMap, Map<LNode,String> vnMap){
        this.bd = bd;
        this.g = g;
        this.parac=paracc;
        this.nodec = nodecc;
        this.arrowc = arrowcc;
        this.nodesize = nodesizee;
        this.nlMap = (HashMap)nlMap;
        this.preNlMap = (HashMap)preNlMap;
        this.vnMap = (HashMap)vnMap;
        psMap = new HashMap<Point, String>();
        ntMap = new HashMap<Point, Point>();
        nhMap = new HashMap<Point, Point>();
        LeftPoint = new ArrayList<Point>();
        LocalVarName = new ArrayList<String>();
        LocalVarPos = new Point(0,0);
        node = null;
        nullVarName = "";
        tailString = "";
        halfPicHeight = 17;
    }


    public LinkVariableDrawer(BaseDrawer bd, Graphics2D g, Map<String, ? extends Node> nlMap, int top){   //���к�ջ�Ĺ�����//lmk
        this.bd = bd;
        this.g = g;
        this.nlMap = (HashMap)nlMap;
        this.top = top;
        psMap = new HashMap<Point, String>();
        ntMap = new HashMap<Point, Point>();
        nhMap = new HashMap<Point, Point>();
        LeftPoint = new ArrayList<Point>();
        LocalVarName = new ArrayList<String>();
        LocalVarPos = new Point(0,0);
        node = null;
        nullVarName = "";
        tailString = "";
        halfPicHeight = 17;
    }

    public void insertVariable(){
        int size = this.nlMap.size();
        if(size<=0) return;
        Set<String> key = nlMap.keySet();
        String[] name = new String[size];
        key.toArray(name);
        for(int i = 0; i < size; i++)
        {
              if(nlMap.get(name[i]) != null)
              {
                  nlMap.get(name[i]).addVList(name[i]);
              }
              LocalVarName.add(name[i]);
        }
    }

    public void createPsMap(boolean Round){
        int temp = 0;
        this.Round = Round;
        boolean pointTFlag = false;
        int size = this.nlMap.size();
        if(size<=0) return;
        Set<String> key = nlMap.keySet();
        String[] name = new String[size];
        key.toArray(name);
        for(int i = 0; i < size; i++){
            if(nlMap.get(name[i]) != null){
                nlMap.get(name[i]).VList.trimToSize();
                int length = nlMap.get(name[i]).VList.size();
                String nameString = "";
                for(int j = 0; j < length; j++){
//                    System.out.println("&&&&&&&&&&&&&&*****************#######################");
                    nameString = nameString.concat(nlMap.get(name[i]).VList.get(j) + ",");
                }
                if(nameString.length() > 0){
                    nameString = nameString.substring(0, nameString.length()-1);
                    if(nlMap.get(name[i]).local!=null){
                    ///��ֱ�ֲ���ͷ
                    int x = (int)nlMap.get(name[i]).local.getWidth()+nodesize*5/4;
                    int y = (int)nlMap.get(name[i]).local.getHeight()+nodesize*1/8;
                    psMap.put(new Point(x, y), nameString);
                  //  temp+=lineLength;
                    }
                }
            }else{
                pointTFlag = true;
                node = preNlMap.get(name[i]);
                if(node != null){        
                    tailString = tailString.concat(name[i]+",");
                }
            }
            if(node!= null && !Round && pointTFlag == true){   //single linklist
                if(!tailString.isEmpty()){
                    pointTFlag = false;
                    tailString = tailString.substring(0, tailString.length()-1);
                    psMap.put(new Point((int)node.local.getWidth(),(int)node.local.getHeight()), tailString);///////x+90
                    tailString = tailString.concat(",");
                }
             }
         }     
    }

    public void drawVariable(Color variac,Color arrowc ,int nodesize){       //������//
        int size = this.psMap.size();
        if(size <= 0)
                return;
        Set<Point> key = psMap.keySet();
        Point[] point = new Point[size];
        key.toArray(point);

       Color c = g.getColor();
       Font F = g.getFont();
        g.setFont(new Font("Monospaced", Font.BOLD, nodesize*2/3));
       // g.setColor(new Color(75,160,240));
       // g.setColor(variac);

        for(int i = 0; i < size; i++){

            g.fillOval(point[i].x + 95, point[i].y + 13, 5, 5);    // draw points
            g.drawString(psMap.get(point[i]), point[i].x + 105, point[i].y + 22);
            NodeDrawer.drawArrow(g, arrowc,  point[i].x + 96,point[i].y + 15,  270, nodesize, false);
          /*  ComArrow.Com_Draw_Ptr(g,arrowc, point[i].x + 96, point[i].y + 15,      //draw arrows
                                     point[i].x + 66, point[i].y + 15, false, 10, 15);*/
            nullVarName = this.colNullPointVar(psMap.get(point[i]));

        }
        //g.setFont(F);
      //  g.setColor(c);
    }

    public void drawVariable(Point LocalVarPos){   //������//
        int temp=0;
        this.LocalVarPos = LocalVarPos;

        int size = this.psMap.size();
        if(size <= 0)
            if(LocalVarName.size() != 0)    //����ָ��Ϊ�յľֲ�����
                nullVarName = this.colNullPointVar(" ");   //���ֲ��������Ӵ�����nullVarName��
            else
                return;

        Set<Point> key = psMap.keySet();
        Point[] point = new Point[size];
        key.toArray(point);

    

        for(int i = 0; i < size; i++){

            //g.fillOval(point[i].x+ 2*nodesize+temp, point[i].y-nodesize*1/5, 5, 5);    // draw points
           NodeDrawer.NodeContent(g, parac,psMap.get(point[i]),point[i].x-nodesize+temp, point[i].y - nodesize*13/4,nodesize,2);
           NodeDrawer.drawArrow(g,arrowc,point[i].x +temp, point[i].y-nodesize*9/4, 270, nodesize, false);
          // temp+=nodesize*17/4;
            nullVarName = this.colNullPointVar(psMap.get(point[i]));

        }
        if(!nullVarName.equals(""))   //���еĿ�ָ��ֲ����������ִ�
        {
            this.insertElem(nullVarName);
        }
     
    }
    public void drawVariable1(Graphics g,LNode node,String name){
        Set<Point> keys = psMap.keySet();
        int Maxheight = 0;
        for(Point key : keys){
            if(key.y > Maxheight){
                Maxheight = key.y;
            }
        }
        LNode f = node;
        int c = 0;
    }



    public void drawCListVar(Map<Point, Point> ntMap, Map<Point, Point> nhMap,
                               ArrayList<Point> LeftPoint,
                               Point LocalVarPos){/////////////////ѭ������

        this.ntMap =(HashMap) ntMap;   //the same size
        this.nhMap =(HashMap) nhMap;
        this.LeftPoint = LeftPoint;
        this.LocalVarPos = LocalVarPos;

        int varSize = this.psMap.size();
        if(varSize <= 0)
            if(LocalVarName.size() == 0)
                return;
            else
                nullVarName = this.colNullPointVar(" ");

        Set<Point> key = psMap.keySet();
        Point[] point = new Point[varSize];
        key.toArray(point);

       
     
        
        for(int i = 0; i < varSize; i++){
          
           if(LeftPoint.contains(point[i]))
               NodeDrawer.NodeContent(g, parac, psMap.get(point[i]),ntMap.get(point[i]).x, ntMap.get(point[i]).y, nodesize, 2);//draw strings
              //  g.drawString(psMap.get(point[i]), ntMap.get(point[i]).x + halfPicHeight - (psMap.get(point[i]).length() * 10),
                                               //   ntMap.get(point[i]).y + halfPicHeight);
            else
              
                NodeDrawer.NodeContent(g, parac,psMap.get(point[i]), ntMap.get(point[i]).x+nodesize*4, ntMap.get(point[i]).y+nodesize*1/2,
                         nodesize,2);
            
               //  g.fillOval(ntMap.get(point[i]).x + halfPicHeight ,
                    //   ntMap.get(point[i]).y + halfPicHeight , 2, 2);    // draw points
                 NodeDrawer.drawArrow(g, arrowc, ntMap.get(point[i]).x+nodesize*4, ntMap.get(point[i]).y+nodesize, 180, nodesize, false);
         

            nullVarName = this.colNullPointVar(psMap.get(point[i]));
            
        }
        if(!nullVarName.equals(""))
        {
            this.insertElem(nullVarName);
        }

    
    }
    
    public String colNullPointVar(String s)   //�����еľֲ������Ķ�̬�����������޳����еķǿվֲ�����
    {
        String subString = "";
        String[] ss = s.split(",");
        for(int i=0; i<ss.length; i++)
            LocalVarName.remove(ss[i]);
        int length = LocalVarName.size();

        for(int j = 0; j < length; j++){
             subString = subString.concat(LocalVarName.get(j) + ",");
        }
        if(subString.length() > 0)   //delete the last word
            subString = subString.substring(0, subString.length()-1);
        
        return subString;
    }
    
    
     public void insertElem(String s){
        Set<LNode> set = vnMap.keySet();
        int size = set.size();
        LNode[] lNode = new LNode[size];
        set.toArray(lNode);
        this.vnMap = vnMap;
        
        for(int i = 0; i < size && lNode[i]!= null; i++){
            if(s.indexOf(vnMap.get(lNode[i])) >= 0)
              this.drawLNode(g,lNode[i].getDataText(), vnMap.get(lNode[i]), 20, LocalVarPos.y + (50+nodesize) * i);
        }
    }

     private void drawLNode(Graphics2D g,String text, String ptext, int x, int y) {

      
        if(Round != true){
          
            NodeDrawer.RectNode(g, nodec, (x),(y),nodesize,2);
            
            NodeDrawer.NodeContent(g,parac,text,(x), (y), nodesize,2);

           
            NodeDrawer.drawArrow(g, arrowc,x+nodesize*17/4,y+nodesize*1/2 ,180, nodesize, false);
            NodeDrawer.NodeContent(g, parac, ptext, x+nodesize*17/4, y, nodesize, 1);
        
        }else{
           
            NodeDrawer.ballNode(g,nodec,(x ),(y),nodesize);
          
            NodeDrawer.ballNodeContent(g, parac, text,(x), (y),nodesize);
          
            NodeDrawer.drawArrow(g, arrowc, x + nodesize*4, y+nodesize, 180, nodesize, false);
          
           
            NodeDrawer.NodeContent(g, parac, ptext, x + nodesize*4,  y+nodesize*1/2, nodesize, 2);
        }
    
    }
}

