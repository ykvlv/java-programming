package server;

import common.Request;
import common.Response;
import common.ResponseType;
import common.forFlat.Flat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
                return isRegistered((String) request.getObject());
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

    public void saveAll() {

    }

    private Response authorization(String login, String password) {
        return new Response(ResponseType.DONE, "fr");
    }

    private Response isRegistered(String login) {
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery("SELECT login FROM users WHERE login like " + "'" + login + "'");
            ResultSet result = statement.getResultSet();
            String dbLogin = null;
            while (result.next()) {
                dbLogin = result.getString("login");
            }
            return new Response(ResponseType.CONNECT, dbLogin);
        } catch (SQLException e) {
            return new Response(ResponseType.ERROR, e.toString());
        }
    }
}
