package dayplanner;

/**
 * @author 
 * @studentid 
 * @Title Day Planner
 * @Date:Friday, November 7, 2014 
 * @Description: sub class of activity
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
    public OtherActivity() throws EmptyStringException,BadTimeException{
        super();
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
    public OtherActivity(String title, String startTime, String endTime, String comment, String location) throws EmptyStringException,BadTimeException{
        super(title, startTime, endTime, comment);
        if(location.isEmpty())
            throw new EmptyStringException("Location");
        this.location = location;

    }

    /**
     * Accessor for location
     *
     * @return String
     */
    public String getLocation() {
        return new String(location);
    }

    /**
     * Mutator for location
     *
     * @param location
     * @throws dayplanner.EmptyStringException
     */
    public void setLocation(String location) throws EmptyStringException{
        if(location.isEmpty())
            throw new EmptyStringException("Location");
        this.location = location;
    }

    /**
     * @return String representation of OtherActivity
     */
    @Override
    public String toString() {
        return "Title: " + title + "\nStart time: " + startTime + "\nEnd time: " + endTime + "\nLocation: " + location + "\nComment: " + comment +"\n";
    }

    /**
     * checks equality of two otheractivity instances
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
            OtherActivity l = (OtherActivity) a;
            return title.equalsIgnoreCase(l.getTitle())
                    && start.equals(l.getStartTime())
                    && end.equals(l.getEndTime())
                    && comment.equalsIgnoreCase(l.getComment())
                    && location.equalsIgnoreCase(l.getLocation());
        }
    }
}
