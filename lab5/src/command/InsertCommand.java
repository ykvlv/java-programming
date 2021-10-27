package command;

import client.FlatCreator;
import client.FlatHashMap;

public class InsertCommand implements Command{
    private final FlatHashMap flatHashMap;
    private final FlatCreator flatCreator;

    public InsertCommand(FlatHashMap flatHashMap, FlatCreator flatCreator) {
        this.flatHashMap = flatHashMap;
        this.flatCreator = flatCreator;
    }

    @Override
    public void execute(String[] params) throws IllegalArgumentException {
        int key;
        if (params.length != 1) {
            throw new IllegalArgumentException("usage: insert key");
        } else {
            try {
                key = Integer.parseInt(params[0]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Key должен быть числом");
            }
        }
        if (flatHashMap.containsKey(key)) {
            System.out.println("Элемент с данным ключом уже есть в коллекции. Воспользуйтесь \"update\"");
            return;
        }
        flatHashMap.put(key, flatCreator.createStandardFlat());
        System.out.println("Элемент добавлен.");
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
