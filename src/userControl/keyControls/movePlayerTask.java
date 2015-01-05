package userControl.keyControls;

import java.util.ArrayList;

import block.block;
import main.main;
import thread.task;

public class movePlayerTask extends task { // The task that moves the player
	Boolean leftOrRight; // True is left Null is neither
	Boolean upOrDown; // True is up Null is neither
	int yAmmount = 0; // negative is up
	int xAmmount = 0; // negative is left

	public movePlayerTask() {

	}

	@Override
	public void runTask() { // Detirmines what direction to move the player in
							// and moves it that way
		leftOrRight = main.map.getLeftOrRight();
		upOrDown = main.map.getUpOrDown();
		if (upOrDown != null) {
			if (upOrDown == true) {
				yAmmount = +-1;
			} else {
				yAmmount = +1;
			}
		} else {
			yAmmount = 0;
		}
		if (leftOrRight != null) {
			if (leftOrRight == true) {
				xAmmount = +-1;
			} else {
				xAmmount = +1;
			}
		} else {
			xAmmount = 0;
		}
		main.movePlayer(xAmmount, yAmmount);
		if (main.map.player.getBounds().x > main.screenWidth) {
			Boolean canBeShifted = false;
			try {
				main.map.chunk.get(main.map.currentScreen + 1);

			} catch (IndexOutOfBoundsException ex) {
				canBeShifted = true;
			}
			if (canBeShifted == false) {
				int playerLowY = (int) (main.getPlayer().getBounds().y)
						+ (main.getPlayer().getHeight());
				ArrayList<block> testBlocks = main.getBlocks(playerLowY,
						main.map.currentScreen + 1);
				Boolean found = false;
				for (int i = 0; i < testBlocks.size(); i++) {
					if (testBlocks.get(i).getBounds().x == main.blockHeight) {
						found = true;
						break;
					}
				}
				if (found == false) {
					canBeShifted = true;
				}
			}
			if (canBeShifted == true) {
				main.map.changeCurrentScreen(main.map.currentScreen + 1);
				main.map.setPlayerPosition(false);
			}
		}
		if (main.map.player.getBounds().x < 0
				&& main.map.currentScreen - 1 >= 0) {
			Boolean canBeShifted = false;
			try {
				main.map.chunk.get(main.map.currentScreen - 1);

			} catch (IndexOutOfBoundsException ex) {
				canBeShifted = true;
			}
			if (canBeShifted == false) {
				int playerLowY = (int) (main.getPlayer().getBounds().y)
						+ (main.getPlayer().getHeight());
				ArrayList<block> testBlocks = main.getBlocks(playerLowY,
						main.map.currentScreen - 1);
				Boolean found = false;
				for (int i = 0; i < testBlocks.size(); i++) {
					if (testBlocks.get(i).getBounds().x == main.blockHeight) {
						found = true;
						break;
					}
				}
				if (found == false) {
					canBeShifted = true;
				}
			}
			if (canBeShifted == true) {
				main.map.changeCurrentScreen(main.map.currentScreen - 1);
				main.map.setPlayerPosition(true);
			}
		}
	}

	@Override
	public Boolean returnRunnable() {
		if (main.map.inventoryOpen == true) { // If the inventory is open, dont
												// run
			return false;
		}
		if (upOrDown != null) { // If the player is trying to move up, but
								// touching the top, dont move up
			if (upOrDown == true) {
				if (main.getCollisionTop() == true) {
					return false;
				}
			} else { // If the player is trying to move down, but touching the
						// bottom, dont move down
				if (main.getCollisionBottom() == true) {
					return false;
				}
			}
		}
		if (leftOrRight != null) { // Same rules as about apply
			if (leftOrRight == true) {
				if (main.getCollisionLeft() == true) {
					return false;
				}
			} else {
				if (main.getCollisionRight() == true) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public int getWait() {
		return 1000 / main.walkSpeed;
	}

	@Override
	public int getCPULoad() {
		return 1;
	}

}
