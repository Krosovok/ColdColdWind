package ua.nure.tsuvarev;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ua.nure.tsuvarev.Constants.*;

/**
 * Created by void on 05.04.2017.
 */

public class WeatherApplet extends Applet {
    
    private Choice monthChoice;
    private Button showButton;

    private Label temperatureLabel;
    private WindRose windRose;
    
    private WeatherLoader weatherLoader = new WeatherLoader(SERVLET_ADDRESS);
    
    @Override
    public void init() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        monthChoice = new Choice();
        showButton = new Button(BUTTON_LABEL);
        temperatureLabel = new Label(TEMPERATURE_LABEL_TEXT, Label.CENTER);
        windRose = new WindRose();

        for (int i = 0; i < MONTHS.length; i++) {
            monthChoice.add(MONTHS[i]);
        }

        showButton.addActionListener(new ButtonListener());

        this.add(monthChoice);
        this.add(showButton);
        this.add(temperatureLabel);
        this.add(windRose);
    }

    @Override
    public void paint(Graphics g) {
        monthChoice.setBounds(LEFT_CONTROL_X, UPPER_CONTROL_Y, LEFT_CONTROL_WIDTH, UPPER_CONTROL_HEIGHT);
        showButton.setBounds(RIGHT_CONTROL_X, UPPER_CONTROL_Y, RIGHT_CONTROL_WIDTH, UPPER_CONTROL_HEIGHT);
        temperatureLabel.setBounds(RIGHT_CONTROL_X, DOWN_CONTROL_Y, RIGHT_CONTROL_WIDTH, DOWN_CONTROL_HEIGHT);
        windRose.setBounds(LEFT_CONTROL_X, DOWN_CONTROL_Y, LEFT_CONTROL_WIDTH, DOWN_CONTROL_HEIGHT);
    }

    private void getDataFromServlet() {

        String month = getMonthNum(monthChoice.getSelectedItem());
        WeatherData data = weatherLoader.getForMonth(month);
    
        changeWindRose(data);
        changeTemperature(data);
    }
    
    private void changeTemperature(WeatherData data) {
        int temperature = data.getAvgTemperature();
        temperatureLabel.setText(TEMPERATURE_LABEL_TEXT + temperature + "*C.");
    }
    
    private void changeWindRose(WeatherData data) {
        int[] winds = new int[8];
        for (Wind wind : Wind.values()) {
            winds[wind.getPosInRose()] = data.getTimesOfDirection(wind);
        }
        windRose.setWindRoseValues(winds);
        windRose.repaint();
    }
    
    private String getMonthNum(String month) {
        for (int i = 0; i < Constants.MONTHS.length; i++) {
            if (Constants.MONTHS[i].equals(month))
            {
                return Integer.toString(i + 1);
            }
        }
        throw new UnexpectedMonthException();
    }
    
    
    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            getDataFromServlet();
        }
    }

}
