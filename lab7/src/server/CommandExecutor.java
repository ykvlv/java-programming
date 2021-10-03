package server;

import common.Request;
import common.Response;
import common.ResponseType;
import common.forFlat.Flat;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Random;

public class CommandExecutor {
    private final String pepper = "pososi";
    private final CommandRegister commandRegister;
    private final FlatHashMap flatHashMap;
    private final Connection connection;

    public CommandExecutor(CommandRegister commandRegister, FlatHashMap flatHashMap, Connection connection) {
        this.flatHashMap = flatHashMap;
        this.commandRegister = commandRegister;
        this.connection = connection;
    }

    public Response execute(Request request) {
        if (request == null) {
            return new Response(ResponseType.ERROR, "Неверный запрос");
        }
        try {
            switch (request.getRequestType()) {
                case COMMAND: // read lock
                    if (passwordInvalid(request.getLogin(), request.getPassword())) {
                        return new Response(ResponseType.ERROR, "Ваш пароль недействителен");
                    }
                    return executeCommand((String) request.getObject(), request.getLogin());
                case SEND_ITEM: // write lock
                    if (passwordInvalid(request.getLogin(), request.getPassword())) {
                        return new Response(ResponseType.ERROR, "Ваш пароль недействителен");
                    }
                    return add((Flat) request.getObject(), (Integer) request.getExtra(), request.getLogin());
                case TOUCH:
                    return touch(request.getLogin());
                case AUTH:
                    return authorization(request.getLogin(), request.getPassword());
                default:
                    return new Response(ResponseType.ERROR, "Ало клиент а что тебе надо?");
            }
        } catch (SQLException | NoSuchAlgorithmException throwables) {
            return new Response(ResponseType.ERROR, "Не могу проверить действительность пароля");
        }
    }

    private boolean passwordInvalid(String login, String password) throws SQLException, NoSuchAlgorithmException {
        if (login == null || password == null) {
            return true;
        }
        return !passwordMatches(login, password);
    }

    private Response executeCommand(String request, String login) {
        return commandRegister.decryptAndRun(request, login);
    }

    private Response add(Flat flat, Integer key, String login) {
        if (flatHashMap.put(key, flat, login)) {
            return new Response(ResponseType.DONE, "Элемент добавлен");
        } else {
            return new Response(ResponseType.ERROR, "Элемент не добавлен. Вот дела...");
        }
    }


    private Response authorization(String login, String password) {
        try {
            if (getLogin(login) == null) {
                // РЕГИСТРАЦИЯ. генерация хеша через md5 с солью и перцем
                MessageDigest md = MessageDigest.getInstance("SHA-256");
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
                return new Response(ResponseType.DONE, "Удачная регистрация, " + login + "!");
            } else {
                if (passwordMatches(login, password)) {
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

    private boolean passwordMatches(String login, String password) throws NoSuchAlgorithmException, SQLException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        Statement statement;
        statement = connection.createStatement();
        statement.executeQuery("SELECT * FROM users where login = '" + login + "'");
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
        return hash.toString().equals(dataBaseHash);
    }

    // returns login if found, otherwise return null
    private String getLogin(String login) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeQuery("SELECT login FROM users WHERE login = '" + login + "'");
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
