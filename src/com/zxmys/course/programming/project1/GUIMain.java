package com.zxmys.course.programming.project1;

import javax.swing.JOptionPane;
import java.util.LinkedList;

/**
 * GUI界面主程序
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.11.25)
 */
public class GUIMain {

	/**
	 * 是否是有效局面
	 */
	private static boolean isValidBoard = true;

	/**
	 * 数独棋盘
	 */
	private static GUISudokuBoard board = new GUISudokuBoard();

	/**
	 * 用于存储步骤
	 */
	private static LinkedList<Step> steps = new LinkedList<Step>();

	/**
	 * 载入游戏
	 */
	private static void loadGame() {

		String game = SudokuCreator.getStoredGame();
		for (int p = 0, i = 1; i <= 9; i++)
			for (int j = 1; j <= 9; j++) {
				board.setVal(i, j, game.charAt(p++) - '0');
			}
		board.setOriginalBoard((SudokuBoard) board);

	}

	/**
	 * 记录新步骤
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param newV
	 *            填入的数字
	 */
	private static void recordStep(int x, int y, int newV) {

		Step step = new Step(x, y, board.getVal(x, y), newV);
		steps.add(step);

	}

	/**
	 * 撤销最新的步骤
	 * 
	 * @return 操作是否成功
	 */
	private static boolean undoStep() {

		if (steps.size() == 0) {
			JOptionPane.showMessageDialog(null, "没有可以撤销的步骤！");
			return false;
		}

		Step step = steps.getLast();
		board.setVal(step.x, step.y, step.v);
		steps.removeLast();
		JOptionPane.showMessageDialog(null, "已经撤销步骤：" + step);

		return true;

	}

	/**
	 * 以GUI显示数独的解答
	 * 
	 * @param board
	 *            要求解的数独
	 */
	private static void showAnswer(SudokuBoard board) {

		GUISudokuBoard tmp = new GUISudokuBoard(board);
		tmp.setOriginalBoard(tmp);
		SudokuSolver ss = new SudokuSolver();
		ss.setBoard(tmp);
		ss.solveBoard();
		JOptionPane.showMessageDialog(null, tmp + "\n答案已显示，按确定退出本游戏");

	}

	/**
	 * 检查是否是死局
	 * 
	 * @return 是否是死局
	 */
	private static boolean checkBoard() {

		if (Common.Variable.isStrictValidBoardMode()) {

			// 使用解数独类试解当前局面
			SudokuSolver ss = new SudokuSolver();
			ss.setBoard(new SudokuBoard(board));
			if (!ss.solveBoard()) {
				// 无解
				JOptionPane.showMessageDialog(null,
						"你已陷入死局，请输入back返回上一步或d(x,y)删除一个数字\n");
				return false;
			}

		} else {

			if (!board.isValid()) {
				JOptionPane.showMessageDialog(null,
						"你已陷入死局，请输入back返回上一步或d(x,y)删除一个数字\n");
				return false;
			} else {
				return true;
			}

		}

		return true;

	}

	/**
	 * 填入数字
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param v
	 *            填入的数字
	 * @return 操作是否成功
	 */
	private static boolean setVal(int x, int y, int v) {
		try {
			if (v != 0 && board.getVal(x, y) != 0) {
				JOptionPane.showMessageDialog(null, "第" + x + "行，第" + y
						+ "列已有数字，请重新输入。\n");
				return false;
			}

			int[] isBlockValid = board.isValid(x, y, v);
			if (isBlockValid[0] != 0) {
				JOptionPane.showMessageDialog(null, "填入数字与表格中第"
						+ isBlockValid[0] + "行，第" + isBlockValid[1]
						+ "列的数字有冲突，请重新输入。\n");
				return false;
			}

			recordStep(x, y, v);
			board.setVal(x, y, v);

		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, "你输入的数据超出范围，请重新输入！");
			return false;
		}

		return true;
	}

	/**
	 * GUI界面主程序
	 * 
	 * @param args
	 *            启动参数
	 */
	public static void main(String[] args) {

		loadGame();
		String strIn;

		// 循环直到程序退出或数独已解
		while (!board.isSolved()) {

			// 显示界面
			if ((strIn = JOptionPane.showInputDialog(null,
					"命令列表： \n(x,y)v:在第x行第y列填入v（非死局情况下）\n"
							+ "d(x,y)删除第x行第y列的数字\n(1<=x,y,v<=9)\n"
							+ "giveup:显示答案\nexit或按取消:退出 "
							+ "back或undo:撤销\nstrict:打开/关闭严格死局判断模式\n请输入命令\n"
							+ board, "Zx.MYS的数独游戏",
					JOptionPane.QUESTION_MESSAGE)) == null) {

				if (JOptionPane.showConfirmDialog(null, "你想退出游戏吗?", "退出游戏？",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				continue;

			}

			strIn = strIn.toLowerCase();

			// 以下处理用户输入

			if (strIn.equals("exit")) {

				System.exit(0);

			} else if (strIn.equals("giveup")) {

				if (JOptionPane.showConfirmDialog(null, "你确定要看答案吗？", "放弃",
						JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
					showAnswer(board.getOriginalBoard());
					System.exit(0);
				} else {
					JOptionPane.showMessageDialog(null, "你选择不看答案");
				}

			} else if (strIn.equals("back") || strIn.equals("undo")) {

				undoStep();

				isValidBoard = checkBoard();

			} else if (strIn.equals("strict")) {

				Common.Variable.setStrictValidBoardMode(!Common.Variable
						.isStrictValidBoardMode());
				JOptionPane.showMessageDialog(null, "严格死局判断模式已"
						+ (Common.Variable.isStrictValidBoardMode() ? "打开"
								: "关闭"));
				isValidBoard = checkBoard();

			} else if (strIn.length() == 6 && strIn.charAt(0) == 'd'
					&& strIn.charAt(1) == '(' && strIn.charAt(3) == ','
					&& strIn.charAt(5) == ')') {

				int x = strIn.charAt(2) - '0';
				int y = strIn.charAt(4) - '0';
				if (board.getOriginalBoard().getVal(x, y) != 0) {
					JOptionPane.showMessageDialog(null, "第" + x + "行，第" + y
							+ "列上的数字是题目原有的数字");
					continue;
				}
				if (board.getVal(x, y) == 0) {
					JOptionPane.showMessageDialog(null, "第" + x + "行，第" + y
							+ "列上没有数字");
					continue;
				}
				if (setVal(x, y, 0))
					JOptionPane.showMessageDialog(null, "删除成功");

				isValidBoard = checkBoard();

			} else if (strIn.length() == 6 && strIn.charAt(0) == '('
					&& strIn.charAt(2) == ',' && strIn.charAt(4) == ')') {
				if (isValidBoard) {
					int x = strIn.charAt(1) - '0';
					int y = strIn.charAt(3) - '0';
					int v = strIn.charAt(5) - '0';
					if (v == 0) {
						JOptionPane
								.showMessageDialog(null, "你输入的数据超出范围，请重新输入！");
						continue;
					}
					if (setVal(x, y, v))
						JOptionPane.showMessageDialog(null, "数据已正确填入");

					isValidBoard = checkBoard();

				} else {
					JOptionPane.showMessageDialog(null, "目前是死局状态，你不能继续填写数字！"
							+ "请撤消步骤或删除已有数字。");
				}
			} else {

				JOptionPane.showMessageDialog(null, "你输入的格式不正确，请重新输入！");

			}

		}

		// 游戏胜利
		JOptionPane.showMessageDialog(null, board + "\n恭喜，你赢得了本局的胜利！\n"
				+ "你共耗时" + Common.Variable.getGameTime() + "秒\n" + "请按确定退出本游戏",
				"胜利", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

}
