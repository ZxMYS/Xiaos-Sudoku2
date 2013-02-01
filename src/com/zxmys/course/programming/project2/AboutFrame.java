package com.zxmys.course.programming.project2;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * �����ڡ�����
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.15)
 */
// ��һ�汾�н�תΪ�̳�JDialog
public class AboutFrame extends JFrame {
	/**
	 * ���ڴ���ַ���ļ���Listener
	 */
	MouseAdapter openURL = new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent e) {
			String URL = ((JLabel) e.getSource()).getName();
			if (java.awt.Desktop.isDesktopSupported()) {
				try {
					java.net.URI uri = java.net.URI.create(URL);
					java.awt.Desktop dp = java.awt.Desktop.getDesktop();
					if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
						dp.browse(uri);
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(AboutFrame.this, "��ʧ�ܣ������з���"
							+ URL);
				}
			}
		}
	};

	/**
	 * �����ڵ�����
	 */
	private JFrame frame;

	/**
	 * ���첢��ʾ���ڴ��ڣ�ͬʱ���ظ�����
	 * @param frame �����ڵ�����
	 */
	public AboutFrame(JFrame frame) {
		this.frame = frame;
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				AboutFrame.this.frame.setVisible(true);
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setSize(300, 300);
		setTitle("About " + MainFrame.title);
		setVisible(true);
		frame.setVisible(false);
		JPanel centerPane = new JPanel();
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
		centerPane.add(Box.createVerticalGlue());

		JLabel jbl;
		jbl = new JLabel("Zx.MYS's Sudoku.2 By Zx.MYS");
		jbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPane.add(jbl);

		jbl = new JLabel("http://ZxMYS.COM");
		jbl.setName("http://ZxMYS.COM");
		jbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		jbl.setForeground(Color.BLUE);
		jbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jbl.addMouseListener(openURL);
		centerPane.add(jbl);

		centerPane.add(Box.createVerticalGlue());

		jbl = new JLabel("Music By TAM Music Factory");
		jbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPane.add(jbl);

		jbl = new JLabel("http://www.tam-music.com");
		jbl.setName("http://www.tam-music.com");
		jbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		jbl.setForeground(Color.BLUE);
		jbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jbl.addMouseListener(openURL);
		centerPane.add(jbl);

		jbl = new JLabel("��ȨЭ��");
		jbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		jbl.setName("http://www.tam-music.com/copyrigh.html");
		jbl.setForeground(Color.BLUE);
		jbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jbl.addMouseListener(openURL);
		centerPane.add(jbl);

		centerPane.add(Box.createVerticalGlue());

		add(centerPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		JButton btnExit = new JButton("OK");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutFrame.this.setVisible(false);
				AboutFrame.this.frame.setVisible(true);
				AboutFrame.this.dispose();
			}
		});
		centerPane.add(btnExit);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	private static final long serialVersionUID = 1L;
}
