package userControl.mouseControl;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.main;

public class moveSelectorBlock extends Thread {
	public int selectorX;
	public int selectorRow;
	public int blockHeight;
	public int playerBlockX;
	public int playerX;
	public int playerHighY;
	public int playerLowY;
	public int playerMidY;
	public int playerLowBlockY;
	public int verticalShift;

	public void run() {
		while (true) {
			try {
				moveSelectorBlock.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
				selectorRow = (int) ((playerLowY) / blockHeight)
						+ verticalShift;
				selectorX = playerX - (playerX - blockHeight) % blockHeight; // Left
				main.moveSelectorBlock(selectorX, selectorRow * blockHeight);
			}
		}
	}
}
