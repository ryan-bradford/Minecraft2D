package userControl.mouseControl;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.main;

public class placeBlock implements MouseListener{

	@Override
	public void mouseEntered(MouseEvent e) {
		main.placeBlock(selectorX, selectorRow, "dirt.jpg");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
