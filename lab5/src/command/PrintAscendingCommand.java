package command;

import client.FlatHashMap;
import forFlat.Flat;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class PrintAscendingCommand implements Command {
    private final FlatHashMap flatHashMap;

    public PrintAscendingCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public void execute(String[] params) {
        if (flatHashMap.size() == 0) {
            System.out.println("Коллекция пустая");
            return;
        }
        LinkedHashMap<Integer, Flat> sorted = flatHashMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
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
        return "Вывести элементы коллекции в порядке возрастания";
    }

    @Override
    public String name() {
        return "print_ascending";
    }
}
