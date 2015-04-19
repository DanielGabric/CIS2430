package dayplanner;

/**
 * @author **********
 * @studentid ******
 * @Title Activity
 * @Date:Wednesday, November 7, 2014 
 * @Description: super class to all other
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
     * @throws dayplanner.BadTimeException
     * @throws dayplanner.EmptyStringException
     */
    public Activity() throws BadTimeException,EmptyStringException{
        this("EMPTY", "0:1:1:1:1", "0:1:1:1:2", "");
    }

    /**
     * Constructor that sets Record variables to the parameters
     *
     * @param title
     * @param startTime
     * @param endTime
     * @param comment
     * @throws dayplanner.BadTimeException
     * @throws dayplanner.EmptyStringException
     */
    public Activity(String title, String startTime, String endTime, String comment) throws BadTimeException,EmptyStringException{
        if(endTime.isEmpty())
            throw new BadTimeException();
        if(startTime.isEmpty())
            throw new BadTimeException();
        if(title.isEmpty())
            throw new EmptyStringException("Title");
        this.title = title;
        instantiateTime(startTime,true);
        instantiateTime(endTime,false);
        if(startTime.compareTo(endTime)>=0)
            throw new BadTimeException();
        
        this.startTime = startTime;
        this.endTime = endTime;
        this.comment = comment;
    }

    /**
     * Accessor for comment
     *
     * @return comment
     */
    public String getComment() {
        return new String(comment);
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
        return new String(title);
    }

    /**
     * Mutator for title
     *
     * @param title
     * @throws dayplanner.EmptyStringException
     */
    public void setTitle(String title) throws EmptyStringException{
        if(title.isEmpty())
            throw new EmptyStringException("Title");
        this.title = title;
    }

    /**
     * Accessor for the start time
     *
     * @return start
     */
    public Time getStartTime() {
        return start.clone();
    }

    /**
     * Mutator for the start time
     *
     * @param startTime
     * @throws dayplanner.BadTimeException
     */
    public void setStartTime(String startTime)throws BadTimeException {
        instantiateTime(startTime,true);
        this.startTime = startTime;  
    }

    /**
     * Accessor for the end time
     *
     * @return end
     */
    public Time getEndTime() {
        return end.clone();
    }

    /**
     * Mutator for the end time
     *
     * @param endTime
     * @throws dayplanner.BadTimeException
     */
    public void setEndTime(String endTime) throws BadTimeException{
        instantiateTime(endTime,false);
        this.endTime = endTime;
    }

    /**
     * Description: instantiates either start or end depending on isStart
     * boolean
     *
     * @param isStart
     */
    private void instantiateTime(String time,boolean isStart)throws BadTimeException {
        if (isStart) {
            start = new Time(time);
        } else {
            end = new Time(time);
        }
    }

    /**
     * abstract equals
     *
     * @param a
     * @return boolean
     */
    @Override
    public abstract boolean equals(Object a);

    /**
     * Abstract, every subclass needs one with different implementation
     *
     * @return string representation of class
     */
    @Override
    public abstract String toString();

}
