package client;

import common.Request;
import common.RequestType;
import common.Response;
import common.forFlat.Flat;
import common.forFlat.FlatAndKey;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;

public class ResponseHandler {
    private final FlatCreator flatCreator;
    private final HashSet<String> nowExecuting = new HashSet<>();
    private final DeliveryHandler deliveryHandler;
    private final Client client;

    public ResponseHandler(FlatCreator flatCreator, DeliveryHandler deliveryHandler, Client client) {
        this.client = client;
        this.deliveryHandler = deliveryHandler;
        this.flatCreator = flatCreator;
    }

    public void process(Response response) {
        switch (response.getResponseType()) {
            case OK_YA_SDELAL:
                System.out.println((String) response.getObject());
                break;
            case WARNING_OSHIBKA_WHAAAAT:
                System.err.println((String) response.getObject());
                break;
            case MNE_NUZHNA_ELEMENTA:
                try {
                    Flat flat = flatCreator.createStandardFlat();
                    sendElement(flat, (Integer) response.getObject());
                } catch (InterruptedIOException e) {
                    System.out.println("Создание элемента коллекции прервано.");
                } catch (IOException e) {
                    System.err.println("Ошибка отправки элемента коллекции.");
                }
                break;
            case SCRIPT_EBASH:
                try {
                    executeScript((String) response.getObject());
                } catch (FileNotFoundException e) {
                    System.err.println("Файл не найден.");
                }
                break;
        }
    }

    public void sendElement(Flat flat, Integer key) throws IOException {
        Request request = new Request(RequestType.DOBAV_ELEMENT_PROSHU, new FlatAndKey(flat, key));
        deliveryHandler.sendRequest(request);
    }

    public void executeScript(String fileName) throws FileNotFoundException {
        Scanner scriptScanner = new Scanner(new InputStreamReader(new FileInputStream(fileName)));
        if (nowExecuting.contains(fileName)) {
            System.err.printf("Во избежание рекурсии %s запущен не будет%n", fileName);
            return;
        }
        nowExecuting.add(fileName);
        client.run(new InputHandler(scriptScanner, true));
        nowExecuting.remove(fileName);
    }
}
