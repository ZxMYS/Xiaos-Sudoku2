package com.zxmys.course.programming.project2;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.zxmys.course.programming.common.game.*;
import com.zxmys.course.programming.project2.Panel.*;
import com.zxmys.course.programming.project2.Panel.SudokuPanel.*;

/**
 * ������
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * ��Ϸ״̬��δ����
	 */
	public static final int UNDEFINED = -2;

	/**
	 * ��Ϸ״̬��������ʾLOGO
	 */
	public static final int GAME_LOGO = -1;

	/**
	 * ��Ϸ״̬��������ʾTITLE
	 */
	public static final int GAME_TITLE = 0;

	/**
	 * ��Ϸ״̬�����ڽ�����Ϸ
	 */
	public static final int GAME_PLAYING = 1;

	/**
	 * ��Ϸ״̬�����ڱ༭
	 */
	public static final int GAME_EDITING = 2;

	/**
	 * ��Ϸ״̬���������루���������Ŀ�У�
	 */
	public static final int GAME_LOADING = 3;

	/**
	 * ��Ϸ״̬�����ڽ��������ܽ����У�
	 */
	public static final int GAME_ENDING = 4;

	/**
	 * ��Ϸ״̬���ѽ���
	 */
	public static final int GAME_ENDED = 5;

	/**
	 * BGM״̬��������
	 */
	public static final int BGM_UNAVAILABLE = 0;

	/**
	 * BGM״̬����ֹͣ
	 */
	public static final int BGM_STOPPED = 1;

	/**
	 * BGM״̬�����ڲ���
	 */
	public static final int BGM_PLAYING = 2;

	/**
	 * ��TITLE������ʹ�õ�BGM�ļ�����<br/> ����һ�汾������BGM���ɴ�ָ����
	 */
	public static final String TITLE_BGM = "tamsp15.mid";

	/**
	 * ��Ϸ���ڱ���
	 */
	public static final String title = "Zx.MYS's Sudoku.2";

	/**
	 * ��Ϸ״̬
	 */
	private int gameStatus = UNDEFINED;

	/**
	 * �Ƿ�������ʾTitle
	 */
	private boolean showingTitle = false;

	/**
	 * ��ϷBGM
	 */
	private GameAudio bgm = null;

	/**
	 * BGM״̬
	 */
	private int bgmStatus;

	/**
	 * BGM·��
	 */
	String bgmPath = "";

	/**
	 * ����������
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
	 * ��ʼ��ʾ����
	 */
	public static void main(String[] args) {
		new MainFrame();
	}

	/**
	 * ��ʾTitle Panel��ֱ���û���Title Panel�ϵ�����ŷ���
	 * 
	 * @return Title���������Ķ�����н�ͼ��SnagPanel
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
	 * ��ʾZx.MYS��LOGO
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
	 * �����Ϸ״̬
	 * 
	 * @return ��Ϸ״̬
	 */
	public int getGameStatus() {
		return gameStatus;
	}

	/**
	 * ������Ϸ״̬����ʱ�������MainControl���JMenuBar�����ø�MainControl��statusChanged����
	 * 
	 * @param gameStatus
	 *            Ҫ���õ���Ϸ״̬
	 */
	public void setGameStatus(int gameStatus) {
		if (gameStatus > 5 || gameStatus < -2)
			gameStatus = -2;
		this.gameStatus = gameStatus;
		if (getJMenuBar() != null && getJMenuBar() instanceof MainControl)
			((MainControl) getJMenuBar()).statusChanged();
	}

	/**
	 * �ı�ContentPane�����ݡ�<br/>��ȥ����ԭ�������ݣ�����panel
	 * 
	 * @param panel
	 *            Ҫ�����Panel
	 */
	public void changePane(JPanel panel) {
		getContentPane().removeAll();
		add(panel);
		paintComponents(getGraphics());
	}

	/**
	 * ��õ�ǰBGM·��
	 * 
	 * @return ��ǰBGM·��
	 */
	public String getBGMPath() {
		return bgmPath;
	}

	/**
	 * ����BGM
	 * 
	 * @param path
	 *            Ҫ���õ�BGM��·��
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
	 * ѭ������BGM
	 */
	public void BGMPlay() {
		if (bgm != null) {
			bgm.loop();
			bgmStatus = BGM_PLAYING;
		}
	}

	/**
	 * ֹͣ����BGM
	 */
	public void BGMStop() {
		if (bgm != null) {
			bgm.stop();
			bgmStatus = BGM_STOPPED;
		}
	}

	/**
	 * ���BGM״̬
	 * 
	 * @return BGM״̬
	 */
	public int getBGMStatus() {
		return bgmStatus;
	}
}
