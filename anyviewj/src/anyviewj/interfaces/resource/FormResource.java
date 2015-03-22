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
    //��ʼ����
    protected static final int LEFTPART_PROJECT_MANAGER = 0;//�����--��--��Ŀ������
    protected static final int LEFTPART_DEBUG_MANAGER = 10;//�����--��--���Թ�����
    protected static final int RIGHTPART_CODE_MANAGER = 20;//�����--�Ҳ�--���������
    protected static final int RIGHTPART_GROUP_MANAGER = 30;//�����--�Ҳ�--�������������

    //�����������ֵ
    public static final int INDEXES_TOTAL_COUNT = 50;

    //�����--��
    ///////////////////////////////////////////////////////////////////
        //�����--��--��Ŀ������
        public static final int PROJECT_INFOS = LEFTPART_PROJECT_MANAGER + 0;//��Ŀ��Ϣ
        public static final int PROJECT_INFOS_STRUCT = LEFTPART_PROJECT_MANAGER + 1;//��Ŀ�ṹ
        public static final int PROJECT_INFOS_CLASSINFOS = LEFTPART_PROJECT_MANAGER + 2;//������
        public static final int PROJECT_INFOS_DOCUMENT = LEFTPART_PROJECT_MANAGER + 3;//��Ŀ�ĵ�

        //�����--��--���Թ�����
        public static final int DEBUG_MANAGER = LEFTPART_DEBUG_MANAGER + 0;//���Թ�����
        public static final int DEBUG_MANAGER_GLOBAL = LEFTPART_DEBUG_MANAGER + 1;//ȫ��
        public static final int DEBUG_MANAGER_STACKFRAME = LEFTPART_DEBUG_MANAGER + 2;//�߳���
        public static final int DEBUG_MANAGER_HEAP = LEFTPART_DEBUG_MANAGER + 3;//�����
        public static final int DEBUG_MANAGER_ARRAYS = LEFTPART_DEBUG_MANAGER + 4;//����
        public static final int DEBUG_MANAGER_DATASTRUCT = LEFTPART_DEBUG_MANAGER + 5;//���ݽṹ

        //�����--�Ҳ�--��������� RIGHTPART_CODE_MANAGER
        public static final int CODEPANE_SOURCEFILE = RIGHTPART_CODE_MANAGER +0;//����༭������ѡ��:Դ�ļ�(Դ�ļ�/���ļ�)
        public static final int CODEPANE_CLASSFILE = RIGHTPART_CODE_MANAGER +1;//����༭������ѡ��:���ļ�(Դ�ļ�/���ļ�)

        //�����--�Ҳ�--������������� RIGHTPART_GROUP_MANAGER
        public static final int OUTPUT_FORM = RIGHTPART_GROUP_MANAGER + 0;        
        public static final int CLASS_FORM = RIGHTPART_GROUP_MANAGER + 1;
        public static final int BREAKPOINT_FORM = RIGHTPART_GROUP_MANAGER + 2;
        public static final int Locals_FORM = RIGHTPART_GROUP_MANAGER + 3;
        
    public abstract String getNames(int index);
    public abstract String getTips(int index);
}
