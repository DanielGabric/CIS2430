package dayplanner;

/**
 * @author *******
 * @studentid ******
 * @Title Day Planner
 * @Date:Friday, November 7, 2014
 */
public class BadActivityException extends Exception{
    /**
     * Exception to be thrown if an activity is bad in text file
     */
    public BadActivityException(){
        super("Invalid activity!");
    }
}
