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
 * ѡ����������ɫ��RadioButtonMenuItem��������������ɫ������Ϣ
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.15)
 */
class BoardColorMenuItem extends JRadioButtonMenuItem {

	private static final long serialVersionUID = 1L;

	/**
	 * ��ʹ�õ���������ɫ����
	 */
	SudokuBoardColor c;

	/**
	 * @param text
	 *            �˵�����
	 * @param c
	 *            ��������ɫ����
	 */
	BoardColorMenuItem(String text, SudokuBoardColor c) {
		super(text);
		this.c = c;
	}

}

/**
 * �����������࣬ͬʱ�������ڵĲ˵������������Ϸ����Ҫ���̡�
 * 
 * @author Zx.MYS
 * @version 1.1 (2009.1.15)
 */
public class MainControl extends JMenuBar {

	/**
	 * ��ɫ�����˵����鳣��
	 */
	private static final BoardColorMenuItem menuBoardColors[] = {
			new BoardColorMenuItem("����֮��", new SimpleSudokuBoardColor()),
			new BoardColorMenuItem("��ɫɳĮ", new SimpleSudokuBoardColor(
					Color.YELLOW, Color.WHITE)),
			new BoardColorMenuItem("��ɫ֮��", new SimpleSudokuBoardColor(
					Color.BLUE, Color.WHITE)),
			new BoardColorMenuItem("��ɫ����", new SimpleSudokuBoardColor(Color.RED
					.darker().darker().darker().darker().darker().darker(),
					Color.RED.darker().darker().darker().darker())),
			new BoardColorMenuItem("������ɫ", new SimpleSudokuBoardColor(
					new Color[] { Color.WHITE, Color.LIGHT_GRAY, Color.BLUE,
							Color.PINK, Color.ORANGE, Color.YELLOW,
							Color.GREEN, Color.MAGENTA, Color.CYAN })),
			new BoardColorMenuItem("�̵�֮��", new SimpleSudokuBoardColor(
					Color.YELLOW, Color.GREEN)),
			new BoardColorMenuItem("��ɫ����", new SimpleSudokuBoardColor(
					Color.MAGENTA)),
			new BoardColorMenuItem("���쵭��", new SimpleSudokuBoardColor(
					Color.PINK.brighter(), Color.MAGENTA.brighter().brighter())) };

	/**
	 * �����Ѷ����ɲ���ʼ�����Ϸ
	 * 
	 * @author Zx.MYS
	 * 
	 */
	protected class RandomGameStarter implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (frame.getGameStatus() == MainFrame.GAME_PLAYING
					|| frame.getGameStatus() == MainFrame.GAME_EDITING)
				if (JOptionPane.showConfirmDialog(frame, "��ȷ��Ҫ������ǰ��������", "ȷ��",
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
	 * ������Ϸ��<br/> ʹ��Java��ObjectIOʵ�֡�
	 * 
	 * @author Zx.MYS
	 * 
	 */
	protected class GameOpener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (frame.getGameStatus() == MainFrame.GAME_PLAYING
					|| frame.getGameStatus() == MainFrame.GAME_EDITING)
				if (JOptionPane.showConfirmDialog(frame, "��ȷ��Ҫ������ǰ��������", "ȷ��",
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
					return "Zx.MYS�������ļ�(*.sud)";
				}
			});
			if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
				long playTime = 0;
				try {
					ObjectInputStream in = new ObjectInputStream(GameFileIO
							.getInputStream(file));
					// ����ײ�������
					game = (GUISudokuBoard) in.readObject();
					System.out.println(game.getOriginalBoard());
					gameSolvable=Common.Function.isSolvable(game.getOriginalBoard());
					// ������Ϸʱ��
					playTime = in.readLong();
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "��ʧ��");
					return;
				} catch (ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(frame, "����һ����Ч�������ļ�");
					e1.printStackTrace();
				}

				if (!Common.Function.isSolvable(game.getOriginalBoard())) {
					if (JOptionPane.showConfirmDialog(frame,
							"����������Ŀ���ɽ⣨���ǿ������������б༭�����㻹Ҫ������", "����",
							JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
						return;
				}
				boolean goEdit = false;
				if (!gameSolvable)
					goEdit = (JOptionPane.showConfirmDialog(frame,
							"����������Ŀ���ɽ�,��Ҫ���ڿ�ʼ�༭��", "��Ϣ",
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
	 * ������Ϸ<br/> ʹ��Java��ObjectIOʵ�֡�
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
					return "Zx.MYS�������ļ�(*.sud)";
				}
			});
			if (!Common.Function.isSolvable(getFrameBoard().getOriginalBoard())) {
				if (JOptionPane.showConfirmDialog(frame, "���������ɽ⣬�㻹Ҫ������",
						"����", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
					return;
			}
			if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
				if (!file.getName().toLowerCase().endsWith(".sud")) {
					file = new File(file.getAbsolutePath() + ".sud");
				}
				if (file.exists()) {
					if (JOptionPane.showConfirmDialog(frame, "�ļ�"
							+ file.getAbsolutePath() + "�Ѵ��ڣ�����Ҫ��������", "����",
							JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
						return;
				}
				try {
					ObjectOutputStream out = new ObjectOutputStream(GameFileIO
							.getOutputStream(file));
					// д���������ĵײ�������
					out.writeObject(getFrameBoard());
					// д����Ϸʱ��
					if (frame.getGameStatus() == MainFrame.GAME_EDITING)
						out.writeLong(0);
					else
						out.writeLong(getGamePanel().getDashBoardPanel()
								.getPlayTimeLong());
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "����ʧ��");
					return;
				}
			}
		}
	}

	/**
	 * ���浱ǰ��Ϸ
	 * 
	 * @author Zx.MYS
	 * 
	 */
	protected class GameReplayer implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			assert (frame.getGameStatus() == MainFrame.GAME_PLAYING);
			if (JOptionPane.showConfirmDialog(frame, "��ȷ��Ҫ������ǰ��������", "ȷ��",
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
	 * �����޸Ĳ���
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
	 * ������Ϸ����
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
	 * ���ܽ���
	 * 
	 * @author Zx.MYS
	 * 
	 */
	protected class GameSolver implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			assert (frame.getGameStatus() == MainFrame.GAME_PLAYING);
			if (JOptionPane.showConfirmDialog(frame, "��ȷ��Ҫ������ǰ��������", "ȷ��",
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
	 * �༭��Ϸ
	 * 
	 * @author Zx.MYS
	 * 
	 */
	protected class GameEditor implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (frame.getGameStatus() == MainFrame.GAME_PLAYING)
				if (JOptionPane.showConfirmDialog(frame, "��ȷ��Ҫ������ǰ��������", "ȷ��",
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
	 * �˳���Ϸ
	 * 
	 * @author Zx.MYS
	 * 
	 */
	protected class GameExiter implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (JOptionPane.showConfirmDialog(frame, "��ȷ��Ҫ�˳���", "ȷ��",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
				return;
			System.exit(0);
		}
	}

	/**
	 * ������������ɫ
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
	 * ���ó���Look&Feel
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
				JOptionPane.showMessageDialog(frame, "��ǰϵͳ��֧�ָ����");
			}
		}
	}

	/**
	 * ���ر�������
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
	 * �򿪹��ڴ���
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
	 * �򿪰����ļ�
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
					JOptionPane.showMessageDialog(frame, "��ʧ��,���ֶ���manual.pdf");
				}
			}
		}
	}

	private static final long serialVersionUID = 1L;

	/**
	 * �����ڵ�����
	 */
	protected MainFrame frame;

	// ����Ϊ�����˵�
	protected JMenu menuGame = new JMenu("��Ϸ");

	protected JMenu menuRandomGame = new JMenu("�����Ϸ");

	protected JMenuItem menuRandomEasy = new JMenuItem("��");

	protected JMenuItem menuRandomNormal = new JMenuItem("һ��");

	protected JMenuItem menuRandomHard = new JMenuItem("����");

	protected JMenuItem menuOpenGame = new JMenuItem("����");

	protected JMenuItem menuSaveGame = new JMenuItem("����");

	protected JMenuItem menuUndo = new JMenuItem("����");

	protected JMenuItem menuRedo = new JMenuItem("����");

	protected JMenuItem menuEditGame = new JMenuItem("�༭");

	protected JMenuItem menuReplayGame = new JMenuItem("����");

	protected JMenuItem menuSolveGame = new JMenuItem("���ܽ���");

	protected JMenuItem menuExit = new JMenuItem("�˳�");

	protected JMenu menuOption = new JMenu("ѡ��");

	protected JMenu menuLookAndFeel = new JMenu("���");

	protected ButtonGroup groupLookAndFeel = new ButtonGroup();

	protected ButtonGroup groupBoardColor = new ButtonGroup();

	protected JRadioButtonMenuItem menuWindowsLook = new JRadioButtonMenuItem(
			"Window(����Windowsϵͳ)");

	protected JRadioButtonMenuItem menuMotifLook = new JRadioButtonMenuItem(
			"Motif");

	protected JRadioButtonMenuItem menuMetalLook = new JRadioButtonMenuItem(
			"Metal");

	protected JRadioButtonMenuItem menuSystemLook = new JRadioButtonMenuItem(
			"ϵͳĬ��");

	protected JMenu menuTheme = new JMenu("��ɫ����");

	protected JCheckBoxMenuItem menuBGM = new JCheckBoxMenuItem("��������");

	protected JMenu menuHelp = new JMenu("����");

	protected JMenuItem menuManual = new JMenuItem("�û��ֲ�");

	protected JMenuItem menuAbout = new JMenuItem("����");

	/**
	 * ���캯������ʼ���˵�������Listener
	 * 
	 * @param frame
	 *            ������
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
	 * ��������Ϸ״̬�ı�ص����� �������ø����˵��Ƿ�ɰ�
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
	 * ��õ�ǰ�����������������
	 * 
	 * @return ��������壬���������û��������巵��null
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
	 * ��õ�ǰ��������������
	 * 
	 * @return �����̣����������û��������巵��null
	 */
	protected GUISudokuBoard getFrameBoard() {
		SudokuGamePanel gp = getGamePanel();
		if (gp == null)
			return null;
		return gp.getSudokuBoard();
	}

	/**
	 * ������״̬���޸Ļص�����
	 */
	public void boardChanged() {
		SudokuGamePanel gp = getGamePanel();
		menuUndo.setEnabled(gp.canUndo());
		menuRedo.setEnabled(gp.canRedo());
	}

	/**
	 * ������Ӧ�˵�ѡ�������õ�ǰ��������ɫ����
	 * 
	 * @return ��ǰ��������ɫ���ã����û�в˵���ѡ�з���Ĭ����ɫ��ȫ�ף�
	 */
	public SudokuBoardColor getSudokuBoardColor() {
		for (BoardColorMenuItem bcmi : menuBoardColors) {
			if (bcmi.isSelected())
				return bcmi.c;
		}
		return new SimpleSudokuBoardColor();
	}
}
