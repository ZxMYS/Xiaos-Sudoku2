/**
 * 
 */
package com.zxmys.course.programming.project1;

/**
 * ������¼��
 * 
 * @author Zx.MYS
 * @version 1.1 (2009.11.25)
 */
public class Step {

	/**
	 * x����
	 */
	public int x;

	/**
	 * y����
	 */
	public int y;

	/**
	 * Ŀ���ԭ������
	 */
	public int v;

	/**
	 * Ŀ���Ҫ���������
	 */
	public int newV;

	/**
	 * ��ʼ��������¼
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 * @param v
	 *            Ŀ���ԭ������
	 * @deprecated ��1.1�濪ʼ��{@link #Step(int, int, int, int)}���
	 */
	public Step(int x, int y, int v) {
		this.x = x;
		this.y = y;
		this.v = v;
	}

	/**
	 * ��ʼ��������¼
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 * @param v
	 *            Ŀ���ԭ������
	 * @param newV
	 *            Ŀ���Ҫ���������
	 */
	public Step(int x, int y, int v, int newV) {
		this(x, y, v);
		this.newV = newV;
	}

	/**
	 * {@inheritDoc} �˷�������{@link java.lang.Object#toString()}
	 * ����ñ�ʾ�˲������ַ���
	 * 
	 * @see java.lang.Object#toString()
	 * @return ��������
	 */
	@Override
	public String toString() {
		if(newV == 0)
			return "d(" + x + "," + y + ")";
		else
			return "(" + x + "," + y + ")"+newV;
	}

}
