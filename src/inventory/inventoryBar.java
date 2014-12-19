package inventory;

import java.awt.Color;
import java.awt.Graphics;

import main.main;

import javax.swing.JPanel;

public class inventoryBar extends JPanel{
	int width = main.screenWidth/3;
	int height = (int) (main.blockHeight*1.3);
	public inventoryBar() {
		System.out.println("Init");
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		System.out.println("Drawn");
		g.setColor(Color.gray);
		g.fillRect(0, 0, width, height);
	}
}
