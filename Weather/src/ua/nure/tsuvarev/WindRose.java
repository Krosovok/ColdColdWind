package ua.nure.tsuvarev;

import java.awt.*;

import static ua.nure.tsuvarev.Constants.CARDINAL_DIRECTIONS_COUNT;
import static ua.nure.tsuvarev.GeneralFunctions.*;

/**
 * Created by void on 05.04.2017.
 */

public class WindRose extends Panel {

    private boolean isValues;

    private Point center;
    // углы розы ветров, расположены по часовой стелке, начиная с севера, т е north -> northeast -> east и т д
    private Point[] cornerPoints;
    // точки-значения на розе ветров; соответствуют каждой из cornerPoints
    private Point[] windRosePoints;

    public WindRose() {
        isValues = false;

        cornerPoints = new Point[CARDINAL_DIRECTIONS_COUNT];
        windRosePoints = new Point[CARDINAL_DIRECTIONS_COUNT];

        center = new Point(getSize().width / 2, getSize().height / 2);
    }

    public void paint(Graphics g) {
        g.drawLine(cornerPoints[0].x, cornerPoints[0].y, cornerPoints[4].x, cornerPoints[4].y); // horizontal line
        g.drawLine(cornerPoints[6].x, cornerPoints[6].y, cornerPoints[2].x, cornerPoints[2].y); // vertical line
        g.drawLine(cornerPoints[7].x, cornerPoints[7].y, cornerPoints[3].x, cornerPoints[3].y); // from upper left corner to down right corner
        g.drawLine(cornerPoints[1].x, cornerPoints[1].y, cornerPoints[5].x, cornerPoints[5].y); // from upper right corner to down left corner

        if(isValues) {
            g.setColor(Color.RED);
            int l = windRosePoints.length;
            for (int i = 0; i < l; i++) {
                g.drawLine(windRosePoints[i % l].x, windRosePoints[i % l].y, windRosePoints[(i+1) % l].x, windRosePoints[(i+1) % l].y);
            }

        }
    }

    public void setWindRoseValues(int[] windRoseValues) {
        
        isValues = true;

        int max = getMaxValue(windRoseValues);

        int unitInterval = (getSize().width / 2) / max;

        for (int i = 0; i < CARDINAL_DIRECTIONS_COUNT; i++) {
            windRosePoints[i] = calculatePoint(center, cornerPoints[i], (windRoseValues[i] * unitInterval), getSize().width / 2);
        }
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);

        center = new Point(getSize().width / 2, getSize().height / 2);

        cornerPoints[0] = new Point(getSize().width / 2, 0); // north
        cornerPoints[1] = calculateDiagonalPoint(center, new Point(getSize().width, 0), getSize().width / 2);// northeast
        cornerPoints[2] = new Point(getSize().width, getSize().height / 2); // east
        cornerPoints[3] = calculateDiagonalPoint(center, new Point(getSize().width, getSize().height), getSize().width / 2);// southeast

        cornerPoints[4] = new Point(getSize().width / 2, getSize().height); // south
        cornerPoints[5] = calculateDiagonalPoint(center, new Point(0, getSize().height), getSize().width / 2);// southwest
        cornerPoints[6] = new Point(0, getSize().height / 2); // west
        cornerPoints[7] = calculateDiagonalPoint(center, new Point(0, 0), getSize().width / 2);//northwest
    }



}
