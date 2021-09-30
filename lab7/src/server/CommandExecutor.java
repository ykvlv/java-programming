package server;

import common.Request;
import common.Response;
import common.ResponseType;
import common.StringDye;
import common.forFlat.Flat;

import java.io.*;
import java.sql.Connection;

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
                Flat flat = (Flat) request.getObject();
                Integer key = (Integer) request.getExtra();
                return add(flat, key);
            case TOUCH:

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
}
