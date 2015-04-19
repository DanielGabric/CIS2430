package dayplanner;

/**
 * @author ********
 * @studentid *********
 * @Title Day Planner
 * @Date:Friday, November 7, 2014
 * @Description: sub class of activity
 */
public class HomeActivity extends Activity {

    /**
     * Default constructor the instantiates the super class with ""
     * @throws dayplanner.BadTimeException
     * @throws dayplanner.EmptyStringException
     */
    public HomeActivity() throws BadTimeException, EmptyStringException {
        super();

    }

    /**
     * Constructor that instantiates the super class with the parameters
     *
     * @param title
     * @param startTime
     * @param endTime
     * @param comment
     * @throws dayplanner.EmptyStringException
     * @throws dayplanner.BadTimeException
     */
    public HomeActivity(String title, String startTime, String endTime, String comment) throws EmptyStringException, BadTimeException {
        super(title, startTime, endTime, comment);
    }

    /**
     * checks equality of two activity instances
     *
     * @param a
     * @return boolean
     */
    @Override
    public boolean equals(Object a) {
        if (a == null) {
            return false;
        } else if (getClass() != a.getClass()) {
            return false;
        } else {
            HomeActivity l = (HomeActivity) a;
            return title.equalsIgnoreCase(l.getTitle())
                    && start.equals(l.getStartTime())
                    && end.equals(l.getEndTime())
                    && comment.equalsIgnoreCase(l.getComment());
        }

    }

    /**
     * @return String representation of HomeActivity
     */
    @Override
    public String toString() {
        return "Title: " + title + "\nStart time: " + startTime + "\nEnd time: " + endTime + "\nComment: " + comment + "\n";
    }

}
