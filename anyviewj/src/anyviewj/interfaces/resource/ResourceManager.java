package anyviewj.interfaces.resource;

/**
 * <p>Title: 资源管理器</p>
 *
 * <p>Description: 管理系统资源</p>
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
    //默认加速键资源
	private AcceleratorKeyResource acceleratorKeyResource = new DefaultAcceleratorKeyResource();

    //默认动作资源
    private ActionResource actionResource = new ChineseActionResource();

    //默认图标资源
    private IconResource iconResource = new DefaultIconResource();    

    //默认窗体元素资源
    private FormResource formResource = new ChineseFormResource();

    //全局控制中心引用
    private ConsoleCenter center;

    public ResourceManager(ConsoleCenter aCenter) {
        this.center = aCenter;
    }

    //获取动作资源
    public ActionResource getActionResource(){
        return this.actionResource;
    }

    //加速键资源
    public AcceleratorKeyResource getAcceleratorKeyResource() {
        return acceleratorKeyResource;
    }

     //默认图标资源
    public IconResource getIconResource(){
        return iconResource;
    }

    //默认窗体元素资源
    public FormResource getFormResource(){
        return formResource;
    }

    //测试
    public static void main(String[] args){
    	ImageIcon i = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                   getResource("Common/File/New.png")); //新建项目,类,接口,文件等
        if(i==null) System.out.print("error");
        else System.out.print("ok");
    }




}
