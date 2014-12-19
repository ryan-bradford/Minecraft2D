package userControl.keyControls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.main;

public class keyControls implements KeyListener {
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode( );

			if (key == KeyEvent.VK_W && main.getCollisionTop() == false) {
				if (main.getCreative() == false) {
					if (main.getJumpingObject() == null && main.getJumping() == false
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

			if (key == KeyEvent.VK_D && main.getCollisionRight() == false) {
				if (main.getMoveRight() != null) {
					if (main.getMoveRight().getRunning() == false) {
						main.startMoveRight();
					}
				} else {
					main.startMoveRight();
				}
			}

			if (key == KeyEvent.VK_A && main.getCollisionLeft() == false) {
				if (main.getMoveLeft() != null) {
					if (main.getMoveLeft().getRunning() == false) {
						main.startMoveLeft();
					}
				} else {
					main.startMoveLeft();
				}
			}

			if (key == KeyEvent.VK_S && main.getCollisionBottom() == false) {
				if (main.getCreative() == true) {
					if (main.getMoveDown() != null) {
						if (main.getMoveDown().getRunning() == false) {
							main.startMoveDown();
						}
					} else {
						main.startMoveDown();
					}
				}
			}
		}

		public void keyReleased(KeyEvent e) {

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