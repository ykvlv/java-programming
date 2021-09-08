package server.command;

import common.Response;
import common.ResponseType;
import server.CommandRegister;

import java.util.Formatter;
import java.util.Optional;

public class HelpCommand implements Command {
    private final CommandRegister cr;

    public HelpCommand(CommandRegister cr) {
        this.cr = cr;
    }

    @Override
    public Response execute(String[] params) {
        Optional<String> string = cr.getCommands().entrySet().stream()
                .map(x -> new Formatter().format("\n\t%-25s%s", x.getKey(), x.getValue().shortInfo()).toString())
                .reduce((x, y) -> x + y);
        return new Response(ResponseType.OK_YA_SDELAL, string
                .map(s -> "Команды для работы с программой:" + s)
                .orElse("Комманд нет."));
    }

    @Override
    public String shortInfo() {
        return "Вывести справку по доступным командам";
    }

    @Override
    public String name() {
        return "help";
    }
}
