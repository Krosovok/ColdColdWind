package ua.nure.tsuvarev;

/**
 * Created by Slava on 05.04.2017.
 */
public class DbConnectionException extends RuntimeException {
    
    public static final String ERROR = "Error in database accessing:\n";
    
    public DbConnectionException(String s, Exception e)  { super(s, e); }
    
    public DbConnectionException(String s) {
        super(s);
    }
}
