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
 * ��Ϸ��ͼ��
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public abstract class GameGraphicPanel extends JPanel implements Runnable {

	protected static final long serialVersionUID = 1L;

	/**
	 * ����FPSʱ�������ʱ��
	 */
	protected static final long MAX_INTERVAL = 1000L;

	/**
	 * ��Panel�������ӵ�����
	 */
	protected List<GameSpirit> spirits = Collections
			.synchronizedList(new LinkedList<GameSpirit>());

	/**
	 * ���FPS
	 */
	protected int maxFPS = 0;

	/**
	 * �Ƿ��������л�ͼ�߳�
	 */
	protected boolean running = false;

	/**
	 * �Ƿ���ʾFPS
	 */
	protected boolean showFPS = false;

	/**
	 * ˫�����ͼ��
	 */
	protected Image screen = null;

	/**
	 * ˫�����Graphics2D
	 */
	protected Graphics2D bgGraphics = null;

	/**
	 * ��ͼ�߳�
	 */
	protected Thread th;

	/**
	 * ����FPSʹ�����
	 */
	protected transient long interval, offsetTime, curFPS, tickStartTime,
			frameCount;

	/**
	 * ���캯��������Ĭ��FPSΪ60��ͬʱ����ϵͳ������repaint��Ϣ
	 */
	public GameGraphicPanel() {
		setIgnoreRepaint(true);
		setMaxFPS(60);
	}

	/**
	 * ��ʹ��ϵͳ˫����
	 * 
	 * @see javax.swing.JComponent#isDoubleBuffered()
	 */
	public boolean isDoubleBuffered() {
		return false;
	}

	/**
	 * �˳���������Դ
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
	 * ����FPS
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
	 * ����ͼ��
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
	 * ������ͬһʹ��OverlayLayout�ĸ������еĿؼ��� ���������������OverlayLayout����ֱ�ӷ���
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
	 * ��ͼ
	 * 
	 * @param g
	 *            ��ʹ�õ�Graphics
	 */
	public abstract void draw(Graphics g);

	/**
	 * ����������ʱ����ListӦ���Ѿ������߳�����
	 * 
	 * @param sp
	 *            ����List��ListIterator
	 * @param g
	 *            ��ʹ�õ�Graphics
	 */
	public abstract void drawSpirit(ListIterator<GameSpirit> sp, Graphics g);

	/**
	 * ��ʼ��˫����
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
	 * ��ʼ��ͼ�߳�
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
	 * ֹͣ��ͼ�߳�
	 */
	public void stop() {
		running = false;
	}

	/**
	 * �ñ���ɫ���˫�����е�ͼ��
	 * 
	 * @param bgColor
	 *            ����ɫ
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
	 * �ú�ɫ���˫�����е�ͼ��
	 */
	public void clear() {
		clear(Color.BLACK);
	}

	/**
	 * �������߳�
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
	 * ��ô�ǰ���õ����FPS
	 * 
	 * @return ���FPS
	 */
	public int getMaxFPS() {
		return maxFPS;
	}

	/**
	 * �������FPS
	 * 
	 * @param maxFPS
	 *            Ҫ���õ����FPS
	 */
	public void setMaxFPS(int maxFPS) {
		this.maxFPS = maxFPS;
		offsetTime = (int) Math.ceil(1000.0 / maxFPS);
	}

	/**
	 * ���߳��Ƿ���������
	 * 
	 * @return �Ƿ���������
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * ����Ҫ���Ƶ�����
	 * 
	 * @param gs
	 *            Ҫ���������
	 */
	public void addSpirit(GameSpirit gs) {
		spirits.add(gs);
	}

	/**
	 * ȥ������List�е�����
	 * 
	 * @param gs
	 *            Ҫȥ��������
	 */
	public void removeSpirit(GameSpirit gs) {
		spirits.remove(gs);
	}

	/**
	 * �����������
	 * 
	 * @return ��������
	 */
	public int getSpiritCount() {
		return spirits.size();
	}

	/**
	 * ���˫��������Graphics
	 * 
	 * @return ˫��������Graphics
	 */
	public Graphics2D getBgGraphics() {
		return bgGraphics;
	}
}
