package startScreen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class texturedButton extends JButton {

	int width;
	int height;
	String text;
	Graphics g1;
	private BufferedImage image;
	public java.io.File f = null;

	public texturedButton(String text1, int width1, int height1) {
		f = new java.io.File("textures/button.png");
		try {
			image = ImageIO.read(f);
		} catch (IOException ex) {
			// handle exception...
		}
		width = width1;
		height = height1;
		text = text1;
	}

	protected void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters
		g.setColor(Color.WHITE);
		g.drawString(text, (int) (width / 2 - text.length() * 3), height / 2);
	}

}
