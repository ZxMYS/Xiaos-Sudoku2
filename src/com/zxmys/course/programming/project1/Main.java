/**
 * 
 */
package com.zxmys.course.programming.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.LinkedList;

import javax.swing.JOptionPane;

/**
 * �����н���������
 * 
 * @author Zx.MYS
 * @version 1.1 (2009.11.25)
 */

public class Main {

	/**
	 * �Ƿ�����Ч����
	 */
	private static boolean isValidBoard = true;

	/**
	 * ��������
	 */
	private static SudokuBoard board = new SudokuBoard();

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
			System.out.println("û�п��Գ����Ĳ��裡");
			return false;
		}

		Step step = (Step) steps.getLast();
		board.setVal(step.x, step.y, step.v);
		steps.removeLast();
		System.out.println("�Ѿ��������裺" + step);

		return true;

	}

	/**
	 * ���漰��ʾ
	 */
	private static void showHints() {

		String hints = "�����б� \n(x,y)v:�ڵ�x�е�y������v(�����������)\n"
				+ "d(x,y)ɾ����x�е�y�е�����\n(1<=x,y,v<=9)\n"
				+ "giveup:��ʾ��\nexit:�˳� "
				+ "back��undo:����\nstrict:��/�ر��ϸ������ж�ģʽ\n";
		if (!Common.Variable.isNarrowScreen())
			Common.Function.printParallel(board.toString(), hints);
		else
			System.out.println(hints + "\n" + board);
		System.out.println("����������\n");

	}

	/**
	 * ����������ʾ�����Ľ��
	 * 
	 * @param board
	 *            Ҫ��������
	 */
	private static void showAnswer(SudokuBoard board) {

		SudokuSolver ss = new SudokuSolver();
		ss.setBoard(board);
		ss.solveBoard();
		System.out.println(board);

	}

	/**
	 * ����Ƿ�������
	 * 
	 * @return �Ƿ�������
	 */
	private static boolean checkBoard() {

		if (Common.Variable.isStrictValidBoardMode()) {

			SudokuSolver ss = new SudokuSolver();
			ss.setBoard(new SudokuBoard(board));
			if (!ss.solveBoard()) {
				System.out.println("�����������֣�������back������һ����d(x,y)ɾ��һ������\n");
				return false;
			}

		} else {

			return board.isValid();

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
	 * @return �����ɹ�������������ַ���true�����򷵻�false
	 */
	private static boolean setVal(int x, int y, int v) {

		try {

			if (v != 0 && board.getVal(x, y) != 0) {
				System.out.println("��" + x + "�У���" + y + "���������֣����������롣\n");
				return false;
			}
			int[] isBlockValid = board.isValid(x, y, v);
			if (isBlockValid[0] != 0) {
				System.out.println("�������������е�" + isBlockValid[0] + "�У���"
						+ isBlockValid[1] + "�е������г�ͻ�����������롣\n");
				return false;
			}
			recordStep(x, y, v);
			board.setVal(x, y, v);

		} catch (IllegalArgumentException e) {
			System.out.println("����������ݳ�����Χ�����������룡");
			return false;
		}

		return true;

	}

	/**
	 * �����н���������
	 * 
	 * @param args
	 *            ��������
	 * @throws IOException
	 *             ��������IO����ʱ
	 */
	public static void main(String[] args) throws IOException {

		loadGame();
		SudokuBoard originalBoard = new SudokuBoard(board);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String strIn;

		// ѭ��ֱ�������˳��������ѽ�
		while (!board.isSolved()) {

			// ��ʾ����
			showHints();

			if ((strIn = in.readLine()) == null) {
				JOptionPane.showMessageDialog(null, "���벻��Ϊ�գ�����������");
				continue;
			}

			strIn = strIn.toLowerCase();

			// ���´����û�����

			if (strIn.equals("exit")) {

				System.exit(0);

			} else if (strIn.equals("giveup")) {

				System.out.println("��ȷ��Ҫ��������������Y��y����������Ϊ��");
				strIn = in.readLine();
				if (strIn != null && strIn.equalsIgnoreCase("Y")) {
					showAnswer(originalBoard);
					System.out.println("������ʾ���밴�س����˳�");
					strIn = in.readLine();
					System.exit(0);
				} else {
					System.out.println("��ѡ�񲻿���");
				}

			} else if (strIn.equals("back") || strIn.equals("undo")) {

				undoStep();
				isValidBoard = checkBoard();

			} else if (strIn.equals("strict")) {

				Common.Variable.setStrictValidBoardMode(!Common.Variable
						.isStrictValidBoardMode());
				System.out.println("�ϸ������ж�ģʽ��"
						+ (Common.Variable.isStrictValidBoardMode() ? "��"
								: "�ر�"));
				isValidBoard = checkBoard();

			} else if (strIn.length() == 6 && strIn.charAt(0) == 'd'
					&& strIn.charAt(1) == '(' && strIn.charAt(3) == ','
					&& strIn.charAt(5) == ')') {

				int x = strIn.charAt(2) - '0';
				int y = strIn.charAt(4) - '0';
				if (originalBoard.getVal(x, y) != 0) {
					System.out.println("��" + x + "�У���" + y + "���ϵ���������Ŀԭ�е�����");
					continue;
				}
				if (board.getVal(x, y) == 0) {
					System.out.println("��" + x + "�У���" + y + "����û������");
					continue;
				}

				if (setVal(x, y, 0))
					System.out.println("ɾ���ɹ�");
				isValidBoard = checkBoard();

			} else if (strIn.length() == 6 && strIn.charAt(0) == '('
					&& strIn.charAt(2) == ',' && strIn.charAt(4) == ')') {
				if (isValidBoard) {
					int x = strIn.charAt(1) - '0';
					int y = strIn.charAt(3) - '0';
					int v = strIn.charAt(5) - '0';
					if (v == 0) {
						System.out.println("����������ݳ�����Χ�����������룡");
						continue;
					}
					if (setVal(x, y, v))
						System.out.println("��������ȷ����");

					isValidBoard = checkBoard();

				} else {
					System.out.println("Ŀǰ������״̬���㲻�ܼ�����д���֣��볷�������ɾ���������֡�");
				}

			} else {

				System.out.println("������ĸ�ʽ����ȷ�����������룡");

			}

		}
		// ��Ϸʤ��
		System.out.println(board + "��ϲ����Ӯ���˱��ֵ�ʤ����\n" + "�㹲��ʱ"
				+ (Common.Variable.getGameTime()) + "��\n" + "�밴�س����˳�");
		strIn = in.readLine();
		System.exit(0);
	}

}
