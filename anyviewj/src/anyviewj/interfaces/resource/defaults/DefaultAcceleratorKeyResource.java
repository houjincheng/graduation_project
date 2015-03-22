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
     * ���������ֵ�ο�ActionResource
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
        //====================�ļ��˵�====================//
        acclKeys[ActionResource.MENUFILE] = null; //�ļ��˵�
        acclKeys[ActionResource.NEW] = KeyStroke.getKeyStroke("ctrl N"); //�½���Ŀ,��,�ӿ�,�ļ���
        acclKeys[ActionResource.NEW_PROJECT] = null; //�½���Ŀ
        acclKeys[ActionResource.NEW_CLASS] = null; //�½����ļ�
        acclKeys[ActionResource.OPEN_PROJECT] = null; //����Ŀ
        acclKeys[ActionResource.OPEN_FILE] = KeyStroke.getKeyStroke("ctrl O"); //���ļ�
        acclKeys[ActionResource.CLOSE_PROJECT] = null; //�ر���Ŀ
        acclKeys[ActionResource.CLOSE_FILE] = null; //�ر��ļ�
        acclKeys[ActionResource.SAVE_PROJECT] = null; //������Ŀ
        acclKeys[ActionResource.SAVE_FILE] = KeyStroke.getKeyStroke("ctrl S"); //�����ļ�
        acclKeys[ActionResource.SAVE_ALL] = null; //����ȫ���ļ�
        acclKeys[ActionResource.RELOGIN] = null; //���µ�¼
        acclKeys[ActionResource.CHANGE_PASSWORD] = null; //�޸�����
        acclKeys[ActionResource.EXIT] = null;//�˳�

        acclNames[ActionResource.MENUFILE] = ""; //�ļ��˵�
        acclNames[ActionResource.NEW] = "Ctrl+N"; //�½���Ŀ,��,�ӿ�,�ļ���
        acclNames[ActionResource.NEW_PROJECT] = ""; //�½���Ŀ
        acclNames[ActionResource.NEW_CLASS] = ""; //�½����ļ�
        acclNames[ActionResource.OPEN_PROJECT] = ""; //����Ŀ
        acclNames[ActionResource.OPEN_FILE] = "Ctrl+O"; //���ļ�
        acclNames[ActionResource.CLOSE_PROJECT] = ""; //�ر���Ŀ
        acclNames[ActionResource.CLOSE_FILE] = ""; //�ر��ļ�
        acclNames[ActionResource.SAVE_PROJECT] = ""; //������Ŀ
        acclNames[ActionResource.SAVE_FILE] = "Ctrl+S"; //�����ļ�
        acclNames[ActionResource.SAVE_ALL] = ""; //����ȫ���ļ�
        acclNames[ActionResource.CHANGE_PASSWORD] = ""; //�޸�����
        acclNames[ActionResource.EXIT] = "";//�˳�

        //====================�༭�˵�====================//
        acclKeys[ActionResource.MENUEDIT] = null; //�༭�˵�
        acclKeys[ActionResource.UNDO] = KeyStroke.getKeyStroke("ctrl Z"); //����
        acclKeys[ActionResource.REDO] = KeyStroke.getKeyStroke("ctrl shift Z"); //����
        acclKeys[ActionResource.CUT] = KeyStroke.getKeyStroke("ctrl X"); //����
        acclKeys[ActionResource.COPY] = KeyStroke.getKeyStroke("ctrl C"); //����
        acclKeys[ActionResource.PASTE] = KeyStroke.getKeyStroke("ctrl V"); //ճ��
        acclKeys[ActionResource.DELETE] = null; //ɾ��
        acclKeys[ActionResource.SELECTALL] = KeyStroke.getKeyStroke("ctrl A"); //ȫѡ

        acclNames[ActionResource.MENUEDIT] = ""; //�༭�˵�
        acclNames[ActionResource.UNDO] = "Ctrl+Z"; //����
        acclNames[ActionResource.REDO] = "Ctrl+Shift+Z"; //����
        acclNames[ActionResource.CUT] = "Ctrl+X"; //����
        acclNames[ActionResource.COPY] = "Ctrl+C"; //����
        acclNames[ActionResource.PASTE] = "Ctrl+V"; //ճ��
        acclNames[ActionResource.DELETE] = ""; //ɾ��
        acclNames[ActionResource.SELECTALL] = "Ctrl+A"; //ȫѡ


        //====================���Ҳ˵�====================//
        acclKeys[ActionResource.MENUSEARCH] = null; //���Ҳ˵�
        acclKeys[ActionResource.FIND] = KeyStroke.getKeyStroke("ctrl F"); //����
        acclKeys[ActionResource.FIND_INPATH] = null; //��ָ��·������
        acclKeys[ActionResource.FINDDOWN] = KeyStroke.getKeyStroke("F3"); //������һ��
        acclKeys[ActionResource.FINDUP] = KeyStroke.getKeyStroke("F2"); //������һ��
        acclKeys[ActionResource.REPLACE] = KeyStroke.getKeyStroke("ctrl R"); //�滻
        acclKeys[ActionResource.REPLACE_INPATH] = null; //��ָ��·���滻
        acclKeys[ActionResource.GOTO] = KeyStroke.getKeyStroke("ctrl G"); //����

        acclNames[ActionResource.MENUSEARCH] = ""; //���Ҳ˵�
        acclNames[ActionResource.FIND] = "Ctrl+F"; //����
        acclNames[ActionResource.FIND_INPATH] = ""; //��ָ��·������
        acclNames[ActionResource.FINDDOWN] = "F3"; //������һ��
        acclNames[ActionResource.FINDUP] = "F2"; //������һ��
        acclNames[ActionResource.REPLACE] = "Ctrl+R"; //�滻
        acclNames[ActionResource.REPLACE_INPATH] = ""; //��ָ��·���滻
        acclNames[ActionResource.GOTO] = "Ctrl+G"; //����


        //====================��Ŀ�˵�====================//
        acclKeys[ActionResource.MENUPROJECT] = null; //��Ŀ�˵�
        acclKeys[ActionResource.COMPILE_PROJECT] = KeyStroke.getKeyStroke("ctrl F9"); //������Ŀ
        acclKeys[ActionResource.COMPILE_FILE] = null; //�����ļ�
        acclKeys[ActionResource.BUILD] = null; //������Ŀ
        acclKeys[ActionResource.PROJECT_PROPERTIES] = null; //��Ŀ����

        acclNames[ActionResource.MENUPROJECT] = ""; //��Ŀ�˵�
        acclNames[ActionResource.COMPILE_PROJECT] = "Ctrl+F9"; //������Ŀ
        acclNames[ActionResource.COMPILE_FILE] = ""; //�����ļ�
        acclNames[ActionResource.BUILD] = ""; //������Ŀ
        acclNames[ActionResource.PROJECT_PROPERTIES] = ""; //��Ŀ����


        //====================���в˵�====================//
        acclKeys[ActionResource.MENURUN] = null; //���в˵�
        acclKeys[ActionResource.RUN_PROJECT] = KeyStroke.getKeyStroke("F9"); //������Ŀ
        acclKeys[ActionResource.RUN_FILE] = null;//�����ļ�
        acclKeys[ActionResource.DEBUG_PROJECT] = KeyStroke.getKeyStroke("shift F9"); //������Ŀ
        acclKeys[ActionResource.DEBUG_FILE] = null; //�����ļ�
        acclKeys[ActionResource.DEBUG_PROJECT_TIME] = null; //��ʱִ�е�����Ŀ
        acclKeys[ActionResource.DEBUG_FILE_TIME] = null; //��ʱִ�е����ļ�
        acclKeys[ActionResource.PAUSE] = KeyStroke.getKeyStroke("F5"); //��ͣ
        acclKeys[ActionResource.STOP] = KeyStroke.getKeyStroke("F4"); //ֹͣ
        acclKeys[ActionResource.STEPOVER] = KeyStroke.getKeyStroke("F8"); //��һ��
        acclKeys[ActionResource.STEPINTO] = KeyStroke.getKeyStroke("F7"); //���룬��һ��
        acclKeys[ActionResource.STEPOUT] = KeyStroke.getKeyStroke("F6"); //����
        acclKeys[ActionResource.SINGLE_INSTRUCTION] = KeyStroke.getKeyStroke("F10"); //��ָ��

        acclNames[ActionResource.MENURUN] = ""; //���в˵�
        acclNames[ActionResource.RUN_PROJECT] = "F9"; //������Ŀ
        acclNames[ActionResource.RUN_FILE] = "";//�����ļ�
        acclNames[ActionResource.DEBUG_PROJECT] = "Shift+F9"; //������Ŀ
        acclNames[ActionResource.DEBUG_FILE] = ""; //�����ļ�
        acclNames[ActionResource.DEBUG_PROJECT_TIME] = ""; //��ʱִ�е�����Ŀ
        acclNames[ActionResource.DEBUG_FILE_TIME] = ""; //��ʱִ�е����ļ�
        acclNames[ActionResource.PAUSE] = "F5"; //��ͣ
        acclNames[ActionResource.STOP] = "F4"; //ֹͣ
        acclNames[ActionResource.STEPOVER] = "F8"; //��һ��
        acclNames[ActionResource.STEPINTO] = "F7"; //���룬��һ��
        acclNames[ActionResource.STEPOUT] = "F6"; //����
        acclNames[ActionResource.SINGLE_INSTRUCTION] = "F10"; //��ָ��


        //====================���߲˵�====================//
        acclKeys[ActionResource.MENUTOOLS] = null; //���߲˵�
        acclKeys[ActionResource.CONFIG] = null; //����

        acclNames[ActionResource.MENUTOOLS] = ""; //���߲˵�
        acclNames[ActionResource.CONFIG] = ""; //����


        //====================��ͼ�˵�====================//
        acclKeys[ActionResource.MENUVIEW] = null; //��ͼ�˵�
        acclKeys[ActionResource.OUTPUT] = null; //�������

        acclNames[ActionResource.MENUVIEW] = ""; //��ͼ�˵�
        acclNames[ActionResource.OUTPUT] = ""; //�������


        //====================�����˵�====================//
        acclKeys[ActionResource.MENUHELP] = null; //�����˵�
        acclKeys[ActionResource.HELP] = null; //����
        acclKeys[ActionResource.ABOUT] = null; //����

        acclNames[ActionResource.MENUHELP] = ""; //�����˵�
        acclNames[ActionResource.HELP] = ""; //����
        acclNames[ActionResource.ABOUT] = ""; //����

    }
}
