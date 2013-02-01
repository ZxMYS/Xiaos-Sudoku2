package com.zxmys.course.programming.project2;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import com.zxmys.course.programming.common.game.*;
import com.zxmys.course.programming.project1.*;
import com.zxmys.course.programming.project2.Panel.SudokuPanel.*;
import com.zxmys.course.programming.project2.Panel.SudokuPanel.BoardColor.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.metal.MetalLookAndFeel;

import com.sun.java.swing.plaf.motif.MotifLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

/**
 * 选择数独盘颜色的RadioButtonMenuItem，带有数独盘颜色配置信息
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.15)
 */
class BoardColorMenuItem extends JRadioButtonMenuItem {

	private static final long serialVersionUID = 1L;

	/**
	 * 所使用的数独盘颜色配置
	 */
	SudokuBoardColor c;

	/**
	 * @param text
	 *            菜单文字
	 * @param c
	 *            数独盘颜色配置
	 */
	BoardColorMenuItem(String text, SudokuBoardColor c) {
		super(text);
		this.c = c;
	}

}

/**
 * 主控制流程类，同时是主窗口的菜单。负责控制游戏的主要流程。
 * 
 * @author Zx.MYS
 * @version 1.1 (2009.1.15)
 */
public class MainControl extends JMenuBar {

	/**
	 * 配色方案菜单数组常量
	 */
	private static final BoardColorMenuItem menuBoardColors[] = {
			new BoardColorMenuItem("纯白之翼", new SimpleSudokuBoardColor()),
			new BoardColorMenuItem("黄色沙漠", new SimpleSudokuBoardColor(
					Color.YELLOW, Color.WHITE)),
			new BoardColorMenuItem("蓝色之海", new SimpleSudokuBoardColor(
					Color.BLUE, Color.WHITE)),
			new BoardColorMenuItem("红色咖啡", new SimpleSudokuBoardColor(Color.RED
					.darker().darker().darker().darker().darker().darker(),
					Color.RED.darker().darker().darker().darker())),
			new BoardColorMenuItem("五颜六色", new SimpleSudokuBoardColor(
					new Color[] { Color.WHITE, Color.LIGHT_GRAY, Color.BLUE,
							Color.PINK, Color.ORANGE, Color.YELLOW,
							Color.GREEN, Color.MAGENTA, Color.CYAN })),
			new BoardColorMenuItem("绿地之金", new SimpleSudokuBoardColor(
					Color.YELLOW, Color.GREEN)),
			new BoardColorMenuItem("紫色浪漫", new SimpleSudokuBoardColor(
					Color.MAGENTA)),
			new BoardColorMenuItem("淡红淡紫", new SimpleSudokuBoardColor(
					Color.PINK.brighter(), Color.MAGENTA.brighter().brighter())) };

	/**
	 * 根据难度生成并开始随机游戏
	 * 
	 * @author Zx.MYS
	 * 
	 */
	protected class RandomGameStarter implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (frame.getGameStatus() == MainFrame.GAME_PLAYING
					|| frame.getGameStatus() == MainFrame.GAME_EDITING)
				if (JOptionPane.showConfirmDialog(frame, "你确定要放弃当前的数独吗？", "确认",
						JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
					return;

			frame.setGameStatus(MainFrame.GAME_LOADING);
			JMenuItem source = ((JMenuItem) e.getSource());
			String name = source.getName();
			int blanks = 40;
			if (name.equals("menuRandomEasy")) {
				blanks = 35;
			} else if (name.equals("menuRandomNormal")) {
				blanks = 40;
			} else if (name.equals("menuRandomHard")) {
				blanks = 45;
			}
			SudokuPlayPanel panel = new SudokuPlayPanel(frame);
			panel.loadSudokuBoard(SudokuCreator.getRandomGame(blanks));
			frame.changePane(panel);
			frame.setGameStatus(MainFrame.GAME_PLAYING);
		}
	}

	/**
	 * 载入游戏。<br/> 使用Java的ObjectIO实现。
	 * 
	 * @author Zx.MYS
	 * 
	 */
	protected class GameOpener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (frame.getGameStatus() == MainFrame.GAME_PLAYING
					|| frame.getGameStatus() == MainFrame.GAME_EDITING)
				if (JOptionPane.showConfirmDialog(frame, "你确定要放弃当前的数独吗？", "确认",
						JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
					return;

			File file;
			GUISudokuBoard game = null;
			boolean gameSolvable = false;
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileFilter() {
				public boolean accept(File f) {
					return f.getName().toLowerCase().endsWith(".sud")
							|| f.isDirectory();
				}

				public String getDescription() {
					return "Zx.MYS的数独文件(*.sud)";
				}
			});
			if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
				long playTime = 0;
				try {
					ObjectInputStream in = new ObjectInputStream(GameFileIO
							.getInputStream(file));
					// 读入底层数独盘
					game = (GUISudokuBoard) in.readObject();
					System.out.println(game.getOriginalBoard());
					gameSolvable=Common.Function.isSolvable(game.getOriginalBoard());
					// 读入游戏时间
					playTime = in.readLong();
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "打开失败");
					return;
				} catch (ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(frame, "不是一个有效的数独文件");
					e1.printStackTrace();
				}

				if (!Common.Function.isSolvable(game.getOriginalBoard())) {
					if (JOptionPane.showConfirmDialog(frame,
							"该数独的题目不可解（但是可以在载入后进行编辑），你还要读入吗？", "警告",
							JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
						return;
				}
				boolean goEdit = false;
				if (!gameSolvable)
					goEdit = (JOptionPane.showConfirmDialog(frame,
							"该数独的题目不可解,您要现在开始编辑吗？", "消息",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
				SudokuGamePanel panel;
				if (goEdit)
					panel = new SudokuEditPanel(frame);
				else
					panel = new SudokuPlayPanel(frame);
				frame.setGameStatus(MainFrame.GAME_LOADING);
				panel.loadSudokuBoard(new GUISudokuBoard(game));
				frame.changePane(panel);
				if (goEdit)
					frame.setGameStatus(MainFrame.GAME_EDITING);
				else {
					frame.setGameStatus(MainFrame.GAME_PLAYING);
					getGamePanel().getDashBoardPanel().setPlayTime(playTime);

				}
			}
		}
	}

	/**
	 * 保存游戏<br/> 使用Java的ObjectIO实现。
	 * 
	 * @author Zx.MYS
	 * 
	 */
	protected class GameSaver implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			assert (frame.getGameStatus() == MainFrame.GAME_PLAYING || frame
					.getGameStatus() == MainFrame.GAME_EDITING);
			File file;
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileFilter() {
				public boolean accept(File f) {
					return f.getName().toLowerCase().endsWith(".sud")
							|| f.isDirectory();
				}

				public String getDescription() {
					return "Zx.MYS的数独文件(*.sud)";
				}
			});
			if (!Common.Function.isSolvable(getFrameBoard().getOriginalBoard())) {
				if (JOptionPane.showConfirmDialog(frame, "该数独不可解，你还要保存吗？",
						"警告", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
					return;
			}
			if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
				if (!file.getName().toLowerCase().endsWith(".sud")) {
					file = new File(file.getAbsolutePath() + ".sud");
				}
				if (file.exists()) {
					if (JOptionPane.showConfirmDialog(frame, "文件"
							+ file.getAbsolutePath() + "已存在，你想要覆盖它吗？", "警告",
							JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
						return;
				}
				try {
					ObjectOutputStream out = new ObjectOutputStream(GameFileIO
							.getOutputStream(file));
					// 写入数独面板的底层数独盘
					out.writeObject(getFrameBoard());
					// 写入游戏时间
					if (frame.getGameStatus() == MainFrame.GAME_EDITING)
						out.writeLong(0);
					else
						out.writeLong(getGamePanel().getDashBoardPanel()
								.getPlayTimeLong());
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "保存失败");
					return;
				}
			}
		}
	}

	/**
	 * 重玩当前游戏
	 * 
	 * @author Zx.MYS
	 * 
	 */
	protected class GameReplayer implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			assert (frame.getGameStatus() == MainFrame.GAME_PLAYING);
			if (JOptionPane.showConfirmDialog(frame, "你确定要放弃当前的数独吗？", "确认",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
				return;

			frame.setGameStatus(MainFrame.GAME_LOADING);
			SudokuPlayPanel panel = new SudokuPlayPanel(frame);
			panel.loadSudokuBoard(getFrameBoard().getOriginalBoard());
			frame.changePane(panel);
			frame.setGameStatus(MainFrame.GAME_PLAYING);
		}
	}

	/**
	 * 撤销修改步骤
	 * 
	 * @author Zx.MYS
	 * 
	 */
	protected class GameUndoer implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			assert (frame.getGameStatus() == MainFrame.GAME_PLAYING || frame
					.getGameStatus() == MainFrame.GAME_EDITING);
			SudokuGamePanel gp = getGamePanel();
			assert (gp != null && gp.canUndo());
			gp.undo();
			menuUndo.setEnabled(gp.canUndo());
		}
	}

	/**
	 * 重做游戏步骤
	 * 
	 * @author Zx.MYS
	 * 
	 */
	protected class GameRedoer implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			assert (frame.getGameStatus() == MainFrame.GAME_PLAYING || frame
					.getGameStatus() == MainFrame.GAME_EDITING);
			SudokuGamePanel gp = getGamePanel();
			assert (gp != null && gp.canRedo());
			gp.redo();
			menuRedo.setEnabled(gp.canRedo());
		}
	}

	/**
	 * 智能解题
	 * 
	 * @author Zx.MYS
	 * 
	 */
	protected class GameSolver implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			assert (frame.getGameStatus() == MainFrame.GAME_PLAYING);
			if (JOptionPane.showConfirmDialog(frame, "你确定要放弃当前的数独吗？", "确认",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
				return;

			frame.setGameStatus(MainFrame.GAME_ENDING);
			SudokuPlayPanel panel = new SudokuPlayPanel(frame);
			GUISudokuBoard sb = getFrameBoard();
			assert (sb != null);
			SudokuSolver ss = new SudokuSolver();
			ss.setBoard(sb);
			ss.solveBoard();
			panel.loadSudokuBoard(sb);
			panel.getSudokuBoardPanel().lock();
			frame.changePane(panel);
			frame.setGameStatus(MainFrame.GAME_ENDED);
		}
	}

	/**
	 * 编辑游戏
	 * 
	 * @author Zx.MYS
	 * 
	 */
	protected class GameEditor implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (frame.getGameStatus() == MainFrame.GAME_PLAYING)
				if (JOptionPane.showConfirmDialog(frame, "你确定要放弃当前的数独吗？", "确认",
						JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
					return;

			frame.setGameStatus(MainFrame.GAME_LOADING);
			SudokuEditPanel panel = new SudokuEditPanel(frame);
			if (getFrameBoard() != null) {
				GUISudokuBoard sb = getFrameBoard();
				panel.loadSudokuBoard(sb);
			}
			frame.changePane(panel);
			frame.setGameStatus(MainFrame.GAME_EDITING);
		}
	}

	/**
	 * 退出游戏
	 * 
	 * @author Zx.MYS
	 * 
	 */
	protected class GameExiter implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (JOptionPane.showConfirmDialog(frame, "你确定要退出吗？", "确认",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
				return;
			System.exit(0);
		}
	}

	/**
	 * 设置数独盘颜色
	 * 
	 * @author Zx.MYS
	 * 
	 */
	protected class BoardColorChanger implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			SudokuGamePanel gp = getGamePanel();
			assert (gp != null && (e.getSource() instanceof BoardColorMenuItem));
			gp.getSudokuBoardPanel().setSudokuBoardColor(
					((BoardColorMenuItem) e.getSource()).c);
		}
	}

	/**
	 * 设置程序Look&Feel
	 * 
	 * @author Zx.MYS
	 * 
	 */
	protected class LookChanger implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String name = ((JRadioButtonMenuItem) e.getSource()).getName();
			try {
				if (name.equals("menuWindowsLook")) {
					UIManager.setLookAndFeel(new WindowsLookAndFeel());
				} else if (name.equals("menuMotifLook")) {
					UIManager.setLookAndFeel(new MotifLookAndFeel());
				} else if (name.equals("menuMetalLook")) {
					UIManager.setLookAndFeel(new MetalLookAndFeel());
				} else if (name.equals("menuSystemLook")) {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} else {
					assert (false);
				}
				SwingUtilities.updateComponentTreeUI(frame);
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(frame, "当前系统不支持该外观");
			}
		}
	}

	/**
	 * 开关背景音乐
	 * 
	 * @author Zx.MYS
	 * 
	 */
	protected class BGMSwitcher implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (frame.getBGMStatus() == MainFrame.BGM_UNAVAILABLE)
				return;
			boolean play = ((JCheckBoxMenuItem) e.getSource()).isSelected();
			if (play) {
				frame.BGMPlay();
			} else {
				frame.BGMStop();
			}

		}
	}

	/**
	 * 打开关于窗口
	 * 
	 * @author Zx.MYS
	 * 
	 */
	protected class AboutFrameOpener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new AboutFrame(frame);
		}
	}

	/**
	 * 打开帮助文件
	 * 
	 * @author Zx.MYS
	 * 
	 */
	protected class ManualOpener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String path = "manual.pdf";
			if (java.awt.Desktop.isDesktopSupported()) {
				try {
					java.net.URI uri = GameResource.getResource(path).toURI();
					java.awt.Desktop dp = java.awt.Desktop.getDesktop();
					if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
						dp.browse(uri);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "打开失败,请手动打开manual.pdf");
				}
			}
		}
	}

	private static final long serialVersionUID = 1L;

	/**
	 * 主窗口的引用
	 */
	protected MainFrame frame;

	// 以下为各个菜单
	protected JMenu menuGame = new JMenu("游戏");

	protected JMenu menuRandomGame = new JMenu("随机游戏");

	protected JMenuItem menuRandomEasy = new JMenuItem("简单");

	protected JMenuItem menuRandomNormal = new JMenuItem("一般");

	protected JMenuItem menuRandomHard = new JMenuItem("困难");

	protected JMenuItem menuOpenGame = new JMenuItem("载入");

	protected JMenuItem menuSaveGame = new JMenuItem("保存");

	protected JMenuItem menuUndo = new JMenuItem("撤销");

	protected JMenuItem menuRedo = new JMenuItem("重做");

	protected JMenuItem menuEditGame = new JMenuItem("编辑");

	protected JMenuItem menuReplayGame = new JMenuItem("重玩");

	protected JMenuItem menuSolveGame = new JMenuItem("智能解题");

	protected JMenuItem menuExit = new JMenuItem("退出");

	protected JMenu menuOption = new JMenu("选项");

	protected JMenu menuLookAndFeel = new JMenu("外观");

	protected ButtonGroup groupLookAndFeel = new ButtonGroup();

	protected ButtonGroup groupBoardColor = new ButtonGroup();

	protected JRadioButtonMenuItem menuWindowsLook = new JRadioButtonMenuItem(
			"Window(仅限Windows系统)");

	protected JRadioButtonMenuItem menuMotifLook = new JRadioButtonMenuItem(
			"Motif");

	protected JRadioButtonMenuItem menuMetalLook = new JRadioButtonMenuItem(
			"Metal");

	protected JRadioButtonMenuItem menuSystemLook = new JRadioButtonMenuItem(
			"系统默认");

	protected JMenu menuTheme = new JMenu("配色主题");

	protected JCheckBoxMenuItem menuBGM = new JCheckBoxMenuItem("背景音乐");

	protected JMenu menuHelp = new JMenu("帮助");

	protected JMenuItem menuManual = new JMenuItem("用户手册");

	protected JMenuItem menuAbout = new JMenuItem("关于");

	/**
	 * 构造函数。初始化菜单并加入Listener
	 * 
	 * @param frame
	 *            主窗口
	 */
	public MainControl(MainFrame frame) {

		this.frame = frame;

		// menuGame
		menuRandomEasy.setName("menuRandomEasy");
		menuRandomNormal.setName("menuRandomNormal");
		menuRandomHard.setName("menuRandomHard");
		RandomGameStarter randomGameStarter = new RandomGameStarter();
		menuRandomEasy.addActionListener(randomGameStarter);
		menuRandomNormal.addActionListener(randomGameStarter);
		menuRandomHard.addActionListener(randomGameStarter);
		menuRandomGame.add(menuRandomEasy);
		menuRandomGame.add(menuRandomNormal);
		menuRandomGame.add(menuRandomHard);
		menuGame.add(menuRandomGame);
		menuOpenGame.addActionListener(new GameOpener());
		menuGame.add(menuOpenGame);
		menuSaveGame.addActionListener(new GameSaver());
		menuGame.add(menuSaveGame);
		menuReplayGame.addActionListener(new GameReplayer());
		menuGame.add(menuReplayGame);
		//menuGame.addSeparator();
		menuUndo.addActionListener(new GameUndoer());
		//menuGame.add(menuUndo);
		menuRedo.addActionListener(new GameRedoer());
		//menuGame.add(menuRedo);
		//menuGame.addSeparator();
		menuSolveGame.addActionListener(new GameSolver());
		menuGame.add(menuSolveGame);
		menuGame.addSeparator();
		menuEditGame.addActionListener(new GameEditor());
		menuGame.add(menuEditGame);
		menuGame.addSeparator();
		menuExit.addActionListener(new GameExiter());
		menuGame.add(menuExit);
		add(menuGame);

		// menuOption
		menuWindowsLook.setName("menuWindowsLook");
		menuMotifLook.setName("menuMotifLook");
		menuMetalLook.setName("menuMetalLook");
		menuSystemLook.setName("menuSystemLook");
		menuWindowsLook.addActionListener(new LookChanger());
		menuMotifLook.addActionListener(new LookChanger());
		menuMetalLook.addActionListener(new LookChanger());
		menuSystemLook.addActionListener(new LookChanger());
		menuLookAndFeel.add(menuWindowsLook);
		menuLookAndFeel.add(menuMotifLook);
		menuLookAndFeel.add(menuMetalLook);
		menuLookAndFeel.add(menuSystemLook);
		groupLookAndFeel.add(menuWindowsLook);
		groupLookAndFeel.add(menuMotifLook);
		groupLookAndFeel.add(menuMetalLook);
		groupLookAndFeel.add(menuSystemLook);
		menuOption.add(menuLookAndFeel);

		if (UIManager.getLookAndFeel() instanceof WindowsLookAndFeel) {
			menuWindowsLook.setSelected(true);
		} else if (UIManager.getLookAndFeel() instanceof MotifLookAndFeel) {
			menuMotifLook.setSelected(true);
		} else if (UIManager.getLookAndFeel() instanceof MetalLookAndFeel) {
			menuMetalLook.setSelected(true);
		} else if (UIManager.getLookAndFeel().getClass().getName().equals(
				UIManager.getSystemLookAndFeelClassName())) {
			menuSystemLook.setSelected(true);
		}

		ActionListener bcc = new BoardColorChanger();
		for (BoardColorMenuItem bcmi : menuBoardColors) {
			bcmi.addActionListener(bcc);
			groupBoardColor.add(bcmi);
			menuTheme.add(bcmi);
		}
		menuBoardColors[0].setSelected(true);
		menuOption.add(menuTheme);

		menuBGM.addActionListener(new BGMSwitcher());
		menuOption.add(menuBGM);
		add(menuOption);

		// menuHelp
		menuManual.addActionListener(new ManualOpener());
		menuHelp.add(menuManual);
		menuHelp.addSeparator();
		menuAbout.addActionListener(new AboutFrameOpener());
		menuHelp.add(menuAbout);
		add(menuHelp);

		statusChanged();
	}

	/**
	 * 主窗口游戏状态改变回调函数 用于设置各个菜单是否可按
	 */
	public void statusChanged() {
		menuUndo.setEnabled(false);
		menuRedo.setEnabled(false);
		if (frame.getBGMStatus() == MainFrame.BGM_UNAVAILABLE)
			menuBGM.setEnabled(false);
		else
			menuBGM.setSelected(frame.getBGMStatus() == MainFrame.BGM_PLAYING);
		switch (frame.getGameStatus()) {
		case MainFrame.GAME_LOGO:
		case MainFrame.GAME_TITLE:
		case MainFrame.GAME_ENDED:
			frame.setTitle(MainFrame.title);
			boolean boardExists = (getFrameBoard() != null);
			menuRandomGame.setEnabled(true);
			menuSolveGame.setEnabled(false);
			menuReplayGame.setEnabled(boardExists);
			menuEditGame.setEnabled(true);
			menuSaveGame.setEnabled(boardExists);
			menuTheme.setEnabled(boardExists);
			break;
		case MainFrame.GAME_PLAYING:
			frame.setTitle(MainFrame.title + " - Playing");
			menuRandomGame.setEnabled(true);
			menuSolveGame.setEnabled(true);
			menuReplayGame.setEnabled(true);
			menuEditGame.setEnabled(true);
			menuSaveGame.setEnabled(true);
			menuTheme.setEnabled(true);
			break;
		case MainFrame.GAME_EDITING:
			frame.setTitle(MainFrame.title + " - Editing");
			menuRandomGame.setEnabled(true);
			menuSolveGame.setEnabled(false);
			menuReplayGame.setEnabled(false);
			menuEditGame.setEnabled(false);
			menuSaveGame.setEnabled(true);
			menuTheme.setEnabled(true);
			break;
		case MainFrame.GAME_LOADING:
		case MainFrame.GAME_ENDING:
			frame.setTitle(MainFrame.title);
			menuRandomGame.setEnabled(false);
			menuSolveGame.setEnabled(false);
			menuReplayGame.setEnabled(false);
			menuEditGame.setEnabled(false);
			menuSaveGame.setEnabled(false);
			menuTheme.setEnabled(false);
			break;
		default:
			assert (false);
		}
	}

	/**
	 * 获得当前数独面板的数独盘面板
	 * 
	 * @return 数独盘面板，如果主窗口没有数独面板返回null
	 */
	protected SudokuGamePanel getGamePanel() {
		for (Component c : frame.getContentPane().getComponents()) {
			if (c instanceof SudokuGamePanel) {
				return (SudokuGamePanel) c;
			}
		}
		return null;
	}

	/**
	 * 获得当前数独面板的数独盘
	 * 
	 * @return 数独盘，如果主窗口没有数独面板返回null
	 */
	protected GUISudokuBoard getFrameBoard() {
		SudokuGamePanel gp = getGamePanel();
		if (gp == null)
			return null;
		return gp.getSudokuBoard();
	}

	/**
	 * 数独盘状态被修改回调函数
	 */
	public void boardChanged() {
		SudokuGamePanel gp = getGamePanel();
		menuUndo.setEnabled(gp.canUndo());
		menuRedo.setEnabled(gp.canRedo());
	}

	/**
	 * 根据相应菜单选择情况获得当前数独盘颜色配置
	 * 
	 * @return 当前数独盘颜色配置，如果没有菜单被选中返回默认颜色（全白）
	 */
	public SudokuBoardColor getSudokuBoardColor() {
		for (BoardColorMenuItem bcmi : menuBoardColors) {
			if (bcmi.isSelected())
				return bcmi.c;
		}
		return new SimpleSudokuBoardColor();
	}
}
