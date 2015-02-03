package userControl.mouseControl;

import main.main;
import thread.task;

public class mineBlockTask extends task{
	int wait;
	int blockNum;
	int yRow;
	int xCord;
	public Boolean running = false;
	public mineBlockTask(int wait1) {
		wait = wait1;
	}
	
	@Override
	public void runTask() {
		main.map.chunk.get(main.map.currentScreen).get(yRow)[blockNum].deductHealth(100);
		if(main.map.chunk.get(main.map.currentScreen).get(yRow)[blockNum].health <= 0) {
			main.map.mineBlockAt(xCord, yRow, blockNum);
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
	
	public void setRunning(int blockNum1, int yRow1, int xCord1) {
		running = true;
		blockNum = blockNum1;
		yRow = yRow1;
		xCord = xCord1;
	}
	
	public void stopRunning() {
		running = false;
	}
}
