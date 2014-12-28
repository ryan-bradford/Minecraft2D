package thread;

public class taskManager { // The thing that distributes the tasks to the different cores
	public CPUCore[] cores;
	int lowestLoadAmount;
	int lowestLoadCoreNum;

	public taskManager() {
		lowestLoadCoreNum = 0; // Assumes the lowest load is on core 0(Core 1), the first task will always be added to core 0
		lowestLoadAmount = 50000; // Random value
		cores = new CPUCore[Runtime.getRuntime().availableProcessors()]; // Gets the amount of available cores
		for (int i = 0; i < cores.length; i++) {
			cores[i] = new CPUCore(null); // Creates the core objects
		}
	}

	public int[] addTask(task task1) { // Adds a task
		int taskNum;
		for (int i = 0; i < cores.length; i++) { // Finds the core with the lowest load
			if (cores[i].getLoad() < lowestLoadAmount) {
				lowestLoadAmount = cores[i].getLoad();
				lowestLoadCoreNum = i;
			}
		}
		taskNum = cores[lowestLoadCoreNum].addTask(task1); // Adds the task to this core
		System.out.println(lowestLoadCoreNum);
		if (cores[lowestLoadCoreNum].getState() == Thread.State.NEW) { // Checks if the core is not running
			cores[lowestLoadCoreNum].start(); // Starts it if it isn't
		}
		return new int[] { lowestLoadCoreNum, taskNum }; // Returns the core number the task was added to
	}
}
