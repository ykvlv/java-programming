package command;

import client.FlatHashMap;

public class ClearCommand implements Command {
    private final FlatHashMap flatHashMap;

    public ClearCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public void execute(String[] params) {
        flatHashMap.clear();
        System.out.println("Коллекция очищена.");
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
