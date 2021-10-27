package command;

public interface Command {
    void execute(String[] params);
    String shortInfo();
    String name();
}
