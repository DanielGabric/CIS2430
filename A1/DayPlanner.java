package dayplanner;

import java.util.*;

/**
 * @author Daniel Gabric
 * @studentid ******
 * @Title Day Planner
 * @Date:Wednesday, October 15, 2014 Description: Main class which handles the
 * input and output
 */
public class DayPlanner {

    /**
     * Three instance activity arraylists
     */
    private ArrayList<OtherActivity> other;
    private ArrayList<SchoolActivity> school;
    private ArrayList<HomeActivity> home;

    /**
     * Default constructor initializes instance ArrayLists
     */
    public DayPlanner() {
        home = new ArrayList<HomeActivity>();
        school = new ArrayList<SchoolActivity>();
        other = new ArrayList<OtherActivity>();
    }

    /**
     * Description: Holds command loop, takes in input static so that it can be
     * run without the class being instantiated
     *
     * @param args
     */
    public static void main(String[] args) {
        //new instance of dayplanner class
        DayPlanner d = new DayPlanner();
        Scanner s = new Scanner(System.in);
        boolean isEnd = false;
        String input;

        System.out.println("Welcome to the Dayplanner!");

        //command loop
        while (!isEnd) {
            input = "";

            do {
                System.out.print("To add an activity type add.");
                System.out.print(" To search for an activity type search.");
                System.out.println(" To quit type quit.");

                input = s.nextLine();
            } while (!isGoodInput(input));

            if (isAdd(input)) {
                d.addInput(s);

            } else if (isSearch(input)) {
                d.search(s);
            } else if (isQuit(input)) {
                isEnd = true;
                System.out.println("Bye, see you another time!");
            }

        }
    }

    /**
     * Description: instance method, purpose is to search through some given
     * activity lists based on some keywords, start time/end time, and
     * specification on which activity, loops through the lists and removes all
     * indexes that cannot be matches, everything left over is a match, and is
     * printed.
     *
     * @param s input stream
     */
    public void search(Scanner s) {
        String keywords;
        String start = "";
        String end = "";
        Time startTime = null;
        Time endTime = null;
        String in;
        String text[];
        char activity;
        System.out.println("Please enter a few keywords you want to search for, delimited with spaces.");
        keywords = s.nextLine();

        start = getTime(s, "start");
        if (!start.isEmpty()) {
            startTime = new Time(start);
        }
        end = getTime(s, "end");
        if (!end.isEmpty()) {
            endTime = new Time(end);
        }

        do {
            System.out.println("Enter what type of Activity");
            in = s.nextLine();
            activity = whatActivity(in);
        } while (activity == '!' && !in.isEmpty());

        text = keywords.split(" ");
        List<Activity> search = new ArrayList<Activity>();
        //Depending on which activity you picked, it either adds all the lists into 
        //one big list, or just uses the seperate lists
        if (activity == '!') {
            search.addAll(school);
            search.addAll(home);
            search.addAll(other);
        } else if (activity == 'h') {
            search.addAll(home);
        } else if (activity == 'o') {
            search.addAll(other);
        } else {
            search.addAll(school);
        }

        //loop to remove all possible non matches
        out:
        for (int i = 0; i < search.size(); ++i) {
            if (text[0].length() > 0) {
                String title[] = search.get(i).getTitle().split(" ");
                for (int j = 0; j < text.length; ++j) {
                    int c = 0;
                    for (int k = 0; k < title.length; ++k) {
                        if (text[j].equalsIgnoreCase(title[k])) {
                            c++;
                        }
                    }
                    if (c == 0) {
                        search.remove(i);
                        i = -1;
                        continue out;
                    }
                }
            }
            if ((startTime != null && (!startTime.equals(search.get(i).getStartTime())) || (endTime != null && !endTime.equals(search.get(i).getEndTime())))) {
                search.remove(i);
                i = -1;

            }
        }

        //Printing all matches
        System.out.println("The results are:");
        if (search.isEmpty()) {
            System.out.println("NOTHING!");
        } else {
            for (int i = 0; i < search.size(); ++i) {
                System.out.println(search.get(i).toString());
            }
        }
    }

    /**
     * Description: inputs a date from the user, checks if it is correct by the
     * criteria in the Time class, it returns it.
     *
     * @param s input stream
     * @param startOrEnd String with contents start or end
     * @return returns a date string that needs to be parsed
     */
    private static String getTime(Scanner s, String startOrEnd) {
        String time = "";
        do {
            System.out.println("Please enter a " + startOrEnd + " time in the form YYYY:MM:DD:HH:mm.");
            time = s.nextLine();
        } while (!Time.isGoodTime(time) && !time.isEmpty());
        return time;

    }

    /**
     * Description: gets all input for which type of activity you want to
     * schedule, then takes in information on that activity and adds it to the
     * corresponding list.
     *
     * @param s input stream
     */
    public void addInput(Scanner s) {
        //Record variables
        String title;
        String startTime;
        String endTime;
        String comment;
        String location;
        char activity;

        //Input defensively
        do {
            System.out.println("Enter what type of activity you want to enter.");
            System.out.println("(h for home, s for school, o for other)");
            activity = whatActivity(s.nextLine());
        } while (activity == '!');

        do {
            System.out.println("Enter a title of the activity(short description):");
            title = s.nextLine();
            System.out.println("Please enter a date in 2014 or on.");
        } while (title.isEmpty());

        do {
            System.out.println("Please enter a non empty date. ");
            startTime = getTime(s, "start");
        } while (startTime.isEmpty());

        do {
            System.out.println("Please enter a non empty date, and/or a date after the start date.");
            endTime = getTime(s, "end");
        } while (endTime.isEmpty()); //|| (new Time(startTime)).compareTo(new Time(endTime)) == 1);

        System.out.println("Enter a comment on the activity (i.e. longer description):");
        comment = s.nextLine();

        //adding the activity to the right list
        if (activity == 'o') {
            do {
                System.out.println("Please enter a location for this 'other' activity.");
                location = s.nextLine();
            } while (location.isEmpty());
            other.add(new OtherActivity(title, startTime, endTime, comment, location));
        } else if (activity == 'h') {
            home.add(new HomeActivity(title, startTime, endTime, comment));
        } else {
            school.add(new SchoolActivity(title, startTime, endTime, comment));
        }
        System.out.println("Successfully added a new activity!");
    }

    /**
     * Description: simplifies the activity input and returns the character,
     * makes defensive coding easier
     *
     * @param in input
     * @return simplified activity
     */
    public static char whatActivity(String in) {
        if (in.equalsIgnoreCase("home") || in.equalsIgnoreCase("h")) {
            return 'h';
        } else if (in.equalsIgnoreCase("school") || in.equalsIgnoreCase("s")) {
            return 's';
        } else if (in.equalsIgnoreCase("other") || in.equalsIgnoreCase("o")) {
            return 'o';
        }
        return '!';
    }

    /**
     * Description: checks if you entered add or not
     *
     * @param in input
     * @return boolean
     */
    public static boolean isAdd(String in) {
        return in.equalsIgnoreCase("a") || in.equalsIgnoreCase("add");
    }

    /**
     * Description: checks if you entered quit or not
     *
     * @param in input
     * @return boolean
     */
    public static boolean isQuit(String in) {
        return in.equalsIgnoreCase("quit") || in.equalsIgnoreCase("q");

    }

    /**
     * Description: checks if you entered search or not
     *
     * @param in input
     * @return boolean
     */
    public static boolean isSearch(String in) {
        return in.equalsIgnoreCase("search") || in.equalsIgnoreCase("s");
    }

    /**
     * Description: checks if the input is either search quit or add
     *
     * @param in input
     * @return boolean
     */
    public static boolean isGoodInput(String in) {
        return isQuit(in) || isAdd(in) || isSearch(in);
    }
}
