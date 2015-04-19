package dayplanner;

/**
 * @author **********
 * @studentid *******
 * @Title Day Planner
 * @Date:Friday, November 7, 2014
 */
public class BadTimeException extends Exception{
    /**
     * Exception to be thrown if time is invalid
     */
    public BadTimeException(){
        super("Invalid time!");
    }
}
