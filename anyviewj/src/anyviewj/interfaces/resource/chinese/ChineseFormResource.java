package anyviewj.interfaces.resource.chinese;

import anyviewj.interfaces.resource.FormResource;

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
public class ChineseFormResource extends FormResource {
    private String[] names = new String[INDEXES_TOTAL_COUNT];
    private String[] tips = new String[INDEXES_TOTAL_COUNT];

    public ChineseFormResource() {
        loadResource();
    }

    /**
     * getComponentName
     *
     * @param index int
     * @return String
     * @todo Implement this anyviewj.resources.FormResource method
     */
    @Override
	public String getNames(int index) {
        return names[index];
    }

    /**
     * getComponentTip
     *
     * @param index int
     * @return String
     * @todo Implement this anyviewj.resources.FormResource method
     */
    @Override
	public String getTips(int index) {
        return tips[index];
    }

    private void loadResource(){
        names[PROJECT_INFOS] = "项目信息";
        tips[PROJECT_INFOS] = "";
            names[FormResource.PROJECT_INFOS_STRUCT] = "项目结构";
            tips[PROJECT_INFOS_STRUCT] = "";
            names[FormResource.PROJECT_INFOS_CLASSINFOS] = "类数据";
            tips[PROJECT_INFOS_CLASSINFOS] = "";
            names[FormResource.PROJECT_INFOS_DOCUMENT] = "项目文档";
            tips[PROJECT_INFOS_DOCUMENT] = "";

        names[DEBUG_MANAGER] = "调试器";
        tips[DEBUG_MANAGER] = "";
            names[FormResource.DEBUG_MANAGER_GLOBAL] = "全局";
            tips[DEBUG_MANAGER_GLOBAL] = "";
            names[FormResource.DEBUG_MANAGER_STACKFRAME] = "调用栈帧";
            tips[DEBUG_MANAGER_STACKFRAME] = "";
            names[FormResource.DEBUG_MANAGER_HEAP] = "对象堆";
            tips[DEBUG_MANAGER_HEAP] = "";
            names[FormResource.DEBUG_MANAGER_ARRAYS] = "数组";
            tips[DEBUG_MANAGER_ARRAYS] = "";
            names[FormResource.DEBUG_MANAGER_DATASTRUCT] = "数据结构";
            tips[DEBUG_MANAGER_DATASTRUCT] = "";

        //主面板--右部--代码管理器
            names[CODEPANE_SOURCEFILE] = "source";//代码编辑器类型选择:源文件(源文件/类文件)
            tips[CODEPANE_SOURCEFILE] = "";//代码编辑器类型选择:源文件(源文件/类文件)
            names[CODEPANE_CLASSFILE] = "class";//代码编辑器类型选择:类文件(源文件/类文件)
            tips[CODEPANE_CLASSFILE] = "";//代码编辑器类型选择:类文件(源文件/类文件)

        names[FormResource.OUTPUT_FORM] = "输入输出窗口";
        tips[OUTPUT_FORM] = "";

        names[FormResource.CLASS_FORM] = "类结构窗口";
        tips[CLASS_FORM] = "";
        
        names[FormResource.BREAKPOINT_FORM] = "断点窗口";
        tips[BREAKPOINT_FORM] = "";
        
        names[FormResource.Locals_FORM] = "变量窗口";
        tips[Locals_FORM] = " ";
        		
       
    }
}
