package server.command;

import common.Response;
import common.ResponseType;
import server.FlatHashMap;

public class UpdateCommand implements Command {
    private final FlatHashMap flatHashMap;

    public UpdateCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }
    @Override
    public Response execute(String[] params) {
        if (params.length != 1) {
            return new Response(ResponseType.ERROR, "Первым аргументом вводится key.");
        }
        int key;
        try {
            key = Integer.parseInt(params[0]);
        } catch (NumberFormatException e) {
            return new Response(ResponseType.ERROR, "Ключ должен быть числом.");
        }
        if (!flatHashMap.containsKey(key)) {
            return new Response(ResponseType.ERROR, "Элемента с данным ключом нет в коллекции. Воспользуйтесь \"insert\"");
        }
        return new Response(ResponseType.REQUEST_ITEM, key);
    }

    @Override
    public String shortInfo() {
        return "Обновить значение элемента, id которого равен заданному";
    }

    @Override
    public String name() {
        return "update";
    }
}
