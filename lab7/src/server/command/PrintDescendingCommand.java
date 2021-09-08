package server.command;

import common.Response;
import common.ResponseType;
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
    public Response execute(String[] params) {
        Optional<String> string = flatHashMap.getFlats().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(x -> x.getKey() + ":\n" + x.getValue().toString())
                .reduce((x, y) -> x + "\n" + y);
        return new Response(ResponseType.OK_YA_SDELAL, string.orElse("Коллекция пустая"));
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
