import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class RaceTime {
    public static File fileName;
    public static Scanner in = new Scanner(System.in);
    public static PrintWriter out;
    public static String[][] raceTimes = new String[16][3];

    public static void main(String[] args) throws FileNotFoundException {
        String password = "clyderunners";
        int fail = 3;

        do {
            System.out.println("Welcome to Glasgow Clyde Runners Club.");
            System.out.println("Please enter your password to continue: ");
            String login = in.nextLine();
            if (login.equals(password)) {
                System.out.println("Password Validated");
                menu();
            } else {
                fail--;
                System.out.println("Your Password is incorrect");
                System.out.println("You have: " + fail + " attempts left.");
            }
        } while (fail != 0);

        System.out.println("Number of attempts exceeded. You are now locked out.");
        System.exit(0);
    }

    public static void menu() throws FileNotFoundException {
        int option;

        do {
            System.out.println("\nPlease choose from the following options: ");
            System.out.println("1. Read and Display File");
            System.out.println("2. Sort and Print Recorded Times");
            System.out.println("3. Find and Print Fastest Time");
            System.out.println("4. Find and Print the Slowest Time");
            System.out.println("5. Search");
            System.out.println("6. Time Occurrence");
            System.out.println("7. Exit Program");
            option = in.nextInt();

            if (option == 1) {
                readAndDisplayFile();
            } else if (option == 2) {
                sortAndPrintTimes();
            } else if (option == 3) {
                findAndPrintFastestTime();
            } else if (option == 4) {
                findAndPrintSlowestTime();
            } else if (option == 5) {
                searchTime();
            } else if (option == 6) {
                timeOccurrence();
            } else if (option == 7) {
                in.close();
                System.out.println("Thank you for using Glasgow Clyde Runners Club. Goodbye");
                System.exit(0);
            }
        } while (option != 7);
    }

    public static void readAndDisplayFile() throws FileNotFoundException {
        String fileName = "race-results-1.txt";
        File file = new File(fileName);
        Scanner fileScanner = new Scanner(file);

        int index = 0;
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] parts = line.split(" ");
            raceTimes[index] = parts;
            index++;
        }
        fileScanner.close();

        System.out.println("Race Times:");
        for (String[] time : raceTimes) {
            System.out.println(Arrays.toString(time));
        }
    }

    public static void sortAndPrintTimes() throws FileNotFoundException {
        String[][] sortedTimes = raceTimes.clone();
        boolean sorted;

        for (int i = 0; i < sortedTimes.length - 1; i++) {
            sorted = true;
            for (int j = 0; j < sortedTimes.length - i - 1; j++) {
                int time1 = Integer.parseInt(sortedTimes[j][2]);
                int time2 = Integer.parseInt(sortedTimes[j + 1][2]);

                if (time1 > time2) {
                    String[] temp = sortedTimes[j];
                    sortedTimes[j] = sortedTimes[j + 1];
                    sortedTimes[j + 1] = temp;
                    sorted = false;
                }
            }
            if (sorted) {
                break;
            }
        }

        System.out.println("Sorted Recorded Times:");
        for (String[] time : sortedTimes) {
            System.out.println(Arrays.toString(time));
        }

        PrintWriter writer = new PrintWriter("sorted_times.txt");
        for (String[] time : sortedTimes) {
            writer.println(Arrays.toString(time));
        }
        writer.close();
    }

    public static void findAndPrintFastestTime() throws FileNotFoundException {
        int fastestTime = Integer.MAX_VALUE;
        String fastestName = "";

        for (String[] time : raceTimes) {
            int raceTime = Integer.parseInt(time[2]);
            if (raceTime < fastestTime) {
                fastestTime = raceTime;
                fastestName = time[0] + " " + time[1];
            }
        }

        System.out.println("Fastest Recorded Time: " + fastestTime + " seconds (" + fastestName + ")");

        PrintWriter writer = new PrintWriter("fastest_time.txt");
        writer.println("Fastest Recorded Time: " + fastestTime + " seconds (" + fastestName + ")");
        writer.close();
    }

    public static void findAndPrintSlowestTime() throws FileNotFoundException {
        int slowestTime = Integer.MIN_VALUE;
        String slowestName = "";

        for (String[] time : raceTimes) {
            int raceTime = Integer.parseInt(time[2]);
            if (raceTime > slowestTime) {
                slowestTime = raceTime;
                slowestName = time[0] + " " + time[1];
            }
        }

        System.out.println("Slowest Recorded Time: " + slowestTime + " seconds (" + slowestName + ")");

        PrintWriter writer = new PrintWriter("slowest_time.txt");
        writer.println("Slowest Recorded Time: " + slowestTime + " seconds (" + slowestName + ")");
        writer.close();
    }

    public static void searchTime() throws FileNotFoundException {
        System.out.println("Enter a time (seconds):");
        int searchTime = in.nextInt();
        boolean found = false;
        String foundNames = "";

        for (String[] time : raceTimes) {
            int raceTime = Integer.parseInt(time[2]);
            if (raceTime == searchTime) {
                found = true;
                foundNames += time[0] + " " + time[1] + ", ";
            }
        }

        if (found) {
            foundNames = foundNames.substring(0, foundNames.length() - 2); // Remove the trailing comma and space
            System.out.println("Time " + searchTime + " found for: " + foundNames);
        } else {
            System.out.println("Time " + searchTime + " not found");
        }

        PrintWriter writer = new PrintWriter("search_result.txt");
        if (found) {
            writer.println("Time " + searchTime + " found for: " + foundNames);
        } else {
            writer.println("Time " + searchTime + " not found");
        }
        writer.close();
    }

    public static void timeOccurrence() throws FileNotFoundException {
        System.out.println("Enter a time (seconds):");
        int searchTime = in.nextInt();
        int occurrence = 0;

        for (String[] time : raceTimes) {
            int raceTime = Integer.parseInt(time[2]);
            if (raceTime == searchTime) {
                occurrence++;
            }
        }

        System.out.println("Time Occurrence: " + occurrence);

        PrintWriter writer = new PrintWriter("time_occurrence.txt");
        writer.println("Time Occurrence: " + occurrence);
        writer.close();
    }
}
