package anyviewj.interfaces.resource;

/**
 * <p>Title: ��Դ������</p>
 *
 * <p>Description: ����ϵͳ��Դ</p>
 *
 * <p>Copyright: Copyright (c) 2007 gdut 1627</p>
 *
 * <p>Company: gdut 1627</p>
 *
 * @author cyf
 * @version 1.0
 */

import javax.swing.ImageIcon;
import anyviewj.console.ConsoleCenter;
import anyviewj.interfaces.resource.defaults.DefaultIconResource;
import anyviewj.interfaces.resource.defaults.DefaultAcceleratorKeyResource;
import anyviewj.interfaces.resource.chinese.*;

public class ResourceManager {
    //Ĭ�ϼ��ټ���Դ
	private AcceleratorKeyResource acceleratorKeyResource = new DefaultAcceleratorKeyResource();

    //Ĭ�϶�����Դ
    private ActionResource actionResource = new ChineseActionResource();

    //Ĭ��ͼ����Դ
    private IconResource iconResource = new DefaultIconResource();    

    //Ĭ�ϴ���Ԫ����Դ
    private FormResource formResource = new ChineseFormResource();

    //ȫ�ֿ�����������
    private ConsoleCenter center;

    public ResourceManager(ConsoleCenter aCenter) {
        this.center = aCenter;
    }

    //��ȡ������Դ
    public ActionResource getActionResource(){
        return this.actionResource;
    }

    //���ټ���Դ
    public AcceleratorKeyResource getAcceleratorKeyResource() {
        return acceleratorKeyResource;
    }

     //Ĭ��ͼ����Դ
    public IconResource getIconResource(){
        return iconResource;
    }

    //Ĭ�ϴ���Ԫ����Դ
    public FormResource getFormResource(){
        return formResource;
    }

    //����
    public static void main(String[] args){
    	ImageIcon i = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                   getResource("Common/File/New.png")); //�½���Ŀ,��,�ӿ�,�ļ���
        if(i==null) System.out.print("error");
        else System.out.print("ok");
    }




}
