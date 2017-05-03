package ua.nure.tsuvarev;

/**
 * Created by Slava on 05.04.2017.
 */
public class Times {
    
    private int times;
    
    public void add(int times) {
        this.times += times;
    }
    
    public int getValue() {
        return times;
    }
    
    @Override
    public String toString() {
        return Integer.toString(times);
    }
}
