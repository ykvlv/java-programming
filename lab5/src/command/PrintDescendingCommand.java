package command;

import client.FlatHashMap;
import forFlat.Flat;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PrintDescendingCommand implements Command {
    private final FlatHashMap flatHashMap;

    public PrintDescendingCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public void execute(String[] params) {
        LinkedHashMap<Integer, Flat> sorted = flatHashMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        for (Map.Entry<Integer, Flat> entry : sorted.entrySet()) {
            System.out.println(entry.getKey() + ":");
            System.out.println(entry.getValue().toString());
        }
    }

    @Override
    public String shortInfo() {
        return "Вывести элементы коллекции в порядке убывания";
    }

    @Override
    public String name() {
        return "print_descending";
    }
}
