package server.command;

import server.FlatHashMap;

public class UpdateCommand implements Command {
    private final FlatHashMap flatHashMap;

    public UpdateCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }
    @Override
    public String execute(String[] params) {
        if (params.length != 1) {
            return "Первым аргументом вводится key.";
        }
        int key;
        try {
            key = Integer.parseInt(params[0]);
        } catch (NumberFormatException e) {
            return "Ключ должен быть числом.";
        }
        if (!flatHashMap.containsKey(key)) {
            return "Элемента с данным ключом нет в коллекции. Воспользуйтесь \"insert\"";
        }
        flatHashMap.remove(key);
        return "Добавление..";
    }

    @Override
    public String shortInfo() {
        return "Обновить значение элемента, id которого равен заданному";
    }

    @Override
    public String name() {
        return "update";
    }
}
