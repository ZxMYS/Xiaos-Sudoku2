package com.zxmys.course.programming.project2.Panel.SudokuPanel;

import java.awt.*;

import javax.swing.JOptionPane;

import com.zxmys.course.programming.project2.MainFrame;
import com.zxmys.course.programming.project2.Panel.BlurredPanel;

/**
 * 数独面板实现之一的游戏类，实现进行数独游戏的功能
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.15)
 */
public class SudokuPlayPanel extends SudokuGamePanel {

	/**
	 * 初始化并开始控制面板的计时
	 * 
	 * @param frame
	 *            主窗口的引用
	 */
	public SudokuPlayPanel(MainFrame frame) {
		super(frame);
		if (getDashBoardPanel() instanceof DashBoardPanel) {
			((DashBoardPanel) getDashBoardPanel()).startCountingTime();
		}
	}

	/**
	 * 检查是否胜利 如果胜利，从主窗口中移除本Panel并显示以本Panel初始化的BlurredPanel。
	 */
	private void checkIfWin() {
		if (sb.isSolved()) {
			frame.changePane(new BlurredPanel(this) {
				private static final long serialVersionUID = 1L;
				{
					Graphics2D g2 = (Graphics2D) i.getGraphics();
					g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
					g2.setColor(Color.BLACK);
					g2.drawString("Victory!", 10, 30);
				}
			});
			JOptionPane.showMessageDialog(frame, "恭喜，你胜利了！"
					+ (getDashBoardPanel() instanceof DashBoardPanel ? "共耗时："
							+ ((DashBoardPanel) getDashBoardPanel())
									.getPlayTime() : ""));
			frame.setGameStatus(MainFrame.GAME_ENDED);
		}
	}

	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxmys.course.programming.project2.SudokuGamePanel#blockChanged(int,
	 *      int)
	 */
	@Override
	public void BlockChanged(int x, int y, int oldVal) {
		recordStep(x, y, oldVal);
		checkIfWin();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxmys.course.programming.project2.SudokuGamePanel#blockInsert(int,
	 *      int)
	 */
	@Override
	public void BlockInsert(int x, int y) {
		recordStep(x, y, 0);
		checkIfWin();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxmys.course.programming.project2.SudokuGamePanel#blockRemove(int,
	 *      int)
	 */
	@Override
	public void BlockRemove(int x, int y, int oldVal) {
		recordStep(x, y, oldVal);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxmys.course.programming.project2.Panel.SudokuPanel.SudokuGamePanel#updateBlocks()
	 */
	public void updateBlocks() {
		for (int i = 1; i < 10; i++)
			for (int j = 1; j < 10; j++) {
				int val = sb.getVal(i, j);
				if (val != 0) {
					getBlock(i, j).setText("" + val);
					if (sb.isOriginalBlock(i, j)) {
						getBlock(i, j).setEditable(false);
						getBlock(i, j).setForeground(Color.RED);
					} else {
						getBlock(i, j).setEditable(true);
					}
				}
			}
	}

}
