package physicsEngine;

import main.main;
import thread.task;

public class gravityTask extends task{
	
	public gravityTask() {
		
	}
	
	@Override
	public void runTask() {
		main.movePlayer(0, 1);		
	}
	
	@Override
	public Boolean returnRunnable() {
		if (main.map.physics.runnable == true) {
			if (main.getJumping() == false) {
				if (main.getCollisionBottom() == false) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public int getWait() {
		int gravitySpeed = main.getGravitySpeed(); //
		int wait = 1000/gravitySpeed;
		return wait;
	}
	
	@Override
	public int getCPULoad() {
		return 2;
	}
	
}
