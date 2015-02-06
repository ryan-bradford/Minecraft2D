package userControl.mouseControl;

import java.awt.MouseInfo;
import java.awt.Point;

import main.main;
import thread.task;

public class moveSelectorBlockTask extends task {
	public int selectorRowX; // Moves it on a grid that has squares the size of
								// blocks
	public int selectorRowY;
	public int blockHeight;
	public int playerBlockX;
	public int playerX;
	public int playerHighY;
	public int playerLowY;
	public int playerMidY;
	public int playerLowBlockY;
	public int verticalShift;
	public int playerXRow;
	public int playerYRow;
	public int mouseXRow;

	public moveSelectorBlockTask() {

	}

	@Override
	public void runTask() { // Same code as before to set the mouse on a grid
							// and find where to place the blue outline box
		verticalShift = main.getPlayer().getHeight() / main.blockHeight * 2;
		blockHeight = main.getBlockHeight();
		playerBlockX = (int) (main.getPlayer().getBounds().x / blockHeight);
		playerX = main.getPlayer().getBounds().x;
		playerXRow = playerX / blockHeight;
		playerYRow = playerHighY / blockHeight;
		playerHighY = main.getPlayer().getBounds().y;
		playerLowY = playerHighY - main.getPlayer().getHeight();
		playerMidY = playerLowY + main.getBlockHeight();
		playerLowBlockY = (int) (playerLowY / blockHeight) + 1;
		Point mouseInfo = MouseInfo.getPointerInfo().getLocation();
		mouseXRow = mouseInfo.x / blockHeight;
		if (mouseInfo.y < playerLowY + main.getPlayer().getHeight() * 2
				&& mouseInfo.y > (playerLowY - blockHeight + main.getPlayer()
						.getHeight() * 2)) { // Middle
			if (mouseXRow > playerXRow) {
				selectorRowY = playerYRow + 1;
				selectorRowX = (playerXRow + 1); // Right
				main.moveSelectorBlock(selectorRowX * blockHeight, selectorRowY
						* blockHeight);
			} else {
				selectorRowY = playerYRow + 1;
				selectorRowX = (playerXRow - 1); // Left
				main.moveSelectorBlock(selectorRowX * blockHeight, selectorRowY
						* blockHeight);
			}
		} else if (mouseInfo.y <= playerLowY - blockHeight
				+ main.getPlayer().getHeight() * 2) { // Highest
			if (mouseXRow > playerXRow) {
				selectorRowY = playerYRow;
				selectorRowX = (playerXRow + 1); // Right
				main.moveSelectorBlock(selectorRowX * blockHeight, selectorRowY
						* blockHeight);
			} else {
				selectorRowY = playerYRow;
				selectorRowX = (playerXRow - 1); // Left
				main.moveSelectorBlock(selectorRowX * blockHeight, selectorRowY
						* blockHeight);
			}
		} else { // Lowest
			selectorRowY = playerYRow + 2;
			selectorRowX = (playerXRow); // Left
			main.moveSelectorBlock(selectorRowX * blockHeight, selectorRowY
					* blockHeight);
		}
	}

	@Override
	public Boolean returnRunnable() { // Always runnable(Stops when the inveory
										// is open somehow....(Don't rememmber
										// how I did that))
		return true;
	}

	@Override
	public int getWait() { // Checks every 15 seconds
		return 15;
	}

	@Override
	public int[] getData() {
		return new int[] { selectorRowX, selectorRowY };
	}

	@Override
	public int getCPULoad() {
		return 2;
	}

}
