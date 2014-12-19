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

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class map extends JFrame {
	public ArrayList<ArrayList<block>> chunk; // Horizonatal Rows that are 1
												// tall
	public player player;
	public selectorBlock select;
	public moveSelectorBlock selectThread;
	public inventoryBar inventoryBar;
	public placeBlock placer;
	public jump jump;
	public movePlayer moveLeft;
	public movePlayer moveRight;
	public movePlayer moveUp;
	public movePlayer moveDown;
	public air air;
	public physicsEngine physics;
	public Boolean jumping;
	public Boolean creative;
	public int blockHeight;
	public int mapHeightUntilAir;
	public int mapWidth;
	public int mapHeight;
	public int mapAir;
	public int jumpSpeed;
	public int gravitySpeed;
	public double jumpDistance;
	public int walkSpeed;
	public double startTime = System.nanoTime();

	public map(Boolean creative, int blockHeight1, int inventoryBlock, int inventoryGap, int inventoryExtra) {
		initVar(creative, blockHeight1);
		drawMap();
		drawPlayer();
		initPhysics();
		startPhysics();
		startUserControl();
		drawInventoryBar(inventoryBlock, inventoryGap, inventoryExtra);
		System.out.println("The Game Has Begun!");
	}

	public void initVar(Boolean creativ, int blockHeight1) {
		jumping = false;
		blockHeight = blockHeight1; // Sets Block Pixel Height
		mapWidth = (main.screenWidth) / blockHeight;
		mapHeight = (main.screenHeight) // Calculates
										// the
										// total(top
										// to
										// bottom)
										// map
										// height
										// in
										// blocks
				/ blockHeight;
		mapHeightUntilAir = mapHeight/2 -1; // Sets Map Height From the Base to the grass in block width
		mapAir = mapHeight - mapHeightUntilAir; // Calculates the height of the
												// amount of air
		jumpSpeed = blockHeight * 2;// Pixels per Second
		gravitySpeed = blockHeight * 2;// Pixels per Second
		jumpDistance = 1.5; // In Block Width
		walkSpeed = blockHeight * 3;
		creative = creativ;
		System.out.println("Variables Initialized" + " In "
				+ (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void drawMap() {
		chunk = new ArrayList<ArrayList<block>>();
		for (int i = 0; i < mapHeight; i++) {
			chunk.add(new ArrayList<block>());
		}
		drawAir();
		drawDirt();
		drawGrass();
		System.out.println("Map Drawn" + " In "
				+ (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void drawDirt() {
		int current = 0;
		int rowID = (mapAir);
		for (int i = 0; i < mapHeight - mapAir; i++) {
			current = 0;
			for (int x = 0; x < mapWidth; x++) {
				chunk.get(rowID).add(new block("dirt.jpg"));
				chunk.get(rowID)
						.get(current)
						.setBounds((x * blockHeight), ((rowID) * blockHeight),
								blockHeight, blockHeight);
				chunk.get(rowID).get(current).setOpaque(false);
				add(chunk.get(rowID).get(current), 0);
				current++;
			}
			rowID = rowID + 1;

		}
		System.out.println("Dirt Drawn" + " In "
				+ (System.nanoTime() - startTime) + " Nanoseconds");
		// int chunkNum = 5;
		// chunk.get(chunkNum).add(new block("dirt.jpg"));
		// chunk.get(chunkNum).get(0).setBounds(600, (chunkNum*blockHeight),
		// blockHeight, blockHeight);
		// add(chunk.get(chunkNum).get(0),1);
	}

	public void drawGrass() {
		int current = 0;
		int rowID = (mapAir - 1);
		for (int x = 0; x < mapWidth; x++) {
			chunk.get(rowID).add(new block("grass.jpg"));
			chunk.get(rowID)
					.get(current)
					.setBounds((x * blockHeight), ((mapAir - 1) * blockHeight),
							blockHeight, blockHeight);
			chunk.get(rowID).get(current).setOpaque(false);
			add(chunk.get(rowID).get(current), 1);
			current++;
		}
		System.out.println("Grass Drawn" + " In "
				+ (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void drawAir() {
		air = new air();
		air.setBounds(0, 0, main.screenWidth, main.screenHeight);
	    air.setOpaque(false);
		add(air, 0);
		System.out.println("Air Drawn" + " In "
				+ (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void drawPlayer() {
		player = new player();
		player.setBounds(((main.screenWidth) / 2),
				(((mapAir - 1) * blockHeight) - player.getPlayerHeight()),
				player.getPlayerWidth(), player.getPlayerHeight());
	    player.setOpaque(false);
		add(player, 0);
		System.out.println("Player Drawn" + " In "
				+ (System.nanoTime() - startTime) + " Nanoseconds");
	}

	public void drawNewBlock(int xCord, int yRow, String fileName) {
		chunk.get(yRow).add(new block(fileName));
		int yRowSize = chunk.get(yRow).size() - 1;
		chunk.get(yRow).get(yRowSize)
				.setBounds(xCord, yRow * blockHeight, blockHeight, blockHeight);
		add(chunk.get(yRow).get(yRowSize), 1);
	}

	public void drawInventoryBar(int inventoryBlock, int inventoryGap, int inventoryExtra) {
		inventoryBar = new inventoryBar(inventoryBlock, inventoryGap, inventoryExtra);
		int width = inventoryBar.width;
		int height = inventoryBar.height;
		System.out.println(main.screenHeight - height);
		inventoryBar.setBounds((main.screenWidth/2 - width/2), main.screenHeight - height*3, width, height);
		add(inventoryBar, 0);
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
		select = new selectorBlock();
		select.setBounds(128, 128, blockHeight, blockHeight);
	    select.setOpaque(false);
		add(select, 0);
		selectThread = new moveSelectorBlock();
		selectThread.start();
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

}
