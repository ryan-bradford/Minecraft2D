package startScreen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

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
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.gray);
		g2d.fillRect(0, 0, width, height);
		g2d.setColor(Color.WHITE);
		g2d.drawString(text, (int)(width/2 - text.length() * 3), height/2);
		g2d.setColor(Color.DARK_GRAY);
		g2d.setStroke(new BasicStroke(5));
		g2d.drawLine(0, 0, 0, height);
		g2d.drawLine(0, width, height, height);
		g2d.drawLine(width, width, 0, height);
		g2d.drawLine(0, width, 0, 0);
	}

}
