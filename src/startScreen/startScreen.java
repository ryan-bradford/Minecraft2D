package startScreen;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import save.getSavedStuff;
import main.main;
import map.map;

public class startScreen {

	texturedButton newGame;
	texturedButton loadGame;
	texturedButton[][] selectGame;
	texturedButton done;
	texturedButton back;
	splashScreen splash;
	JTextPane message;
	JTextPane enterWorldName;
	JTextPane noSavedGames;
	Boolean newOrOld;
	String[] savedGames;
	SimpleAttributeSet center;
	map map1;
	Style s;
	ImageIcon icUf;
	int stage; // Stage 1 is the first screen, 2 is load game, 3 is new game

	public startScreen() {
		map1 = new map(false, 64, main.dirtHeightInBlocks,
				main.inventoryBlockNumber, main.inventoryGap,
				main.inventoryExtra, main.inventoryHeight,
				main.defaultBoxColor, main.swapBoxColor, main.selectedBoxColor,
				main.backgroundColor, main.textColor, main.airColor,
				main.skinColor, main.pantsColor, main.shirtColor,
				main.shoeColor, main.imageFileNames, main.stackHeight,
				main.jumpDistance, main.jumpSpeed, main.gravitySpeed,
				main.walkSpeed, main.mineBlockSpeed, null, null, null, null, 0,
				"normal", main.worldSeed, true, 0, 0);
		map1.pack();
		map1.setBounds(0, 0, main.screenWidth, main.screenHeight);
		map1.setVisible(true);
		map1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		map1.getContentPane().setBackground(main.airColor);
		map1.setLayout(null);
		splash = new splashScreen();
		splash.setBounds(main.screenWidth/2 - 250, 0, 1000, 1000);
		map1.add(splash, 0);
		center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		map1.setLayout(null);
		savedGames = getSavedStuff.getWorldNames();
		startLoadScreen();
		map1.repaint();
	}

	public class listenToNewGame implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			newGameButton();
		}
	}

	public class listenToLoadGame implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			loadGameButton();
		}
	}

	public class listenToBack implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (stage == 1) {

			} else if (stage == 2) {
				startLoadScreen();
				stage = 1;
			} else if (stage == 3) {
				startLoadScreen();
				stage = 1;
			} else if (stage == 4) {
				startLoadScreen();
				stage = 1;
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
			pressGameButton(id);
		}
	}

	public class listenToDone implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			runDone();
		}
	}

	public void pressGameButton(int id) {
		main.startGame(savedGames[id]);
		map1.setVisible(false);
		map1.setFocusable(false);
		map1 = null;
	}

	public void runDone() {
		File thisFile = new File(enterWorldName.getText() + ".xml");
		thisFile.delete();
		main.startGame(enterWorldName.getText() + ".xml");
		map1.setVisible(false);
		map1.setFocusable(false);
		map1 = null;
	}

	public void initDone() {
		done = new texturedButton("Done", 200, 50);
		done.setBounds(main.screenWidth / 2 - 100, 400,
				200, 50);
		map1.add(done, 0);
		done.addActionListener(new listenToDone());
	}

	public void loadGameButton() {
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
			map1.add(noSavedGames, 0);
			newOrOld = true;
			initDone();
			message = new JTextPane();
			message.setText("Type the Name of Your World");
			message.setBounds(main.screenWidth / 2 - 100,
					main.screenHeight / 2 - 200, 200, 25);
			message.setParagraphAttributes(center, true);
			map1.add(message, 0);
			enterWorldName = new JTextPane();
			enterWorldName.setBounds(main.screenWidth / 2 - 100,
					main.screenHeight / 2 - 100, 200, 25);
			enterWorldName.setParagraphAttributes(center, true);
			enterWorldName.addKeyListener(new keyControls());
			map1.add(enterWorldName, 0);
			map1.repaint();
			stage = 4;
		} else {
			int widthInButtons = main.screenWidth / 250;
			int heightInButtons = main.screenHeight / 50;
			int rowsNeeded = (savedGames.length / widthInButtons) + 1;
			int counter = 0;
			selectGame = new texturedButton[widthInButtons][heightInButtons];
			for (int i = 0; i < rowsNeeded; i++) {
				for (int x = 0; x < widthInButtons; x++) {
					if (!(counter + 1 > savedGames.length)) {
						selectGame[x][i] = new texturedButton(
								savedGames[counter].substring(0,
										savedGames[counter].length() - 4), 200,
								50);
						selectGame[x][i].setBounds(250 * x + 50, 100 * i + 50,
								200, 50);
						selectGame[x][i]
								.addActionListener(new listenToGameButtons(
										counter));
						map1.add(selectGame[x][i], 0);
						map1.repaint();
						counter++;
					}
				}
			}
			stage = 2;
			map1.repaint();
		}
	}

	public void newGameButton() {
		newOrOld = true;
		initDone();
		newGame.setVisible(false);
		loadGame.setVisible(false);
		map1.remove(newGame);
		map1.remove(loadGame);
		message = new JTextPane();
		message.setText("Type the Name of Your World");
		message.setBounds(main.screenWidth / 2 - 100,
				main.screenHeight / 2 - 200, 200, 18);
		message.setParagraphAttributes(center, true);
		map1.add(message, 0);
		enterWorldName = new JTextPane();
		enterWorldName.setBounds(main.screenWidth / 2 - 100,
				main.screenHeight / 2 - 100, 200, 18);
		enterWorldName.setParagraphAttributes(center, true);
		map1.add(enterWorldName, 0);
		enterWorldName.addKeyListener(new keyControls());
		map1.repaint();
		map1.setFocusable(true);
		stage = 3;
	}

	public void startLoadScreen() {
		if (selectGame != null) {
			for (int i = 0; i < selectGame.length; i++) {
				for (int x = 0; x < selectGame[i].length; x++) {
					if (selectGame[i][x] != null) {
						map1.remove(selectGame[i][x]);
					}
				}
			}
			selectGame = null;
		}
		if (message != null) {
			map1.remove(message);
			message = null;
		}
		if (enterWorldName != null) {
			map1.remove(enterWorldName);
			enterWorldName = null;
		}
		if (noSavedGames != null) {
			map1.remove(noSavedGames);
			noSavedGames = null;
		}
		if (done != null) {
			map1.remove(done);
			done = null;
		}
		newGame = new texturedButton("New/Overwrite Game", 200, 50);
		newGame.setBounds(main.screenWidth / 2 - 300, 300, 200, 50);
		map1.add(newGame, 0);
		newGame.addActionListener(new listenToNewGame());
		loadGame = new texturedButton("Load Game", 200, 50);
		loadGame.setBounds(main.screenWidth / 2 + 100, 300, 200, 50);
		map1.add(loadGame, 0);
		loadGame.addActionListener(new listenToLoadGame());
		back = new texturedButton("Back", 200, 50);
		back.setBounds(main.screenWidth / 2 - 100, 500, 200, 50);
		back.addActionListener(new listenToBack());
		map1.add(back, 0);
		map1.repaint();
	}

	public class keyControls implements KeyListener { // The thing that controls
														// the keys
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_ENTER) {
				if (stage == 3 || stage == 4) {
					runDone();
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}
	}
}