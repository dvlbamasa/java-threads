package util;

import java.util.concurrent.CopyOnWriteArrayList;

import model.Horse;

public class CountDownTimer implements Runnable {

	CopyOnWriteArrayList<Horse> horseList;
	
	public CountDownTimer(CopyOnWriteArrayList<Horse> horseList) {
		this.horseList = horseList;
	}

	public void run() {
		try {
			System.out.println("\n\t*****All horses are now at the gate.*****\n");
			Util.printUnhealthyHorses(horseList);
			Thread.sleep(1000);
			for (int i = 3; i > 0; i--) {
				System.out.println("\n\t\tThe race will start in " + i + "...");
				Thread.sleep(1000);
			}
			System.out.println("\n\n\t\t*****The race has started!*****\n\n");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}