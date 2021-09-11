package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.forFlat.Flat;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;

public class FlatHashMap {
    private final HashMap<Integer, Flat> flats;
    private final String fileName;
    private final HashSet<Integer> ids = new HashSet<>();
    private final LocalDateTime initTime;

    public FlatHashMap(LocalDateTime initTime, String fileName) throws IOException {
        this.initTime = initTime;
        this.fileName = fileName;
        File file = new File(fileName.trim());
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String json = "";
        while (br.ready()) {
            json = json.concat(br.readLine().trim());
        }
        Type itemsHashMapType = new TypeToken<HashMap<Integer, Flat>>() {}.getType();
        flats = new Gson().fromJson(json, itemsHashMapType);
        flats.values().forEach(flat -> ids.add(flat.getId()));
    }

    public void clear() {
        flats.clear();
        ids.clear();
    }

    public HashMap<Integer, Flat> getFlats() {
        return flats;
    }

    public LocalDateTime getInitTime() {
        return initTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void remove(int key) {
        System.out.println(ids);
        ids.remove(flats.get(key).getId());
        flats.remove(key);
        System.out.println(ids);
    }

    public boolean containsKey(int key) {
        return flats.containsKey(key);
    }

    public void put(int key, Flat flat) {
        flats.put(key, flat);
        ids.add(flat.getId());
    }

    public Flat get(int key) {
        return flats.get(key);
    }
}
