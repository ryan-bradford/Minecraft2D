package inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.main;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class inventoryBar extends JPanel {
	private BufferedImage[] images = new BufferedImage[8];
	private String[] files;
	public java.io.File f;
	public int gaps;
	public int blockNumber;
	public int rectangleWidth;
	public int width;
	public int height;

	public inventoryBar(int inventoryBlock, int inventoryGap, int inventoryExtra) {
		files = new String[] { "dirt.jpg", "grass.jpg", "", "", "", "", "",
				"" };
		f = null;
		gaps = inventoryGap;
		blockNumber = inventoryBlock;
		rectangleWidth = main.blockHeight + inventoryExtra;
		height = (int) (rectangleWidth + (gaps) * 2);
		width = (rectangleWidth + gaps) * blockNumber + gaps;
		for (int i = 0; i < blockNumber; i++) {
			f = new java.io.File(files[i]); // Reads in the file
			try {
				images[i] = ImageIO.read(f);
			} catch (IOException ex) {
				// handle exception...
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		// System.out.println("Drawn");
		g.setColor(Color.gray);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		for (int i = 0; i < blockNumber; i++) {
			g.drawRect((rectangleWidth + gaps) * i + gaps, height / 2
					- (rectangleWidth) / 2, rectangleWidth, rectangleWidth);
			g.drawImage(images[i], ((i * (main.blockHeight + gaps)) + gaps)
					+ gaps * i + (rectangleWidth - main.blockHeight) / 2,
					(height / 2 - (rectangleWidth) / 2)
							+ (rectangleWidth - main.blockHeight) / 2, null); // see
																				// javadoc
			// for more
			// info on the
			// parameters
		}

	}
}
