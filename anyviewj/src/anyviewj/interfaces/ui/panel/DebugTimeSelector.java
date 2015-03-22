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
 * 2013��8��11��10:38:19
 * 
 * @author hou ����һ��ʱ��ѡ����,���������3�������:����������ǰֵ��ʾ��塢��λ��ʾ�����ɣ����þ��Բ��֡� ����������2����������
 *         1��ChangeListener������ͬ����ʾ��������ֵ�������档
 *         2��MouseListener��������ͷ�ʱ�����ݻ������ĵ�ǰֵ���ö�ʱ��������,����ǰ������ͬ������״̬��ֻ�����ӳ�ʱ�䡣
 */
public class DebugTimeSelector extends JPanel {

	private static final long serialVersionUID = -6260040275238742860L;
	private Color backGround = null; // ����ɫ
	private Dimension defaultSize = null; // �൱��һ����ͨ��ť��size�����ڰ���������ʱ��ѡ�����Ĵ�С

	private JSlider silder = null; // ������
	private JTextArea valuePane = null; // ���Զ�̬��ʾ��������ֵ
	private JTextArea unitPane = null; // ����չʾֵ�ĵ�λ

	public final static int DEFAULT_VALUE = 250; // ��������Ĭ�ϳ�ʼֵ����ʱ�����������ȡ���ֵ���Ա���ͬ��
	private int currentValue = DEFAULT_VALUE;

	/**
	 * 
	 * @param backGround
	 *            ��ť�ı���ɫ���Ա��ں�������ť������һ��
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
	 * ��ʼ��slider�󣬲�����Ϊһ�������ӵ�DebugTimeSelector��
	 */
	private void initSlider() {
		// 0-1000���롣����1��
		this.silder = new JSlider(0, 1000, DEFAULT_VALUE);

		silder.setMajorTickSpacing(500);
		silder.setMinorTickSpacing( 100 );

		silder.setPaintTicks(true); // ���̶�
		silder.setPaintLabels(false);// ��Ҫ���̶�ֵ
		silder.setPaintTrack(true); // ��������
//		silder.setSnapToTicks(true);// ����Ϊ�����ֵ

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

			// ��ʾ��ֵҪʼ��ͬ��
			valuePane.setText(String.valueOf(currentValue));
		}
	}

	protected class JSliderMouseListener extends MouseAdapter {

		@Override
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);

			DebugProjectTimeAction timeAction = DebugProjectTimeAction
					.getDebugProjectTimeAction();
			// ��ʹ��ǰ�����ڶ�ʱ����״̬��ҲӦ���¶�ʱ���Լ��
			timeAction.setDelay(currentValue);

			if (timeAction.isActived())
			// ����ǰ���ڶ�ʱ����״̬�����õ��Ը����µĵ��Լ��ʱ�����¿�ʼ
			{
//				ȡ��ԭ���������µ�ʱ������ʼ����
				timeAction.cancelTask();
				timeAction.startTask();
			}
		}
	}
}
