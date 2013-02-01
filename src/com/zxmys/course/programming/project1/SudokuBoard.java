/**
 * 
 */
package com.zxmys.course.programming.project1;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 数独棋盘类
 * 
 * @author Zx.MYS
 * @version 1.1 (2009.1.13)
 */
public class SudokuBoard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 数独中的数字
	 */
	protected int[][] board = new int[10][10];

	/**
	 * 数独中空白的个数
	 */
	private int zeroCounter = 0;

	/**
	 * 检查坐标是否有效
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @throws IllegalArgumentException
	 *             如果坐标无效
	 */
	protected void checkArgument(int x, int y) throws IllegalArgumentException {
		if (x <= 0 || x >= 10 || y <= 0 || y >= 10)
			throw new IllegalArgumentException();
	}

	/**
	 * 检查坐标和填入的值是否有效
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param v
	 *            填入的值
	 * @throws IllegalArgumentException
	 *             如果坐标或填入的值无效
	 */
	protected void checkArgument(int x, int y, int v) {
		checkArgument(x, y);
		if (v < 0 || v >= 10)
			throw new IllegalArgumentException();
	}

	/**
	 * 初始化全空的数独
	 */
	public SudokuBoard() {
		for (int i = 1; i <= 9; i++)
			Arrays.fill(board[i], 0);
		zeroCounter = 81;
	}

	/**
	 * 初始化内容与另一个数独一样的数独
	 * 
	 * @param board
	 *            另一个数独
	 */
	public SudokuBoard(SudokuBoard board) {
		for (int i = 1; i <= 9; i++)
			for (int j = 1; j <= 9; j++) {
				this.board[i][j] = board.getVal(i, j);
				if (this.board[i][j] == 0)
					zeroCounter++;
			}
	}
	
	/**
	 * 读入以String形式表示的数独棋盘。
	 * 字符串储存从(0,0)到(9,9)，从左往右从上到下的数独棋盘内容，0表示空格。 
	 * @param strBoard
	 */
	public SudokuBoard(String strBoard){
		if(strBoard.length()!=81)
			throw new IllegalArgumentException();
		for (int p = 0, i = 1; i <= 9; i++)
			for (int j = 1; j <= 9; j++) {
				setVal(i, j, strBoard.charAt(p++) - '0');
			}
	}

	/**
	 * 此方法覆盖{@link java.lang.Object#toString()} 获得文字形式的数独界面字符串
	 * 
	 * @see java.lang.Object#toString()
	 * @return 文字形式的数独界面字符串
	 */
	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder("┏━┳━┳━┳━┳━┳━┳━┳━┳━┓\n");
		for (int i = 1; i <= 9; i++) {
			ret.append("┃");
			for (int j = 1; j <= 9; j++) {
				ret.append(" " + (board[i][j] == 0 ? " " : "" + board[i][j])
						+ "┃");
			}
			if (i == 9)
				break;
			ret.append("\n┣━╋━╋━╋━╋━╋━╋━╋━╋━┫\n");
		}
		ret.append("\n┗━┻━┻━┻━┻━┻━┻━┻━┻━┛\n");
		return ret.toString();
	}

	/**
	 * 向数独中填入数字
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param v
	 *            填入的值
	 * @throws IllegalArgumentException
	 *             如果坐标或填入的值无效
	 */
	public void setVal(int x, int y, int v) throws IllegalArgumentException {
		checkArgument(x, y, v);
		int[] checkValid = isValid(x, y, v);
		if (checkValid[0] != 0){
			throw new IllegalArgumentException();
		}
		if (board[x][y] == 0 && v != 0)
			zeroCounter--;
		else if (board[x][y] != 0 && v == 0)
			zeroCounter++;
		board[x][y] = v;
	}

	/**
	 * 获得数独中指定坐标的数字
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @return 指定坐标的数字
	 */
	public int getVal(int x, int y) {
		checkArgument(x, y);
		return board[x][y];
	}

	/**
	 * 是否是可能有解的数独
	 * 
	 * @return 如果有任意一格无法填入任何数字返回false，否则返回true
	 */
	public boolean isValid() {
		for (int i = 1; i <= 9; i++) {
			j: for (int j = 1; j <= 9; j++) {
				for (int k = 1; k <= 9; k++) {
					int[] isBlockValid = isValid(i, j, k);
					if (isBlockValid[0] == 0)
						continue j;
				}
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查向指定格填入数字是否会引起冲突
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param v
	 *            要填入的数字
	 * @return 如果可以填入返回int[]{0,0}，否则返回第一个检测到的冲突坐标int[]{x,y}
	 * @throws IllegalArgumentException
	 *             如果参数无效
	 */
	public int[] isValid(int x, int y, int v) throws IllegalArgumentException {
		checkArgument(x, y);
		int[] ret = new int[2];
		if (v == 0)
			return ret;
		ret[0] = ret[1] = 0;

		// 检查列冲突
		for (int i = 1; i <= 9; i++) {
			if (i == x)
				continue;
			if (board[i][y] == v) {
				ret[0] = i;
				ret[1] = y;
				return ret;
			}
		}
		// 检查行冲突
		for (int i = 1; i <= 9; i++) {
			if (i == y)
				continue;
			if (board[x][i] == v) {
				ret[0] = x;
				ret[1] = i;
				return ret;
			}
		}
		// 检查小九宫格冲突
		int startX = ((x - 1) / 3) * 3 + 1, endX = startX + 3;
		int startY = ((y - 1) / 3) * 3 + 1, endY = startY + 3;
		for (int i = startX; i < endX; i++) {
			for (int j = startY; j < endY; j++)
				if ((i != x || j != y) && board[i][j] == v) {
					ret[0] = i;
					ret[1] = j;
					return ret;
				}
		}
		return ret;
	}

	/**
	 * 检查数独是否已解
	 * 
	 * @return 是否已解
	 */
	public boolean isSolved() {
		return zeroCounter == 0;
	}

}
