package client;

import common.StringDye;

import java.io.Console;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnknownHostException, SocketException {
        int port = 1305;
        try {
            port = Integer.parseInt(args[0]);
            System.out.printf(StringDye.green("Подключение по порту %s.%n"), port);
        } catch (NumberFormatException e) {
            System.err.println(StringDye.red("Ошибка парсинга порта."));
            return;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.printf(StringDye.yellow("Порт не задан. Подключение по стандартному: %s.%n"), port);
        }

        SocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), port);
        DatagramSocket socket = new DatagramSocket();

        Console console = System.console();
        Scanner scanner = new Scanner(System.in);
        InputHandler inputHandler = new InputHandler(scanner);
        FlatCreator flatCreator = new FlatCreator(inputHandler);
        DeliveryHandler deliveryHandler = new DeliveryHandler(address, socket);
        ResponseHandler responseHandler = new ResponseHandler(flatCreator, deliveryHandler, inputHandler);

        Client client = new Client(deliveryHandler, responseHandler);

        try {
            String password = client.connect(console);
            if (password != null) {
                client.run(inputHandler, client.getLogin(), password);
            } else {
                System.out.println(StringDye.red("Авторизация прервана"));
            }
        } catch (ClassNotFoundException e) {
            System.out.println(StringDye.red("Ошибка распаковки ответа"));
        } catch (IOException e) {
            System.out.println(StringDye.red("Нет ответа сервера"));
        } catch (Exception e) {
            System.out.println(StringDye.red("Вы положили клиент. (oT-T)尸"));
        }
    }
}
