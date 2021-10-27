package client;

import common.forFlat.*;

import java.time.LocalDateTime;
import java.util.*;

public class FlatCreator {
    private final InputHelper inputHelper;

    public FlatCreator(InputHelper inputHelper) {
        this.inputHelper = inputHelper;
    }

    public Flat createStandardFlat(String s) {
        int id = generateId();
        String name = requestName();
        Coordinates coordinates = requestCoordinates();
        Float area = requestArea();
        Integer numberOfRooms = requestNumberOfRooms();
        Furnish furnish = requestFurnish();
        View view = requestView();
        Transport transport = requestTransport();
        House house = requestHouse();
        LocalDateTime creationDate = LocalDateTime.now();
        return new Flat(id, name, coordinates, creationDate, area, numberOfRooms, furnish, view, transport, house, Integer.parseInt(s));
    }

    public void userInvitation(String string) {
        if (!inputHelper.isScriptMode()) {
            System.out.println(string);
        }
    }

    private int generateId() {
        return new Random().nextInt(Integer.MAX_VALUE);
    }

    private String requestName() {
        userInvitation("Имя владельца квартиры:");
        return inputHelper.requestString(false);
    }

    private Coordinates requestCoordinates() {
        long x;
        do {
            userInvitation("Координата квартиры X, (0 < X < 39):");
            x = inputHelper.requestLong(false);
        } while (x >= 39 || x <= 0);

        float y;
        do {
            userInvitation("Координата квартиры Y, (Y > 0):");
            y = inputHelper.requestFloat();
        } while (y <= 0);
        return new Coordinates(x, y);
    }

    private Float requestArea() {
        float area;
        do {
            userInvitation("Площадь квартиры, (0 < S <= 668):");
            area = inputHelper.requestFloat();
        } while (area <= Flat.MIN_AREA || area > Flat.MAX_AREA);
        return area;
    }

    private Integer requestNumberOfRooms() {
        int numberOfRooms;
        do {
            userInvitation("Количество комнат:");
            numberOfRooms = inputHelper.requestInt();
        } while (numberOfRooms <= Flat.MIN_NUMBER_OF_ROOMS);
        return numberOfRooms;
    }

    private Furnish requestFurnish() {
        userInvitation("Ну как там с отделкой? (можно пропустить) Варианты:");
        userInvitation(Arrays.toString(Furnish.class.getEnumConstants()));
        ArrayList<String> array = new ArrayList<>();
        for (Furnish i : Furnish.class.getEnumConstants()) {
            array.add(i.toString());
        }

        String string = inputHelper.chooseFromArray(array, true);
        return string == null ? null : Furnish.valueOf(string);
    }

    private View requestView() {
        userInvitation("Вид из окон? (можно пропустить) Варианты:");
        userInvitation(Arrays.toString(View.class.getEnumConstants()));
        View view;
        ArrayList<String> array = new ArrayList<>();
        for (View i : View.class.getEnumConstants()) {
            array.add(i.toString());
        }
        String string = inputHelper.chooseFromArray(array, true);
        return string == null ? null : View.valueOf(string);
    }

    private Transport requestTransport() {
        userInvitation("Какой вид транспорта рядом? (можно пропустить) Варианты:");
        userInvitation(Arrays.toString(Transport.class.getEnumConstants()));
        Transport transport;
        ArrayList<String> array = new ArrayList<>();
        for (Transport i : Transport.class.getEnumConstants()) {
            array.add(i.toString());
        }
        String string = inputHelper.chooseFromArray(array, true);
        return string == null ? null : Transport.valueOf(string);
    }

    private House requestHouse() {
        userInvitation("Информация о доме:");
        userInvitation("Название дома (можно пропустить):");
        String houseName = inputHelper.requestString(true);

        Long houseYear;
        do {
            userInvitation("Год постройки > 0 (можно пропустить):");
            houseYear = inputHelper.requestLong(true);
            if (houseYear == null) {
                break;
            }
        } while (houseYear <= House.MIN_YEAR);

        long numberOfLifts;
        do {
            userInvitation("Количество лифтов:");
            numberOfLifts = inputHelper.requestLong(false);
        } while (numberOfLifts < House.MIN_NUMBER_OF_LIFTS);

        return new House(houseName, houseYear, numberOfLifts);
    }
}
