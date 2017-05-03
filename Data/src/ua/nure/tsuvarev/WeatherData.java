package ua.nure.tsuvarev;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Slava on 05.04.2017.
 */
public class WeatherData {
    
    private static final String AVG_TEMP = "avgTemp";
    private static final String WINDS = "winds";
    private int avgTemperature;
    private Map<Wind, Times> windDirections = new HashMap<>();
    
    {
        for (Wind wind : Wind.values()) {
            windDirections.put(wind, new Times());
        }
    }
    
    public int getAvgTemperature() {
        return avgTemperature;
    }
    
    public void setAvgTemperature(int avgTemperature) {
        this.avgTemperature = avgTemperature;
    }
    
    public int getTimesOfDirection(Wind direction) {
        return windDirections.get(direction).getValue();
    }
    
    public void addToDirections(Wind direction, int times) {
        windDirections.get(direction).add(times);
    }
    
    @Override
    public String toString() {
        JSONObject json = new JSONObject();
    
        try {
            json.put(AVG_TEMP, avgTemperature);
            json.put(WINDS, new JSONObject(this.windDirections));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    
        return json.toString();
    }
    
    public static WeatherData fromString(String json) {
        try {
            JSONObject newData = new JSONObject(json);
            
            WeatherData newWeather = new WeatherData();
            newWeather.avgTemperature = (Integer) newData.get(AVG_TEMP);
    
            JSONObject winds = newData.getJSONObject(WINDS);
            for (Wind wind : Wind.values()) {
                int times = winds.getInt(wind.toString());
                newWeather.addToDirections(wind, times);
            }
    
            return newWeather;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
