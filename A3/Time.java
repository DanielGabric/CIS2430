package dayplanner;

/**
 * @author 
 * @studentid 
 * @Title Day Planner
 * @Date:Friday, November 7, 2014
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
     * @throws dayplanner.BadTimeException
     */
    public Time() throws BadTimeException {
        this("0000:01:01:01:01");
    }

    /**
     * Constructor that instantiates the class with the value of the parameters
     *
     * @param date
     * @throws dayplanner.BadTimeException
     */
    public Time(String date) throws BadTimeException {
        if (!isGoodTime(date)) {
            throw new BadTimeException();
        }
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
        return new String(date);
    }

    /**
     * Accessor for year
     *
     * @return int
     */
    public int getYear() {
        return new Integer(year);
    }

    /**
     * Accessor for month
     *
     * @return int
     */
    public int getMonth() {
        return new Integer(month);
    }

    /**
     * Accessor for day
     *
     * @return int
     */
    public int getDay() {
        return new Integer(day);
    }

    /**
     * Accessor for hour
     *
     * @return hour
     */
    public int getHour() {
        return new Integer(hour);
    }

    /**
     * Accessor for minute
     *
     * @return minute
     */
    public int getMinute() {
        return new Integer(minute);
    }

    /**
     * Mutator for year
     *
     * @param year
     * @throws dayplanner.BadTimeException
     */
    public void changeYear(int year) throws BadTimeException{
        if(year < 0)
            throw new BadTimeException();
        this.year = year;
    }

    /**
     * Mutator for month
     *
     * @param month
     * @throws dayplanner.BadTimeException
     */
    public void changeMonth(int month) throws BadTimeException{
        if(month < 1 || month > 12)
            throw new BadTimeException();
        this.month = month;
    }

    /**
     * Mutator for day
     *
     * @param day
     * @throws dayplanner.BadTimeException
     */
    public void changeDay(int day) throws BadTimeException{
        if(day < 1 || day > 31)
            throw new BadTimeException();
        this.day = day;
    }

    /**
     * Mutator for hour
     *
     * @param hour
     * @throws dayplanner.BadTimeException
     */
    public void changeHour(int hour) throws BadTimeException{
        if(hour < 0||hour > 23)
            throw new BadTimeException();
        this.hour = hour;
    }

    /**
     * Mutator for minute
     *
     * @param minute
     * @throws dayplanner.BadTimeException
     */
    public void changeMinute(int minute) throws BadTimeException{
        if(minute < 0 || minute > 59)
            throw new BadTimeException();
        this.minute = minute;
    }

    /**
     * return true if this()==t false if this()!=t
     *
     * @param t
     * @return boolean
     */
    public boolean equals(Time t) {
        return compareTo(t) == 0;
    }

    /**
     * Returns 1 if this() > t 0 if this() == t -1 if this() < t
     *
     * @param t
     * @return int value 1,0,-1
     */
    public int compareTo(Time t) {
        String text[] = date.split(":");

        long r = 0;
        r += Long.parseLong(text[0]);
        r *= 100;
        r += Long.parseLong(text[1]);
        r *= 100;
        r += Long.parseLong(text[2]);
        r *= 100;
        r += Long.parseLong(text[3]);
        r *= 100;
        r += Long.parseLong(text[4]);
        String text1[] = t.getUnParsedTime().split(":");

        long s = 0;
        s += Long.parseLong(text1[0]);
        s *= 100;
        s += Long.parseLong(text1[1]);
        s *= 100;
        s += Long.parseLong(text1[2]);
        s *= 100;
        s += Long.parseLong(text1[3]);
        s *= 100;
        s += Long.parseLong(text1[4]);
        if (r > s) {
            return 1;
        } else if (r == s) {
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
                if (n % 4 == 0 && n % 100 != 0) {
                    if (n % 100 != 0) {
                        days[1] = 29;
                    }
                } else if (n % 400 == 0) {

                    days[1] = 29;
                }

                if (n < 0) {
                    return false;
                }
            } else if (i == 1) {
                month = n - 1;
                if ((n > 12 || n < 1)) {
                    return false;
                }
            } else if (i == 2) {
                if (n > days[month] || n < 0) {
                    return false;
                }
            } else if (i == 3) {
                if (n > 23 || n < 0) {
                    return false;
                }
            } else if (i == 4) {
                if (n > 59 || n < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * clones the time to prevent privacy leaks
     *
     * @return
     */
    @Override
    public Time clone() {
        try {
            return new Time(getUnParsedTime());
        } catch (BadTimeException b) {
            return null;
        }
    }

    /**
     * @return String representation of Time
     */
    @Override
    public String toString() {
        return "Year: " + year + " Month: " + month + " Day: " + day + " Hour: " + hour + " Minute: " + minute;
    }
}
