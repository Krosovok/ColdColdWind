package ua.nure.tsuvarev;

/**
 * Created by Slava on 05.04.2017.
 */
public class Date {
    private int month;
    private int year;
    
    public Date(int month, int year) {
        this.month = month;
        this.year = year;
    }
    
    public int getMonth() {
        return month;
    }
    
    public void setMonth(int month) {
        this.month = month;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Date) || obj == null) {
            return false;
        }
        Date other = (Date) obj;
        return month == other.month && year == other.year;
    }
    
    @Override
    public int hashCode() {
        return year ^ month;
    }
}
