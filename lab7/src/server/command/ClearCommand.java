package server.command;

import common.Response;
import common.ResponseType;
import server.FlatHashMap;

public class ClearCommand implements Command {
    private final FlatHashMap flatHashMap;

    public ClearCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public Response execute(String[] params, String login) {
        flatHashMap.clear(login);
        return new Response(ResponseType.DONE, "Созданные вами элементы очищены");
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
