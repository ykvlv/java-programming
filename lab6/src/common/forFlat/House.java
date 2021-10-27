package common.forFlat;

import java.io.Serializable;

public class House implements Serializable {
    private final String name; //Поле может быть null
    public static Long MIN_YEAR = 0L;
    private final Long year; //Поле может быть null
    public static long MIN_NUMBER_OF_LIFTS = 0;
    private final long numberOfLifts;

    public House(String name, Long year, long numberOfLifts) {
        this.name = name;
        this.year = year;
        this.numberOfLifts = numberOfLifts;
    }

    @Override
    public String toString() {
        return ((name == null) ? "" : "Название: " + name) +
                ((year == null) ? "" : (name == null ? "" : ", ") + "Год: " + year) +
                ((name == null && year == null) ? "" : ", ") + "Количество лифтов: " + numberOfLifts;
    }
}