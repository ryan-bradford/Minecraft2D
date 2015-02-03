package inventory;

import java.awt.event.ActionEvent;

import main.main;
import java.awt.event.ActionListener;

public class buttonListener implements ActionListener {
	
	int idX;
	int idY;
	Boolean inventOrBar; //True is inventory
	
	public buttonListener(int idX1, int idY1, Boolean inventOrBar1) { //The thing that controls what happens when a inventory 
		idX = idX1; 											      //Button is clicked
		idY = idY1;
		inventOrBar = inventOrBar1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(inventOrBar == true) {
			main.setSwapInvent(idX, idY, true);
		} else {
			main.setSwapInvent(idX, 0, false);
		}
	}

}
