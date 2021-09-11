package server.command;

import common.Response;
import common.ResponseType;

public class ExecuteScriptCommand implements Command {

    @Override
    public Response execute(String[] params) {
        return new Response(ResponseType.SCRIPT, "Выполнение скрипта...");
    }

    @Override
    public String shortInfo() {
        return "Исполнить скрипт из указанного файла";
    }

    @Override
    public String name() {
        return "execute_script";
    }
}
