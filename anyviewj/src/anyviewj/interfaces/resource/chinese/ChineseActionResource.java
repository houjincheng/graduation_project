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
    private ImageIcon[] icons = new ImageIcon[INDEXES_TOTAL_COUNT];//ͼ��
    private String[] names = new String[INDEXES_TOTAL_COUNT];//����
    private String[] descs = new String[INDEXES_TOTAL_COUNT];//��ʾ

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
        //====================�ļ��˵�====================//
        icons[NEW] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                   getResource("/anyviewj/interfaces/resources/file/New.gif")); //�½���Ŀ,��,�ӿ�,�ļ���

        icons[NEW_PROJECT] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                           getResource("/anyviewj/interfaces/resources/file/NewProject.gif")); //�½���Ŀ

        icons[NEW_CLASS] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                         getResource("/anyviewj/interfaces/resources/file/NewClass.gif")); //�½����ļ�

        icons[OPEN_PROJECT] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                            getResource(
                "/anyviewj/interfaces/resources/file/OpenProject.gif")); //����Ŀ

        icons[OPEN_FILE] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                         getResource("/anyviewj/interfaces/resources/file/OpenFile.gif")); //���ļ�

        icons[CLOSE_PROJECT] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                             getResource(
                "/anyviewj/interfaces/resources/file/CloseProject.gif")); //�ر���Ŀ

        icons[CLOSE_FILE] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                          getResource("/anyviewj/interfaces/resources/file/CloseFile.gif")); //�ر��ļ�

        icons[SAVE_PROJECT] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                            getResource(
                "/anyviewj/interfaces/resources/file/SaveProject.gif")); //������Ŀ

        icons[SAVE_FILE] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                         getResource("/anyviewj/interfaces/resources/file/SaveFile.gif")); //�����ļ�

        icons[SAVE_ALL] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                        getResource("/anyviewj/interfaces/resources/file/SaveAll.gif")); //����ȫ���ļ�
        
        icons[RELOGIN] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
        		getResource("/anyviewj/net/client/resource/relogin.gif")); //���µ�¼
        
        icons[CHANGE_PASSWORD] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
        		getResource("/anyviewj/net/client/resource/changePassword.gif")); //�޸�����
        
        //icons[UPLOAD] =  icons[SAVE_ALL];

        //====================�༭�˵�====================//
        icons[UNDO] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                    getResource("/anyviewj/interfaces/resources/edit/Undo.gif")); //����
        icons[REDO] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                    getResource("/anyviewj/interfaces/resources/edit/Redo.gif")); //����
        icons[CUT] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                   getResource("/anyviewj/interfaces/resources/edit/Cut.gif")); //����
        icons[COPY] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                    getResource("/anyviewj/interfaces/resources/edit/Copy.gif")); //����
        icons[PASTE] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                     getResource("/anyviewj/interfaces/resources/edit/Paste.gif")); //ճ��
        icons[DELETE] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                      getResource("/anyviewj/interfaces/resources/edit/Delete.gif")); //ɾ��

        //====================���Ҳ˵�====================//
        icons[FIND] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                    getResource("/anyviewj/interfaces/resources/search/Find.gif")); //����
        icons[FIND_INPATH] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                           getResource("/anyviewj/interfaces/resources/search/FindInPath.gif")); //��ָ��·������
        icons[FINDDOWN] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                        getResource("/anyviewj/interfaces/resources/search/FindDown.gif")); //������һ��
        icons[FINDUP] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                        getResource("/anyviewj/interfaces/resources/search/FindUp.gif")); //������һ��
        icons[REPLACE] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                       getResource("/anyviewj/interfaces/resources/search/Replace.gif")); //�滻
        icons[REPLACE_INPATH] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.
                                       class.getResource("/anyviewj/interfaces/resources/search/ReplaceInPath.gif")); //��ָ��·���滻
        icons[GOTO] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                    getResource("/anyviewj/interfaces/resources/search/Goto.gif")); //����

        //====================��Ŀ�˵�====================//
        icons[COMPILE_PROJECT] = new ImageIcon(anyviewj.interfaces.resource.
                                               ResourceManager.class.
                                               getResource("/anyviewj/interfaces/resources/project/CompileProject.gif")); //������Ŀ
        icons[COMPILE_FILE] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                            getResource("/anyviewj/interfaces/resources/project/CompileFile.gif")); //�����ļ�
        icons[BUILD] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                     getResource("/anyviewj/interfaces/resources/project/Build.gif")); //������Ŀ
        icons[PROJECT_PROPERTIES] = new ImageIcon(anyviewj.interfaces.resource.
                                                  ResourceManager.class.
                                                  getResource("/anyviewj/interfaces/resources/project/PrjProperties.gif")); //��Ŀ����

        //====================���в˵�====================//
        icons[RUN_PROJECT] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                           getResource("/anyviewj/interfaces/resources/run/Run.gif")); //������Ŀ
        icons[RUN_FILE] = icons[RUN_PROJECT];//�����ļ�

        icons[DEBUG_PROJECT] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                             getResource("/anyviewj/interfaces/resources/run/Debug.gif")); //������Ŀ
        icons[DEBUG_FILE] = icons[DEBUG_PROJECT]; //�����ļ�

        icons[DEBUG_PROJECT_TIME] = new ImageIcon(anyviewj.interfaces.resource.
                                                  ResourceManager.class.
                                                  getResource("/anyviewj/interfaces/resources/run/Time.gif")); //��ʱִ�е�����Ŀ
        icons[DEBUG_FILE_TIME] = icons[DEBUG_PROJECT_TIME]; //��ʱִ�е����ļ�

        icons[PAUSE] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                     getResource("/anyviewj/interfaces/resources/run/Pause.gif")); //��ͣ
        icons[STOP] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                    getResource("/anyviewj/interfaces/resources/run/Stop.gif")); //ֹͣ
        icons[STEPOVER] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                        getResource("/anyviewj/interfaces/resources/run/StepOver.gif")); //��һ��
        icons[STEPINTO] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                        getResource("/anyviewj/interfaces/resources/run/StepInto.gif")); //���룬��һ��
        icons[STEPOUT] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
        								getResource("/anyviewj/interfaces/resources/run/StepOut.gif")); //����
        icons[SINGLE_INSTRUCTION] = new ImageIcon(anyviewj.interfaces.resource.
                                                  ResourceManager.class.
                                                  getResource("/anyviewj/interfaces/resources/run/SingleInst.gif")); //��ָ��
         
        icons[RUN_ANSWER] = new ImageIcon(anyviewj.interfaces.resource.
                ResourceManager.class.
                getResource("/anyviewj/interfaces/resources/run/SingleInst.gif")); //�𰸲�����ʱʹ�õ�ָ���ͼ��
        
        //====================���߲˵�====================//
        icons[CONFIG] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                      getResource("/anyviewj/interfaces/resources/tools/Config.gif")); //����

        //====================��ͼ�˵�====================//
        icons[OUTPUT] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                      getResource("/anyviewj/interfaces/resources/view/Output.gif")); //�������

        //====================�����˵�====================//
        icons[HELP] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                    getResource("/anyviewj/interfaces/resources/helps/Help.gif")); //����
        icons[ABOUT] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                     getResource("/anyviewj/interfaces/resources/helps/About.gif")); //����
    }

    private void loadNames(){
        //====================�ļ��˵�====================//
        names[MENUFILE] = "�ļ�"; //�ļ��˵�
        names[NEW] = "�½�"; //�½���Ŀ,��,�ӿ�,�ļ���
        names[NEW_PROJECT] = "�½���Ŀ"; //�½���Ŀ
        names[NEW_CLASS] = "�½����ļ�"; //�½����ļ�
        names[OPEN_PROJECT] = "����Ŀ"; //����Ŀ
        names[OPEN_FILE] = "���ļ�"; //���ļ�
        names[CLOSE_PROJECT] = "�ر���Ŀ"; //�ر���Ŀ
        names[CLOSE_FILE] = "�ر��ļ�"; //�ر��ļ�
        names[SAVE_PROJECT] = "������Ŀ"; //������Ŀ
        names[SAVE_FILE] = "�����ļ�"; //�����ļ�
        names[SAVE_ALL] = "����ȫ���ļ�"; //����ȫ���ļ�
        names[RELOGIN] = "���µ�¼"; //���µ�¼
        names[CHANGE_PASSWORD] = "�޸�����"; //
        
        names[OPEN_TABLE] = "����Ŀ";//����Ŀ��
        names[UPLOAD] = "�ϴ�";
        names[NEW_HOMEWORK]="�½���ҵ����";//�½���ҵ����
        
        names[EXIT] = "�˳�";//�˳�

        //====================�༭�˵�====================//
        names[MENUEDIT] = "�༭"; //�༭�˵�
        names[UNDO] = "����"; //����
        names[REDO] = "����"; //����
        names[CUT] = "����"; //����
        names[COPY] = "����"; //����
        names[PASTE] = "ճ��"; //ճ��
        names[DELETE] = "ɾ��"; //ɾ��
        names[SELECTALL] = "ȫѡ";//ȫѡ

        //====================���Ҳ˵�====================//
        names[MENUSEARCH] = "����"; //���Ҳ˵�
        names[FIND] = "����"; //����
        names[FIND_INPATH] = "��ָ��·������"; //��ָ��·������
        names[FINDDOWN] = "������һ��"; //������һ��
        names[FINDUP] = "������һ��"; //������һ��
        names[REPLACE] = "�滻"; //�滻
        names[REPLACE_INPATH] = "��ָ��·���滻"; //��ָ��·���滻
        names[GOTO] = "����"; //����

        //====================��Ŀ�˵�====================//
        names[MENUPROJECT] = "��Ŀ"; //��Ŀ�˵�
        names[COMPILE_PROJECT] = "������Ŀ"; //������Ŀ
        names[COMPILE_FILE] = "�����ļ�"; //�����ļ�
        names[BUILD] = "������Ŀ"; //������Ŀ
        names[PROJECT_PROPERTIES] = "��Ŀ����"; //��Ŀ����

        //====================���в˵�====================//
        names[MENURUN] = "����"; //���в˵�
        names[RUN_PROJECT] = "������Ŀ"; //������Ŀ
        names[RUN_FILE] = "�����ļ�";//�����ļ�
        names[DEBUG_PROJECT] = "������Ŀ"; //������Ŀ
        names[DEBUG_FILE] = "�����ļ�"; //�����ļ�
        names[DEBUG_PROJECT_TIME] = "��ʱ������Ŀ"; //��ʱִ�е�����Ŀ
        names[DEBUG_FILE_TIME] = "��ʱ�����ļ�"; //��ʱִ�е����ļ�
        names[PAUSE] = "��ͣ"; //��ͣ
        names[STOP] = "ֹͣ"; //ֹͣ
        names[STEPOVER] = "��һ��"; //��һ��
        names[STEPINTO] = "����"; //���룬��һ��
        names[STEPOUT] = "����"; //����
        names[SINGLE_INSTRUCTION] = "��ָ��"; //��ָ��
        names[RUN_ANSWER] = "��������";

        //====================���߲˵�====================//
        names[MENUTOOLS] = "����"; //���߲˵�
        names[CONFIG] = "����"; //����

        //====================��ͼ�˵�====================//
        names[MENUVIEW] = "��ͼ"; //��ͼ�˵�
        names[OUTPUT] = "�������"; //�������

        //====================�����˵�====================//
        names[MENUHELP] = "����"; //�����˵�
        names[HELP] = "����"; //����
        names[ABOUT] = "����"; //����
    }

    private void loadDescs(){
        //====================�ļ��˵�====================//
        descs[NEW] = "�½���Ŀ,��,�ӿ�,�ļ���"; //�½���Ŀ,��,�ӿ�,�ļ���
        descs[NEW_PROJECT] = "�½���Ŀ"; //�½���Ŀ
        descs[NEW_CLASS] = "�½����ļ�"; //�½����ļ�
        descs[OPEN_PROJECT] = "����Ŀ"; //����Ŀ
        descs[OPEN_FILE] = "���ļ�"; //���ļ�
        descs[CLOSE_PROJECT] = "�ر���Ŀ"; //�ر���Ŀ
        descs[CLOSE_FILE] = "�ر��ļ�"; //�ر��ļ�
        descs[SAVE_PROJECT] = "������Ŀ"; //������Ŀ
        descs[SAVE_FILE] = "�����ļ�"; //�����ļ�
        descs[SAVE_ALL] = "����ȫ���ļ�"; //����ȫ���ļ�
        descs[RELOGIN] = "���µ�¼"; //���µ�¼
        descs[CHANGE_PASSWORD] = "�޸�����"; //�޸�����
        
        descs[OPEN_TABLE] = "����Ŀ";
        descs[UPLOAD] = "�ϴ�";
        names[NEW_HOMEWORK]="�½���ҵ����";
        
        descs[EXIT] = "�˳�";//�˳�
        //====================�༭�˵�====================//
        descs[UNDO] = "����"; //����
        descs[REDO] = "����"; //����
        descs[CUT] = "����"; //����
        descs[COPY] = "����"; //����
        descs[PASTE] = "ճ��"; //ճ��
        descs[DELETE] = "ɾ��"; //ɾ��
        descs[SELECTALL] = "ȫѡ"; //ȫѡ

        //====================���Ҳ˵�====================//
        descs[FIND] = "����"; //����
        descs[FIND_INPATH] = "��ָ��·������"; //��ָ��·������
        descs[FINDDOWN] = "������һ��"; //������һ��
        descs[FINDUP] = "������һ��"; //������һ��
        descs[REPLACE] = "�滻"; //�滻
        descs[REPLACE_INPATH] = "��ָ��·���滻"; //��ָ��·���滻
        descs[GOTO] = "����"; //����

        //====================��Ŀ�˵�====================//
        descs[COMPILE_PROJECT] = "������Ŀ"; //������Ŀ
        descs[COMPILE_FILE] = "�����ļ�"; //�����ļ�
        descs[BUILD] = "������Ŀ"; //������Ŀ
        descs[PROJECT_PROPERTIES] = "��Ŀ����"; //��Ŀ����

        //====================���в˵�====================//
        descs[RUN_PROJECT] = "������Ŀ"; //������Ŀ
        descs[RUN_FILE] = "�����ļ�";//�����ļ�
        descs[DEBUG_PROJECT] = "������Ŀ"; //������Ŀ
        descs[DEBUG_FILE] = "�����ļ�"; //�����ļ�

        descs[DEBUG_PROJECT_TIME] = "��ʱ������Ŀ"; //��ʱִ�е�����Ŀ
        descs[DEBUG_FILE_TIME] = "��ʱ�����ļ�"; //��ʱִ�е����ļ�
        descs[PAUSE] = "��ͣ"; //��ͣ
        descs[STOP] = "ֹͣ"; //ֹͣ
        descs[STEPOVER] = "��һ��"; //��һ��
        descs[STEPINTO] = "����"; //���룬��һ��
        descs[STEPOUT] = "����"; //����
        descs[SINGLE_INSTRUCTION] = "��ָ��"; //��ָ��
        
        descs[RUN_ANSWER] = "��������";

        //====================���߲˵�====================//
        descs[CONFIG] = "����"; //����

        //====================��ͼ�˵�====================//
        descs[OUTPUT] = "�������"; //�������

        //====================�����˵�====================//
        descs[HELP] = "����"; //����
        descs[ABOUT] = "����"; //����
    }
}
