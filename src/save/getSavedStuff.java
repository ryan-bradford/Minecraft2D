package save;

import inventory.inventoryButton;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.main;
import block.block;

public class getSavedStuff {
	static int currentRow = 0;
	static int lastChunkNum = 0;
	static String[] text = getText();
	static Boolean runnable = true;

	public static int getScreenNum() {
		if (runnable) {
			try {
				if (new File(main.fileName).exists()) {
					return Integer.parseInt(String.valueOf(text[1].toCharArray()[1]));
				} else {
					runnable = false;
					return 0;
				}
			} catch (ArrayIndexOutOfBoundsException ex) {
				runnable = false;
				return 0;
			}
		}
		return 0;
	}

	public static int getScreenNumAmmount() {
		if (runnable) {
			int screenNum = 0;
			if (new File(main.fileName).exists()) {
				for (int i = 0; i < text.length; i++) {
					if (text[i].equals("Chunk ")) {
						screenNum++;
					}
				}
				return screenNum;
			} else {
				return 0;
			}
		}
		return 0;
	}

	public static ArrayList<ArrayList<block[]>> getAllScreens() {
		if (runnable) {
			ArrayList<ArrayList<block[]>> screens = new ArrayList<ArrayList<block[]>>();
			for (int i = 0; i < getScreenNumAmmount(); i++) {
				screens.add(getNextScreen());
			}
			return screens;
		}
		return null;
	}

	public static ArrayList<block[]> getNextScreen() {
		if (runnable) {
			int chunkStart = 0;
			int chunkEnd = 0;
			ArrayList<block[]> screen = new ArrayList<block[]>();
			while (!(text[currentRow].equals("Chunk "))) {
				currentRow++;
			}
			currentRow++;
			chunkStart = currentRow;
			lastChunkNum = chunkStart;
			while (!(text[currentRow].equals("End "))) {
				currentRow++;
			}
			currentRow--;
			chunkEnd = currentRow;
			for (int i = 0; i < main.mapHeight; i++) {
				screen.add(new block[main.screenWidth / main.blockHeight + 1]);
			}
			for (int i = chunkStart; i < chunkEnd; i = i + 4) {
				block currentBlock = new block(main.getImageFileNames()[Integer.parseInt(text[i + 2].trim())],
						Integer.parseInt(text[i + 2].trim()));
				currentBlock.health = Integer.parseInt(text[i + 3].trim());
				currentBlock.setBounds(Integer.parseInt(text[i].trim()), Integer.parseInt(text[i + 1].trim()),
						main.blockHeight, main.blockHeight);
				screen.get(Integer.parseInt(text[i + 1].trim()) / main.blockHeight)[Integer.parseInt(text[i].trim())
						/ main.blockHeight] = (currentBlock);
			}
			return screen;
		}
		return null;
	}

	public static Integer[] getPlayerBounds() {
		if (runnable) {
			currentRow = 0;
			if (text != null) {
				while (!text[currentRow].equals("Player ")) {
					currentRow = currentRow + 1;
				}
				return new Integer[] { Integer.parseInt(text[currentRow + 1].trim()),
						Integer.parseInt(text[currentRow + 2].trim()) };
			} else {
				return null;
			}
		}
		return null;
	}

	public static String[] getText() {
		try {
			if (new File(main.fileName).exists()) {
				return FileArrayProvider.readLines(main.fileName);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static inventoryButton[][] getInventoryButtons() {
		if (runnable) {
			inventoryButton[][] buttons = new inventoryButton[main.inventoryBlockNumber][main.inventoryHeight];
			while (!(text[currentRow].equals("Inventory "))) {
				currentRow++;
			}
			currentRow++;
			int start = currentRow;
			for (int i = 0; i < main.inventoryBlockNumber; i++) {
				for (int x = 0; x < main.inventoryHeight; x++) {
					Image currentBlock = null;
					try {
						currentBlock = ImageIO.read(new java.io.File(main.getImageFileNames()[Integer
								.parseInt(text[start + 1].trim())]));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					buttons[i][x] = new inventoryButton(currentBlock, Integer.parseInt(text[start].trim()),
							Integer.parseInt(text[start + 1].trim()), main.stackHeight, main.textColor);
					start = start + 2;
				}
			}
			return buttons;
		}
		return null;
	}

	public static inventoryButton[] getInventoryBarButtons() {
		if (runnable) {
			inventoryButton[] buttons = new inventoryButton[main.inventoryBlockNumber];
			while (!(text[currentRow].equals("Inventory Bar "))) {
				currentRow++;
			}
			currentRow++;
			int start = currentRow;
			for (int i = 0; i < main.inventoryBlockNumber; i++) {
				Image currentBlock = null;
				try {
					currentBlock = ImageIO.read(new java.io.File(main.getImageFileNames()[Integer
							.parseInt(text[start + 1].trim())]));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				buttons[i] = new inventoryButton(currentBlock, Integer.parseInt(text[start].trim()),
						Integer.parseInt(text[start + 1].trim()), main.stackHeight, main.textColor);
				start = start + 2;			
				}
			return buttons;
		}
		return null;
	}
}