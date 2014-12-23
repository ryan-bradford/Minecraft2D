package map;

import javax.swing.JFrame;

import physicsEngine.physicsEngine;
import player.player;
import block.block;
import block.air;
import block.selectorBlock;
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
 * 
 * To Do:
 * Send file names in the constructors*/

public class map extends JFrame {
	public ArrayList<ArrayList<block>> chunk; // Horizonatal Rows that are 1
												// tall
	public player player;
	public selectorBlock selectMapBlock;
	public moveSelectorBlock selectMapBlockThread;
	public inventoryBar inventoryBar;
	public inventory inventory;
	public placeBlock placer;
	public jump jump;
	public movePlayer moveLeft;
	public movePlayer moveRight;
	public movePlayer moveUp;
	public movePlayer moveDown;
	public air air;
	public physicsEngine physics;
	public String selectedBlockKind;
	public Boolean jumping;
	public Boolean creative;
	public Boolean inventoryOpen;
	public String[] imageFileNames;
	public int blockHeight;
	public int mapWidth;
	public int mapHeight;
	public int jumpSpeed;
	public int gravitySpeed;
	public int dirtRows;
	public double jumpDistance;
	public int walkSpeed;
	public double startTime = System.nanoTime();

	public map(Boolean creative, int blockHeight1, int dirtHeightInBlocks,
			int inventoryBlock, int inventoryGap, int inventoryExtra,
			int inventoryHeight, Color defaultBoxColor, Color swapBoxColor,
			Color selectedBoxColor, Color backgroundColor, Color textColor,
			Color airColor, Color skinColor, Color pantsColor, Color shirtColor, Color shoeColor, String[] imageFileNames) {
		initVar(creative, blockHeight1, dirtHeightInBlocks, imageFileNames);
		drawMap(airColor);
		drawPlayer(skinColor, pantsColor, shirtColor, shoeColor);
		initPhysics();
		startPhysics();
		startUserControl();
		initAndDrawInventory(inventoryBlock, inventoryGap, inventoryExtra,
				inventoryHeight, defaultBoxColor, swapBoxColor,
				selectedBoxColor, backgroundColor, textColor);
		System.out.println("The Game Has Begun!");
	}

	public void initVar(Boolean creativ, int blockHeight1,
			int dirtHeightInBlocks, String[] imageFileNames1) {
		jumping = false;
		imageFileNames = imageFileNames1;
		inventoryOpen = false;
		blockHeight = blockHeight1; // Sets Block Pixel Height
		mapWidth = (main.screenWidth) / blockHeight;
		mapHeight = (main.screenHeight) / blockHeight;
		dirtRows = dirtHeightInBlocks;
		jumpSpeed = blockHeight * 2;// Pixels per Second
		gravitySpeed = blockHeight * 2;// Pixels per Second
		jumpDistance = 1.5; // In Block Width
		walkSpeed = blockHeight * 3;
		creative = creativ;
		System.out.println("Variables Initialized" + " In "
				+ (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void drawMap(Color airColor) {
		chunk = new ArrayList<ArrayList<block>>();
		for (int i = 0; i < mapHeight; i++) {
			chunk.add(new ArrayList<block>());
		}
		drawAir(airColor);
		drawDirt();
		drawGrass();
		System.out.println("Map Drawn" + " In "
				+ (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void drawDirt() {
		for (int i = Math.abs(dirtRows - mapHeight); i < mapHeight; i++) {
			for (int x = 0; x < mapWidth; x++) {
				chunk.get(i).add(new block(imageFileNames[1]));
				chunk.get(i)
						.get(x)
						.setBounds((x * blockHeight), ((i) * blockHeight),
								blockHeight, blockHeight);
				chunk.get(i).get(x).setOpaque(false);
				add(chunk.get(i).get(x), 0);
			}

		}
		System.out.println("Dirt Drawn" + " In "
				+ (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void drawGrass() {
		int current = 0;
		int rowID = Math.abs(dirtRows - mapHeight) - 1;
		for (int x = 0; x < mapWidth; x++) {
			chunk.get(rowID).add(new block(imageFileNames[2]));
			chunk.get(rowID)
					.get(current)
					.setBounds((x * blockHeight), ((rowID) * blockHeight),
							blockHeight, blockHeight);
			chunk.get(rowID).get(current).setOpaque(false);
			add(chunk.get(rowID).get(current), 1);
			current++;
		}
		System.out.println("Grass Drawn" + " In "
				+ (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void drawAir(Color airColor) {
		air = new air(airColor);
		air.setBounds(0, 0, main.screenWidth, main.screenHeight);
		air.setOpaque(false);
		add(air, 0);
		System.out.println("Air Drawn" + " In "
				+ (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void drawPlayer(Color skinColor, Color pantsColor, Color shirtColor, Color shoeColor) {
		player = new player(skinColor, pantsColor, shirtColor, shoeColor);
		player.setBounds(((main.screenWidth) / 2), (((Math.abs(dirtRows
				- mapHeight) - 1) * blockHeight) - player.getPlayerHeight()),
				player.getPlayerWidth(), player.getPlayerHeight());
		player.setOpaque(false);
		add(player, 0);
		System.out.println("Player Drawn" + " In "
				+ (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void drawNewBlock(int xCord, int yRow, String fileName) { //Check if block already exists
		selectedBlockKind = main.getImageFileNames()[inventoryBar.inventoryBarButtons[inventoryBar.selected].getBlockID()];
		if (!selectedBlockKind.equals(new String(imageFileNames[0]))
				&& inventoryBar.inventoryBarButtons[inventoryBar.selected].getAmount() > 0
				&& main.getInventoryState() == false) {
			chunk.get(yRow).add(new block(selectedBlockKind));
			int yRowSize = (chunk.get(yRow).size() - 1);
			chunk.get(yRow)
					.get(yRowSize)
					.setBounds(xCord, yRow * blockHeight, blockHeight,
							blockHeight);
					inventoryBar.inventoryBarButtons[inventoryBar.selected].subtractOne();
			add(chunk.get(yRow).get(yRowSize), 2);
			if(inventoryBar.inventoryBarButtons[inventoryBar.selected].getAmount() <= 0) {
				inventoryBar.removeButton(inventoryBar.selected);
			}
		}
	}

	public void initAndDrawInventory(int inventoryBlock, int inventoryGap,
			int inventoryExtra, int inventoryHeight, Color defaultBoxColor,
			Color swapBoxColor, Color selectedBoxColor, Color backgroundColor,
			Color textColor) {
		inventoryBar = new inventoryBar(inventoryBlock, inventoryGap,
				inventoryExtra, defaultBoxColor, swapBoxColor,
				selectedBoxColor, backgroundColor, textColor);
		int width = inventoryBar.width;
		int height = inventoryBar.height;
		inventoryBar.setBounds((main.screenWidth / 2 - width / 2),
				main.screenHeight - height * 3, width, height);
		add(inventoryBar, 0);
		selectedBlockKind = inventoryBar.setSelected(0);
		inventory = new inventory(inventoryBlock, inventoryGap, inventoryExtra,
				inventoryHeight, defaultBoxColor, swapBoxColor,
				backgroundColor, textColor);
		width = inventory.width;
		height = inventory.height;
		inventory.setBounds((main.screenWidth - inventory.width) / 2,
				(main.screenHeight - inventory.height) / 2, width, height);
		inventory.setVisible(false);
		inventoryBar.setFocusable(false);
		add(inventory, 0);
		System.out.println("Inventory Drawn" + " In "
				+ (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void startUserControl() {
		startKeyControls();
		startMouseControl();
		System.out.println("User Controls Started" + " In "
				+ (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void startKeyControls() {
		keyControls keyListener = new keyControls();
		this.addKeyListener(keyListener);
		this.setLayout(null);
	}

	public void startMouseControl() {
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

	public void hideCursor() {
		BufferedImage cursorImg = new BufferedImage(16, 16,
				BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				cursorImg, new Point(0, 0), "blank cursor");
		super.getContentPane().setCursor(blankCursor);
	}

	public void initPhysics() {
		physics = new physicsEngine(creative);
		System.out.println("Physics Initalized" + " In "
				+ (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void startPhysics() {
		physics.start();
		System.out.println("Physics Started" + " In "
				+ (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void doneJumping() {
		jumping = false;
	}

	public block getBlock(int i, int x) {
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

	public Boolean getCollisionLeft() {
		try {
			return physics.getColisionLeft();
		} catch (NullPointerException ex) {
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
