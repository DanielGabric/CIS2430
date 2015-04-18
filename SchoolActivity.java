package dayplanner;

/**
 * @author 
 * @studentid 
 * @Title Day Planner
 * @Date:Friday, November 7, 2014
 * @Description: sub class of activity
 */
public class SchoolActivity extends Activity {

    /**
     * Default constructor which instantiates the superclass with ""
     * @throws dayplanner.BadTimeException
     * @throws dayplanner.EmptyStringException
     */
    public SchoolActivity() throws BadTimeException, EmptyStringException {
        super();

    }

    /**
     * Constructor that instantiates the super class with the parameters
     *
     * @param title
     * @param startTime
     * @param endTime
     * @param comment
     * @throws dayplanner.BadTimeException
     * @throws dayplanner.EmptyStringException
     */
    public SchoolActivity(String title, String startTime, String endTime, String comment) throws BadTimeException, EmptyStringException {
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
            SchoolActivity l = (SchoolActivity) a;
            return title.equalsIgnoreCase(l.getTitle())
                    && start.equals(l.getStartTime())
                    && end.equals(l.getEndTime())
                    && comment.equalsIgnoreCase(l.getComment());
        }

    }

    /**
     * @return String representation of SchoolActivity
     */
    @Override
    public String toString() {
        return "Title: " + title + "\nStart time: " + start.getUnParsedTime() + "\nEnd time: " + end.getUnParsedTime() + "\nComment: " + comment + "\n";
    }

}
