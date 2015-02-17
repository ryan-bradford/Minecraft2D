package physicsEngine;

import main.main;

public class physicsEngine { // The physics engine

	Boolean runnable = false; // Do you want to run the physics engine?
	Boolean crative;

	public Boolean getColisionLeft() { // Checks for colision on the left side

		int playerLeftHighX = (int) (main.getPlayer().getPlayerWidth() * ((1 + 1 / 3) * .1) + main.getPlayer()
				.getBounds().x) - 3;
		int playerLeftLowX = (int) (main.getPlayer().getPlayerWidth() / 4 + main.getPlayer().getBounds().x) - 3;
		int playerHighY = (int) (main.getPlayer().getBounds().y) + 6;
		int playerLowY = main.getPlayer().getBounds().y + (main.getPlayer().getHeight()) - 6;
		int[] playerYs = new int[] { playerHighY, playerLowY, (playerLowY + playerHighY) / 2 };
		int[] playerXs = new int[] { playerLeftHighX, playerLeftLowX, playerLeftLowX };
		for (int i = 0; i < playerYs.length; i++) {
			if (main.getBlock(playerYs[i] / main.blockHeight, playerXs[i] / main.blockHeight) != null) {
				return true;
			}
		}

		return false;
	}

	public Boolean getColisionRight() { // Same but for the right side

		int playerRightHighX = (int) (main.getPlayer().getPlayerWidth() * ((1 + 1 / 3) * .1) + main.getPlayer()
				.getBounds().x) + (int) (main.getPlayer().getPlayerWidth() / 1.2) + 3;
		int playerRightLowX = (int) (main.getPlayer().getPlayerWidth() / 4 + main.getPlayer().getBounds().x)
				+ main.getPlayer().getPlayerWidth() / 2 + 3;
		int playerHighY = (int) (main.getPlayer().getBounds().y) + 6;
		int playerLowY = main.getPlayer().getBounds().y + (main.getPlayer().getHeight()) - 6;
		int[] playerYs = new int[] { playerHighY, playerLowY, (playerLowY + playerHighY) / 2 };
		int[] playerXs = new int[] { playerRightHighX, playerRightLowX, playerRightLowX };
		for (int i = 0; i < playerYs.length; i++) {
			if (main.getBlock(playerYs[i] / main.blockHeight, playerXs[i] / main.blockHeight) != null) {
				return true;
			}
		}

		return false;

	}

	public Boolean getColisionBottom() { // Checks colision at the feet

		int playerLeftLowX = (int) (main.getPlayer().getPlayerWidth() / 4 + main.getPlayer().getBounds().x);
		int playerRightLowX = (int) (main.getPlayer().getPlayerWidth() / 4 + main.getPlayer().getBounds().x)
				+ main.getPlayer().getPlayerWidth() / 2;
		int playerLowY = (int) (main.getPlayer().getBounds().y) + (main.getPlayer().getHeight()) - 2;
		if (main.getBlock(playerLowY / main.getBlockHeight(), (playerRightLowX + playerLeftLowX) / 2
				/ main.blockHeight) != null) {
			return true;
		}

		return false;

	}

	public Boolean getColisionTop() { // Checks colision on the head

		int playerLeftLowX = (int) (main.getPlayer().getPlayerWidth() / 4 + main.getPlayer().getBounds().x);
		int playerRightLowX = (int) (main.getPlayer().getPlayerWidth() / 4 + main.getPlayer().getBounds().x)
				+ main.getPlayer().getPlayerWidth() / 2;
		int playerHighY = (int) (main.getPlayer().getBounds().y);
		if (main.getBlock(playerHighY / main.getBlockHeight(), (playerRightLowX + playerLeftLowX) / 2
				/ main.blockHeight) != null) {
			return true;
		}

		return false;

	}

	public void start() {
		runnable = true;
	}
}
