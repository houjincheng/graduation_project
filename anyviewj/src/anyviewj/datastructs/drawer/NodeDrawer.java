/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package anyviewj.datastructs.drawer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.util.Vector;
/**
 *
 * @author Administrator
 */
public class NodeDrawer {
   /* //黄色调
	static Color[] LinkListNodeColor = {
		new Color(252,243,155),   //front
		new Color(254,253,203),   //top
		new Color(252,251,135)    //right
	};
	//蓝色调
   	static Color[] TreeNodeColor = {
   		new Color(135,254,247)   //front
       ,new Color(198,252,248)   //top
       ,new Color(96,251,243)    //right
   	};
   	//红色调
   	static Color[] GraphNodeColor = {
   		new Color(252,209,252)   //front
       ,new Color(252,235,252)   //top
       ,new Color(252,186,252)    //right
   	};
    //链表的圆球结点
   	static Color LinkListBallColor = new Color(173,203,3);
   	//树的圆球结点
   	static Color TreeBallColor = new Color(0x0153CC);
   	//图的圆球结点
   	static Color GraphBallColor = new Color(213,6,89);*/

   	//无分割的结点
   	//（left,top)正面左上角的坐标
   	// size 高度
   	// length = 长度  / 高度
   	// type 结点所属数据结构
   	// clientColor 以后用于和用户交互备用
	public static void RectNode(Graphics2D g2,Color colorFront, int left, int top, int size, int length) {
		// TODO Auto-generated method stubl

		Color colorFront1= null,colorTop = null,colorRight = null;
	
		//////////////////////////////Wei
		colorFront1 = colorFront;

		colorTop =colorFront.brighter();//getHSBColor(1,10,10);///wcb: 改变颜色深浅
		colorRight = colorFront.darker();
		//控制正面的长度和高度的比例
		int fatX = length;
		//画正面
		g2.setColor(colorFront1);
		g2.fillRect(left, top, fatX *size, size);
		g2.setColor(Color.black);
		g2.drawRect(left, top, fatX *size, size);
		//画上面
		int[] topA = new int[4];
		int[] topB = new int[4];
		topA[0] = left;
		topB[0] = top;
		topA[1] = left + fatX *size;
		topB[1] = top;
		topA[2] = left + fatX*size + size * 1/2;
		topB[2] = top - size *1/4;
		topA[3] = left + size * 1/2  ;
		topB[3] = top - size * 1/4;
		Polygon p1 = new Polygon(topA,topB,4);
		g2.setColor(colorTop);
		g2.fillPolygon(p1);
		g2.setColor(Color.black);
		g2.drawPolygon(p1);
		//画右面
		int[] rightA = new int[4];
		int[] rightB = new int[4];
		rightA[0] = topA[1];
		rightB[0] = topB[1];
		rightA[1] = topA[2];
		rightB[1] = topB[2];
		rightA[2] = topA[2];
		rightB[2] = topB[2] + size;
		rightA[3] = topA[1];
		rightB[3] = topB[1] + size;
		Polygon p2 = new Polygon(rightA,rightB,4);
		g2.setColor(colorRight);
		g2.fillPolygon(p2);
		g2.setColor(Color.black);
		g2.drawPolygon(p2);
	}

	//没有分割线的结点内画数字
    //string：所画数字
    //（left，top)，正面左上角坐标c
    //size 结点正面高度
    //length:长度和高度的比值
    public static void NodeContent(Graphics2D g2,Color colorFont, String string,int left,int top,int size,int length){
    	g2.setColor(colorFont);////////////////////////////Wei 字体颜色
		int fontsize;
	    Font f;
	    fontsize = size * 4/5;
		f = new Font("Serif",Font.BOLD,fontsize);
		g2.setFont(f);
		if(string.length() == 1)
			g2.drawString(string,left + size * length/2   ,top + 5 * size/6);
		else
			g2.drawString(string,left + size * length/2 - fontsize* 2/5 ,top + 5 * size/6);
    }


    //画带分割线的结点
    //（left，top)，正面左上角坐标
    //size:结点高度
    //length：长度与高度的比值，也是格子数
    //type 结点所属数据结构类型
	public static void RectDividedNode(Graphics2D g2,Color colorFront, int left, int top, int size,int length) {
		// TODO Auto-generated method stub
		RectNode(g2,colorFront, left, top, size, length);/////////////////////////////////////////////
		g2.setColor(Color.gray);

		for(int i = 0; i <= length; i++){
			g2.drawLine(left + size * i, top, left + size * i + size * 1/2, top - size * 1/4);
			g2.drawLine(left + size * i, top, left + size * i, top + size);
		}
		g2.drawLine(left + size * length, top + size, left + size *length + size * 1/2, top + size - size * 1/4);
        g2.drawLine(left + size * length + size * 1/2 , top - size * 1/4, left + size * length + size * 1/2, top + size * 3/4);
	    g2.drawLine(left,top,left + size * length, top);
	    g2.drawLine(left, top + size, left + size * length, top + size);
	    g2.drawLine(left + size * 1/2, top - size * 1/4, left + size *1/2 + length * size, top - size * 1/4);
    }

	//在有分割线的结点内画数字
	//string 所画数字（一般是一位，或者两位）
	//（left，top)，正面左上角坐标
	//size，每个方格的大小
	//local 字符串要画在第几个方格
    public static void DividedNodeContent(Graphics2D g2,Color colorFont,String string,int left,int top,int size,int local){
    	g2.setColor(colorFont);////////////////////Wei
		int fontsize;
	    Font f;
	    fontsize = size * 4/5;
		f = new Font("Serif",Font.BOLD,fontsize);
		g2.setFont(f);
        if(string == "∧")
            g2.drawString(string,left + size * (local - 1) + size*1 /7  ,top + 5 * size/6);
        else
            if(string.length() == 1)
                g2.drawString(string,left + size * (local - 1) + size*3 /8  ,top + 5 * size/6);
            else
                g2.drawString(string,left + size * (local - 1) + size*1 /8  ,top + 5 * size/6);
    }



	//画竖直结点（专用于哈希表）
	//（left，top)，正面左上角坐标
    //size:结点宽度
	//length:结点高度与长度的比值（注意，此处和其他函数相反）
	//type结点所属数据结构类型
	public static void verticalNode(Graphics2D g2,Color colorFront, int left, int top, int size,double length) {
		// TODO Auto-generated method stub
		int height = (int)(length * size);////////////////////////////////////////
		Color colorFront1 = null,colorTop = null,colorRight = null;
		
			colorFront1 = colorFront;
			colorTop = colorFront.brighter();
			colorRight = colorFront.darker();
		//画正面
		g2.setColor(colorFront1);
		g2.fillRect(left, top, size, height);
		g2.setColor(Color.black);
		g2.drawRect(left, top, size, height);
		//画上面
		g2.setColor(colorTop);
		int[] topA = new int[4];
		int[] topB = new int[4];
		topA[0] = left;
		topB[0] = top;
		topA[1] = left + size*1/2;
		topB[1] = top - size*1/4;
		topA[2] = left + size + size *1/2;
		topB[2] = top - size * 1/4;
		topA[3] = left + size;
		topB[3] = top;
		Polygon p = new Polygon(topA,topB,4);
		g2.fill(p);
		g2.setColor(Color.black);
		g2.drawPolygon(p);
		//画右面
		g2.setColor(colorRight);
		int[] rightA = new int[4];
		int[] rightB = new int[4];
		rightA[0] = left + size;
		rightB[0] = top;
		rightA[1] = left + size + size *1/2;
		rightB[1] = top - size *1/4;
		rightA[2] = left + size + size *1/2;
		rightB[2] = top - size * 1/4 + height;
		rightA[3] = left + size;
		rightB[3] = top + height;
		Polygon p1 = new Polygon(rightA,rightB,4);
		g2.fill(p1);
		g2.setColor(Color.black);
		g2.drawPolygon(p1);

	}

	//画竖直结点的值
	//string：所画数字
    //（left，top)，正面左上角坐标
    //size 结点正面宽度
    //length:高度和长度的比值
	public static void verticalContent(Graphics2D g2,Color colorFont, String string,int left, int top, int size,double length){
		g2.setColor(colorFont);
		int fontsize;
	    Font f;
	    fontsize = size * 4/5;
		f = new Font("Serif",Font.BOLD,fontsize);
		g2.setFont(f);
		if(string.length() == 1)
			g2.drawString(string,left + size/2 - fontsize* 1/3  ,(int) (top + size * length/2 +  size/6));
		else
			g2.drawString(string,left + size/2 - fontsize* 3/5 ,(int) (top + size * length/2 +  size/6));
	}

	//画圆球结点
	//（left，top)，左上角坐标
	//radius 球的半径
	//type结点所属数据结构类型
	public static void ballNode(Graphics2D g2,Color colorFront,int left,int top,int radius) {
		// TODO Auto-generated method stub
		Color BallColor = null ;/////////////////////////
		
			BallColor = colorFront;
		
		g2.setColor(BallColor);
        g2.fillOval(left, top, radius * 2, radius * 2);
        Point2D center = new Point2D.Float(left + radius, top + radius/3 );
        float[] dist = {0.0f, 1.0f};
		Color[] colors = {Color.white,BallColor};
		RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);
		g2.setPaint(p);
		g2.fillOval(left, top, 2 * radius, 2 * radius);
	}
	//画圆球结点的值
    //string：所画数字
	//（left，top)，正面左上角坐标
	//radius:球的半径
	public static void ballNodeContent(Graphics2D g2,Color colorFont,String string,int left,int top,int radius){
		g2.setColor(colorFont);
		int fontsize;
	    Font f;
	    fontsize = radius;
		f = new Font("Serif",Font.BOLD,fontsize);
		g2.setFont(f);
		if(string.length() == 1)
			g2.drawString(string,left + radius - fontsize/4  ,top + radius *4/3);
		else
			g2.drawString(string,left + radius - fontsize/2 ,top + radius *4/3);
	}
   /////////////////
    public static void drawArrow(Graphics2D g2,Color colorArrow,int originX,int originY,int angle,int size,boolean bBoth){
		int length;
		if(size < 14)
			length = 20;
		else
			length = size * 2;
		int desX = (int)(originX + length * Math.cos(Math.PI *angle/180));
		int desY = (int)(originY - length * Math.sin(Math.PI * angle/180));
		int sizeA,sizeB;
		if(size < 20){
			sizeA = 10;
			sizeB = 12;
		}else if(size >=20 && size <= 40){
			sizeA = 12;
		    sizeB = 14;
		}else{
			sizeA = 14;
			sizeB = 16;
		}
		ComArrow.Com_Draw_Ptr(g2,colorArrow, originX, originY, desX, desY, bBoth, sizeA, sizeB);
	}

/////////////////////////
	public static void drawlocalVarial(Graphics2D g2,int originX,int originY,int size,Color strColor,String content){
		Font font = new Font("Serif", Font.BOLD,size);
		g2.setFont(font);
		g2.drawString(content, originX, originY);
	}

    public static void drawVQueue(Graphics2D g2,Color fontColor , Color nodeColor ,int x, int y, int fontsize,int n,int begin,int rear,Vector<String>content,Color arrowColor)
    {
   //   int size = content.size();
      if(n > 0 && begin <= n ){
         int oRadius = ((int)( n/2.5 + 0.5 )) * fontsize;
         int iRadius = ((int) ( n / 2.5 )) * (fontsize /2);
         double angle = Math.PI * 2 / n;
         double totalAngle = 0;

         g2.setColor(nodeColor);
         g2.fillOval(x , y, 2 * oRadius , 2 * oRadius);
         g2.setColor(new Color(245,245,245));
         g2.fillOval(x + oRadius - iRadius, y + oRadius - iRadius , 2 * iRadius , 2 * iRadius);
         
         g2.setColor(Color.BLACK);
         g2.drawOval(x , y , 2 * oRadius, 2 * oRadius);
         g2.drawOval(x + oRadius - iRadius, y + oRadius - iRadius, 2 * iRadius, 2 * iRadius);
         
         
         
        
        
         g2.translate(x + oRadius, y + oRadius);
         String text;
         int j = 0;
         int arrowLength = fontsize;

         for(int i = 0 ; i < n ; i++){
            if(content != null){
           //     System.out.println("NodeDrawer,VQueue, content is not null._______+=======================");
                int size = content.size();
                 

               if(i == (j + begin) % n && j < size){
                   text = content.get(j);
                   g2.rotate(-totalAngle);
                   double x1 = oRadius * Math.cos(totalAngle);
                   double y1 = oRadius * Math.sin(totalAngle);
                   double x2 = iRadius * Math.cos(totalAngle);
                   double y2 = iRadius * Math.sin(totalAngle);
                   double x3 = oRadius * Math.cos(totalAngle - angle);
                   double y3 = oRadius * Math.sin(totalAngle - angle);
                   double x4 = iRadius * Math.cos(totalAngle - angle);
                   double y4 = iRadius * Math.sin(totalAngle - angle);
                   int fontPosX = (int)(x1 + x2 + x3 + x4)/4;
                   int fontPosY = (int)(y1 + y2 + y3 + y4)/4;
                  g2.setFont(new Font("Serif",Font.BOLD,fontsize));
                  g2.setColor(fontColor);
                  //g2.drawString(text, oRadius - fontsize, -(fontsize / 4));
                  g2.drawString(text,fontPosX ,fontPosY);
                  g2.rotate(totalAngle);
          //        if(size == 0){
             //         g2.drawString("front,rear", oRadius + arrowLength, -fontsize /2);
           //     }
             //    else{
                //     if(j == 0){
                //         g2.rotate(angle);
                //         drawArrow(g2,arrowColor,oRadius + 2 * arrowLength, -fontsize *5/8 - fontsize /4,180,arrowLength,false);
               //          g2.drawString("front", oRadius + 2 * arrowLength, -fontsize *5/8 );
                //         g2.rotate(-angle);
                 //    }
                 //    if(j == size - 1){
                 //        drawArrow(g2,arrowColor,oRadius + 2 * arrowLength , -fontsize *5/8 - fontsize /4,180,arrowLength,false);
                //          g2.drawString("rear", oRadius + 2 * arrowLength, -fontsize *5/8);
              //       }
            //     }
                 j++;
               }
            }
       //     else{
      //        System.out.println("+++++++++++++++++++++++++++++NodeDrawer,VQueue, the value of var begin is " + begin);

         //      if(i == begin){
          //         System.out.println("NodeDrawer,VQueue, i == begin+++++++++++++++++++++++++");
           //        g2.setColor(fontColor);
            //      g2.drawString("front,rear", oRadius + arrowLength, -fontsize * 3/2);
            //   }
            //}
          if(rear == begin && i == begin){
             g2.setFont(new Font("Serif",Font.BOLD,fontsize));
             drawArrow(g2 ,arrowColor,oRadius + 2 * arrowLength,-fontsize *5/8 - fontsize /4,180,arrowLength,false);
               if(totalAngle < -Math.PI /2 && totalAngle > - Math.PI *3/2){
                    g2.rotate(-Math.PI);
                    g2.drawString("front,rear",- (oRadius + 2 * arrowLength + fontsize),fontsize *5/8+fontsize/4);
                    g2.rotate(Math.PI);
                }else{
                   g2.drawString("front,rear", oRadius + 2 * arrowLength, -fontsize * 5 /8);
                }
          }
          else{
             g2.setFont(new Font("Serif",Font.BOLD,fontsize));
             if(i == begin){
                drawArrow(g2,arrowColor,oRadius + 2 * arrowLength, -fontsize *5/8 - fontsize /4,180,arrowLength ,false);
                if(totalAngle < -Math.PI /2 && totalAngle > -Math.PI *3/2){
                    g2.rotate(-Math.PI);
                    g2.drawString("front",- (oRadius + 2 * arrowLength + 2 * fontsize),fontsize *5/8 + fontsize/4);
                    g2.rotate(Math.PI);
                }
                else{
                   g2.drawString("front", oRadius + 2 * arrowLength, -fontsize *5/8 );
                }
             }
             if(i == rear){
               drawArrow(g2,arrowColor,oRadius + 2 * arrowLength ,-fontsize *5/8 - fontsize /4,180,arrowLength,false);
                 if(totalAngle < -Math.PI /2 && totalAngle > - Math.PI *3/2){
                    g2.rotate(-Math.PI);
                    g2.drawString("rear",- (oRadius + 2 * arrowLength + 2 * fontsize),fontsize *5/8 + fontsize/4);
                    g2.rotate(Math.PI);
                }
                 else{
                   g2.drawString("rear", oRadius + 2 * arrowLength, -fontsize *5/8 );
                 }
             }
          }
           
           g2.setColor(Color.BLACK);
      //      System.out.println("NodeDrawer , VQueue ,totalAnagle: " + totalAngle * 180 / Math.PI);
        //   g2.drawLine((int)(iRadius * Math.cos(totalAngle)),(int)(iRadius * Math.sin(totalAngle)), (int)(oRadius * Math.cos(totalAngle)), (int)(oRadius * Math.sin(totalAngle)));
          g2.drawLine((iRadius),0,(oRadius ), 0);
       //   g2.translate(x + iRadius , y + iRadius);
           g2.setFont(new Font("Serif",Font.BOLD,fontsize *3/4));
          g2.drawString(Integer.toString(i),iRadius - (fontsize * 3/4),-fontsize/ 6);
       //    totalAngle += angle;
           totalAngle += (-angle);
           g2.rotate(-angle);
         }
      }

    }

    public static void drawRotatedString(Graphics2D g2,Color fontColor,String str,int fontsize,int x, int y ,double angle)
    {
     //  Font f = new Font ("Serif",Font.BOLD,fontsize);
      Font preFont = g2.getFont();
      Color preColor = g2.getColor();
      g2.setFont(new Font("Serif",Font.BOLD,fontsize));
      g2.setColor(fontColor);
      g2.translate(x,y);
      g2.rotate(angle);
      g2.drawString(str, 0 , 0);
      g2.rotate(-angle);
      g2.translate(-x,-y);
      g2.setFont(preFont);
      g2.setColor(preColor);
    }

  //  public static double calculating
}
