package dayplanner;

/**
 * @author Daniel Gabric
 * @studentid ******
 * @Title Day Planner
 * @Date:Wednesday, October 15, 2014 Description: sub class of activity
 */
public class HomeActivity extends Activity {

    /**
     * Default constructor the instantiates the super class with ""
     */
    public HomeActivity() {
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
    public HomeActivity(String title, String startTime, String endTime, String comment) {
        super(title, startTime, endTime, comment);
    }

    /**
     * @return String representation of HomeActivity
     */
    @Override
    public String toString() {
        return "Title: " + title + " Start time: " + startTime + " End time: " + endTime + " Comment: " + comment;
    }

}
