package com.zxmys.course.programming.project1;

import java.io.Serializable;

/**
 * GUI����������
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
	 * ��������Ŀ
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
	 * ��ʼ������һ��GUI������ͬ��GUI����
	 * 
	 * @param board
	 *            ��һ��GUI����
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
	 * ���ñ��������̵���Ŀ
	 * 
	 * @param board
	 *            ����
	 */
	public void setOriginalBoard(SudokuBoard board) {
		originalBoard = new SudokuBoard(board);
	}

	/**
	 * ���ָ�����Ƿ�Ϊԭ�⼴�еĸ���
	 * 
	 * @param x
	 * @param y
	 * @return ���originalBoardδ���ã�����false�����򷵻�ָ�����Ƿ�Ϊԭ�⼴�еĸ���
	 */
	public boolean isOriginalBlock(int x,int y){
		checkArgument(x, y);
		if(originalBoard==null)return false;
		return originalBoard.getVal(x, y)==getVal(x,y)&&getVal(x,y)!=0;
	}
	
	
	/**
	 * ��ñ��������̵���Ŀ
	 * 
	 * @return ����
	 */
	public SudokuBoard getOriginalBoard() {
		return originalBoard;
	}

	/**
	 * �˷�������{@link SudokuBoard#toString()} �����HTML�����ʽ���������棬������Ŀ�е�����Ϊ��ɫ��
	 * 
	 * @see java.lang.Object#toString()
	 * @return HTML�����ʽ��������Ŀ�ַ���
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
					// ��λ�õ�������ԭ����Ŀ���е����֣������ɫ
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
