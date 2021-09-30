package server.command;

import common.Response;
import common.ResponseType;
import server.FlatHashMap;

import java.time.format.DateTimeFormatter;

public class InfoCommand implements Command {
    private final FlatHashMap flatHashMap;

    public InfoCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public Response execute(String[] params) {
        return new Response(ResponseType.DONE,
                "Информация о коллекции:\n" +
                "\tВремя инициализации: " + flatHashMap.getInitTime()
                        .format(DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm:ss")) + "\n" +
                "\tКласс: " + flatHashMap.getClass().getSimpleName() + "\n" +
                "\tРазмер: " + flatHashMap.getFlats().size());
    }

    @Override
    public String shortInfo() {
        return "Вывести информацию о коллекции";
    }

    @Override
    public String name() {
        return "info";
    }
}
