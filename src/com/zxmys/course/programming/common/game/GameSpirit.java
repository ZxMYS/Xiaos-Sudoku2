/**
 * 
 */
package com.zxmys.course.programming.common.game;

import java.awt.*;

/**
 * 游戏图像纹理类
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public abstract class GameSpirit {

	/**
	 * 坐标
	 */
	private double x, y;

	/**
	 * 长、宽
	 */
	private double width, height;

	/**
	 * 绘图过程
	 * 
	 * @param g
	 *            所使用的Graphics
	 */
	public abstract void draw(Graphics g);

	/**
	 * 是否存活
	 * 
	 * @return 是否存活
	 */
	public abstract boolean isLiving();

	/**
	 * 获得x坐标
	 * 
	 * @return x坐标
	 */
	public double getX() {
		return x;
	}

	/**
	 * 设置x坐标
	 * 
	 * @param x
	 *            要设置的x坐标
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * 获得y坐标
	 * 
	 * @return y坐标
	 */
	public double getY() {
		return y;
	}

	/**
	 * 设置y坐标
	 * 
	 * @param y
	 *            要设置的y坐标
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * 获得高度
	 * 
	 * @return 高度
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * 设置高度
	 * 
	 * @param height
	 *            要设置的高度
	 */
	public void setHeight(double height) {
		this.height = height;
	}

	/**
	 * 获得宽度
	 * 
	 * @return 宽度
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * 设置宽度
	 * 
	 * @param width
	 *            要设置的宽度
	 */
	public void setWidth(double width) {
		this.width = width;
	}
}
