package inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import main.main;

public class inventory extends JPanel {
	private BufferedImage[] images;
	public String[][] files;
	public Integer[][] blockAmmount;
	public JButton[][] inventoryButtons;
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
		setLayout(null);
		f = null;
		gaps = inventoryGap;
		blockNumber = inventoryBlock;
		inventoryHeight = inventoryHeight1;
		rectangleWidth = main.blockHeight + inventoryExtra;
		images = new BufferedImage[blockNumber];
		files = new String[blockNumber][inventoryHeight];
		inventoryButtons = new JButton[blockNumber][inventoryHeight];
		blockAmmount = new Integer[blockNumber][inventoryHeight];
		selected = 0;
		height = (rectangleWidth + gaps) * (inventoryHeight) + gaps;
		width = (rectangleWidth + gaps) * blockNumber + gaps;
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.fillRect(0, 0, width, height);
		g.setColor(Color.gray);
		for (int x = 0; x < blockNumber; x++) {
			for (int y = 0; y < inventoryHeight; y++) {
				g.drawRect((rectangleWidth + gaps) * (x) + gaps,
						(rectangleWidth + gaps) * (y) + gaps, rectangleWidth,
						rectangleWidth);
				blockAmmount[x][y] = new Integer(64);
				files[x][y] = "blank.jpg";
				inventoryButtons[x][y] = new JButton(new ImageIcon(files[x][y]));
				inventoryButtons[x][y].setBounds((rectangleWidth + gaps) * (x) + gaps,
						(rectangleWidth + gaps) * (y) + gaps, rectangleWidth,
						rectangleWidth);
				inventoryButtons[x][y].setOpaque(false);
				inventoryButtons[x][y].setContentAreaFilled(false);
				inventoryButtons[x][y].setBorderPainted(false);
				inventoryButtons[x][y].setFocusable(false);
				add(inventoryButtons[x][y]);
				
			}
		}
	}
}
