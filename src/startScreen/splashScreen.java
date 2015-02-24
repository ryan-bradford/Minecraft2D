package startScreen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class splashScreen extends JPanel {
	Graphics g1;
	String text = "Hi";
	private BufferedImage image;
	public java.io.File f = null;
	rotatedText textBox = new rotatedText();
	
	public splashScreen() {
		setLayout(null);
		textBox.setBounds(265, 70, 1000, 1000);
		add(textBox, 0);
		f = new java.io.File("textures/Minecraft-Logo.png");
		try {
			image = ImageIO.read(f);
		} catch (IOException ex) {
			// handle exception...
		}
	}
	
	protected void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters
	}
	
}
