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
        names[PROJECT_INFOS] = "��Ŀ��Ϣ";
        tips[PROJECT_INFOS] = "";
            names[FormResource.PROJECT_INFOS_STRUCT] = "��Ŀ�ṹ";
            tips[PROJECT_INFOS_STRUCT] = "";
            names[FormResource.PROJECT_INFOS_CLASSINFOS] = "������";
            tips[PROJECT_INFOS_CLASSINFOS] = "";
            names[FormResource.PROJECT_INFOS_DOCUMENT] = "��Ŀ�ĵ�";
            tips[PROJECT_INFOS_DOCUMENT] = "";

        names[DEBUG_MANAGER] = "������";
        tips[DEBUG_MANAGER] = "";
            names[FormResource.DEBUG_MANAGER_GLOBAL] = "ȫ��";
            tips[DEBUG_MANAGER_GLOBAL] = "";
            names[FormResource.DEBUG_MANAGER_STACKFRAME] = "����ջ֡";
            tips[DEBUG_MANAGER_STACKFRAME] = "";
            names[FormResource.DEBUG_MANAGER_HEAP] = "�����";
            tips[DEBUG_MANAGER_HEAP] = "";
            names[FormResource.DEBUG_MANAGER_ARRAYS] = "����";
            tips[DEBUG_MANAGER_ARRAYS] = "";
            names[FormResource.DEBUG_MANAGER_DATASTRUCT] = "���ݽṹ";
            tips[DEBUG_MANAGER_DATASTRUCT] = "";

        //�����--�Ҳ�--���������
            names[CODEPANE_SOURCEFILE] = "source";//����༭������ѡ��:Դ�ļ�(Դ�ļ�/���ļ�)
            tips[CODEPANE_SOURCEFILE] = "";//����༭������ѡ��:Դ�ļ�(Դ�ļ�/���ļ�)
            names[CODEPANE_CLASSFILE] = "class";//����༭������ѡ��:���ļ�(Դ�ļ�/���ļ�)
            tips[CODEPANE_CLASSFILE] = "";//����༭������ѡ��:���ļ�(Դ�ļ�/���ļ�)

        names[FormResource.OUTPUT_FORM] = "�����������";
        tips[OUTPUT_FORM] = "";

        names[FormResource.CLASS_FORM] = "��ṹ����";
        tips[CLASS_FORM] = "";
        
        names[FormResource.BREAKPOINT_FORM] = "�ϵ㴰��";
        tips[BREAKPOINT_FORM] = "";
        
        names[FormResource.Locals_FORM] = "��������";
        tips[Locals_FORM] = " ";
        		
       
    }
}
