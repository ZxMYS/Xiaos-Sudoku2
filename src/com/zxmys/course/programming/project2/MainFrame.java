package com.zxmys.course.programming.project2;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.zxmys.course.programming.common.game.*;
import com.zxmys.course.programming.project2.Panel.*;
import com.zxmys.course.programming.project2.Panel.SudokuPanel.*;

/**
 * 主窗口
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * 游戏状态：未定义
	 */
	public static final int UNDEFINED = -2;

	/**
	 * 游戏状态：正在显示LOGO
	 */
	public static final int GAME_LOGO = -1;

	/**
	 * 游戏状态：正在显示TITLE
	 */
	public static final int GAME_TITLE = 0;

	/**
	 * 游戏状态：正在进行游戏
	 */
	public static final int GAME_PLAYING = 1;

	/**
	 * 游戏状态：正在编辑
	 */
	public static final int GAME_EDITING = 2;

	/**
	 * 游戏状态：正在载入（随机生成题目中）
	 */
	public static final int GAME_LOADING = 3;

	/**
	 * 游戏状态：正在结束（智能解题中）
	 */
	public static final int GAME_ENDING = 4;

	/**
	 * 游戏状态：已结束
	 */
	public static final int GAME_ENDED = 5;

	/**
	 * BGM状态：不可用
	 */
	public static final int BGM_UNAVAILABLE = 0;

	/**
	 * BGM状态：已停止
	 */
	public static final int BGM_STOPPED = 1;

	/**
	 * BGM状态：正在播放
	 */
	public static final int BGM_PLAYING = 2;

	/**
	 * 在TITLE界面所使用的BGM文件名。<br/> 在这一版本中所有BGM都由此指定。
	 */
	public static final String TITLE_BGM = "tamsp15.mid";

	/**
	 * 游戏窗口标题
	 */
	public static final String title = "Zx.MYS's Sudoku.2";

	/**
	 * 游戏状态
	 */
	private int gameStatus = UNDEFINED;

	/**
	 * 是否正在显示Title
	 */
	private boolean showingTitle = false;

	/**
	 * 游戏BGM
	 */
	private GameAudio bgm = null;

	/**
	 * BGM状态
	 */
	private int bgmStatus;

	/**
	 * BGM路径
	 */
	String bgmPath = "";

	/**
	 * 主窗口流程
	 */
	public MainFrame() {

		setGameStatus(GAME_LOGO);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		setResizable(false);
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new SudokuPlayPanel(this).getPreferredSize());
		setVisible(true);
		getContentPane().removeAll();
		//showLogo();
		paintComponents(getGraphics());
		getContentPane().removeAll();
		setBGM(MainFrame.TITLE_BGM);
		//BGMPlay();
		add(showTitle());

		setGameStatus(GAME_TITLE);

		setJMenuBar(new MainControl(this));
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		paintComponents(getGraphics());

	}

	/**
	 * 开始显示窗口
	 */
	public static void main(String[] args) {
		new MainFrame();
	}

	/**
	 * 显示Title Panel，直到用户在Title Panel上点击鼠标才返回
	 * 
	 * @return Title画面结束后的对其进行截图的SnagPanel
	 */
	private SnagPanel showTitle() {

		showingTitle = true;
		Container tmp = getContentPane();
		TitlePanel titlePanel = new TitlePanel();
		titlePanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				showingTitle = false;
			}
		});

		setContentPane(titlePanel);
		titlePanel.setSize(tmp.getSize());
		titlePanel.start();
		while (showingTitle)
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		titlePanel.stop();
		SnagPanel ret = new SnagPanel(titlePanel);
		setContentPane(tmp);
		titlePanel = null;
		return ret;
	}

	/**
	 * 显示Zx.MYS的LOGO
	 */
	private void showLogo() {
		Container tmp = getContentPane();
		LogoPanel logoPanel = new LogoPanel();
		setContentPane(logoPanel);
		add(logoPanel);
		logoPanel.setSize(tmp.getSize());
		logoPanel.start();
		setContentPane(tmp);
		logoPanel = null;
	}

	/**
	 * 获得游戏状态
	 * 
	 * @return 游戏状态
	 */
	public int getGameStatus() {
		return gameStatus;
	}

	/**
	 * 设置游戏状态。此时如果具有MainControl类的JMenuBar将调用该MainControl的statusChanged方法
	 * 
	 * @param gameStatus
	 *            要设置的游戏状态
	 */
	public void setGameStatus(int gameStatus) {
		if (gameStatus > 5 || gameStatus < -2)
			gameStatus = -2;
		this.gameStatus = gameStatus;
		if (getJMenuBar() != null && getJMenuBar() instanceof MainControl)
			((MainControl) getJMenuBar()).statusChanged();
	}

	/**
	 * 改变ContentPane的内容。<br/>移去所有原来的内容，加入panel
	 * 
	 * @param panel
	 *            要加入的Panel
	 */
	public void changePane(JPanel panel) {
		getContentPane().removeAll();
		add(panel);
		paintComponents(getGraphics());
	}

	/**
	 * 获得当前BGM路径
	 * 
	 * @return 当前BGM路径
	 */
	public String getBGMPath() {
		return bgmPath;
	}

	/**
	 * 设置BGM
	 * 
	 * @param path
	 *            要设置的BGM的路径
	 */
	public void setBGM(String path) {
		try {
			this.bgm = new GameAudio(GameResource.getResource(path));
			bgmPath = path;
			bgmStatus = BGM_STOPPED;
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bgmStatus = BGM_UNAVAILABLE;
		}
		;
	}

	/**
	 * 循环播放BGM
	 */
	public void BGMPlay() {
		if (bgm != null) {
			bgm.loop();
			bgmStatus = BGM_PLAYING;
		}
	}

	/**
	 * 停止播放BGM
	 */
	public void BGMStop() {
		if (bgm != null) {
			bgm.stop();
			bgmStatus = BGM_STOPPED;
		}
	}

	/**
	 * 获得BGM状态
	 * 
	 * @return BGM状态
	 */
	public int getBGMStatus() {
		return bgmStatus;
	}
}
