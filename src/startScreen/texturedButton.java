package startScreen;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

public class texturedButton extends JButton {
	
	int width;
	int height;
	String text;
	
	public texturedButton(String text1, int width1, int height1) {
		width = width1;
		height = height1;
		text = text1;
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.WHITE);
		g.drawString(text, (int)(width/2 - text.length() * 3), height/2);
		g.setColor(Color.BLACK);
		g.drawLine(0, 0, 0, height);
		g.drawLine(0, width, height, height);
		g.drawLine(width, width, 0, height);
		g.drawLine(0, width, 0, 0);
	}

}
