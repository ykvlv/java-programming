package server;

import common.forFlat.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class FlatHashMap {
    private final HashMap<Integer, Flat> flats = new HashMap<>();
    private final HashMap<Integer, String> owners = new HashMap<>();
    private final LocalDateTime initTime;

    public FlatHashMap(LocalDateTime initTime, Connection connection) throws SQLException {
        this.initTime = initTime;
        Statement statement = connection.createStatement();
        ResultSet result =  statement.executeQuery("SELECT * FROM flats");

        while (result.next()) {
            this.put(result.getInt("key"), createFlatFromResult(result));
        }
    }

    private Flat createFlatFromResult(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        String name = result.getString("name");
        Coordinates coordinates = new Coordinates(
                result.getLong("coordinateX"),
                result.getFloat("coordinateY"));
        LocalDateTime creationDate = LocalDateTime.of(
                result.getDate("creationDate").toLocalDate(),
                result.getTime("creationTime").toLocalTime());
        Float area = result.getFloat("area");
        Integer rooms = result.getInt("rooms");
        Furnish furnish = Furnish.valueOf(result.getString("furnish"));
        View view = View.valueOf(result.getString("view"));
        Transport transport = Transport.valueOf(result.getString("transport"));
        House house = new House(
                result.getString("houseName"),
                result.getLong("houseYear"),
                result.getLong("houseElevators"));

        return new Flat(id, name, coordinates, creationDate, area, rooms, furnish, view, transport, house);
    }

    public void clear() {
        flats.clear();
    }

    public HashMap<Integer, Flat> getFlats() {
        return flats;
    }

    public LocalDateTime getInitTime() {
        return initTime;
    }

    public void remove(int key) {
        flats.remove(key);
    }

    public boolean containsKey(int key) {
        return flats.containsKey(key);
    }

    public void put(int key, Flat flat) {
        flats.put(key, flat);
    }

    public Flat get(int key) {
        return flats.get(key);
    }

    public HashMap<Integer, String> getOwners() {
        return owners;
    }
    public void putOwners() {

    }
}
