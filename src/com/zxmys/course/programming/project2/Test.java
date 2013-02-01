package com.zxmys.course.programming.project2;

import javax.swing.*;

/**
 * 用于测试com.zxmys.course.programming.project2包及其子包。
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public class Test extends JFrame {

	private static final long serialVersionUID = 1L;

	public Test() {

	}

	public static void main(String[] args) {
		new AboutFrame(new MainFrame());
	}
}
