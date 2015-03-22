package anyviewj.interfaces.ui.drawer;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class FontChooser implements ItemListener,ActionListener{
   
   public List fontName,fontSize,fontShape;
   public JPanel pName,pShape,pSize,pModel,pRb;
   public JTextField txtName,txtShape,txtSize;
   public JTextArea txtModel;
   public JDialog dialog;
   public JButton ok,cancel;
   public Font modelFont;
   
   
   public FontChooser(JFrame f){
	 GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();  
     String name[] = g.getAvailableFontFamilyNames();
     String shape[] = {"常规","斜体","粗体  倾斜","粗体"};
     
     fontName = new List();
     fontShape = new List();
     fontSize = new List();
     
     pRb = new JPanel();
     
     txtModel = new JTextArea();
     txtModel.setSize(20,40);
     txtModel.setText("广东工业大学计算机学院");
     ok = new JButton("确定");
     cancel = new JButton("取消");
     
     fontName.addItemListener(this);
     fontShape.addItemListener(this);
     fontSize.addItemListener(this);
     ok.addActionListener(this);
     cancel.addActionListener(this);
     
     for(int i = 0;i<name.length;i++)
    	 fontName.add(name[i]);
     for(int i = 0;i<200;i++)
    	 fontSize.add(Integer.toString(i+10));
     for(int i = 0;i<shape.length;i++)
    	 fontShape.add(shape[i]);
     
     
     fontName.select(137);
     fontSize.select(22);
     fontShape.select(1);
     
     pName = new JPanel();
     pShape = new JPanel();
     pSize = new JPanel();
     pModel = new JPanel();
     txtName = new JTextField(12);
     txtShape = new JTextField(12);
     txtSize = new JTextField(12);
     
     txtName.setText(fontName.getSelectedItem());
     txtShape.setText(fontShape.getSelectedItem());
     txtSize.setText(fontSize.getSelectedItem());
     
     dialog = new JDialog(f,"字体选择",true);
     dialog.setLayout(null);
     
     pName.setBorder(BorderFactory.createTitledBorder("字体"));
     pShape.setBorder(BorderFactory.createTitledBorder("字形"));
     pSize.setBorder(BorderFactory.createTitledBorder("大小"));
     pModel.setBorder(BorderFactory.createTitledBorder("示例"));
     
     pName.setBounds(10,10,190,170);
     pShape.setBounds(210,10,160,170);
     pSize.setBounds(380,10,80,170);
     pModel.setBounds(10,200,450,120);
     
     txtName.setBounds(30,30,160,22);
     txtShape.setBounds(220,30,140,22);
     txtSize.setBounds(390,30,60,22);
     fontName.setBounds(30,65,160,100);
     fontSize.setBounds(390,65,60,100);
     fontShape.setBounds(220,65,140,100);
     txtModel.setBounds(20,220,430,85);
     ok.setBounds(140,340,65,30);
     cancel.setBounds(250,340,65,30);
     
     dialog.add(pName);
     dialog.add(pShape);
     dialog.add(pModel);
     dialog.add(pSize);
     dialog.add(txtName);
     dialog.add(txtShape);
     dialog.add(txtSize);
     dialog.add(fontName);
     dialog.add(fontSize);
     dialog.add(fontShape);
     dialog.add(txtModel);
     
     dialog.add(ok);
     dialog.add(cancel);
     
     dialog.setSize(480,440);
     dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
     dialog.setVisible(true);
   }


@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}


@Override
public void itemStateChanged(ItemEvent e) {
	// TODO Auto-generated method stub
	try{
		if(fontShape.getSelectedItem()=="粗体")
		{
			modelFont = new Font(fontName.getSelectedItem(),Font.BOLD,Integer.parseInt(fontSize.getSelectedItem()));
		    txtModel.repaint();
		}
		if(fontShape.getSelectedItem()=="斜体")
		{
			modelFont = new Font(fontName.getSelectedItem(),Font.ITALIC,Integer.parseInt(fontSize.getSelectedItem()));
		    txtModel.setFont(modelFont);
		}
		if(fontShape.getSelectedItem()=="粗体  倾斜")
		{
			modelFont = new Font(fontName.getSelectedItem(),Font.BOLD|Font.ITALIC,Integer.parseInt(fontSize.getSelectedItem()));
		    txtModel.setFont(modelFont);
		}
		if(fontShape.getSelectedItem()=="常规")
		{
			modelFont = new Font(fontName.getSelectedItem(),Font.ITALIC,Integer.parseInt(fontSize.getSelectedItem()));
		    txtModel.setFont(modelFont);
		}
		
		
		txtName.setText(fontName.getSelectedItem());
		txtShape.setText(fontShape.getSelectedItem());
		txtSize.setText(fontSize.getSelectedItem());
	}catch(Exception x){
		x.printStackTrace();
	}
	
}

  public static void main(String args[]){
	  JFrame f2 = new JFrame();
	  f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  FontChooser fc = new FontChooser(f2);
  }


}
