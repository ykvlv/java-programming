package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.Request;
import common.Response;
import common.ResponseType;
import common.forFlat.Flat;
import common.forFlat.FlatAndKey;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {
    private final CommandRegister commandRegister;
    private final FlatHashMap flatHashMap;

    public CommandExecutor(CommandRegister commandRegister, FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
        this.commandRegister = commandRegister;
    }

    public Response execute(Request request) {
        switch (request.getRequestType()) {
            case VIPOLNIT_COMMANDA_PLZ:
                return execute((String) request.getObject());
            case DOBAV_ELEMENT_PROSHU:
                FlatAndKey flatAndKey = (FlatAndKey) request.getObject();
                return add(flatAndKey.getKey(), flatAndKey.getFlat());
            default:
                return new Response(ResponseType.WARNING_OSHIBKA_WHAAAAT, "Ало клиент а что тебе надо?");
        }
    }

    private Response add(Integer key, Flat flat) {
        flatHashMap.put(key, flat);
        return new Response(ResponseType.OK_YA_SDELAL, "Элемент добавлен");
    }

    private Response execute(String args) {
        //Да хочу чтобы тут не было возвращения запросов. Типа тут будет стринг – значит все ок. иначе там ошибка вылетит.
        return commandRegister.decryptAndRun(args);
    }

    public void saveAll() {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        String json = gson.toJson(flatHashMap.getFlats());
        try {
            File file = new File(flatHashMap.getFileName());
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(json);
            osw.close();
            System.out.println("Коллекция " + flatHashMap.getFileName() + " сохранена.");
        } catch (FileNotFoundException e) {
            System.out.println("Доступ к " + flatHashMap.getFileName() + " ограничен.");
        } catch (IOException e) {
            System.out.println("Записать " + flatHashMap.getFileName() + " невозможно.");
        }
    }
}
