package com.zxmys.course.programming.project1;

import java.io.Serializable;

/**
 * GUI数独棋盘类
 * 
 * @author Zx.MYS
 * @version 1.1 (2010.1.13)
 */
public class GUISudokuBoard extends SudokuBoard implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 数独的题目
	 */
	private SudokuBoard originalBoard = null;

	/**
	 * @see SudokuBoard#SudokuBoard()
	 */
	public GUISudokuBoard() {
		super();
	}

	/**
	 * @see SudokuBoard#SudokuBoard(SudokuBoard)
	 */
	public GUISudokuBoard(SudokuBoard board) {
		super(board);
	}

	/**
	 * 初始化与另一个GUI数独相同的GUI数独
	 * 
	 * @param board
	 *            另一个GUI数独
	 */
	public GUISudokuBoard(GUISudokuBoard board) {
		super(board);
		originalBoard = new SudokuBoard(board.getOriginalBoard());
	}
	
	public GUISudokuBoard(String strBoard){
		super(strBoard);
		originalBoard = new SudokuBoard(this);
	}

	/**
	 * 设置本数独棋盘的题目
	 * 
	 * @param board
	 *            数独
	 */
	public void setOriginalBoard(SudokuBoard board) {
		originalBoard = new SudokuBoard(board);
	}

	/**
	 * 检查指定格是否为原题即有的格子
	 * 
	 * @param x
	 * @param y
	 * @return 如果originalBoard未设置，返回false。否则返回指定格是否为原题即有的格子
	 */
	public boolean isOriginalBlock(int x,int y){
		checkArgument(x, y);
		if(originalBoard==null)return false;
		return originalBoard.getVal(x, y)==getVal(x,y)&&getVal(x,y)!=0;
	}
	
	
	/**
	 * 获得本数独棋盘的题目
	 * 
	 * @return 数独
	 */
	public SudokuBoard getOriginalBoard() {
		return originalBoard;
	}

	/**
	 * 此方法覆盖{@link SudokuBoard#toString()} 获得用HTML表格形式的数独界面，其中题目中的数字为红色。
	 * 
	 * @see java.lang.Object#toString()
	 * @return HTML表格形式的数独题目字符串
	 */
	@Override
	public String toString() {
		if (originalBoard == null)
			return null;
		StringBuilder ret = new StringBuilder("<html><body>"
				+ "<table border=\"1px\" width=\"200px\">" + "<tbody>");

		for (int i = 1; i <= 9; i++) {
			ret.append("<tr>");
			for (int j = 1; j <= 9; j++) {
				ret.append("<td>");
				if (originalBoard.getVal(i, j) != 0) {
					// 此位置的数字是原来题目就有的数字，输出红色
					ret.append("<p align=\"center\"><font color=\"red\">");
					ret.append(board[i][j] == 0 ? "&nbsp" : "" + board[i][j]);
					ret.append("</font></p>");
				} else {
					ret.append("<p align=\"center\">");
					ret.append(board[i][j] == 0 ? "&nbsp" : "" + board[i][j]);
					ret.append("</p>");
				}
				ret.append("</td>");
			}
			ret.append("</tr>");
		}
		ret.append("</tbody></table></body></html>");

		return ret.toString();

	}
}
