package server.command;

import common.Response;
import common.ResponseType;

import java.io.FileNotFoundException;

public class ExecuteScriptCommand implements Command {

    @Override
    public Response execute(String[] params, String login) {
        try {
            return new Response(ResponseType.SCRIPT, params[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return new Response(ResponseType.ERROR, "Введите название скрипта");
        }
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
