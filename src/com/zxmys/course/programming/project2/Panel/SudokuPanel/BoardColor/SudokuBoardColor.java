package com.zxmys.course.programming.project2.Panel.SudokuPanel.BoardColor;

import java.awt.Color;

/**
 * �����������ʹ�õ���������ɫ���ó������
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public interface SudokuBoardColor {

	/**
	 * �������Ϊx,y�ĸ��ӵ���ɫ
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 * @return ָ�����ӵ���ɫ
	 */
	public Color getBlockColor(int x, int y);

	/**
	 * �������Ϊx,y�ĸ��ӵ���ɫ
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 * @param c
	 *            Ҫ���õ���ɫ
	 */
	public void setBlockColor(int x, int y, Color c);

}
