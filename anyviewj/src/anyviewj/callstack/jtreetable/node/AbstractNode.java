package anyviewj.callstack.jtreetable.node;

import java.util.ArrayList;

import com.sun.jdi.Type;

public abstract class AbstractNode {
	
	//change by ydl �����������
	private Boolean isLeaf= false;
	private String typeName=null;	//�� type class��δ���������ȡʱ,ʹ�ô˱�ʶ
	
	private Object parent = null;
	private int id = -1;
	private String name = null;
	private Type type = null;
	private String value = null;
	private ArrayList<Object> children = new ArrayList<Object>();
	private boolean expandable = false;
	private boolean expanded = false;
	
	public Boolean isLeaf(){
		return isLeaf;
	}
	
	public void setLeaf(Boolean is){
		isLeaf=is;
	}

	public void addChild( Object child )
	{
		children.add( child );
	}
	public Object[] getChildren()
	{
		return this.children.toArray();
	}
	/**
	 * @return parent
	 */
	public Object getParent() {
		return parent;
	}
	/**
	 * @param parent Ҫ���õ� parent
	 */
	public void setParent(Object parent) {
		this.parent = parent;
	}
	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id Ҫ���õ� id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name Ҫ���õ� name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return type
	 */
	public Type getType() {
		return type;
	}
	/**
	 * @param type Ҫ���õ� type
	 */
	public void setType(Type type) {
		this.type = type;
	}
	/**
	 * @return value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value Ҫ���õ� value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return expandable
	 */
	public boolean isExpandable() {
		return expandable;
	}
	/**
	 * @param expandable Ҫ���õ� expandable
	 */
	public void setExpandable(boolean expandable) {
		this.expandable = expandable;
	}
	/**
	 * @return expanded
	 */
	public boolean isExpanded() {
		return expanded;
	}
	/**
	 * @param expanded Ҫ���õ� expanded
	 */
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	
	public String toString() { 
		return this.getName();
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
