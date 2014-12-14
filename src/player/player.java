/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package player;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JPanel;

/**
 *
 * @author ryanbradford
 */
public class player extends JPanel {
	int playerHeight = 150;
	int playerWidth = 30;
	int standardResolutionX;
	int standardResolutionY;
	double scaleFactorX;
	double scaleFactorY;
	Color brown;
	Color lightBlue;
	Color darkBlue;

	public player() {
		playerHeight = 160;
		playerWidth = playerHeight/4;
		darkBlue = new Color(25,25,112);
		lightBlue = new Color(0,155,155);
		brown = new Color(139, 69, 19);
		standardResolutionY = 720;
		standardResolutionX = 1280;
		scaleFactorX = Toolkit.getDefaultToolkit().getScreenSize().getWidth()
				/ standardResolutionX;
		scaleFactorY = Toolkit.getDefaultToolkit().getScreenSize().getHeight()
				/ standardResolutionY;
	}

	protected void paintComponent(Graphics g) {
		drawHead(g);
		drawBody(g);
		drawArm(g);
		drawLegs(g);
	}

	public void drawArm(Graphics g) {
    	g.setColor(brown);
    	g.fillRect(playerWidth/4, playerHeight*3/10, playerWidth/2, playerHeight*3/10);
    	//3/10 of Height
    	//1/2 of Width
    }

	public void drawBody(Graphics g) {
		g.setColor(lightBlue);
		g.fillRect(playerWidth/4, playerHeight/5, playerWidth/2, playerHeight/10);
		// 1/10 of Height
		// 1/2 of Width
	}

	public void drawHead(Graphics g) {
		g.setColor(brown);
		g.fillRect((int)(playerWidth*((1+1/3)*.1)), 0, (int)(playerWidth/1.2), playerHeight/5);
		// 1/5 of Height
		// Width/1.2 or *1/1.2 or *.8333333333
	}
	
	public void drawLegs(Graphics g) {
		g.setColor(darkBlue);
		g.fillRect(playerWidth/4, playerHeight*6/10, playerWidth/2, playerHeight*7/20);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(playerWidth/4, playerHeight*19/20, playerWidth/2, playerHeight*1/20);		
		// 4/10 of Height
		// Full Width
	}

	public int getPlayerHeight() {
		return playerHeight;
	}

	public int getPlayerWidth() {
		return playerWidth;
	}
}
