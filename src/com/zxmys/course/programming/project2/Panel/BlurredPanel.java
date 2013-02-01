/**
 * 
 */
package com.zxmys.course.programming.project2.Panel;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

/**
 * 模糊截图JPanel类，对另一个Container截图并进行模糊后显示。
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public class BlurredPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * 双缓冲所使用的image
	 */
	protected BufferedImage i = null;

	/**
	 * 初始化以c的模糊后的截图为画面的BlurredPanel
	 * 
	 * @param c
	 *            要截图的Container
	 */
	public BlurredPanel(Container c) {
		if (c.getWidth() != 0)
			setPreferredSize(c.getSize());
		else
			setPreferredSize(c.getPreferredSize());
		i = new BufferedImage(getPreferredSize().width,
				getPreferredSize().height, BufferedImage.TYPE_INT_ARGB);

		// 模糊所用3x3变幻矩阵
		float[] BLUR3x3 = { 0.1f, 0.1f, 0.1f, 0.1f, 0.2f, 0.1f, 0.1f, 0.1f,
				0.1f };

		// 使用CopvolopOp进行模糊处理
		ConvolveOp cop = new ConvolveOp(new Kernel(3, 3, BLUR3x3),
				ConvolveOp.EDGE_NO_OP, null);

		c.paintComponents(i.getGraphics());
		((Graphics2D) i.getGraphics()).drawImage(i, cop, 0, 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(i, 0, 0, null);
	}

}
