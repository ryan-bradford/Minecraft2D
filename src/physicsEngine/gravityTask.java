package physicsEngine;

import main.main;

import thread.task;

public class gravityTask extends task {
	
	public gravityTask() {
		
	}
	
	@Override
	public void runTask() {
		main.movePlayer(0, 1);		
	}
	
	@Override
	public Boolean returnRunnable() {
		if (main.map.physics.runnable == true) { //If the physics is running
			if (main.getJumping() == false) {	//If the player is not jumping
				if (main.getCollisionBottom() == false) { //If the player is not touching the ground
					return true; //It is runnable
				}
			}
		}
		return false;
	}
	
	@Override
	public int getWait() { //Returns the wait
		int gravitySpeed = main.getGravitySpeed(); //
		int wait = 1000/gravitySpeed;
		return wait;
	}
	
	@Override
	public int getCPULoad() { //Returns the load
		return 2;
	}
	
	@Override
	public int[] getData() {
		return new int[] {3};
	}
	
}
