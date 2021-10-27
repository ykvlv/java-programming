package server.command;

public interface Command {
    String execute(String[] params);
    String shortInfo();
    String name();
}
