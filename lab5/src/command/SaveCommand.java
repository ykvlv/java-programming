package command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import client.FlatHashMap;
import forFlat.Flat;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SaveCommand implements Command {
    private final FlatHashMap flatHashMap;

    public SaveCommand(FlatHashMap flatHashMap) {
        this.flatHashMap = flatHashMap;
    }

    @Override
    public void execute(String[] params) {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        HashMap<Integer, Flat> allFlats = new HashMap<>();
        for (Map.Entry<Integer, Flat> entry : flatHashMap.entrySet()) {
            allFlats.put(entry.getKey(), entry.getValue());
        }
        String json = gson.toJson(allFlats);
        try {
            File file = new File(flatHashMap.getName());
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(json);
            osw.close();
            System.out.println("Коллекция сохранена.");
        } catch (FileNotFoundException e) {
            System.out.println("Доступ к файлу ограничен, сохранение невозможно.");
        } catch (IOException e) {
            System.out.println("О черт, разработчик не знает что произошло, но сохранить файл невозможно.");
        }
    }

    @Override
    public String shortInfo() {
        return "Сохранить коллекцию в файл";
    }

    @Override
    public String name() {
        return "save";
    }
}
