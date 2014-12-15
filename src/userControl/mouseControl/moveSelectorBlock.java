package userControl.mouseControl;

import java.awt.MouseInfo;
import java.awt.Point;

import main.main;

public class moveSelectorBlock extends Thread {
	public void run() {
		while (true) {
			try {
				moveSelectorBlock.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int blockHeight = main.getBlockHeight();
			int playerBlockX = (int) (main.getPlayer().getBounds().x / blockHeight);
			int playerX = main.getPlayer().getBounds().x;
			int playerHighY = main.getPlayer().getBounds().y;
			int playerLowY = playerHighY - main.getPlayer().getHeight();
			int playerMidY = playerLowY + 4* main.getBlockHeight();
			int playerLowBlockY = (int) (playerLowY / blockHeight) + 1;		
			Point mouseInfo = MouseInfo.getPointerInfo().getLocation();
			if (mouseInfo.x > playerX) {
				if (mouseInfo.y > playerMidY) {
					main.moveSelectorBlock((playerBlockX + 2) * blockHeight, (playerLowBlockY+3) * 64);
				} else {
					main.moveSelectorBlock((playerBlockX + 2) * blockHeight, (playerLowBlockY + 2) * 64);
				}
			} else {
				if (mouseInfo.y > playerMidY) {
					main.moveSelectorBlock(
							(playerBlockX - 1) * blockHeight,
							(playerLowBlockY+3) * 64);
				} else {
					main.moveSelectorBlock(
							(playerBlockX - 1) * blockHeight,
							(playerLowBlockY+2) * 64);
				}
			}
		}
	}

}
