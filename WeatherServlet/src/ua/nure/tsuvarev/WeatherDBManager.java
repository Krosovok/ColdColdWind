package ua.nure.tsuvarev;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Slava on 05.04.2017.
 */
public class WeatherDBManager {
    
    public static final String USER = "root";
    public static final String PASS = "TakeMageAndDontWorry";
    public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/weather?user=%s&password=%s";
    
    private static WeatherDBManager singleton;
    
    private Connection conn;
    private Map<Date, WeatherData> weatherData = new HashMap<>();
    
    private WeatherDBManager() {
        String user = USER;
        String password = PASS;
        String connectionString = String.format(CONNECTION_STRING,user, password);
    
        try {
            java.sql.Driver driver =
                (java.sql.Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
            DriverManager.registerDriver(driver);
            conn = DriverManager.getConnection(connectionString);
            
            initData();
        } catch (InstantiationException | IllegalAccessException | SQLException | ClassNotFoundException e) {
            throw new DbConnectionException(DbConnectionException.ERROR + e.getMessage(), e);
        }
    }
    
    public static WeatherDBManager getInstance() {
        if (singleton == null) {
            return singleton = new WeatherDBManager();
        } else {
            return singleton;
        }
    }
    
    public WeatherData getData(int month, int year) {
        if (weatherData != null) {
            return weatherData.get(new Date(month, year));
        }
        else {
            return null;
        }
    }
    
    public void addData(int month, int year, WeatherData weather) {
        if (conn == null) {
            throw new DbConnectionException("Not connected to a DB.");
        }
        
        PreparedStatement s;
        try {
            s = conn.prepareStatement(Queries.INSERT_INTO_WEATHER);
        } catch (SQLException e) {
            throw new DataAccessException("Can not create statement.  ===> " + e.getMessage(), e);
        }
        
        try {
            int i = 1;
            
            s.setInt(i++, month);
            s.setInt(i++, year);
            s.setInt(i++, weather.getAvgTemperature());
            
            for (Wind wind : Wind.values()) {
                s.setInt(i++,
                    weather.getTimesOfDirection(wind));
            }
            
            boolean b = s.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Error in executing query."
                + "\nCause: " + e.getMessage(), e);
        }
        finally {
            try {
                s.close();
            } catch (SQLException e) {
                throw new DataAccessException("Can not close.  ===> " + e.getMessage(), e);
            }
        }
    }
    
    private void initData() {
    
        if (conn == null) {
            throw new DbConnectionException("Not connected to a DB.");
        }
        
        Statement s;
        try {
            s = conn.createStatement();
        } catch (SQLException e) {
            throw new DataAccessException("Can not create a statement.  ===> " + e.getMessage(), e);
        }
    
    
        ResultSet res = null;
        try {
            res = s.executeQuery(Queries.SELECT_ALL);
            while (res.next()) {
                int month = res.getInt(Queries.MONTH);
                int year = res.getInt(Queries.YEAR);
    
                WeatherData weather = new WeatherData();
                weather.setAvgTemperature(
                    res.getInt(Queries.AVG_TEMPERATURE)
                );
                for (Wind wind : Wind.values()) {
                    int times = res.getInt(wind.toString());
                    weather.addToDirections(wind,
                        times);
                }
    
                weatherData.put(new Date(month, year), weather);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error in executing query."
                + "\nCause: " + e.getMessage(), e);
        } finally {
            try {
                s.close();
            
                if (res != null) {
                    res.close();
                }
            
            } catch (SQLException e) {
                throw new DataAccessException("Can not close.  ===> " + e.getMessage(), e);
            }
        }
    }
    
    private class Queries {
        static final String SELECT_ALL = "SELECT * FROM weather.weather";
        static final String INSERT_INTO_WEATHER =
            "INSERT INTO weather.weather VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        static final String MONTH = "month";
        static final String YEAR = "year";
        static final String AVG_TEMPERATURE = "avgTemperature";
    }
}
