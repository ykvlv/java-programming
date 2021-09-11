package server.command;

import common.Response;
import common.ResponseType;
import server.FlatHashMap;

import java.util.Set;
import java.util.stream.Collectors;

public class RemoveGreaterKeyCommand implements Command {
    private final FlatHashMap flatHashMap;

    public RemoveGreaterKeyCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public Response execute(String[] params) {
        if (params.length != 1) {
            return new Response(ResponseType.ERROR, "usage: remove_greater_key value");
        }
        int key;
        try {
            key = Integer.parseInt(params[0]);
        } catch (NumberFormatException e) {
            return new Response(ResponseType.ERROR, "Ключ должен быть числом.");
        }
        //Множество ключей которые надо будет удалить
        Set<Integer> collect = flatHashMap.getFlats().keySet().stream()
                .filter(curr_key -> curr_key > key)
                .collect(Collectors.toSet());
        for (Integer illiquidKey : collect) {
            flatHashMap.remove(illiquidKey);
        }
        return new Response(ResponseType.DONE, "Удаление прошло успешно");
    }

    @Override
    public String shortInfo() {
        return "Удалить из коллекции все элементы, ключ которых больше заданного";
    }

    @Override
    public String name() {
        return "remove_greater_key";
    }
}
