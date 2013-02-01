/**
 * Zx.MYS's Sudoku.2�ı������
 */
package com.zxmys.course.programming.project2.Panel;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;

import com.zxmys.course.programming.common.game.*;

/**
 * ������ַ�����
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.15)
 */
class FlyingChars extends GameSpirit {

	/**
	 * Ҫ���ڶ೤ʱ��
	 */
	private long liveTime;

	/**
	 * ��ʼ���ֵ�ʱ��
	 */
	private long startTime;

	/**
	 * ��ת�Ƕ�
	 */
	private double angle;

	/**
	 * ��ת���ٶ�
	 */
	private double angleSpeed;

	/**
	 * x�����ٶ�
	 */
	private double xSpeed;

	/**
	 * y�����ٶ�
	 */
	private double ySpeed;

	/**
	 * ��һ�λ�ͼʱ��
	 */
	private long lastDrawTime;

	/**
	 * ����ʾ��char
	 */
	private char c;

	/**
	 * x��y�������ֵ
	 */
	private int xMax, yMax;

	/**
	 * �ַ����ڱ������x��y����
	 */
	private int strX, strY;

	/**
	 * ��ת��AffineTransform
	 */
	AffineTransform at = new AffineTransform();

	/**
	 * �ַ���ɫ
	 */
	Color color;

	/**
	 * �����ʼ����������Ĭ�Ͽ����߶�Ϊ30px
	 * 
	 * @param xMax
	 *            x�������ֵ
	 * @param yMax
	 *            y�������ֵ
	 * @param c
	 *            �����ַ�
	 * @param g
	 *            ����Graphics
	 */
	public FlyingChars(int xMax, int yMax, char c, Graphics g) {
		reset(xMax, yMax, c, g);
		at.translate(20, 20);
		setHeight(30);
		setWidth(30);
	}

	/**
	 * ���ã��ٴ������ʼ��������
	 * 
	 * @param xMax
	 *            x�������ֵ
	 * @param yMax
	 *            y�������ֵ
	 * @param c
	 *            �����ַ�
	 * @param g
	 *            ����Graphics
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
	 * ����Ԥ�ȳ�ʼ���Ĵ���ʱ�����Ϊ�Ѿ����Զ���
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
		// ��������λ��
		setX(getX() + intervalTime * xSpeed);
		setY(getY() + intervalTime * ySpeed);
		angle = (angleSpeed * intervalTime) % (Math.PI * 2);
		// ��ת
		at.rotate(angle);
		g2.setTransform(at);
		g2.drawString("" + c, strX, strY);
	}

}

/**
 * ��ͼPanel���������������ʾ��Ϸ����
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.15)
 */
public class TitlePanel extends GameGraphicPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Ҫʹ�õķ�������ֵ�����
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
	 * ��ʼ�����ֲ���ʼ��ͼ
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
				// �Ƚ�������Ƶ���һ������
				Graphics2D g2 = (Graphics2D) spiritImg.getGraphics();
				spirit.draw(g2);
				// Ȼ���������������Ƶ���Panel����
				((Graphics2D) g).drawImage(spiritImg, (int) spirit.getX(),
						(int) spirit.getY(), null);
			} else {
				spirit.reset(getWidth(), getHeight(),
						(char) (Math.random() * 10 + '0'), getBgGraphics());
			}
		}
	}
}
