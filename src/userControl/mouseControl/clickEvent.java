package userControl.mouseControl;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.main;

public class clickEvent implements MouseListener{ //Listens for a mouse click and places when it finds one

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		main.placeBlockAtMouse("dirt.jpg");		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		main.map.stopMining();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

}
