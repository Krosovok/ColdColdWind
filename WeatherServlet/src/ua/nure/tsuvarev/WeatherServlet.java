package ua.nure.tsuvarev;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Slava on 05.04.2017.
 */
@WebServlet(name = "WeatherServlet")
public class WeatherServlet extends HttpServlet {
    
    public static final String MONTH_PARAM = "month";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
        String month = request.getParameter(MONTH_PARAM);
        int monthNum = Integer.parseInt(month);
    
        WeatherDBManager dbManager = WeatherDBManager.getInstance();
        WeatherData weatherInMonth = dbManager.getData(monthNum, SinoptikSite.YEAR_2016);
        
        if (weatherInMonth == null) {
            SinoptikSite sinoptik = new SinoptikSite();
            weatherInMonth = sinoptik.getWeatherInMonth(monthNum);
            dbManager.addData( monthNum, SinoptikSite.YEAR_2016, weatherInMonth);
        }
        
        response.getWriter().write(
            weatherInMonth.toString());
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        doPost(request, response);
    }
}
