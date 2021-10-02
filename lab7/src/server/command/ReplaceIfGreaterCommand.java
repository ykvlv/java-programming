package server.command;

import common.Response;
import common.ResponseType;
import server.FlatHashMap;
import common.forFlat.Flat;

public class ReplaceIfGreaterCommand implements Command {
    private final FlatHashMap flatHashMap;

    public ReplaceIfGreaterCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public Response execute(String[] params, String login) {
        if (params.length != 2) {
            return new Response(ResponseType.ERROR, "usage: replace_if_greater key value");
        }
        int key;
        int value;
        try {
            key = Integer.parseInt(params[0]);
            value = Integer.parseInt(params[1]);
        } catch (NumberFormatException e) {
            return new Response(ResponseType.ERROR, "Ключ и значение должны быть числом.");
        }
        if (!flatHashMap.getOwner(key).equals(login)) {
            return new Response(ResponseType.ERROR, "Этот элемент вам не принадлежит");
        }
        Flat flat = flatHashMap.get(key);
        if (flat == null) {
            return new Response(ResponseType.ERROR, "Несуществует элемента с таким key");
        }
        if (value > flat.getArea()) {
            return new Response(ResponseType.REQUEST_ITEM, key);
        } else {
            return new Response(ResponseType.ERROR, "Настоящее значение " + flat.getArea() + " больше введённого. Замена не требуется");
        }
    }

    @Override
    public String shortInfo() {
        return "Заменить значение по ключу, если новое значение больше старого";
    }

    @Override
    public String name() {
        return "replace_if_greater";
    }
}
