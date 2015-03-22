package anyviewj.callstack.jtreetable;

/*
 * %W% %E%
 *
 * Copyright 1997, 1998 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer. 
 *   
 * - Redistribution in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials
 *   provided with the distribution. 
 *   
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.  
 * 
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE 
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,   
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER  
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF 
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS 
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 */

import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.JTree;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeWillExpandListener;

import com.sun.jdi.ArrayReference;
import com.sun.jdi.ArrayType;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.Field;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.Type;
import com.sun.jdi.Value;

import anyviewj.callstack.jtreetable.node.FieldNode;

/**
 * This is a wrapper class takes a TreeTableModel and implements the table model
 * interface. The implementation is trivial, with all of the event dispatching
 * support provided by the superclass: the AbstractTableModel.
 * 
 * @version %I% %G%
 * 
 * @author Philip Milne
 * @author Scott Violet
 */

public class TreeTableModelAdapter extends AbstractTableModel {
	JTree tree;
	TreeTableModel treeTableModel;

	public TreeTableModelAdapter(TreeTableModel treeTableModel, JTree tree) {
		this.tree = tree;
		this.treeTableModel = treeTableModel;

		tree.addTreeWillExpandListener(new  TreeWillExpandListener(){

			@Override
			public void treeWillExpand(TreeExpansionEvent event)
					throws ExpandVetoException {
				// TODO Auto-generated method stub

				Object comp = event.getPath().getLastPathComponent();
				if(comp instanceof FieldNode){
					FieldNode fn=((FieldNode) comp);
					Value varVal = fn.getVarValue();
					Type varType = fn.getType();
					if(varType instanceof ReferenceType && varVal instanceof ObjectReference && fn.getChildren().length==0){
						ObjectReference or = (ObjectReference) varVal;
						ReferenceType rt = (ReferenceType)varType;
						List<Field> lf= rt.allFields();
						for(Field f : rt.visibleFields()){
//							Value val=or.getValue(f);
//							System.out.println(val);
							fn.addChild( new FieldNode( or, f ) );
						}
						// 数组没有属性， 所以上面代码对数组没影响
						if(varType instanceof ArrayType){
							ArrayReference ar = (ArrayReference)or;
							Type art=null;
							try {
								art = ((ArrayType) varType).componentType();
							} catch (ClassNotLoadedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							String artName = ((ArrayType) varType).componentTypeName();
							for(int i=0; i<ar.length(); i++){
								Value v=ar.getValue(i);
								fn.addChild( new FieldNode(i, v, art, artName) );
							}
						}
					}	
				}
				
			}

			@Override
			public void treeWillCollapse(TreeExpansionEvent event)
					throws ExpandVetoException {
				// TODO Auto-generated method stub
				
			}
			
			});
		tree.addTreeExpansionListener(new TreeExpansionListener() {
			// Don't use fireTableRowsInserted() here;
			// the selection model would get updated twice.
			public void treeExpanded(TreeExpansionEvent event) {
				
				fireTableDataChanged();
			}

			public void treeCollapsed(TreeExpansionEvent event) {
				
				
				fireTableDataChanged();
			}
		});
	}

	// Wrappers, implementing TableModel interface.

	public int getColumnCount() {
		return treeTableModel.getColumnCount();
	}

	public String getColumnName(int column) {
		return treeTableModel.getColumnName(column);
	}

	public Class getColumnClass(int column) {
		return treeTableModel.getColumnClass(column);
	}

	public int getRowCount() {
		return tree.getRowCount();
	}

	protected Object nodeForRow(int row) {
		TreePath treePath = tree.getPathForRow(row);
		return treePath.getLastPathComponent();
	}

	public Object getValueAt(int row, int column) {
		Object tmp = treeTableModel.getValueAt(nodeForRow(row), column);

		// System.out.println( "tmp = " + tmp );
		return tmp;
	}

	public boolean isCellEditable(int row, int column) {
		return treeTableModel.isCellEditable(nodeForRow(row), column);
	}

	public void setValueAt(Object value, int row, int column) {
		treeTableModel.setValueAt(value, nodeForRow(row), column);
	}
}
