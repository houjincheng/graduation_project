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
     * ע��:����û��ʹ��HashMap��ʹ������������ͼ��.��˶�����ֵ��Ҫ�����˹��趨.
     * ����ֵ�Ĳ��ظ�,������,��������Ϊ��Ҫ.
     * IMAGES_COUNT��Ĭ������Ĵ�С,����������ʱҪ�ʵ��޸ĸ�ֵ,�Է�Խ��.
     * ���水�����д����ֵ.���ĳ�������������һ�����ʼ����,��Ҫ�ʵ�����.
     * ÿ���һ������ֵ,��Ҫ�޸�����λ��:
     * (1).�����initActionKeys()����������
     * (2).�������ActionName��������Ӧ���ַ���
     * (3).�������ActionShortDesc��������Ӧ���ַ���
     * (4).������Ҫ,�������ImageIcon��������Ӧ��ͼ��
     */

    //��ʼ����ֵ
    protected static final int BASE_FILE_INDEX = 0; //�ļ��˵� ��ʼ����ֵ
    protected static final int BASE_EDIT_INDEX = 20; //�༭�˵� ��ʼ����ֵ
    protected static final int BASE_SEARCH_INDEX = 40; //���Ҳ˵� ��ʼ����ֵ
    protected static final int BASE_PROJECT_INDEX = 60; //��Ŀ�˵� ��ʼ����ֵ
    protected static final int BASE_RUN_INDEX = 80; //���в˵� ��ʼ����ֵ
    protected static final int BASE_TOOLS_INDEX = 100; //���߲˵� ��ʼ����ֵ
    protected static final int BASE_VIEW_INDEX = 120; //��ͼ�˵� ��ʼ����ֵ
    protected static final int BASE_HELP_INDEX = 140; //�����˵� ��ʼ����ֵ
    //�����ܴ�С
    public static final int INDEXES_TOTAL_COUNT = 150; //Ĭ������Ĵ�С,����������ʱҪ�޸�
    //�ļ��˵�:0-19
    public static final int MENUFILE = BASE_FILE_INDEX + 0; //�ļ��˵�
    public static final int NEW = BASE_FILE_INDEX + 1; //�½���Ŀ,��,�ӿ�,�ļ���
    public static final int NEW_PROJECT = BASE_FILE_INDEX + 2; //�½���Ŀ
    public static final int NEW_CLASS = BASE_FILE_INDEX + 3; //�½����ļ�
    public static final int OPEN_PROJECT = BASE_FILE_INDEX + 5; //����Ŀ
    public static final int OPEN_FILE = BASE_FILE_INDEX + 6; //���ļ�
    public static final int CLOSE_PROJECT = BASE_FILE_INDEX + 8; //�ر���Ŀ
    public static final int CLOSE_FILE = BASE_FILE_INDEX + 9; //�ر��ļ�
    public static final int SAVE_PROJECT = BASE_FILE_INDEX + 10; //������Ŀ
    public static final int SAVE_FILE = BASE_FILE_INDEX + 11; //�����ļ�
    public static final int SAVE_ALL = BASE_FILE_INDEX + 12; //����ȫ���ļ�
    public static final int RELOGIN = BASE_FILE_INDEX + 13; //���µ�¼
    public static final int CHANGE_PASSWORD = BASE_FILE_INDEX + 14; //�޸�����
    
    public static final int OPEN_TABLE = BASE_FILE_INDEX + 15;//����Ŀ��
    
    public static final int UPLOAD = BASE_FILE_INDEX + 16;
    public static final int NEW_HOMEWORK = BASE_FILE_INDEX + 17;//�½���ҵ����
    
    public static final int EXIT = BASE_FILE_INDEX + 19;//�˳�
    //�༭�˵�:0-19
    public static final int MENUEDIT = BASE_EDIT_INDEX + 0; //�༭�˵�
    public static final int UNDO = BASE_EDIT_INDEX + 1; //����
    public static final int REDO = BASE_EDIT_INDEX + 2; //����
    public static final int CUT = BASE_EDIT_INDEX + 3; //����
    public static final int COPY = BASE_EDIT_INDEX + 4; //����
    public static final int PASTE = BASE_EDIT_INDEX + 5; //ճ��
    public static final int DELETE = BASE_EDIT_INDEX + 6; //ɾ��
    public static final int SELECTALL = BASE_EDIT_INDEX + 7;//ȫѡ
    //���Ҳ˵�:0-19
    public static final int MENUSEARCH = BASE_SEARCH_INDEX + 0; //���Ҳ˵�
    public static final int FIND = BASE_SEARCH_INDEX + 1; //����
    public static final int FIND_INPATH = BASE_SEARCH_INDEX + 2; //��ָ��·������
    public static final int FINDDOWN = BASE_SEARCH_INDEX + 3; //������һ��
    public static final int FINDUP = BASE_SEARCH_INDEX + 4; //������һ��
    public static final int REPLACE = BASE_SEARCH_INDEX + 5; //�滻
    public static final int REPLACE_INPATH = BASE_SEARCH_INDEX + 6; //��ָ��·���滻
    public static final int GOTO = BASE_SEARCH_INDEX + 7; //����
    //��Ŀ�˵�0-19
    public static final int MENUPROJECT = BASE_PROJECT_INDEX + 0; //��Ŀ�˵�
    public static final int COMPILE_PROJECT = BASE_PROJECT_INDEX + 1; //������Ŀ
    public static final int COMPILE_FILE = BASE_PROJECT_INDEX + 2; //�����ļ�
    public static final int BUILD = BASE_PROJECT_INDEX + 3; //������Ŀ
    public static final int PROJECT_PROPERTIES = BASE_PROJECT_INDEX + 4; //��Ŀ����
    //���в˵�:0-19
    public static final int MENURUN = BASE_RUN_INDEX + 0; //���в˵�
    public static final int RUN_PROJECT = BASE_RUN_INDEX + 1; //������Ŀ
    public static final int RUN_FILE = BASE_RUN_INDEX + 2; //�����ļ�
    public static final int DEBUG_PROJECT = BASE_RUN_INDEX + 3; //������Ŀ
    public static final int DEBUG_FILE = BASE_RUN_INDEX + 4; //�����ļ�
    public static final int DEBUG_PROJECT_TIME = BASE_RUN_INDEX + 5; //��ʱִ�е�����Ŀ
    public static final int DEBUG_FILE_TIME = BASE_RUN_INDEX + 6; //��ʱִ�е����ļ�
    public static final int PAUSE = BASE_RUN_INDEX + 7; //��ͣ
    public static final int STOP = BASE_RUN_INDEX + 8; //ֹͣ
    public static final int STEPOVER = BASE_RUN_INDEX + 9; //��һ��
    public static final int STEPINTO = BASE_RUN_INDEX + 10; //������һ��
    public static final int STEPOUT = BASE_RUN_INDEX + 11; //����
    public static final int SINGLE_INSTRUCTION = BASE_RUN_INDEX + 12; //��ָ��
    
    public static final int RUN_ANSWER = BASE_RUN_INDEX + 13;
    
    //���߲˵�:0-19
    public static final int MENUTOOLS = BASE_TOOLS_INDEX + 0; //���߲˵�
    public static final int CONFIG = BASE_TOOLS_INDEX + 1; //����
    //��ͼ�˵�:0-19
    public static final int MENUVIEW = BASE_VIEW_INDEX + 0; //��ͼ�˵�
    public static final int OUTPUT = BASE_VIEW_INDEX + 1; //�������
    //�����˵�:0-9
    public static final int MENUHELP = BASE_HELP_INDEX + 0; //�����˵�
    public static final int HELP = BASE_HELP_INDEX + 1; //����
    public static final int ABOUT = BASE_HELP_INDEX + 2; //����
	

    //�������ͳһ��
    private final String[] ActionKeys = new String[INDEXES_TOTAL_COUNT];

    public ActionResource() {
        initActionKeys();
    }

    /**
     * ��ȡ�����
     * @param index int
     * @return String
     */
    public String getActionKey(int index) {
        return ActionKeys[index];
    }

    public abstract Icon getIcon(int index); //��ȡͼ��

    public abstract String getActionName(int index); //��ȡ����

    public abstract String getActionDesc(int index); //��ȡ��ʾ

    private void initActionKeys() {
        //�ļ��˵�:0-19
        ActionKeys[MENUFILE] = "MENUFILE_ACTION"; //�ļ��˵�
        ActionKeys[NEW] = "NEW_ACTION"; //�½���Ŀ,��,�ӿ�,�ļ���
        ActionKeys[NEW_PROJECT] = "NEW_PROJECT_ACTION"; //�½���Ŀ
        ActionKeys[NEW_CLASS] = "NEW_CLASS_ACTION"; //�½����ļ�
        ActionKeys[OPEN_PROJECT] = "OPEN_PROJECT_ACTION"; //����Ŀ
        ActionKeys[OPEN_FILE] = "OPEN_FILE_ACTION"; //���ļ�
        ActionKeys[CLOSE_PROJECT] = "CLOSE_PROJECT_ACTION"; //�ر���Ŀ
        ActionKeys[CLOSE_FILE] = "CLOSE_FILE_ACTION"; //�ر��ļ�
        ActionKeys[SAVE_PROJECT] = "SAVE_PROJECT_ACTION"; //������Ŀ
        ActionKeys[SAVE_FILE] = "SAVE_FILE_ACTION"; //�����ļ�
        ActionKeys[SAVE_ALL] = "SAVE_ALL_ACTION"; //����ȫ���ļ�
        ActionKeys[RELOGIN] = "RELOGIN"; //���µ�¼
        ActionKeys[CHANGE_PASSWORD] = "CHANGE_PASSWORD_ACTION"; 
        
        ActionKeys[OPEN_TABLE] = "OPEN_TABLE_ACTION";//����Ŀ��
        ActionKeys[UPLOAD]="UPLOAD_ACTION"; //�ϴ�
        ActionKeys[NEW_HOMEWORK] = "NEW_HOMEWORK_ACTION";//�½���ҵ����
        
        ActionKeys[EXIT] = "EXIT_ACTION";//�˳�
        //�༭�˵�:0-19
        ActionKeys[MENUEDIT] = "MENUEDIT_ACTION"; //�༭�˵�
        ActionKeys[UNDO] = "UNDO_ACTION"; //����
        ActionKeys[REDO] = "REDO_ACTION"; //����
        ActionKeys[CUT] = "CUT_ACTION"; //����
        ActionKeys[COPY] = "COPY_ACTION"; //����
        ActionKeys[PASTE] = "PASTE_ACTION"; //ճ��
        ActionKeys[DELETE] = "DELETE_ACTION"; //ɾ��
        ActionKeys[SELECTALL] = "SELETEALL_ACTION"; //ճ��
        //���Ҳ˵�:0-19
        ActionKeys[MENUSEARCH] = "MENUSEARCH_ACTION"; //���Ҳ˵�
        ActionKeys[FIND] = "FIND_ACTION"; //����
        ActionKeys[FIND_INPATH] = "FIND_INPATH_ACTION"; //��ָ��·������
        ActionKeys[FINDDOWN] = "FINDDOWN_ACTION"; //������һ��
        ActionKeys[FINDUP] = "FINDUP_ACTION"; //������һ��
        ActionKeys[REPLACE] = "REPLACE_ACTION"; //�滻
        ActionKeys[REPLACE_INPATH] = "REPLACE_INPATH_ACTION"; //��ָ��·���滻
        ActionKeys[GOTO] = "GOTO_ACTION"; //����
        //��Ŀ�˵�0-19
        ActionKeys[MENUPROJECT] = "MENUPROJECT_ACTION"; //��Ŀ�˵�
        ActionKeys[COMPILE_PROJECT] = "COMPILE_PROJECT_ACTION"; //������Ŀ
        ActionKeys[COMPILE_FILE] = "COMPILE_FILE_ACTION"; //�����ļ�
        ActionKeys[BUILD] = "BUILD_ACTION"; //������Ŀ
        ActionKeys[PROJECT_PROPERTIES] = "PROJECT_PROPERTIES_ACTION"; //��Ŀ����
        //���в˵�:0-19
        ActionKeys[MENURUN] = "MENURUN_PROJECT_ACTION"; //���в˵�
        ActionKeys[RUN_PROJECT] = "RUN_PROJECT_ACTION"; //������Ŀ
        ActionKeys[RUN_FILE] = "RUN_FILE_ACTION"; //�����ļ�
        ActionKeys[DEBUG_PROJECT] = "DEBUG_PROJECT_ACTION"; //������Ŀ
        ActionKeys[DEBUG_FILE] = "DEBUG_FILE_ACTION"; //�����ļ�
        ActionKeys[DEBUG_PROJECT_TIME] = "DEBUG_PROJECT_TIME_ACTION"; //��ʱִ�е�����Ŀ
        ActionKeys[DEBUG_FILE_TIME] = "DEBUG_FILE_TIME_ACTION"; //��ʱִ�е����ļ�
        ActionKeys[PAUSE] = "PAUSE_ACTION"; //��ͣ
        ActionKeys[STOP] = "STOP_ACTION"; //ֹͣ
        ActionKeys[STEPOVER] = "STEPOVER_ACTION"; //��һ��
        ActionKeys[STEPINTO] = "STEPINTO_ACTION"; //��һ��
        ActionKeys[STEPOUT] = "STEPOUT_ACTION"; //����
        ActionKeys[SINGLE_INSTRUCTION] = "SINGLE_INSTRUCTION_ACTION"; //��ָ��
        //���߲˵�:0-19
        ActionKeys[MENUTOOLS] = "MENUTOOLS_ACTION"; //���߲˵�
        ActionKeys[CONFIG] = "CONFIG_ACTION"; //����
        //��ͼ�˵�:0-19
        ActionKeys[MENUVIEW] = "MENUVIEW_ACTION"; //�������
        ActionKeys[OUTPUT] = "OUTPUT_ACTION"; //�������
        //�����˵�:0-9
        ActionKeys[MENUHELP] = "MENUHELP_ACTION"; //�����˵�
        ActionKeys[HELP] = "HELP_ACTION"; //����
        ActionKeys[ABOUT] = "ABOUT_ACTION"; //����
    }

}
