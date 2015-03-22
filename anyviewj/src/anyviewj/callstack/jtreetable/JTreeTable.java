package anyviewj.callstack.jtreetable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeSelectionModel;

/**
 * This example shows how to create a simple JTreeTable component, by using a
 * JTree as a renderer (and editor) for the cells in a particular column in the
 * JTable.
 * 
 * @version %I% %G%
 * 
 * @author Philip Milne
 * @author Scott Violet
 */

public class JTreeTable extends JTable {
	protected TreeTableCellRenderer tree;

	public JTreeTable(TreeTableModel treeTableModel) {
		super();

		// Create the tree. It will be used as a renderer and editor.
		tree = new TreeTableCellRenderer(treeTableModel, this);

		// Install a tableModel representing the visible rows in the tree.
		super.setModel(new TreeTableModelAdapter(treeTableModel, tree));

		// Force the JTable and JTree to share their row selection models.
		tree.setSelectionModel(new DefaultTreeSelectionModel() {
			// Extend the implementation of the constructor, as if:
			/* public this() */{
				setSelectionModel(listSelectionModel);
			}
		});
		ToolTipManager.sharedInstance().registerComponent(this); // hou 注册单元格的悬浮提示
		// for ( MouseListener m : tree.getMouseListeners() )
		// {
		// System.out.println( "remove l " + m );
		// if ( m instanceof BasicTreeUI.MouseHandler )
		// {
		// tree.removeMouseListener( m );
		// }
		//
		// }
		// Make the tree and table row heights the same.
		setRowHeight(getRowHeight() * 5 / 4);
		tree.setRowHeight(getRowHeight());
		tree.setRootVisible(false);
		// Install the tree editor renderer and editor.
		setDefaultRenderer(TreeTableModel.class, tree);
		// 使在展开关闭树节点时，一并展开表
		setDefaultEditor(TreeTableModel.class, new TreeTableCellEditor());

		setGridColor(Color.LIGHT_GRAY);
		setShowGrid(true);
		setIntercellSpacing(new Dimension(0, 0));
		getTableHeader().setReorderingAllowed(false); // 不可拖拽整列

		// for ( MouseListener m : getMouseListeners() )
		// {
		// System.out.println( "m = " + m );
		// removeMouseListener( m );
		//
		// }
		SelectionListener listener = new SelectionListener(this);
		getSelectionModel().addListSelectionListener(listener);
	}

	@Override
	public String getToolTipText(MouseEvent e) {
		int row = this.rowAtPoint(e.getPoint());
		int col = this.columnAtPoint(e.getPoint());
		String tiptextString = null;
		if (row > -1 && col > -1) {
			Object value = this.getValueAt(row, col);
			if (null != value && !"".equals(value))
				tiptextString = value.toString();// 悬浮显示单元格内容
		}
		return tiptextString;
	}

	private class SelectionListener implements ListSelectionListener {
		JTable table;

		SelectionListener(JTable table) {
			this.table = table;
		}

		public void valueChanged(ListSelectionEvent e) {
			System.out.println(e);

			int firstRow = e.getFirstIndex();
			int lastRow = e.getLastIndex();

			if (e.getSource() instanceof DefaultListSelectionModel) {
				((DefaultListSelectionModel) e.getSource()).getSelectionMode();
			}
			// 事件处理...
			if (JTreeTable.this.tree.isExpanded(lastRow)) {
				JTreeTable.this.tree.collapseRow(lastRow);
			} else {

				JTreeTable.this.tree.expandRow(lastRow);
			}
		}
	}

	public TreeTableCellRenderer getTreeTableCellRenderer() {
		return this.tree;
	}

	/*
	 * Workaround for BasicTableUI anomaly. Make sure the UI never tries to
	 * paint the editor. The UI currently uses different techniques to paint the
	 * renderers and editors and overriding setBounds() below is not the right
	 * thing to do for an editor. Returning -1 for the editing row in this case,
	 * ensures the editor is never painted.
	 */
	public int getEditingRow() {
		return (getColumnClass(editingColumn) == TreeTableModel.class) ? -1
				: editingRow;
	}

	//
	// The renderer used to display the tree nodes, a JTree.
	//

	public class TreeTableCellRenderer extends JTree implements
			TableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		protected int visibleRow;

		public TreeTableCellRenderer(TreeTableModel treeTableModel, JTable table) {
			super(treeTableModel);
			// hou
			setCellRenderer(new MyDefaultTreeCellRenderer(this, table));
			// hou 2013年12月28日20:24:33
			addTreeExpansionListener(new MyTreeExpansionListener());
			setToggleClickCount(5);
		}

		public void setBounds(int x, int y, int w, int h) {
			super.setBounds(x, 0, w, JTreeTable.this.getHeight());
		}

		public void paint(Graphics g) {
			g.translate(0, -visibleRow * getRowHeight());
			super.paint(g);

			drawGrid(g);
		}

		/**
		 * hou 画出树的网格，分隔每个节点
		 * 
		 * @param g
		 */
		private void drawGrid(Graphics g) {
			g.setColor(Color.LIGHT_GRAY);
			Rectangle r = getBounds();
			g.drawRect(r.x, r.y - 1, r.width - 1, r.height);

			for (int i = 0; i <= getRowCount(); i++) {
				g.drawLine(r.x, r.y + getRowHeight() * i - 1, r.x + r.width,
						r.y + getRowHeight() * i - 1);
			}
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (isSelected)
				setBackground(table.getSelectionBackground());
			else
				setBackground(table.getBackground());

			visibleRow = row;
			return this;
		}
	}

	//
	// The editor used to interact with tree nodes, a JTree.
	//

	public class TreeTableCellEditor extends AbstractCellEditor implements
			TableCellEditor {
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int r, int c) {
			return tree;
		}
	}

}
