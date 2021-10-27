package command;

import client.FlatCreator;
import client.FlatHashMap;

public class UpdateCommand implements Command {
    private final FlatHashMap flatHashMap;
    private final FlatCreator flatCreator;

    public UpdateCommand(FlatHashMap flatHashMap, FlatCreator flatCreator) {
        this.flatHashMap = flatHashMap;
        this.flatCreator = flatCreator;
    }
    @Override
    public void execute(String[] params) {
        if (params.length != 1) {
            System.out.println("Первым аргументом вводится key.");
            return;
        }
        int key;
        try {
            key = Integer.parseInt(params[0]);
        } catch (NumberFormatException e) {
            System.out.println("Ключ должен быть числом.");
            return;
        }
        if (!flatHashMap.containsKey(key)) {
            System.out.println("Элемента с данным ключом нет в коллекции. Воспользуйтесь \"insert\"");
            return;
        }
        System.out.println("Обновление элемента:");
        flatHashMap.put(key, flatCreator.createStandardFlat());
        System.out.println("Элемент обновлен.");
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
