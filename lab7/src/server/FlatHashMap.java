package server;

import common.forFlat.*;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

public class FlatHashMap {
    private final HashMap<Integer, Flat> flats = new HashMap<>();
    private final HashMap<Integer, String> owners = new HashMap<>();
    private final LocalDateTime initTime;
    private final Connection connection;

    public FlatHashMap(LocalDateTime initTime, Connection connection) throws SQLException {
        this.connection = connection;
        this.initTime = initTime;
        Statement statement = connection.createStatement();
        ResultSet result =  statement.executeQuery("SELECT * FROM flats");

        while (result.next()) {
            flats.put(result.getInt("key"), createFlatFromResult(result));
            owners.put(result.getInt("key"), result.getString("login"));
        }
        result.close();
        statement.close();
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
        Integer rooms = result.getInt("rooms") == 0 ? null : result.getInt("rooms");
        Furnish furnish = result.getString("furnish") == null ? null : Furnish.valueOf(result.getString("furnish"));
        View view = result.getString("view") == null ? null : View.valueOf(result.getString("view"));
        Transport transport = result.getString("transport") == null ? null : Transport.valueOf(result.getString("transport"));
        House house = new House(
                result.getString("houseName"),
                result.getLong("houseYear"),
                result.getLong("houseElevators") == 0 ? null : result.getLong("houseElevators"));
        return new Flat(id, name, coordinates, creationDate, area, rooms, furnish, view, transport, house);
    }

    public void clear(String login) {
        for (Map.Entry<Integer, String> entry : owners.entrySet()) {
            if (entry.getValue().equals(login)) {
                remove(entry.getKey(), login);
            }
        }
        flats.entrySet().removeIf(e -> owners.get(e.getKey()).equals(login));
        owners.entrySet().removeIf(e -> e.getValue().equals(login));
    }

    public HashMap<Integer, Flat> getFlats() {
        return flats;
    }

    public LocalDateTime getInitTime() {
        return initTime;
    }

    public boolean remove(int key, String login) {
        if (owners.get(key).equals(login)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DELETE FROM flats WHERE key = '" + key + "'");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            flats.remove(key);
            owners.remove(key);
            return true;
        } else {
            return false;
        }
    }

    public boolean containsKey(int key) {
        return flats.containsKey(key);
    }

    public boolean put(int key, Flat flat, String login) {
        if (containsKey(key)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DELETE FROM flats WHERE key = '" + key + "'");
            } catch (SQLException e) {
                return false;
            }
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT " +
                "INTO flats (key, name, \"coordinateX\", \"coordinateY\", \"creationDate\", \"creationTime\", " +
                "area, rooms, furnish, view, transport, \"houseName\", \"houseYear\", \"houseElevators\", login) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");) {
            preparedStatement.setInt(1,  key);
            preparedStatement.setString(2, flat.getName());
            preparedStatement.setLong(3, flat.getCoordinates().getX());
            preparedStatement.setFloat(4, flat.getCoordinates().getY());
            preparedStatement.setDate(5, Date.valueOf(flat.getCreationDateTime().toLocalDate()));
            preparedStatement.setTime(6, Time.valueOf(flat.getCreationDateTime().toLocalTime()));
            preparedStatement.setFloat(7, flat.getArea());
            if (flat.getRooms() == null) {
                System.out.println("румы нулл");
                preparedStatement.setNull(8, Types.INTEGER);
            } else {
                System.out.printf("румы есть %d%n", flat.getRooms());
                preparedStatement.setInt(8, flat.getRooms());
            }
            if (flat.getFurnish() == null) {
                preparedStatement.setNull(9, Types.VARCHAR);
            } else {
                preparedStatement.setString(9, String.valueOf(flat.getFurnish()));
            }
            if (flat.getView() == null) {
                preparedStatement.setNull(10, Types.VARCHAR);
            } else {
                preparedStatement.setString(10, String.valueOf(flat.getView()));
            }
            if (flat.getTransport() == null) {
                preparedStatement.setNull(11, Types.VARCHAR);
            } else {
                preparedStatement.setString(11, String.valueOf(flat.getTransport()));
            }
            preparedStatement.setString(12, flat.getHouse().getName());
            if (flat.getHouse().getYear() == null) {
                System.out.println("год нулл");
                preparedStatement.setNull(13, Types.REAL);
            } else {
                System.out.printf("год есть %d%n", flat.getHouse().getYear());
                preparedStatement.setLong(13, flat.getHouse().getYear());
            }
            preparedStatement.setLong(14, flat.getHouse().getElevators());
            preparedStatement.setString(15, login);

            preparedStatement.execute();
        } catch (SQLException e) {
            return false;
        }
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT id FROM flats WHERE key = '" + key + "'");
            if (resultSet.next()) {
                flat.setId(resultSet.getInt("id"));
            }
            resultSet.close();
        } catch (SQLException e) {
            return false;
        }
        flats.put(key, flat);
        owners.put(key, login);
        return true;
    }

    public String getOwner(Integer key) {
        return owners.get(key);
    }

    public Flat get(int key) {
        return flats.get(key);
    }

}
