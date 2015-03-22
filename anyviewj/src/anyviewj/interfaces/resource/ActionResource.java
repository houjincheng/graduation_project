package anyviewj.interfaces.resource;

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

import javax.swing.Icon;

public abstract class ActionResource {

    /**
     * 注意:这里没有使用HashMap而使用数组来保存图标.因此对索引值需要进行人工设定.
     * 索引值的不重复,不混乱,不过界尤为重要.
     * IMAGES_COUNT是默认数组的大小,当增加索引时要适当修改该值,以防越界.
     * 下面按分类编写索引值.如果某类的索引大于下一类的起始索引,就要适当调整.
     * 每添加一个索引值,需要修改以下位置:
     * (1).本类的initActionKeys()中添加命令键
     * (2).在子类的ActionName中增加相应的字符串
     * (3).在子类的ActionShortDesc中增加相应的字符串
     * (4).根据需要,在子类的ImageIcon中增加相应的图标
     */

    //起始索引值
    protected static final int BASE_FILE_INDEX = 0; //文件菜单 起始索引值
    protected static final int BASE_EDIT_INDEX = 20; //编辑菜单 起始索引值
    protected static final int BASE_SEARCH_INDEX = 40; //查找菜单 起始索引值
    protected static final int BASE_PROJECT_INDEX = 60; //项目菜单 起始索引值
    protected static final int BASE_RUN_INDEX = 80; //运行菜单 起始索引值
    protected static final int BASE_TOOLS_INDEX = 100; //工具菜单 起始索引值
    protected static final int BASE_VIEW_INDEX = 120; //视图菜单 起始索引值
    protected static final int BASE_HELP_INDEX = 140; //帮助菜单 起始索引值
    //数组总大小
    public static final int INDEXES_TOTAL_COUNT = 150; //默认数组的大小,当增加索引时要修改
    //文件菜单:0-19
    public static final int MENUFILE = BASE_FILE_INDEX + 0; //文件菜单
    public static final int NEW = BASE_FILE_INDEX + 1; //新建项目,类,接口,文件等
    public static final int NEW_PROJECT = BASE_FILE_INDEX + 2; //新建项目
    public static final int NEW_CLASS = BASE_FILE_INDEX + 3; //新建类文件
    public static final int OPEN_PROJECT = BASE_FILE_INDEX + 5; //打开项目
    public static final int OPEN_FILE = BASE_FILE_INDEX + 6; //打开文件
    public static final int CLOSE_PROJECT = BASE_FILE_INDEX + 8; //关闭项目
    public static final int CLOSE_FILE = BASE_FILE_INDEX + 9; //关闭文件
    public static final int SAVE_PROJECT = BASE_FILE_INDEX + 10; //保存项目
    public static final int SAVE_FILE = BASE_FILE_INDEX + 11; //保存文件
    public static final int SAVE_ALL = BASE_FILE_INDEX + 12; //保存全部文件
    public static final int RELOGIN = BASE_FILE_INDEX + 13; //重新登录
    public static final int CHANGE_PASSWORD = BASE_FILE_INDEX + 14; //修改密码
    
    public static final int OPEN_TABLE = BASE_FILE_INDEX + 15;//打开题目表
    
    public static final int UPLOAD = BASE_FILE_INDEX + 16;
    public static final int NEW_HOMEWORK = BASE_FILE_INDEX + 17;//新建作业工程
    
    public static final int EXIT = BASE_FILE_INDEX + 19;//退出
    //编辑菜单:0-19
    public static final int MENUEDIT = BASE_EDIT_INDEX + 0; //编辑菜单
    public static final int UNDO = BASE_EDIT_INDEX + 1; //撤销
    public static final int REDO = BASE_EDIT_INDEX + 2; //重做
    public static final int CUT = BASE_EDIT_INDEX + 3; //剪切
    public static final int COPY = BASE_EDIT_INDEX + 4; //复制
    public static final int PASTE = BASE_EDIT_INDEX + 5; //粘贴
    public static final int DELETE = BASE_EDIT_INDEX + 6; //删除
    public static final int SELECTALL = BASE_EDIT_INDEX + 7;//全选
    //查找菜单:0-19
    public static final int MENUSEARCH = BASE_SEARCH_INDEX + 0; //查找菜单
    public static final int FIND = BASE_SEARCH_INDEX + 1; //查找
    public static final int FIND_INPATH = BASE_SEARCH_INDEX + 2; //在指定路径查找
    public static final int FINDDOWN = BASE_SEARCH_INDEX + 3; //查找下一个
    public static final int FINDUP = BASE_SEARCH_INDEX + 4; //查找上一个
    public static final int REPLACE = BASE_SEARCH_INDEX + 5; //替换
    public static final int REPLACE_INPATH = BASE_SEARCH_INDEX + 6; //在指定路径替换
    public static final int GOTO = BASE_SEARCH_INDEX + 7; //跳至
    //项目菜单0-19
    public static final int MENUPROJECT = BASE_PROJECT_INDEX + 0; //项目菜单
    public static final int COMPILE_PROJECT = BASE_PROJECT_INDEX + 1; //编译项目
    public static final int COMPILE_FILE = BASE_PROJECT_INDEX + 2; //编译文件
    public static final int BUILD = BASE_PROJECT_INDEX + 3; //建立项目
    public static final int PROJECT_PROPERTIES = BASE_PROJECT_INDEX + 4; //项目属性
    //运行菜单:0-19
    public static final int MENURUN = BASE_RUN_INDEX + 0; //运行菜单
    public static final int RUN_PROJECT = BASE_RUN_INDEX + 1; //运行项目
    public static final int RUN_FILE = BASE_RUN_INDEX + 2; //运行文件
    public static final int DEBUG_PROJECT = BASE_RUN_INDEX + 3; //调试项目
    public static final int DEBUG_FILE = BASE_RUN_INDEX + 4; //调试文件
    public static final int DEBUG_PROJECT_TIME = BASE_RUN_INDEX + 5; //定时执行调试项目
    public static final int DEBUG_FILE_TIME = BASE_RUN_INDEX + 6; //定时执行调试文件
    public static final int PAUSE = BASE_RUN_INDEX + 7; //暂停
    public static final int STOP = BASE_RUN_INDEX + 8; //停止
    public static final int STEPOVER = BASE_RUN_INDEX + 9; //下一行
    public static final int STEPINTO = BASE_RUN_INDEX + 10; //步入下一步
    public static final int STEPOUT = BASE_RUN_INDEX + 11; //步出
    public static final int SINGLE_INSTRUCTION = BASE_RUN_INDEX + 12; //单指令
    
    public static final int RUN_ANSWER = BASE_RUN_INDEX + 13;
    
    //工具菜单:0-19
    public static final int MENUTOOLS = BASE_TOOLS_INDEX + 0; //工具菜单
    public static final int CONFIG = BASE_TOOLS_INDEX + 1; //配置
    //视图菜单:0-19
    public static final int MENUVIEW = BASE_VIEW_INDEX + 0; //视图菜单
    public static final int OUTPUT = BASE_VIEW_INDEX + 1; //输出窗口
    //帮助菜单:0-9
    public static final int MENUHELP = BASE_HELP_INDEX + 0; //帮助菜单
    public static final int HELP = BASE_HELP_INDEX + 1; //帮助
    public static final int ABOUT = BASE_HELP_INDEX + 2; //关于
	

    //命令键是统一的
    private final String[] ActionKeys = new String[INDEXES_TOTAL_COUNT];

    public ActionResource() {
        initActionKeys();
    }

    /**
     * 获取命令键
     * @param index int
     * @return String
     */
    public String getActionKey(int index) {
        return ActionKeys[index];
    }

    public abstract Icon getIcon(int index); //获取图标

    public abstract String getActionName(int index); //获取名称

    public abstract String getActionDesc(int index); //获取提示

    private void initActionKeys() {
        //文件菜单:0-19
        ActionKeys[MENUFILE] = "MENUFILE_ACTION"; //文件菜单
        ActionKeys[NEW] = "NEW_ACTION"; //新建项目,类,接口,文件等
        ActionKeys[NEW_PROJECT] = "NEW_PROJECT_ACTION"; //新建项目
        ActionKeys[NEW_CLASS] = "NEW_CLASS_ACTION"; //新建类文件
        ActionKeys[OPEN_PROJECT] = "OPEN_PROJECT_ACTION"; //打开项目
        ActionKeys[OPEN_FILE] = "OPEN_FILE_ACTION"; //打开文件
        ActionKeys[CLOSE_PROJECT] = "CLOSE_PROJECT_ACTION"; //关闭项目
        ActionKeys[CLOSE_FILE] = "CLOSE_FILE_ACTION"; //关闭文件
        ActionKeys[SAVE_PROJECT] = "SAVE_PROJECT_ACTION"; //保存项目
        ActionKeys[SAVE_FILE] = "SAVE_FILE_ACTION"; //保存文件
        ActionKeys[SAVE_ALL] = "SAVE_ALL_ACTION"; //保存全部文件
        ActionKeys[RELOGIN] = "RELOGIN"; //重新登录
        ActionKeys[CHANGE_PASSWORD] = "CHANGE_PASSWORD_ACTION"; 
        
        ActionKeys[OPEN_TABLE] = "OPEN_TABLE_ACTION";//打开题目表
        ActionKeys[UPLOAD]="UPLOAD_ACTION"; //上传
        ActionKeys[NEW_HOMEWORK] = "NEW_HOMEWORK_ACTION";//新建作业工程
        
        ActionKeys[EXIT] = "EXIT_ACTION";//退出
        //编辑菜单:0-19
        ActionKeys[MENUEDIT] = "MENUEDIT_ACTION"; //编辑菜单
        ActionKeys[UNDO] = "UNDO_ACTION"; //撤销
        ActionKeys[REDO] = "REDO_ACTION"; //重做
        ActionKeys[CUT] = "CUT_ACTION"; //剪切
        ActionKeys[COPY] = "COPY_ACTION"; //复制
        ActionKeys[PASTE] = "PASTE_ACTION"; //粘贴
        ActionKeys[DELETE] = "DELETE_ACTION"; //删除
        ActionKeys[SELECTALL] = "SELETEALL_ACTION"; //粘贴
        //查找菜单:0-19
        ActionKeys[MENUSEARCH] = "MENUSEARCH_ACTION"; //查找菜单
        ActionKeys[FIND] = "FIND_ACTION"; //查找
        ActionKeys[FIND_INPATH] = "FIND_INPATH_ACTION"; //在指定路径查找
        ActionKeys[FINDDOWN] = "FINDDOWN_ACTION"; //查找下一个
        ActionKeys[FINDUP] = "FINDUP_ACTION"; //查找上一个
        ActionKeys[REPLACE] = "REPLACE_ACTION"; //替换
        ActionKeys[REPLACE_INPATH] = "REPLACE_INPATH_ACTION"; //在指定路径替换
        ActionKeys[GOTO] = "GOTO_ACTION"; //跳至
        //项目菜单0-19
        ActionKeys[MENUPROJECT] = "MENUPROJECT_ACTION"; //项目菜单
        ActionKeys[COMPILE_PROJECT] = "COMPILE_PROJECT_ACTION"; //编译项目
        ActionKeys[COMPILE_FILE] = "COMPILE_FILE_ACTION"; //编译文件
        ActionKeys[BUILD] = "BUILD_ACTION"; //建立项目
        ActionKeys[PROJECT_PROPERTIES] = "PROJECT_PROPERTIES_ACTION"; //项目属性
        //运行菜单:0-19
        ActionKeys[MENURUN] = "MENURUN_PROJECT_ACTION"; //运行菜单
        ActionKeys[RUN_PROJECT] = "RUN_PROJECT_ACTION"; //运行项目
        ActionKeys[RUN_FILE] = "RUN_FILE_ACTION"; //运行文件
        ActionKeys[DEBUG_PROJECT] = "DEBUG_PROJECT_ACTION"; //调试项目
        ActionKeys[DEBUG_FILE] = "DEBUG_FILE_ACTION"; //调试文件
        ActionKeys[DEBUG_PROJECT_TIME] = "DEBUG_PROJECT_TIME_ACTION"; //定时执行调试项目
        ActionKeys[DEBUG_FILE_TIME] = "DEBUG_FILE_TIME_ACTION"; //定时执行调试文件
        ActionKeys[PAUSE] = "PAUSE_ACTION"; //暂停
        ActionKeys[STOP] = "STOP_ACTION"; //停止
        ActionKeys[STEPOVER] = "STEPOVER_ACTION"; //下一行
        ActionKeys[STEPINTO] = "STEPINTO_ACTION"; //下一步
        ActionKeys[STEPOUT] = "STEPOUT_ACTION"; //步出
        ActionKeys[SINGLE_INSTRUCTION] = "SINGLE_INSTRUCTION_ACTION"; //单指令
        //工具菜单:0-19
        ActionKeys[MENUTOOLS] = "MENUTOOLS_ACTION"; //工具菜单
        ActionKeys[CONFIG] = "CONFIG_ACTION"; //配置
        //视图菜单:0-19
        ActionKeys[MENUVIEW] = "MENUVIEW_ACTION"; //输出窗口
        ActionKeys[OUTPUT] = "OUTPUT_ACTION"; //输出窗口
        //帮助菜单:0-9
        ActionKeys[MENUHELP] = "MENUHELP_ACTION"; //帮助菜单
        ActionKeys[HELP] = "HELP_ACTION"; //帮助
        ActionKeys[ABOUT] = "ABOUT_ACTION"; //关于
    }

}
