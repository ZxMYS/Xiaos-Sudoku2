/**
 * 
 */
package com.zxmys.course.programming.common.game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

import java.util.*;
import java.util.List;

/**
 * 游戏绘图类
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public abstract class GameGraphicPanel extends JPanel implements Runnable {

	protected static final long serialVersionUID = 1L;

	/**
	 * 计算FPS时的最大间隔时间
	 */
	protected static final long MAX_INTERVAL = 1000L;

	/**
	 * 本Panel中所附加的纹理
	 */
	protected List<GameSpirit> spirits = Collections
			.synchronizedList(new LinkedList<GameSpirit>());

	/**
	 * 最大FPS
	 */
	protected int maxFPS = 0;

	/**
	 * 是否正在运行绘图线程
	 */
	protected boolean running = false;

	/**
	 * 是否显示FPS
	 */
	protected boolean showFPS = false;

	/**
	 * 双缓冲的图像
	 */
	protected Image screen = null;

	/**
	 * 双缓冲的Graphics2D
	 */
	protected Graphics2D bgGraphics = null;

	/**
	 * 绘图线程
	 */
	protected Thread th;

	/**
	 * 计算FPS使用相关
	 */
	protected transient long interval, offsetTime, curFPS, tickStartTime,
			frameCount;

	/**
	 * 构造函数，设置默认FPS为60，同时忽略系统发来的repaint消息
	 */
	public GameGraphicPanel() {
		setIgnoreRepaint(true);
		setMaxFPS(60);
	}

	/**
	 * 不使用系统双缓冲
	 * 
	 * @see javax.swing.JComponent#isDoubleBuffered()
	 */
	public boolean isDoubleBuffered() {
		return false;
	}

	/**
	 * 退出，回收资源
	 * 
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() {
		try {
			super.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			bgGraphics = null;
			screen = null;
		}
	}

	/**
	 * 计算FPS
	 */
	protected void tickFrame() {
		frameCount++;
		interval += offsetTime;
		if (interval >= MAX_INTERVAL) {
			long timeNow = System.currentTimeMillis();
			long realElapsedTime = timeNow - tickStartTime;
			curFPS = (long) (((double) frameCount / realElapsedTime) * MAX_INTERVAL);
			frameCount = 0L;
			interval = 0L;
			tickStartTime = timeNow;
		}
	}

	/**
	 * 绘制图像
	 * 
	 * @see java.awt.Container#paintComponents(java.awt.Graphics)
	 */
	public void paintComponents(Graphics g) {
		if (bgGraphics != null) {
			draw(bgGraphics);
			synchronized (spirits) {
				ListIterator<GameSpirit> iter = spirits.listIterator();
				drawSpirit(iter, bgGraphics);
			}
			g.drawImage(screen, 0, 0, null);
		}
	}

	/**
	 * 绘制在同一使用OverlayLayout的父容器中的控件。 如果父容器不试用OverlayLayout，则直接返回
	 */
	public void drawOverlaidComponent() {
		if (!(getParent().getLayout() instanceof OverlayLayout))
			return;
		for (Component c : getParent().getComponents()) {
			if (c == this)
				continue;
			Image tmp = new BufferedImage(c.getWidth(), c.getHeight(), 1);
			Graphics tmpG = tmp.getGraphics();
			((JComponent) c).paint(tmpG);
			bgGraphics.drawImage(tmp, ((JComponent) c).getX(), ((JComponent) c)
					.getY(), null);
		}
	}

	/**
	 * 绘图
	 * 
	 * @param g
	 *            所使用的Graphics
	 */
	public abstract void draw(Graphics g);

	/**
	 * 绘制纹理。此时纹理List应该已经被本线程锁定
	 * 
	 * @param sp
	 *            纹理List的ListIterator
	 * @param g
	 *            所使用的Graphics
	 */
	public abstract void drawSpirit(ListIterator<GameSpirit> sp, Graphics g);

	/**
	 * 初始化双缓冲
	 */
	public void prepare() {
		if (!running) {
			if (getWidth() > 0 && getHeight() > 0)
				screen = new BufferedImage(getWidth(), getHeight(), 1);
			else {
				Dimension screenSize = Toolkit.getDefaultToolkit()
						.getScreenSize();
				screen = new BufferedImage(screenSize.width, screenSize.height,
						1);
			}
			bgGraphics = (Graphics2D) screen.getGraphics();
		}
	}

	/**
	 * 开始绘图线程
	 */
	public void start() {
		if (!running) {
			if (bgGraphics == null)
				prepare();
			running = true;
			th = new Thread(this);
			th.start();
		}
	}

	/**
	 * 停止绘图线程
	 */
	public void stop() {
		running = false;
	}

	/**
	 * 用背景色清除双缓冲中的图像
	 * 
	 * @param bgColor
	 *            背景色
	 */
	public void clear(Color bgColor) {
		if (bgGraphics != null) {
			Color tmp = bgGraphics.getColor();
			bgGraphics.setColor(bgColor);
			bgGraphics.fillRect(0, 0, getWidth(), getHeight());
			bgGraphics.setColor(tmp);
		}
	}

	/**
	 * 用黑色清除双缓冲中的图像
	 */
	public void clear() {
		clear(Color.BLACK);
	}

	/**
	 * 主运行线程
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		tickStartTime = System.currentTimeMillis();
		while (running) {
			long startTime = System.currentTimeMillis();
			paintComponents(this.getGraphics());
			tickFrame();
			long timeToSleep = offsetTime
					- (System.currentTimeMillis() - startTime);
			while (timeToSleep > 0) {
				try {
					Thread.sleep(timeToSleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				long curTime = System.currentTimeMillis();
				timeToSleep -= curTime - startTime;
				startTime = curTime;
			}
		}
	}

	/**
	 * 获得此前设置的最大FPS
	 * 
	 * @return 最大FPS
	 */
	public int getMaxFPS() {
		return maxFPS;
	}

	/**
	 * 设置最大FPS
	 * 
	 * @param maxFPS
	 *            要设置的最大FPS
	 */
	public void setMaxFPS(int maxFPS) {
		this.maxFPS = maxFPS;
		offsetTime = (int) Math.ceil(1000.0 / maxFPS);
	}

	/**
	 * 主线程是否正在运行
	 * 
	 * @return 是否正在运行
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * 加入要绘制的纹理
	 * 
	 * @param gs
	 *            要加入的纹理
	 */
	public void addSpirit(GameSpirit gs) {
		spirits.add(gs);
	}

	/**
	 * 去除纹理List中的纹理
	 * 
	 * @param gs
	 *            要去除的纹理
	 */
	public void removeSpirit(GameSpirit gs) {
		spirits.remove(gs);
	}

	/**
	 * 获得纹理数量
	 * 
	 * @return 纹理数量
	 */
	public int getSpiritCount() {
		return spirits.size();
	}

	/**
	 * 获得双缓冲所用Graphics
	 * 
	 * @return 双缓冲所用Graphics
	 */
	public Graphics2D getBgGraphics() {
		return bgGraphics;
	}
}
