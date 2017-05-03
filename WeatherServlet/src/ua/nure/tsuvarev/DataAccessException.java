package ua.nure.tsuvarev;

/**
 * Created by Slava on 05.04.2017.
 */
public class DataAccessException extends RuntimeException {
    public DataAccessException(String s, Exception e) {
        super(s, e);
    }
}
