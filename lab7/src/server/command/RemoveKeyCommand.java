package server.command;

import common.Response;
import common.ResponseType;
import server.FlatHashMap;

public class RemoveKeyCommand implements Command {
    private final FlatHashMap flatHashMap;

    public RemoveKeyCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public Response execute(String[] params) {
        if (params.length != 1) {
            return new Response(ResponseType.ERROR, "usage: remove_key key");
        }
        int key;
        try {
            key = Integer.parseInt(params[0]);
        } catch (NumberFormatException e) {
            return new Response(ResponseType.ERROR, "Ключ должен быть числом.");
        }
        if (flatHashMap.containsKey(key)) {
            flatHashMap.remove(key);
            return new Response(ResponseType.DONE, "Элемент с ключом " + key + " удален");
        } else {
            return new Response(ResponseType.ERROR, "Элемента с таким ключом нету.");
        }
    }

    @Override
    public String shortInfo() {
        return "Удалить элемент из коллекции по его ключу";
    }

    @Override
    public String name() {
        return "remove_key";
    }
}
