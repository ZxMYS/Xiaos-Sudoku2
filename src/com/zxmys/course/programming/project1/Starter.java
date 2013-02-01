/**
 * 
 */
package com.zxmys.course.programming.project1;

import java.io.IOException;

/**
 * ������������
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.11.25)
 */
public class Starter {

	/**
	 * �������򣬴���������������ʼ������������
	 * 
	 * @param args
	 *            ��������
	 * @throws IOException
	 *             �������н���IO����ʱ
	 */
	public static void main(String[] args) throws IOException {

		// ������Ϸ��ʼʱ��
		Common.Variable.setGameStartTime(System.currentTimeMillis());

		// ������������Ƿ���Ч
		String[] arguments = new String[] { "-?", "-h", "-help", "-ns",
				"-narrowscreen", "-strict", "-nogui" };
		for (String arg : args) {
			if (Common.Function.linearSearch(arguments, arg) < 0) {
				System.out.println("��Ч�����������������ʹ��-?����ʾ�����б�");
				System.exit(0);
			}
		}

		// ���´�����������

		if (Common.Function.linearSearch(args, "-?") >= 0
				|| Common.Function.linearSearch(args, "-h") >= 0
				|| Common.Function.linearSearch(args, "-help") >= 0) {
			System.out.println("��ʾ�����������б�");
			System.out.println("-nogui������������ʾ����");
			System.out.println("-strict����ʼʱ�����ϸ�������ж�ģʽ");
			System.out.println("-ns/-narrowscreen������-noguiǰ���£���ʾ��խ�Ľ���");
			System.out.println("-?/-h/-help����ʾ�����������б�");
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
			// ���������н���
			Common.Variable.setGUI(false);
			Main.main(args);
			System.exit(0);
		} else {
			// ����GUI����
			Common.Variable.setGUI(true);
			GUIMain.main(args);
			System.exit(0);
		}

	}

}
