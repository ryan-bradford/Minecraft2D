package inventory;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.main;

public class inventory extends JPanel { // Same as the inventoryBar, except this has a height and a width
	public inventoryButton[][] inventoryButtons; // Also, it has no placement selector box
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
	public int stackHeight;

	public inventory(int inventoryBlock, int inventoryGap, int inventoryExtra, int inventoryHeight1, Color defaultColor1, Color swapBoxColor1,
			Color backgroundColor1, Color textColor1, int stackHeight1) {
		setLayout(null);
		f = null;
		stackHeight = stackHeight1;
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
		inventoryButtons = new inventoryButton[blockNumber][inventoryHeight];
		selected = 0;
		height = (rectangleWidth + gaps) * (inventoryHeight) + gaps;
		width = (rectangleWidth + gaps) * blockNumber + gaps;
		for (int x = 0; x < blockNumber; x++) {
			for (int y = 0; y < inventoryHeight; y++) {
				try {
					inventoryButtons[x][y] = new inventoryButton(ImageIO.read(new java.io.File(main.getImageFileNames()[0])), 0, 0, stackHeight);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				standardButtonAction(x, y);
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);
		g.setColor(defaultColor);
		for (int x = 0; x < blockNumber; x++) {
			for (int y = 0; y < inventoryHeight; y++) {
				if (switchedNumX != null && switchedNumY != null && switchedNumX == x && switchedNumY == y) {
					g.setColor(swapBoxColor);
				} else {
					g.setColor(defaultColor);
				}
				g.drawRect((rectangleWidth + gaps) * (x) + gaps, (rectangleWidth + gaps) * (y) + gaps, rectangleWidth, rectangleWidth);
			}
		}
	}

	public void setSwitch(int idX, int idY, Boolean selected, Boolean inveotOrBar) {// True is inventory
		if (main.selected == false) {
			switchedNumX = idX;
			switchedNumY = idY;
			repaint();
		} else {
			switchedNumX = null;
			switchedNumY = null;
			repaint();
		}
	}

	public void repaintButton(int idX, int idY) {
		System.out.println("repainting");
		remove(inventoryButtons[idX][idY]);
		inventoryButtons[idX][idY].setVisible(false);
		int amount = inventoryButtons[idX][idY].getAmount();
		int blockID = inventoryButtons[idX][idY].getBlockID();
		Image icon;
		try {
			icon = ImageIO.read(new java.io.File(main.getImageFileNames()[blockID]));
			inventoryButtons[idX][idY] = new inventoryButton(icon, amount, blockID, stackHeight);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		standardButtonAction(idX, idY);
		repaint();
	}

	public void removeButton(int idX, int idY) {
		remove(inventoryButtons[idX][idY]);
		inventoryButtons[idX][idY].setVisible(false);
		try {
			inventoryButtons[idX][idY] = new inventoryButton(ImageIO.read(new java.io.File(main.getImageFileNames()[0])), 0, 0, stackHeight);
		} catch (IOException e) {

			e.printStackTrace();
		}
		standardButtonAction(idX, idY);

	}

	public void standardButtonAction(int idX, int idY) {
		inventoryButtons[idX][idY].setBounds((rectangleWidth + gaps) * (idX) + gaps + (rectangleWidth - main.blockHeight) / 2,
				(rectangleWidth + gaps) * (idY) + gaps + (rectangleWidth - main.blockHeight) / 2, rectangleWidth, rectangleWidth);
		inventoryButtons[idX][idY].setOpaque(false);
		inventoryButtons[idX][idY].setContentAreaFilled(false);
		inventoryButtons[idX][idY].setBorderPainted(false);
		inventoryButtons[idX][idY].setFocusable(false);
		inventoryButtons[idX][idY].addActionListener(new buttonListener(idX, idY, true));
		add(inventoryButtons[idX][idY]);
	}
}