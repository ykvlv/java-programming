package server;

import common.StringDye;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Инициализация БД
        String heliosUrl = "jdbc:postgresql://pg:5432/studs";
        String localUrl = "jdbc:postgresql://localhost:5432/studs";
        String name, pass;
        Connection connection;
        try {
            Console console = System.console();
            name = console.readLine("Введите имя пользователя БД: ");
            pass = new String(console.readPassword("Введите пароль: "));
            try {
                connection = DriverManager.getConnection(heliosUrl, name, pass);
                System.out.printf(StringDye.green("Подключение к %s успешно.%n"), heliosUrl);
            } catch (SQLException e) {
                System.out.printf(StringDye.yellow("Ошибка %s подключения к %s.%n"), e.getSQLState() , heliosUrl);
                connection = DriverManager.getConnection(localUrl, name, pass);
                System.out.printf(StringDye.green("Подключение к %s успешно.%n"), localUrl);
            }
        } catch (NullPointerException e) {
            System.out.println(StringDye.red("Не удалось достучаться до консоли."));
            return;
        } catch (SQLException e) {
            System.out.printf(StringDye.red("Ошибка %s подключения к %s.%n"), e.getSQLState() , localUrl);
            return;
        }

        //Инициализация коллекции
        FlatHashMap flatHashMap;
        try {
            flatHashMap = new FlatHashMap(LocalDateTime.now(), connection);
        } catch (SQLException e) {
            System.out.printf(StringDye.red("Ошибка %s инициализации коллекции.%n"), e.getSQLState());
            return;
        }

        //Инициализация порта
        int port = 1305;
        try {
            port = Integer.parseInt(args[0]);
            System.out.printf(StringDye.green("Подключение по порту %s.%n"), port);
        } catch (NumberFormatException e) {
            System.out.println(StringDye.red("Ошибка парсинга порта."));
            return;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.printf(StringDye.yellow("Порт не задан. Подключение по стандартному: %s.%n"), port);
        }

        //Запуск сервера
        try {
            SocketAddress address = new InetSocketAddress(port);
            DatagramChannel channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.socket().bind(address);

            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            ServerIOHandler serverIOHandler = new ServerIOHandler();
            CommandRegister commandRegister = new CommandRegister(flatHashMap);
            CommandExecutor commandExecutor = new CommandExecutor(commandRegister, flatHashMap, connection);

            Server server = new Server(selector, serverIOHandler, commandExecutor, reader);
            System.out.println("Запуск сервера.");
            server.run();

        } catch (SocketException e) {
            System.out.println(StringDye.red("Не удалось подключиться к каналу."));
        } catch (ClosedChannelException e) {
            System.out.println(StringDye.red("Канал закрыт для подключения."));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(StringDye.red("Произошла ошибка ввода/вывода..."));
        } catch (ClassNotFoundException e) {
            System.out.println(StringDye.red("Не удалось распаковать класс."));
        }
    }
}
