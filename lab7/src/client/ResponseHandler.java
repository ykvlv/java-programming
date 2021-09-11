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
            case DONE:
                System.out.println((String) response.getObject());
                break;
            case ERROR:
                System.err.println((String) response.getObject());
                break;
            case REQUEST_ITEM:
                try {
                    Flat flat = flatCreator.createStandardFlat();
                    sendElement(flat, (Integer) response.getObject());
                } catch (InterruptedIOException e) {
                    System.err.println("Создание элемента коллекции прервано.");
                } catch (IOException e) {
                    System.err.println("Ошибка отправки элемента коллекции.");
                } catch (ClassNotFoundException e) {
                    System.err.println("Ошибка распаковки ответа.");
                }
                break;
            case SCRIPT:
                try {
                    executeScript((String) response.getObject());
                } catch (FileNotFoundException e) {
                    System.err.println("Файл не найден.");
                }
                break;
        }
    }

    public void sendElement(Flat flat, Integer key) throws IOException, ClassNotFoundException {
        Request request = new Request(RequestType.SEND_ITEM, new FlatAndKey(flat, key));
        deliveryHandler.sendRequest(request);
        process(deliveryHandler.receiveResponse());
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
