package com.zxmys.course.programming.project2.Panel.SudokuPanel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.*;
import javax.swing.text.*;

import com.zxmys.course.programming.project1.*;
import com.zxmys.course.programming.project2.*;
import com.zxmys.course.programming.project2.Panel.SudokuPanel.BoardColor.*;

/**
 * ������������࣬ʵ�������������Ĵ󲿷ֹ��ܡ�
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public abstract class SudokuGamePanel extends JPanel {

	/**
	 * Ĭ�Ͽ�����壬Ŀǰֻ�м�ʱ��JLabel���Ը�����������滻Ĭ����������๦�ܡ�
	 * 
	 * @author Zx.MYS
	 */
	public class DashBoardPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		private JLabel jlb = new JLabel("����ʱ�䣺");;

		private JLabel jlbTime = new JLabel("00:00:00");

		/**
		 * ��Ϸ��ʼʱ��
		 */
		long timerStartTime;

		/**
		 * ��ʱ��timer
		 */
		Timer timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long time = System.currentTimeMillis() - timerStartTime;
				Calendar c = Calendar.getInstance(new SimpleTimeZone(0,
						"Zx.MYS's Home"));
				c.setTime((new Date(time)));
				c.toString();
				jlbTime.setText(String.format("%02d", c
						.get(Calendar.HOUR_OF_DAY))
						+ ":"
						+ String.format("%02d", c.get(Calendar.MINUTE))
						+ ":" + String.format("%02d", c.get(Calendar.SECOND)));
			}
		});

		/**
		 * ��ʼ��������ؼ�
		 */
		public DashBoardPanel() {

			jlb.setEnabled(false);
			jlb.setAlignmentX(Component.CENTER_ALIGNMENT);
			add(jlb);

			jlbTime.setEnabled(false);
			jlbTime.setAlignmentX(Component.CENTER_ALIGNMENT);
			add(jlbTime);

		}

		/**
		 * ��ʼ��ʱ
		 */
		public void startCountingTime() {
			jlb.setEnabled(true);
			jlbTime.setEnabled(true);
			timerStartTime = System.currentTimeMillis();
			timer.start();
		}

		/**
		 * �����Ϸʱ�䣨������ʽ��
		 * 
		 * @return jlbTime����ʾ����Ϸʱ��
		 */
		public String getPlayTime() {
			return jlbTime.getText();
		}

		/**
		 * �����Ϸʱ��
		 * 
		 * @return �Ժ�������ʾ����Ϸʱ��
		 */
		public long getPlayTimeLong() {
			return System.currentTimeMillis() - timerStartTime;
		}

		/**
		 * ������Ϸʱ��
		 * 
		 * @param playTime
		 *            Ҫ���õ���Ϸʱ��
		 */
		public void setPlayTime(long playTime) {
			this.timerStartTime = System.currentTimeMillis() - playTime;
		}

	}

	/**
	 * ��������壬���ڷ��ü�����������
	 * 
	 * @author Zx.MYS
	 * 
	 */
	public class SudokuBoardPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		/**
		 * ��JTextField��Document����������ϵ��HashMao
		 */
		private HashMap<SudokuBlockDmt, int[]> map = new HashMap<SudokuBlockDmt, int[]>();

		/**
		 * ��������ɫ����
		 */
		private SudokuBoardColor sbc;

		/**
		 * ������JTextField��SudokuBlockDmt������ֻ������Ϸ����ֲ���������¼�
		 * 
		 * @author Zx.MYS
		 * 
		 */
		class SudokuBlockDmt extends PlainDocument {

			private static final long serialVersionUID = 1L;

			/**
			 * �Ƿ����ڽ��С��޸ġ�����
			 */
			private boolean isChanging = true;

			/**
			 * �޸ļ�ɾ������ԭ��������
			 */
			private int change = 0;

			/**
			 * ��ʼ����������ӦDocumentListener��������غ��� ��(frame.getGameStatus() ==
			 * MainFrame.GAME_LOADING || frame.getGameStatus() ==
			 * MainFrame.GAME_ENDING || isUndoingOrRedoing)ʱ��������غ���
			 */
			public SudokuBlockDmt() {
				addDocumentListener(new DocumentListener() {

					public void insertUpdate(DocumentEvent e) {
						int[] cord = map.get(e.getDocument());
						if (isChanging) {
							synchronizeBlock(cord[0], cord[1]);
							if (!(frame.getGameStatus() == MainFrame.GAME_LOADING
									|| frame.getGameStatus() == MainFrame.GAME_ENDING || isUndoingOrRedoing))
								BlockChanged(cord[0], cord[1], change);
						} else {
							synchronizeBlock(cord[0], cord[1]);
							if (!(frame.getGameStatus() == MainFrame.GAME_LOADING
									|| frame.getGameStatus() == MainFrame.GAME_ENDING || isUndoingOrRedoing))
								BlockInsert(cord[0], cord[1]);
						}
					}

					public void removeUpdate(DocumentEvent e) {
						int[] cord = map.get(e.getDocument());
						if (!isChanging) {
							change = sb.getVal(cord[0], cord[1]);
							synchronizeBlock(cord[0], cord[1]);
							if (!(frame.getGameStatus() == MainFrame.GAME_LOADING
									|| frame.getGameStatus() == MainFrame.GAME_ENDING || isUndoingOrRedoing))
								BlockRemove(cord[0], cord[1], change);
						}
					}

					public void changedUpdate(DocumentEvent e) {
					}
				});
			}

			/**
			 * �ڲ����ַ�ʱ����Ƿ�Ϸ����Լ��ж��Ƿ�Ϊ�޸Ĳ�����
			 * 
			 * @see javax.swing.text.PlainDocument#insertString(int,
			 *      java.lang.String, javax.swing.text.AttributeSet)
			 */
			@Override
			public void insertString(int offset, String str, AttributeSet attr)
					throws BadLocationException {
				if (str == null || str.length() > 1
						|| !Character.isDigit(str.charAt(0))
						|| str.charAt(0) == '0') {
					return;
				}
				if (getLength() > 0) {
					isChanging = true;
					change = Integer.parseInt(getText(0, 1));
					System.out.println(change);
					remove(0, getLength());
				}
				int[] cord = map.get(this);
				int[] isBlockValid = sb.isValid(cord[0], cord[1], Integer
						.parseInt(str));
				if (isBlockValid[0] != 0) {
					JOptionPane.showMessageDialog(frame, "�������������е�"
							+ isBlockValid[0] + "�У���" + isBlockValid[1]
							+ "�е������г�ͻ�����������롣\n");
					return;
				}
				super.insertString(0, str, attr);
				isChanging = false;
			}
		}

		/**
		 * ����������JTextField���飨�����±귶ΧΪ1-9,1-9��
		 */
		JTextField[][] block = new JTextField[10][10];

		/**
		 * ��ʼ����������ʹ��GridLayout����JTextField��ͬʱ������������ɫ��������JTextField��ɫ
		 */
		public SudokuBoardPanel() {
			map.clear();
			setLayout(new GridLayout(9, 9));
			for (int i = 1; i < 10; i++)
				for (int j = 1; j < 10; j++) {
					block[i][j] = new JTextField();
					block[i][j].setHorizontalAlignment(JTextField.CENTER);
					SudokuBlockDmt dmt = new SudokuBlockDmt();
					block[i][j].setDocument(dmt);
					map.put(dmt, new int[] { i, j });
					block[i][j].setFont(new Font(block[i][j].getFont()
							.getFontName(), Font.BOLD, 22));
					add(block[i][j]);
				}
			setPreferredSize(BOARDSIZE);
			if (frame.getJMenuBar() != null
					&& frame.getJMenuBar() instanceof MainControl)
				setSudokuBoardColor(((MainControl) frame.getJMenuBar())
						.getSudokuBoardColor());
			else
				setSudokuBoardColor(new SimpleSudokuBoardColor());

		}

		/**
		 * �����������ɫ����
		 * 
		 * @return ��������ɫ����
		 */
		public SudokuBoardColor getSudokuBoardColor() {
			return sbc;
		}

		/**
		 * ������������ɫ����
		 * 
		 * @param sbc
		 *            Ҫ���õ���ɫ����
		 */
		public void setSudokuBoardColor(SudokuBoardColor sbc) {
			if (sbc == null)
				throw new IllegalArgumentException();
			this.sbc = sbc;
			for (int i = 1; i < 10; i++)
				for (int j = 1; j < 10; j++) {
					block[i][j].setBackground(sbc.getBlockColor(i, j));
				}
		}

		/**
		 * ��������JTextFieldΪ�ɱ༭״̬
		 */
		public void unlock() {
			for (int i = 1; i < 10; i++)
				for (int j = 1; j < 10; j++) {
					block[i][j].setEditable(true);
				}
		}

		/**
		 * ��������JTextFieldΪ���ɱ༭״̬
		 */
		public void lock() {
			for (int i = 1; i < 10; i++)
				for (int j = 1; j < 10; j++) {
					block[i][j].setEditable(false);
				}
		}
	}

	private static final long serialVersionUID = 1L;

	/**
	 * Ĭ�ϴ�С
	 */
	public static final Dimension BOARDSIZE = new Dimension(9 * 62, 9 * 62);

	/**
	 * Block�в������ֺ���õĺ���
	 * 
	 * @param x
	 *            ��Ӧ��x����
	 * @param y
	 *            ��Ӧ��y����
	 */
	public abstract void BlockInsert(int x, int y);

	/**
	 * Block��ɾ�����ֺ���õĺ���
	 * 
	 * @param x
	 *            ��Ӧ��x����
	 * @param y
	 *            ��Ӧ��y����
	 * @param oldVal
	 *            �˸�ԭ��������
	 */
	public abstract void BlockRemove(int x, int y, int oldVal);

	/**
	 * Block���޸����ֺ���õĺ���
	 * 
	 * @param x
	 *            ��Ӧ��x����
	 * @param y
	 *            ��Ӧ��y����
	 * @param oldVal
	 *            �˸�ԭ��������
	 */
	public abstract void BlockChanged(int x, int y, int oldVal);

	/**
	 * �����ڵ�����
	 */
	protected MainFrame frame;

	/**
	 * �ײ���������
	 */
	protected GUISudokuBoard sb;

	/**
	 * ���������
	 */
	private SudokuBoardPanel sudokuBoardPanel;

	/**
	 * �������
	 */
	private DashBoardPanel dashBoardPanel;

	/**
	 * �����¼
	 */
	protected Vector<Step> steps = new Vector<Step>();

	/**
	 * ��ǰ�����ڲ����¼�е�λ��
	 */
	protected int stepPointer = 0;

	/**
	 * �Ƿ����ڳ���������
	 */
	protected boolean isUndoingOrRedoing = false;

	/**
	 * ��ʼ�����������
	 * 
	 * @param frame
	 *            ����������
	 */
	public SudokuGamePanel(MainFrame frame) {
		this.frame = frame;
		setLayout(new BorderLayout(0, 0));
		sudokuBoardPanel = new SudokuBoardPanel();
		dashBoardPanel = new DashBoardPanel();
		add(sudokuBoardPanel, BorderLayout.CENTER);
		add(dashBoardPanel, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(Math.max(sudokuBoardPanel
				.getPreferredSize().width,
				dashBoardPanel.getPreferredSize().width), sudokuBoardPanel
				.getPreferredSize().height
				+ dashBoardPanel.getPreferredSize().height));
	}

	/**
	 * ��ÿ�����������
	 * 
	 * @return ������������
	 */
	public DashBoardPanel getDashBoardPanel() {
		return dashBoardPanel;
	}

	/**
	 * ���ÿ������
	 * 
	 * @param panel
	 *            Ҫ���õĿ������
	 */
	public void setDashBoardPanel(DashBoardPanel panel) {
		remove(dashBoardPanel);
		dashBoardPanel = panel;
		add(dashBoardPanel, BorderLayout.SOUTH);
	}

	/**
	 * ���������������
	 * 
	 * @return ������������
	 */
	public SudokuBoardPanel getSudokuBoardPanel() {
		return sudokuBoardPanel;
	}

	/**
	 * ��������������Ӧ�����JTextField
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 * @return ��Ӧ�����JTextField
	 */
	public JTextField getBlock(int x, int y) {
		return sudokuBoardPanel.block[x][y];
	}

	/**
	 * �������������Ӧ�����JTextField������ͬ�����ײ���������
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 */
	public void synchronizeBlock(int x, int y) {
		int v = 0;
		if (!getBlock(x, y).getText().equals(""))
			v = Integer.parseInt(getBlock(x, y).getText());
		sb.setVal(x, y, v);
	}

	/**
	 * ��������ڵ�JMenuBarΪMainControl�����ø�MainControl��boardChanged()����
	 */
	public void synchronizeMenu() {
		if (frame.getJMenuBar() != null
				&& !(frame.getJMenuBar() instanceof MainControl))
			return;
		((MainControl) frame.getJMenuBar()).boardChanged();
	}

	/**
	 * ʹ��������е�JTextField��ȡ�ײ������̵�����
	 */
	public abstract void updateBlocks();

	/**
	 * ����ײ�������
	 * 
	 * @param sb
	 *            Ҫ����ĵײ�������
	 */
	public void loadSudokuBoard(SudokuBoard sb) {
		if (sb instanceof GUISudokuBoard) {
			this.sb = (GUISudokuBoard) sb;
		} else {
			this.sb = new GUISudokuBoard(sb);
			this.sb.setOriginalBoard(sb);
		}
		updateBlocks();
	}

	/**
	 * �������ַ�����ʽ��ʾ�ĵײ�������
	 * 
	 * @param strBoard
	 *            Ҫ��������ַ�����ʽ��ʾ��������
	 */
	public void loadSudokuBoard(String strBoard) {
		sb = new GUISudokuBoard(strBoard);
		updateBlocks();
	}

	/**
	 * ��õײ�������
	 * 
	 * @return �ײ�������
	 */
	public GUISudokuBoard getSudokuBoard() {
		return sb;
	}

	/**
	 * ��¼����
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 * @param oldV
	 *            �������ԭ����ֵ
	 */
	public void recordStep(int x, int y, int oldV) {
		System.out.println("" + x + ":" + y + ":" + oldV);
		stepPointer++;
		int v;
		if (getBlock(x, y).getText().equals(""))
			v = 0;
		else
			v = Integer.parseInt(getBlock(x, y).getText());
		steps.add(new Step(x, y, oldV, v));
		while (steps.size() != stepPointer) {
			steps.remove(steps.size() - 1);
		}
		synchronizeMenu();
	}

	/**
	 * Ŀǰ�Ƿ���Գ�������
	 * 
	 * @return �Ƿ���Գ�������
	 */
	public boolean canUndo() {
		return stepPointer > 0;
	}

	/**
	 * ��������
	 */
	public void undo() {
		isUndoingOrRedoing = true;
		Step step = steps.get(--stepPointer);
		System.out.println(step);
		System.out.println(step.v);
		getBlock(step.x, step.y).setText(step.v == 0 ? "" : "" + step.v);
		synchronizeBlock(step.x, step.y);
		synchronizeMenu();
		isUndoingOrRedoing = false;
	}

	/**
	 * Ŀǰ�Ƿ������������
	 * 
	 * @return �Ƿ������������
	 */
	public boolean canRedo() {
		return stepPointer < steps.size();
	}

	/**
	 * ��������
	 */
	public void redo() {
		isUndoingOrRedoing = true;
		Step step = steps.get(stepPointer++);
		getBlock(step.x, step.y).setText(step.v == 0 ? "" + step.newV : "");
		synchronizeBlock(step.x, step.y);
		synchronizeMenu();
		isUndoingOrRedoing = false;
	}

}
