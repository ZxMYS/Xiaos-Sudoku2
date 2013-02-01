package com.zxmys.course.programming.common.game;

import java.applet.*;
import java.net.*;

/**
 * 游戏音乐类
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public class GameAudio {
	/**
	 * 音乐文件的引用
	 */
	private AudioClip audioClip;

	/**
	 * 构造的同时载入音乐文件
	 * 
	 * @param audioURL
	 *            要载入的音乐文件的URL
	 */
	public GameAudio(URL audioURL) {
		loadAudioClip(audioURL);
	}

	/**
	 * 获得当前音乐文件的引用
	 * 
	 * @return 音乐文件的引用
	 */
	public AudioClip getAudioClip() {
		return audioClip;
	}

	/**
	 * 设置音乐文件
	 * 
	 * @param audioClip
	 *            要设置的音乐文件
	 */
	public void setAudioClip(AudioClip audioClip) {
		this.audioClip = audioClip;
	}

	/**
	 * 载入音乐文件
	 * 
	 * @param audioURL
	 *            要载入的音乐文件的URL
	 */
	public void loadAudioClip(URL audioURL) {
		this.audioClip = Applet.newAudioClip(audioURL);
	}

	/**
	 * 不循环播放音乐
	 */
	public void play() {
		if (audioClip != null)
			audioClip.play();
	}

	/**
	 * 循环播放音乐
	 */
	public void loop() {
		if (audioClip != null)
			audioClip.loop();
	}

	/**
	 * 停止播放音乐
	 */
	public void stop() {
		if (audioClip != null)
			audioClip.stop();
	}
}
