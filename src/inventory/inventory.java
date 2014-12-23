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
	public inventoryButton[][] inventoryButtons;
	public java.io.File f;
	public int gaps;
	public int blockNumber;
	public int inventoryHeight;
	public int rectangleWidth;
	public int width;
	public int height;
	public int selected;
	public Color defaultColor;
	public Color swapBoxColor;
	public Color backgroundColor;
	public Color textColor;
	public Integer switchedNumX;
	public Integer switchedNumY;
	
	public inventory(int inventoryBlock, int inventoryGap, int inventoryExtra,
			int inventoryHeight1, Color defaultColor1, Color swapBoxColor1,
			Color backgroundColor1, Color textColor1) {
		setLayout(null);
		f = null;
		switchedNumX = null;
		switchedNumY = null;
		defaultColor = defaultColor1;
		swapBoxColor = swapBoxColor1;
		backgroundColor = backgroundColor1;
		textColor = textColor1;
		gaps = inventoryGap;
		blockNumber = inventoryBlock;
		inventoryHeight = inventoryHeight1;
		rectangleWidth = main.blockHeight + inventoryExtra;
		images = new BufferedImage[blockNumber];
		inventoryButtons = new inventoryButton[blockNumber][inventoryHeight];
		selected = 0;
		height = (rectangleWidth + gaps) * (inventoryHeight) + gaps;
		width = (rectangleWidth + gaps) * blockNumber + gaps;
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);
		g.setColor(defaultColor);
		for (int x = 0; x < blockNumber; x++) {
			for (int y = 0; y < inventoryHeight; y++) {
				if(switchedNumX != null && switchedNumY != null && switchedNumX == x&& switchedNumY == y) {
					g.setColor(swapBoxColor);
				} else {
					g.setColor(defaultColor);
				}
				g.drawRect((rectangleWidth + gaps) * (x) + gaps,
						(rectangleWidth + gaps) * (y) + gaps, rectangleWidth,
						rectangleWidth);
				inventoryButtons[x][y] = new inventoryButton(new ImageIcon(main.getImageFileNames()[2]), 64, 2);
				inventoryButtons[x][y].setBounds((rectangleWidth + gaps) * (x)
						+ gaps, (rectangleWidth + gaps) * (y) + gaps,
						rectangleWidth, rectangleWidth);
				inventoryButtons[x][y].setOpaque(false);
				inventoryButtons[x][y].setContentAreaFilled(false);
				inventoryButtons[x][y].setBorderPainted(false);
				inventoryButtons[x][y].setFocusable(false);
				inventoryButtons[x][y].addActionListener(new buttonListener(x, y, true));
				add(inventoryButtons[x][y]);

			}
		}
	}
	
	public void setSwitch(int idX, int idY, Boolean selected, Boolean inveotOrBar) {//True is inventory
		if(main.selected == false) {
			 switchedNumX = idX;
			 switchedNumY = idY;
			 repaint();
		} else {
			switchedNumX = null;
			switchedNumY = null;
			repaint();
		}
	}
}
