package client;

import common.forFlat.*;

import java.io.InterruptedIOException;
import java.time.LocalDateTime;
import java.util.*;

public class FlatCreator {
    private final InputHandler inputHandler;

    public FlatCreator(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public Flat createStandardFlat() throws InterruptedIOException {
        String name = requestName();
        Coordinates coordinates = requestCoordinates();
        Float area = requestArea();
        Integer numberOfRooms = requestNumberOfRooms();
        Furnish furnish = requestFurnish();
        View view = requestView();
        Transport transport = requestTransport();
        House house = requestHouse();
        LocalDateTime creationDate = LocalDateTime.now();
        return new Flat(name, coordinates, creationDate, area, numberOfRooms, furnish, view, transport, house);
    }

    private void userInvitation(String string) {
        if (!inputHandler.isScriptMode()) {
            System.out.println(string);
        }
    }

    private String requestName() throws InterruptedIOException {
        userInvitation("Имя владельца квартиры:");
        return inputHandler.requestString(false);
    }

    private Coordinates requestCoordinates() throws InterruptedIOException {
        long x;
        do {
            userInvitation("Координата квартиры X, (0 < X < 39):");
            x = inputHandler.requestLong(false);
        } while (x >= Coordinates.MAX_X || x <= Coordinates.MIN_X);

        float y;
        do {
            userInvitation("Координата квартиры Y, (Y > 0):");
            y = inputHandler.requestFloat(false);
        } while (y <= Coordinates.MIN_Y);
        return new Coordinates(x, y);
    }

    private Float requestArea() throws InterruptedIOException {
        float area;
        do {
            userInvitation("Площадь квартиры, (0 < S <= 668):");
            area = inputHandler.requestFloat(false);
        } while (area <= Flat.MIN_AREA || area > Flat.MAX_AREA);
        return area;
    }

    private Integer requestNumberOfRooms() throws InterruptedIOException {
        Integer numberOfRooms;
        do {
            userInvitation("Количество комнат:");
            numberOfRooms = inputHandler.requestInteger(true);
            if (numberOfRooms == null) {
                break;
            }
        } while (numberOfRooms <= Flat.MIN_NUMBER_OF_ROOMS);
        return numberOfRooms;
    }

    private Furnish requestFurnish() throws InterruptedIOException {
        userInvitation("Ну как там с отделкой? (можно пропустить) Варианты:");
        userInvitation(Arrays.toString(Furnish.values()));

        String string = inputHandler.chooseFromEnumValues(Furnish.values(), true);
        return string == null ? null : Furnish.valueOf(string);
    }

    private View requestView() throws InterruptedIOException {
        userInvitation("Вид из окон? (можно пропустить) Варианты:");
        userInvitation(Arrays.toString(View.values()));

        String string = inputHandler.chooseFromEnumValues(View.values(), true);
        return string == null ? null : View.valueOf(string);
    }

    private Transport requestTransport() throws InterruptedIOException {
        userInvitation("Какой вид транспорта рядом? (можно пропустить) Варианты:");
        userInvitation(Arrays.toString(Transport.values()));

        String string = inputHandler.chooseFromEnumValues(Transport.values(), true);
        return string == null ? null : Transport.valueOf(string);
    }

    private House requestHouse() throws InterruptedIOException {
        userInvitation("Название дома (можно пропустить):");
        String houseName = inputHandler.requestString(true);

        Long houseYear;
        do {
            userInvitation("Год постройки > 0 (можно пропустить):");
            houseYear = inputHandler.requestLong(true);
            if (houseYear == null) {
                break;
            }
        } while (houseYear <= House.MIN_YEAR);

        long numberOfLifts;
        do {
            userInvitation("Количество лифтов:");
            numberOfLifts = inputHandler.requestLong(false);
        } while (numberOfLifts < House.MIN_NUMBER_OF_LIFTS);

        return new House(houseName, houseYear, numberOfLifts);
    }
}
