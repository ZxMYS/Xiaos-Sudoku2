package com.zxmys.course.programming.project2.Panel.SudokuPanel.BoardColor;

import java.awt.Color;

/**
 * 数独面板类所使用的数独盘颜色配置抽象基类
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public interface SudokuBoardColor {

	/**
	 * 获得坐标为x,y的格子的颜色
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @return 指定格子的颜色
	 */
	public Color getBlockColor(int x, int y);

	/**
	 * 获得坐标为x,y的格子的颜色
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param c
	 *            要设置的颜色
	 */
	public void setBlockColor(int x, int y, Color c);

}
