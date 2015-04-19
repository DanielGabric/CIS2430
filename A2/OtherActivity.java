package dayplanner;

/**
 * @author Daniel Gabric
 * @studentid ******
 * @Title Day Planner
 * @Date:Friday, November 7, 2014 
 * Description: sub class of activity
 */
public class OtherActivity extends Activity {

    /**
     * field instance variable
     */
    private String location;

    /**
     * Default constructor which instantiates the superclass with "" and the
     * field variable with ""
     */
    public OtherActivity() {
        super("", "", "", "");
        location = "";
    }

    /**
     * Constructor that instantiates the superclass with the parameters given
     * intializes field to location
     *
     * @param title
     * @param startTime
     * @param endTime
     * @param comment
     * @param location
     */
    public OtherActivity(String title, String startTime, String endTime, String comment, String location) {
        super(title, startTime, endTime, comment);
        this.location = location;

    }

    /**
     * Accessor for location
     *
     * @return String
     */
    public String getLocation() {
        return location;
    }

    /**
     * Mutator for location
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return String representation of OtherActivity
     */
    @Override
    public String toString() {
        return "Title: " + title + " Start time: " + startTime + " End time: " + endTime + " Comment: " + comment + " Location: " + location;
    }

    /**
     * checks equality of two otheractivity instances
     *
     * @param a
     * @return boolean
     */
    public boolean equals(Object a) {
        return super.equals(a)
                && ((OtherActivity) a).getLocation().equals(location);
    }
}
