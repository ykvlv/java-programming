package server.command;

import common.Response;

public interface Command {
    Response execute(String[] params);
    String shortInfo();
    String name();
}
