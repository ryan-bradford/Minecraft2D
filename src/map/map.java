package map;

import javax.swing.JFrame;

import physicsEngine.gravityTask;
import physicsEngine.physicsEngine;
import player.player;
import block.block;
import block.selectorBlock;
import save.saveTask;
import thread.task;
import thread.taskManager;
import userControl.keyControls.jump;
import userControl.keyControls.keyControls;
import userControl.keyControls.movePlayerTask;
import userControl.mouseControl.mineBlockTask;
import userControl.mouseControl.moveSelectorBlockTask;
import userControl.mouseControl.clickEvent;
import inventory.inventoryBar;
import inventory.inventoryButton;
import main.main;
import inventory.inventory;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/* Notes: 
 * To Do:
 *  1. Add crafting
 *  2. Add Up and Down Movement
 *  3. Add the ability for there to be item drops
 * */

public class map extends JFrame { // The main panel of display
	public Chunk chunk; // Horizontal Row
	public player player;
	public selectorBlock selectMapBlock; // The blue outline that can be seen on placement area
	public inventoryBar inventoryBar;
	public inventory inventory;
	public clickEvent placer; // The thing that listens for mouse clicks
	public jump jump; // The thread that controls jumping, one run will jump once
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
	public int selectTaskTaskNumber; // The task number of the block selector task
	public int selectTaskCoreNumber; // The core number of the block selector task
	public double startTime = System.currentTimeMillis(); // Stores the start time, for debugging proposes
	public taskManager manager; // The task manager object
	public keyControls keyListener; // The keyListener object
	public mineBlockTask mine;
	public int currentScreen = 0;
	public String WorldGen; // The string that describes the map generation "Flatworld" = flat, "Normal" = variation/biomes
	public Random rnd;
	public int seed; // Randomly generates land
	public int playerStartSpot;
	public int prevSurfaceLR;
	public int prevSurfaceRL;

	// The long list of constructors, allows for easy customizability
	// For all intensive porposes, this is the main class
	public map(Boolean creative, int blockHeight1, int dirtHeightInBlocks, int inventoryBlock, int inventoryGap, int inventoryExtra, int inventoryHeight, Color defaultBoxColor, Color swapBoxColor,
			Color selectedBoxColor, Color backgroundColor, Color textColor, Color airColor, Color skinColor, Color pantsColor, Color shirtColor, Color shoeColor, String[] imageFileNames,
			int stackHeight, int jumpHeight, int jumpSpeed, int gravitySpeed1, int walkSpeed1, int mineBlockSpeed, Chunk chunk1, inventoryButton[][] inventButtons, inventoryButton[] inventBarButtons,
			Integer[] playerPosition, int currentScreen1, String WorldType, int worldSeed, Boolean paused, int prevSurfaceLR, int prevSurfaceRL) {
		double startTime1 = System.currentTimeMillis();
		setLayout(null);
		initVar(creative, blockHeight1, dirtHeightInBlocks, imageFileNames, jumpHeight, jumpSpeed, gravitySpeed1, walkSpeed1, chunk1, currentScreen1, WorldType, worldSeed, prevSurfaceLR,
				prevSurfaceRL);
		if (!paused) {
			initTaskManager();
		}
		drawMap();
		if (!paused) {
			drawPlayer(skinColor, pantsColor, shirtColor, shoeColor, playerPosition);
		}
		if (!paused) {
			initPhysics();
		}
		if (!paused) {
			startPhysics();
		}
		if (!paused) {
			startUserControl(mineBlockSpeed);
		}
		if (!paused) {
			initAndDrawInventory(inventoryBlock, inventoryGap, inventoryExtra, inventoryHeight, defaultBoxColor, swapBoxColor, selectedBoxColor, backgroundColor, textColor, stackHeight,
					inventButtons, inventBarButtons);
		}
		if (!paused) {
			startSave();
		}
		System.out.println("The Game Has Begun!" + " In " + (System.currentTimeMillis() - startTime1) + " Milliseconds");
	}

	public void initVar(Boolean creativ, int blockHeight1, int dirtHeightInBlocks, String[] imageFileNames1, int jumpDistance1, int jumpSpeed1, int gravitySpeed1, int walkSpeed1, Chunk chunk1,
			int currentScreen1, String WorldType, int worldSeed, int prevSurfaceLR1, int prevSurfaceRL1) { //
		startTime = System.currentTimeMillis();
		currentScreen = currentScreen1;
		prevSurfaceLR = prevSurfaceLR1;
		prevSurfaceRL = prevSurfaceRL1;
		chunk = new Chunk();
		if (chunk1 != null) {
			for (int i = 0; i < chunk1.size(); i++) {
				chunk.add(chunk1.get(i));
			}
			if (chunk1.ChunkRL != null) {
				for (int i = 1; i < chunk1.ChunkRL.size(); i++) {
					chunk.ChunkRL.add(chunk1.get(-i));
				}
			}
		}
		jumping = false;
		imageFileNames = imageFileNames1;
		inventoryOpen = false;
		blockHeight = blockHeight1; // Sets Block Pixel Height
		mapWidth = (main.screenWidth) / blockHeight + 1;
		mapHeight = (main.screenHeight) / blockHeight;
		dirtRows = dirtHeightInBlocks - 1;
		jumpSpeed = jumpSpeed1;// Pixels per Second
		gravitySpeed = gravitySpeed1;// Pixels per Second
		jumpDistance = jumpDistance1; // In Block Width
		walkSpeed = walkSpeed1;
		creative = creativ;
		WorldGen = WorldType;
		if (worldSeed == 0) {
			rnd = new Random();
			seed = rnd.nextInt(999999);// can be substituted for a specific
										// number
		} else {
			seed = worldSeed;
		}
		try {
			chunk.get(0);
		} catch (IndexOutOfBoundsException ex) {
			chunk = new Chunk();
		}
		System.out.println("Variables Initialized" + " In " + (System.currentTimeMillis() - startTime) + " Milliseconds");
	}

	public boolean getDrawNewOrOld() { // True is new, false is old
		try {
			chunk.get(currentScreen);
			return false;
		} catch (IndexOutOfBoundsException ex) {
			return true;
		}
	}

	public boolean getDrawNewOrOld(int currentScreen1) { // True is new, false
															// is old
		try {
			chunk.get(currentScreen1);
			return false;
		} catch (IndexOutOfBoundsException ex) {
			return true;
		}
	}

	public void drawMap() {
		startTime = System.currentTimeMillis();
		if (getDrawNewOrOld()) {
			if (currentScreen >= 0) {
				chunk.add(new ArrayList<block[]>());
			} else {
				chunk.ChunkRL.add(new ArrayList<block[]>());
			}
			for (int i = 0; i < mapHeight; i++) {
				chunk.get(currentScreen).add(new block[mapWidth]);
			}
			if (WorldGen.toUpperCase().equals("FLATWORLD")) { // Flat world generation I didn't touch
				drawDirt();
				drawAir();
				drawGrass();
			} else if (WorldGen.toUpperCase().equals("NORMAL")) { // Environment varies
				System.out.println("World generating with seed " + seed);
				dirtRows = dirtRows + 5;
				Biome CurrentBiome = new Biome(seed, currentScreen, null);
				drawLand(CurrentBiome);
				drawStructures(CurrentBiome);
			}
		} else {
			for (int x = 0; x < chunk.get(currentScreen).size(); x++) {
				for (int y = 0; y < chunk.get(currentScreen).get(x).length; y++) {
					try {
						chunk.get(currentScreen).get(x)[y].setVisible(true);
						add(chunk.get(currentScreen).get(x)[y], 0);
						repaint();
					} catch (NullPointerException ex) {

					}
				}
			}
		}
		chunk.get(currentScreen).get(0)[0].setLight(100);
		chunk.get(currentScreen).get(0)[0].lightSource = true;
		updateLighting();
		repaint();
		System.out.println("Map Drawn" + " In " + (System.currentTimeMillis() - startTime) + " Milliseconds");
	}

	public void drawLand(Biome CurrentBiome) {
		// Draws the ground
		if (currentScreen == 0) {
			prevSurfaceLR = Math.max(dirtRows - 5, 3);
		}
		// PER COLUMN
		for (int x = 0; x < mapWidth; x++) {
			// generate surface block number
			int surface = CurrentBiome.genSurface(x, currentScreen, prevSurfaceLR);
			/*
			 * int surface = (int) ((int) seed * (currentScreen + 1) * Math.sqrt((seed * (x + 1)) % 240)) % 100; // System.out.println("column " x " percentage of " surface " mapwidth " mapWidth); if
			 * (surface < 2) { // unlikely scenario where floor is raised two blocks surface = -2; } else if (surface < 20) { // floor is up 1 block surface = -1; } else if (surface < 80) { // floor
			 * is dirtRows surface = 0; } else if (surface < 98) { // floor is down 1 surface = 1; } else { // (surface < 100) floor is down 2 surface = 2; }
			 */
			surface = prevSurfaceLR + surface;
			surface = Math.min(surface, (mapHeight - CurrentBiome.maxBiomeHeight)); // will not go higher than mapHeight - maxBiomeHeight
			surface = Math.max(surface, CurrentBiome.minBiomeHeight);// will not go less than minBiomeHeight
			prevSurfaceLR = surface;
			if (x == 1) {
				playerStartSpot = surface;
			}
			// PER ROW
			for (int y = 0; y < mapHeight; y++) {
				int blockID = 0;
				if (y < surface) {

				} else if (y == surface) {
					blockID = 2;
				} else if (y <= surface + 3) {
					blockID = 1;
				} else if (y > surface + 3) {
					blockID = 3;
				}
				chunk.get(currentScreen).get(y)[x] = (new block(imageFileNames[blockID], blockID, main.blockIDNotBackground[blockID], main.blockIDDiggable[blockID],
						main.blockIDLightToSubtract[blockID]));
				// System.out.println("Block " blockID " at x=" x " y=" y chunk.get(currentScreen).get(y).get(x));
				chunk.get(currentScreen).get(y)[x].setBounds((x * blockHeight), ((y) * blockHeight), blockHeight, blockHeight);
				chunk.get(currentScreen).get(y)[x].setOpaque(false);
				add(chunk.get(currentScreen).get(y)[x], 0);
			}
		}
	}

	public void drawStructures(Biome CurrentBiome) {

	}

	public void drawDirt() { // Draws the dirt
		startTime = System.currentTimeMillis();
		for (int i = Math.abs(dirtRows - mapHeight); i < mapHeight; i++) {
			for (int x = 0; x < mapWidth; x++) {
				chunk.get(currentScreen).get(i)[x] = (new block(imageFileNames[1], 1, main.blockIDNotBackground[1], main.blockIDDiggable[1], main.blockIDLightToSubtract[1]));
				chunk.get(currentScreen).get(i)[x].setBounds((x * blockHeight), ((i) * blockHeight), blockHeight, blockHeight);
				chunk.get(currentScreen).get(i)[x].setOpaque(false);
				add(chunk.get(currentScreen).get(i)[x], 0);
			}

		}
		System.out.println("Dirt Drawn" + " In " + (System.currentTimeMillis() - startTime) + " Milliseconds");
	}
	
	public void drawAir() {
		for (int i = 0; i < Math.abs(dirtRows - mapHeight); i++) {
			for (int x = 0; x < mapWidth; x++) {
				chunk.get(currentScreen).get(i)[x] = (new block(imageFileNames[0], 1, main.blockIDNotBackground[0], main.blockIDDiggable[0], main.blockIDLightToSubtract[0]));
				chunk.get(currentScreen).get(i)[x].setBounds((x * blockHeight), ((i) * blockHeight), blockHeight, blockHeight);
				chunk.get(currentScreen).get(i)[x].setOpaque(false);
				add(chunk.get(currentScreen).get(i)[x], 0);
			}
		}
	}

	public void drawGrass() { // Draws the grass
		startTime = System.currentTimeMillis();
		int current = 0;
		int rowID = Math.abs(dirtRows - mapHeight) - 1;
		for (int x = 0; x < mapWidth; x++) {
			chunk.get(currentScreen).get(rowID)[current] = (new block(imageFileNames[2], 2, main.blockIDNotBackground[2], main.blockIDDiggable[2], main.blockIDLightToSubtract[2]));
			chunk.get(currentScreen).get(rowID)[current].setBounds((x * blockHeight), ((rowID) * blockHeight), blockHeight, blockHeight);
			chunk.get(currentScreen).get(rowID)[current].setOpaque(false);
			add(chunk.get(currentScreen).get(rowID)[current], 1);
			current++;
		}
		System.out.println("Grass Drawn" + " In " + (System.currentTimeMillis() - startTime) + " Milliseconds");
	}

	public void drawPlayer(Color skinColor, Color pantsColor, Color shirtColor, Color shoeColor, Integer[] playerPosition) { // Draws the player
		startTime = System.currentTimeMillis();
		player = new player(skinColor, pantsColor, shirtColor, shoeColor);
		try {
			player.setBounds(playerPosition[0], playerPosition[1], player.getPlayerWidth(), player.getPlayerHeight());
		} catch (NullPointerException ex) {
			setPlayerPosition(false);
		}
		player.setOpaque(false);
		add(player, 0);
		System.out.println("Player Drawn" + " In " + (System.currentTimeMillis() - startTime) + " Milliseconds");
	}

	public void mouseClicked(int xRow, int yRow, String fileName) {
		startTime = System.currentTimeMillis();
		Boolean blockExists = false;
		int blockNum = 0;
		try {
			if (chunk.get(currentScreen).get(yRow)[xRow] != null) {
				blockExists = true;
				blockNum = xRow;
			}
			if (blockExists == false) {
				placeNewBlock(xRow, yRow, fileName);
			} else if (!chunk.get(currentScreen).get(yRow)[xRow].diggable) {
				placeNewBlock(xRow, yRow, fileName);
			} else {
				startToMineBlock(xRow, yRow);
			}
		} catch (IndexOutOfBoundsException ex) {

		}
		System.out.println("Mouse Event" + " In " + (System.currentTimeMillis() - startTime) + " Milliseconds");

	}

	public void placeNewBlock(int xRow, int yRow, String fileName) {
		startTime = System.currentTimeMillis();
		int id = inventoryBar.inventoryBarButtons[inventoryBar.selected].getBlockID();
		selectedBlockKind = main.getImageFileNames()[id]; // Gets what block you have in your inventory
		if (!selectedBlockKind.equals(new String(imageFileNames[0])) // Checks if a block is there
				&& inventoryBar.inventoryBarButtons[inventoryBar.selected].getAmount() > 0 // Checks if you have blocks to place
				&& main.getInventoryState() == false) { // Checks if your inventory is closed
			chunk.get(currentScreen).get(yRow)[xRow] = (new block(selectedBlockKind, id, main.blockIDNotBackground[id], main.blockIDDiggable[id], main.blockIDLightToSubtract[id]));
			chunk.get(currentScreen).get(yRow)[xRow].setBounds(xRow * blockHeight, yRow * blockHeight, blockHeight, blockHeight);
			if (creative == false) {
				inventoryBar.inventoryBarButtons[inventoryBar.selected].subtractOne();
			}
			add(chunk.get(currentScreen).get(yRow)[xRow], 2);
			if (inventoryBar.inventoryBarButtons[inventoryBar.selected].getAmount() <= 0) {
				inventoryBar.removeButton(inventoryBar.selected); // Removes the block from your hotbar
			}
		}
		updateLighting();
		System.out.println("Block Placed" + " In " + (System.currentTimeMillis() - startTime) + " Milliseconds");
	}

	public int removeBlock(int xRow, int yRow) {
		startTime = System.currentTimeMillis();
		remove(chunk.get(currentScreen).get(yRow)[xRow]);
		int id = chunk.get(currentScreen).get(yRow)[xRow].id;
		chunk.get(currentScreen).get(yRow)[xRow] = new block(main.getImageFileNames()[0], 0, main.blockIDNotBackground[0], main.blockIDDiggable[0], main.blockIDLightToSubtract[0]);
		chunk.get(currentScreen).get(yRow)[xRow].setBounds(xRow * main.blockHeight, yRow * main.blockHeight, main.blockHeight, main.blockHeight);
		add(chunk.get(currentScreen).get(yRow)[xRow], 1);
		repaintObjects();
		updateLighting();
		System.out.println("Block Removed" + " In " + (System.currentTimeMillis() - startTime) + " Milliseconds");
		return id;
	}

	public void startToMineBlock(int xRow, int yRow) {
		mine.setRunning(xRow, yRow);
	}

	public void mineBlockAt(int xRow, int yRow) {
		startTime = System.currentTimeMillis();
		int id = removeBlock(xRow, yRow);
		Boolean needsToBeRun = true;
		for (int i = 0; i < inventoryBar.inventoryBarButtons.length; i++) {
			if (id == inventoryBar.inventoryBarButtons[i].blockID) {
				if (inventoryBar.inventoryBarButtons[i].amount != main.stackHeight) {
					inventoryBar.inventoryBarButtons[i].addBlock(1, id);
					needsToBeRun = false;
					break;
				}
			}
		}
		if (needsToBeRun == true) {
			for (int x = 0; x < inventory.blockNumber; x++) {
				for (int y = 0; y < inventory.inventoryHeight; y++) {
					if (inventory.inventoryButtons[x][y].blockID == id) {
						if (inventory.inventoryButtons[x][y].amount != main.stackHeight) {
							inventory.inventoryButtons[x][y].addBlock(1, id);
							needsToBeRun = false;
							break;
						}
					}
				}
				if (needsToBeRun == false) {
					break;
				}
			}
		}
		if (needsToBeRun == true) {
			for (int i = 0; i < inventoryBar.inventoryBarButtons.length; i++) {
				if (0 == inventoryBar.inventoryBarButtons[i].blockID) {
					inventoryBar.inventoryBarButtons[i].addBlock(1, id);
					inventoryBar.repaintButton(i);
					needsToBeRun = false;
					break;
				}
			}
		}
		if (needsToBeRun == true) {
			for (int x = 0; x < inventory.blockNumber; x++) {
				for (int y = 0; y < inventory.inventoryHeight; y++) {
					if (0 == inventory.inventoryButtons[x][y].blockID) {
						inventory.inventoryButtons[x][y].addBlock(1, id);
						inventory.repaintButton(x, y);
						needsToBeRun = false;
						break;
					}
				}
				if (needsToBeRun == false) {
					break;
				}
			}
		}
		mine.stopRunning();
		System.out.println("Block Mined" + " In " + (System.currentTimeMillis() - startTime) + " Milliseconds");
	}

	public void stopMining() {
		if (mine.running == true) {
			mine.stopRunning();
		}
	}

	public void initAndDrawInventory(int inventoryBlock, int inventoryGap, int inventoryExtra, int inventoryHeight, Color defaultBoxColor, Color swapBoxColor, Color selectedBoxColor,
			Color backgroundColor, Color textColor, int stackHeight, inventoryButton[][] inventButtons, inventoryButton[] inventBarButtons) { // Initializes and draws the
		startTime = System.currentTimeMillis();
		// inventory
		inventoryBar = new inventoryBar(inventoryBlock, inventoryGap, inventoryExtra, defaultBoxColor, swapBoxColor, selectedBoxColor, backgroundColor, textColor, stackHeight, inventBarButtons);
		int width = inventoryBar.width;
		int height = inventoryBar.height;
		inventoryBar.setBounds((main.screenWidth / 2 - width / 2), main.screenHeight - height * 3, width, height);
		add(inventoryBar, 0);
		selectedBlockKind = inventoryBar.setSelected(0);
		try {
			inventory = new inventory(inventoryBlock, inventoryGap, inventoryExtra, inventoryHeight, defaultBoxColor, swapBoxColor, backgroundColor, textColor, stackHeight, inventButtons);
		} catch (NullPointerException ex) {
			inventory = new inventory(inventoryBlock, inventoryGap, inventoryExtra, inventoryHeight, defaultBoxColor, swapBoxColor, backgroundColor, textColor, stackHeight, null);
		}
		width = inventory.width;
		height = inventory.height;
		inventory.setBounds((main.screenWidth - inventory.width) / 2, (main.screenHeight - inventory.height) / 2, width, height);
		inventory.setVisible(false);
		inventoryBar.setFocusable(false);
		add(inventory, 0);
		System.out.println("Inventory Drawn" + " In " + (System.currentTimeMillis() - startTime) + " Milliseconds");
	}

	public void initTaskManager() {
		startTime = System.currentTimeMillis();
		manager = new taskManager();
		System.out.println("Task Manager Initialized " + " In " + (System.currentTimeMillis() - startTime) + " Milliseconds");
	}

	public void startUserControl(int mineBlockSpeed) { // Starts the user
														// controls
		startTime = System.currentTimeMillis();
		startKeyControls();
		startMouseControl(mineBlockSpeed);
		System.out.println("User Controls Started" + " In " + (System.currentTimeMillis() - startTime) + " Milliseconds");
	}

	public void startKeyControls() { // Adds the key listner
		startTime = System.currentTimeMillis();
		keyListener = new keyControls();
		this.addKeyListener(keyListener);
		movePlayerTask move = new movePlayerTask();
		manager.addTask(move, 1);
		this.setLayout(null);
		System.out.println("Key Controls Started" + " In " + (System.currentTimeMillis() - startTime) + " Milliseconds");
	}

	public void startMouseControl(int mineBlockSpeed) { // Starts the thread that moves the block selector
		// hideCursor();
		startTime = System.currentTimeMillis();
		selectMapBlock = new selectorBlock();
		selectMapBlock.setBounds(128, 128, blockHeight, blockHeight);
		selectMapBlock.setOpaque(false);
		add(selectMapBlock, 0);
		task select = new moveSelectorBlockTask();
		int[] nums;
		nums = manager.addTask(select);
		selectTaskTaskNumber = nums[1];
		selectTaskCoreNumber = nums[0];
		placer = new clickEvent();
		this.addMouseListener(placer);
		mine = new mineBlockTask(mineBlockSpeed);
		manager.addTask(mine);
		System.out.println("Mouse Controls Started" + " In " + (System.currentTimeMillis() - startTime) + " Milliseconds");

	}

	public void hideCursor() { // Hides the cursor, undecided if this should be done
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		super.getContentPane().setCursor(blankCursor);
	}

	public void initPhysics() { // Initializes the physics
		startTime = System.currentTimeMillis();
		physics = new physicsEngine();
		System.out.println("Physics Initalized" + " In " + (System.currentTimeMillis() - startTime) + " Milliseconds");
	}

	public void startPhysics() { // Starts the physics
		startTime = System.currentTimeMillis();
		physics.start();
		if (creative == false) {
			task thisTask = new gravityTask();
			manager.addTask(thisTask, 1);
		}
		System.out.println("Physics Started" + " In " + (System.currentTimeMillis() - startTime) + " Milliseconds");
	}

	public void startSave() {
		startTime = System.currentTimeMillis();
		saveTask task = new saveTask();
		manager.addTask(task);
		System.out.println("Save Started" + " In " + (System.currentTimeMillis() - startTime) + " Milliseconds");
	}

	public void doneJumping() { // Called when the jump thread is done excuting
		jumping = false;
	}

	public block getBlock(int i, int x) { // Gets the information of a block
		try {
			return chunk.get(currentScreen).get(i)[x];
		} catch (IndexOutOfBoundsException ex) {
			return null;
		}
	}

	public block getBlock(int screenNum1, int i, int x) { // Gets the info of a block
		return chunk.get(screenNum1).get(i)[x];
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

	public Boolean getLeftOrRight() {
		if (keyListener.aPressed == true && keyListener.dPressed == true) {
			return null;
		}
		if (keyListener.aPressed == true) {
			return true;
		}
		if (keyListener.dPressed == true) {
			return false;
		}
		return null;
	}

	public Boolean getUpOrDown() {
		if (keyListener.sPressed == true && keyListener.wPressed == true) {
			return null;
		}
		if (keyListener.wPressed == true) {
			return true;
		}
		if (keyListener.sPressed == true) {
			return false;
		}
		return null;
	}

	public void clearMap() {
		startTime = System.currentTimeMillis();
		for (int y = 0; y < chunk.get(currentScreen).size(); y++) {
			for (int x = 0; x < chunk.get(currentScreen).get(y).length; x++) {
				if (chunk.get(currentScreen).get(y)[x] == null) {
					continue;
				}
				chunk.get(currentScreen).get(y)[x].setVisible(false);
				remove(chunk.get(currentScreen).get(y)[x]);
			}
		}
		System.out.println("Map Cleard" + " In " + (System.currentTimeMillis() - startTime) + " Milliseconds");
	}

	public int changeCurrentScreen(Boolean backwardsOrForwards) {
		startTime = System.currentTimeMillis();
		clearMap();
		if (backwardsOrForwards) {
			currentScreen--;
		} else {
			currentScreen++;
		}
		drawMap();
		repaintObjects();
		System.out.println("Screen Changed" + " In " + (System.currentTimeMillis() - startTime) + " Milliseconds");
		return 0;
	}

	public void setPlayerPosition(Boolean endOrStart) { // True is end, false is start
		int playerLastY = player.getBounds().y;
		if (endOrStart == false) {
			player.setBounds(blockHeight, playerLastY, player.getPlayerWidth(), player.getPlayerHeight());
		} else {
			player.setBounds(main.screenWidth - blockHeight, playerLastY, player.getPlayerWidth(), player.getPlayerHeight());
		}

	}

	public void repaintObjects() {
		startTime = System.currentTimeMillis();
		remove(player);
		add(player, 0);
		remove(inventoryBar);
		add(inventoryBar, 0);
		remove(inventory);
		add(inventory, 0);
		remove(selectMapBlock);
		add(selectMapBlock, 1);
		repaint();
		System.out.println("Objects Repainted" + " In " + (System.currentTimeMillis() - startTime) + " Milliseconds");
	}

	public void updateLighting() {
		int light = 0;
		List<Integer> lightSourcesX = new LinkedList<Integer>();
		List<Integer> lightSourcesY = new LinkedList<Integer>();
		for (int i = 0; i < chunk.get(currentScreen).size(); i++) {
			for (int x = 0; x < chunk.get(currentScreen).get(i).length; x++) {
				if (chunk.get(currentScreen).get(i)[x].lightSource) {
					lightSourcesX.add(x);
					lightSourcesY.add(i);
				} else {
					chunk.get(currentScreen).get(i)[x].light = 0;
				}
			}
		}
		for (int z = 0; z < lightSourcesX.size(); z++) {
			for (int i = lightSourcesX.get(z); i >= 0; i--) {
				for (int x = lightSourcesY.get(z); x >= 0; x--) {
					if (i == lightSourcesX.get(z) && x == lightSourcesY.get(z)) {

					} else {
						if (x != lightSourcesY.get(z)) {
							light = chunk.get(currentScreen).get(x)[i].light + chunk.get(currentScreen).get(x + 1)[i].lightToPass;
							if (light < 0) {
								chunk.get(currentScreen).get(x)[i].setLight(0);
							} else if ((light < 100)) {
								chunk.get(currentScreen).get(x)[i].setLight(light);
							} else {
								chunk.get(currentScreen).get(x)[i].setLight(100);
							}
						} else {
							light = chunk.get(currentScreen).get(x)[i].light + chunk.get(currentScreen).get(x)[i + 1].lightToPass;
							if (light < 0) {
								chunk.get(currentScreen).get(x)[i].setLight(0);
							} else if ((light < 100)) {
								chunk.get(currentScreen).get(x)[i].setLight(light);
							} else {
								chunk.get(currentScreen).get(x)[i].setLight(100);
							}
						}
					}
				}
				for (int x = lightSourcesY.get(z) + 1; x < mapHeight - 1; x++) {
					if (x == lightSourcesY.get(z) + 1 && i == lightSourcesX.get(z)) {
						light = chunk.get(currentScreen).get(x)[i].light + chunk.get(currentScreen).get(x - 1)[i].lightToPass;
						if (light < 0) {
							chunk.get(currentScreen).get(x)[i].setLight(0);
						} else if ((light < 100)) {
							chunk.get(currentScreen).get(x)[i].setLight(light);
						} else {
							chunk.get(currentScreen).get(x)[i].setLight(100);
						}
					} else {
						if (x == lightSourcesY.get(z) + 1) {
							light = chunk.get(currentScreen).get(x)[i].light + chunk.get(currentScreen).get(x)[i + 1].lightToPass;
							if (light < 0) {
								chunk.get(currentScreen).get(x)[i].setLight(0);
							} else if ((light < 100)) {
								chunk.get(currentScreen).get(x)[i].setLight(light);
							} else {
								chunk.get(currentScreen).get(x)[i].setLight(100);
							}
						} else {
							light = chunk.get(currentScreen).get(x)[i].light + chunk.get(currentScreen).get(x - 1)[i].lightToPass;
							if (light < 0) {
								chunk.get(currentScreen).get(x)[i].setLight(0);
							} else if ((light < 100)) {
								chunk.get(currentScreen).get(x)[i].setLight(light);
							} else {
								chunk.get(currentScreen).get(x)[i].setLight(100);
							}
						}
					}
				}
			}
			for (int i = lightSourcesX.get(z) + 1; i < mapWidth; i++) {
				for (int x = lightSourcesY.get(z); x >= 0; x--) {
					if (x == lightSourcesY.get(z)) {
						light = chunk.get(currentScreen).get(x)[i].light + chunk.get(currentScreen).get(x)[i - 1].lightToPass;
						if (light < 0) {
							chunk.get(currentScreen).get(x)[i].setLight(0);
						} else if ((light < 100)) {
							chunk.get(currentScreen).get(x)[i].setLight(light);
						} else {
							chunk.get(currentScreen).get(x)[i].setLight(100);
						}
					} else {
						light = chunk.get(currentScreen).get(x)[i].light + chunk.get(currentScreen).get(x + 1)[i].lightToPass;
						if (light < 0) {
							chunk.get(currentScreen).get(x)[i].setLight(0);
						} else if ((light < 100)) {
							chunk.get(currentScreen).get(x)[i].setLight(light);
						} else {
							chunk.get(currentScreen).get(x)[i].setLight(100);
						}
					}
				}
				for (int x = lightSourcesY.get(z) + 1; x < mapHeight; x++) {
					if (x == lightSourcesY.get(z) + 1 && i == lightSourcesX.get(z)) {
						light = chunk.get(currentScreen).get(x)[i].light + chunk.get(currentScreen).get(x - 1)[i].lightToPass;
						if (light < 0) {
							chunk.get(currentScreen).get(x)[i].setLight(0);
						} else if ((light < 100)) {
							chunk.get(currentScreen).get(x)[i].setLight(light);
						} else {
							chunk.get(currentScreen).get(x)[i].setLight(100);
						}
					} else {
						if (x == lightSourcesY.get(z) + 1) {
							light = chunk.get(currentScreen).get(x)[i].light + chunk.get(currentScreen).get(x)[i - 1].lightToPass;
							if (light < 0) {
								chunk.get(currentScreen).get(x)[i].setLight(0);
							} else if ((light < 100)) {
								chunk.get(currentScreen).get(x)[i].setLight(light);
							} else {
								chunk.get(currentScreen).get(x)[i].setLight(100);
							}
						} else {
							light = chunk.get(currentScreen).get(x)[i].light + chunk.get(currentScreen).get(x - 1)[i].lightToPass;
							if (light < 0) {
								chunk.get(currentScreen).get(x)[i].setLight(0);
							} else if ((light < 100)) {
								chunk.get(currentScreen).get(x)[i].setLight(light);
							} else {
								chunk.get(currentScreen).get(x)[i].setLight(100);
							}
						}
					}
				}
			}
		}
		repaint();
	}

}
