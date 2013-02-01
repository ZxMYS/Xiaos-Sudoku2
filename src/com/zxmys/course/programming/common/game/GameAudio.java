package com.zxmys.course.programming.common.game;

import java.applet.*;
import java.net.*;

/**
 * ��Ϸ������
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public class GameAudio {
	/**
	 * �����ļ�������
	 */
	private AudioClip audioClip;

	/**
	 * �����ͬʱ���������ļ�
	 * 
	 * @param audioURL
	 *            Ҫ����������ļ���URL
	 */
	public GameAudio(URL audioURL) {
		loadAudioClip(audioURL);
	}

	/**
	 * ��õ�ǰ�����ļ�������
	 * 
	 * @return �����ļ�������
	 */
	public AudioClip getAudioClip() {
		return audioClip;
	}

	/**
	 * ���������ļ�
	 * 
	 * @param audioClip
	 *            Ҫ���õ������ļ�
	 */
	public void setAudioClip(AudioClip audioClip) {
		this.audioClip = audioClip;
	}

	/**
	 * ���������ļ�
	 * 
	 * @param audioURL
	 *            Ҫ����������ļ���URL
	 */
	public void loadAudioClip(URL audioURL) {
		this.audioClip = Applet.newAudioClip(audioURL);
	}

	/**
	 * ��ѭ����������
	 */
	public void play() {
		if (audioClip != null)
			audioClip.play();
	}

	/**
	 * ѭ����������
	 */
	public void loop() {
		if (audioClip != null)
			audioClip.loop();
	}

	/**
	 * ֹͣ��������
	 */
	public void stop() {
		if (audioClip != null)
			audioClip.stop();
	}
}
