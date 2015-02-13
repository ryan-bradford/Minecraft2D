package main;

import inventory.inventoryButton;

import java.awt.Color;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import player.player;
import block.block;
import map.Chunk;
import map.map;
import save.getSavedStuff;
import startScreen.startScreen;
import userControl.keyControls.jump;

public class main {

	public static map map;
	public static String fileName;
	public static int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width; // In pixels
	public static int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height; // In pixels
	public static int blockHeight = 64; // Height of a block
	public static int inventoryGap = 4; // The gap between boxes in the inventory
	public static int inventoryBlockNumber = 8; // Amount of slots in the ivnentory
	public static int inventoryExtra = 4; // How much larger the background box is on each side of the inventory boxes
	public static int inventoryHeight = 4; // How many boxes there are in the veritcal of the inventory
	public static int dirtHeightInBlocks = ((main.screenHeight) / blockHeight) / 2 - 1; // How much dirt
	public static Color defaultBoxColor = Color.gray; // COLORS!
	public static Color swapBoxColor = Color.yellow; // The color of the block selector in the inventory
	public static Color selectedBoxColor = Color.blue; // The color of the block selector in the world
	public static Color backgroundColor = Color.darkGray; // The background color in the inventory screen
	public static Color textColor = Color.black; // The Color of Text in the Inventory
	public static Color airColor = new Color(135, 206, 250); // The Color of the air
	public static Color skinColor = new Color(139, 69, 19); // The Color of the players skin
	public static Color pantsColor = new Color(25, 25, 112); // The color of the players pants
	public static Color shirtColor = new Color(0, 155, 155); // The color of the players shirt
	public static Color shoeColor = Color.darkGray; // The color of the players shoes
	public static Boolean selected = false; // Whether a inventory box has alread been slected, True is yes
	public static Integer lastClickedX = null; // The previously selected box, by the mouse, for movement in the inventory
	public static Integer lastClickedY = null; // The previously selected box, by the mouse, for movement in the inventory(Not used for the inventory bar)
	public static Boolean lastClickedInventOrBar = null; // Whether the last selected box was in the inventory or inventory bar
	public static String[] imageFileNames = new String[] { "textures/blank.jpg", "textures/dirt.jpg", "textures/grass.jpg", "textures/stone.jpg" }; // The block image file names
	public static Boolean creative = false; // If the game is in creative or not
	public static int stackHeight = 64; // How many blocks can go in one "stack" in the inventory
	public static int jumpDistance = 2; // How many blocks the player can jump
	public static int jumpSpeed = (int) (blockHeight * 3); // How fast the player will jump(Pixels per second)
	public static int gravitySpeed = (int) (blockHeight * 3.5); // How fast the player will fall(Pixels per second)
	public static int walkSpeed = blockHeight * 3;// How fast the player will walk(Pixels per second)
	public static int mineBlockSpeed = 100; // How many milliseconds per swing
	public static int mapHeight = screenHeight / blockHeight; // The height of the map in block measures
	public static String WorldGen = "normal"; // Can be either "Normal" or "Flatworld"- not case sensitive.
	public static int worldSeed = 0; // Number used for world generation, '0' is random
	public static int currentScreen; //The current viewed screen
	public static Chunk savedChunk; //The saved blocks
	public static Integer[] playerBounds; //The saved player location
	public static inventoryButton[][] savedInventoryButtons; //The saved inventory stuff
	public static inventoryButton[] savedInventoryBarButtons; //The saved inventory bar stuff
	public static String fileNamesSaveFile = "fileNames.txt";
	public static startScreen start;
	public static int prevSurfaceLR;
	public static int prevSurfaceRL;
	/*
	 * Block ID: 0 is blank 1 is dirt 2 is grass
	 */

	public static void main(String[] args) { // Creates the map
		start = new startScreen();
		screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	}

	public static void startGame(String worldName) {
		start = null;
		fileName = worldName;
		getSavedStuff.readFile();
		currentScreen = getSavedStuff.getScreenNum();
		playerBounds = getSavedStuff.getPlayerBounds();
		savedChunk = getSavedStuff.getAllScreens();
		savedInventoryButtons = getSavedStuff.getInventoryButtons();
		savedInventoryBarButtons = getSavedStuff.getInventoryBarButtons();
		prevSurfaceLR = getSavedStuff.getPrevSurfaceLR();
		prevSurfaceRL = getSavedStuff.getPrevSurfaceRL();
		map = new map(creative, blockHeight, dirtHeightInBlocks, inventoryBlockNumber, inventoryGap, inventoryExtra,
				inventoryHeight, defaultBoxColor, swapBoxColor, selectedBoxColor, backgroundColor, textColor, airColor,
				skinColor, pantsColor, shirtColor, shoeColor, imageFileNames, stackHeight, jumpDistance, jumpSpeed,
				gravitySpeed, walkSpeed, mineBlockSpeed, savedChunk, savedInventoryButtons, savedInventoryBarButtons,
				playerBounds, currentScreen, WorldGen, worldSeed, false, prevSurfaceLR, prevSurfaceRL);
		map.pack();
		map.setBounds(0, 0, screenWidth, screenHeight);
		map.setVisible(true);
		map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		map.getContentPane().setBackground(airColor);
	}
	
	// From here:
	public static block getBlock(int height, int x) {
		return map.getBlock(height, x);
	}

	public static block getBlocks(int screenNum, int height, int x) {
		return map.getBlock(screenNum, height, x);
	}

	public static Boolean getCreative() {
		return map.creative;
	}

	public static player getPlayer() {
		return map.player;
	}

	public static int getWalkSpeed() {
		return map.walkSpeed;
	}

	public static int getBlockHeight() {
		return blockHeight;
	}

	public static void movePlayer(int x, int y) {
		try {
			map.player.setBounds(map.player.getBounds().x + x, (map.player.getBounds().y) + y, map.player.getBounds().width, map.player.getBounds().height);
		} catch (NullPointerException ex) {
			ex.printStackTrace();
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
			ex.printStackTrace();
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

	public static void moveSelectorBlock(int xCord, int yCord) {
		try {
			if (map.getInventoryState() == false) {
				try {
					map.selectMapBlock.setBounds(xCord, yCord, map.blockHeight, map.blockHeight);
				} catch (NullPointerException ex) {

				}
			}
		} catch (NullPointerException ex) {
		}
	}

	public static void placeBlock(int xCord, int row, String fileName) {
		map.mouseClicked(xCord, row, fileName);
		map.repaint();
	}

	public static void placeBlockAtMouse(String fileName) {
		int selectorX = map.manager.cores[map.selectTaskCoreNumber].tasks.get(map.selectTaskTaskNumber - 1).getData()[0];
		int selectorRow = map.manager.cores[map.selectTaskCoreNumber].tasks.get(map.selectTaskTaskNumber - 1).getData()[1];
		placeBlock(selectorX, selectorRow, fileName);
	}

	public static int getGravitySpeed() {
		try {
			return gravitySpeed;
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

	// To here is used for basic passing of variables and preforming basic
	// methods
	public static void setSwapInvent(int x, int y, Boolean inventOrBar) {
		if (inventOrBar == true) { // True is inventory false is bar
			if (selected == true) { // If one was already selected
				map.inventory.setSwitch(x, y, selected, inventOrBar);
				map.inventoryBar.setSwitch(x, selected, inventOrBar);
				if (lastClickedInventOrBar == true) {
					int leftOver;
					int toAdd = map.inventory.inventoryButtons[lastClickedX][lastClickedY].getAmount();
					int blockID = map.inventory.inventoryButtons[lastClickedX][lastClickedY].getBlockID();
					leftOver = map.inventory.inventoryButtons[x][y].addBlock(toAdd, blockID);
					map.inventory.repaintButton(x, y); // Adds the amounts to
														// the button objects
					handleLeftOver(leftOver, toAdd, lastClickedX, lastClickedInventOrBar, x, y, blockID, inventOrBar);
				} else { // Same thing, but runs if the last clicked was the bar
					int leftOver;
					int toAdd = map.inventoryBar.inventoryBarButtons[lastClickedX].getAmount();
					int blockID = map.inventoryBar.inventoryBarButtons[lastClickedX].getBlockID();
					leftOver = map.inventory.inventoryButtons[x][y].addBlock(toAdd, blockID);
					map.inventory.repaintButton(x, y);
					handleLeftOver(leftOver, toAdd, lastClickedX, lastClickedInventOrBar, x, y, blockID, inventOrBar);
				}
				selected = false;
			} else { // Sets the swap box to the proper place
				map.inventory.setSwitch(x, y, selected, inventOrBar);
				lastClickedX = x;
				lastClickedY = y;
				lastClickedInventOrBar = true;
				selected = true;
			}
		} else { // Same thing but runs of the final clicked was the bar
			if (selected == true) {
				map.inventory.setSwitch(x, y, selected, inventOrBar);
				map.inventoryBar.setSwitch(x, selected, inventOrBar);
				if (lastClickedInventOrBar == true) {
					int leftOver;
					int toAdd = map.inventory.inventoryButtons[lastClickedX][lastClickedY].getAmount();
					int blockID = map.inventory.inventoryButtons[lastClickedX][lastClickedY].getBlockID();
					leftOver = map.inventoryBar.inventoryBarButtons[x].addBlock(toAdd, blockID);
					map.inventoryBar.repaintButton(x);
					handleLeftOver(leftOver, toAdd, lastClickedX, lastClickedInventOrBar, x, y, blockID, inventOrBar);

				} else {
					int leftOver;
					int toAdd = map.inventoryBar.inventoryBarButtons[lastClickedX].getAmount();
					int blockID = map.inventoryBar.inventoryBarButtons[lastClickedX].getBlockID();
					leftOver = map.inventoryBar.inventoryBarButtons[x].addBlock(toAdd, blockID);
					map.inventoryBar.repaintButton(x);
					handleLeftOver(leftOver, toAdd, lastClickedX, lastClickedInventOrBar, x, y, blockID, inventOrBar);
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

	public static void handleLeftOver(int leftOver, int toAdd, int lastClickedX, Boolean lastClickedInventOrBar, int x, int y, int blockID, Boolean selected) {
		if (leftOver == 0) { // If all of the amount could be passed, clear the
								// button
			if (lastClickedInventOrBar == false) {
				map.inventoryBar.removeButton(lastClickedX);
			} else {
				map.inventory.removeButton(lastClickedX, lastClickedY);
			}
		} else if (leftOver == toAdd) {
			if (lastClickedInventOrBar == false) { // Change the vaule to the
													// leftover
				if (selected == false) {
					int lastBarBlockID = map.inventoryBar.inventoryBarButtons[lastClickedX].getBlockID();
					int lastBarAmount = map.inventoryBar.inventoryBarButtons[lastClickedX].getAmount();
					int barBlockAmount = map.inventoryBar.inventoryBarButtons[x].getAmount();
					int barBlockID = map.inventoryBar.inventoryBarButtons[x].getBlockID();
					map.inventoryBar.inventoryBarButtons[x].setValue(lastBarAmount, lastBarBlockID);
					map.inventoryBar.inventoryBarButtons[lastClickedX].setValue(barBlockAmount, barBlockID);
					map.inventoryBar.repaintButton(lastClickedX);
					map.inventoryBar.repaintButton(x);
				} else {
					int barBlockID = map.inventoryBar.inventoryBarButtons[lastClickedX].getBlockID();
					int barAmount = map.inventoryBar.inventoryBarButtons[lastClickedX].getAmount();
					int inventoryBlockAmount = map.inventory.inventoryButtons[x][y].getAmount();
					int inventoryBlockID = map.inventory.inventoryButtons[x][y].getBlockID();
					map.inventory.inventoryButtons[x][y].setValue(barAmount, barBlockID);
					map.inventoryBar.inventoryBarButtons[lastClickedX].setValue(inventoryBlockAmount, inventoryBlockID);
					map.inventoryBar.repaintButton(lastClickedX);
					map.inventory.repaintButton(x, y);
				}
			} else {
				if (selected == true) {
					int barBlockID = map.inventory.inventoryButtons[x][y].getBlockID();
					int barAmount = map.inventory.inventoryButtons[x][y].getAmount();
					int inventoryBlockAmount = map.inventory.inventoryButtons[lastClickedX][lastClickedY].getAmount();
					int inventorylockID = map.inventory.inventoryButtons[lastClickedX][lastClickedY].getBlockID();
					map.inventory.inventoryButtons[lastClickedX][lastClickedY].setValue(barAmount, barBlockID);
					map.inventory.inventoryButtons[x][y].setValue(inventoryBlockAmount, inventorylockID);
					map.inventory.repaintButton(lastClickedX, lastClickedY);
					map.inventory.repaintButton(x, y);
				} else {
					int barBlockID = map.inventoryBar.inventoryBarButtons[x].getBlockID();
					int barAmount = map.inventoryBar.inventoryBarButtons[y].getAmount();
					int inventoryBlockAmount = map.inventory.inventoryButtons[lastClickedX][lastClickedY].getAmount();
					int inventorylockID = map.inventory.inventoryButtons[lastClickedX][lastClickedY].getBlockID();
					map.inventory.inventoryButtons[lastClickedX][lastClickedY].setValue(barAmount, barBlockID);
					map.inventoryBar.inventoryBarButtons[x].setValue(inventoryBlockAmount, inventorylockID);
					map.inventory.repaintButton(lastClickedX, lastClickedY);
					map.inventoryBar.repaintButton(x);
				}
			}
		} else { // If some couldn't

			if (lastClickedInventOrBar == false) { // Change the vaule to the
													// leftover
				map.inventoryBar.inventoryBarButtons[lastClickedX].setValue(leftOver, blockID);
			} else {
				map.inventory.inventoryButtons[lastClickedX][lastClickedY].setValue(leftOver, blockID);
			}
		}
	}
}
