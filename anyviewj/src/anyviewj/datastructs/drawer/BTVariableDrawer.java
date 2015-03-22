package anyviewj.datastructs.drawer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



//import com.bluemarsh.jswat.datastruct.drawer.BinaryTreeDrawer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import anyviewj.datastructs.drawer.BinaryTreeDrawer.BTree;

/**
 *
 * @author AlwaysMai && wcb
 */
public class BTVariableDrawer {//AlwaysMai:���ڻ��ֲ�������ͨ����
    BaseDrawer bd = null;
    Graphics2D g = null;
    HashMap<String, BTree> nbMap = null;//�����������õ�ӳ��
    HashMap<String, BTree> beforeNbMap = null;
    HashMap<Point, String> psMap = new HashMap<Point, String>();//����λ�õ���������ӳ��
    HashMap<Point, BTree> bMap=new HashMap<Point, BTree>();//Ҫ���ڵ������ڵ��ӳ��wcb
    private Color arrowc;
    private Color parac;
    private int nodesize;
    private int x1=0;
   
    public BTVariableDrawer(BaseDrawer bd, Graphics2D g,Color arrow_c, Color para_c,int node_size, Map<String, BTree> nbMap, Map<String, BTree> beforeNbMap){///////�ֲ�������ͼ��
        this.bd = bd;
        this.g = g;
        this.nbMap = (HashMap)nbMap;
        this.beforeNbMap = (HashMap)beforeNbMap;
        this.nodesize = node_size;
        this.parac = para_c;
        this.arrowc = arrow_c;
    }

   

    public void insertVariable(){
        int size = this.nbMap.size();
        if(size<=0) return;
        Set<String> key = nbMap.keySet();
        String[] name = new String[size];
        key.toArray(name);
        for(int i = 0; i < size; i++){
            if(nbMap.get(name[i]) != null)
                nbMap.get(name[i]).addVList(name[i]);//V
        }
    }

          
    
   public void irChild1(Point point){//wcb �ж������������� 2012-7-25
      int a=0;
      int size=bMap.get(point).getLR().length();
      if(size>0){
         if(bMap.get(point).getLR().charAt(size-1)=='L'){
                   a=-10;
               }
         if(bMap.get(point).getLR().charAt(size-1)=='R'){
                   a=10; 
               }             
      }
                  x1=a;
}



      public  void createbMap(){//Sun_Wei ���������ڵ������ڵ��ӳ��
      int size = this.nbMap.size();
        if(size<=0) return;
        Set<String> key = nbMap.keySet();
        String[] name = new String[size];
        key.toArray(name);
        for(int i = 0; i < size; i++){
           if(nbMap. get(name[i]) != null){
                 int x = (int)nbMap.get(name[i]).local.getWidth();
                 int y = (int)nbMap.get(name[i]).local.getHeight();
                 bMap.put(new Point(x,y),nbMap.get(name[i]));
           }
        }

} 
    

    public void createPsMap(){
         System.out.println("************************************************++++++++++");
        int size = this.nbMap.size();
        if(size<=0) return;
        Set<String> key = nbMap.keySet();
        String[] name = new String[size];
        key.toArray(name);
        for(int i = 0; i < size; i++){
           if(nbMap. get(name[i]) != null){
                nbMap.get(name[i]).VList.trimToSize();
              System.out.println(")))))))))))))))))))((((((((((((((((((((((((("+nbMap.get(name[i]).getLR());////��ȷ
             
                int length = nbMap.get(name[i]).VList.size();
                String nameString = "";
                for(int j = 0; j < length; j++){
                    nameString = nameString.concat(nbMap.get(name[i]).VList.get(j) + "��");
                    System.out.println(",,,,,,,,,,,,,,,,,,,,"+nbMap.get(name[i]).VList);
                }//����һ��

                if(nameString.length() > 0){
                    nameString = nameString.substring(0, nameString.length()-1);
                    int x = (int)nbMap.get(name[i]).local.getWidth();
                    int y = (int)nbMap.get(name[i]).local.getHeight();

                    psMap.put(new Point(x, y), nameString);
                   
                }
                   
           }else{//AlwaysMai:nbMapȡֵΪ��ʱ������ñ���Ϊ����ֵ��Ϊnull����Ҫ����
               
                BTree bt = beforeNbMap.get(name[i]);
                if(bt != null){
                    int x = (int)bt.local.getWidth();
                    int y = (int)bt.local.getHeight();
                    if(bt.getLR().equalsIgnoreCase("LR")){
                        System.out.println("LR");
                    }else if(bt.getLR().equalsIgnoreCase("RL")){
                        System.out.println("RL");
                    }else if(bt.getLR().equalsIgnoreCase("L")){
                    
                        psMap.put(new Point(x+10, y+60), name[i]);
                    }else if(bt.getLR().equalsIgnoreCase("R")){
                       
                        psMap.put(new Point(x-10, y+60), name[i]);
                        System.out.println("R");
                    }else{
                        psMap.put(new Point(x, y+60), name[i]);
                    }
                }
            }
        }

    }

    public void drawVariable(){
        
        int size =this.psMap.size();
        if(size<=0) return;
        Set<Point> key = psMap.keySet();
        Point[] point = new Point[size];
        key.toArray(point);
        Color c = g.getColor();
        Font F = g.getFont();
        g.setFont(new Font("Serif", Font.BOLD, nodesize));
        for(int i = 0; i < size; i++){
            NodeDrawer.NodeContent(g, arrowc, psMap.get(point[i]),point[i].x-(psMap.get(point[i]).length()*4)+nodesize*3/2, point[i].y - nodesize*2,nodesize,1);
            irChild1(point[i]);// wcb
            ComArrow.Com_Draw_Ptr(g,arrowc, point[i].x+x1+nodesize * 3/2, point[i].y - nodesize, point[i].x+nodesize * 3/2, point[i].y, false, 10, 15);
}
      
       
        
    
//    public void drawPointer(){
//        int size = this.nbMap.size();
//        if(size <= 0) return;
//
//        Color c = g.getColor();
//        Font F = g.getFont();
//        g.setFont(new Font("Serif", Font.BOLD, 14));
//        g.setColor(Color.red);
//        Set<String> key = nbMap.keySet();
//        String[] name = new String[size];
//        key.toArray(name);
//        int lastX = 0;
//        int lastY = 0;//AlwaysMai:��¼�ϴλ���ָ��
//        int m = 0;
//        for(int i = 0; i < size; i++){
//            int x = (int)nbMap.get(name[i]).local.getWidth();
//            int y = (int)nbMap.get(name[i]).local.getHeight();
//            //AlwaysMai:��������ָ����������Ҫ���ǣ���Ҫ�ǻ������ص�
//            if(x == lastX && y == lastY){//AlwaysMai:���ָ��λ����ͬ����ƫ��֮
//                g.drawString(name[i], x + m, y - 25);
//                g.drawLine(x + m, y - 25, x, y);
//                lastX = x;
//                lastY = y;
//                 m += 15;
//            }else{
//                g.drawString(name[i], x - 15, y - 25);
//                g.drawLine(x - 15, y - 25, x, y);
//                lastX = x;
//                lastY = y;
//                m = 0;
//            }
//        }
//        g.setColor(c);
//        g.setFont(F);
    }
  
}
