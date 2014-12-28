package map;

import javax.swing.JFrame;

import physicsEngine.gravityTask;
import physicsEngine.physicsEngine;
import player.player;
import block.block;
import block.air;
import block.selectorBlock;
import thread.task;
import thread.taskManager;
import userControl.keyControls.jump;
import userControl.keyControls.keyControls;
import userControl.keyControls.movePlayer;
import userControl.mouseControl.moveSelectorBlock;
import userControl.mouseControl.placeBlock;
import inventory.inventoryBar;
import main.main;
import inventory.inventory;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/* Notes: 
 * Math.abs(dirtRows-mapHeight)-1; is where the grass is drawn.
 * Math.abs(dirtRows-mapHeight) is the top of the dirt.
 * Chunk 0 is the bottom row. 
 * Map:
 * Row 3:	|_|_|_|
 * Row 2:	|_|_|_|
 * Row 1:	|_|_|_|
 * Row 0:   |_|_|_|
 * To Do:
 * 1. Add the ability to gather blocks
 * 2. Add a mouse action method
 * 		Two Methods
 * 			Place Block: Called if a block does not exist in the clicked area
 * 			Break Block: Called if a block does exist in the clicked area
 * 3. Add a main thread that runs the processes of all the others.
 * 		Make it modular?
 * */

public class map extends JFrame { // The main panel of display
	public ArrayList<ArrayList<block>> chunk; // Horizontal Row
	public player player;
	public selectorBlock selectMapBlock; // The blue outline that can be seen on placement area
	public moveSelectorBlock selectMapBlockThread; // The thread that controls moving the selector block
	public inventoryBar inventoryBar;
	public inventory inventory;
	public placeBlock placer; // The thing that listens for mouse clicks
	public jump jump; // The thread that controls jumping, one run will jump once
	public movePlayer moveLeft; // The thread that controls left facing movement
	public movePlayer moveRight; // The thread that controls right facing movement
	public movePlayer moveUp; // The thread that controls upwards movement
	public movePlayer moveDown; // The thread that controls downwards movement
	public air air; // The JPanel that is the air
	public physicsEngine physics; // The thread that controls the physics
	public String selectedBlockKind; // The kind of block that is selected in the inventory bar
	public Boolean jumping; // The boolean that updates when the player is jumping
	public Boolean creative; // The boolean that holds creative game mode or not
	public Boolean inventoryOpen; // The boolean that holds whether the inventory is open or not
	public String[] imageFileNames; // The string that holds the block image file names (Often not used)
	public int blockHeight; // The int that stores the block height
	public int mapWidth; // The int that stores the map width in block widths (Ex. A 64 pixel map will have a width of 1)
	public int mapHeight; // The int that stores the map height in block widths (Ex. A 64 pixel map will have a width of 1)
	public int jumpSpeed; // The int that stores how fast you will jump (In pixels per second)
	public int gravitySpeed; // The int that stores how fast you will fall (In pixels per second)
	public int dirtRows; // The int that stores how many dirt rows there are, will be gone soon
	public double jumpDistance; // The int that stores how height you will jump (In pixels)
	public int walkSpeed; // The int that stores how fast you will walk (In pixels per second)
	public double startTime = System.nanoTime(); // Stores the start time, for debugging proposes
	public taskManager manager;

	public map(Boolean creative,
			int blockHeight1,
			int dirtHeightInBlocks,// The long list of constructors, allows for easy customizability
			int inventoryBlock,
			int inventoryGap,
			int inventoryExtra, // For all intensive porposes, this is the main class
			int inventoryHeight, Color defaultBoxColor, Color swapBoxColor, Color selectedBoxColor, Color backgroundColor, Color textColor,
			Color airColor, Color skinColor, Color pantsColor, Color shirtColor, Color shoeColor, String[] imageFileNames, int stackHeight,
			int jumpHeight, int jumpSpeed, int gravitySpeed1, int walkSpeed1) {
		initVar(creative, blockHeight1, dirtHeightInBlocks, imageFileNames, jumpHeight, jumpSpeed, gravitySpeed1, walkSpeed1);
		initTaskManager();
		drawMap(airColor);
		drawPlayer(skinColor, pantsColor, shirtColor, shoeColor);
		initPhysics();
		startPhysics();
		startUserControl();
		initAndDrawInventory(inventoryBlock, inventoryGap, inventoryExtra, inventoryHeight, defaultBoxColor, swapBoxColor, selectedBoxColor,
				backgroundColor, textColor, stackHeight);
		System.out.println("The Game Has Begun!");
	}

	public void initVar(Boolean creativ, int blockHeight1, int dirtHeightInBlocks, String[] imageFileNames1, int jumpDistance1, int jumpSpeed1,
			int gravitySpeed1, int walkSpeed1) { // Sets all the variables to their desired values
		jumping = false;
		imageFileNames = imageFileNames1;
		inventoryOpen = false;
		blockHeight = blockHeight1; // Sets Block Pixel Height
		mapWidth = (main.screenWidth) / blockHeight;
		mapHeight = (main.screenHeight) / blockHeight;
		dirtRows = dirtHeightInBlocks;
		jumpSpeed = jumpSpeed1;// Pixels per Second
		gravitySpeed = gravitySpeed1;// Pixels per Second
		jumpDistance = jumpDistance1; // In Block Width
		walkSpeed = walkSpeed1;
		creative = creativ;
		System.out.println("Variables Initialized" + " In " + (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void drawMap(Color airColor) { // Calls the methods for drawing the map
		chunk = new ArrayList<ArrayList<block>>();
		for (int i = 0; i < mapHeight; i++) {
			chunk.add(new ArrayList<block>());
		}
		drawAir(airColor);
		drawDirt();
		drawGrass();
		System.out.println("Map Drawn" + " In " + (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void drawDirt() { // Draws the dirt
		for (int i = Math.abs(dirtRows - mapHeight); i < mapHeight; i++) {
			for (int x = 0; x < mapWidth; x++) {
				chunk.get(i).add(new block(imageFileNames[1]));
				chunk.get(i).get(x).setBounds((x * blockHeight), ((i) * blockHeight), blockHeight, blockHeight);
				chunk.get(i).get(x).setOpaque(false);
				add(chunk.get(i).get(x), 0);
			}

		}
		System.out.println("Dirt Drawn" + " In " + (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void drawGrass() { // Draws the grass
		int current = 0;
		int rowID = Math.abs(dirtRows - mapHeight) - 1;
		for (int x = 0; x < mapWidth; x++) {
			chunk.get(rowID).add(new block(imageFileNames[2]));
			chunk.get(rowID).get(current).setBounds((x * blockHeight), ((rowID) * blockHeight), blockHeight, blockHeight);
			chunk.get(rowID).get(current).setOpaque(false);
			add(chunk.get(rowID).get(current), 1);
			current++;
		}
		System.out.println("Grass Drawn" + " In " + (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void drawAir(Color airColor) { // Draws the air
		air = new air(airColor);
		air.setBounds(0, 0, main.screenWidth, main.screenHeight);
		air.setOpaque(false);
		add(air, 0);
		System.out.println("Air Drawn" + " In " + (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void drawPlayer(Color skinColor, Color pantsColor, Color shirtColor, Color shoeColor) { // Draws the player
		player = new player(skinColor, pantsColor, shirtColor, shoeColor);
		player.setBounds(((main.screenWidth) / 2), (((Math.abs(dirtRows - mapHeight) - 1) * blockHeight) - player.getPlayerHeight()),
				player.getPlayerWidth(), player.getPlayerHeight());
		player.setOpaque(false);
		add(player, 0);
		System.out.println("Player Drawn" + " In " + (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void drawNewBlock(int xCord, int yRow, String fileName) { // Draws a new block when requested
		Boolean blockExists = true;
		for (int i = 0; i < chunk.get(yRow).size(); i++) {
			if (chunk.get(yRow).get(i).getBounds().x == xCord) { // Checks if a block already exists
				blockExists = false;
				break;
			}
		}
		if (blockExists == true) {
			selectedBlockKind = main.getImageFileNames()[inventoryBar.inventoryBarButtons[inventoryBar.selected].getBlockID()]; // Gets what block you have selected in your inventory
			if (!selectedBlockKind.equals(new String(imageFileNames[0])) // Checks if the block in your inventory isn't a blank file block
					&& inventoryBar.inventoryBarButtons[inventoryBar.selected].getAmount() > 0 // Checks if you have blocks to place
					&& main.getInventoryState() == false) { // Checks if your inventory is closed
				chunk.get(yRow).add(new block(selectedBlockKind));
				int yRowSize = (chunk.get(yRow).size() - 1);
				chunk.get(yRow).get(yRowSize).setBounds(xCord, yRow * blockHeight, blockHeight, blockHeight);
				if (creative == false) {
					inventoryBar.inventoryBarButtons[inventoryBar.selected].subtractOne();
				}
				add(chunk.get(yRow).get(yRowSize), 2);
				if (inventoryBar.inventoryBarButtons[inventoryBar.selected].getAmount() <= 0) {
					inventoryBar.removeButton(inventoryBar.selected); // Removes the block from your hotbar if you have 0 left
				}// To Add:Check if block already exists
			}
		}
	}

	public void initAndDrawInventory(int inventoryBlock, int inventoryGap, int inventoryExtra, int inventoryHeight, Color defaultBoxColor,
			Color swapBoxColor, Color selectedBoxColor, Color backgroundColor, Color textColor, int stackHeight) { // Initializes and draws the inventory
		inventoryBar = new inventoryBar(inventoryBlock, inventoryGap, inventoryExtra, defaultBoxColor, swapBoxColor, selectedBoxColor,
				backgroundColor, textColor, stackHeight);
		int width = inventoryBar.width;
		int height = inventoryBar.height;
		inventoryBar.setBounds((main.screenWidth / 2 - width / 2), main.screenHeight - height * 3, width, height);
		add(inventoryBar, 0);
		selectedBlockKind = inventoryBar.setSelected(0);
		inventory = new inventory(inventoryBlock, inventoryGap, inventoryExtra, inventoryHeight, defaultBoxColor, swapBoxColor, backgroundColor,
				textColor, stackHeight);
		width = inventory.width;
		height = inventory.height;
		inventory.setBounds((main.screenWidth - inventory.width) / 2, (main.screenHeight - inventory.height) / 2, width, height);
		inventory.setVisible(false);
		inventoryBar.setFocusable(false);
		add(inventory, 0);
		System.out.println("Inventory Drawn" + " In " + (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void initTaskManager() {
		manager = new taskManager(null);
		manager.start();
		System.out.println("Task Manager Initialized " + " In " + (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void startUserControl() { // Starts the user controls
		startKeyControls();
		startMouseControl();
		System.out.println("User Controls Started" + " In " + (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void startKeyControls() { // Adds the key listner
		keyControls keyListener = new keyControls();
		this.addKeyListener(keyListener);
		this.setLayout(null);
	}

	public void startMouseControl() { // Starts the thread that can move the map block selector
		// hideCursor();
		selectMapBlock = new selectorBlock();
		selectMapBlock.setBounds(128, 128, blockHeight, blockHeight);
		selectMapBlock.setOpaque(false);
		add(selectMapBlock, 0);
		selectMapBlockThread = new moveSelectorBlock();
		selectMapBlockThread.start();
		placer = new placeBlock();
		this.addMouseListener(placer);
	}

	public void hideCursor() { // Hides the cursor, undecided if this should be done
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		super.getContentPane().setCursor(blankCursor);
	}

	public void initPhysics() { // Initializes the physics
		physics = new physicsEngine();
		System.out.println("Physics Initalized" + " In " + (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void startPhysics() { // Starts the physics
		physics.start();
		task thisTask = new gravityTask();
		manager.addTask(thisTask);
		System.out.println("Physics Started" + " In " + (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void doneJumping() { // Called when the jump thread is done excuting
		jumping = false;
	}

	public block getBlock(int i, int x) { // Gets the information of a block
		try {
			if (chunk.get(i).get(0).equals(null)) {
				return null;
			} else {
				return chunk.get(i).get(x);
			}
		} catch (IndexOutOfBoundsException ex) {
		}
		return null;
	}

	// The below methods should be self explanatory, ask for explanation if needed
	public Boolean getCollisionLeft() {
		try {
			return physics.getColisionLeft();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public Boolean getCollisionRight() {
		return physics.getColisionRight();
	}

	public Boolean getCollisionTop() {
		return physics.getColisionTop();
	}

	public Boolean getCollisionBottom() {
		return physics.getColisionBottom();
	}

	public void setSelected(int i) {
		selectedBlockKind = inventoryBar.setSelected(i);
	}

	public void showInventory() {
		inventory.setVisible(true);
		inventoryOpen = true;
	}

	public void hideInventory() {
		inventory.setVisible(false);
		inventoryOpen = false;
	}

	public Boolean getInventoryState() {
		return inventoryOpen;
	}

}
