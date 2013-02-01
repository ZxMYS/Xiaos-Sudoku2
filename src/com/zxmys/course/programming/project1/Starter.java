/**
 * 
 */
package com.zxmys.course.programming.project1;

import java.io.IOException;

/**
 * 启动引导程序
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.11.25)
 */
public class Starter {

	/**
	 * 引导程序，处理启动参数及初始化公共变量。
	 * 
	 * @param args
	 *            启动参数
	 * @throws IOException
	 *             当命令行界面IO出错时
	 */
	public static void main(String[] args) throws IOException {

		// 设置游戏开始时间
		Common.Variable.setGameStartTime(System.currentTimeMillis());

		// 检查启动参数是否有效
		String[] arguments = new String[] { "-?", "-h", "-help", "-ns",
				"-narrowscreen", "-strict", "-nogui" };
		for (String arg : args) {
			if (Common.Function.linearSearch(arguments, arg) < 0) {
				System.out.println("无效的启动参数，你可以使用-?来显示参数列表");
				System.exit(0);
			}
		}

		// 以下处理启动参数

		if (Common.Function.linearSearch(args, "-?") >= 0
				|| Common.Function.linearSearch(args, "-h") >= 0
				|| Common.Function.linearSearch(args, "-help") >= 0) {
			System.out.println("显示本启动参数列表：");
			System.out.println("-nogui：以命令行显示界面");
			System.out.println("-strict：开始时启用严格的死局判断模式");
			System.out.println("-ns/-narrowscreen：（在-nogui前提下）显示较窄的界面");
			System.out.println("-?/-h/-help：显示本启动参数列表");
			System.exit(0);
		}

		if (Common.Function.linearSearch(args, "-strict") >= 0) {
			Common.Variable.setStrictValidBoardMode(true);
		} else {
			Common.Variable.setStrictValidBoardMode(false);
		}

		if (Common.Function.linearSearch(args, "-ns") >= 0
				|| Common.Function.linearSearch(args, "-narrowscreen") >= 0) {
			Common.Variable.setNarrowScreen(true);
		} else {
			Common.Variable.setNarrowScreen(false);
		}

		if (Common.Function.linearSearch(args, "-nogui") >= 0) {
			// 启动命令行界面
			Common.Variable.setGUI(false);
			Main.main(args);
			System.exit(0);
		} else {
			// 启动GUI界面
			Common.Variable.setGUI(true);
			GUIMain.main(args);
			System.exit(0);
		}

	}

}
