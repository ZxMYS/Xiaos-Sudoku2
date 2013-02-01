package com.zxmys.course.programming.project2.Panel;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

/**
 * 截图JPanel类，对另一个JPanel截图并显示。
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.15)
 */
public class SnagPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * 双缓冲所使用的image
	 */
	protected BufferedImage i = null;

	/**
	 * 初始化以c的截图为画面的SnagPanel
	 * @param c 要截图的Container
	 */
	public SnagPanel(Container c) {
		if (c.getWidth() != 0)
			setPreferredSize(c.getSize());
		else
			setPreferredSize(c.getPreferredSize());
		i = new BufferedImage(getPreferredSize().width,
				getPreferredSize().height, BufferedImage.TYPE_INT_ARGB);
		c.paintComponents(i.getGraphics());
		((Graphics2D) i.getGraphics()).drawImage(i, 0, 0, null);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(i, 0, 0, null);
	}

}
