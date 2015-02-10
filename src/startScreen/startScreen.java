package startScreen;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.plaf.LayerUI;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import save.getSavedStuff;
import main.main;
import map.map;

public class startScreen {

	JButton newGame;
	JButton loadGame;
	JButton[][] selectGame;
	JTextPane message;
	JTextPane enterWorldName;
	JTextPane noSavedGames;
	JButton done;
	Boolean newOrOld;
	String[] savedGames;
	SimpleAttributeSet center;
	map map1;

	public startScreen() {
		map1 = new map(false, 64, main.dirtHeightInBlocks, main.inventoryBlockNumber, main.inventoryGap, main.inventoryExtra, main.inventoryHeight, main.defaultBoxColor, main.swapBoxColor,
				main.selectedBoxColor, main.backgroundColor, main.textColor, main.airColor, main.skinColor, main.pantsColor, main.shirtColor, main.shoeColor, main.imageFileNames, main.stackHeight,
				main.jumpDistance, main.jumpSpeed, main.gravitySpeed, main.walkSpeed, main.mineBlockSpeed, null, null, null, null, 0, "normal", main.worldSeed, true);
		map1.pack();
		map1.setBounds(0, 0, main.screenWidth, main.screenHeight);
		map1.setVisible(true);
		map1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		map1.getContentPane().setBackground(main.airColor);
		map1.setLayout(null);
		center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		map1.setLayout(null);
		savedGames = getSavedStuff.getWorldNames();
		newGame = new JButton("New/Overwrite Game");
		newGame.setBounds(main.screenWidth / 2 - 300, 300, 200, 50);
		map1.add(newGame, 0);
		newGame.addActionListener(new listenToNewGame());
		loadGame = new JButton("Load Game");
		loadGame.setBounds(main.screenWidth / 2 + 100, 300, 200, 50);
		map1.add(loadGame, 0);
		loadGame.addActionListener(new listenToLoadGame());
	}

	public class listenToNewGame implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			newOrOld = true;
			initDone();
			newGame.setVisible(false);
			loadGame.setVisible(false);
			map1.remove(newGame);
			map1.remove(loadGame);
			message = new JTextPane();
			message.setText("Type the Name of Your World");
			message.setBounds(main.screenWidth / 2 - 100, main.screenHeight / 2 - 200, 200, 25);
			message.setParagraphAttributes(center, true);
			map1.add(message);
			enterWorldName = new JTextPane();
			enterWorldName.setBounds(main.screenWidth / 2 - 100, main.screenHeight / 2 - 100, 200, 25);
			enterWorldName.setParagraphAttributes(center, true);
			map1.add(enterWorldName);
			map1.repaint();
		}
	}

	public class listenToLoadGame implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			newGame.setVisible(false);
			loadGame.setVisible(false);
			map1.remove(newGame);
			map1.remove(loadGame);
			savedGames = getSavedStuff.getWorldNames();
			if (savedGames == null) {
				noSavedGames = new JTextPane();
				noSavedGames.setText("You have no saved games");
				noSavedGames.setBounds(main.screenWidth / 2 - 100, 50, 200, 25);
				noSavedGames.setParagraphAttributes(center, true);
				map1.add(noSavedGames);
				newOrOld = true;
				initDone();
				message = new JTextPane();
				message.setText("Type the Name of Your World");
				message.setBounds(main.screenWidth / 2 - 100, main.screenHeight / 2 - 200, 200, 25);
				message.setParagraphAttributes(center, true);
				map1.add(message);
				enterWorldName = new JTextPane();
				enterWorldName.setBounds(main.screenWidth / 2 - 100, main.screenHeight / 2 - 100, 200, 25);
				enterWorldName.setParagraphAttributes(center, true);
				map1.add(enterWorldName);
				map1.repaint();
			} else {
				int widthInButtons = main.screenWidth / 100;
				int heightInButtons = main.screenHeight / 50;
				int rowsNeeded = (savedGames.length / widthInButtons) + 1;
				int counter = 0;
				selectGame = new JButton[widthInButtons][heightInButtons];
				for (int i = 0; i < widthInButtons; i++) {
					for (int x = 0; x < rowsNeeded; x++) {
						if (!(counter + 1 > savedGames.length)) {
							selectGame[i][x] = new JButton(savedGames[counter].substring(0, savedGames[counter].length() - 4));
							selectGame[i][x].setBounds(150 * i + 50, 100 * x + 50, 100, 50);
							selectGame[i][x].addActionListener(new listenToGameButtons(counter));
							map1.add(selectGame[i][x]);
							map1.repaint();
							counter++;
						}
					}
				}
			}
		}
	}

	public class listenToGameButtons implements ActionListener {
		int id;

		public listenToGameButtons(int id1) {
			id = id1;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			main.startGame(savedGames[id]);
			map1.setVisible(false);
			map1.setFocusable(false);
			map1 = null;
		}
	}

	public class listenToDone implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			File thisFile = new File(enterWorldName.getText() + ".xml");
			thisFile.delete();
	 		main.startGame(enterWorldName.getText() + ".xml");
			map1.setVisible(false);
			map1.setFocusable(false);
			map1 = null;
		}
	}

	public void initDone() {
		done = new JButton("Done");
		done.setBounds(main.screenWidth / 2 - 100, main.screenHeight / 2 - 300, 200, 50);
		map1.add(done);
		done.addActionListener(new listenToDone());
	}
	


}
