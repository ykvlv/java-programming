package common.forFlat;


import java.io.Serializable;

public class FlatAndKey implements Serializable {
    private final Flat flat;
    private final Integer key;

    public FlatAndKey(Flat flat, Integer key) {
        this.flat = flat;
        this.key = key;
    }

    public Flat getFlat() {
        return flat;
    }

    public Integer getKey() {
        return key;
    }
}
