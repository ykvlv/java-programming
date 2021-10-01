package server;

import common.Request;
import common.Response;
import common.ResponseType;
import common.forFlat.Flat;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class CommandExecutor {
    private final CommandRegister commandRegister;
    private final FlatHashMap flatHashMap;
    private final Connection connection;

    public CommandExecutor(CommandRegister commandRegister, FlatHashMap flatHashMap, Connection connection) {
        this.flatHashMap = flatHashMap;
        this.commandRegister = commandRegister;
        this.connection = connection;
    }

    public Response execute(Request request) {
        switch (request.getRequestType()) {
            case COMMAND:
                return execute((String) request.getObject());
            case SEND_ITEM:
                return add((Flat) request.getObject(), (Integer) request.getExtra());
            case TOUCH:
                return touch((String) request.getObject());
            case AUTH:
                return authorization((String) request.getObject(), (String) request.getExtra());
            default:
                return new Response(ResponseType.ERROR, "Ало клиент а что тебе надо?");
        }
    }

    private Response add(Flat flat, Integer key) {
        flatHashMap.put(key, flat);
        return new Response(ResponseType.DONE, "Элемент добавлен");
    }

    private Response execute(String args) {
        return commandRegister.decryptAndRun(args);
    }

    private Response authorization(String login, String password) {
        String pepper = "pososi";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (getLogin(login) == null) {
                // генерация хеша через md5 с солью и перцем
                String salt = new Random()
                        .ints(48, 122 + 1)
                        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                        .limit(10)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
                byte[] hashInBytes = md.digest((pepper + password + salt).getBytes(StandardCharsets.UTF_8));
                StringBuilder hash = new StringBuilder();
                for (byte hashInByte : hashInBytes)
                    hash.append(Integer.toString((hashInByte & 0xff) + 0x100, 16).substring(1));

                // добавление пользователя в БД
                Statement statement = connection.createStatement();
                statement.execute("INSERT " +
                        "INTO users (login, salt, hash) " +
                        "VALUES ('" + login + "', '" + salt + "', '" + hash + "')");
                statement.close();
                return new Response(ResponseType.DONE, "Добро пожаловать, " + login + "!");
            } else {
                Statement statement = connection.createStatement();
                statement.executeQuery("SELECT * FROM users where login like '" + login + "'");
                ResultSet result = statement.getResultSet();
                result.next();
                String dataBaseHash = result.getString("hash");
                String salt = statement.getResultSet().getString("salt");

                byte[] hashInBytes = md.digest((pepper + password + salt).getBytes(StandardCharsets.UTF_8));
                StringBuilder hash = new StringBuilder();
                for (byte hashInByte : hashInBytes)
                    hash.append(Integer.toString((hashInByte & 0xff) + 0x100, 16).substring(1));

                result.close();
                statement.close();
                if (hash.toString().equals(dataBaseHash)) {
                    return new Response(ResponseType.DONE, "Добро пожаловать, " + login + "!");
                } else {
                    return new Response(ResponseType.ERROR, "Неверный пароль! Повторите попытку.");
                }
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new Response(ResponseType.ERROR, "Ошибка на стороне сервера.");
        }
    }

    // returns login if found, otherwise return null
    private String getLogin(String login) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeQuery("SELECT login FROM users WHERE login like '" + login + "'");
        ResultSet result = statement.getResultSet();
        String dbLogin = null;
        if (result.next()) {
            dbLogin = result.getString("login");
        }
        return dbLogin;
    }

    private Response touch(String login) {
        try {
            return new Response(ResponseType.CONNECT, getLogin(login));
        } catch (SQLException e) {
            return new Response(ResponseType.ERROR, "Ошибка на стороне сервера.");
        }
    }
}
