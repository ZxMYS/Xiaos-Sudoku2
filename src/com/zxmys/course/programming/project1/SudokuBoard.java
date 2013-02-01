/**
 * 
 */
package com.zxmys.course.programming.project1;

import java.io.Serializable;
import java.util.Arrays;

/**
 * ����������
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
	 * �����е�����
	 */
	protected int[][] board = new int[10][10];

	/**
	 * �����пհ׵ĸ���
	 */
	private int zeroCounter = 0;

	/**
	 * ��������Ƿ���Ч
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 * @throws IllegalArgumentException
	 *             ���������Ч
	 */
	protected void checkArgument(int x, int y) throws IllegalArgumentException {
		if (x <= 0 || x >= 10 || y <= 0 || y >= 10)
			throw new IllegalArgumentException();
	}

	/**
	 * �������������ֵ�Ƿ���Ч
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 * @param v
	 *            �����ֵ
	 * @throws IllegalArgumentException
	 *             �������������ֵ��Ч
	 */
	protected void checkArgument(int x, int y, int v) {
		checkArgument(x, y);
		if (v < 0 || v >= 10)
			throw new IllegalArgumentException();
	}

	/**
	 * ��ʼ��ȫ�յ�����
	 */
	public SudokuBoard() {
		for (int i = 1; i <= 9; i++)
			Arrays.fill(board[i], 0);
		zeroCounter = 81;
	}

	/**
	 * ��ʼ����������һ������һ��������
	 * 
	 * @param board
	 *            ��һ������
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
	 * ������String��ʽ��ʾ���������̡�
	 * �ַ��������(0,0)��(9,9)���������Ҵ��ϵ��µ������������ݣ�0��ʾ�ո� 
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
	 * �˷�������{@link java.lang.Object#toString()} ���������ʽ�����������ַ���
	 * 
	 * @see java.lang.Object#toString()
	 * @return ������ʽ�����������ַ���
	 */
	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder("�����ש��ש��ש��ש��ש��ש��ש��ש���\n");
		for (int i = 1; i <= 9; i++) {
			ret.append("��");
			for (int j = 1; j <= 9; j++) {
				ret.append(" " + (board[i][j] == 0 ? " " : "" + board[i][j])
						+ "��");
			}
			if (i == 9)
				break;
			ret.append("\n�ǩ��贈�贈�贈�贈�贈�贈�贈�贈��\n");
		}
		ret.append("\n�����ߩ��ߩ��ߩ��ߩ��ߩ��ߩ��ߩ��ߩ���\n");
		return ret.toString();
	}

	/**
	 * ����������������
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 * @param v
	 *            �����ֵ
	 * @throws IllegalArgumentException
	 *             �������������ֵ��Ч
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
	 * ���������ָ�����������
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 * @return ָ�����������
	 */
	public int getVal(int x, int y) {
		checkArgument(x, y);
		return board[x][y];
	}

	/**
	 * �Ƿ��ǿ����н������
	 * 
	 * @return ���������һ���޷������κ����ַ���false�����򷵻�true
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
	 * �����ָ�������������Ƿ�������ͻ
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 * @param v
	 *            Ҫ���������
	 * @return ����������뷵��int[]{0,0}�����򷵻ص�һ����⵽�ĳ�ͻ����int[]{x,y}
	 * @throws IllegalArgumentException
	 *             ���������Ч
	 */
	public int[] isValid(int x, int y, int v) throws IllegalArgumentException {
		checkArgument(x, y);
		int[] ret = new int[2];
		if (v == 0)
			return ret;
		ret[0] = ret[1] = 0;

		// ����г�ͻ
		for (int i = 1; i <= 9; i++) {
			if (i == x)
				continue;
			if (board[i][y] == v) {
				ret[0] = i;
				ret[1] = y;
				return ret;
			}
		}
		// ����г�ͻ
		for (int i = 1; i <= 9; i++) {
			if (i == y)
				continue;
			if (board[x][i] == v) {
				ret[0] = x;
				ret[1] = i;
				return ret;
			}
		}
		// ���С�Ź����ͻ
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
	 * ��������Ƿ��ѽ�
	 * 
	 * @return �Ƿ��ѽ�
	 */
	public boolean isSolved() {
		return zeroCounter == 0;
	}

}
