package inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.main;

public class inventory extends JPanel {
	private BufferedImage[] images;
	public String[] files;
	public Integer[] blockAmmount;
	public java.io.File f;
	public int gaps;
	public int blockNumber;
	public int inventoryHeight;
	public int rectangleWidth;
	public int width;
	public int height;
	public int selected;

	public inventory(int inventoryBlock, int inventoryGap, int inventoryExtra,
			int inventoryHeight1) {
		f = null;
		gaps = inventoryGap;
		blockNumber = inventoryBlock;
		rectangleWidth = main.blockHeight + inventoryExtra;
		images = new BufferedImage[blockNumber];
		files = new String[blockNumber];
		blockAmmount = new Integer[blockNumber];
		inventoryHeight = inventoryHeight1;
		for (int i = 0; i < blockNumber; i++) {
			blockAmmount[i] = 64;
			files[i] = "";
		}
		files[0] = "dirt.jpg";
		files[1] = "grass.jpg";
		selected = 0;
		height = (rectangleWidth + gaps) * (inventoryHeight) + gaps;
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
		g.fillRect(0, 0, width, height);
		g.setColor(Color.gray);
//		for (int i = 0; i < blockNumber; i++) {
//			if (i == selected) {
//				g.setColor(Color.blue);
//			} else {
//				g.setColor(Color.gray);
//			}
//			g.drawRect((rectangleWidth + gaps) * i + gaps, gaps, rectangleWidth, rectangleWidth);
//			int imageCornerX = (rectangleWidth + gaps) * i + gaps
//					+ (rectangleWidth - main.blockHeight) / 2;
//			int imageCornerY = gaps + (rectangleWidth - main.blockHeight) / 2;
//			g.drawImage(images[i], imageCornerX, imageCornerY, null);
//			if (!files[i].equals("")) {
//				g.setFont(new Font("TimesRoman", Font.BOLD, 15));
//				g.setColor(Color.BLACK);
//				g.drawString(blockAmmount[i].toString(), imageCornerX + 5,
//						imageCornerY + main.blockHeight - 5);
//			}
//		}
		for (int x = 0; x < blockNumber; x++) {
			for (int y = 0; y < inventoryHeight; y++) {
				g.drawRect((rectangleWidth + gaps) * (x) + gaps,
						(rectangleWidth + gaps) * (y) + gaps, rectangleWidth,
						rectangleWidth);
			}
		}
	}
}
