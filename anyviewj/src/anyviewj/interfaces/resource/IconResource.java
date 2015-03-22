package anyviewj.interfaces.resource;

import javax.swing.Icon;

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
public abstract class IconResource {
    //��ʼ����
    protected static final int BASE_TABPANE_INDEX = 0;

    //ͼ�������ܴ�С
    public static final int INDEXES_TOTAL_COUNT = 10;

    //ѡ�ͼ������
    public static final int TABPANE_COMBINE = BASE_TABPANE_INDEX + 0;//ѡ��ϲ�ͼ��
    public static final int TABPANE_DEPART = BASE_TABPANE_INDEX + 1;//ѡ�����ͼ��
    public static final int TABPANE_CLOSE_WITHOUT_SAVE= BASE_TABPANE_INDEX + 2;//�ر����豣��
    public static final int TABPANE_CLOSE_WITH_SAVE = BASE_TABPANE_INDEX + 3;//�ر�ǰ�豣��
    public static final int TABPANE_READONLY = BASE_TABPANE_INDEX + 4;//ֻ��

    //ͨ��������ȡͼ��
    public abstract Icon getIcon(int index);
}
