package server;

import common.Request;
import common.Response;
import common.StringDye;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class Server {
    private final Selector selector;
    private final ServerIOHandler serverIOHandler;
    private final CommandExecutor commandExecutor;
    private final BufferedReader reader;

    public Server(Selector selector, ServerIOHandler serverIOHandler, CommandExecutor commandExecutor, BufferedReader reader) {
        this.selector = selector;
        this.reader = reader;
        this.serverIOHandler = serverIOHandler;
        this.commandExecutor = commandExecutor;
    }

    public boolean isExit() throws IOException {
        while (reader.ready()) {
            String str = reader.readLine();
            if ("exit".equals(str)) {
                selector.close();
                reader.close();
                return true;
            }
        }
        return false;
    }

    public void run() throws IOException, ClassNotFoundException {
        while (true) {
            if (selector.select(1000) == 0) {
                //Чекер комманд на сервере
                if(isExit()) {
                    return;
                }
            }

            Set<SelectionKey> keys = selector.selectedKeys();
            for (Iterator<SelectionKey> it = keys.iterator(); it.hasNext();) {
                SelectionKey key = it.next();
                if (key.isValid()) {
                    if (key.isReadable()) {
                        //Прочитать канал и достать запрос
                        Request request = serverIOHandler.readFrom((DatagramChannel) key.channel());
                        key.attach(request);
                        key.interestOps(SelectionKey.OP_WRITE);

                    } else if (key.isWritable()) {
                        //Выполнить и отправить запрос закрепленный за ключом
                        Request request = (Request) key.attachment();
                        Response response = commandExecutor.execute(request);
                        System.out.println(response.getResponseType());

                        sendResponse(response, (DatagramChannel) key.channel(), request.getAddress());
                        key.interestOps(SelectionKey.OP_READ);
                    }
                } else {
                    System.out.println(StringDye.yellow("key is not valid. (oT-T)尸"));
                }
                it.remove();
            }
        }
    }

    public void sendResponse(Response response, DatagramChannel channel, SocketAddress address) {
        new Thread(() -> {
            try {
                serverIOHandler.writeTo(response, channel, address);
            } catch (IOException e) {
                System.out.println(StringDye.yellow("Не удалось отправить ответ"));
            }
        }).start();
    }
}
