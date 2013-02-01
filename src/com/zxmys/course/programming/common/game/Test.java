package com.zxmys.course.programming.common.game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ListIterator;

import javax.swing.*;

/**
 * ”√”⁄≤‚ ‘com.zxmys.course.programming.common.game∞¸°£
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.1.14)
 */
public class Test extends JFrame {

	private static final long serialVersionUID = 1L;

	public Test(){
	
		GameGraphicPanel pane=new GameGraphicPanel(){
			private static final long serialVersionUID = 1L;
			private double x,y;
			private int dx=100,dy=100,_dx=100,_dy=100;
			
			private long lastDraw=System.currentTimeMillis();
			public void draw(Graphics g){
				clear();
				g.setColor(Color.WHITE);
				long now=System.currentTimeMillis();
				x+=(now-lastDraw)/1000.0*dx;
				y+=(now-lastDraw)/1000.0*dy;
				lastDraw=now;
				((Graphics2D)g).fill(new Rectangle2D.Double(x,y,10,10));
				g.drawString("FPS:"+curFPS, 15, 15);
				if(y<=0){
					y=0;
					dy=_dy;
				}
				if(y+10>=getHeight()){
					y=getHeight()-10;
					dy=-_dy;
				}
				if(x<=0){
					x=0;
					dx=_dx;
				}
				if(x+10>=getWidth()){
					x=getWidth()-10;
					dx=-_dx;
				}
				drawOverlaidComponent();
			}
			@Override
			public void drawSpirit(ListIterator<GameSpirit> sp, Graphics g) {
				// TODO Auto-generated method stub
				
			}
		};
		pane.setMaxFPS(60);
		setLayout(new OverlayLayout(this.getContentPane()));
		pane.setSize(getSize());
		add(pane);
		JButton asd=new JButton("Test Button");
		asd.setIgnoreRepaint(true);
		add(asd);
		setSize(200,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setIgnoreRepaint(true);
		pane.start();
		GameAudio sound;
		try{
			sound=new GameAudio(GameResource.getResource("test.mid"));
			//sound.play();
		}catch(NullPointerException e){
			e.printStackTrace();
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("FileNotFound!");
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Test();
	}

}
