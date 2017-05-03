package ua.nure.tsuvarev;

import java.awt.*;

/**
 * Created by void on 06.04.2017.
 */
public final class GeneralFunctions {

    public static int getMaxValue(int[] windRoseValues) {
        int max = windRoseValues[0];
        for (int i = 1; i < windRoseValues.length; i++) {
            if(windRoseValues[i] > max) {
                max = windRoseValues[i];
            }
        }
        return max;
    }

    /**
     * Calculates direction of a point M on the line segment, when we have lengths of AM and BM line segments.
     *
     * @param a beginning point of the line segment - A
     * @param b ending point of the line segment - B
     * @param am length of AM line segment
     * @param bm length of BM line segment
     * @return coordinates of M point
     */
    public static Point calculatePoint(Point a, Point b, double am, double bm) {
        double lambda = am / bm;
        double xm = (a.getX() + lambda * b.getX()) / (1 + lambda);
        double ym = (a.getY() + lambda * b.getY()) / (1 + lambda);

        return new Point((int)xm, (int)ym);
    }

    public static Point calculateDiagonalPoint(Point a, Point b, double length) {
        double gipotenuse = Math.sqrt(Math.pow(length, 2) + Math.pow(length, 2));

        return calculatePoint(a, b, length, gipotenuse - length);
    }

    private GeneralFunctions() {}
}
