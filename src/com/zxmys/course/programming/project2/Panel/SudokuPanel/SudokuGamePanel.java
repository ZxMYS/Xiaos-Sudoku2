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
 * 数独面板抽象基类，实现数独面板所需的大部分功能。
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public abstract class SudokuGamePanel extends JPanel {

	/**
	 * 默认控制面板，目前只有计时的JLabel。以该类的子类来替换默认面板加入更多功能。
	 * 
	 * @author Zx.MYS
	 */
	public class DashBoardPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		private JLabel jlb = new JLabel("已用时间：");;

		private JLabel jlbTime = new JLabel("00:00:00");

		/**
		 * 游戏开始时间
		 */
		long timerStartTime;

		/**
		 * 计时用timer
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
		 * 初始化，加入控件
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
		 * 开始计时
		 */
		public void startCountingTime() {
			jlb.setEnabled(true);
			jlbTime.setEnabled(true);
			timerStartTime = System.currentTimeMillis();
			timer.start();
		}

		/**
		 * 获得游戏时间（文字形式）
		 * 
		 * @return jlbTime上显示的游戏时间
		 */
		public String getPlayTime() {
			return jlbTime.getText();
		}

		/**
		 * 获得游戏时间
		 * 
		 * @return 以毫秒数表示的游戏时间
		 */
		public long getPlayTimeLong() {
			return System.currentTimeMillis() - timerStartTime;
		}

		/**
		 * 设置游戏时间
		 * 
		 * @param playTime
		 *            要设置的游戏时间
		 */
		public void setPlayTime(long playTime) {
			this.timerStartTime = System.currentTimeMillis() - playTime;
		}

	}

	/**
	 * 数独盘面板，用于放置及控制数独盘
	 * 
	 * @author Zx.MYS
	 * 
	 */
	public class SudokuBoardPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		/**
		 * 将JTextField的Document与坐标相联系的HashMao
		 */
		private HashMap<SudokuBlockDmt, int[]> map = new HashMap<SudokuBlockDmt, int[]>();

		/**
		 * 数独盘颜色配置
		 */
		private SudokuBoardColor sbc;

		/**
		 * 数独格JTextField的SudokuBlockDmt，限制只能输入合法数字并引发相关事件
		 * 
		 * @author Zx.MYS
		 * 
		 */
		class SudokuBlockDmt extends PlainDocument {

			private static final long serialVersionUID = 1L;

			/**
			 * 是否正在进行“修改”操作
			 */
			private boolean isChanging = true;

			/**
			 * 修改及删除操作原来的数字
			 */
			private int change = 0;

			/**
			 * 初始化，加入相应DocumentListener并调用相关函数 当(frame.getGameStatus() ==
			 * MainFrame.GAME_LOADING || frame.getGameStatus() ==
			 * MainFrame.GAME_ENDING || isUndoingOrRedoing)时不调用相关函数
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
			 * 在插入字符时检查是否合法，以及判断是否为修改操作。
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
					JOptionPane.showMessageDialog(frame, "填入数字与表格中第"
							+ isBlockValid[0] + "行，第" + isBlockValid[1]
							+ "列的数字有冲突，请重新输入。\n");
					return;
				}
				super.insertString(0, str, attr);
				isChanging = false;
			}
		}

		/**
		 * 数独格所用JTextField数组（所用下标范围为1-9,1-9）
		 */
		JTextField[][] block = new JTextField[10][10];

		/**
		 * 初始化各参数，使用GridLayout插入JTextField，同时根据数独盘颜色配置设置JTextField颜色
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
		 * 获得数独盘颜色配置
		 * 
		 * @return 数独盘颜色配置
		 */
		public SudokuBoardColor getSudokuBoardColor() {
			return sbc;
		}

		/**
		 * 设置数独盘颜色配置
		 * 
		 * @param sbc
		 *            要设置的颜色配置
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
		 * 设置所有JTextField为可编辑状态
		 */
		public void unlock() {
			for (int i = 1; i < 10; i++)
				for (int j = 1; j < 10; j++) {
					block[i][j].setEditable(true);
				}
		}

		/**
		 * 设置所有JTextField为不可编辑状态
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
	 * 默认大小
	 */
	public static final Dimension BOARDSIZE = new Dimension(9 * 62, 9 * 62);

	/**
	 * Block中插入文字后调用的函数
	 * 
	 * @param x
	 *            相应格x坐标
	 * @param y
	 *            相应格y坐标
	 */
	public abstract void BlockInsert(int x, int y);

	/**
	 * Block中删除文字后调用的函数
	 * 
	 * @param x
	 *            相应格x坐标
	 * @param y
	 *            相应格y坐标
	 * @param oldVal
	 *            此格原来的数字
	 */
	public abstract void BlockRemove(int x, int y, int oldVal);

	/**
	 * Block中修改文字后调用的函数
	 * 
	 * @param x
	 *            相应格x坐标
	 * @param y
	 *            相应格y坐标
	 * @param oldVal
	 *            此格原来的数字
	 */
	public abstract void BlockChanged(int x, int y, int oldVal);

	/**
	 * 主窗口的引用
	 */
	protected MainFrame frame;

	/**
	 * 底层数独盘类
	 */
	protected GUISudokuBoard sb;

	/**
	 * 数独盘面板
	 */
	private SudokuBoardPanel sudokuBoardPanel;

	/**
	 * 控制面板
	 */
	private DashBoardPanel dashBoardPanel;

	/**
	 * 步骤记录
	 */
	protected Vector<Step> steps = new Vector<Step>();

	/**
	 * 当前步骤在步骤记录中的位置
	 */
	protected int stepPointer = 0;

	/**
	 * 是否正在撤销或重做
	 */
	protected boolean isUndoingOrRedoing = false;

	/**
	 * 初始化，加入组件
	 * 
	 * @param frame
	 *            主窗口引用
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
	 * 获得控制面板的引用
	 * 
	 * @return 控制面板的引用
	 */
	public DashBoardPanel getDashBoardPanel() {
		return dashBoardPanel;
	}

	/**
	 * 设置控制面板
	 * 
	 * @param panel
	 *            要设置的控制面板
	 */
	public void setDashBoardPanel(DashBoardPanel panel) {
		remove(dashBoardPanel);
		dashBoardPanel = panel;
		add(dashBoardPanel, BorderLayout.SOUTH);
	}

	/**
	 * 获得数独面板的引用
	 * 
	 * @return 数独面板的引用
	 */
	public SudokuBoardPanel getSudokuBoardPanel() {
		return sudokuBoardPanel;
	}

	/**
	 * 获得数独面板中相应坐标的JTextField
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @return 相应坐标的JTextField
	 */
	public JTextField getBlock(int x, int y) {
		return sudokuBoardPanel.block[x][y];
	}

	/**
	 * 将数独面板中相应坐标的JTextField的内容同步到底层数独盘中
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 */
	public void synchronizeBlock(int x, int y) {
		int v = 0;
		if (!getBlock(x, y).getText().equals(""))
			v = Integer.parseInt(getBlock(x, y).getText());
		sb.setVal(x, y, v);
	}

	/**
	 * 如果主窗口的JMenuBar为MainControl，调用该MainControl的boardChanged()方法
	 */
	public void synchronizeMenu() {
		if (frame.getJMenuBar() != null
				&& !(frame.getJMenuBar() instanceof MainControl))
			return;
		((MainControl) frame.getJMenuBar()).boardChanged();
	}

	/**
	 * 使数独面板中的JTextField读取底层数独盘的数据
	 */
	public abstract void updateBlocks();

	/**
	 * 载入底层数独盘
	 * 
	 * @param sb
	 *            要载入的底层数独盘
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
	 * 载入以字符串形式表示的底层数独盘
	 * 
	 * @param strBoard
	 *            要载入的以字符串形式表示的数独盘
	 */
	public void loadSudokuBoard(String strBoard) {
		sb = new GUISudokuBoard(strBoard);
		updateBlocks();
	}

	/**
	 * 获得底层数独盘
	 * 
	 * @return 底层数独盘
	 */
	public GUISudokuBoard getSudokuBoard() {
		return sb;
	}

	/**
	 * 记录步骤
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param oldV
	 *            此坐标格原来的值
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
	 * 目前是否可以撤销步骤
	 * 
	 * @return 是否可以撤销步骤
	 */
	public boolean canUndo() {
		return stepPointer > 0;
	}

	/**
	 * 撤销步骤
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
	 * 目前是否可以重做步骤
	 * 
	 * @return 是否可以重做步骤
	 */
	public boolean canRedo() {
		return stepPointer < steps.size();
	}

	/**
	 * 重做步骤
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
