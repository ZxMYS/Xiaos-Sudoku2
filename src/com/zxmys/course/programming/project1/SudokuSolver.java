/**
 * 
 */
package com.zxmys.course.programming.project1;

import java.util.Arrays;

/**
 * 解数独类<br/>使用Dancing Links算法解决数独问题。关于Dancing Links算法，请参阅《Dancing Links》, Donald
 * E.Knuth, Stanford University, 2008.9.13
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.10.21)
 */
public class SudokuSolver {

	/**
	 * 要求解的数独
	 */
	private SudokuBoard board = null;
	
	
	//以下为Dancing Links部分
	private class node {
		int r, c;

		node up, down, left, right;
	}

	private int[] mem = new int[729 + 9];

	private int[] cnt = new int[729 + 9];

	private node[] row = new node[729];

	private node[] col = new node[324];

	private node root = new node();

	private void link(int r, int c) {
		node tmp;
		cnt[c]++;
		tmp = new node();
		tmp.r = r;
		tmp.c = c;
		tmp.left = row[r];
		tmp.right = row[r].right;
		tmp.left.right = tmp;
		tmp.right.left = tmp;
		tmp.up = col[c];
		tmp.down = col[c].down;
		tmp.up.down = tmp;
		tmp.down.up = tmp;
	}

	private void remove(int c) {
		node t, tt;
		col[c].right.left = col[c].left;
		col[c].left.right = col[c].right;
		for (t = col[c].down; t != col[c]; t = t.down) {
			for (tt = t.right; tt != t; tt = tt.right) {
				cnt[tt.c]--;
				tt.up.down = tt.down;
				tt.down.up = tt.up;
			}
			t.left.right = t.right;
			t.right.left = t.left;
		}
	}

	private void resume(int c) {
		node t, tt;
		for (t = col[c].down; t != col[c]; t = t.down) {
			t.right.left = t;
			t.left.right = t;
			for (tt = t.left; tt != t; tt = tt.left) {
				cnt[tt.c]++;
				tt.down.up = tt;
				tt.up.down = tt;
			}
		}
		col[c].left.right = col[c];
		col[c].right.left = col[c];
	}
	
	/**
	 * Dancing Links主搜索程序
	 * @return 有解返回True
	 */
	private boolean solve(int k) {
		if (root.right == root)
			return true;
		int min = 1 << 29;
		node t, tt;
		int tc = 0;
		for (t = root.right; t != root; t = t.right) {
			if (cnt[t.c] < min) {
				min = cnt[t.c];
				tc = t.c;
				if (min <= 1)
					break;
			}
		}
		remove(tc);
		for (t = col[tc].down; t != col[tc]; t = t.down) {
			mem[k] = t.r;
			t.left.right = t;
			for (tt = t.right; tt != t; tt = tt.right) {
				remove(tt.c);
			}
			t.left.right = t.right;
			if (solve(k + 1))
				return true;
			t.right.left = t;
			for (tt = t.left; tt != t; tt = tt.left) {
				resume(tt.c);
			}
			t.right.left = t.left;
		}
		resume(tc);
		return false;
	}

	public void setBoard(SudokuBoard board) {
		this.board = board;
	}

	public SudokuBoard getBoard() {
		return board;
	}

	/**
	 * 解数独
	 * @return 有解返回True
	 */
	public boolean solveBoard() {
		if (board == null)
			return false;
		Arrays.fill(cnt, 0);
		root.left = root;
		root.right = root;
		root.up = root;
		root.down = root;
		root.r = 729;
		root.c = 324;
		for (int i = 0; i < 324; i++) {
			col[i] = new node();
			col[i].c = i;
			col[i].r = 729;
			col[i].up = col[i];
			col[i].down = col[i];
			col[i].left = root;
			col[i].right = root.right;
			col[i].left.right = col[i];
			col[i].right.left = col[i];
		}
		for (int i = 0; i < 729; i++) {
			row[i] = new node();
			row[i].r = i;
			row[i].c = 324;
			row[i].left = row[i];
			row[i].right = row[i];
			row[i].up = root;
			row[i].down = root.down;
			row[i].up.down = row[i];
			row[i].down.up = row[i];
		}
		for (int i = 0; i < 729; i++) {
			int r = i / 9 / 9 % 9;
			int c = i / 9 % 9;
			int val = i % 9 + 1;
			//读取数独信息
			if (board.getVal(r + 1, c + 1) == 0
					|| board.getVal(r + 1, c + 1) == val) {
				link(i, r * 9 + val - 1);
				link(i, 81 + c * 9 + val - 1);
				int tr = r / 3;
				int tc = c / 3;
				link(i, 162 + (tr * 3 + tc) * 9 + val - 1);
				link(i, 243 + r * 9 + c);
			}
		}
		for (int i = 0; i < 729; i++) {
			row[i].left.right = row[i].right;
			row[i].right.left = row[i].left;
		}
		//求解
		boolean ret = solve(1);
		if (ret)
			for (int i = 1; i <= 81; i++) {
				int t = mem[i] / 9 % 81;
				int val = mem[i] % 9 + 1;
				//将解更新至求解的数独中
				board.setVal(t / 9 + 1, t % 9 + 1, val);
			}

		return ret;
	}

}
