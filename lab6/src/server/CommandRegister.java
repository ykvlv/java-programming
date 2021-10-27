package server;

import server.command.Command;
import server.command.*;
import common.forFlat.Flat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandRegister {
    private final HashMap<String, Command> commands = new HashMap<>();
    private final String name;
    private final FlatHashMap flatHashMap;

    public CommandRegister(String name, FlatHashMap flatHashMap) {
        this.name = name;
        addCommand(new UpdateCommand(flatHashMap));
        addCommand(new ReplaceIfGreaterCommand(flatHashMap));
        addCommand(new HelpCommand(this));
        addCommand(new InfoCommand(flatHashMap));
        addCommand(new ShowCommand(flatHashMap));
        addCommand(new InsertCommand(flatHashMap));
        addCommand(new RemoveKeyCommand(flatHashMap));
        addCommand(new ClearCommand(flatHashMap));
        addCommand(new ExecuteScriptCommand());
        addCommand(new ExitCommand());
        addCommand(new RemoveGreaterKeyCommand(flatHashMap));
        addCommand(new RemoveLowerKeyCommand(flatHashMap));
        addCommand(new FilterContainsName(flatHashMap));
        addCommand(new PrintAscendingCommand(flatHashMap));
        addCommand(new PrintDescendingCommand(flatHashMap));
        this.flatHashMap = flatHashMap;
    }

    private void addCommand(Command command) {
        if (commands.containsKey(command.name())) {
            throw new IllegalArgumentException("Две команды с одинаковым названием");
        } else {
            commands.put(command.name(), command);
        }
    }

    public String decryptAndRun(String request) {
        String[] commandAndParams = request.trim().split(" ");
        String command = commandAndParams[0].toLowerCase();
        String[] params = Arrays.copyOfRange(commandAndParams, 1, commandAndParams.length);

        if (commands.containsKey(command)) {
            return commands.get(command).execute(params);
        } else {
            return "Команды нет в списке, " + possibleCommand(command);
        }
    }

    public String add(Integer key, Flat flat) {
        flatHashMap.put(key, flat);
        return "Добавлен элемент с ключом " + key;
    }

    public FlatHashMap getMap() {
        return flatHashMap;
    }

    public HashMap<String, String> getAllCommands() {
        HashMap<String, String> allCommands = new HashMap<>();
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            allCommands.put(entry.getKey(), entry.getValue().shortInfo());
        }
        return allCommands;
    }

    public String getName() {
        return name;
    }

    private String possibleCommand(String name) {
        String possibleName = "help";
        int levenshteinDistance = Integer.MAX_VALUE;
        for (String potentialName : commands.keySet()) {
            int[] dist = new int[name.length() + 1];
            for (int i = 0; i < dist.length; ++i) {
                dist[i] = i;
            }
            for (int j = 0; j < potentialName.length(); ++j) {
                int diag = dist[0]++;
                for (int i = 0; i < name.length(); ++i) {
                    int update = diag;
                    if (name.charAt(i) != potentialName.charAt(j)) {
                        update++;
                    }
                    for (int delta = 0; delta < 2; ++delta) {
                        if (update > dist[i + delta] + 1) {
                            update = dist[i + delta] + 1;
                        }
                    }
                    diag = dist[i + 1];
                    dist[i + 1] = update;
                }
            }
            if (dist[dist.length - 1] < levenshteinDistance) {
                possibleName = potentialName;
                levenshteinDistance = dist[dist.length - 1];
            }
        }
        return levenshteinDistance <= name.length()/2 ? "возможно вы имели в виду \"" + possibleName + "\"" : "воспользуйтесь \"help\"";
    }
}
