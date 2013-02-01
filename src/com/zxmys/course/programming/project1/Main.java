/**
 * 
 */
package com.zxmys.course.programming.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.LinkedList;

import javax.swing.JOptionPane;

/**
 * 命令行界面主程序
 * 
 * @author Zx.MYS
 * @version 1.1 (2009.11.25)
 */

public class Main {

	/**
	 * 是否是有效局面
	 */
	private static boolean isValidBoard = true;

	/**
	 * 数独棋盘
	 */
	private static SudokuBoard board = new SudokuBoard();

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
			System.out.println("没有可以撤销的步骤！");
			return false;
		}

		Step step = (Step) steps.getLast();
		board.setVal(step.x, step.y, step.v);
		steps.removeLast();
		System.out.println("已经撤销步骤：" + step);

		return true;

	}

	/**
	 * 界面及提示
	 */
	private static void showHints() {

		String hints = "命令列表： \n(x,y)v:在第x行第y列填入v(非死局情况下)\n"
				+ "d(x,y)删除第x行第y列的数字\n(1<=x,y,v<=9)\n"
				+ "giveup:显示答案\nexit:退出 "
				+ "back或undo:撤销\nstrict:打开/关闭严格死局判断模式\n";
		if (!Common.Variable.isNarrowScreen())
			Common.Function.printParallel(board.toString(), hints);
		else
			System.out.println(hints + "\n" + board);
		System.out.println("请输入命令\n");

	}

	/**
	 * 以命令行显示数独的解答
	 * 
	 * @param board
	 *            要求解的数独
	 */
	private static void showAnswer(SudokuBoard board) {

		SudokuSolver ss = new SudokuSolver();
		ss.setBoard(board);
		ss.solveBoard();
		System.out.println(board);

	}

	/**
	 * 检查是否是死局
	 * 
	 * @return 是否是死局
	 */
	private static boolean checkBoard() {

		if (Common.Variable.isStrictValidBoardMode()) {

			SudokuSolver ss = new SudokuSolver();
			ss.setBoard(new SudokuBoard(board));
			if (!ss.solveBoard()) {
				System.out.println("你已陷入死局，请输入back返回上一步或d(x,y)删除一个数字\n");
				return false;
			}

		} else {

			return board.isValid();

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
	 * @return 操作成功且填入后不是死局返回true，否则返回false
	 */
	private static boolean setVal(int x, int y, int v) {

		try {

			if (v != 0 && board.getVal(x, y) != 0) {
				System.out.println("第" + x + "行，第" + y + "列已有数字，请重新输入。\n");
				return false;
			}
			int[] isBlockValid = board.isValid(x, y, v);
			if (isBlockValid[0] != 0) {
				System.out.println("填入数字与表格中第" + isBlockValid[0] + "行，第"
						+ isBlockValid[1] + "列的数字有冲突，请重新输入。\n");
				return false;
			}
			recordStep(x, y, v);
			board.setVal(x, y, v);

		} catch (IllegalArgumentException e) {
			System.out.println("你输入的数据超出范围，请重新输入！");
			return false;
		}

		return true;

	}

	/**
	 * 命令行界面主程序
	 * 
	 * @param args
	 *            启动参数
	 * @throws IOException
	 *             当命令行IO出错时
	 */
	public static void main(String[] args) throws IOException {

		loadGame();
		SudokuBoard originalBoard = new SudokuBoard(board);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String strIn;

		// 循环直到程序退出或数独已解
		while (!board.isSolved()) {

			// 显示界面
			showHints();

			if ((strIn = in.readLine()) == null) {
				JOptionPane.showMessageDialog(null, "输入不能为空，请重新输入");
				continue;
			}

			strIn = strIn.toLowerCase();

			// 以下处理用户输入

			if (strIn.equals("exit")) {

				System.exit(0);

			} else if (strIn.equals("giveup")) {

				System.out.println("你确定要看答案吗？是请输入Y或y，其他输入为否");
				strIn = in.readLine();
				if (strIn != null && strIn.equalsIgnoreCase("Y")) {
					showAnswer(originalBoard);
					System.out.println("答案已显示，请按回车键退出");
					strIn = in.readLine();
					System.exit(0);
				} else {
					System.out.println("你选择不看答案");
				}

			} else if (strIn.equals("back") || strIn.equals("undo")) {

				undoStep();
				isValidBoard = checkBoard();

			} else if (strIn.equals("strict")) {

				Common.Variable.setStrictValidBoardMode(!Common.Variable
						.isStrictValidBoardMode());
				System.out.println("严格死局判断模式已"
						+ (Common.Variable.isStrictValidBoardMode() ? "打开"
								: "关闭"));
				isValidBoard = checkBoard();

			} else if (strIn.length() == 6 && strIn.charAt(0) == 'd'
					&& strIn.charAt(1) == '(' && strIn.charAt(3) == ','
					&& strIn.charAt(5) == ')') {

				int x = strIn.charAt(2) - '0';
				int y = strIn.charAt(4) - '0';
				if (originalBoard.getVal(x, y) != 0) {
					System.out.println("第" + x + "行，第" + y + "列上的数字是题目原有的数字");
					continue;
				}
				if (board.getVal(x, y) == 0) {
					System.out.println("第" + x + "行，第" + y + "列上没有数字");
					continue;
				}

				if (setVal(x, y, 0))
					System.out.println("删除成功");
				isValidBoard = checkBoard();

			} else if (strIn.length() == 6 && strIn.charAt(0) == '('
					&& strIn.charAt(2) == ',' && strIn.charAt(4) == ')') {
				if (isValidBoard) {
					int x = strIn.charAt(1) - '0';
					int y = strIn.charAt(3) - '0';
					int v = strIn.charAt(5) - '0';
					if (v == 0) {
						System.out.println("你输入的数据超出范围，请重新输入！");
						continue;
					}
					if (setVal(x, y, v))
						System.out.println("数据已正确填入");

					isValidBoard = checkBoard();

				} else {
					System.out.println("目前是死局状态，你不能继续填写数字！请撤消步骤或删除已有数字。");
				}

			} else {

				System.out.println("你输入的格式不正确，请重新输入！");

			}

		}
		// 游戏胜利
		System.out.println(board + "恭喜，你赢得了本局的胜利！\n" + "你共耗时"
				+ (Common.Variable.getGameTime()) + "秒\n" + "请按回车键退出");
		strIn = in.readLine();
		System.exit(0);
	}

}
