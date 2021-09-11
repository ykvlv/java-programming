package client;

import java.net.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnknownHostException, SocketException {
        int port = 1305;
        try {
            port = Integer.parseInt(args[0]);
            System.out.printf("Подключение по порту %s.%n", port);
        } catch (NumberFormatException e) {
            System.err.printf("Ошибка парсинга порта. Подключение по стандартному: %s.%n", port);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.printf("Порт не задан. Подключение по стандартному: %s.%n", port);
        }

        SocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), port);
        DatagramSocket socket = new DatagramSocket();

        Client client = null;
        Scanner scanner = new Scanner(System.in);
        InputHandler inputHandler = new InputHandler(scanner, false);
        FlatCreator flatCreator = new FlatCreator(inputHandler);
        DeliveryHandler deliveryHandler = new DeliveryHandler(address, socket);
        ResponseHandler responseHandler = new ResponseHandler(flatCreator, deliveryHandler, client);

        client = new Client(deliveryHandler, responseHandler);

        try {
            client.run(inputHandler);
        } catch (Exception e) {
            System.out.println("Вы положили клиент. (oT-T)尸");
            e.printStackTrace();
        }
    }
}
