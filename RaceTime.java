import java.io.File;
import java.io.IOException;
import java.util.*;

public class RaceTime {
    private static final String PASSWORD = "clyderunners";
    private static final String FILENAME = "race-results-1.txt";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String password;
        System.out.print("Please enter the password: ");
        password = input.nextLine();

        if (!password.equals(PASSWORD)) {
            System.out.println("Incorrect password.");
            return;
        }

        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine(); // Consume the newline character after reading the choice

            switch (choice) {
                case 1:
                    readAndDisplayFile();
                    break;
                case 2:
                    sortAndPrintTimes();
                    break;
                case 3:
                    findAndPrintFastestTime();
                    break;
                case 4:
                    findAndPrintSlowestTime();
                    break;
                case 5:
                    searchTime();
                    break;
                case 6:
                    timeOccurrence();
                    break;
                case 7:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 7);
    }

    private static void displayMenu() {
        System.out.println("Menu:");
        System.out.println("1. Read and Display File");
        System.out.println("2. Sort and Print Recorded Times");
        System.out.println("3. Find and Print Fastest Time");
        System.out.println("4. Find and Print the Slowest Time");
        System.out.println("5. Search");
        System.out.println("6. Time Occurrence");
        System.out.println("7. Exit Program");
    }

    private static void readAndDisplayFile() {
        try (Scanner fileScanner = new Scanner(new File(FILENAME))) {
            int numRows = 0;
            while (fileScanner.hasNextLine()) {
                fileScanner.nextLine();
                numRows++;
            }

            String[][] runners = new String[numRows][3];

            fileScanner.close();
            Scanner scanner = new Scanner(new File(FILENAME));
            for (int i = 0; i < numRows; i++) {
                runners[i][0] = scanner.next();
                runners[i][1] = scanner.nextLine().trim();
            }

            System.out.println("Runners:");
            for (String[] runner : runners) {
                System.out.println(runner[0] + " " + runner[1]);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }   

    private static void sortAndPrintTimes() {
        try (Scanner scanner = new Scanner(new File(FILENAME))) {
            List<String> runners = new ArrayList<>();
            List<Integer> times = new ArrayList<>();
    
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    String runner = parts[0] + " " + parts[1];
                    int time = Integer.parseInt(parts[2]);
    
                    runners.add(runner);
                    times.add(time);
                }
            }
    
            List<Integer> sortedTimes = new ArrayList<>(times);
            Collections.sort(sortedTimes);
    
            System.out.println("Sorted times:");
            for (int time : sortedTimes) {
                int index = times.indexOf(time);
                String runner = runners.get(index);
                System.out.println(runner + " " + time);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }    

    private static void findAndPrintFastestTime() {
        try (Scanner scanner = new Scanner(new File(FILENAME))) {
            int fastestTime = Integer.MAX_VALUE;
            String fastestRunner = "";
    
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    String runner = parts[0] + " " + parts[1];
                    int time = Integer.parseInt(parts[2]);
    
                    if (time < fastestTime) {
                        fastestTime = time;
                        fastestRunner = runner;
                    }
                }
            }
    
            System.out.println("Fastest time: " + fastestTime);
            System.out.println("Runner: " + fastestRunner);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    private static void findAndPrintSlowestTime() {
        try (Scanner scanner = new Scanner(new File(FILENAME))) {
            int slowestTime = Integer.MIN_VALUE;
            String slowestRunner = "";
    
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    String runner = parts[0] + " " + parts[1];
                    int time = Integer.parseInt(parts[2]);
    
                    if (time > slowestTime) {
                        slowestTime = time;
                        slowestRunner = runner;
                    }
                }
            }
    
            System.out.println("Slowest time: " + slowestTime);
            System.out.println("Runner: " + slowestRunner);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    private static void searchTime() {
        try (Scanner scanner = new Scanner(new File(FILENAME))) {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter a time (seconds): ");
            int searchTime = input.nextInt();
            boolean found = false;
    
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    String runner = parts[0] + " " + parts[1];
                    int time = Integer.parseInt(parts[2]);
    
                    if (time == searchTime) {
                        System.out.println("Runner: " + runner);
                        found = true;
                    }
                }
            }
    
            if (!found) {
                System.out.println("Time not found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    private static void timeOccurrence() {
        try (Scanner scanner = new Scanner(new File(FILENAME))) {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter a time (seconds): ");
            int searchTime = input.nextInt();
            int occurrences = 0;
    
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    int time = Integer.parseInt(parts[2]);
                    if (time == searchTime) {
                        occurrences++;
                    }
                }
            }
    
            System.out.println("Occurrences: " + occurrences);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
}