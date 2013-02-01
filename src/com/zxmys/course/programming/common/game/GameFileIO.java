/**
 * 
 */
package com.zxmys.course.programming.common.game;

import java.io.*;

/**
 * ��Ϸ�ļ�IO��
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public class GameFileIO {

	/**
	 * ����ʵ����
	 */
	private GameFileIO() {

	}

	/**
	 * ��ö�file��BufferedInputStream
	 * 
	 * @param file
	 *            Ҫ��ȡ��file
	 * @return BufferedInputStream
	 * @throws FileNotFoundException
	 *             ���ļ��޷��ҵ�ʱ�׳�
	 */
	public static BufferedInputStream getInputStream(File file)
			throws FileNotFoundException {
		return new BufferedInputStream(new FileInputStream(file));
	}

	/**
	 * ��ö�file��BufferedOutputStream
	 * 
	 * @param file
	 *            Ҫд���file
	 * @return BufferedOutputStream
	 * @throws FileNotFoundException
	 *             ���ļ��޷��ҵ�ʱ�׳�
	 */
	public static BufferedOutputStream getOutputStream(File file)
			throws FileNotFoundException {
		return new BufferedOutputStream(new FileOutputStream(file));
	}

}
