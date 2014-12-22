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
import main.main;

/**
 *
 * @author ryanbradford
 */
public class player extends JPanel {
	int playerHeight;
	int playerWidth;
	int standardResolutionX;
	int standardResolutionY;
	double scaleFactorX;
	double scaleFactorY;
	Color skinColor;
	Color shirtColor;
	Color pantsColor;
	Color shoeColor;

	public player(Color skinColor1, Color pantsColor1, Color shirtColor1, Color shoeColor1) {
		skinColor = skinColor1;
		shirtColor = shirtColor1;
		pantsColor = pantsColor1;
		shoeColor = shoeColor1;
		playerHeight = main.getBlockHeight() * 2;
		playerWidth = playerHeight / 4;
		standardResolutionY = 1080;
		standardResolutionX = 1920;
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
		g.setColor(skinColor);
		g.fillRect(playerWidth / 4, playerHeight * 3 / 10 - 1, playerWidth / 2,
				playerHeight * 3 / 10 + 2);
		// 3/10 of Height
		// 1/2 of Width
	}

	public void drawBody(Graphics g) {
		g.setColor(shirtColor);
		g.fillRect(playerWidth / 4, playerHeight / 5, playerWidth / 2,
				playerHeight / 10);
		// 1/10 of Height
		// 1/2 of Width
	}

	public void drawHead(Graphics g) {
		g.setColor(skinColor);
		g.fillRect((int) (playerWidth * ((1 + 1 / 3) * .1)), 0,
				(int) (playerWidth / 1.2), playerHeight / 5);
		// 1/5 of Height
		// Width/1.2 or *1/1.2 or *.8333333333
	}

	public void drawLegs(Graphics g) {
		g.setColor(pantsColor);
		g.fillRect(playerWidth / 4, playerHeight * 6 / 10, playerWidth / 2,
				playerHeight * 7 / 20);
		g.setColor(shoeColor);
		g.fillRect(playerWidth / 4, playerHeight * 19 / 20 - 1,
				playerWidth / 2, playerHeight * 1 / 20 + 1);
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
