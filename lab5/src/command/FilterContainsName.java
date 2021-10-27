package command;

import client.FlatHashMap;
import forFlat.Flat;

import java.util.Map;

public class FilterContainsName implements Command {
    private final FlatHashMap flatHashMap;

    public FilterContainsName(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public void execute(String[] params) {
        if (params.length != 1) {
            System.out.println("usage: filter_contains_name name");
            return;
        }
        for (Map.Entry<Integer, Flat> entry : flatHashMap.entrySet()) {
            if (entry.getValue().getName().contains(params[0])) {
                System.out.println(entry.getKey() + ":");
                System.out.println(entry.getValue().toString());
            }
        }
    }

    @Override
    public String shortInfo() {
        return "Вывести элементы, значение поля name которых содержит подстроку";
    }

    @Override
    public String name() {
        return "filter_contains_name";
    }
}
