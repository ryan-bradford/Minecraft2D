package block;

import java.awt.Color; 
import java.awt.Graphics;

import javax.swing.JPanel;

import main.main;

public class air extends JPanel{
	
	public air( ) {
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(new Color(135,206,250));
		g.fillRect(0, 0, main.screenWidth,
				main.screenHeight);
	}
}
