package block;

import java.awt.Color;
import java.awt.Graphics;

import main.main;

import javax.swing.JPanel;

public class selectorBlock extends JPanel{
	public selectorBlock() {
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.BLUE );
		g.drawRect(0,0,main.getBlockHeight()-1, main.getBlockHeight()-1);
		//g.drawRect(1,1,main.getBlockHeight()-4, main.getBlockHeight()-4);
		//g.drawRect(2,2,main.getBlockHeight()-5, main.getBlockHeight()-5);		
	}
}
