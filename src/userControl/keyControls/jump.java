package userControl.keyControls;

import main.main;

public class jump extends Thread {
	Boolean running = true;

	public void run() { //When the player jumps, moves him at "jump speed" upwards
		for (int i = 0; i < (int ) (main.getJumpDistance() * main.getBlockHeight()); i++) { 
			try {
				jump.sleep((long) (1000 / main.getJumpSpeed()));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (main.getCollisionTop() == false) {
				main.movePlayer(0, -1);
			}
		}
		main.doneJumping();
	}
}
