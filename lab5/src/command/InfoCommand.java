package command;

import client.FlatHashMap;

import java.time.format.DateTimeFormatter;

public class InfoCommand implements Command {
    private final FlatHashMap flatHashMap;

    public InfoCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public void execute(String[] params) {
        System.out.println("Информация о коллекции:");
        System.out.printf("\tВремя инициализации: %s%n", flatHashMap.getInitTime().format(DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm:ss")));
        System.out.printf("\tНазвание: %s%n", flatHashMap.getName());
        System.out.printf("\tКласс: %s%n", flatHashMap.getClass().getSimpleName());
        System.out.printf("\tРазмер: %d%n", flatHashMap.size());
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
