package userControl.keyControls;

import main.main;

public class movePlayer extends Thread {
	Boolean running = true;
	Boolean leftOrRight; // True is left Null is neither
	Boolean upOrDown; // True is up Null is neither
	int yAmmount = 0; // negative is up
	int xAmmount = 0; // negative is left

	public movePlayer(Boolean leftOrRight1, Boolean UpOrDown1) {
		leftOrRight = leftOrRight1;
		upOrDown = UpOrDown1;
		if (upOrDown != null ) {
			if (upOrDown == true) {
				yAmmount = -1;
			} else {
				yAmmount = 1;
			}
		}
		if (leftOrRight != null) {
			if (leftOrRight == true) {
				xAmmount = -1;
			} else {
				xAmmount = 1;
			}
		}
	}

	public void run() {
		while (running == true) {
			try {
				movePlayer.sleep(1000 / main.getWalkSpeed());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("Move Player");
			if (upOrDown != null) {
				if (upOrDown == true) {
					if (main.getCollisionTop() == true) {
						running = false;
					}
				} else {
					if(main.getCollisionBottom() == true) {
						running = false;
					}
				}
			}
			if (leftOrRight != null) {
				if (leftOrRight == true) {
					if (main.getCollisionLeft() == true) {
						running = false;
					}
				} else {
					if(main.getCollisionRight() == true) {
						running = false;
					}
				}
			}
			if (running == true) {
				main.movePlayer(xAmmount, yAmmount);
			}
		}
	}

	public Boolean getRunning() {
		return running;
	}
}
