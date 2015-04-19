package dayplanner;

/**
 * @author Daniel Gabric
 * @studentid ******
 * @Title Activity
 * @Date:Wednesday, November 7, 2014
 * Description: super class to all other
 * activities
 */
public abstract class Activity {

    /**
     * Record variables
     */
    protected String title;
    protected String startTime;
    protected String endTime;
    protected Time start;
    protected Time end;
    protected String comment;

    /**
     * Default constructor initializes everything to ""
     */
    public Activity() {
        this("", "", "", "");
    }

    /**
     * Constructor that sets Record variables to the parameters
     *
     * @param title
     * @param startTime
     * @param endTime
     * @param comment
     */
    public Activity(String title, String startTime, String endTime, String comment) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        if (!startTime.isEmpty()) {
            instantiateTime(true);
        }
        if (!endTime.isEmpty()) {
            instantiateTime(false);
        }
        this.comment = comment;
    }

    /**
     * Accessor for comment
     *
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Mutator for comment
     *
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Accessor for title
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Mutator for title
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Accessor for the start time
     *
     * @return start
     */
    public Time getStartTime() {
        return start;
    }

    /**
     * Mutator for the start time
     *
     * @param startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
        instantiateTime(true);
    }

    /**
     * Accessor for the end time
     *
     * @return end
     */
    public Time getEndTime() {
        return end;
    }

    /**
     * Mutator for the end time
     *
     * @param endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
        instantiateTime(false);
    }

    /**
     * Description: instantiates either start or end depending on isStart
     * boolean
     *
     * @param isStart
     */
    private void instantiateTime(boolean isStart) {
        if (isStart) {
            start = new Time(startTime);
        } else {
            end = new Time(endTime);
        }
    }

    /**
     * checks equality of two activity instances
     *
     * @param a
     * @return boolean
     */
    public boolean equals(Object a) {
        if (a == null) {
            return false;
        } else if (getClass() != a.getClass()) {
            return false;
        } else {
            Activity l = (Activity) a;
            return title.equalsIgnoreCase(l.getTitle())
                    && start.equals(l.getStartTime())
                    && end.equals(l.getEndTime())
                    && comment.equalsIgnoreCase(l.getComment());
        }

    }

    /**
     * Abstract, every subclass needs one with different implementation
     *
     * @return string representation of class
     */
    public abstract String toString();

}
