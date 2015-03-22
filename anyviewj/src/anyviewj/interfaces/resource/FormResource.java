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
public abstract class FormResource {
    //起始索引
    protected static final int LEFTPART_PROJECT_MANAGER = 0;//主面板--左部--项目管理器
    protected static final int LEFTPART_DEBUG_MANAGER = 10;//主面板--左部--调试管理器
    protected static final int RIGHTPART_CODE_MANAGER = 20;//主面板--右部--代码管理器
    protected static final int RIGHTPART_GROUP_MANAGER = 30;//主面板--右部--输入输出管理器

    //索引数组最大值
    public static final int INDEXES_TOTAL_COUNT = 50;

    //主面板--左部
    ///////////////////////////////////////////////////////////////////
        //主面板--左部--项目管理器
        public static final int PROJECT_INFOS = LEFTPART_PROJECT_MANAGER + 0;//项目信息
        public static final int PROJECT_INFOS_STRUCT = LEFTPART_PROJECT_MANAGER + 1;//项目结构
        public static final int PROJECT_INFOS_CLASSINFOS = LEFTPART_PROJECT_MANAGER + 2;//类数据
        public static final int PROJECT_INFOS_DOCUMENT = LEFTPART_PROJECT_MANAGER + 3;//项目文档

        //主面板--左部--调试管理器
        public static final int DEBUG_MANAGER = LEFTPART_DEBUG_MANAGER + 0;//调试管理器
        public static final int DEBUG_MANAGER_GLOBAL = LEFTPART_DEBUG_MANAGER + 1;//全局
        public static final int DEBUG_MANAGER_STACKFRAME = LEFTPART_DEBUG_MANAGER + 2;//线程组
        public static final int DEBUG_MANAGER_HEAP = LEFTPART_DEBUG_MANAGER + 3;//对象堆
        public static final int DEBUG_MANAGER_ARRAYS = LEFTPART_DEBUG_MANAGER + 4;//数组
        public static final int DEBUG_MANAGER_DATASTRUCT = LEFTPART_DEBUG_MANAGER + 5;//数据结构

        //主面板--右部--代码管理器 RIGHTPART_CODE_MANAGER
        public static final int CODEPANE_SOURCEFILE = RIGHTPART_CODE_MANAGER +0;//代码编辑器类型选择:源文件(源文件/类文件)
        public static final int CODEPANE_CLASSFILE = RIGHTPART_CODE_MANAGER +1;//代码编辑器类型选择:类文件(源文件/类文件)

        //主面板--右部--输入输出管理器 RIGHTPART_GROUP_MANAGER
        public static final int OUTPUT_FORM = RIGHTPART_GROUP_MANAGER + 0;        
        public static final int CLASS_FORM = RIGHTPART_GROUP_MANAGER + 1;
        public static final int BREAKPOINT_FORM = RIGHTPART_GROUP_MANAGER + 2;
        public static final int Locals_FORM = RIGHTPART_GROUP_MANAGER + 3;
        
    public abstract String getNames(int index);
    public abstract String getTips(int index);
}
