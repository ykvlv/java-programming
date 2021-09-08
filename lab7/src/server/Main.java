package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        //Инициализация коллекции
        FlatHashMap flatHashMap;
        try {
            String fileName = args[0];
            flatHashMap = new FlatHashMap(LocalDateTime.now(), fileName);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Первым аргументом введите название коллекции.");
            return;
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден.");
            return;
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла.");
            return;
        }

        //Инициализация порта
        int port = 1305;
        try {
            port = Integer.parseInt(args[1]);
            System.out.printf("Подключение по порту %s.%n", port);
        } catch (NumberFormatException e) {
            System.err.printf("Ошибка парсинга порта. Подключение по стандартному: %s.%n", port);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.printf("Порт не задан. Подключение по стандартному: %s.%n", port);
        }

        //Запуск сервера
        try {
            SocketAddress address = new InetSocketAddress(port);
            DatagramChannel channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.socket().bind(address);

            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);

            ServerIOHandler serverIOHandler = new ServerIOHandler();
            CommandRegister commandRegister = new CommandRegister(flatHashMap, serverIOHandler);

            CommandExecutor commandExecutor = new CommandExecutor(commandRegister, flatHashMap);

            Server server = new Server(selector, serverIOHandler, commandExecutor, new BufferedReader(new InputStreamReader(System.in)));
            System.out.println("Запуск сервера.");
            server.run();

        } catch (SocketException e) {
            System.out.println("Не удалось подключиться к каналу.");
        } catch (ClosedChannelException e) {
            System.out.println("Канал закрыт для подключения.");
        } catch (IOException e) {
            System.out.println("Произошла ошибка ввода/вывода...");
        } catch (ClassNotFoundException e) {
            System.out.println("Не удалось распаковать класс.");
        }
    }
}
