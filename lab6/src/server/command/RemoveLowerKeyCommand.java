package server.command;

import server.FlatHashMap;

public class RemoveLowerKeyCommand implements Command {
    private final FlatHashMap flatHashMap;

    public RemoveLowerKeyCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public String execute(String[] params) {
        if (params.length != 1) {
            return "usage: remove_lower_key value";
        }
        int key;
        try {
            key = Integer.parseInt(params[0]);
        } catch (NumberFormatException e) {
            return "Ключ должен быть числом.";
        }
        flatHashMap.entrySet().removeIf(entry -> entry.getKey() < key);
        return "Удаление прошло успешно";
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
