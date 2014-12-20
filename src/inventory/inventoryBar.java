package inventory;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.main;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class inventoryBar extends JPanel {
	private BufferedImage[] images;
	public String[] files;
	public Integer[] blockAmmount;
	public java.io.File f;
	public int gaps;
	public int blockNumber;
	public int rectangleWidth;
	public int width;
	public int height;
	public int selected;

	public inventoryBar(int inventoryBlock, int inventoryGap, int inventoryExtra) {
		f = null;
		gaps = inventoryGap;
		blockNumber = inventoryBlock;
		rectangleWidth = main.blockHeight + inventoryExtra;
		images = new BufferedImage[blockNumber];
		files = new String[blockNumber];
		blockAmmount = new Integer[blockNumber];
		for (int i = 0; i < blockNumber; i++) {
			blockAmmount[i] = 64;
			files[i] = "";
		}
		files[0] = "dirt.jpg";
		files[1] = "grass.jpg";
		selected = 0;
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
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		for (int i = 0; i < blockNumber; i++) {
			if (i == selected) {
				g.setColor(Color.blue);
			} else {
				g.setColor(Color.gray);
			}
			g.drawRect((rectangleWidth + gaps) * i + gaps, height / 2
					- (rectangleWidth) / 2, rectangleWidth, rectangleWidth);
			int imageCornerX = (rectangleWidth + gaps) * i + gaps
					+ (rectangleWidth - main.blockHeight) / 2;
			int imageCornerY = height / 2 - (rectangleWidth) / 2
					+ (rectangleWidth - main.blockHeight) / 2;
			g.drawImage(images[i], imageCornerX, imageCornerY, null);
			// see javadoc for more info on the parameters
			if (!files[i].equals("")) {
				g.setFont(new Font("TimesRoman", Font.BOLD, 15));
				g.setColor(Color.BLACK);
				g.drawString(blockAmmount[i].toString(), imageCornerX + 5,
						imageCornerY + main.blockHeight - 5);
			}
		}

	}

	public String setSelected(int i) {
		selected = i;
		repaint();
		return files[selected];
	}

	public void setBlockAmmount(int which, int ammount) {
		blockAmmount[which] = ammount;
		repaint();
	}
}
