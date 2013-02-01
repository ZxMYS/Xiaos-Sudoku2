/**
 * 
 */
package com.zxmys.course.programming.common.game;

import java.awt.*;

/**
 * ��Ϸͼ��������
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public abstract class GameSpirit {

	/**
	 * ����
	 */
	private double x, y;

	/**
	 * ������
	 */
	private double width, height;

	/**
	 * ��ͼ����
	 * 
	 * @param g
	 *            ��ʹ�õ�Graphics
	 */
	public abstract void draw(Graphics g);

	/**
	 * �Ƿ���
	 * 
	 * @return �Ƿ���
	 */
	public abstract boolean isLiving();

	/**
	 * ���x����
	 * 
	 * @return x����
	 */
	public double getX() {
		return x;
	}

	/**
	 * ����x����
	 * 
	 * @param x
	 *            Ҫ���õ�x����
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * ���y����
	 * 
	 * @return y����
	 */
	public double getY() {
		return y;
	}

	/**
	 * ����y����
	 * 
	 * @param y
	 *            Ҫ���õ�y����
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * ��ø߶�
	 * 
	 * @return �߶�
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * ���ø߶�
	 * 
	 * @param height
	 *            Ҫ���õĸ߶�
	 */
	public void setHeight(double height) {
		this.height = height;
	}

	/**
	 * ��ÿ��
	 * 
	 * @return ���
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * ���ÿ��
	 * 
	 * @param width
	 *            Ҫ���õĿ��
	 */
	public void setWidth(double width) {
		this.width = width;
	}
}
