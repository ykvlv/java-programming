package common.forFlat;

import java.io.Serializable;

public class Coordinates implements Serializable {
    public static final long MAX_X = 38;
    public static final long MIN_X = 0;
    private final long x;
    public static final long MIN_Y = 0;
    private final float y;

    public Coordinates(long x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "X:" + x +
                ", Y:" + y;
    }
}