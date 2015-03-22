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
    //起始索引
    protected static final int BASE_TABPANE_INDEX = 0;

    //图标数组总大小
    public static final int INDEXES_TOTAL_COUNT = 10;

    //选项卡图标索引
    public static final int TABPANE_COMBINE = BASE_TABPANE_INDEX + 0;//选项卡合并图标
    public static final int TABPANE_DEPART = BASE_TABPANE_INDEX + 1;//选项卡分离图标
    public static final int TABPANE_CLOSE_WITHOUT_SAVE= BASE_TABPANE_INDEX + 2;//关闭无需保存
    public static final int TABPANE_CLOSE_WITH_SAVE = BASE_TABPANE_INDEX + 3;//关闭前需保存
    public static final int TABPANE_READONLY = BASE_TABPANE_INDEX + 4;//只读

    //通过索引获取图标
    public abstract Icon getIcon(int index);
}
