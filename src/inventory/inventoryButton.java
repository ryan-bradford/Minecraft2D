package inventory;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class inventoryButton extends JButton {
	public Integer amount;
	public Integer blockID;
	public inventoryButton(ImageIcon image, Integer amount1, Integer blockID1) {
		super(image);
		amount = amount1;
		blockID = blockID1;
	}
	
	public int addBlock(int add, int blockID1) {
		int space = 64-amount;
		int extra = add-space;
		if(blockID == 0) {
			amount = add;
			blockID = blockID1;
		} else if(blockID == blockID1) {
			if(add >= space) {
				amount = amount + add;
			} else {
				
			}
		}
		if(extra > 0) {
			return extra;
		}
			
		return 0;
	}
}
