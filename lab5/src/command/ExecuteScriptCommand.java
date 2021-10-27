package command;

import client.CommandRegister;
import client.InputHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ExecuteScriptCommand implements Command {
    private final CommandRegister cr;
    private final ArrayList<String> nowExecuting = new ArrayList<>();
    private final InputHelper inputHelper;

    public ExecuteScriptCommand(CommandRegister cr, InputHelper inputHelper) {
        this.cr = cr;
        this.inputHelper = inputHelper;
    }

    @Override
    public void execute(String[] params) {
        if (params.length != 1) {
            System.out.println("usage: execute_script path");
            return;
        }
        try {
            File file = new File(params[0]);
            Scanner scriptScanner = new Scanner(new InputStreamReader(new FileInputStream(file)));
            if (nowExecuting.contains(params[0])) {
                System.out.printf("Во избежание рекурсии %s запущен не будет%n", params[0]);
                return;
            }
            nowExecuting.add(params[0]);
            inputHelper.setScriptMode(scriptScanner);
            String line;
            while (inputHelper.hasNext()) {
                line = inputHelper.nextLine();
                cr.decryptAndRun(line);
            }
            nowExecuting.remove(params[0]);
            inputHelper.endScriptMode();
        } catch (IOException e) {
            System.out.println("Ошибка с чтением файла");
        }
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
