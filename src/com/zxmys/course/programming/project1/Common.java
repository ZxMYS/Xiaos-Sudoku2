package com.zxmys.course.programming.project1;

/**
 * 公共参数及函数类
 * 
 * @author Zx.MYS
 * @version 1.1 (2009.1.14)
 */
public class Common {

	/**
	 * 不可实例化
	 */
	private Common() {

	}

	/**
	 * 公共参数类
	 */
	public static class Variable {

		/**
		 * 不可实例化
		 */
		private Variable() {

		}

		/**
		 * 是否用较窄的界面显示（命令行有效）
		 */
		private static boolean narrowScreen = false;

		/**
		 * 是否为GUI界面
		 */
		private static boolean GUI = true;

		/**
		 * 是否为严格死局判断模式
		 */
		private static boolean StrictValidBoardMode = false;

		/**
		 * 游戏开始时间
		 */
		private static long gameStartTime = 0;

		/**
		 * 获得游戏已用时间
		 * 
		 * @return 时间（秒）
		 */
		public static long getGameTime() {
			return (System.currentTimeMillis() - gameStartTime) / 1000;
		}

		/**
		 * 获得游戏开始时间
		 * 
		 * @return 游戏开始时间与January 1, 1970 UTC相差的毫秒数
		 */
		public static long getGameStartTime() {
			return gameStartTime;
		}

		/**
		 * 设置游戏开始时间
		 * 
		 * @param gameStartTime
		 *            游戏开始时间
		 */
		public static void setGameStartTime(long gameStartTime) {
			Variable.gameStartTime = gameStartTime;
		}

		/**
		 * 是否用较窄的界面显示（命令行有效）
		 * 
		 * @return 是否用较窄的界面显示（命令行有效）
		 */
		public static boolean isNarrowScreen() {
			return narrowScreen;
		}

		/**
		 * 设置是否用较窄的界面显示（命令行有效）
		 * 
		 * @param narrowScreen
		 *            是否用较窄的界面显示
		 */
		public static void setNarrowScreen(boolean narrowScreen) {
			Variable.narrowScreen = narrowScreen;
		}

		/**
		 * 是否使用GUI
		 * 
		 * @return 是否使用GUI
		 */
		public static boolean isGUI() {
			return GUI;
		}

		/**
		 * 设置是否使用GUI
		 * 
		 * @param GUI
		 *            设置是否使用GUI
		 */
		public static void setGUI(boolean GUI) {
			Variable.GUI = GUI;
		}

		/**
		 * 是否为严格死局判断模式
		 * 
		 * @return 是否为严格死局判断模式
		 */
		public static boolean isStrictValidBoardMode() {
			return StrictValidBoardMode;
		}

		/**
		 * 设置是否为严格死局判断模式
		 * 
		 * @param StrictValidBoardMode
		 *            是否为严格死局判断模式
		 */
		public static void setStrictValidBoardMode(boolean StrictValidBoardMode) {
			Variable.StrictValidBoardMode = StrictValidBoardMode;
		}

	}

	/**
	 * 公共函数
	 */
	public static class Function {

		/**
		 * 不可实例化
		 */
		private Function() {

		}

		/**
		 * 用两列输出两个多行字符串，字符串1在左。
		 * 
		 * @param str1
		 *            字符串1
		 * @param str2
		 *            字符串2
		 * @throws IllegalArgumentException
		 *             如果字符串1的行数小于字符串2的行数
		 */
		public static void printParallel(String str1, String str2)
				throws IllegalArgumentException {
			String[] lines1 = str1.split("\n");
			String[] lines2 = str2.split("\n");

			if (lines1.length < lines2.length)
				throw new IllegalArgumentException();

			for (int i = 0; i < lines1.length; i++) {
				System.out.print(lines1[i]);
				if (i < lines2.length) {
					System.out.print('\t');
					System.out.print(lines2[i]);
				}
				System.out.println();
			}
		}

		/**
		 * 线性搜索
		 * 
		 * @param arr
		 *            目标数组
		 * @param key
		 *            要查找的值
		 * @return 如果找到key返回key第一次出现的索引，否则返回-1
		 */
		public static <T> int linearSearch(T[] arr, T key) {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i].equals(key)) {
					return i;
				}
			}
			return -1;
		}

		/**
		 * 判断一个数独是否可解
		 * 
		 * @param sb
		 *            要解的数独
		 * @return 是否可解
		 */
		public static boolean isSolvable(SudokuBoard sb) {
			SudokuSolver ss = new SudokuSolver();
			ss.setBoard(sb);
			return ss.solveBoard();
		}

	}

}
