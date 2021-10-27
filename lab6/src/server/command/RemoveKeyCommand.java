package server.command;

import server.FlatHashMap;

public class RemoveKeyCommand implements Command {
    private final FlatHashMap flatHashMap;

    public RemoveKeyCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public String execute(String[] params) {
        if (params.length != 1) {
            return "usage: remove_key key";
        }
        int key;
        try {
            key = Integer.parseInt(params[0]);
        } catch (NumberFormatException e) {
            return "Ключ должен быть числом.";
        }
        if (flatHashMap.containsKey(key)) {
            flatHashMap.remove(key);
            return "Элемент с ключом " + key + " удален";
        } else {
            return "Элемента с таким ключом нету.";
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
