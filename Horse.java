import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.text.SimpleDateFormat; 
import java.util.Date;

public class Horse implements Runnable{
	
	private String name;
	private boolean healthy;
	private CyclicBarrier barrier;
	private int metersPerGallop;
	private boolean winner;

	public Horse(String name, boolean healthy, CyclicBarrier barrier, int metersPerGallop, boolean winner) {
		this.name = name;
		this.healthy = healthy;
		this.barrier = barrier;
		this.metersPerGallop = metersPerGallop;
		this.winner = winner;
	}

	public void setHorseName(String name) {
		this.name = name;
	}

	public String getHorseName() {
		return name;
	}

	public void setHealthy(boolean healthy) {
		this.healthy = healthy;
	}

	public boolean getHealthy() {
		return healthy;
	}

	public void setBarrier(CyclicBarrier barrier) {
		this.barrier = barrier;
	}

	public void setMetersPerGallop(int metersPerGallop) {
		this.metersPerGallop = metersPerGallop;
	}

	public int getMetersPerGallop() {
		return metersPerGallop;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}

	public boolean getWinner() {
		return winner;
	}

	@Override
	public void run() {
		try {
			int metersTotal = 0;
			while (metersTotal < 10) {
				setMetersPerGallop(Util.getRandomMeters());
				metersTotal += getMetersPerGallop();
				if (metersTotal < 10) {
					System.out.println(getHorseName() + " is "  + (10-metersTotal) + ((10-metersTotal) == 1 ? " meter" : " meters") + " before the gate");
				}
				else {
					System.out.println(getHorseName() + " has arrived on the gate and is currently waiting for the other horses.");
				}
				Thread.sleep(1000);
			}
            barrier.await();            
			metersTotal = 0;
			while (metersTotal < HorseRaceApp.trackLength) {
				Date d = new Date();
				SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss:SSS");
				setMetersPerGallop(Util.getRandomMeters());
				metersTotal += getMetersPerGallop();
				System.out.println(getHorseName() + " - Distance Travelled: " + metersTotal + "m Distance Remaining: " + ((HorseRaceApp.trackLength - metersTotal) < 0 ? 0 : (HorseRaceApp.trackLength - metersTotal)) + "m  time:" + ft.format(d));
				if (metersTotal >= HorseRaceApp.trackLength) {
					setWinner(Util.modifyWinner());
					System.out.println("\t***" + getHorseName() + " has finished the race!***");
				}
				Thread.sleep(100);
			}          
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
	}
}