package command;

import client.FlatHashMap;

public class RemoveKeyCommand implements Command {
    private final FlatHashMap flatHashMap;

    public RemoveKeyCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public void execute(String[] params) {
        if (params.length != 1) {
            System.out.println("usage: remove_key key");
            return;
        }
        int key;
        try {
            key = Integer.parseInt(params[0]);
        } catch (NumberFormatException e) {
            System.out.println("Ключ должен быть числом.");
            return;
        }
        if (flatHashMap.containsKey(key)) {
            flatHashMap.remove(key);
            System.out.printf("Элемент с ключом %s удален.%n", key);
        } else {
            System.out.println("Элемента с таким ключом нету.");
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
