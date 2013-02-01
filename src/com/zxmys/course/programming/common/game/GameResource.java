package com.zxmys.course.programming.common.game;

import java.net.*;


/**
 * ��Ϸ��Դ��ȡ��
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public class GameResource {
	/**
	 * �����Ϸ��Դ
	 * @param path ��Դ·��
	 * @return ָ����Դ��URL
	 * @throws ResourceNotFoundException ��Դδ�ҵ�
	 */
	public static URL getResource(String path) throws ResourceNotFoundException{
		URL ret=ClassLoader.getSystemClassLoader().getResource(path);
		if(ret==null)
			throw new ResourceNotFoundException();
		return ret;
	}
}
