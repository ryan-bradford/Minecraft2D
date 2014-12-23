package userControl.keyControls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.main;

public class keyControls implements KeyListener { //The thing that controls the keys
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W && main.getCollisionTop() == false && main.getInventoryState() == false) { 
			if (main.getCreative() == false) {	//Checks colision and moves also, if the player is in creative, he will not jump, but fly
				if (main.getJumpingObject() == null
						&& main.getJumping() == false
						&& main.getCollisionBottom() == true) {
					main.startjumping();
				}
			} else {
				if (main.getMoveUp() != null) {
					if (main.getMoveUp().getRunning() == false) {
						main.startMoveUp();
					}
				} else {
					main.startMoveUp();
				}
			}
		}

		if (key == KeyEvent.VK_D && main.getCollisionRight() == false && main.getInventoryState() == false) {
			if (main.getMoveRight() != null) { //Checks colision and moves
				if (main.getMoveRight().getRunning() == false) {
					main.startMoveRight();
				}
			} else {
				main.startMoveRight();
			}
		}

		if (key == KeyEvent.VK_A && main.getCollisionLeft() == false && main.getInventoryState() == false) {
			if (main.getMoveLeft() != null) { //Checks colision and moves
				if (main.getMoveLeft().getRunning() == false) {
					main.startMoveLeft();
				}
			} else {
				main.startMoveLeft();
			}
		}

		if (key == KeyEvent.VK_S && main.getCollisionBottom() == false && main.getInventoryState() == false) {
			if (main.getCreative() == true) { //Checks colision and only works if the player is in creative
				if (main.getMoveDown() != null) {
					if (main.getMoveDown().getRunning() == false) {
						main.startMoveDown();
					}
				} else {
					main.startMoveDown();
				}
			}
		}

		if (key == KeyEvent.VK_1 && main.getInventoryState() == false) { //Moves the block selector for placement
			main.setSelected(0);
		}

		if (key == KeyEvent.VK_2 && main.getInventoryState() == false) {
			main.setSelected(1);
		}

		if (key == KeyEvent.VK_3 && main.getInventoryState() == false) {
			main.setSelected(2);
		}

		if (key == KeyEvent.VK_4 && main.getInventoryState() == false) {
			main.setSelected(3);
		}

		if (key == KeyEvent.VK_5 && main.getInventoryState() == false) {
			main.setSelected(4);
		}

		if (key == KeyEvent.VK_6 && main.getInventoryState() == false) {
			main.setSelected(5);
		}

		if (key == KeyEvent.VK_7 && main.getInventoryState() == false) {
			main.setSelected(6);
		}

		if (key == KeyEvent.VK_8 && main.getInventoryState() == false) {
			main.setSelected(7);
		}
		
		if(key == KeyEvent.VK_E) { //Opens and closes the inventory
			if(main.getInventoryState() == true) {
				main.hideInventory();
			} else {
				main.showInventory();
			}
		}
	}

	public void keyReleased(KeyEvent e) { //Stops the above started threads

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W) {
			if (main.getCreative() == false) {
				main.setJumpNull();
			} else {
				if (main.getMoveUp() != null) {
					main.stopMoveUp();
				}
			}
		}

		if (key == KeyEvent.VK_D) {
			if (main.getMoveRight() != null) {
				main.stopMoveRight();
			}

		}

		if (key == KeyEvent.VK_A) {
			if (main.getMoveLeft() != null) {
				main.stopMoveLeft();
			}
		}

		if (key == KeyEvent.VK_S) {
			if (main.getCreative() == true) {
				if (main.getMoveDown() != null) {
					main.stopMoveDown();
				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}