/**
 * 
 */
package com.zxmys.course.programming.common.game;

import java.io.*;

/**
 * 游戏文件IO类
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public class GameFileIO {

	/**
	 * 不可实例化
	 */
	private GameFileIO() {

	}

	/**
	 * 获得对file的BufferedInputStream
	 * 
	 * @param file
	 *            要读取的file
	 * @return BufferedInputStream
	 * @throws FileNotFoundException
	 *             当文件无法找到时抛出
	 */
	public static BufferedInputStream getInputStream(File file)
			throws FileNotFoundException {
		return new BufferedInputStream(new FileInputStream(file));
	}

	/**
	 * 获得对file的BufferedOutputStream
	 * 
	 * @param file
	 *            要写入的file
	 * @return BufferedOutputStream
	 * @throws FileNotFoundException
	 *             当文件无法找到时抛出
	 */
	public static BufferedOutputStream getOutputStream(File file)
			throws FileNotFoundException {
		return new BufferedOutputStream(new FileOutputStream(file));
	}

}
