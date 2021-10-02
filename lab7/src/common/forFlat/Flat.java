package common.forFlat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Flat implements Comparable<Flat>, Serializable {
    private int id ; //Поле генерируется автоматически
    private final String name; //Поле НЕ может быть null
    private final Coordinates coordinates; //Поле НЕ может быть null
    private final LocalDateTime creationDateTime; //Поле НЕ может быть null
    public static final Float MIN_AREA = 0F;
    public static final Float MAX_AREA = 668F;
    private final Float area; //Поле НЕ может быть null
    public static final Integer MIN_NUMBER_OF_ROOMS = 0;
    private final Integer rooms;
    private final Furnish furnish;
    private final View view;
    private final Transport transport;
    private final House house; //Поле НЕ может быть null

    public Flat(String name, Coordinates coordinates, LocalDateTime creationDateTime, Float area, Integer rooms, Furnish furnish, View view, Transport transport, House house) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDateTime = creationDateTime;
        this.area = area;
        this.rooms = rooms;
        this.furnish = furnish;
        this.view = view;
        this.transport = transport;
        this.house = house;
    }

    public Flat(int id, String name, Coordinates coordinates, LocalDateTime creationDateTime, Float area, Integer rooms, Furnish furnish, View view, Transport transport, House house) {
        this(name, coordinates, creationDateTime, area, rooms, furnish, view, transport, house);
        this.id = id;
    }

    @Override
    public String toString() {
        return "\tID: " + id +
                "\n\tДата добавления в список: " + creationDateTime.format(DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm:ss")) +
                "\n\tВладелец: " + name +
                "\n\tКоординаты: " + coordinates.toString() +
                "\n\tПлощадь: " + area +
                ((rooms == null) ? "" : "\n\tКоличество комнат: " + rooms) +
                ((furnish == null) ? "" : "\n\tОтделка: " + furnish) +
                ((view == null) ? "" : "\n\tВид из окна: " + view) +
                ((transport == null) ? "" : "\n\tТранспорт: " + transport) +
                "\n\tИнформация о доме: " + house.toString();
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public int compareTo(Flat o) {
        return (int) (this.area - o.getArea());
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Furnish getFurnish() {
        return furnish;
    }

    public House getHouse() {
        return house;
    }

    public Integer getRooms() {
        return rooms;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public Transport getTransport() {
        return transport;
    }

    public View getView() {
        return view;
    }
}