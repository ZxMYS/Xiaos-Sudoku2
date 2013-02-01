package com.zxmys.course.programming.project2.Panel.SudokuPanel.BoardColor;

import java.awt.*;

/**
 * 简单的数独盘颜色配置类实现，对在同一小九宫格内的格子使用同样的颜色
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.15)
 */
public class SimpleSudokuBoardColor implements SudokuBoardColor {
	
	
	/**
	 * 初始化所有颜色为白色
	 */
	public SimpleSudokuBoardColor() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				c[i][j] = Color.WHITE;
	}

	/**
	 * 初始化所有格子的颜色为c
	 * @param c 要设置的颜色
	 	*/
	public SimpleSudokuBoardColor(Color c) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				this.c[i][j] = c;
	}

	/**
	 * 初始化九个小九宫格中十字的格子颜色为cross，其余格子的颜色为rest
	 * @param cross 十字的颜色
	 * @param rest 其余的颜色
	 */
	public SimpleSudokuBoardColor(Color cross, Color rest) {
		c[0][0] = c[0][2] = c[2][0] = c[2][2] = rest;
		c[1][0] = c[0][1] = c[1][1] = c[1][2] = c[2][1] = cross;
	}

	/**
	 * 用一个数组初始化颜色。<br/>
	 * 当数组长度为2时相当于SimpleSudokuBoardColor(carr[0], carr[1])<br/>
	 * 当数组长度为9时以从左至右从上到下的顺序为九个小九宫格设置颜色<br/>
	 * 当数组长度为其他时抛出IllegalArgumentException
	 * @param carr 用于初始化颜色的数组
	 */
	public SimpleSudokuBoardColor(Color[] carr) {
		this(carr[0], carr[1]);
		if (carr.length == 2) {
			return;
		}
		if (carr.length != 9) {
			throw new IllegalArgumentException();
		}
		int p = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				c[i][j] = carr[p++];
	}

	/**
	 * 九个小九宫格的颜色
	 */
	protected Color[][] c = new Color[3][3];

	/* (non-Javadoc)
	 * @see com.zxmys.course.programming.project2.Panel.SudokuPanel.BoardColor.SudokuBoardColor#getBlockColor(int, int)
	 */
	public Color getBlockColor(int x, int y) {
		return c[(x - 1) / 3][(y - 1) / 3];
	}

	/** 
	 * 修改一格颜色的同时将修改所有在同一小九宫格中的格子的颜色
	 * @see com.zxmys.course.programming.project2.Panel.SudokuPanel.BoardColor.SudokuBoardColor#setBlockColor(int, int, java.awt.Color)
	 */
	public void setBlockColor(int x, int y, Color c) {
		this.c[(x - 1) / 3][(y - 1) / 3] = c;
	}

}
