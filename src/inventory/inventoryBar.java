package inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import main.main;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class inventoryBar extends JPanel {
	public java.io.File f;
	public inventoryButton[] inventoryBarButtons;
	public Image[] images;
	public int gaps;
	public int blockNumber;
	public int rectangleWidth;
	public int width;
	public int height;
	public int selected;
	public Color defaultColor;
	public Color swapBoxColor;
	public Color selectedBoxColor;
	public Color backgroundColor;
	public Color textColor;
	public Integer switchedNum;

	public inventoryBar(int inventoryBlock, int inventoryGap,
			int inventoryExtra, Color defaultColor1, Color swapBoxColor1,
			Color selectedBoxColor1, Color backgroundColor1, Color textColor1) {
		setLayout(null);
		f = null;
		switchedNum = null;
		gaps = inventoryGap;
		defaultColor = defaultColor1;
		swapBoxColor = swapBoxColor1;
		selectedBoxColor = selectedBoxColor1;
		backgroundColor = backgroundColor1;
		textColor = textColor1;
		blockNumber = inventoryBlock;
		rectangleWidth = main.blockHeight + inventoryExtra;
		inventoryBarButtons = new inventoryButton[blockNumber];
		selected = 0;
		height = (int) (rectangleWidth + (gaps) * 2);
		width = (rectangleWidth + gaps) * blockNumber + gaps;
		images = new Image[blockNumber];
		for (int i = 0; i < blockNumber; i++) {
			try {
				inventoryBarButtons[i] = new inventoryButton(
						ImageIO.read(new java.io.File(
								main.getImageFileNames()[1])), 1, 1);
				images[i] = ImageIO.read(new java.io.File(main
						.getImageFileNames()[1]));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			inventoryBarButtons[i] = new inventoryButton(images[i], 1, 1);
			standardButtonAction(i);
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);
		for (int i = 0; i < blockNumber; i++) {
			if (i == selected) {
				g.setColor(selectedBoxColor);
			} else if (switchedNum != null && i == switchedNum) {
				g.setColor(swapBoxColor);
			} else {
				g.setColor(defaultColor);
			}
			g.drawRect((rectangleWidth + gaps) * i + gaps, height / 2
					- (rectangleWidth) / 2, rectangleWidth, rectangleWidth);

		}

	}

	public String setSelected(int i) {
		try {
			selected = i;
			repaint();
			return main.getImageFileNames()[inventoryBarButtons[selected]
					.getBlockID()];
		} catch (NullPointerException ex) {

		}
		return "blank.jpg";
	}

	public void setSwitch(int id, Boolean selected, Boolean inveotOrBar) { // True
																			// //
																			// inventory
		if (main.selected == false) {
			switchedNum = id;
			repaint();
		} else {
			switchedNum = null;
			repaint();
		}
	}

	public void setAsNewButton(int id) {
		remove(inventoryBarButtons[id]);
		inventoryBarButtons[id].setVisible(false);
		int amount = inventoryBarButtons[id].getAmount();
		int blockID = inventoryBarButtons[id].getBlockID();
		Image icon;
		try {
			icon = ImageIO.read(new java.io.File(main.getImageFileNames()[blockID]));
			inventoryBarButtons[id] = new inventoryButton(icon, amount, blockID);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		standardButtonAction(id);
	}

	public void removeButton(int id) {
		remove(inventoryBarButtons[id]);
		inventoryBarButtons[id].setVisible(false);
		try {
			inventoryBarButtons[id] = new inventoryButton(ImageIO.read(new java.io.File(main.getImageFileNames()[0])), 0, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		standardButtonAction(id);

	}

	public void standardButtonAction(int id) {
		int imageCornerX = (rectangleWidth + gaps) * id + gaps
				+ (rectangleWidth - main.blockHeight) / 2;
		int imageCornerY = height / 2 - (rectangleWidth) / 2
				+ (rectangleWidth - main.blockHeight) / 2;
		inventoryBarButtons[id].setBounds(imageCornerX, imageCornerY,
				main.blockHeight, main.blockHeight);
		inventoryBarButtons[id].setOpaque(false);
		inventoryBarButtons[id].setContentAreaFilled(false);
		inventoryBarButtons[id].setBorderPainted(false);
		inventoryBarButtons[id].setFocusable(false);
		inventoryBarButtons[id].addActionListener(new buttonListener(id, 0,
				false));
		add(inventoryBarButtons[id]);
	}
}
