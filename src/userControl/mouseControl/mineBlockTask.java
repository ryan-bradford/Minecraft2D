package userControl.mouseControl;

import main.main;
import thread.task;

public class mineBlockTask extends task{
	int wait;
	int yRow;
	int xCord;
	public Boolean running = false;
	public mineBlockTask(int wait1) {
		wait = wait1;
	}
	
	@Override
	public void runTask() {
		main.map.chunk.get(main.map.currentScreen).get(yRow)[xCord].deductHealth(100);
		if(main.map.chunk.get(main.map.currentScreen).get(yRow)[xCord].health <= 0) {
			main.map.mineBlockAt(xCord, yRow);
		}
	}
	
	@Override
	public Boolean returnRunnable() {
		return running;
	}
	
	@Override
	public int getWait() {
		return wait;
	}
	
	@Override
	public int getCPULoad() {
		return 1; //0 is no load, 3 is maximum load
	}
	
	public void setRunning(int xCord1, int yRow1) {
		running = true;
		yRow = yRow1;
		xCord = xCord1;
	}
	
	public void stopRunning() {
		running = false;
	}
}