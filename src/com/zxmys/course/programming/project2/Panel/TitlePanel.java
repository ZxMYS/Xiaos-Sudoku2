/**
 * Zx.MYS's Sudoku.2的标题界面
 */
package com.zxmys.course.programming.project2.Panel;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;

import com.zxmys.course.programming.common.game.*;

/**
 * 飞扬的字符纹理。
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.15)
 */
class FlyingChars extends GameSpirit {

	/**
	 * 要存在多长时间
	 */
	private long liveTime;

	/**
	 * 开始出现的时间
	 */
	private long startTime;

	/**
	 * 旋转角度
	 */
	private double angle;

	/**
	 * 旋转角速度
	 */
	private double angleSpeed;

	/**
	 * x坐标速度
	 */
	private double xSpeed;

	/**
	 * y坐标速度
	 */
	private double ySpeed;

	/**
	 * 上一次绘图时间
	 */
	private long lastDrawTime;

	/**
	 * 所显示的char
	 */
	private char c;

	/**
	 * x与y坐标最大值
	 */
	private int xMax, yMax;

	/**
	 * 字符所在本纹理的x与y坐标
	 */
	private int strX, strY;

	/**
	 * 旋转用AffineTransform
	 */
	AffineTransform at = new AffineTransform();

	/**
	 * 字符颜色
	 */
	Color color;

	/**
	 * 随机初始化个参数。默认宽度与高度为30px
	 * 
	 * @param xMax
	 *            x坐标最大值
	 * @param yMax
	 *            y坐标最大值
	 * @param c
	 *            所绘字符
	 * @param g
	 *            所用Graphics
	 */
	public FlyingChars(int xMax, int yMax, char c, Graphics g) {
		reset(xMax, yMax, c, g);
		at.translate(20, 20);
		setHeight(30);
		setWidth(30);
	}

	/**
	 * 重置，再次随机初始化各参数
	 * 
	 * @param xMax
	 *            x坐标最大值
	 * @param yMax
	 *            y坐标最大值
	 * @param c
	 *            所绘字符
	 * @param g
	 *            所用Graphics
	 */
	public void reset(int xMax, int yMax, char c, Graphics g) {
		liveTime = (long) (Math.random() * 30000) + 10000;
		startTime = System.currentTimeMillis();
		angle = Math.random() * Math.PI * 2;
		angleSpeed = Math.random() * Math.PI * 2;
		xSpeed = Math.random() * 100 - 50;
		ySpeed = Math.random() * 100;
		lastDrawTime = System.currentTimeMillis();
		setX(Math.random() * xMax - 10);
		setY(Math.random() * yMax - 10);
		this.xMax = xMax;
		this.yMax = yMax;
		this.c = c;
		strX = (int) (Math.random() * 5);
		strY = (int) (Math.random() * 5);
		color = new Color((int) (Math.random() * 256),
				(int) (Math.random() * 256), (int) (Math.random() * 256));
	}

	/**
	 * 超过预先初始化的存在时间后认为已经可以丢弃
	 * 
	 * @see com.zxmys.course.programming.common.game.GameSpirit#isLiving()
	 */
	public boolean isLiving() {
		return System.currentTimeMillis() - startTime < liveTime
				&& getX() < xMax && getX() > -10 && getY() < yMax
				&& getY() > -10;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxmys.course.programming.common.game.GameSpirit#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		double intervalTime = (System.currentTimeMillis() - lastDrawTime) / 1000.0;
		lastDrawTime = System.currentTimeMillis();
		// 更新坐标位置
		setX(getX() + intervalTime * xSpeed);
		setY(getY() + intervalTime * ySpeed);
		angle = (angleSpeed * intervalTime) % (Math.PI * 2);
		// 旋转
		at.rotate(angle);
		g2.setTransform(at);
		g2.drawString("" + c, strX, strY);
	}

}

/**
 * 绘图Panel，绘制相关纹理并显示游戏标题
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.15)
 */
public class TitlePanel extends GameGraphicPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * 要使用的飞扬的数字的数量
	 */
	private int numberOfFlyingNumber = 60;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxmys.course.programming.common.game.GameGraphicPanel#draw(java.awt.Graphics)
	 */
	@Override
	public synchronized void draw(Graphics g) {
		clear();
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform oldAt = g2.getTransform();
		AffineTransform at = new AffineTransform();
		at.scale(3, 3);
		g2.setTransform(at);
		g2.drawString("Zx.MYS's Sudoku.2", 20, 50);
		g2.setTransform(oldAt);
		g2.drawString("--A Simple Sudoku Game By Zx.MYS", 290, 200);
		if (running) {
			g.drawString("FPS:" + curFPS, 15, 15);
			FontMetrics metrics = g2.getFontMetrics();
			String strToShow = "~ Please Click Your Mouse ~";
			g2.drawString(strToShow, (getWidth() - metrics
					.stringWidth(strToShow)) / 2, getHeight() - 20);
		}
	}

	/**
	 * 初始化数字并开始绘图
	 * 
	 * @see com.zxmys.course.programming.common.game.GameGraphicPanel#start()
	 */
	public void start() {
		super.prepare();
		for (int i = 0; i < numberOfFlyingNumber; i++) {
			addSpirit(new FlyingChars(getWidth(), getHeight(), (char) (Math
					.random() * 10 + '0'), getBgGraphics()));
		}
		super.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxmys.course.programming.common.game.GameGraphicPanel#drawSpirit(java.util.ListIterator,
	 *      java.awt.Graphics)
	 */
	@Override
	public void drawSpirit(ListIterator<GameSpirit> iter, Graphics g) {
		while (iter.hasNext()) {
			FlyingChars spirit = (FlyingChars) iter.next();
			if (spirit.isLiving()) {
				BufferedImage spiritImg = new BufferedImage(40, 40,
						BufferedImage.TYPE_INT_ARGB);
				// 先将纹理绘制到另一个缓冲
				Graphics2D g2 = (Graphics2D) spiritImg.getGraphics();
				spirit.draw(g2);
				// 然后根据纹理坐标绘制到本Panel缓冲
				((Graphics2D) g).drawImage(spiritImg, (int) spirit.getX(),
						(int) spirit.getY(), null);
			} else {
				spirit.reset(getWidth(), getHeight(),
						(char) (Math.random() * 10 + '0'), getBgGraphics());
			}
		}
	}
}
