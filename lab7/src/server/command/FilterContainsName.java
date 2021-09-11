package server.command;

import common.Response;
import common.ResponseType;
import server.FlatHashMap;

import java.util.Optional;

public class FilterContainsName implements Command {
    private final FlatHashMap flatHashMap;

    public FilterContainsName(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public Response execute(String[] params) {
        if (params.length != 1) {
            return new Response(ResponseType.ERROR, "usage: filter_contains_name name");
        }
        Optional<String> string = flatHashMap.getFlats().entrySet().stream()
                .filter(x -> x.getValue()
                        .getName()
                        .contains(params[0]))
                .map(x -> x.getKey() + ":\n" + x.getValue().toString())
                .reduce((x, y) -> x + "\n" + y);
        return new Response(ResponseType.DONE, string.orElse("Элементов по данному фильтру не найдено"));
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
