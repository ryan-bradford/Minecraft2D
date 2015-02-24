package startScreen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import  main.main;

import javax.swing.JPanel;

public class rotatedText extends JPanel {

	int fontSize = 20;
	String[] text = new String[]{"Hi Mom!", "This is a game!", "Welcome!", "10/10 -IGN", "Danny Approved", "Kill all Humans", "Fun on a Bun", "Watch Out For Bears", "Drugs are Bad"};
	int currentText = 7;
	
	public rotatedText() {
		new changeText().start();		
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.YELLOW);
		g2.rotate(-Math.PI/8);
		g2.setFont(new Font("Serif", Font.BOLD, fontSize));
		g2.drawString(text[currentText], 150, 150);
	}
	
	public class changeText extends Thread {
		
		public void run() {
			while(true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fontSize = 18;
				repaint();
				main.start.map1.repaint();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fontSize = 20;
				repaint();
				main.start.map1.repaint();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fontSize = 22;
				if(currentText < text.length-1) {
					currentText++;
				} else {
					currentText = 0;
				}
				repaint();
				main.start.map1.repaint();
			}
		}
		
	}
	
}
