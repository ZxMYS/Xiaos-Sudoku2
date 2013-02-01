/**
 * 
 */
package com.zxmys.course.programming.project2.Panel.SudokuPanel;

import com.zxmys.course.programming.project1.*;
import com.zxmys.course.programming.project2.MainFrame;

/**
 * �������ʵ��֮һ�ı༭�࣬ʵ�ֱ༭�����Ĺ��ܡ� �༭ģʽ�еײ������̵���Ŀ����������������ݽ�����һ�£����༭ģʽ�����и�����Ŀ��
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.15)
 */
public class SudokuEditPanel extends SudokuGamePanel {

	/**
	 * @param frame
	 *            �����ڵ�����
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
	 * ͬ�����ݵ��ײ������̡�ͬʱͬ��������������Ŀ��
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
