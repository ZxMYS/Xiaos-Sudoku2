package com.zxmys.course.programming.common.game;

import java.awt.*;
import java.util.ListIterator;

/**
 * 显示Zx.MYS PRESENT的Panel
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public class LogoPanel extends GameGraphicPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * 字的颜色
	 */
	private Color c=Color.BLACK;
	
	/**
	 * 是否正在变淡
	 */
	private boolean brighter=true;
	
	/* (non-Javadoc)
	 * @see com.zxmys.course.programming.common.game.GameGraphicPanel#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		clear();
		g.setColor(c);
		String strToShow="Z x . M Y S  P R E S E N T";
		FontMetrics metrics = g.getFontMetrics();
		g.drawString(strToShow, (getWidth()-metrics.stringWidth(strToShow))/2, (getHeight()-metrics.getHeight())/2);
	}
	
	/**
	 * 开始绘图，控制LOGO淡入后淡出，并在淡出后才返回
	 * @see com.zxmys.course.programming.common.game.GameGraphicPanel#start()
	 */
	public void start(){
		super.start();
		while(true){
			if(brighter){
				c=c.brighter();
				if(c.equals(Color.WHITE)){
					brighter=false;
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}else{
				c=c.darker();
				if(c.equals(Color.BLACK)){
					stop();
					break;
				}
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.zxmys.course.programming.common.game.GameGraphicPanel#drawSpirit(java.util.ListIterator, java.awt.Graphics)
	 */
	@Override
	public void drawSpirit(ListIterator<GameSpirit> sp, Graphics g) {
	}

}
