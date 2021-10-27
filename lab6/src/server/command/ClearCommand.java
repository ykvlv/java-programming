package server.command;

import server.FlatHashMap;

public class ClearCommand implements Command {
    private final FlatHashMap flatHashMap;

    public ClearCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public String execute(String[] params) {
        flatHashMap.clear();
        return "Коллекция очищена.";
    }

    @Override
    public String shortInfo() {
        return "Очистить коллекцию";
    }

    @Override
    public String name() {
        return "clear";
    }
}
