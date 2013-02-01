/**
 * 
 */
package com.zxmys.course.programming.project1;

/**
 * 操作记录类
 * 
 * @author Zx.MYS
 * @version 1.1 (2009.11.25)
 */
public class Step {

	/**
	 * x坐标
	 */
	public int x;

	/**
	 * y坐标
	 */
	public int y;

	/**
	 * 目标格原有数字
	 */
	public int v;

	/**
	 * 目标格要填入的数字
	 */
	public int newV;

	/**
	 * 初始化操作记录
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param v
	 *            目标格原有数字
	 * @deprecated 从1.1版开始由{@link #Step(int, int, int, int)}替代
	 */
	public Step(int x, int y, int v) {
		this.x = x;
		this.y = y;
		this.v = v;
	}

	/**
	 * 初始化操作记录
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param v
	 *            目标格原有数字
	 * @param newV
	 *            目标格要填入的数字
	 */
	public Step(int x, int y, int v, int newV) {
		this(x, y, v);
		this.newV = newV;
	}

	/**
	 * {@inheritDoc} 此方法覆盖{@link java.lang.Object#toString()}
	 * ，获得表示此操作的字符串
	 * 
	 * @see java.lang.Object#toString()
	 * @return 操作命令
	 */
	@Override
	public String toString() {
		if(newV == 0)
			return "d(" + x + "," + y + ")";
		else
			return "(" + x + "," + y + ")"+newV;
	}

}
