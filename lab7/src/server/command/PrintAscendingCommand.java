package server.command;

import common.Response;
import common.ResponseType;
import server.FlatHashMap;

import java.util.Map;
import java.util.Optional;


public class PrintAscendingCommand implements Command {
    private final FlatHashMap flatHashMap;

    public PrintAscendingCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public Response execute(String[] params, String login) {
        Optional<String> string = flatHashMap.getFlats().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(x -> x.getKey() + ":\n" + x.getValue().toString())
                .reduce((x, y) -> x + "\n" + y);
        return new Response(ResponseType.DONE, string.orElse("Коллекция пустая"));
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
