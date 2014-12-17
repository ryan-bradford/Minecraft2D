package physicsEngine;

import java.util.ArrayList;


import block.block;
import main.main;

public class physicsEngine {

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

	public Boolean getColisionLeft() {

		int playerLeftHighX = (int) (main.getPlayer().getPlayerWidth()
				* ((1 + 1 / 3) * .1) + main.getPlayer().getBounds().x);
		int playerLeftLowX = (int) (main.getPlayer().getPlayerWidth() / 4 + main.getPlayer()
				.getBounds().x);
		int playerHighY = (int) (main.getPlayer().getBounds().y);
		int playerLowY = (int) (main.getPlayer().getBounds().y)
				+ (main.getPlayer().getHeight()) - 4;
		int playerMidY = (playerLowY + playerHighY)/2;
		ArrayList<block> blocks = main.getBlocks(playerHighY / main.getBlockHeight());
		ArrayList<block> lowBlocks = main.getBlocks(playerLowY
				/ main.getBlockHeight());
		ArrayList<block> midBlocks = main.getBlocks(playerMidY
				/ main.getBlockHeight());
		if (blocks != null) {
			for (int i = 0; i < blocks.size(); i++) {
				if (blocks.get(i) != null) {
					if (blocks.get(i).getBounds().x < playerLeftHighX
							&& blocks.get(i).getBounds().x + 64 > playerLeftHighX) { // High
																						// Blocks
																						// Collison
						if (blocks.get(i).getBounds().y < playerHighY
								&& blocks.get(i).getBounds().y + 64 > playerHighY
								|| blocks.get(i).getBounds().y + 3 < playerLowY
								&& blocks.get(i).getBounds().y + 64 > playerLowY) {
							return true;
						}
					}
					if (blocks.get(i).getBounds().x < playerLeftLowX
							&& blocks.get(i).getBounds().x + 64 > playerLeftLowX) { // Low
																						// Blocks
																						// Collision
						if (blocks.get(i).getBounds().y + 3 < playerMidY
								&& blocks.get(i).getBounds().y + 64 > playerMidY) {
							return true;
						}
					}
				}
			}
			if (lowBlocks != null) {
				for (int i = 0; i < lowBlocks.size(); i++) {
					if (lowBlocks.get(i) != null) {
						if (lowBlocks.get(i).getBounds().x < playerLeftLowX
								&& lowBlocks.get(i).getBounds().x + 64 > playerLeftLowX) { // Low
																							// Blocks
																							// Collision
							if (lowBlocks.get(i).getBounds().y + 3 < playerLowY
									&& lowBlocks.get(i).getBounds().y + 64 > playerLowY) {
								return true;
							}
						}
					}
				}
			}
			if (midBlocks != null) {
				for (int i = 0; i < midBlocks.size(); i++) {
					if (midBlocks.get(i) != null) {
						if (midBlocks.get(i).getBounds().x < playerLeftLowX
								&& midBlocks.get(i).getBounds().x + 64 > playerLeftLowX) { // Low
																							// Blocks
																							// Collision
							if (midBlocks.get(i).getBounds().y < playerMidY
									&& midBlocks.get(i).getBounds().y + 64 > playerMidY) {
								return true;
							}
						}
					}
				}
			}
		}

		return false;

	}

	public Boolean getColisionRight() {

		int playerRightHighX = (int) (main.getPlayer().getPlayerWidth()
				* ((1 + 1 / 3) * .1) + main.getPlayer().getBounds().x)
				+ (int) (main.getPlayer().getPlayerWidth() / 1.2);
		int playerRightLowX = (int) (main.getPlayer().getPlayerWidth() / 4 + main.getPlayer()
				.getBounds().x) + main.getPlayer().getPlayerWidth() / 2;
		int playerHighY = (int) (main.getPlayer().getBounds().y);
		int playerLowY = (int) (main.getPlayer().getBounds().y)
				+ (main.getPlayer().getHeight()) - 4;
		ArrayList<block> blocks = main
				.getBlocks((playerHighY / main.getBlockHeight()));
		ArrayList<block> lowBlocks = main.getBlocks(playerLowY
				/ main.getBlockHeight());
		if (blocks != null) {
			for (int i = 0; i < blocks.size(); i++) {
				if (blocks.get(i) != null) {
					if ((blocks.get(i).getBounds().x < playerRightHighX && blocks
							.get(i).getBounds().x + 64 > playerRightHighX)) {
						if (blocks.get(i).getBounds().y < playerHighY
								&& blocks.get(i).getBounds().y + 64 > playerHighY) {// High
																					// Blocks
																					// Collision
							return true;
						}
					}
				}
			}
		}
		if (lowBlocks != null) {
			for (int i = 0; i < lowBlocks.size(); i++) {
				if (lowBlocks.get(i) != null) {
					if (lowBlocks.get(i).getBounds().x < playerRightLowX
							&& lowBlocks.get(i).getBounds().x + 64 > playerRightLowX) {// Low
																						// Blocks
																						// Collision
						if (lowBlocks.get(i).getBounds().y + 3 < playerLowY
								&& lowBlocks.get(i).getBounds().y + 64 > playerLowY) {
							return true;
						}
					}
				}
			}
		}
		return false;

	}

	public Boolean getColisionBottom() {

		int playerLeftLowX = (int) (main.getPlayer().getPlayerWidth() / 4 + main.getPlayer()
				.getBounds().x);
		int playerRightLowX = (int) (main.getPlayer().getPlayerWidth() / 4 + main.getPlayer()
				.getBounds().x) + main.getPlayer().getPlayerWidth() / 2;
		int playerLowY = (int) (main.getPlayer().getBounds().y)
				+ (main.getPlayer().getHeight());
		ArrayList<block> blocks = main
				.getBlocks((playerLowY / main.getBlockHeight()));
		if (blocks != null) {
			for (int i = 0; i < blocks.size(); i++) {
				if (blocks.get(i) != null) {
					if (blocks.get(i).getBounds().x <= (playerRightLowX + playerLeftLowX) / 2
							&& blocks.get(i).getBounds().x + 64 >= (playerRightLowX + playerLeftLowX) / 2) {
						if (blocks.get(i).getBounds().y < playerLowY
								&& blocks.get(i).getBounds().y + 64 > playerLowY) {
							return true;
						}
					}
				}
			}
		}
		return false;

	}

	public Boolean getColisionTop() {

		int playerLeftLowX = (int) (main.getPlayer().getPlayerWidth() / 4 + main.getPlayer()
				.getBounds().x);
		int playerRightLowX = (int) (main.getPlayer().getPlayerWidth() / 4 + main.getPlayer()
				.getBounds().x) + main.getPlayer().getPlayerWidth() / 2;
		int playerHighY = (int) (main.getPlayer().getBounds().y);
		int playerLowY = (int) (main.getPlayer().getBounds().y)
				+ (main.getPlayer().getHeight());
		ArrayList<block> blocks = main
				.getBlocks((playerHighY / main.getBlockHeight()));
		if (blocks != null) {

			for (int i = 0; i < blocks.size(); i++) {
				if (blocks.get(i) != null) {

					if (blocks.get(i).getBounds().x < (playerRightLowX + playerLeftLowX) / 2
							&& blocks.get(i).getBounds().x + 64 > (playerRightLowX + playerLeftLowX) / 2) {
						if (blocks.get(i).getBounds().y < playerHighY
								&& blocks.get(i).getBounds().y + 64 > playerHighY
								|| blocks.get(i).getBounds().y < playerLowY
								&& blocks.get(i).getBounds().y + 64 > playerLowY) {
							return true;
						}
					}
				}
			}
		}

		return false;

	}

	public class gravity extends Thread {
		public void run() {
			while (true) {
				while (runnable == true) {
					try {
						gravity.sleep(15);
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
