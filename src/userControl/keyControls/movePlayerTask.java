package userControl.keyControls;

import main.main;
import thread.task;

public class movePlayerTask extends task{
	Boolean leftOrRight; // True is left Null is neither
	Boolean upOrDown; // True is up Null is neither
	int yAmmount = 0; // negative is up
	int xAmmount = 0; // negative is left
	public movePlayerTask() {
		
	}
	
	@Override
	public void runTask() {
		leftOrRight = main.map.getLeftOrRight();
		upOrDown = main.map.getUpOrDown();
		if (upOrDown != null ) {
			if (upOrDown == true) {
				yAmmount=+ -1;
			} else {
				yAmmount=+ 1;
			}
		} else {
			yAmmount = 0;
		}
		if (leftOrRight != null) {
			if (leftOrRight == true) {
				xAmmount=+ -1;
			} else {
				xAmmount=+ 1;
			}
		} else {
			xAmmount = 0;
		}
		main.movePlayer(xAmmount, yAmmount);
	}
	
	@Override
	public Boolean returnRunnable() {
		if(main.map.inventoryOpen == true) {
			return false;
		}
		if (upOrDown != null) {
			if (upOrDown == true) {
				if (main.getCollisionTop() == true) {
					 return false;
				}
			} else {
				if(main.getCollisionBottom() == true) {
					 return false;
				}
			}
		}
		if (leftOrRight != null) {
			if (leftOrRight == true) {
				if (main.getCollisionLeft() == true) {
					 return false;
				}
			} else {
				if(main.getCollisionRight() == true) {
					 return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public int getWait() {
		return 15;
	}
	
	@Override
	public int getCPULoad() {
		return 1;
	}
	
}
