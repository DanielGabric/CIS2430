package dayplanner;

/**
 * @author Daniel Gabric
 * @studentid ******
 * @Title Day Planner
 * @Date:Wednesday, October 15, 2014 Description: sub class of activity
 */
public class SchoolActivity extends Activity {

    /**
     * Default constructor which instantiates the superclass with ""
     */
    public SchoolActivity() {
        super("", "", "", "");

    }

    /**
     * Constructor that instantiates the super class with the parameters
     *
     * @param title
     * @param startTime
     * @param endTime
     * @param comment
     */
    public SchoolActivity(String title, String startTime, String endTime, String comment) {
        super(title, startTime, endTime, comment);
    }

    /**
     * @return String representation of SchoolActivity
     */
    @Override
    public String toString() {
        return "Title: " + title + " Start time: " + start.getUnParsedTime() + " End time: " + end.getUnParsedTime() + " Comment: " + comment;
    }
}
