package userControl.keyControls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.main;

public class keyControls implements KeyListener { // The thing that controls the
													// keys
	public Boolean aPressed = false;
	public Boolean dPressed = false;
	public Boolean wPressed = false;
	public Boolean sPressed = false;

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A) {
			aPressed = true;
		}
		if (key == KeyEvent.VK_D) {
			dPressed = true;
		}
		if (key == KeyEvent.VK_S) {
			if (main.creative == true) {
				sPressed = true;
			}
		}
		if (key == KeyEvent.VK_W && main.getCollisionTop() == false && main.getInventoryState() == false) {
			if (main.getCreative() == false) { // Checks colision and moves
												// also, if the player is in
												// creative, he will not jump,
												// but fly
				if (main.getJumpingObject() == null && main.getJumping() == false && main.getCollisionBottom() == true) {
					main.startjumping();
				}
			} else {
				wPressed = true;
			}
		}

		if (key == KeyEvent.VK_1 && main.getInventoryState() == false) { // Moves
																			// the
																			// block
																			// selector
																			// for
																			// placement
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

		if (key == KeyEvent.VK_E) { // Opens and closes the inventory
			if (main.getInventoryState() == true) {
				main.hideInventory();
			} else {
				main.showInventory();
			}
		}
	}

	public void keyReleased(KeyEvent e) { // Stops the above started threads

		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W) {
			wPressed = false;
		}
		if (key == KeyEvent.VK_A) {
			aPressed = false;
		}
		if (key == KeyEvent.VK_D) {
			dPressed = false;
		}
		if (key == KeyEvent.VK_S) {
			sPressed = false;
		}
		if (key == KeyEvent.VK_W) {
			if (main.getCreative() == false) {
				main.setJumpNull();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}