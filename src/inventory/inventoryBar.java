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

	public inventoryBar(int inventoryBlock, int inventoryGap, int inventoryExtra) {
		setLayout(null);
		f = null;
		gaps = inventoryGap;
		blockNumber = inventoryBlock;
		rectangleWidth = main.blockHeight + inventoryExtra;
		files = new String[blockNumber];
		blockAmmount = new Integer[blockNumber];
		inventoryBarButtons = new JButton[blockNumber];
		for (int i = 0; i < blockNumber; i++) {
			blockAmmount[i] = 64;
			files[i] = "blank.jpg";
		}
		files[0] = "dirt.jpg";
		files[1] = "grass.jpg";
		selected = 0;
		height = (int) (rectangleWidth + (gaps) * 2);
		width = (rectangleWidth + gaps) * blockNumber + gaps;
		images = new ImageIcon[blockNumber];
	}

	@Override
	protected void paintComponent(Graphics g) {
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
			images[i] = new ImageIcon(files[i]);
			inventoryBarButtons[i] = new JButton(images[i]);
			inventoryBarButtons[i].setBounds(imageCornerX, imageCornerY,
					main.blockHeight, main.blockHeight);
			inventoryBarButtons[i].setOpaque(false);
			inventoryBarButtons[i].setContentAreaFilled(false);
			inventoryBarButtons[i].setBorderPainted(false);
			inventoryBarButtons[i].setFocusable(false);
			add(inventoryBarButtons[i]);
			if (!files[i].equals("blank.jpg")) {
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
