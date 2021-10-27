package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.Request;
import common.RequestType;
import common.forFlat.Flat;

import java.io.*;
import java.net.SocketAddress;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestExecutor {
    private final HashMap<SocketAddress, CommandRegister> hashMaps = new HashMap<>();

    public RequestExecutor() {
    }

    public Request execute(Request request) {
        System.out.println(request.getObject());
        System.out.println(request.getType());
        System.out.println(request.getAddress());
        if (request.getType() == RequestType.CONNECTION) {
            return connectTo(request.getAddress(), (String) request.getObject());

        } else if (request.getType() == RequestType.EXECUTION) {
            return execute(request.getAddress(),(String) request.getObject());

        } else if (request.getType() == RequestType.ADDITEM) {
            return add(request.getAddress(), (Flat) request.getObject());

        }
        return null;
    }

    private Request execute(SocketAddress address, String args) {
        if (!hashMaps.containsKey(address)) {
            return new Request(RequestType.BAD, "Соединение не установлено.");
        }
        String result = hashMaps.get(address).decryptAndRun(args);
        Request request;
        if (result.equals("Добавление..")) {
            request = new Request(RequestType.ADDITEM, result);
        } else if (result.equals("Выполнение скрипта..")) {
            request = new Request(RequestType.SCRIPT, result);
        } else {
            request = new Request(RequestType.EXECUTION, result);
        }
        return request;
    }

    private Request add(SocketAddress address, Flat flat) {
        String result = hashMaps.get(address).add(flat.getKey(), flat);
        return new Request(RequestType.COOL, result);
    }

    public void saveAll() {
        for (CommandRegister reg : hashMaps.values()) {
            Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
            HashMap<Integer, Flat> allFlats = new HashMap<>();
            for (Map.Entry<Integer, Flat> entry : reg.getMap().entrySet()) {
                allFlats.put(entry.getKey(), entry.getValue());
            }
            String json = gson.toJson(allFlats);
            try {
                File file = new File(reg.getName());
                FileOutputStream fos = new FileOutputStream(file);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                osw.write(json);
                osw.close();
                System.out.println("Коллекция " + reg.getName() + " сохранена.");
            } catch (FileNotFoundException e) {
                System.out.println("Доступ к " + reg.getName() + " ограничен.");
            } catch (IOException e) {
                System.out.println("Записать " + reg.getName() + " невозможно.");
            }
        }
    }

    private Request connectTo(SocketAddress address, String name) {
        Optional<CommandRegister> hashMap = hashMaps.values().stream()
                .filter(x -> x.getName().equals(name))
                .findAny();
        if (hashMap.isPresent()) {
            hashMaps.put(address, hashMap.get());
            return new Request(RequestType.CONNECTION, RequestType.COOL);
        } else {
            try {
                hashMaps.put(address, new CommandRegister(name, new FlatHashMap(LocalDateTime.now(), name)));
                return new Request(RequestType.CONNECTION, RequestType.COOL);
            } catch (FileNotFoundException e) {
                System.out.printf("Файл %s не найден или доступ к нему ограничен%n", name);
                return new Request(RequestType.CONNECTION, RequestType.NOPE);
            } catch (IOException e) {
                System.out.printf("Ошибка в чтении %s%n", name);
                return new Request(RequestType.CONNECTION, RequestType.BAD);
            }
        }
    }
}
