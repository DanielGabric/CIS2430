package dayplanner;

/**
 * @author **********
 * @studentid *********
 * @Title Day Planner
 * @Date:Friday, November 7, 2014
 */
public class EmptyStringException extends Exception{
    /**
     * Exception to be thrown if it a string is empty where it shouldn't be
     * @param describer 
     */
    public EmptyStringException(String describer){
        super(describer+" was empty.\n");
    }
}
