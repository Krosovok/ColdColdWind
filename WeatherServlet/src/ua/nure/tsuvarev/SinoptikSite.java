package ua.nure.tsuvarev;

import org.htmlcleaner.TagNode;

import java.time.YearMonth;

/**
 * Created by Slava on 05.04.2017.
 */
public class SinoptikSite {
    
    public static final int YEAR_2016 = 2016;
    private static final String ADDRESS_AND_CITY =
        "https://sinoptik.ua/%D0%BF%D0%BE%D0%B3%D0%BE%D0%B4%D0%B0-" +
        "%D1%85%D0%B0%D1%80%D1%8C%D0%BA%D0%BE%D0%B2/";
    private static final String YEAR = "%s";
    private static final String MONTH = "%s";
    private static final String DAY = "%s";
    
    private static final String FORMAT_PATH = YEAR + "-" + MONTH + "-" + DAY;
    
    private static final String FORMAT_TEMPERATURE_XPATH = "//div[@class='main loaded']//div[@class='%s']/span";
    private static final String MIN = "min";
    private static final String MAX = "max";
    
    private static final String FORMAT_WINDS_XPATH = "//div[@class='Tooltip wind wind-%s']";
    
    public WeatherData getWeatherInMonth(int month) {
        return getMonthInYear(YEAR_2016, month);
    }
    
    private WeatherData getMonthInYear(int year, int month) {
        String yearString = Integer.toString(year);
        String monthString = getStringDate(month);
        
        int days = getDaysInMonth(year, month);
        int temperatures[] = new int[days];
        WeatherData weather = new WeatherData();
        
        for (int day = 1; day < days; day++) {
            Parser sinoptikForDay = new Parser(
                ADDRESS_AND_CITY +
                String.format(FORMAT_PATH, yearString, monthString, getStringDate(day))
            );
    
            addTemperatures(sinoptikForDay, day, temperatures);
            addWinds(sinoptikForDay, weather);
        }
        
        weather.setAvgTemperature(
            average(temperatures));
        
        return weather;
    }
    
    private void addTemperatures(
        Parser sinoptikForDay,
        int day,
        int[] temperatures) {
        int min = getTemperature(sinoptikForDay, MIN);
        int max = getTemperature(sinoptikForDay, MAX);
        temperatures[day] = average(min, max);
    }
    
    private void addWinds(
        Parser sinoptikForDay,
        WeatherData weather) {
    
        
        for (Wind wind : Wind.values()) {
            int times = sinoptikForDay.getAllByXpath(
                String.format(FORMAT_WINDS_XPATH, wind)
            ).size();
            
            weather.addToDirections(wind, times);
        }
    }
    
    private int getTemperature(Parser sinoptikForDay, String minOrMax) {
        TagNode min = sinoptikForDay.getSingleByXpath(
            String.format(FORMAT_TEMPERATURE_XPATH, minOrMax));
        String stringTemperature = min.getAllChildren().get(0).toString().split("&")[0];
        
        int sign;
        switch (stringTemperature.charAt(0)) {
            case '+':
                sign = 1;
                break;
            case '-':
                sign = -1;
                break;
            default:
                return 0;
        }
    
        return Integer.parseInt(
            stringTemperature.substring(1)) * sign;
    }
    
    private int getDaysInMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.lengthOfMonth();
    }
    
    private String getStringDate(int monthOrDay) {
        String monthString = Integer.toString(monthOrDay);
        if (monthString.length() < 2) {
            monthString = "0" + monthString;
        }
        return monthString;
    }
    
    private int average(int... values) {
        int res = 0;
        for (int i = 0; i < values.length; i++) {
            res += values[i];
        }
        return res / values.length;
    }
    
}
