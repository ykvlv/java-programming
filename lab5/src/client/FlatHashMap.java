package client;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import forFlat.Flat;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FlatHashMap {
    private final HashMap<Integer, Flat> flats;
    private final String name;
    private final ArrayList<Integer> ids = new ArrayList<>();
    private final LocalDateTime initTime;

    public FlatHashMap(LocalDateTime initTime, String[] args) throws Exception {
        this.initTime = initTime;
        if (args.length == 0) {
            System.out.println("Создание новой коллекции");
            this.name = "New_at_" + initTime.format(DateTimeFormatter.ofPattern("dd.MM.uuuu_HH:mm:ss")) + ".json";
            flats = new HashMap<>();
        } else {
            try {
                File file = new File(args[0]);
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String json = "", line;
                while ((line = br.readLine()) != null) {
                    json = json.concat(line.trim());
                }
                Type itemsHashMapType = new TypeToken<HashMap<Integer, Flat>>() {
                }.getType();
                this.flats = new Gson().fromJson(json, itemsHashMapType);
                System.out.println("Коллекция успешно загружена.");
            } catch (FileNotFoundException e) {
                System.out.printf("Файл %s не найден или доступ к нему ограничен%n", args[0]);
                throw new FileNotFoundException();
            } catch (JsonParseException e) {
                System.out.println("Ошибка в парсинге json");
                throw new JsonParseException("смерть");
            } catch (IOException e) {
                System.out.println("Ошибка ввода вывода, извините программа завершается.");
                throw new IOException();
            }
            this.name = args[0];
            updateIds();
        }
    }

    public void clear() {
        flats.clear();
    }

    public LocalDateTime getInitTime() {
        return initTime;
    }

    public String getName() {
        return name;
    }

    public void updateIds() {
        ids.clear();
        for (Map.Entry<Integer, Flat> entry : entrySet()) {
            ids.add(entry.getValue().getId());
        }
    }

    public int size() {
        return flats.size();
    }

    public Set<Map.Entry<Integer, Flat>> entrySet() {
        return flats.entrySet();
    }

    public boolean containsKey(int key) {
        return flats.containsKey(key);
    }

    public void remove(int key) {
        flats.remove(key);
    }

    public void put(int key, Flat flat) {
        flats.put(key, flat);
    }

    public Flat get(int key) {
        return flats.get(key);
    }

    public ArrayList<Integer> getIds() {
        return ids;
    }
}
