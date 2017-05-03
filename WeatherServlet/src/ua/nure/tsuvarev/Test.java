package ua.nure.tsuvarev;

/**
 * Created by Slava on 05.04.2017.
 */
public class Test {
    
    public static void main(String[] args) {
    
        SinoptikSite sinoptik = new SinoptikSite();
        WeatherData weatherInMonth = sinoptik.getWeatherInMonth(5);
    
        WeatherData fromString = WeatherData.fromString(weatherInMonth.toString());
    }
}
