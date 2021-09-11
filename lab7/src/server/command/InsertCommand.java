package server.command;

import common.Response;
import common.ResponseType;
import server.FlatHashMap;

public class InsertCommand implements Command {
    private final FlatHashMap flatHashMap;

    public InsertCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public Response execute(String[] params) {
        int key;
        if (params.length != 1) {
            return new Response(ResponseType.ERROR, "usage: insert key");
        } else {
            try {
                key = Integer.parseInt(params[0]);
            } catch (NumberFormatException e) {
                return new Response(ResponseType.ERROR, "Key должен быть числом");
            }
        }
        if (flatHashMap.containsKey(key)) {
            return new Response(ResponseType.ERROR, "Элемент с данным ключом уже есть в коллекции. Воспользуйтесь \"update\"");
        }
        return new Response(ResponseType.REQUEST_ITEM, key);
    }

    @Override
    public String shortInfo() {
        return "Добавить новый элемент с заданным ключом";
    }

    @Override
    public String name() {
        return "insert";
    }
}
