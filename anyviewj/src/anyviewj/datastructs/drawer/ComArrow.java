/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package anyviewj.datastructs.drawer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Color;
/**
 *
 * @author lenovo
 */
public class ComArrow {
    public static final int DefArrowLen = 10;
    public static final int DefArrowAngle = 15;
    public static final double Cos15 = 0.965925826289068;
    public static final double Cos13 = 0.974370064785235;
    public static final double Arc180 = Math.PI;
    public static final double Arc90 = Arc180 / 2;
    public static final double DefArcAngle = Math.PI * DefArrowAngle / 180;
    public static final double R = DefArrowLen / Cos15;

    static void Com_Draw_Ptr(Graphics2D g) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public ComArrow() {
    }

    public static void Calculate_Arrow_Pos(Dimension P1, Dimension P2, int X1, int Y1,
                                    int X2, int Y2,
                                    int ArrowLen, int ArrowAngle) {
        double Radius;
        double TanAngle;
        double ArcAngle;
        //����ͷ�ĳ������н���Ĭ��ֵ��ͬ,��ֱ��ʹ�ó���ֵ��������
        /*if (ArrowLen == DefArrowLen&&ArrowAngle == DefArrowAngle){
            Radius = R;
            ArcAngle = DefArcAngle;
                 }
                else {*/
        ArcAngle = Math.PI * ArrowAngle / 180;
        Radius = ArrowLen / Math.cos(ArcAngle);
        //}
        //���¶�����(x2,y2)Ϊ������ԭ����м����

        //��ֱ��������Y����,��б��ȡ90��
        if (X1 == X2) {
            TanAngle = Arc90;
        } else {
            //����б��ͨ��б�����
            TanAngle = Math.atan((Y2 - Y1) / ((X2 - X1)*1.0));
            //ʹб����0~180��֮��
            if (TanAngle < 0) {
                TanAngle = Arc180 + TanAngle;
            }
        }
        //��Ϊ��ͨ��ѧ�ϵ���������Windows�ϵ�������Y���ϲ�ͬ(���������෴),
        //���Ա�����о���

        //���й���X��Գ�
        if (Y1 < Y2) {
            TanAngle = Arc180 - TanAngle;
        } else if (Y1 > Y2) {
            TanAngle = -TanAngle;
        } else {
            if (X1 >= X2) {
                TanAngle = -TanAngle;
            } else {
                TanAngle = Arc180 - TanAngle;
            }
        }

        //����ĳ���Ƕȵ�Բ���ϵ������
        P1.width = Math.round((float) (X2 +
                                       Radius * Math.cos(TanAngle + ArcAngle)));
        P1.height = Math.round((float) (Y2 -
                                        Radius * Math.sin(TanAngle + ArcAngle)));
        P2.width = Math.round((float) (X2 +
                                       Radius * Math.cos(TanAngle - ArcAngle)));
        P2.height = Math.round((float) (Y2 -
                                        Radius * Math.sin(TanAngle - ArcAngle)));
    }
    //����g��ǰ�˵����ꡢβ�˵����ꡢ�Ƿ�˫��ͷ��һƲ���ȡ�һƲ�Ƕ�
    //��ͨ����̬��������
    public static void Com_Draw_Ptr(Graphics g,Color colorArrow, int fx, int fy, int tx, int ty,
                             boolean bBoth, int ArrowLen, int ArrowAngle) {
        Dimension P1 = new Dimension();
        Dimension P2 = new Dimension();
        g.setColor(colorArrow);
        Calculate_Arrow_Pos(P1, P2, fx, fy, tx, ty, ArrowLen, ArrowAngle);

        g.fillPolygon(new Polygon(new int[] { tx,P1.width, P2.width,tx},
                                  new int[] {ty, P1.height, P2.height,ty}, 4));
        if (bBoth == true) {
            Calculate_Arrow_Pos(P1, P2, tx, ty, fx, fy, ArrowLen, ArrowAngle);
            g.fillPolygon(new Polygon(new int[] {fx,P1.width, P2.width, fx},
                                      new int[] {fy,P1.height, P2.height, fy}, 4));
        }
        g.drawLine(fx, fy, tx, ty);

    }
    /*
         //��ʼ��,������,�м��,���ڵľ���
         public void Com_Draw_Cycle_Ptr(Integer fx,Integer fy,Integer tx,Integer ty,Integer mx,Integer my,
     Integer ALeft,Integer ATop,Integer ARight,Integer ABottom){
        with FLinkCanvas do
         begin
        Pen.Color:=FDrawAttr.FNodeLineColor;
        Arc(ALeft,ATop,ARight,ABottom,fx,fy,tx,ty);
        Com_Draw_Ptr(mx,my,tx,ty,FDrawAttr.FNodeLineColor,False,6,13);
         end;

         }

         public void Com_Draw_Cycle_Rect_Ptr(cell:T2DDrawCell){
        Integer fx,fy,tx,ty,mx,my;
        Assert(cell <> nil);//???
         fx=Cell.X + Cell.Z;
         fy=Cell.Y;
         tx=fx-FHeadAttr.NodeHalfHeight;
         ty=fy - FHeadAttr.NodeHalfHeight;
         mx=tx;
         my=ty-FHeadAttr.NodeHalfHeight div 2;
         Com_Draw_Cycle_Ptr(fx,fy+FHeadAttr.NodeHalfHeight div 4,tx,ty,mx,my,tx,ty-FHeadAttr.NodeHeight,fx+FHeadAttr.NodeHeight,fy);
         }

         public void Com_Draw_Cycle_Round_Ptr(){
       Dimension CenterPos = new Dimension();
       Assert(pCell <> nil);
         with FHeadAttr,FLinkCanvas,FDrawAttr do
         begin
        CenterPos.X:=pCell.X + NodeRadius;
        CenterPos.Y:=pCell.Y - NodeRadius;
        Pen.Color:=FNodeLineColor;
        MoveTo(CenterPos.X,pCell.Y);
        AngleArc(Handle,CenterPos.X,CenterPos.Y,NodeRadius,-90,270);
        Com_Draw_Ptr(pCell.X+2,CenterPos.Y-6,pCell.X,CenterPos.Y,FNodeLineColor,False,6,13);


         }
     */

}

