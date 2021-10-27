package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        int port = 1305;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка парсинга порта. Подключение по стандартному.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Порт не задан. Подключение по стандартному.");
        }
        System.out.printf("Выбран порт %s.%n", port);

        SocketAddress address = new InetSocketAddress(port);
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.socket().bind(address);

        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_READ);


        DeliveryHandlerNIO deliveryHandlerNIO = new DeliveryHandlerNIO();
        RequestExecutor requestExecutor = new RequestExecutor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Server server = new Server(selector, deliveryHandlerNIO, requestExecutor, reader);

        try {
            server.run();
        } catch (Exception e) {
            System.out.println("Сервер упал. Шо ты наделал...");
            e.printStackTrace();
        }
    }
}
