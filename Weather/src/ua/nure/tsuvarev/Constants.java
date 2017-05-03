package ua.nure.tsuvarev;

/**
 * Created by void on 05.04.2017.
 */
public final class Constants {

    public static final int BOUND = 10;

    public static final int UPPER_CONTROL_HEIGHT = 24;
    public static final int DOWN_CONTROL_HEIGHT = 500;

    public static final int LEFT_CONTROL_WIDTH = 500;
    public static final int RIGHT_CONTROL_WIDTH = 200;

    public static final int LEFT_CONTROL_X = BOUND;
    public static final int RIGHT_CONTROL_X = 2 * BOUND + LEFT_CONTROL_WIDTH;

    public static final int UPPER_CONTROL_Y = BOUND;
    public static final int DOWN_CONTROL_Y = 2 * BOUND + UPPER_CONTROL_HEIGHT;

    public static final int WINDOW_WIDTH = 3 * BOUND + LEFT_CONTROL_WIDTH + RIGHT_CONTROL_WIDTH;
    public static final int WINDOW_HEIGHT = 3 * BOUND + UPPER_CONTROL_HEIGHT + DOWN_CONTROL_HEIGHT;



    public static final String BUTTON_LABEL = "Show!";

    public static final String[] MONTHS;
    
    public static final String SERVLET_ADDRESS = "http://localhost:8080/Weather?";
    
    static {
        MONTHS = new String[]{
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
        };
    }

    public static final String TEMPERATURE_LABEL_TEXT = "Average temperature: ";

    public static final int CARDINAL_DIRECTIONS_COUNT = 8;

    private Constants() {}
}
