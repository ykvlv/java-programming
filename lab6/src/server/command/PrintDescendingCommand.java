package server.command;

import server.FlatHashMap;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class PrintDescendingCommand implements Command {
    private final FlatHashMap flatHashMap;

    public PrintDescendingCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public String execute(String[] params) {
        Optional<String> string = flatHashMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(x -> x.getKey() + ":\n" + x.getValue().toString())
                .reduce((x, y) -> x + "\n" + y);
        return string.orElse("Коллекция пустая");
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
