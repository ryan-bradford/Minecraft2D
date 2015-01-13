package inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import main.main;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class inventoryBar extends JPanel { // This is the bar you see on the bottom of the screen
	public inventoryButton[] inventoryBarButtons; // The buttons on the bar
	public Image[] images; // The image files that will be drawn on the buttons
	public int gaps; // The gap between two selection areas(boxes) on the bar (In pixels)
	public int blockNumber; // The amount of block spots on the bar
	public int rectangleWidth; // The width of a single box
	public int width; // The width of the whole bar
	public int height; // The height of the whole bar
	public int selected; // The int that shows which box is the one being selected for placement
	public Color defaultColor; // The color of the boxes
	public Color swapBoxColor; // The color of the box that is used to show which box has been clicked on for movment in the inventory
	public Color selectedBoxColor; //The color of the box that is used to show which box has been selected for placment
	public Color backgroundColor; //The color of the background
	public Color textColor; //The color of the text(As of now is not used)
	public Integer switchedNum; //The int that shows which box has been clicked on
	public int stackHeight;

	public inventoryBar(int inventoryBlock, int inventoryGap, int inventoryExtra,
			Color defaultColor1, Color swapBoxColor1, Color selectedBoxColor1,
			Color backgroundColor1, Color textColor1, int stackHeight1) {
		setLayout(null); //Allows for boxes to be moved
		switchedNum = null;
		stackHeight = stackHeight1;
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
				inventoryBarButtons[i] = new inventoryButton(ImageIO.read(new java.io.File(main
						.getImageFileNames()[0])), 0, 0, stackHeight, textColor);
				images[i] = ImageIO.read(new java.io.File(main.getImageFileNames()[0]));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			inventoryBarButtons[i] = new inventoryButton(images[i], 0, 0, stackHeight, textColor);
			standardButtonAction(i);
		}

	}

	@Override
	protected void paintComponent(Graphics g) { //Draws the layout of the bar
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
			g.drawRect((rectangleWidth + gaps) * i + gaps, height / 2 - (rectangleWidth) / 2,
					rectangleWidth, rectangleWidth);

		}

	}

	public String setSelected(int i) { //Sets which box to be selected for placement
		try {
			selected = i;
			repaint();
			return main.getImageFileNames()[inventoryBarButtons[selected].getBlockID()];
		} catch (NullPointerException ex) {

		}
		return "blank.jpg";
	}

	public void setSwitch(int id, Boolean selected, Boolean inveotOrBar) { //Sets which block is clicked on for movement in the inventory
		if (main.selected == false) {
			switchedNum = id;
			repaint();
		} else {
			switchedNum = null;
			repaint();
		}
	}

	public void repaintButton(int id) { //Rapaints the button
		remove(inventoryBarButtons[id]);
		inventoryBarButtons[id].setVisible(false);
		int amount = inventoryBarButtons[id].getAmount();
		int blockID = inventoryBarButtons[id].getBlockID();
		Image icon;
		try {
			icon = ImageIO.read(new java.io.File(main.getImageFileNames()[blockID]));
			inventoryBarButtons[id] = new inventoryButton(icon, amount, blockID, stackHeight, textColor);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		standardButtonAction(id);
		repaint();
	}

	public void removeButton(int id) { //Sets a button to be blank
		remove(inventoryBarButtons[id]);
		inventoryBarButtons[id].setVisible(false);
		try {
			inventoryBarButtons[id] = new inventoryButton(ImageIO.read(new java.io.File(main
					.getImageFileNames()[0])), 0, 0, stackHeight, textColor);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		standardButtonAction(id);

	}

	public void standardButtonAction(int id) { //Is the action used on all buttons to draw them
		int imageCornerX = (rectangleWidth + gaps) * id + gaps
				+ (rectangleWidth - main.blockHeight) / 2;
		int imageCornerY = height / 2 - (rectangleWidth) / 2 + (rectangleWidth - main.blockHeight)
				/ 2;
		inventoryBarButtons[id].setBounds(imageCornerX, imageCornerY, main.blockHeight,
				main.blockHeight);
		inventoryBarButtons[id].setOpaque(false);
		inventoryBarButtons[id].setContentAreaFilled(false);
		inventoryBarButtons[id].setBorderPainted(false);
		inventoryBarButtons[id].setFocusable(false);
		inventoryBarButtons[id].addActionListener(new buttonListener(id, 0, false));
		add(inventoryBarButtons[id]);
	}
}