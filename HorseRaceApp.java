import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;
import java.util.List;

public class HorseRaceApp {
	
	private static Scanner scanner;
	private int horseCount;
	public static int trackLength;
	public static boolean hasWinner;

	private CopyOnWriteArrayList<Horse> horseList;
	private ExecutorService executor;
	private CyclicBarrier cyclicBarrier;

	public HorseRaceApp() {
		initialize();
		startApp();
	}

	public void initialize() {
		horseCount = 0;
		trackLength = 0;
		scanner = new Scanner(System.in).useDelimiter("\\n");
		horseList = new CopyOnWriteArrayList<Horse>();
		hasWinner = false;
	}

	public void startApp() {
		System.out.println("\n\t*****Welcome to the Horse Race App!******\n\n");
		getInput();
		setComponents();
		startRace();
		promptUser();
	}

	public void reset() {
		horseList.clear();
		hasWinner = false;
		horseCount = 0;
		trackLength = 0;
	}

	public void getInput() {
		try {
			System.out.print("Enter the number of horses that would participate in the race: ");
			horseCount = scanner.nextInt();
			scanner.nextLine();
			if (horseCount < 2) {
				System.out.println("The number of horses participating in the race should be more than 1!");
				getInput();
			}
			while (trackLength < 1) {
				System.out.print("Enter the length of the race track: ");
				trackLength = scanner.nextInt();
				scanner.nextLine();
				if (trackLength < 1) {
					System.out.println("The length of the race track should not be less than 1 meters!");
				}
			}
		} catch (java.util.InputMismatchException e) {
			System.out.println("\n***Wrong input!");
			scanner.nextLine();
			getInput();
		}
	}

	public void setComponents() {
		cyclicBarrier = new CyclicBarrier(horseCount, new CountDownTimer(horseList));
		for (int horse = 1; horse <= horseCount; horse++) {
			horseList.add(new Horse("Horse" + horse, false, cyclicBarrier, 0, false));
		}
		horseList.forEach(
			(horse)->(horse).setHealthy(Util.getRandomBoolean()));
	}

	public void startRace() {
		if (Util.getHealthyHorsesCount(horseList) > 1) {
			System.out.println("\n\t*****The horses are now proceeding to the gate...\n");
			executor = Executors.newFixedThreadPool(Util.getHealthyHorsesCount(horseList));
			CopyOnWriteArrayList<Horse> healthyHorseList = Util.retrieveHealthyHorses(horseList);
			cyclicBarrier = new CyclicBarrier((int) Util.getHealthyHorsesCount(horseList), new CountDownTimer(horseList));
			healthyHorseList.forEach(
				(horse)->(horse).setBarrier(cyclicBarrier));
			healthyHorseList.forEach(
				(horse)->executor.execute(horse)
			);
			executor.shutdown();  
       		while (!executor.isTerminated()) {  }  
		}
		else {
			System.out.println("The race can't proceed because there are not enough healthy horses to race.");
			Util.printHorsesHealthCondition(horseList);
			reset();
			startApp();
		}
	}

	public void promptUser() {
		int userChoice = 0;
		try {
			Util.printWinnerHorse(horseList);
			Util.printMenu();
			userChoice = scanner.nextInt();
		} catch (java.util.InputMismatchException e) {
			System.out.println("\n***Wrong input!");
			scanner.nextLine();
			promptUser();
		}
		if (userChoice == 1) {
			reset();
			startApp();
		}
		else if (userChoice == 2) {
			System.exit(0);
		}
		else {
			System.out.println("\n***Wrong input!");
			promptUser();
		}
	}
}