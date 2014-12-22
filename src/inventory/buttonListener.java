package inventory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class buttonListener implements ActionListener {
	
	int idX;
	int idY;
	Boolean inventOrBar; //True is inventory
	
	public buttonListener(int idX1, int idY1, Boolean inventOrBar1) {
		idX = idX1;
		idY = idY1;
		inventOrBar = inventOrBar1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
