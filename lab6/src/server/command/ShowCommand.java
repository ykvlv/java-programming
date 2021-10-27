package server.command;

import server.FlatHashMap;

import java.util.Optional;

public class ShowCommand implements Command {
    private final FlatHashMap flatHashMap;

    public ShowCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public String execute(String[] params) {
        Optional<String> string = flatHashMap.entrySet().stream()
                .map(x -> x.getKey() + ":\n" + x.getValue().toString())
                .reduce((x, y) -> x + "\n" + y);
        return string.orElse("Коллекция пустая.");
    }

    @Override
    public String shortInfo() {
        return "Вывести все элементы коллекции в строковом представлении";
    }

    @Override
    public String name() {
        return "show";
    }
}
