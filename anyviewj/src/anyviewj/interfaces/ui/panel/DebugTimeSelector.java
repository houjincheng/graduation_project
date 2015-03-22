package anyviewj.interfaces.ui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import anyviewj.debug.actions.DebugProjectTimeAction;

/**
 * 2013年8月11日10:38:19
 * 
 * @author hou 这是一个时间选择器,整个组件由3部分组成:滑动条、当前值显示面板、单位显示面板组成，采用绝对布局。 滑动条上有2个监听器：
 *         1、ChangeListener。用以同步显示滑动条的值，并保存。
 *         2、MouseListener。当鼠标释放时，根据滑动条的当前值重置定时调试任务,若当前不处于同步调试状态，只重置延迟时间。
 */
public class DebugTimeSelector extends JPanel {

	private static final long serialVersionUID = -6260040275238742860L;
	private Color backGround = null; // 背景色
	private Dimension defaultSize = null; // 相当于一个普通按钮的size，用于按比例设置时间选择器的大小

	private JSlider silder = null; // 滑动条
	private JTextArea valuePane = null; // 用以动态显示滑动条的值
	private JTextArea unitPane = null; // 用以展示值的单位

	public final static int DEFAULT_VALUE = 250; // 滑动条的默认初始值，定时调试器将会获取这个值，以保持同步
	private int currentValue = DEFAULT_VALUE;

	/**
	 * 
	 * @param backGround
	 *            按钮的背景色，以便于和其他按钮保存风格一致
	 */
	public DebugTimeSelector(Color backGround) {
		super(null);
		this.defaultSize = new Dimension(20, 20);
		this.backGround = backGround;

		initDebugTimeSelector();
	}

	private void initDebugTimeSelector() {
		setBackground(this.backGround);
		setSize(new Dimension(this.defaultSize.width * 6,
				this.defaultSize.height));
		setVisible(true);

		initSlider();
		initValuePane();
		initUnitPane();

		add(this.silder);
		add(this.valuePane);
		add(this.unitPane);

	}

	/**
	 * 初始化slider后，才能作为一个组件添加到DebugTimeSelector中
	 */
	private void initSlider() {
		// 0-1000毫秒。既是1秒
		this.silder = new JSlider(0, 1000, DEFAULT_VALUE);

		silder.setMajorTickSpacing(500);
		silder.setMinorTickSpacing( 100 );

		silder.setPaintTicks(true); // 画刻度
		silder.setPaintLabels(false);// 不要画刻度值
		silder.setPaintTrack(true); // 画滑动块
//		silder.setSnapToTicks(true);// 解析为最靠近的值

		silder.setSize(new Dimension(this.defaultSize.width * 7 / 2,
				this.defaultSize.height));
		silder.setBounds(1, 1, this.defaultSize.width * 7 / 2,
				this.defaultSize.height);

		silder.addChangeListener(new JSliderChangerListener());
		silder.addMouseListener(new JSliderMouseListener());

	}

	private void initValuePane() {
		this.valuePane = new JTextArea(1, 4);

		valuePane.setBackground(this.backGround);
		valuePane.setForeground(Color.BLUE);
		valuePane.setEditable(false);
		valuePane.setText(String.valueOf(DEFAULT_VALUE));

		valuePane.setSize(new Dimension(this.defaultSize.width * 3 / 2,
				this.defaultSize.height));
		valuePane.setBounds(this.defaultSize.width * 7 / 2 + 1, 1,
				this.defaultSize.width * 3 / 2, this.defaultSize.height);

	}

	private void initUnitPane() {
		this.unitPane = new JTextArea();

		unitPane.setBackground(this.backGround);
		unitPane.setForeground(Color.RED);
		unitPane.setText("ms");
		unitPane.setEditable(false);
		unitPane.setVisible(true);

		unitPane.setSize(new Dimension(this.defaultSize.width,
				this.defaultSize.height));
		unitPane.setBounds(this.defaultSize.width * 5 + 1, 1,
				this.defaultSize.width, this.defaultSize.height);
	}

	protected class JSliderChangerListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			currentValue = ((JSlider) e.getSource()).getValue();

			// 显示的值要始终同步
			valuePane.setText(String.valueOf(currentValue));
		}
	}

	protected class JSliderMouseListener extends MouseAdapter {

		@Override
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);

			DebugProjectTimeAction timeAction = DebugProjectTimeAction
					.getDebugProjectTimeAction();
			// 即使当前不处于定时调试状态，也应更新定时调试间隔
			timeAction.setDelay(currentValue);

			if (timeAction.isActived())
			// 若当前处于定时调试状态，则让调试根据新的调试间隔时间重新开始
			{
//				取消原有任务，以新的时间间隔开始任务
				timeAction.cancelTask();
				timeAction.startTask();
			}
		}
	}
}
