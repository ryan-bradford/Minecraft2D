package main;

import java.awt.Toolkit;

import java.util.ArrayList;

import player.player;
import block.block;
import map.map;

public class main {

	static map map;
	public static int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static int screenHeight = Toolkit.getDefaultToolkit()
			.getScreenSize().height;
	private static ArrayList<block> blocks;

	public static void main(String[] args) {
		screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		map = new map(false);
		map.pack();
		map.setBounds(0, 0, screenWidth, screenHeight);
		map.setVisible(true);
	}

	public static ArrayList<block> getBlocks(int height) {
		try {
		blocks = new ArrayList<block>();
		for (int i = 0; i < (map.mapWidth); i++) {
			blocks.add(map.getBlock(height, i));
		}
		return blocks;
		} catch(ArrayIndexOutOfBoundsException ex) {
			
		}
		return null;
	}
	
	public static player getPlayer() {
		return map.player;
	}
	
	public static int getWalkSpeed() {
		return map.walkSpeed;
	}
	
	public static int getBlockHeight() {
		return map.blockHeight;
	}
	
	public static void movePlayer(int x, int y) {
		map.player.setBounds(map.player.getBounds().x + x,
				(map.player.getBounds().y) + y,
				map.player.getBounds().width,
				map.player.getBounds().height);
	}
	
	public static Boolean getJumping() {
		return map.jumping;
	}
	
	public static void doneJumping() {
		map.doneJumping();
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
}
