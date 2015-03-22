package anyviewj.interfaces.resource.chinese;

import javax.swing.ImageIcon;
import javax.swing.Icon;
import anyviewj.interfaces.resource.ActionResource;

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

public class ChineseActionResource extends ActionResource {
    private ImageIcon[] icons = new ImageIcon[INDEXES_TOTAL_COUNT];//图标
    private String[] names = new String[INDEXES_TOTAL_COUNT];//名称
    private String[] descs = new String[INDEXES_TOTAL_COUNT];//提示

    public ChineseActionResource() {
        super();
        this.loadNames();
        this.loadDescs();
        this.loadImages();
    }

    /**
     * getActionName
     *
     * @param index int
     * @return String
     */
    @Override
	public String getActionName(int index) {
        return names[index];
    }

    /**
     * getActionShortDesc
     *
     * @param index int
     * @return String
     */
    @Override
	public String getActionDesc(int index) {
        return descs[index];
    }

    /**
     * getImage
     *
     * @param index int
     * @return ImageIcon
     */
    @Override
	public Icon getIcon(int index) {
        return icons[index];
    }

    /**
     *
     */
    private void loadImages(){
        //====================文件菜单====================//
        icons[NEW] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                   getResource("/anyviewj/interfaces/resources/file/New.gif")); //新建项目,类,接口,文件等

        icons[NEW_PROJECT] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                           getResource("/anyviewj/interfaces/resources/file/NewProject.gif")); //新建项目

        icons[NEW_CLASS] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                         getResource("/anyviewj/interfaces/resources/file/NewClass.gif")); //新建类文件

        icons[OPEN_PROJECT] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                            getResource(
                "/anyviewj/interfaces/resources/file/OpenProject.gif")); //打开项目

        icons[OPEN_FILE] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                         getResource("/anyviewj/interfaces/resources/file/OpenFile.gif")); //打开文件

        icons[CLOSE_PROJECT] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                             getResource(
                "/anyviewj/interfaces/resources/file/CloseProject.gif")); //关闭项目

        icons[CLOSE_FILE] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                          getResource("/anyviewj/interfaces/resources/file/CloseFile.gif")); //关闭文件

        icons[SAVE_PROJECT] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                            getResource(
                "/anyviewj/interfaces/resources/file/SaveProject.gif")); //保存项目

        icons[SAVE_FILE] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                         getResource("/anyviewj/interfaces/resources/file/SaveFile.gif")); //保存文件

        icons[SAVE_ALL] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                        getResource("/anyviewj/interfaces/resources/file/SaveAll.gif")); //保存全部文件
        
        icons[RELOGIN] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
        		getResource("/anyviewj/net/client/resource/relogin.gif")); //重新登录
        
        icons[CHANGE_PASSWORD] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
        		getResource("/anyviewj/net/client/resource/changePassword.gif")); //修改密码
        
        //icons[UPLOAD] =  icons[SAVE_ALL];

        //====================编辑菜单====================//
        icons[UNDO] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                    getResource("/anyviewj/interfaces/resources/edit/Undo.gif")); //撤销
        icons[REDO] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                    getResource("/anyviewj/interfaces/resources/edit/Redo.gif")); //重做
        icons[CUT] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                   getResource("/anyviewj/interfaces/resources/edit/Cut.gif")); //剪切
        icons[COPY] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                    getResource("/anyviewj/interfaces/resources/edit/Copy.gif")); //复制
        icons[PASTE] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                     getResource("/anyviewj/interfaces/resources/edit/Paste.gif")); //粘贴
        icons[DELETE] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                      getResource("/anyviewj/interfaces/resources/edit/Delete.gif")); //删除

        //====================查找菜单====================//
        icons[FIND] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                    getResource("/anyviewj/interfaces/resources/search/Find.gif")); //查找
        icons[FIND_INPATH] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                           getResource("/anyviewj/interfaces/resources/search/FindInPath.gif")); //在指定路径查找
        icons[FINDDOWN] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                        getResource("/anyviewj/interfaces/resources/search/FindDown.gif")); //查找下一个
        icons[FINDUP] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                        getResource("/anyviewj/interfaces/resources/search/FindUp.gif")); //查找上一个
        icons[REPLACE] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                       getResource("/anyviewj/interfaces/resources/search/Replace.gif")); //替换
        icons[REPLACE_INPATH] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.
                                       class.getResource("/anyviewj/interfaces/resources/search/ReplaceInPath.gif")); //在指定路径替换
        icons[GOTO] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                    getResource("/anyviewj/interfaces/resources/search/Goto.gif")); //跳至

        //====================项目菜单====================//
        icons[COMPILE_PROJECT] = new ImageIcon(anyviewj.interfaces.resource.
                                               ResourceManager.class.
                                               getResource("/anyviewj/interfaces/resources/project/CompileProject.gif")); //编译项目
        icons[COMPILE_FILE] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                            getResource("/anyviewj/interfaces/resources/project/CompileFile.gif")); //编译文件
        icons[BUILD] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                     getResource("/anyviewj/interfaces/resources/project/Build.gif")); //建立项目
        icons[PROJECT_PROPERTIES] = new ImageIcon(anyviewj.interfaces.resource.
                                                  ResourceManager.class.
                                                  getResource("/anyviewj/interfaces/resources/project/PrjProperties.gif")); //项目属性

        //====================运行菜单====================//
        icons[RUN_PROJECT] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                           getResource("/anyviewj/interfaces/resources/run/Run.gif")); //运行项目
        icons[RUN_FILE] = icons[RUN_PROJECT];//运行文件

        icons[DEBUG_PROJECT] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                             getResource("/anyviewj/interfaces/resources/run/Debug.gif")); //调试项目
        icons[DEBUG_FILE] = icons[DEBUG_PROJECT]; //调试文件

        icons[DEBUG_PROJECT_TIME] = new ImageIcon(anyviewj.interfaces.resource.
                                                  ResourceManager.class.
                                                  getResource("/anyviewj/interfaces/resources/run/Time.gif")); //定时执行调试项目
        icons[DEBUG_FILE_TIME] = icons[DEBUG_PROJECT_TIME]; //定时执行调试文件

        icons[PAUSE] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                     getResource("/anyviewj/interfaces/resources/run/Pause.gif")); //暂停
        icons[STOP] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                    getResource("/anyviewj/interfaces/resources/run/Stop.gif")); //停止
        icons[STEPOVER] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                        getResource("/anyviewj/interfaces/resources/run/StepOver.gif")); //下一行
        icons[STEPINTO] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                        getResource("/anyviewj/interfaces/resources/run/StepInto.gif")); //步入，下一步
        icons[STEPOUT] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
        								getResource("/anyviewj/interfaces/resources/run/StepOut.gif")); //步出
        icons[SINGLE_INSTRUCTION] = new ImageIcon(anyviewj.interfaces.resource.
                                                  ResourceManager.class.
                                                  getResource("/anyviewj/interfaces/resources/run/SingleInst.gif")); //单指令
         
        icons[RUN_ANSWER] = new ImageIcon(anyviewj.interfaces.resource.
                ResourceManager.class.
                getResource("/anyviewj/interfaces/resources/run/SingleInst.gif")); //答案测试暂时使用单指令的图标
        
        //====================工具菜单====================//
        icons[CONFIG] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                      getResource("/anyviewj/interfaces/resources/tools/Config.gif")); //配置

        //====================视图菜单====================//
        icons[OUTPUT] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                      getResource("/anyviewj/interfaces/resources/view/Output.gif")); //输出窗口

        //====================帮助菜单====================//
        icons[HELP] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                    getResource("/anyviewj/interfaces/resources/helps/Help.gif")); //帮助
        icons[ABOUT] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                     getResource("/anyviewj/interfaces/resources/helps/About.gif")); //关于
    }

    private void loadNames(){
        //====================文件菜单====================//
        names[MENUFILE] = "文件"; //文件菜单
        names[NEW] = "新建"; //新建项目,类,接口,文件等
        names[NEW_PROJECT] = "新建项目"; //新建项目
        names[NEW_CLASS] = "新建类文件"; //新建类文件
        names[OPEN_PROJECT] = "打开项目"; //打开项目
        names[OPEN_FILE] = "打开文件"; //打开文件
        names[CLOSE_PROJECT] = "关闭项目"; //关闭项目
        names[CLOSE_FILE] = "关闭文件"; //关闭文件
        names[SAVE_PROJECT] = "保存项目"; //保存项目
        names[SAVE_FILE] = "保存文件"; //保存文件
        names[SAVE_ALL] = "保存全部文件"; //保存全部文件
        names[RELOGIN] = "重新登录"; //重新登录
        names[CHANGE_PASSWORD] = "修改密码"; //
        
        names[OPEN_TABLE] = "打开题目";//打开题目表
        names[UPLOAD] = "上传";
        names[NEW_HOMEWORK]="新建作业工程";//新建作业工程
        
        names[EXIT] = "退出";//退出

        //====================编辑菜单====================//
        names[MENUEDIT] = "编辑"; //编辑菜单
        names[UNDO] = "撤销"; //撤销
        names[REDO] = "重做"; //重做
        names[CUT] = "剪切"; //剪切
        names[COPY] = "复制"; //复制
        names[PASTE] = "粘贴"; //粘贴
        names[DELETE] = "删除"; //删除
        names[SELECTALL] = "全选";//全选

        //====================查找菜单====================//
        names[MENUSEARCH] = "查找"; //查找菜单
        names[FIND] = "查找"; //查找
        names[FIND_INPATH] = "在指定路径查找"; //在指定路径查找
        names[FINDDOWN] = "查找下一个"; //查找下一个
        names[FINDUP] = "查找上一个"; //查找上一个
        names[REPLACE] = "替换"; //替换
        names[REPLACE_INPATH] = "在指定路径替换"; //在指定路径替换
        names[GOTO] = "跳至"; //跳至

        //====================项目菜单====================//
        names[MENUPROJECT] = "项目"; //项目菜单
        names[COMPILE_PROJECT] = "编译项目"; //编译项目
        names[COMPILE_FILE] = "编译文件"; //编译文件
        names[BUILD] = "建立项目"; //建立项目
        names[PROJECT_PROPERTIES] = "项目属性"; //项目属性

        //====================运行菜单====================//
        names[MENURUN] = "运行"; //运行菜单
        names[RUN_PROJECT] = "运行项目"; //运行项目
        names[RUN_FILE] = "运行文件";//运行文件
        names[DEBUG_PROJECT] = "调试项目"; //调试项目
        names[DEBUG_FILE] = "调试文件"; //调试文件
        names[DEBUG_PROJECT_TIME] = "定时调试项目"; //定时执行调试项目
        names[DEBUG_FILE_TIME] = "定时调试文件"; //定时执行调试文件
        names[PAUSE] = "暂停"; //暂停
        names[STOP] = "停止"; //停止
        names[STEPOVER] = "下一行"; //下一行
        names[STEPINTO] = "步入"; //步入，下一步
        names[STEPOUT] = "步出"; //步出
        names[SINGLE_INSTRUCTION] = "单指令"; //单指令
        names[RUN_ANSWER] = "单步运行";

        //====================工具菜单====================//
        names[MENUTOOLS] = "工具"; //工具菜单
        names[CONFIG] = "配置"; //配置

        //====================视图菜单====================//
        names[MENUVIEW] = "视图"; //视图菜单
        names[OUTPUT] = "输出窗口"; //输出窗口

        //====================帮助菜单====================//
        names[MENUHELP] = "帮助"; //帮助菜单
        names[HELP] = "帮助"; //帮助
        names[ABOUT] = "关于"; //关于
    }

    private void loadDescs(){
        //====================文件菜单====================//
        descs[NEW] = "新建项目,类,接口,文件等"; //新建项目,类,接口,文件等
        descs[NEW_PROJECT] = "新建项目"; //新建项目
        descs[NEW_CLASS] = "新建类文件"; //新建类文件
        descs[OPEN_PROJECT] = "打开项目"; //打开项目
        descs[OPEN_FILE] = "打开文件"; //打开文件
        descs[CLOSE_PROJECT] = "关闭项目"; //关闭项目
        descs[CLOSE_FILE] = "关闭文件"; //关闭文件
        descs[SAVE_PROJECT] = "保存项目"; //保存项目
        descs[SAVE_FILE] = "保存文件"; //保存文件
        descs[SAVE_ALL] = "保存全部文件"; //保存全部文件
        descs[RELOGIN] = "重新登录"; //重新登录
        descs[CHANGE_PASSWORD] = "修改密码"; //修改密码
        
        descs[OPEN_TABLE] = "打开题目";
        descs[UPLOAD] = "上传";
        names[NEW_HOMEWORK]="新建作业工程";
        
        descs[EXIT] = "退出";//退出
        //====================编辑菜单====================//
        descs[UNDO] = "撤销"; //撤销
        descs[REDO] = "重做"; //重做
        descs[CUT] = "剪切"; //剪切
        descs[COPY] = "复制"; //复制
        descs[PASTE] = "粘贴"; //粘贴
        descs[DELETE] = "删除"; //删除
        descs[SELECTALL] = "全选"; //全选

        //====================查找菜单====================//
        descs[FIND] = "查找"; //查找
        descs[FIND_INPATH] = "在指定路径查找"; //在指定路径查找
        descs[FINDDOWN] = "查找下一个"; //查找下一个
        descs[FINDUP] = "查找上一个"; //查找上一个
        descs[REPLACE] = "替换"; //替换
        descs[REPLACE_INPATH] = "在指定路径替换"; //在指定路径替换
        descs[GOTO] = "跳至"; //跳至

        //====================项目菜单====================//
        descs[COMPILE_PROJECT] = "编译项目"; //编译项目
        descs[COMPILE_FILE] = "编译文件"; //编译文件
        descs[BUILD] = "建立项目"; //建立项目
        descs[PROJECT_PROPERTIES] = "项目属性"; //项目属性

        //====================运行菜单====================//
        descs[RUN_PROJECT] = "运行项目"; //运行项目
        descs[RUN_FILE] = "运行文件";//运行文件
        descs[DEBUG_PROJECT] = "调试项目"; //调试项目
        descs[DEBUG_FILE] = "调试文件"; //调试文件

        descs[DEBUG_PROJECT_TIME] = "定时调试项目"; //定时执行调试项目
        descs[DEBUG_FILE_TIME] = "定时调试文件"; //定时执行调试文件
        descs[PAUSE] = "暂停"; //暂停
        descs[STOP] = "停止"; //停止
        descs[STEPOVER] = "下一行"; //下一行
        descs[STEPINTO] = "步入"; //步入，下一步
        descs[STEPOUT] = "步出"; //步出
        descs[SINGLE_INSTRUCTION] = "单指令"; //单指令
        
        descs[RUN_ANSWER] = "单步运行";

        //====================工具菜单====================//
        descs[CONFIG] = "配置"; //配置

        //====================视图菜单====================//
        descs[OUTPUT] = "输出窗口"; //输出窗口

        //====================帮助菜单====================//
        descs[HELP] = "帮助"; //帮助
        descs[ABOUT] = "关于"; //关于
    }
}
