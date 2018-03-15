import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.util.stream.Collectors; 

public class Util {

	public static boolean getRandomBoolean() {
       return Math.random() < 0.5;
   	}

   	public static int getRandomMeters() {
   		return (int) Math.ceil(Math.random() * 10);
   	}

   	public static int getHealthyHorsesCount(CopyOnWriteArrayList<Horse> horseList) {
   		long healthyHorseCount = horseList.stream()
   									.filter(horse -> horse.getHealthy() == true)
   									.count();
   		return (int) healthyHorseCount;
   	}

   	public static void printUnhealthyHorses(CopyOnWriteArrayList<Horse> horseList) {
   		horseList.stream()
				.filter(horse -> horse.getHealthy() == false)
				.map(horse -> {
					return horse.getHorseName();
				})
				.forEach(horseName -> System.out.println("******" + horseName + " is not healthy to participate on the race.******"));
   	}

   	public static CopyOnWriteArrayList<Horse> retrieveHealthyHorses(CopyOnWriteArrayList<Horse> horseList) {
   		List <Horse> healthyHorses = horseList.stream()
									.filter(horse -> horse.getHealthy() == true)
									.collect(Collectors.toList());

   		return new CopyOnWriteArrayList<Horse> (healthyHorses);
   	}

   	public static void printWinnerHorse(CopyOnWriteArrayList<Horse> horseList) {
   		horseList.stream()
   				.filter(horse -> horse.getWinner() == true)
   				.forEach(horse -> System.out.println("\n\n\t*****The race has ended! " + horse.getHorseName() + " is the winner of the race!*****\n"));
   	}

   	public static void printMenu() {
   		System.out.print("\nDo you want to race again?" +
							"\n1. Race Again" +
							"\n2. Exit App" +
							"\nEnter your choice: ");
   	}

   	public static void printHorsesHealthCondition(CopyOnWriteArrayList<Horse> horseList) {
   		horseList.forEach(
   			(horse)-> System.out.println(horse.getHorseName() + " is" + (horse.getHealthy() ? "" : " not") + " healthy."));
   	}

   	public static synchronized boolean modifyWinner() {
		if (!HorseRaceApp.hasWinner) {
			HorseRaceApp.hasWinner = true;
			return true;
		}
		else {
			return false;
		}
   	}

}
