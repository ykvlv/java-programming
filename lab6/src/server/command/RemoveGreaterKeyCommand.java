package server.command;

import server.FlatHashMap;

public class RemoveGreaterKeyCommand implements Command {
    private final FlatHashMap flatHashMap;

    public RemoveGreaterKeyCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public String execute(String[] params) {
        if (params.length != 1) {
            return "usage: remove_greater_key value";
        }
        int key;
        try {
            key = Integer.parseInt(params[0]);
        } catch (NumberFormatException e) {
            return "Ключ должен быть числом.";
        }
        flatHashMap.entrySet().removeIf(entry -> entry.getKey() > key);
        return "Удаление прошло успешно";
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
