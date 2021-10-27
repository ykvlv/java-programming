package client;

import common.Request;

import java.io.IOException;

public class Client {
    private final DeliveryHandlerIO deliveryHandlerIO;
    private final InputHelper inputHelper;
    private final String fileName;
    private final RequestHandler requestHandler;

    public Client(DeliveryHandlerIO deliveryHandlerIO, InputHelper inputHelper, String fileName, RequestHandler requestHandler) {
        this.deliveryHandlerIO = deliveryHandlerIO;
        this.requestHandler = requestHandler;
        this.inputHelper = inputHelper;
        this.fileName = fileName;
    }

    public void run() throws IOException, ClassNotFoundException {
        try {
            deliveryHandlerIO.connectTo(fileName);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Подключение к серверу не удалось.");
        }

        String args;
        do {
            System.out.print("% ");
            args = inputHelper.nextLine().trim();
            deliveryHandlerIO.sendCommand(args);
            Request request = deliveryHandlerIO.read();
            requestHandler.process(request, args);
        } while (!args.equals("exit"));
    }
}
