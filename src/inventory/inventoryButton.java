package inventory;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class inventoryButton extends JButton {
	private int amount = 0;
	private int blockID;
	public double serialID;

	public inventoryButton(ImageIcon image, int amount1, Integer blockID1) {
		super(image);
		serialID = Math.random();
		amount = amount1;
		blockID = blockID1;
	}

	public int addBlock(int add, int blockID1) {
		int space = 64 - amount;
		int extra = add - space;
		if (blockID == 0) {
			amount = add;
			blockID = blockID1;
		} else if (blockID == blockID1) {
			if (add >= space && amount + add <= 64) {
				this.amount = amount + add;
			} else {

			}
		}
		if (extra > 0) {
			return extra;
		}
		return 0;
	}
	
	public void setValue(int value, int blockID1) {
		amount = value;
		blockID = blockID1;
	}

	public int getBlockID() {
		return blockID;
	}

	public int getAmount() {
		return amount;
	}
	
	public void subtractOne() {
		amount--;
	}
}
