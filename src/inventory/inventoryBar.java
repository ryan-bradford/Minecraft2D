package inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.main;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class inventoryBar extends JPanel {
	public java.io.File f;
	public inventoryButton[] inventoryBarButtons;
	public ImageIcon[] images;
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

	public inventoryBar(int inventoryBlock, int inventoryGap, int inventoryExtra, Color defaultColor1, Color swapBoxColor1, Color selectedBoxColor1,
			Color backgroundColor1, Color textColor1) {
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
		images = new ImageIcon[blockNumber];
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);
		for (int i = 0; i < blockNumber; i++) {
			if (i == selected) {
				g.setColor(selectedBoxColor);
			} else if(switchedNum != null && i == switchedNum){
				g.setColor(swapBoxColor);
			} else {
				g.setColor(defaultColor);
			}
			g.drawRect((rectangleWidth + gaps) * i + gaps, height / 2
					- (rectangleWidth) / 2, rectangleWidth, rectangleWidth);
			int imageCornerX = (rectangleWidth + gaps) * i + gaps
					+ (rectangleWidth - main.blockHeight) / 2;
			int imageCornerY = height / 2 - (rectangleWidth) / 2
					+ (rectangleWidth - main.blockHeight) / 2;
			inventoryBarButtons[i] = new inventoryButton(images[i], 0, 0);
			images[i] = new ImageIcon(main.getImageFileNames()[0]);
			inventoryBarButtons[i] = new inventoryButton(images[i], 0, 0);
			inventoryBarButtons[i].setBounds(imageCornerX, imageCornerY,
					main.blockHeight, main.blockHeight);
			inventoryBarButtons[i].setOpaque(false);
			inventoryBarButtons[i].setContentAreaFilled(false);
			inventoryBarButtons[i].setBorderPainted(false);
			inventoryBarButtons[i].setFocusable(false);
			inventoryBarButtons[i].addActionListener(new buttonListener(i, 0, false));
			add(inventoryBarButtons[i]);
			if (inventoryBarButtons[i].blockID != 0) {
				g.setFont(new Font("TimesRoman", Font.BOLD, 15));
				g.setColor(textColor);
				g.drawString(inventoryBarButtons[i].amount.toString(), imageCornerX + 5,
						imageCornerY + main.blockHeight - 5);
			}
		}

	}

	public String setSelected(int i) {
		try {
		selected = i;
		repaint();
		return main.getImageFileNames()[inventoryBarButtons[selected].blockID];
		} catch(NullPointerException ex) {
			
		}
		return "blank.jpg";
	}

	public void setBlockAmmount(int which, int ammount) {
		inventoryBarButtons[which].amount = ammount;
		repaint();
	}
	
	public void setSwitch(int id, Boolean selected, Boolean inveotOrBar) { //True is inventory
		if(main.selected == false) {
			 switchedNum = id;
			 repaint();
		} else {
			switchedNum = null;
			repaint();
		}
	}
	
}
