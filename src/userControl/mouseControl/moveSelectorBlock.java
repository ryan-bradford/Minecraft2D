package userControl.mouseControl;

import java.awt.MouseInfo;

import main.main;

public class moveSelectorBlock extends Thread {
	
	public void run() {
		while(true) {
			try {
				moveSelectorBlock.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int playerBlock = (int)(main.getPlayer().getBounds().x/64);
			if(MouseInfo.getPointerInfo().getLocation().x > main.getPlayer().getBounds().x) {
				main.moveSelectorBlock((playerBlock + 2)*64, main.getPlayer().getBounds().y + (main.getPlayer().getHeight()-64));
			} else {
				main.moveSelectorBlock((playerBlock - 1) * 64, main.getPlayer().getBounds().y + (main.getPlayer().getHeight()-64));				
			}
		}
	}
	
}
