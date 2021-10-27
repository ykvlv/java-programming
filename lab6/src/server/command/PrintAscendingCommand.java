package server.command;

import server.FlatHashMap;

import java.util.Map;
import java.util.Optional;


public class PrintAscendingCommand implements Command {
    private final FlatHashMap flatHashMap;

    public PrintAscendingCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public String execute(String[] params) {
        Optional<String> string = flatHashMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(x -> x.getKey() + ":\n" + x.getValue().toString())
                .reduce((x, y) -> x + "\n" + y);
        return string.orElse("Коллекция пустая");
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
