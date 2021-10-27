package command;

public class ExitCommand implements Command {
    @Override
    public void execute(String[] params) {
        System.out.println("Завершение работы.");
    }

    @Override
    public String shortInfo() {
        return "Завершить программу (без сохранения в файл)";
    }

    @Override
    public String name() {
        return "exit";
    }
}
