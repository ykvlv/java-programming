package server;

import common.Request;
import common.RequestType;
import common.Response;
import common.StringDye;

import java.io.BufferedReader;
import java.io.IOException;
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

    public void run() throws IOException, ClassNotFoundException {
        while (true) {
            if (selector.select(1000) == 0) {
                //Чекер комманд на сервере
                if (reader.ready()) {
                    String str = reader.readLine();
                    switch (str) {
                        case "save":
                            commandExecutor.saveAll();
                            break;
                        case "exit":
                            selector.close();
                            reader.close();
                            return;
                        default:
                            System.out.println(StringDye.yellow("Есть только save и exit"));
                    }
                }
                continue;
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
                        //Выполнить запрос закрепленный за ключом
                        Request request = (Request) key.attachment();
                        Response response = commandExecutor.execute(request);
                        System.out.println(response.getResponseType());
                        serverIOHandler.writeTo(response, (DatagramChannel) key.channel(), request.getAddress());
                        key.interestOps(SelectionKey.OP_READ);
                    }
                } else {
                    System.out.println(StringDye.yellow("key is not valid. (oT-T)尸"));
                }
                it.remove();
            }
        }
    }
}
