package server.command;

import common.Response;
import common.ResponseType;
import server.FlatHashMap;

public class RemoveLowerKeyCommand implements Command {
    private final FlatHashMap flatHashMap;

    public RemoveLowerKeyCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public Response execute(String[] params) {
        if (params.length != 1) {
            return new Response(ResponseType.ERROR, "usage: remove_lower_key value");
        }
        int key;
        try {
            key = Integer.parseInt(params[0]);
        } catch (NumberFormatException e) {
            return new Response(ResponseType.ERROR, "Ключ должен быть числом.");
        }
        flatHashMap.getFlats().entrySet().stream()
                .filter(entry -> entry.getKey() < key)
                .forEach(entry -> flatHashMap.remove(entry.getKey()));
        return new Response(ResponseType.DONE, "Удаление прошло успешно");
    }

    @Override
    public String shortInfo() {
        return "Удалить из коллекции все элементы, ключ которых меньше, заданного";
    }

    @Override
    public String name() {
        return "remove_lower_key";
    }
}
