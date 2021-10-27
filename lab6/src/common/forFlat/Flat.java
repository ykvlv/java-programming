package common.forFlat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Flat implements Comparable<Flat>, Serializable {
    private final int id;
    private final int key;
    private final String name; //Поле НЕ может быть null
    private final Coordinates coordinates; //Поле НЕ может быть null
    private final LocalDateTime creationDate; //Поле НЕ может быть null
    public static Float MIN_AREA = 0F;
    public static Float MAX_AREA = 668F;
    private final Float area;
    public static Integer MIN_NUMBER_OF_ROOMS = 0;
    private final Integer numberOfRooms;
    private final Furnish furnish; //Поле может быть null
    private final View view; //Поле может быть null
    private final Transport transport; //Поле может быть null
    private final House house; //Поле не может быть null

    public Flat(int id, String name, Coordinates coordinates, LocalDateTime creationDate, Float area, Integer numberOfRooms, Furnish furnish, View view, Transport transport, House house, int key) {
        this.key = key;
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.furnish = furnish;
        this.view = view;
        this.transport = transport;
        this.house = house;
    }

    @Override
    public String toString() {
        return "\tID: " + id +
                "\n\tДата добавления в список: " + creationDate.format(DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm:ss")) +
                "\n\tВладелец: " + name +
                "\n\tКоординаты: " + coordinates.toString() +
                "\n\tПлощадь: " + area +
                "\n\tКоличество комнат: " + numberOfRooms +
                ((furnish == null) ? "" : "\n\tОтделка: " + furnish) +
                ((view == null) ? "" : "\n\tВид из окна: " + view) +
                ((transport == null) ? "" : "\n\tТранспорт: " + transport) +
                "\n\tИнформация о доме: " + house.toString();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getArea() {
        return area;
    }

    public int getKey() {
        return key;
    }

    @Override
    public int compareTo(Flat o) {
        return (int) (this.area - o.getArea());
    }
}