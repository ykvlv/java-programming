package server.command;

import server.FlatHashMap;
import common.forFlat.Flat;

public class ReplaceIfGreaterCommand implements Command {
    private final FlatHashMap flatHashMap;

    public ReplaceIfGreaterCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public String  execute(String[] params) {
        if (params.length != 2) {
            return "usage: replace_if_greater key value";
        }
        int key;
        int value;
        try {
            key = Integer.parseInt(params[0]);
            value = Integer.parseInt(params[1]);
        } catch (NumberFormatException e) {
            return "Ключ и значение должны быть числом.";
        }
        Flat flat = flatHashMap.get(key);
        if (flat == null) {
            return "Несуществует элемента с таким key";
        }
        if (value > flat.getArea()) {
            flatHashMap.remove(key);
            return "Добавление..";
        } else {
            return "Настоящее значение " + flat.getArea() + " больше введённого.";
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
