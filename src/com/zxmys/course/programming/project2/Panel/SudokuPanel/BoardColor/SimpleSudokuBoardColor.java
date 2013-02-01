package com.zxmys.course.programming.project2.Panel.SudokuPanel.BoardColor;

import java.awt.*;

/**
 * �򵥵���������ɫ������ʵ�֣�����ͬһС�Ź����ڵĸ���ʹ��ͬ������ɫ
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.15)
 */
public class SimpleSudokuBoardColor implements SudokuBoardColor {
	
	
	/**
	 * ��ʼ��������ɫΪ��ɫ
	 */
	public SimpleSudokuBoardColor() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				c[i][j] = Color.WHITE;
	}

	/**
	 * ��ʼ�����и��ӵ���ɫΪc
	 * @param c Ҫ���õ���ɫ
	 	*/
	public SimpleSudokuBoardColor(Color c) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				this.c[i][j] = c;
	}

	/**
	 * ��ʼ���Ÿ�С�Ź�����ʮ�ֵĸ�����ɫΪcross��������ӵ���ɫΪrest
	 * @param cross ʮ�ֵ���ɫ
	 * @param rest �������ɫ
	 */
	public SimpleSudokuBoardColor(Color cross, Color rest) {
		c[0][0] = c[0][2] = c[2][0] = c[2][2] = rest;
		c[1][0] = c[0][1] = c[1][1] = c[1][2] = c[2][1] = cross;
	}

	/**
	 * ��һ�������ʼ����ɫ��<br/>
	 * �����鳤��Ϊ2ʱ�൱��SimpleSudokuBoardColor(carr[0], carr[1])<br/>
	 * �����鳤��Ϊ9ʱ�Դ������Ҵ��ϵ��µ�˳��Ϊ�Ÿ�С�Ź���������ɫ<br/>
	 * �����鳤��Ϊ����ʱ�׳�IllegalArgumentException
	 * @param carr ���ڳ�ʼ����ɫ������
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
	 * �Ÿ�С�Ź������ɫ
	 */
	protected Color[][] c = new Color[3][3];

	/* (non-Javadoc)
	 * @see com.zxmys.course.programming.project2.Panel.SudokuPanel.BoardColor.SudokuBoardColor#getBlockColor(int, int)
	 */
	public Color getBlockColor(int x, int y) {
		return c[(x - 1) / 3][(y - 1) / 3];
	}

	/** 
	 * �޸�һ����ɫ��ͬʱ���޸�������ͬһС�Ź����еĸ��ӵ���ɫ
	 * @see com.zxmys.course.programming.project2.Panel.SudokuPanel.BoardColor.SudokuBoardColor#setBlockColor(int, int, java.awt.Color)
	 */
	public void setBlockColor(int x, int y, Color c) {
		this.c[(x - 1) / 3][(y - 1) / 3] = c;
	}

}
