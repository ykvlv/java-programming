package common.forFlat;

import java.io.Serializable;

public class House implements Serializable {
    private final String name;
    public static final Long MIN_YEAR = 0L;
    private final Long year;
    public static final long MIN_NUMBER_OF_LIFTS = 0L;
    private final long elevators;

    public House(String name, Long year, Long elevators) {
        this.name = name;
        this.year = year;
        this.elevators = elevators;
    }

    @Override
    public String toString() {
        return ((name == null) ? "" : "Название: " + name) +
                ((year == null) ? "" : (name == null ? "" : ", ") + "Год: " + year) +
                (((name == null && year == null) ? "" : ", ") + "Количество лифтов: " + elevators);
    }

    public String getName() {
        return name;
    }

    public Long getYear() {
        return year;
    }

    public long getElevators() {
        return elevators;
    }
}
