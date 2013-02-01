package com.zxmys.course.programming.project1;

import javax.swing.JOptionPane;
import java.util.LinkedList;

/**
 * GUI����������
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.11.25)
 */
public class GUIMain {

	/**
	 * �Ƿ�����Ч����
	 */
	private static boolean isValidBoard = true;

	/**
	 * ��������
	 */
	private static GUISudokuBoard board = new GUISudokuBoard();

	/**
	 * ���ڴ洢����
	 */
	private static LinkedList<Step> steps = new LinkedList<Step>();

	/**
	 * ������Ϸ
	 */
	private static void loadGame() {

		String game = SudokuCreator.getStoredGame();
		for (int p = 0, i = 1; i <= 9; i++)
			for (int j = 1; j <= 9; j++) {
				board.setVal(i, j, game.charAt(p++) - '0');
			}
		board.setOriginalBoard((SudokuBoard) board);

	}

	/**
	 * ��¼�²���
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 * @param newV
	 *            ���������
	 */
	private static void recordStep(int x, int y, int newV) {

		Step step = new Step(x, y, board.getVal(x, y), newV);
		steps.add(step);

	}

	/**
	 * �������µĲ���
	 * 
	 * @return �����Ƿ�ɹ�
	 */
	private static boolean undoStep() {

		if (steps.size() == 0) {
			JOptionPane.showMessageDialog(null, "û�п��Գ����Ĳ��裡");
			return false;
		}

		Step step = steps.getLast();
		board.setVal(step.x, step.y, step.v);
		steps.removeLast();
		JOptionPane.showMessageDialog(null, "�Ѿ��������裺" + step);

		return true;

	}

	/**
	 * ��GUI��ʾ�����Ľ��
	 * 
	 * @param board
	 *            Ҫ��������
	 */
	private static void showAnswer(SudokuBoard board) {

		GUISudokuBoard tmp = new GUISudokuBoard(board);
		tmp.setOriginalBoard(tmp);
		SudokuSolver ss = new SudokuSolver();
		ss.setBoard(tmp);
		ss.solveBoard();
		JOptionPane.showMessageDialog(null, tmp + "\n������ʾ����ȷ���˳�����Ϸ");

	}

	/**
	 * ����Ƿ�������
	 * 
	 * @return �Ƿ�������
	 */
	private static boolean checkBoard() {

		if (Common.Variable.isStrictValidBoardMode()) {

			// ʹ�ý��������Խ⵱ǰ����
			SudokuSolver ss = new SudokuSolver();
			ss.setBoard(new SudokuBoard(board));
			if (!ss.solveBoard()) {
				// �޽�
				JOptionPane.showMessageDialog(null,
						"�����������֣�������back������һ����d(x,y)ɾ��һ������\n");
				return false;
			}

		} else {

			if (!board.isValid()) {
				JOptionPane.showMessageDialog(null,
						"�����������֣�������back������һ����d(x,y)ɾ��һ������\n");
				return false;
			} else {
				return true;
			}

		}

		return true;

	}

	/**
	 * ��������
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 * @param v
	 *            ���������
	 * @return �����Ƿ�ɹ�
	 */
	private static boolean setVal(int x, int y, int v) {
		try {
			if (v != 0 && board.getVal(x, y) != 0) {
				JOptionPane.showMessageDialog(null, "��" + x + "�У���" + y
						+ "���������֣����������롣\n");
				return false;
			}

			int[] isBlockValid = board.isValid(x, y, v);
			if (isBlockValid[0] != 0) {
				JOptionPane.showMessageDialog(null, "�������������е�"
						+ isBlockValid[0] + "�У���" + isBlockValid[1]
						+ "�е������г�ͻ�����������롣\n");
				return false;
			}

			recordStep(x, y, v);
			board.setVal(x, y, v);

		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, "����������ݳ�����Χ�����������룡");
			return false;
		}

		return true;
	}

	/**
	 * GUI����������
	 * 
	 * @param args
	 *            ��������
	 */
	public static void main(String[] args) {

		loadGame();
		String strIn;

		// ѭ��ֱ�������˳��������ѽ�
		while (!board.isSolved()) {

			// ��ʾ����
			if ((strIn = JOptionPane.showInputDialog(null,
					"�����б� \n(x,y)v:�ڵ�x�е�y������v������������£�\n"
							+ "d(x,y)ɾ����x�е�y�е�����\n(1<=x,y,v<=9)\n"
							+ "giveup:��ʾ��\nexit��ȡ��:�˳� "
							+ "back��undo:����\nstrict:��/�ر��ϸ������ж�ģʽ\n����������\n"
							+ board, "Zx.MYS��������Ϸ",
					JOptionPane.QUESTION_MESSAGE)) == null) {

				if (JOptionPane.showConfirmDialog(null, "�����˳���Ϸ��?", "�˳���Ϸ��",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				continue;

			}

			strIn = strIn.toLowerCase();

			// ���´����û�����

			if (strIn.equals("exit")) {

				System.exit(0);

			} else if (strIn.equals("giveup")) {

				if (JOptionPane.showConfirmDialog(null, "��ȷ��Ҫ������", "����",
						JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
					showAnswer(board.getOriginalBoard());
					System.exit(0);
				} else {
					JOptionPane.showMessageDialog(null, "��ѡ�񲻿���");
				}

			} else if (strIn.equals("back") || strIn.equals("undo")) {

				undoStep();

				isValidBoard = checkBoard();

			} else if (strIn.equals("strict")) {

				Common.Variable.setStrictValidBoardMode(!Common.Variable
						.isStrictValidBoardMode());
				JOptionPane.showMessageDialog(null, "�ϸ������ж�ģʽ��"
						+ (Common.Variable.isStrictValidBoardMode() ? "��"
								: "�ر�"));
				isValidBoard = checkBoard();

			} else if (strIn.length() == 6 && strIn.charAt(0) == 'd'
					&& strIn.charAt(1) == '(' && strIn.charAt(3) == ','
					&& strIn.charAt(5) == ')') {

				int x = strIn.charAt(2) - '0';
				int y = strIn.charAt(4) - '0';
				if (board.getOriginalBoard().getVal(x, y) != 0) {
					JOptionPane.showMessageDialog(null, "��" + x + "�У���" + y
							+ "���ϵ���������Ŀԭ�е�����");
					continue;
				}
				if (board.getVal(x, y) == 0) {
					JOptionPane.showMessageDialog(null, "��" + x + "�У���" + y
							+ "����û������");
					continue;
				}
				if (setVal(x, y, 0))
					JOptionPane.showMessageDialog(null, "ɾ���ɹ�");

				isValidBoard = checkBoard();

			} else if (strIn.length() == 6 && strIn.charAt(0) == '('
					&& strIn.charAt(2) == ',' && strIn.charAt(4) == ')') {
				if (isValidBoard) {
					int x = strIn.charAt(1) - '0';
					int y = strIn.charAt(3) - '0';
					int v = strIn.charAt(5) - '0';
					if (v == 0) {
						JOptionPane
								.showMessageDialog(null, "����������ݳ�����Χ�����������룡");
						continue;
					}
					if (setVal(x, y, v))
						JOptionPane.showMessageDialog(null, "��������ȷ����");

					isValidBoard = checkBoard();

				} else {
					JOptionPane.showMessageDialog(null, "Ŀǰ������״̬���㲻�ܼ�����д���֣�"
							+ "�볷�������ɾ���������֡�");
				}
			} else {

				JOptionPane.showMessageDialog(null, "������ĸ�ʽ����ȷ�����������룡");

			}

		}

		// ��Ϸʤ��
		JOptionPane.showMessageDialog(null, board + "\n��ϲ����Ӯ���˱��ֵ�ʤ����\n"
				+ "�㹲��ʱ" + Common.Variable.getGameTime() + "��\n" + "�밴ȷ���˳�����Ϸ",
				"ʤ��", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

}
