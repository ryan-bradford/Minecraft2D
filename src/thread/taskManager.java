package thread;

public class taskManager {
	public CPUCore[] cores;
	int[] loadNum;
	int lowestLoadAmount;
	int lowestLoadCoreNum;

	public taskManager() {
		lowestLoadCoreNum = 0;
		lowestLoadAmount = 50000;
		cores = new CPUCore[Runtime.getRuntime().availableProcessors()];
		loadNum = new int[cores.length];
		for(int i = 0; i < cores.length; i++) {
			cores[i] = new CPUCore(null);
			cores[i].start();
			loadNum[i] = 0;
		}
	}

	public int[] addTask(task task1) {
		int taskNum;
		for(int i = 0; i < cores.length; i++) {
			if(cores[i].getLoad() < lowestLoadAmount) {
				lowestLoadAmount = cores[i].getLoad();
				lowestLoadCoreNum = i;
			}
		}
		taskNum = cores[lowestLoadCoreNum].addTask(task1);

		return new int[]{lowestLoadCoreNum, taskNum};
	}
}
