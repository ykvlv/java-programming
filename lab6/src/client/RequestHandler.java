package client;

import common.Request;
import common.RequestType;
import common.forFlat.Flat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;

public class RequestHandler {
    private final DeliveryHandlerIO deliveryHandlerIO;
    private final FlatCreator flatCreator;
    private final HashSet<String> nowExecuting = new HashSet<>();
    private final InputHelper inputHelper;

    public RequestHandler(DeliveryHandlerIO deliveryHandlerIO, FlatCreator flatCreator, InputHelper inputHelper) {
        this.deliveryHandlerIO = deliveryHandlerIO;
        this.inputHelper = inputHelper;
        this.flatCreator = flatCreator;
    }

    public void process(Request request, String string) throws IOException, ClassNotFoundException {
        if (request.getType() == RequestType.SCRIPT) {
            executeScript(string.split(" ")[1]);
        } else if (request.getType() == RequestType.ADDITEM) {
            add(string, (String) request.getObject());
        } else if (request.getType() == RequestType.EXECUTION) {
            System.out.println((String) request.getObject());
        } else if (request.getType() == RequestType.BAD) {
            System.out.println("Соединение потеряно. Введите название коллекции:");
            deliveryHandlerIO.connectTo(new Scanner(System.in).nextLine());
        }
    }

    public void add(String string, String request) throws IOException, ClassNotFoundException {
        System.out.println(request);
        Flat flat = flatCreator.createStandardFlat(string.split(" ")[1]);
        deliveryHandlerIO.sendPacket(deliveryHandlerIO.serialize(new Request(RequestType.ADDITEM, flat)));
        Request requestButResponse = deliveryHandlerIO.read();
        System.out.println((String) requestButResponse.getObject());
    }

    public void executeScript(String fileName) {
        /// СКРИПТ ААААА
        try {
            File file = new File(fileName);
            Scanner scriptScanner = new Scanner(new InputStreamReader(new FileInputStream(file)));
            if (nowExecuting.contains(fileName)) {
                System.out.printf("Во избежание рекурсии %s запущен не будет%n", fileName);
                return;
            }
            nowExecuting.add(fileName);
            inputHelper.setScriptMode(scriptScanner);
            String line;
            while (inputHelper.hasNext()) {
                line = inputHelper.nextLine().trim();
                deliveryHandlerIO.sendCommand(line);
                Request request = deliveryHandlerIO.read();
                process(request, line);
            }
            nowExecuting.remove(fileName);
            inputHelper.endScriptMode();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка с чтением файла");
        }
    }
}
