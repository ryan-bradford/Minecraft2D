package startScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import save.getSavedStuff;
import main.main;

public class startScreen extends JFrame {

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

	public startScreen() {
		center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		setLayout(null);
		savedGames = getSavedStuff.getWorldNames();
		newGame = new JButton("New/Overwrite Game");
		newGame.setBounds(main.screenWidth / 2 - 300, 300, 200, 50);
		add(newGame);
		newGame.addActionListener(new listenToNewGame());
		loadGame = new JButton("Load Game");
		loadGame.setBounds(main.screenWidth / 2 + 100, 300, 200, 50);
		add(loadGame);
		loadGame.addActionListener(new listenToLoadGame());
	}

	public class listenToNewGame implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			newOrOld = true;
			initDone();
			newGame.setVisible(false);
			loadGame.setVisible(false);
			remove(newGame);
			remove(loadGame);
			message = new JTextPane();
			message.setText("Type the Name of Your World");
			message.setBounds(main.screenWidth / 2 - 100, main.screenHeight / 2 - 200, 200, 25);
			message.setParagraphAttributes(center, true);
			add(message);
			enterWorldName = new JTextPane();
			enterWorldName.setBounds(main.screenWidth / 2 - 100, main.screenHeight / 2 - 100, 200, 25);
			enterWorldName.setParagraphAttributes(center, true);
			add(enterWorldName);
		}
	}

	public class listenToLoadGame implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			newGame.setVisible(false);
			loadGame.setVisible(false);
			remove(newGame);
			remove(loadGame);
			savedGames = getSavedStuff.getWorldNames();
			if (savedGames == null) {
				noSavedGames = new JTextPane();
				noSavedGames.setText("You have no saved games");
				noSavedGames.setBounds(main.screenWidth / 2 - 100, 50, 200, 25);
				noSavedGames.setParagraphAttributes(center, true);
				add(noSavedGames);
				newOrOld = true;
				initDone();
				message = new JTextPane();
				message.setText("Type the Name of Your World");
				message.setBounds(main.screenWidth / 2 - 100, main.screenHeight / 2 - 200, 200, 25);
				message.setParagraphAttributes(center, true);
				add(message);
				enterWorldName = new JTextPane();
				enterWorldName.setBounds(main.screenWidth / 2 - 100, main.screenHeight / 2 - 100, 200, 25);
				enterWorldName.setParagraphAttributes(center, true);
				add(enterWorldName);
				repaint();
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
							add(selectGame[i][x]);
							repaint();
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
		}
	}

	public class listenToDone implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (newOrOld) {
				File thisFile = new File(enterWorldName.getText() + ".xml");
				thisFile.delete();
				main.startGame(enterWorldName.getText() + ".xml");
			} else {

			}
		}
	}

	public void initDone() {
		done = new JButton("Done");
		done.setBounds(main.screenWidth / 2 - 100, main.screenHeight / 2 - 300, 200, 50);
		add(done);
		done.addActionListener(new listenToDone());
	}

}
