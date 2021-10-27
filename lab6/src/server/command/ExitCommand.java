package server.command;

public class ExitCommand implements Command {
    @Override
    public String execute(String[] params) {
        return "Завершение работы.";
    }

    @Override
    public String shortInfo() {
        return "Завершить программу";
    }

    @Override
    public String name() {
        return "exit";
    }
}
