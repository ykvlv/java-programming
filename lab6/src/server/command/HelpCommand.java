package server.command;

import server.CommandRegister;

import java.util.Formatter;
import java.util.Optional;

public class HelpCommand implements Command{
    private final CommandRegister cr;

    public HelpCommand(CommandRegister cr) {
        this.cr = cr;
    }

    @Override
    public String execute(String[] params) {
        Optional<String> string = cr.getAllCommands().entrySet().stream()
                .map(x -> new Formatter().format("\n\t%-25s%s", x.getKey(), x.getValue()).toString())
                .reduce((x, y) -> x + y);
        return "Команды для работы с программой:" + string.orElse("Команд нет.");
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
