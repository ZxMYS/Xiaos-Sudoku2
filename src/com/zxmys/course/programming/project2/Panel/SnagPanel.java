package com.zxmys.course.programming.project2.Panel;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

/**
 * ��ͼJPanel�࣬����һ��JPanel��ͼ����ʾ��
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.15)
 */
public class SnagPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * ˫������ʹ�õ�image
	 */
	protected BufferedImage i = null;

	/**
	 * ��ʼ����c�Ľ�ͼΪ�����SnagPanel
	 * @param c Ҫ��ͼ��Container
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
