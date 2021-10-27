package command;

import client.FlatHashMap;

public class RemoveLowerKeyCommand implements Command {
    private final FlatHashMap flatHashMap;

    public RemoveLowerKeyCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public void execute(String[] params) {
        if (params.length != 1) {
            System.out.println("usage: remove_lower_key value");
            return;
        }
        int key;
        try {
            key = Integer.parseInt(params[0]);
        } catch (NumberFormatException e) {
            System.out.println("Ключ должен быть числом.");
            return;
        }
        flatHashMap.entrySet().removeIf(entry -> entry.getKey() < key);
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
