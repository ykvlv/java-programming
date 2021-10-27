package command;

import client.CommandRegister;

import java.util.Map;

public class HelpCommand implements Command{
    private final CommandRegister cr;

    public HelpCommand(CommandRegister cr) {
        this.cr = cr;
    }

    @Override
    public void execute(String[] params) {
        System.out.println("Стандартные команды для рабты с программой:");
        for (Map.Entry<String, String> entry : cr.getAllCommands().entrySet()) {
            System.out.printf("\t%-25s%s%n", entry.getKey(), entry.getValue());
        }
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
