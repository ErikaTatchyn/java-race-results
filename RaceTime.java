import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RaceTime {
    private static final String PASSWORD = "clyderunners";
    private static final String FILENAME = "race-results-1.txt";
    private static final String FASTEST_FILE = "fastest-time.txt";
    private static final String SLOWEST_FILE = "slowest-time.txt";
    private static final String SEARCH_FILE = "search-result.txt";
    private static final String OCCURRENCE_FILE = "time-occurrence.txt";

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
                runners[i][1] = scanner.next();
                runners[i][2] = scanner.nextLine().trim();
            }

            System.out.println("Runners:");
            for (String[] runner : runners) {
                System.out.println(runner[0] + " " + runner[1] + " " + runner[2]);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void sortAndPrintTimes() {
        try (Scanner scanner = new Scanner(new File(FILENAME))) {
            List<Integer> times = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    int time = Integer.parseInt(parts[2]);
                    times.add(time);
                }
            }

            // Sort the times in ascending order
            Collections.sort(times);

            // Create a new file for sorted times
            File sortedFile = new File("sorted-times.txt");
            try (FileWriter writer = new FileWriter(sortedFile)) {
                // Write the sorted times to the file
                for (int time : times) {
                    writer.write(time + "\n");
                }
                System.out.println("Sorted times have been written to sorted-times.txt.");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }

            // Print the sorted times to the console
            System.out.println("Sorted Times:");
            for (int time : times) {
                System.out.println(time);
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

            // Write the fastest time to a new file
            File fastestFile = new File(FASTEST_FILE);
            try (FileWriter writer = new FileWriter(fastestFile)) {
                writer.write("Fastest time: " + fastestTime + "\n");
                writer.write("Runner: " + fastestRunner + "\n");
                System.out.println("Fastest time and runner have been written to " + FASTEST_FILE + ".");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
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

            // Write the slowest time to a new file
            File slowestFile = new File(SLOWEST_FILE);
            try (FileWriter writer = new FileWriter(slowestFile)) {
                writer.write("Slowest time: " + slowestTime + "\n");
                writer.write("Runner: " + slowestRunner + "\n");
                System.out.println("Slowest time and runner have been written to " + SLOWEST_FILE + ".");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
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

            // Create a new file for search results
            File searchFile = new File(SEARCH_FILE);
            try (FileWriter writer = new FileWriter(searchFile)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(" ");
                    if (parts.length == 3) {
                        String runner = parts[0] + " " + parts[1];
                        int time = Integer.parseInt(parts[2]);

                        if (time == searchTime) {
                            writer.write("Runner: " + runner + "\n");
                            found = true;
                        }
                    }
                }
                if (!found) {
                    writer.write("Time not found.");
                }
                System.out.println("Search results have been written to " + SEARCH_FILE + ".");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
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

            // Create a new file for occurrence count
            File occurrenceFile = new File(OCCURRENCE_FILE);
            try (FileWriter writer = new FileWriter(occurrenceFile)) {
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

                writer.write("Occurrences: " + occurrences);
                System.out.println("Occurrences have been written to " + OCCURRENCE_FILE + ".");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }

            System.out.println("Occurrences: " + occurrences);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}