package ua.nure.tsuvarev;

/**
 * Created by Slava on 05.04.2017.
 */
public enum Wind {
    N, NW, W, SW, S, SE, E, NE;
    
    public int getPosInRose() {
        switch (this) {
            case N:
                return 0;
            default:
                return getPos();
        }
    }
    
    private int getPos() {
        Wind[] winds = Wind.values();
        for (int i = 1; i < winds.length; i++) {
            if (winds[i].equals(this)) {
                return winds.length - i;
            }
        }
        return 0;
    }
}
