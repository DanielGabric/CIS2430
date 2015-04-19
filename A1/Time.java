package dayplanner;

/**
 * @author Daniel Gabric
 * @studentid ******
 * @Title Day Planner
 * @Date:Wednesday, October 15, 2014
 */
public class Time {

    /**
     * instance variables that describe a date
     */
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String date;

    /**
     * Default constructor that instantiates all the variables to 0
     */
    public Time() {
        this("0000:00:00:00:00");
    }

    /**
     * Constructor that instantiates the class with the value of the parameters
     *
     * @param date
     */
    public Time(String date) {

        this.date = date;
        loadTime();
    }

    /**
     * Helper method to load instance variables with values
     */
    private void loadTime() {
        String parsedString[] = date.split(":");
        year = Integer.parseInt(parsedString[0]);
        month = Integer.parseInt(parsedString[1]);
        day = Integer.parseInt(parsedString[2]);
        hour = Integer.parseInt(parsedString[3]);
        minute = Integer.parseInt(parsedString[4]);
    }

    /**
     * Returns de-parsed date
     *
     * @return String
     */
    public String getUnParsedTime() {
        date = year + ":" + month + ":" + day + ":" + hour + ":" + minute;
        return date;
    }

    /**
     * Accessor for year
     *
     * @return int
     */
    public int getYear() {
        return year;
    }

    /**
     * Accessor for month
     *
     * @return int
     */
    public int getMonth() {
        return month;
    }

    /**
     * Accessor for day
     *
     * @return int
     */
    public int getDay() {
        return day;
    }

    /**
     * Accessor for hour
     *
     * @return hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * Accessor for minute
     *
     * @return minute
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Mutator for year
     *
     * @param year
     */
    public void changeYear(int year) {
        this.year = year;
    }

    /**
     * Mutator for month
     *
     * @param month
     */
    public void changeMonth(int month) {
        this.month = month;
    }

    /**
     * Mutator for day
     *
     * @param day
     */
    public void changeDay(int day) {
        this.day = day;
    }

    /**
     * Mutator for hour
     *
     * @param hour
     */
    public void changeHour(int hour) {
        this.hour = hour;
    }

    /**
     * Mutator for minute
     *
     * @param minute
     */
    public void changeMinute(int minute) {
        this.minute = minute;
    }

    /**
     * return true if this()==t
     *        false if this()!=t
     *
     * @param t
     * @return boolean
     */
    public boolean equals(Time t) {
        return compareTo(t) == 0;
    }

    /**
     * Returns 1 if this() > t
     *         0 if this() == t
     *        -1 if this() < t
     *
     * @param t
     * @return int value 1,0,-1
     */
    public int compareTo(Time t) {
        if (year > t.getYear() || month > t.getMonth() || day > t.getDay() || hour > t.getHour() || minute > t.getMinute()) {
            return 1;
        } else if (year == t.getYear() && month == t.getMonth() && day == t.getDay() && hour == t.getHour() && minute == t.getMinute()) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * Checks whether an unparsed date will be valid for this class
     *
     * @param in
     * @return boolean
     */
    public static boolean isGoodTime(String in) {
        int days[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (in.isEmpty()) {
            return true;
        }
        int n;
        String text[] = in.split(":");
        if (text.length != 5) {
            return false;
        }
        int month = 0;
        for (int i = 0; i < 5; ++i) {
            try {
                n = Integer.parseInt(text[i]);
            } catch (NumberFormatException nfe) {
                return false;
            }
            if (i == 0) {
                if (text[i].length() != 4 || n < 2014) {
                    return false;
                }
            } else if (i == 1) {
                month = n - 1;
                if (text[i].length() != 2 || (n > 12 || n < 1)) {
                    return false;
                }
            } else if (i == 2) {
                if (text[i].length() != 2 || n > days[month] || n < 0) {
                    return false;
                }
            } else if (i == 3) {
                if (text[i].length() != 2 || n > 23 || n < 0) {
                    return false;
                }
            } else if (i == 4) {
                if (text[i].length() != 2 || n > 59 || n < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @return String representation of Time
     */
    @Override
    public String toString() {
        return "Year: " + year + " Month: " + month + " Day: " + day + " Hour: " + hour + " Minute: " + minute;
    }
}
