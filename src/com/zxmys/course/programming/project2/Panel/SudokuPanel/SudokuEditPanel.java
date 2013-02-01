/**
 * 
 */
package com.zxmys.course.programming.project2.Panel.SudokuPanel;

import com.zxmys.course.programming.project1.*;
import com.zxmys.course.programming.project2.MainFrame;

/**
 * 数独面板实现之一的编辑类，实现编辑数独的功能。 编辑模式中底层数独盘的题目数据与玩家填入数据将保持一致，即编辑模式中所有格都是题目格
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.15)
 */
public class SudokuEditPanel extends SudokuGamePanel {

	/**
	 * @param frame
	 *            主窗口的引用
	 */
	public SudokuEditPanel(MainFrame frame) {
		super(frame);
	}

	/**
	 * 
	 */
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

	/**
	 * 同步数据到底层数独盘。同时同步玩家所填格与题目格。
	 * 
	 * @see com.zxmys.course.programming.project2.Panel.SudokuPanel.SudokuGamePanel#synchronizeBlock(int,
	 *      int)
	 */
	@Override
	public void synchronizeBlock(int x, int y) {
		super.synchronizeBlock(x, y);
		int v = 0;
		if (!getBlock(x, y).getText().equals(""))
			v = Integer.parseInt(getBlock(x, y).getText());
		sb.getOriginalBoard().setVal(x, y, v);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxmys.course.programming.project2.Panel.SudokuPanel.SudokuGamePanel#updateBlocks()
	 */
	@Override
	public void updateBlocks() {
		for (int i = 1; i < 10; i++)
			for (int j = 1; j < 10; j++) {
				int val = sb.getVal(i, j);
				if (val != 0) {
					getBlock(i, j).setText("" + val);
					getBlock(i, j).setEditable(true);
				}
			}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxmys.course.programming.project2.Panel.SudokuPanel.SudokuGamePanel#loadSudokuBoard(com.zxmys.course.programming.project1.SudokuBoard)
	 */
	@Override
	public void loadSudokuBoard(SudokuBoard sb) {
		super.loadSudokuBoard(sb);
		if (sb instanceof GUISudokuBoard) {
			this.sb.setOriginalBoard(this.sb);
		}
	}

}
