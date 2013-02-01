package com.zxmys.course.programming.common.game;

import java.net.*;


/**
 * 游戏资源读取类
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public class GameResource {
	/**
	 * 获得游戏资源
	 * @param path 资源路径
	 * @return 指向资源的URL
	 * @throws ResourceNotFoundException 资源未找到
	 */
	public static URL getResource(String path) throws ResourceNotFoundException{
		URL ret=ClassLoader.getSystemClassLoader().getResource(path);
		if(ret==null)
			throw new ResourceNotFoundException();
		return ret;
	}
}
