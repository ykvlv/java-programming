package client;

import common.Request;
import common.RequestType;
import common.Response;
import common.StringDye;
import common.forFlat.Flat;

import java.io.*;
import java.util.Scanner;

public class ResponseHandler {
    private final FlatCreator flatCreator;
    private final DeliveryHandler deliveryHandler;
    private final InputHandler inputHandler;

    public ResponseHandler(FlatCreator flatCreator, DeliveryHandler deliveryHandler, InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        this.deliveryHandler = deliveryHandler;
        this.flatCreator = flatCreator;
    }

    public void process(Response response, String login, String password) {
        switch (response.getResponseType()) {
            case DONE:
                System.out.println((String) response.getObject());
                break;
            case ERROR:
                System.out.println(StringDye.yellow((String) response.getObject()));
                break;
            case REQUEST_ITEM:
                try {
                    Flat flat = flatCreator.createStandardFlat();
                    sendElement(flat, (Integer) response.getObject(), login, password);
                } catch (InterruptedIOException e) {
                    System.out.println(StringDye.yellow("Создание элемента коллекции прервано."));
                } catch (IOException e) {
                    System.out.println(StringDye.yellow("Ошибка отправки элемента коллекции."));
                } catch (ClassNotFoundException e) {
                    System.out.println(StringDye.yellow("Ошибка распаковки ответа."));
                }
                break;
            case SCRIPT:
                try {
                    executeScript((String) response.getObject());
                } catch (FileNotFoundException e) {
                    System.out.println(StringDye.yellow("Файл не найден."));
                }
                break;
        }
    }

    private void sendElement(Flat flat, Integer key, String login, String password) throws IOException, ClassNotFoundException {
        Request request = new Request(RequestType.SEND_ITEM, login, password, flat, key);
        deliveryHandler.sendRequest(request);
        process(deliveryHandler.receiveResponse(), login, password);
    }

    private void executeScript(String fileName) throws FileNotFoundException {
        Scanner scriptScanner = new Scanner(new InputStreamReader(new FileInputStream(fileName)));
        if (inputHandler.getScannersNames().contains(fileName)) {
            System.err.printf("Во избежание рекурсии %s запущен не будет%n", fileName);
            return;
        }
        inputHandler.scriptFrom(scriptScanner, fileName);
    }
}
