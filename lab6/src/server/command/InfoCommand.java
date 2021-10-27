package server.command;

import server.FlatHashMap;

import java.time.format.DateTimeFormatter;

public class InfoCommand implements Command {
    private final FlatHashMap flatHashMap;

    public InfoCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public String execute(String[] params) {
        return "Информация о коллекции:\n" +
                "\tВремя инициализации: " + flatHashMap.getInitTime().format(DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm:ss")) + "\n" +
                "\tНазвание: " + flatHashMap.getName() + "\n" +
                "\tКласс: " + flatHashMap.getClass().getSimpleName() + "\n" +
                "\tРазмер: " + flatHashMap.size();
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
