package server.command;

public class ExecuteScriptCommand implements Command {

    @Override
    public String execute(String[] params) {
        return "Выполнение скрипта..";
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
