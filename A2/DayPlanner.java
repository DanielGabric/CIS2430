package dayplanner;

import java.util.*;
import java.io.*;

/**
 * @author Daniel Gabric
 * @studentid ******
 * @Title Day Planner
 * @Date:Friday, November 7, 2014
 * Description: Main class which handles the
 * input and output
 */
public class DayPlanner {

    /**
     * One arraylist that holds activities hashmap that holds keys to indexes of
     * tokenized titles
     */
    private ArrayList<Activity> activities;
    private HashMap<String, ArrayList<Integer>> key;

    /**
     * Default constructor initializes instance ArrayList and Hashmap
     */
    public DayPlanner() {
        activities = new ArrayList<Activity>();
        key = new HashMap<String, ArrayList<Integer>>();
    }

    /**
     * Description: Holds command loop, takes in input static so that it can be
     * run without the class being instantiated
     *
     * @param args
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("You have not specified an input file! Exiting!!");
            System.exit(0);
        }
        //new instance of dayplanner class
        DayPlanner d = new DayPlanner();
        PrintWriter out=null;
        try {
            d.readAndLoad(new Scanner(new File(args[0])));
            out = new PrintWriter(new File(args[0]));
        } catch (FileNotFoundException f) {
            System.out.println("Invalid file path/name, exiting!");
            System.exit(0);
        }
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
        d.writeToFile(out);
        s.close();
    }

    /**
     * Description:Reads in values from textfile and loads arraylist with it
     *
     * @param s input stream to text file
     */
    public void readAndLoad(Scanner s) {
        String title = "";
        String startDate = "";
        String endDate = "";
        String comment = "";
        String location = "";
        String activity = "";
        while (s.hasNext()) {
            activity = s.nextLine();
            title = s.nextLine();
            startDate = s.nextLine();
            endDate = s.nextLine();
            comment = s.nextLine();
            if (activity.charAt(0) == 'o') {
                location = s.nextLine();
                activities.add(new OtherActivity(title, startDate, endDate, comment, location));
            } else if (activity.charAt(0) == 'h') {
                activities.add(new HomeActivity(title, startDate, endDate, comment));
            } else {
                activities.add(new SchoolActivity(title, startDate, endDate, comment));
            }
        }
        s.close();
    }

    /**
     * Description: Saves values of arraylists into textfile to use later
     *
     * @param out output stream to text file
     */
    public void writeToFile(PrintWriter out) {
        String location;
        for (int i = 0; i < activities.size(); ++i) {
            location = "";
            if (activities.get(i).getClass().equals(new HomeActivity().getClass())) {
                out.println("h");
            } else if (activities.get(i).getClass().equals(new SchoolActivity().getClass())) {
                out.println("s");
            } else if (activities.get(i).getClass().equals(new OtherActivity().getClass())) {
                out.println("o");
                location = ((OtherActivity) activities.get(i)).getLocation();
            }
            out.println(activities.get(i).getTitle());
            out.println(activities.get(i).getStartTime().getUnParsedTime());
            out.println(activities.get(i).getEndTime().getUnParsedTime());
            out.println(activities.get(i).getComment());
            if (!location.isEmpty()) {
                out.println(location);
            }
        }
        out.close();
    }

    /**
     * Helper method
     *
     * @param a
     * @param b
     * @return ArrayList that has elements that are in both in a and b
     */
    private ArrayList<Integer> intersect(ArrayList<Integer> a, ArrayList<Integer> b) {
        ArrayList<Integer> c = new ArrayList<Integer>();
        for (int i = 0; i < a.size(); ++i) {
            for (int j = 0; j < b.size(); ++j) {
                if (a.get(i) == b.get(j) && !c.contains(a.get(i))) {
                    c.add(a.get(i));
                }
            }
        }
        return c;
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

        List<Activity> search = new ArrayList<Activity>();
        //System.out.println(text.length+" "+text[0]);
        if (!keywords.isEmpty()) {
            boolean isGood = true;
            ArrayList<ArrayList<Integer>> a = new ArrayList<ArrayList<Integer>>();
            for (String i : keywords.split(" ")) {
                if (key.containsKey(i)) {
                    i = i.toLowerCase();
                    a.add(key.get(i));
                } else {
                    isGood = false;
                    break;
                }
            }
            if (isGood) {
                for (int i = 1; i < a.size(); ++i) {
                    a.set(0, intersect(a.get(0), a.get(i)));
                }
                for (int i : a.get(0)) {
                    search.add(activities.get(i));
                }
            }
        } else {
            search.addAll(activities);
        }

        
        for (int i = 0; i < search.size(); ++i) {
            if ((activity == 'h' && !new HomeActivity().getClass().equals(search.get(i).getClass()))
                    || (activity == 'o' && !new OtherActivity().getClass().equals(search.get(i).getClass()))
                    || (activity == 's' && !new SchoolActivity().getClass().equals(search.get(i).getClass()))) {
                search.remove(i);
                i = -1;
            }
        }
        //loop to remove all possible non matches
        for (int i = 0; i < search.size(); ++i) {
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
     * Description: Adds new index to hashmap
     *
     * @param toAdd
     */
    private void addToHash(String toAdd) {
        ArrayList<Integer> a = new ArrayList<Integer>();
        if (key.containsKey(toAdd)) {
            a.addAll(key.get(toAdd));
            a.add(activities.size());
            key.remove(toAdd);
            key.put(toAdd, a);
        } else {
            a.add(activities.size());
            key.put(toAdd, a);
        }
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

        String tokenize[] = title.split(" ");

        for (String i : tokenize) {
            addToHash(i.toLowerCase());
        }

        //adding the activity to the right list
        if (activity == 'o') {
            do {
                System.out.println("Please enter a location for this 'other' activity.");
                location = s.nextLine();
            } while (location.isEmpty());
            activities.add(new OtherActivity(title, startTime, endTime, comment, location));
        } else if (activity == 'h') {
            activities.add(new HomeActivity(title, startTime, endTime, comment));
        } else {
            activities.add(new SchoolActivity(title, startTime, endTime, comment));
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
