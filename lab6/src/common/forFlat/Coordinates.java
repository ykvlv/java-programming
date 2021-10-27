package common.forFlat;

import java.io.Serializable;

public class Coordinates implements Serializable {
    public static long MAX_X = 38;
    private final long x;
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