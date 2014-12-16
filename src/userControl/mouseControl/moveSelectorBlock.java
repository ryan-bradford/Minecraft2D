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
			playerMidY = playerLowY + 4 * main.getBlockHeight();
			playerLowBlockY = (int) (playerLowY / blockHeight) + 1;
			Point mouseInfo = MouseInfo.getPointerInfo().getLocation();
			if (mouseInfo.x > playerX) {
				if (mouseInfo.y > playerMidY) {
					main.moveSelectorBlock((playerBlockX + 2) * blockHeight,
							(playerLowBlockY + 3) * 64);
					selectorRow = (playerLowBlockY + 3);
					selectorX = (playerBlockX + 2)*64;
				} else {
					main.moveSelectorBlock((playerBlockX + 2) * blockHeight,
							(playerLowBlockY + 2) * 64);
					selectorRow = (playerLowBlockY + 2);
					selectorX = (playerBlockX + 2)*64;
				}
			} else {
				if (mouseInfo.y > playerMidY) {
					main.moveSelectorBlock((playerBlockX - 1) * blockHeight,
							(playerLowBlockY + 3) * 64);
					selectorRow = (playerLowBlockY+3);
					selectorX = (playerBlockX - 1)*64;
				} else {
					main.moveSelectorBlock((playerBlockX - 1) * blockHeight,
							(playerLowBlockY + 2) * 64);
					selectorRow = (playerLowBlockY+2);
					selectorX = (playerBlockX - 1)*64;
				}
			}
		}
	}
}
