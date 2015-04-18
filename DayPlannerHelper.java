package dayplanner;

import java.util.*;
import java.io.*;
import javax.swing.*;

/**
 * @author ***********
 * @studentid **********
 * @Title Day Planner
 * @Date:Friday, November 7, 2014
 * @Description: Main class which handles the input and output
 */
public class DayPlannerHelper  {

    /**
     * One arraylist that holds activities hashmap that holds keys to indexes of
     * tokenized titles
     */
    private ArrayList<Activity> activities;
    private HashMap<String, ArrayList<Integer>> key;

    /**
     * Default constructor initializes instance ArrayList and Hashmap
     */
    public DayPlannerHelper() {
        activities = new ArrayList<Activity>();
        key = new HashMap<String, ArrayList<Integer>>();
    }

    /**
     * Description:Reads in values from textfile and loads arraylist with it
     *
     * @param s input stream to text file
     */
    public void readAndLoad(Scanner s, boolean isBegin) {
        String title = "";
        String startDate = "";
        String endDate = "";
        String comment = "";
        String location = "";
        String activity = "";
        int numErrors = 0;
        while (s.hasNext()) {
            activity = s.nextLine();
            title = s.nextLine();
            startDate = s.nextLine();
            endDate = s.nextLine();
            comment = s.nextLine();
            if (activity.charAt(0) == 'o') {
                location = s.nextLine();
            }
            try {
                if (activity.charAt(0) == 'o') {
                    //location = s.nextLine();
                    activities.add(new OtherActivity(title, startDate, endDate, comment, location));
                    for (String i : title.split(" ")) {
                        addToHash(i.toLowerCase());
                    }
                } else if (activity.charAt(0) == 'h') {
                    activities.add(new HomeActivity(title, startDate, endDate, comment));
                    for (String i : title.split(" ")) {
                        addToHash(i.toLowerCase());
                    }
                } else if (activity.charAt(0) == 's') {
                    activities.add(new SchoolActivity(title, startDate, endDate, comment));
                    for (String i : title.split(" ")) {
                        addToHash(i.toLowerCase());
                    }
                } else {
                    throw new BadActivityException();
                }
            } catch (BadActivityException bae) {
                numErrors++;
            } catch (BadTimeException bte) {
                numErrors++;
            } catch (EmptyStringException e) {
                numErrors++;
            }
        }
        if (!isBegin) {
            JOptionPane.showMessageDialog(null, "There were " + numErrors + " corrupted Activities in the file!");
        } else {
            System.out.println("There were " + numErrors + " corrupted Activities in the file!");
        }
    }

    /**
     * Description: Saves values of arraylists into textfile to use later
     *
     * @param out output stream to text file
     */
    public void writeToFile(PrintWriter out) throws BadTimeException, EmptyStringException {
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
     * @param keywords
     * @param start
     * @param end
     * @param activity
     * @return
     */
    public String search(String keywords, String start, String end, char activity) throws BadTimeException, EmptyStringException {
        Time startTime = null;
        Time endTime = null;

        if (!start.isEmpty()) {
            try {
                startTime = new Time(start);
            } catch (BadTimeException b) {
                return "Invalid Start Time entered.\n";
            }

        }

        if (!end.isEmpty()) {
            try {
                endTime = new Time(end);
                if (startTime != null && startTime.compareTo(endTime) > 0) {
                    return "Start time is later than end time.\n";
                }
            } catch (BadTimeException b) {
                return "Invalid End Time entered.\n";
            }
        }

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
                // System.out.println(a);
                // System.out.println(a.size());
                for (int i = 1; i < a.size(); ++i) {
                    a.set(0, intersect(a.get(0), a.get(i)));
                }
                for (int i : a.get(0)) {
                    search.add(activities.get(i - 1));
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

        String result = "The results are:\n\n";
        //Printing all matches
        if (search.isEmpty()) {
            result += "NOTHING!\n";
        } else {
            for (int i = 0; i < search.size(); ++i) {
                result += getCleanedToString(search.get(i));
            }
        }
        return result;
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

    public String getNextActivity(int year, int month, int day) {
        int hour = 0;
        int minute = 0;
        Time t = null;
        Time min = null;
        try {
            t = new Time(year + ":" + month + ":" + day + ":" + hour + ":" + minute);
            min = new Time("9999999:10:10:10:10");
        } catch (BadTimeException e) {
        }
        String sum = "";

        for (int i = 0; i < activities.size(); ++i) {
            if(activities.get(i).getStartTime().compareTo(min)<0 && activities.get(i).getStartTime().compareTo(t)>0){
                min = activities.get(i).getStartTime();
                sum = min.getYear() + "/" + min.getMonth() + "/" + min.getDay();
            }
        }
    
        if (sum.isEmpty()) {
            return "No next activity!";
        } else {
            return sum;
        }

    }

    /**
     *
     * @param input
     * @return
     * @throws EmptyStringException
     */
    public Object[] isValidCalender(String input) throws EmptyStringException, NumberFormatException {
        Object[] o = new Object[3];
        String text[] = input.split("/");
        if (text.length != 2) {
            throw new EmptyStringException("Calender search bar");
        }
        int m;
        int y;

        m = Integer.parseInt(text[1]);
        y = Integer.parseInt(text[0]);
        o[1] = m;
        o[2] = y;

        if (m > 12 || m < 0) {
            o[0] = false;
            return o;
        }
        if (y < 0) {
            o[0] = false;
            return o;
        }
        o[0] = true;
        return o;
    }

    /**
     * Cleans up the toString command and makes it more presentable for output
     *
     * @param a
     * @return
     */
    private String getCleanedToString(Activity a) {
        String sum = "";
        sum += "Title: " + a.getTitle();
        sum += "\nStart date: " + a.getStartTime().getYear() + "/" + a.getStartTime().getMonth() + "/" + a.getStartTime().getDay();
        sum += "\nStart time: " + a.getStartTime().getHour() + ":" + a.getStartTime().getMinute();
        sum += "\nEnd date: " + a.getEndTime().getYear() + "/" + a.getEndTime().getMonth() + "/" + a.getEndTime().getDay();
        sum += "\nEnd time: " + a.getEndTime().getHour() + ":" + a.getEndTime().getMinute();
        try {
            sum += "\nLocation: " + ((a.getClass() == new OtherActivity().getClass()) ? ((OtherActivity) a).getLocation() : 0 / 0);
        } catch (BadTimeException bte) {
        } catch (EmptyStringException ese) {
        } catch (ArithmeticException e) {
        }
        sum += "\nComments: " + a.getComment() + "\n\n";
        return sum;
    }

    /**
     * Description: Checks if there is an overlaps between two activities
     *
     * @param t
     * @param s
     * @param t1
     * @param s1
     * @return
     */
    public boolean overlaps(Time t, Time s, Time t1, Time s1) {
        if (((t.compareTo(t1) >= 0 && t.compareTo(s1) <= 0) || (s.compareTo(t1) >= 0 && s.compareTo(s1) <= 0))
                || t.compareTo(t1) <= 0 && s.compareTo(s1) >= 0) {
            return true;
        }
        return false;
    }

    /**
     * Description: Gets all overlaps of a given time interval
     *
     * @param start
     * @param end
     * @return
     */
    public String getOverlaps(Time start, Time end) {
        String sum = "";

        for (int i = 0; i < activities.size(); ++i) {
            Activity a = activities.get(i);
            Time start1 = a.getStartTime();
            Time end1 = a.getEndTime();
            if (start != null && end != null) {
                if (overlaps(start, end, start1, end1)) {
                    sum += getCleanedToString(a);

                }
            } else if (start == null && end != null) {
                if ((end.compareTo(start1) >= 0 && end.compareTo(end1) <= 0)) {
                    sum += getCleanedToString(a);

                }
            } else if (start != null && end == null) {
                if ((start.compareTo(start1) >= 0 && start.compareTo(end1) <= 0)) {
                    sum += getCleanedToString(a);

                }
            }
        }
        return sum;
    }

    /**
     * Returns string with all activities within a given date
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public String getAllActivityInTime(int year, int month, int day) {
        String sum = "";
        for (int i = 0; i < activities.size(); ++i) {
            Activity a = activities.get(i);
            if (year == a.getStartTime().getYear()
                    && month + 1 == a.getStartTime().getMonth()
                    && day == a.getStartTime().getDay()) {
                sum += getCleanedToString(a);

            }
        }
        return sum;
    }

    /**
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public boolean isTimeIn(int year, int month, int day) {
        for (int i = 0; i < activities.size(); ++i) {
            if (activities.get(i).getStartTime().getYear() == year
                    && activities.get(i).getStartTime().getMonth() == month + 1
                    && activities.get(i).getStartTime().getDay() == day + 1) {

                return true;
            }
        }
        return false;
    }

    /**
     * Description: gets all input for which type of activity you want to
     * schedule, then takes in information on that activity and adds it to the
     * corresponding list.
     *
     * @param title
     * @param endTime
     * @param startTime
     * @param location
     * @param activity
     * @param comment
     */
    public String addInput(String title, String startTime, String endTime, String comment, String location, char activity) {

        //adding the activity to the right list
        try {
            activities.add((activity == 'o' ? new OtherActivity(title, startTime, endTime, comment, location)
                    : activity == 'h' ? new HomeActivity(title, startTime, endTime, comment)
                            : new SchoolActivity(title, startTime, endTime, comment)));
            // System.out.println(title);
        } catch (EmptyStringException e) {
            return "Empty title or location!";
        } catch (BadTimeException b) {
            return "Invalid start or end times! Or start time is later than end time!";
        }

        //Input defensively
        String tokenize[] = title.split(" ");

        for (String i : tokenize) {
            addToHash(i.toLowerCase());
        }

        return "Successfully added a new activity!";

    }

}
