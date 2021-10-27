package server.command;

import server.FlatHashMap;

public class InsertCommand implements Command{
    private final FlatHashMap flatHashMap;

    public InsertCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public String execute(String[] params) {
        int key;
        if (params.length != 1) {
            return "usage: insert key";
        } else {
            try {
                key = Integer.parseInt(params[0]);
            } catch (NumberFormatException e) {
                return "Key должен быть числом";
            }
        }
        if (flatHashMap.containsKey(key)) {
            return "Элемент с данным ключом уже есть в коллекции. Воспользуйтесь \"update\"";
        }
        return "Добавление..";
    }

    @Override
    public String shortInfo() {
        return "Добавить новый элемент с заданным ключом";
    }

    @Override
    public String name() {
        return "insert";
    }
}
