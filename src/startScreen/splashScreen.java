package startScreen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class splashScreen extends JPanel {
	Graphics g1;
	private BufferedImage image;
	public java.io.File f = null;
	
	public splashScreen() {
		f = new java.io.File("textures/Minecraft-Logo.png");
		try {
			image = ImageIO.read(f);
		} catch (IOException ex) {
			// handle exception...
		}
	}
	
	protected void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters
		//g.setColor(Color.WHITE);
		//g.drawString(text, (int) (width / 2 - text.length() * 3), height / 2);
	}
	
}
