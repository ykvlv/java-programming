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
    public Response execute(String[] params) {
        if (params.length != 2) {
            return new Response(ResponseType.WARNING_OSHIBKA_WHAAAAT, "usage: replace_if_greater key value");
        }
        int key;
        int value;
        try {
            key = Integer.parseInt(params[0]);
            value = Integer.parseInt(params[1]);
        } catch (NumberFormatException e) {
            return new Response(ResponseType.WARNING_OSHIBKA_WHAAAAT, "Ключ и значение должны быть числом.");
        }
        Flat flat = flatHashMap.get(key);
        if (flat == null) {
            return new Response(ResponseType.WARNING_OSHIBKA_WHAAAAT, "Несуществует элемента с таким key");
        }
        if (value > flat.getArea()) {
            return new Response(ResponseType.MNE_NUZHNA_ELEMENTA, key);
        } else {
            return new Response(ResponseType.WARNING_OSHIBKA_WHAAAAT, "Настоящее значение " + flat.getArea() + " больше введённого. Замена не требуется");
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
