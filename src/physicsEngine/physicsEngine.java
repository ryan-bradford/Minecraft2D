package physicsEngine;

import java.util.ArrayList;

import block.block;
import main.main;

public class physicsEngine { // The physics engine

	gravity gravity;
	Boolean runnable = false; // Do you want to run the physics engine?
	Boolean crative;

	public physicsEngine(Boolean creative) {
		init(creative);
	}

	public void init(Boolean creative) {
		if (creative == false) {
			gravity = new gravity();
			gravity.start();
		}
	}

	public Boolean getColisionLeft() { // Checks for colision on the left side

		int playerLeftHighX = (int) (main.getPlayer().getPlayerWidth() * ((1 + 1 / 3) * .1) + main
				.getPlayer().getBounds().x);
		int playerLeftLowX = (int) (main.getPlayer().getPlayerWidth() / 4 + main.getPlayer()
				.getBounds().x);
		int playerHighY = (int) (main.getPlayer().getBounds().y);
		int playerLowY = main.getPlayer().getBounds().y + (main.getPlayer().getHeight()) - 4;
		int[] playerYs = new int[] { playerLowY, (playerLowY + playerHighY) / 2 };
		ArrayList<block> blocks = main.getBlocks(playerHighY / main.getBlockHeight()); // Pulls the block row that could possibly collide
		ArrayList<block> otherBlocks;

		if (blocks != null) { //Checks colision at the head
			for (int i = 0; i < blocks.size(); i++) {
				if (blocks.get(i) != null) {
					if (blocks.get(i).getBounds().x < playerLeftHighX
							&& blocks.get(i).getBounds().x + main.getBlockHeight() > playerLeftHighX) { 
						if (blocks.get(i).getBounds().y < playerHighY
								&& blocks.get(i).getBounds().y + main.getBlockHeight() > playerHighY
								|| blocks.get(i).getBounds().y + 3 < playerLowY
								&& blocks.get(i).getBounds().y + main.getBlockHeight() > playerLowY) { // Checks if the player is in the block (Includes touching)
							return true;
						}
					}
				}
			}
			for (int i = 0; i < playerYs.length; i++) { //Checks colision in any other place requested
				otherBlocks = main.getBlocks(playerYs[i] / main.getBlockHeight());
				if (otherBlocks != null) {
					for (int x = 0; x < otherBlocks.size(); x++) {
						if (otherBlocks.get(x) != null) {
							if (otherBlocks.get(x).getBounds().x < playerLeftLowX
									&& otherBlocks.get(x).getBounds().x + main.getBlockHeight() > playerLeftLowX) {
								if (otherBlocks.get(x).getBounds().y + 3 < playerYs[i]
										&& otherBlocks.get(x).getBounds().y + main.getBlockHeight() > playerYs[i]) {
									return true;
								}
							}
						}
					}
				}
			}
		}

		return false;

	}

	public Boolean getColisionRight() { //Same but for the right side

		int playerRightHighX = (int) (main.getPlayer().getPlayerWidth() * ((1 + 1 / 3) * .1) + main
				.getPlayer().getBounds().x) + (int) (main.getPlayer().getPlayerWidth() / 1.2);
		int playerRightLowX = (int) (main.getPlayer().getPlayerWidth() / 4 + main.getPlayer()
				.getBounds().x) + main.getPlayer().getPlayerWidth() / 2;
		int playerHighY = (int) (main.getPlayer().getBounds().y);
		int playerLowY = main.getPlayer().getBounds().y + (main.getPlayer().getHeight()) - 4;
		int[] playerYs = new int[] { playerLowY, (playerLowY + playerHighY) / 2 };
		ArrayList<block> blocks = main.getBlocks((playerHighY / main.getBlockHeight()));
		ArrayList<block> otherBlocks;
		if (blocks != null) {
			for (int i = 0; i < blocks.size(); i++) {
				if (blocks.get(i) != null) {
					if ((blocks.get(i).getBounds().x < playerRightHighX && blocks.get(i)
							.getBounds().x + main.getBlockHeight() > playerRightHighX)) {
						if (blocks.get(i).getBounds().y < playerHighY
								&& blocks.get(i).getBounds().y + main.getBlockHeight() > playerHighY) {// High
							// Blocks
							// Collision
							return true;
						}
					}
				}
			}
		}
		for (int i = 0; i < playerYs.length; i++) {
			otherBlocks = main.getBlocks(playerYs[i] / main.getBlockHeight());
			if (otherBlocks != null) {
				for (int x = 0; x < otherBlocks.size(); x++) {
					if (otherBlocks.get(x) != null) {
						if (otherBlocks.get(x).getBounds().x < playerRightLowX
								&& otherBlocks.get(x).getBounds().x + main.getBlockHeight() > playerRightLowX) { // Low
							// Blocks
							// Collision
							if (otherBlocks.get(x).getBounds().y + 3 < playerYs[i]
									&& otherBlocks.get(x).getBounds().y + main.getBlockHeight() > playerYs[i]) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;

	}

	public Boolean getColisionBottom() { //Checks colision at the feet

		int playerLeftLowX = (int) (main.getPlayer().getPlayerWidth() / 4 + main.getPlayer()
				.getBounds().x);
		int playerRightLowX = (int) (main.getPlayer().getPlayerWidth() / 4 + main.getPlayer()
				.getBounds().x) + main.getPlayer().getPlayerWidth() / 2;
		int playerLowY = (int) (main.getPlayer().getBounds().y) + (main.getPlayer().getHeight());
		ArrayList<block> blocks = main.getBlocks((playerLowY / main.getBlockHeight()));
		if (blocks != null) {
			for (int i = 0; i < blocks.size(); i++) {
				if (blocks.get(i) != null) {
					if (blocks.get(i).getBounds().x <= (playerRightLowX + playerLeftLowX) / 2
							&& blocks.get(i).getBounds().x + main.getBlockHeight() >= (playerRightLowX + playerLeftLowX) / 2) {
						if (blocks.get(i).getBounds().y < playerLowY
								&& blocks.get(i).getBounds().y + main.getBlockHeight() > playerLowY) {
							return true;
						}
					}
				}
			}
		}
		return false;

	}

	public Boolean getColisionTop() { //Checks colision on the head

		int playerLeftLowX = (int) (main.getPlayer().getPlayerWidth() / 4 + main.getPlayer()
				.getBounds().x);
		int playerRightLowX = (int) (main.getPlayer().getPlayerWidth() / 4 + main.getPlayer()
				.getBounds().x) + main.getPlayer().getPlayerWidth() / 2;
		int playerHighY = (int) (main.getPlayer().getBounds().y);
		int playerLowY = (int) (main.getPlayer().getBounds().y) + (main.getPlayer().getHeight());
		ArrayList<block> blocks = main.getBlocks((playerHighY / main.getBlockHeight()));
		if (blocks != null) {

			for (int i = 0; i < blocks.size(); i++) {
				if (blocks.get(i) != null) {

					if (blocks.get(i).getBounds().x < (playerRightLowX + playerLeftLowX) / 2
							&& blocks.get(i).getBounds().x + main.getBlockHeight() > (playerRightLowX + playerLeftLowX) / 2) {
						if (blocks.get(i).getBounds().y < playerHighY
								&& blocks.get(i).getBounds().y + main.getBlockHeight() > playerHighY
								|| blocks.get(i).getBounds().y < playerLowY
								&& blocks.get(i).getBounds().y + main.getBlockHeight() > playerLowY) {
							return true;
						}
					}
				}
			}
		}

		return false;

	}

	public class gravity extends Thread { //Pulls the player down at the "speed of gravity" if it does not find a block below
		public void run() {				  //Seems(strong empasis on seems) to break when the player is standing on the crack between two blocks
			while (true) {
				while (runnable == true) {
					int gravitySpeed = main.getGravitySpeed(); //
					try {
						gravity.sleep(1000 / gravitySpeed); //
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (main.getJumping() == false) {
						if (getColisionBottom() == false) {
							main.movePlayer(0, 1);
						}
						try {
							gravity.sleep(10);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (getColisionBottom() == false) {
							main.movePlayer(0, 1);
						}
					}
				}
			}
		}
	}

	public void start() {
		runnable = true;
	}
}
