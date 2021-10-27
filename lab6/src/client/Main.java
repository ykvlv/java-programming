package client;

import java.net.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnknownHostException, SocketException {
        int port = 1305;
        String fileName;
        try {
            fileName = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Первым аргументом введите название коллекции.");
            return;
        }
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка парсинга порта. Подключение по стандартному.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Порт не задан. Подключение по стандартному.");
        }
        System.out.printf("Подключение к коллекции %s по порту %s%n", fileName, port);

        SocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), port);
        DatagramSocket socket = new DatagramSocket();

        Scanner scanner = new Scanner(System.in);
        InputHelper inputHelper = new InputHelper(scanner);

        FlatCreator flatCreator = new FlatCreator(inputHelper);
        DeliveryHandlerIO deliveryHandlerIO = new DeliveryHandlerIO(address, socket);
        RequestHandler requestHandler = new RequestHandler(deliveryHandlerIO, flatCreator, inputHelper);
        Client client = new Client(deliveryHandlerIO, inputHelper, fileName, requestHandler);

        try {
            client.run();
        } catch (Exception e) {
            System.out.println("Вы положили клиент. (oT-T)尸");
            e.printStackTrace();
        }
    }
}
