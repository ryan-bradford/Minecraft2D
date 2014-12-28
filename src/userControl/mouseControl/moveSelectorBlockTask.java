package userControl.mouseControl;

import java.awt.MouseInfo;
import java.awt.Point;

import main.main;
import thread.task;

public class moveSelectorBlockTask extends task{
	public int selectorX;						//Moves it on a grid that has squares the size of blocks
	public int selectorRow;
	public int blockHeight;
	public int playerBlockX;
	public int playerX;
	public int playerHighY;
	public int playerLowY;
	public int playerMidY;
	public int playerLowBlockY;
	public int verticalShift;
	
	public moveSelectorBlockTask() {
		
	}
	
	@Override
	public void runTask() {
		verticalShift = main.getPlayer().getHeight() / main.blockHeight * 2;
		blockHeight = main.getBlockHeight();
		playerBlockX = (int) (main.getPlayer().getBounds().x / blockHeight);
		playerX = main.getPlayer().getBounds().x;
		playerHighY = main.getPlayer().getBounds().y;
		playerLowY = playerHighY - main.getPlayer().getHeight();
		playerMidY = playerLowY + main.getBlockHeight();
		playerLowBlockY = (int) (playerLowY / blockHeight) + 1;
		Point mouseInfo = MouseInfo.getPointerInfo().getLocation();
		if (mouseInfo.y < playerLowY + main.getPlayer().getHeight()*2
				&& mouseInfo.y > (playerLowY - blockHeight + main.getPlayer().getHeight()*2)) { // Middle
			if (mouseInfo.x > playerX) {
				selectorRow = (int) ((playerLowY - blockHeight) / blockHeight)
						+ verticalShift;
				selectorX = playerX - (playerX - blockHeight) % blockHeight
						+ blockHeight * 1; // Right
				main.moveSelectorBlock(selectorX, selectorRow * blockHeight);
			} else {
				selectorRow = (int) ((playerLowY - blockHeight) / blockHeight)
						+ verticalShift;
				selectorX = playerX - blockHeight - (playerX - blockHeight)
						% blockHeight; // Left
				main.moveSelectorBlock(selectorX, selectorRow * blockHeight);
			}
		} else if (mouseInfo.y <= playerLowY - blockHeight + main.getPlayer().getHeight()*2) { // Highest
			if (mouseInfo.x > playerX) {
				selectorRow = (int) ((playerLowY - blockHeight * 2) / blockHeight)
						+ verticalShift;
				selectorX = playerX - (playerX - blockHeight) % blockHeight
						+ blockHeight * 1; // Right
				main.moveSelectorBlock(selectorX, selectorRow * blockHeight);
			} else {
				selectorRow = (int) ((playerLowY - blockHeight * 2) / blockHeight)
						+ verticalShift;
				selectorX = playerX - blockHeight - (playerX - blockHeight)
						% blockHeight; // Left
				main.moveSelectorBlock(selectorX, selectorRow * blockHeight);
			}
		} else { // Lowest
			selectorRow = (int) ((playerLowY+40) / blockHeight)
					+ verticalShift;
			selectorX = playerX - (playerX - blockHeight) % blockHeight;
			main.moveSelectorBlock(selectorX, selectorRow * blockHeight);
		}
	}
	
	@Override
	public Boolean returnRunnable() {
		return true;
	}
	
	@Override
	public int getWait() {
		return 15;
	}
	
	@Override
	public int[] getData() {
		return new int[] {selectorX, selectorRow};
	}
	
}
