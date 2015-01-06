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
		Boolean canBeMoved = true;
		int whichDirectionToMove = 0;
		Boolean endOrBegining = null; // True is end
		int currentScreen = main.map.currentScreen;
		if (main.getPlayer().getBounds().x + xAmmount <= 0
				&& currentScreen - 1 >= 0) {
			whichDirectionToMove = -1;
			endOrBegining = true;
		} else if (main.getPlayer().getBounds().x + xAmmount >= main.screenWidth) {
			whichDirectionToMove = 1;
			endOrBegining = false;
		}
		if (currentScreen - 1 < 0
				&& main.getPlayer().getBounds().x + xAmmount <= 0) {
			canBeMoved = false;
		}

//		if (canBeMoved == true && endOrBegining != null) {
//			ArrayList<block> toCheck;
//			toCheck = main.getBlocks(main.getPlayer().getBounds().y, currentScreen
//					+ whichDirectionToMove);
//			for (int i = 0; i < toCheck.size(); i++) {
//				if (endOrBegining == false) {
//					if (toCheck.get(i).getBounds().x == main.screenWidth
//							- main.blockHeight) {
//						canBeMoved = false;
//					}
//				} else {
//					if (toCheck.get(i).getBounds().x == main.blockHeight) {
//						System.out.println("Found falsedddddd");
//						canBeMoved = false;
//						
//					}					
//				}
//			}
//		}
//		System.out.println(canBeMoved);
//		System.out.println(whichDirectionToMove);
//		System.out.println(endOrBegining);
		if (canBeMoved == true) {
			main.movePlayer(xAmmount, yAmmount);
			if (endOrBegining != null) {
				main.map.changeCurrentScreen(main.map.currentScreen
						+ whichDirectionToMove);
				main.map.setPlayerPosition(endOrBegining);
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
