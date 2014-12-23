package main;

import java.awt.Color;
import java.awt.Toolkit;
import java.util.ArrayList;

import player.player;
import block.block;
import map.map;
import userControl.keyControls.jump;
import userControl.keyControls.movePlayer;

public class main {

	static map map;
	public static int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static int screenHeight = Toolkit.getDefaultToolkit()
			.getScreenSize().height;
	private static ArrayList<block> blocks;
	public static int blockHeight = 64;
	public static int inventoryGap = 4;
	public static int inventoryBlockNumber = 8;
	public static int inventoryExtra = 4;
	public static int inventoryHeight = 4;
	public static int dirtHeightInBlocks = ((main.screenHeight) / blockHeight) / 2 - 1;
	public static Color defaultBoxColor = Color.gray;
	public static Color swapBoxColor = Color.yellow;
	public static Color selectedBoxColor = Color.blue;
	public static Color backgroundColor = Color.darkGray;
	public static Color textColor = Color.black;
	public static Color airColor = new Color(135, 206, 250);
	public static Color skinColor = new Color(139, 69, 19);
	public static Color pantsColor = new Color(25, 25, 112);
	public static Color shirtColor = new Color(0, 155, 155);
	public static Color shoeColor = Color.darkGray;
	public static Boolean selected = false;
	public static Integer lastClickedX = null;
	public static Integer lastClickedY = null;
	public static Boolean lastClickedInventOrBar = null;
	public static String[] imageFileNames = new String[] { "blank.jpg",
			"dirt.jpg", "grass.jpg" };

	/*
	 * 0 is blank 1 is dirt 2 is grass
	 */

	public static void main(String[] args) {
		screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		map = new map(false, blockHeight, dirtHeightInBlocks,
				inventoryBlockNumber, inventoryGap, inventoryExtra,
				inventoryHeight, defaultBoxColor, swapBoxColor,
				selectedBoxColor, backgroundColor, textColor, airColor,
				skinColor, pantsColor, shirtColor, shoeColor, imageFileNames);
		map.pack();
		map.setBounds(0, 0, screenWidth, screenHeight);
		map.setVisible(true);
		map.setDefaultCloseOperation(map.EXIT_ON_CLOSE);
	}

	public static ArrayList<block> getBlocks(int height) {
		try {
			try {
				blocks = new ArrayList<block>();
				for (int i = 0; i < (map.mapWidth); i++) {
					blocks.add(map.getBlock(height, i));
				}
				return blocks;
			} catch (NullPointerException ex) {
				return new ArrayList<block>();
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			return new ArrayList<block>();
		}
	}

	public static Boolean getCreative() {
		return map.creative;
	}

	public static player getPlayer() {
		try {
			return map.player;
		} catch (NullPointerException ex) {
			return new player(null, null, null, null);
		}
	}

	public static int getWalkSpeed() {
		return map.walkSpeed;
	}

	public static int getBlockHeight() {
		return blockHeight;
	}

	public static void movePlayer(int x, int y) {
		try {
			map.player
					.setBounds(map.player.getBounds().x + x,
							(map.player.getBounds().y) + y,
							map.player.getBounds().width,
							map.player.getBounds().height);
		} catch (NullPointerException ex) {

		}
	}

	public static jump getJumpingObject() {
		return map.jump;
	}

	public static void startjumping() {
		map.jump = new jump();
		map.jump.start();
		map.jumping = true;
	}

	public static Boolean getJumping() {
		try {
			return map.jumping;
		} catch (NullPointerException ex) {
			return false;
		}
	}

	public static void doneJumping() {
		map.doneJumping();
	}

	public static void setJumpNull() {
		map.jump = null;
	}

	public static double getJumpDistance() {
		return map.jumpDistance;
	}

	public static double getJumpSpeed() {
		return map.jumpSpeed;
	}

	public static Boolean getCollisionTop() {
		return map.getCollisionTop();
	}

	public static Boolean getCollisionBottom() {
		return map.getCollisionBottom();
	}

	public static Boolean getCollisionLeft() {
		return map.getCollisionLeft();
	}

	public static Boolean getCollisionRight() {
		return map.getCollisionRight();
	}

	public static movePlayer getMoveLeft() {
		return map.moveLeft;
	}

	public static void startMoveLeft() {
		map.moveLeft = new movePlayer(true, null);
		map.moveLeft.start();
	}

	public static void stopMoveLeft() {
		map.moveLeft.stop();
		map.moveLeft = null;
	}

	public static movePlayer getMoveRight() {
		return map.moveRight;
	}

	public static void startMoveRight() {
		map.moveRight = new movePlayer(false, null);
		map.moveRight.start();
	}

	public static void stopMoveRight() {
		map.moveRight.stop();
		map.moveRight = null;
	}

	public static movePlayer getMoveDown() {
		return map.moveDown;
	}

	public static void startMoveDown() {
		map.moveDown = new movePlayer(null, false);
		map.moveDown.start();
	}

	public static void stopMoveDown() {
		map.moveDown.stop();
		map.moveDown = null;
	}

	public static movePlayer getMoveUp() {
		return map.moveUp;
	}

	public static void startMoveUp() {
		map.moveUp = new movePlayer(null, true);
		map.moveUp.start();
	}

	public static void stopMoveUp() {
		map.moveUp.stop();
		map.moveUp = null;
	}

	public static void moveSelectorBlock(int xCord, int yCord) {
		try {
			if (map.getInventoryState() == false) {
				try {
					map.selectMapBlock.setBounds(xCord, yCord, map.blockHeight,
							map.blockHeight);
				} catch (NullPointerException ex) {

				}
			}
		} catch (NullPointerException ex) {

		}
	}

	public static void placeBlock(int xCord, int row, String fileName) {
		map.drawNewBlock(xCord, row, fileName);
		map.repaint();
	}

	public static void placeBlockAtMouse(String fileName) {
		placeBlock(map.selectMapBlockThread.selectorX,
				map.selectMapBlockThread.selectorRow, fileName);
	}

	public static int getGravitySpeed() {
		try {
			return map.gravitySpeed;
		} catch (NullPointerException ex) {
			return 64;
		}
	}

	public static void setSelected(int i) {
		map.setSelected(i);
	}

	public static void showInventory() {
		map.showInventory();
	}

	public static void hideInventory() {
		map.hideInventory();
	}

	public static Boolean getInventoryState() {
		return map.getInventoryState();
	}

	public static String[] getImageFileNames() {
		return imageFileNames;
	}

	public static void setSwapInvent(int x, int y, Boolean inventOrBar) {
		if (inventOrBar == true) {
			if (selected == true) {
				map.inventory.setSwitch(x, y, selected, inventOrBar);
				map.inventoryBar.setSwitch(x, selected, inventOrBar);
				if (lastClickedInventOrBar == true) {
					int leftOver;
					int toAdd = map.inventory.inventoryButtons[lastClickedX][lastClickedY]
							.getAmount();
					int blockID = map.inventory.inventoryButtons[lastClickedX][lastClickedY]
							.getBlockID();
					leftOver = map.inventory.inventoryButtons[x][y].addBlock(
							toAdd, blockID);
					map.inventory.setAsNewButton(x, y);
					if (leftOver == 0) {
						if (lastClickedInventOrBar == false) {
							map.inventoryBar.removeButton(lastClickedX);
						} else {
							map.inventory.removeButton(lastClickedX,
									lastClickedY);
						}
					} else {
						if (lastClickedInventOrBar == false) {
							map.inventoryBar.inventoryBarButtons[lastClickedX].setValue(leftOver, blockID);
						} else {
							map.inventory.inventoryButtons[lastClickedX][lastClickedY].setValue(leftOver, blockID);
						}						
					}
				} else {
					int leftOver;
					int toAdd = map.inventoryBar.inventoryBarButtons[lastClickedX]
							.getAmount();
					int blockID = map.inventoryBar.inventoryBarButtons[lastClickedX]
							.getBlockID();
					leftOver = map.inventory.inventoryButtons[x][y].addBlock(
							toAdd, blockID);
					map.inventory.setAsNewButton(x, y);
					if (leftOver == 0) {
						if (lastClickedInventOrBar == false) {
							map.inventoryBar.removeButton(lastClickedX);
						} else {
							map.inventory.removeButton(lastClickedX,
									lastClickedY);
						}
					} else {
						if (lastClickedInventOrBar == false) {
							map.inventoryBar.inventoryBarButtons[lastClickedX].setValue(leftOver, blockID);
						} else {
							map.inventory.inventoryButtons[lastClickedX][lastClickedY].setValue(leftOver, blockID);
						}						
					}
				}
				selected = false;
			} else {
				map.inventory.setSwitch(x, y, selected, inventOrBar);
				lastClickedX = x;
				lastClickedY = y;
				lastClickedInventOrBar = true;
				selected = true;
			}
		} else {
			if (selected == true) {
				map.inventory.setSwitch(x, y, selected, inventOrBar);
				map.inventoryBar.setSwitch(x, selected, inventOrBar);
				if (lastClickedInventOrBar == true) {
					int leftOver;
					int toAdd = map.inventory.inventoryButtons[lastClickedX][lastClickedY]
							.getAmount();
					int blockID = map.inventory.inventoryButtons[lastClickedX][lastClickedY]
							.getBlockID();
					leftOver = map.inventoryBar.inventoryBarButtons[x]
							.addBlock(toAdd, blockID);
					map.inventoryBar.setAsNewButton(x);
					if (leftOver == 0) {
						if (lastClickedInventOrBar == false) {
							map.inventoryBar.removeButton(lastClickedX);
						} else {
							map.inventory.removeButton(lastClickedX,
									lastClickedY);
						}
					} else {
						if (lastClickedInventOrBar == false) {
							map.inventoryBar.inventoryBarButtons[lastClickedX].setValue(leftOver, blockID);
						} else {
							map.inventory.inventoryButtons[lastClickedX][lastClickedY].setValue(leftOver, blockID);
						}						
					}
				} else {
					int leftOver;
					int toAdd = map.inventoryBar.inventoryBarButtons[lastClickedX]
							.getAmount();
					int blockID = map.inventoryBar.inventoryBarButtons[lastClickedX]
							.getBlockID();
					leftOver = map.inventoryBar.inventoryBarButtons[x]
							.addBlock(toAdd, blockID);
					map.inventoryBar.setAsNewButton(x);
					if (leftOver == 0) {
						if (lastClickedInventOrBar == false) {
							map.inventoryBar.removeButton(lastClickedX);
						} else {
							map.inventory.removeButton(lastClickedX,
									lastClickedY);
						}
					} else {
						if (lastClickedInventOrBar == false) {
							map.inventoryBar.inventoryBarButtons[lastClickedX].setValue(leftOver, blockID);
						} else {
							map.inventory.inventoryButtons[lastClickedX][lastClickedY].setValue(leftOver, blockID);
						}						
					}
				}
				selected = false;
			} else {
				map.inventoryBar.setSwitch(x, selected, inventOrBar);
				lastClickedX = x;
				lastClickedY = y;
				lastClickedInventOrBar = false;
				selected = true;
			}
		}
	}
}
