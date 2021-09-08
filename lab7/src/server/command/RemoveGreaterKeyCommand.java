package server.command;

import common.Response;
import common.ResponseType;
import server.FlatHashMap;

public class RemoveGreaterKeyCommand implements Command {
    private final FlatHashMap flatHashMap;

    public RemoveGreaterKeyCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public Response execute(String[] params) {
        if (params.length != 1) {
            return new Response(ResponseType.WARNING_OSHIBKA_WHAAAAT, "usage: remove_greater_key value");
        }
        int key;
        try {
            key = Integer.parseInt(params[0]);
        } catch (NumberFormatException e) {
            return new Response(ResponseType.WARNING_OSHIBKA_WHAAAAT, "Ключ должен быть числом");
        }
        flatHashMap.getFlats().entrySet().stream()
                .filter(entry -> entry.getKey() > key)
                .forEach(entry -> flatHashMap.remove(entry.getKey()));
        return new Response(ResponseType.OK_YA_SDELAL, "Удаление прошло успешно");
    }

    @Override
    public String shortInfo() {
        return "Удалить из коллекции все элементы, ключ которых превышает заданный";
    }

    @Override
    public String name() {
        return "remove_greater_key";
    }
}
