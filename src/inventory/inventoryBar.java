package inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.main;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class inventoryBar extends JPanel {
	public String[] files;
	public Integer[] blockAmmount;
	public java.io.File f;
	public JButton[] inventoryBarButtons;
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

	public inventoryBar(int inventoryBlock, int inventoryGap, int inventoryExtra, Color defaultColor1, Color swapBoxColor1, Color selectedBoxColor1,
			Color backgroundColor1, Color textColor1) {
		setLayout(null);
		f = null;
		gaps = inventoryGap;
		defaultColor = defaultColor1;
		swapBoxColor = swapBoxColor1;
		selectedBoxColor = selectedBoxColor1;
		backgroundColor = backgroundColor1;
		textColor = textColor1;
		blockNumber = inventoryBlock;
		rectangleWidth = main.blockHeight + inventoryExtra;
		files = new String[blockNumber];
		blockAmmount = new Integer[blockNumber];
		inventoryBarButtons = new JButton[blockNumber];
		for (int i = 0; i < blockNumber; i++) {
			blockAmmount[i] = 64;
			files[i] = main.getImageFileNames()[0];
		}
		files[0] = main.getImageFileNames()[1];
		files[1] = main.getImageFileNames()[2];
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
			} else {
				g.setColor(defaultColor);
			}
			g.drawRect((rectangleWidth + gaps) * i + gaps, height / 2
					- (rectangleWidth) / 2, rectangleWidth, rectangleWidth);
			int imageCornerX = (rectangleWidth + gaps) * i + gaps
					+ (rectangleWidth - main.blockHeight) / 2;
			int imageCornerY = height / 2 - (rectangleWidth) / 2
					+ (rectangleWidth - main.blockHeight) / 2;
			images[i] = new ImageIcon(files[i]);
			inventoryBarButtons[i] = new JButton(images[i]);
			inventoryBarButtons[i].setBounds(imageCornerX, imageCornerY,
					main.blockHeight, main.blockHeight);
			inventoryBarButtons[i].setOpaque(false);
			inventoryBarButtons[i].setContentAreaFilled(false);
			inventoryBarButtons[i].setBorderPainted(false);
			inventoryBarButtons[i].setFocusable(false);
			inventoryBarButtons[i].addActionListener(new buttonListener(i, 0, false));
			add(inventoryBarButtons[i]);
			if (!files[i].equals(main.getImageFileNames()[0])) {
				g.setFont(new Font("TimesRoman", Font.BOLD, 15));
				g.setColor(textColor);
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
