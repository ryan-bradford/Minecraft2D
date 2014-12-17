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

	public void run() {
		while (true) {
			try {
				moveSelectorBlock.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			blockHeight = main.getBlockHeight();
			playerBlockX = (int) (main.getPlayer().getBounds().x / blockHeight);
			playerX = main.getPlayer().getBounds().x;
			playerHighY = main.getPlayer().getBounds().y;
			playerLowY = playerHighY - main.getPlayer().getHeight();
			playerMidY = playerLowY + main.getBlockHeight();
			playerLowBlockY = (int) (playerLowY / blockHeight) + 1;
			Point mouseInfo = MouseInfo.getPointerInfo().getLocation();
			if (mouseInfo.y < playerLowY + 4 * blockHeight
					&& mouseInfo.y > (playerLowY - blockHeight + 4 * blockHeight)) { // Middle
				if (mouseInfo.x > playerX) {
					selectorRow = (int) ((playerLowY - blockHeight) / 64) + 4;
					selectorX = playerX - (playerX - blockHeight) % 64
							+ blockHeight * 2; // Right
					main.moveSelectorBlock(selectorX, selectorRow * 64);
				} else {
					selectorRow = (int) ((playerLowY - blockHeight) / 64) + 4;
					selectorX = playerX - blockHeight - (playerX - blockHeight)
							% 64; // Left
					main.moveSelectorBlock(selectorX, selectorRow * 64);
				}
			} else if (mouseInfo.y <= playerLowY - blockHeight + 4
					* blockHeight) { // Highest
				if (mouseInfo.x > playerX) {
					selectorRow = (int) ((playerLowY - blockHeight * 2) / 64) + 4;
					selectorX = playerX - (playerX - blockHeight) % 64
							+ blockHeight * 2; // Right
					main.moveSelectorBlock(selectorX, selectorRow * 64);
				} else {
					selectorRow = (int) ((playerLowY - blockHeight * 2) / 64) + 4;
					selectorX = playerX - blockHeight - (playerX - blockHeight)
							% 64; // Left
					main.moveSelectorBlock(selectorX, selectorRow * 64);
				}
			} else { // Lowest
				selectorRow = (int) ((playerLowY) / 64) + 4;
				selectorX = playerX - (playerX - blockHeight)
						% 64; // Left
				main.moveSelectorBlock(selectorX, selectorRow * 64);
			}
		}
	}
}
