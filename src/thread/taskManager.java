package thread;

import java.util.ArrayList;

public class taskManager extends Thread {
	public ArrayList<task> tasks;
	ArrayList<Integer> waits;
	ArrayList<Integer> numShortWaitsPassed;
	Integer shortestWait;

	public taskManager(ArrayList<task> tasks1) {
		tasks = new ArrayList<task>();
		waits = new ArrayList<Integer>();
		numShortWaitsPassed = new ArrayList<Integer>();
		try {
			if (tasks1 != null) {
				tasks = tasks1;
			}
			for (int i = 0; i < tasks.size(); i++) {
				waits.set(i, tasks.get(i).getWait());
				numShortWaitsPassed.set(i, 0);
				if (shortestWait == null || waits.get(i) < shortestWait) {
					shortestWait = waits.get(i);
				}
			}
		} catch (NullPointerException ex) {

		}
	}

	public void run() {
		while (true) {
			try {
				try {
					taskManager.sleep(shortestWait);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (int i = 0; i < tasks.size(); i++) {
					if (numShortWaitsPassed.get(i) + 1 * shortestWait >= waits
							.get(i)) {
						numShortWaitsPassed.set(i, 0);
						if (tasks.get(i).returnRunnable() == true) {
							tasks.get(i).runTask();
						}
					} else {
						numShortWaitsPassed.set(i,
								numShortWaitsPassed.get(i) + 1);
					}
				}
			} catch (NullPointerException ex) {
			}
		}
	}

	public int addTask(task task1) {
		tasks.add(task1);
		for (int i = 0; i < tasks.size(); i++) {
			waits.add(tasks.get(i).getWait());
			numShortWaitsPassed.add(0);
			if (shortestWait == null || waits.get(i) < shortestWait) {
				shortestWait = waits.get(i);
			}
		}
		return tasks.size();
	}
}
