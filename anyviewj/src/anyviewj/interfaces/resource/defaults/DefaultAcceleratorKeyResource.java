package anyviewj.interfaces.resource.defaults;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007 gdut 1627</p>
 *
 * <p>Company: gdut 1627</p>
 *
 * @author cyf
 * @version 1.0
 */

import javax.swing.KeyStroke;
import anyviewj.interfaces.resource.AcceleratorKeyResource;
import anyviewj.interfaces.resource.ActionResource;

public class DefaultAcceleratorKeyResource extends AcceleratorKeyResource {
    private KeyStroke[] acclKeys;
    private String[] acclNames;

    public DefaultAcceleratorKeyResource() {
        acclKeys = new KeyStroke[ActionResource.INDEXES_TOTAL_COUNT];
        acclNames = new String[ActionResource.INDEXES_TOTAL_COUNT];
        initKeys();
    }

    /**
     * 这里的索引值参考ActionResource
     *
     * @param index int
     * @return String
     */
    @Override
	public KeyStroke getAcceleratorKey(int index) {
        return acclKeys[index];
    }

    /**
     *
     * @param index int
     * @return String
     */
    @Override
	public String getAcceleratorKeyName(int index){
        return acclNames[index];
    }

    private void initKeys(){
        //====================文件菜单====================//
        acclKeys[ActionResource.MENUFILE] = null; //文件菜单
        acclKeys[ActionResource.NEW] = KeyStroke.getKeyStroke("ctrl N"); //新建项目,类,接口,文件等
        acclKeys[ActionResource.NEW_PROJECT] = null; //新建项目
        acclKeys[ActionResource.NEW_CLASS] = null; //新建类文件
        acclKeys[ActionResource.OPEN_PROJECT] = null; //打开项目
        acclKeys[ActionResource.OPEN_FILE] = KeyStroke.getKeyStroke("ctrl O"); //打开文件
        acclKeys[ActionResource.CLOSE_PROJECT] = null; //关闭项目
        acclKeys[ActionResource.CLOSE_FILE] = null; //关闭文件
        acclKeys[ActionResource.SAVE_PROJECT] = null; //保存项目
        acclKeys[ActionResource.SAVE_FILE] = KeyStroke.getKeyStroke("ctrl S"); //保存文件
        acclKeys[ActionResource.SAVE_ALL] = null; //保存全部文件
        acclKeys[ActionResource.RELOGIN] = null; //重新登录
        acclKeys[ActionResource.CHANGE_PASSWORD] = null; //修改密码
        acclKeys[ActionResource.EXIT] = null;//退出

        acclNames[ActionResource.MENUFILE] = ""; //文件菜单
        acclNames[ActionResource.NEW] = "Ctrl+N"; //新建项目,类,接口,文件等
        acclNames[ActionResource.NEW_PROJECT] = ""; //新建项目
        acclNames[ActionResource.NEW_CLASS] = ""; //新建类文件
        acclNames[ActionResource.OPEN_PROJECT] = ""; //打开项目
        acclNames[ActionResource.OPEN_FILE] = "Ctrl+O"; //打开文件
        acclNames[ActionResource.CLOSE_PROJECT] = ""; //关闭项目
        acclNames[ActionResource.CLOSE_FILE] = ""; //关闭文件
        acclNames[ActionResource.SAVE_PROJECT] = ""; //保存项目
        acclNames[ActionResource.SAVE_FILE] = "Ctrl+S"; //保存文件
        acclNames[ActionResource.SAVE_ALL] = ""; //保存全部文件
        acclNames[ActionResource.CHANGE_PASSWORD] = ""; //修改密码
        acclNames[ActionResource.EXIT] = "";//退出

        //====================编辑菜单====================//
        acclKeys[ActionResource.MENUEDIT] = null; //编辑菜单
        acclKeys[ActionResource.UNDO] = KeyStroke.getKeyStroke("ctrl Z"); //撤销
        acclKeys[ActionResource.REDO] = KeyStroke.getKeyStroke("ctrl shift Z"); //重做
        acclKeys[ActionResource.CUT] = KeyStroke.getKeyStroke("ctrl X"); //剪切
        acclKeys[ActionResource.COPY] = KeyStroke.getKeyStroke("ctrl C"); //复制
        acclKeys[ActionResource.PASTE] = KeyStroke.getKeyStroke("ctrl V"); //粘贴
        acclKeys[ActionResource.DELETE] = null; //删除
        acclKeys[ActionResource.SELECTALL] = KeyStroke.getKeyStroke("ctrl A"); //全选

        acclNames[ActionResource.MENUEDIT] = ""; //编辑菜单
        acclNames[ActionResource.UNDO] = "Ctrl+Z"; //撤销
        acclNames[ActionResource.REDO] = "Ctrl+Shift+Z"; //重做
        acclNames[ActionResource.CUT] = "Ctrl+X"; //剪切
        acclNames[ActionResource.COPY] = "Ctrl+C"; //复制
        acclNames[ActionResource.PASTE] = "Ctrl+V"; //粘贴
        acclNames[ActionResource.DELETE] = ""; //删除
        acclNames[ActionResource.SELECTALL] = "Ctrl+A"; //全选


        //====================查找菜单====================//
        acclKeys[ActionResource.MENUSEARCH] = null; //查找菜单
        acclKeys[ActionResource.FIND] = KeyStroke.getKeyStroke("ctrl F"); //查找
        acclKeys[ActionResource.FIND_INPATH] = null; //在指定路径查找
        acclKeys[ActionResource.FINDDOWN] = KeyStroke.getKeyStroke("F3"); //查找下一个
        acclKeys[ActionResource.FINDUP] = KeyStroke.getKeyStroke("F2"); //查找上一个
        acclKeys[ActionResource.REPLACE] = KeyStroke.getKeyStroke("ctrl R"); //替换
        acclKeys[ActionResource.REPLACE_INPATH] = null; //在指定路径替换
        acclKeys[ActionResource.GOTO] = KeyStroke.getKeyStroke("ctrl G"); //跳至

        acclNames[ActionResource.MENUSEARCH] = ""; //查找菜单
        acclNames[ActionResource.FIND] = "Ctrl+F"; //查找
        acclNames[ActionResource.FIND_INPATH] = ""; //在指定路径查找
        acclNames[ActionResource.FINDDOWN] = "F3"; //查找下一个
        acclNames[ActionResource.FINDUP] = "F2"; //查找上一个
        acclNames[ActionResource.REPLACE] = "Ctrl+R"; //替换
        acclNames[ActionResource.REPLACE_INPATH] = ""; //在指定路径替换
        acclNames[ActionResource.GOTO] = "Ctrl+G"; //跳至


        //====================项目菜单====================//
        acclKeys[ActionResource.MENUPROJECT] = null; //项目菜单
        acclKeys[ActionResource.COMPILE_PROJECT] = KeyStroke.getKeyStroke("ctrl F9"); //编译项目
        acclKeys[ActionResource.COMPILE_FILE] = null; //编译文件
        acclKeys[ActionResource.BUILD] = null; //建立项目
        acclKeys[ActionResource.PROJECT_PROPERTIES] = null; //项目属性

        acclNames[ActionResource.MENUPROJECT] = ""; //项目菜单
        acclNames[ActionResource.COMPILE_PROJECT] = "Ctrl+F9"; //编译项目
        acclNames[ActionResource.COMPILE_FILE] = ""; //编译文件
        acclNames[ActionResource.BUILD] = ""; //建立项目
        acclNames[ActionResource.PROJECT_PROPERTIES] = ""; //项目属性


        //====================运行菜单====================//
        acclKeys[ActionResource.MENURUN] = null; //运行菜单
        acclKeys[ActionResource.RUN_PROJECT] = KeyStroke.getKeyStroke("F9"); //运行项目
        acclKeys[ActionResource.RUN_FILE] = null;//运行文件
        acclKeys[ActionResource.DEBUG_PROJECT] = KeyStroke.getKeyStroke("shift F9"); //调试项目
        acclKeys[ActionResource.DEBUG_FILE] = null; //调试文件
        acclKeys[ActionResource.DEBUG_PROJECT_TIME] = null; //定时执行调试项目
        acclKeys[ActionResource.DEBUG_FILE_TIME] = null; //定时执行调试文件
        acclKeys[ActionResource.PAUSE] = KeyStroke.getKeyStroke("F5"); //暂停
        acclKeys[ActionResource.STOP] = KeyStroke.getKeyStroke("F4"); //停止
        acclKeys[ActionResource.STEPOVER] = KeyStroke.getKeyStroke("F8"); //下一行
        acclKeys[ActionResource.STEPINTO] = KeyStroke.getKeyStroke("F7"); //步入，下一步
        acclKeys[ActionResource.STEPOUT] = KeyStroke.getKeyStroke("F6"); //步出
        acclKeys[ActionResource.SINGLE_INSTRUCTION] = KeyStroke.getKeyStroke("F10"); //单指令

        acclNames[ActionResource.MENURUN] = ""; //运行菜单
        acclNames[ActionResource.RUN_PROJECT] = "F9"; //运行项目
        acclNames[ActionResource.RUN_FILE] = "";//运行文件
        acclNames[ActionResource.DEBUG_PROJECT] = "Shift+F9"; //调试项目
        acclNames[ActionResource.DEBUG_FILE] = ""; //调试文件
        acclNames[ActionResource.DEBUG_PROJECT_TIME] = ""; //定时执行调试项目
        acclNames[ActionResource.DEBUG_FILE_TIME] = ""; //定时执行调试文件
        acclNames[ActionResource.PAUSE] = "F5"; //暂停
        acclNames[ActionResource.STOP] = "F4"; //停止
        acclNames[ActionResource.STEPOVER] = "F8"; //下一行
        acclNames[ActionResource.STEPINTO] = "F7"; //步入，下一步
        acclNames[ActionResource.STEPOUT] = "F6"; //步出
        acclNames[ActionResource.SINGLE_INSTRUCTION] = "F10"; //单指令


        //====================工具菜单====================//
        acclKeys[ActionResource.MENUTOOLS] = null; //工具菜单
        acclKeys[ActionResource.CONFIG] = null; //配置

        acclNames[ActionResource.MENUTOOLS] = ""; //工具菜单
        acclNames[ActionResource.CONFIG] = ""; //配置


        //====================视图菜单====================//
        acclKeys[ActionResource.MENUVIEW] = null; //视图菜单
        acclKeys[ActionResource.OUTPUT] = null; //输出窗口

        acclNames[ActionResource.MENUVIEW] = ""; //视图菜单
        acclNames[ActionResource.OUTPUT] = ""; //输出窗口


        //====================帮助菜单====================//
        acclKeys[ActionResource.MENUHELP] = null; //帮助菜单
        acclKeys[ActionResource.HELP] = null; //帮助
        acclKeys[ActionResource.ABOUT] = null; //关于

        acclNames[ActionResource.MENUHELP] = ""; //帮助菜单
        acclNames[ActionResource.HELP] = ""; //帮助
        acclNames[ActionResource.ABOUT] = ""; //关于

    }
}
