package save;

import java.io.IOException;
import java.util.ArrayList;
import main.main;

import block.block;

public class getSavedStuff {
	static int currentRow = 0;
	static String[] text = getText();

	public static ArrayList<ArrayList<ArrayList<block>>> getScreens() {
		return null;
	}

	public static Integer[] getPlayerBounds() {
		if (text != null) {
			while (! text[currentRow].equals("Player ")) {
				currentRow = currentRow + 1;
			}
			System.out.println(currentRow);
			return new Integer[] { Integer.parseInt(text[currentRow + 1].trim()),
					Integer.parseInt(text[currentRow + 2].trim()) };
		} else {
			return null;
		}
	}

	public static String[] getText() {
		try {
			return FileArrayProvider.readLines(main.fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
