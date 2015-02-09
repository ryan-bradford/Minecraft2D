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
	JTextPane message;
	JTextPane enterWorldName;
	JButton done;
	Boolean newOrOld;
	String[] savedGames;

	public startScreen() {
		setLayout(null);
		savedGames = getSavedStuff.getWorldNames();
		newGame = new JButton("New/Overwrite Game");
		newGame.setBounds(main.screenWidth / 2 - 300, 300, 200, 50);
		add(newGame);
		newGame.addActionListener(new listenToNewGame());
		loadGame = new JButton("Load Game");
		loadGame.setBounds(main.screenWidth / 2 + 100, 300, 200, 50);
		add(loadGame);
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
			add(message);
			enterWorldName = new JTextPane();
			enterWorldName.setBounds(main.screenWidth / 2 - 100, main.screenHeight / 2 - 100, 200, 25);
			SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
			enterWorldName.setParagraphAttributes(center, false);
			add(enterWorldName);
		}
	}

	public class listenToDone implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (newOrOld) {
				File thisFile = new File(enterWorldName.getText() + ".xml");
				thisFile.delete();
				main.startGame(enterWorldName.getText());
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
