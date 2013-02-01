/**
 * 
 */
package com.zxmys.course.programming.project2.Panel;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

/**
 * ģ����ͼJPanel�࣬����һ��Container��ͼ������ģ������ʾ��
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public class BlurredPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * ˫������ʹ�õ�image
	 */
	protected BufferedImage i = null;

	/**
	 * ��ʼ����c��ģ����Ľ�ͼΪ�����BlurredPanel
	 * 
	 * @param c
	 *            Ҫ��ͼ��Container
	 */
	public BlurredPanel(Container c) {
		if (c.getWidth() != 0)
			setPreferredSize(c.getSize());
		else
			setPreferredSize(c.getPreferredSize());
		i = new BufferedImage(getPreferredSize().width,
				getPreferredSize().height, BufferedImage.TYPE_INT_ARGB);

		// ģ������3x3��þ���
		float[] BLUR3x3 = { 0.1f, 0.1f, 0.1f, 0.1f, 0.2f, 0.1f, 0.1f, 0.1f,
				0.1f };

		// ʹ��CopvolopOp����ģ������
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
