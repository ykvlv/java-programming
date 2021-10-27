package command;

import client.FlatCreator;
import client.FlatHashMap;
import forFlat.Flat;

public class ReplaceIfGreaterCommand implements Command {
    private final FlatHashMap flatHashMap;
    private final FlatCreator flatCreator;

    public ReplaceIfGreaterCommand(FlatHashMap flatHashMap, FlatCreator flatCreator) {
        this.flatHashMap = flatHashMap;
        this.flatCreator = flatCreator;
    }

    @Override
    public void execute(String[] params) {
        if (params.length != 2) {
            throw new IllegalArgumentException("usage: replace_if_greater key value");
        }
        int key;
        int value;
        try {
            key = Integer.parseInt(params[0]);
            value = Integer.parseInt(params[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ключ и значение должны быть числом.");
        }
        Flat flat = flatHashMap.get(key);
        if (flat == null) {
            throw new IllegalArgumentException("Несуществует элемента с таким key");
        }
        if (value > flat.getArea()) {
            System.out.printf("Предыдущее значение равно %s, замена:%n", flat.getArea());
            flatHashMap.remove(key);
            flatHashMap.put(key, flatCreator.createStandardFlat());
        } else {
            System.out.printf("Настоящее значение %s больше введённого.%n", flat.getArea());
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
