package com.zxmys.course.programming.project1;

/**
 * ����������������
 * 
 * @author Zx.MYS
 * @version 1.1 (2009.1.14)
 */
public class Common {

	/**
	 * ����ʵ����
	 */
	private Common() {

	}

	/**
	 * ����������
	 */
	public static class Variable {

		/**
		 * ����ʵ����
		 */
		private Variable() {

		}

		/**
		 * �Ƿ��ý�խ�Ľ�����ʾ����������Ч��
		 */
		private static boolean narrowScreen = false;

		/**
		 * �Ƿ�ΪGUI����
		 */
		private static boolean GUI = true;

		/**
		 * �Ƿ�Ϊ�ϸ������ж�ģʽ
		 */
		private static boolean StrictValidBoardMode = false;

		/**
		 * ��Ϸ��ʼʱ��
		 */
		private static long gameStartTime = 0;

		/**
		 * �����Ϸ����ʱ��
		 * 
		 * @return ʱ�䣨�룩
		 */
		public static long getGameTime() {
			return (System.currentTimeMillis() - gameStartTime) / 1000;
		}

		/**
		 * �����Ϸ��ʼʱ��
		 * 
		 * @return ��Ϸ��ʼʱ����January 1, 1970 UTC���ĺ�����
		 */
		public static long getGameStartTime() {
			return gameStartTime;
		}

		/**
		 * ������Ϸ��ʼʱ��
		 * 
		 * @param gameStartTime
		 *            ��Ϸ��ʼʱ��
		 */
		public static void setGameStartTime(long gameStartTime) {
			Variable.gameStartTime = gameStartTime;
		}

		/**
		 * �Ƿ��ý�խ�Ľ�����ʾ����������Ч��
		 * 
		 * @return �Ƿ��ý�խ�Ľ�����ʾ����������Ч��
		 */
		public static boolean isNarrowScreen() {
			return narrowScreen;
		}

		/**
		 * �����Ƿ��ý�խ�Ľ�����ʾ����������Ч��
		 * 
		 * @param narrowScreen
		 *            �Ƿ��ý�խ�Ľ�����ʾ
		 */
		public static void setNarrowScreen(boolean narrowScreen) {
			Variable.narrowScreen = narrowScreen;
		}

		/**
		 * �Ƿ�ʹ��GUI
		 * 
		 * @return �Ƿ�ʹ��GUI
		 */
		public static boolean isGUI() {
			return GUI;
		}

		/**
		 * �����Ƿ�ʹ��GUI
		 * 
		 * @param GUI
		 *            �����Ƿ�ʹ��GUI
		 */
		public static void setGUI(boolean GUI) {
			Variable.GUI = GUI;
		}

		/**
		 * �Ƿ�Ϊ�ϸ������ж�ģʽ
		 * 
		 * @return �Ƿ�Ϊ�ϸ������ж�ģʽ
		 */
		public static boolean isStrictValidBoardMode() {
			return StrictValidBoardMode;
		}

		/**
		 * �����Ƿ�Ϊ�ϸ������ж�ģʽ
		 * 
		 * @param StrictValidBoardMode
		 *            �Ƿ�Ϊ�ϸ������ж�ģʽ
		 */
		public static void setStrictValidBoardMode(boolean StrictValidBoardMode) {
			Variable.StrictValidBoardMode = StrictValidBoardMode;
		}

	}

	/**
	 * ��������
	 */
	public static class Function {

		/**
		 * ����ʵ����
		 */
		private Function() {

		}

		/**
		 * ������������������ַ������ַ���1����
		 * 
		 * @param str1
		 *            �ַ���1
		 * @param str2
		 *            �ַ���2
		 * @throws IllegalArgumentException
		 *             ����ַ���1������С���ַ���2������
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
		 * ��������
		 * 
		 * @param arr
		 *            Ŀ������
		 * @param key
		 *            Ҫ���ҵ�ֵ
		 * @return ����ҵ�key����key��һ�γ��ֵ����������򷵻�-1
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
		 * �ж�һ�������Ƿ�ɽ�
		 * 
		 * @param sb
		 *            Ҫ�������
		 * @return �Ƿ�ɽ�
		 */
		public static boolean isSolvable(SudokuBoard sb) {
			SudokuSolver ss = new SudokuSolver();
			ss.setBoard(sb);
			return ss.solveBoard();
		}

	}

}
