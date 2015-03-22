package anyviewj.datastructs.baseclass.linklist;

import anyviewj.datastructs.baseclass.BaseDataStruct;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
/**
 *
 * ??????
 */
 public interface LinkList<T> extends BaseDataStruct{
    boolean ListEmpty(LinkList L);
    int ListLength(LinkList L);
    LinkList GetElem(LinkList L , int i);
    int LocateElem(LinkList L , T data);
    LinkList PriorElem(LinkList L , T data);
    LinkList NextElem(LinkList L , T data);
    void ListInsert(LinkList L , int i , T data);
    T ListDelete(LinkList L , int i );
    LinkList getNext();
    void setNext(T data);
    T getData();
    String getDataText();

}
