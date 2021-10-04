package server;

import common.Request;
import common.Response;
import common.ResponseType;
import common.StringDye;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Server {
    private final Selector selector;
    private final ServerIOHandler serverIOHandler;
    private final CommandExecutor commandExecutor;
    private final BufferedReader reader;
    private final ExecutorService fixedThreadPool;

    public Server(Selector selector, ServerIOHandler serverIOHandler, CommandExecutor commandExecutor, BufferedReader reader, ExecutorService fixedThreadPool) {
        this.fixedThreadPool = fixedThreadPool;
        this.selector = selector;
        this.reader = reader;
        this.serverIOHandler = serverIOHandler;
        this.commandExecutor = commandExecutor;
    }

    public boolean isExit() throws IOException {
        while (reader.ready()) {
            String str = reader.readLine().trim();
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
                        Request request = readRequest((DatagramChannel) key.channel());
                        key.attach(request);
                        key.interestOps(SelectionKey.OP_WRITE);

                    } else if (key.isWritable()) {
                        //Выполнить и отправить запрос закрепленный за ключом
                        Request request = (Request) key.attachment();

                        Response response = executeRequest(request);

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

    public Response executeRequest(Request request) {
        Response response = null;
        try {
            response = fixedThreadPool.submit(() -> commandExecutor.execute(request)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.print(response != null ? response.getResponseType() + "\n" : "");
        return response;
    }

    public Request readRequest(DatagramChannel channel) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new RecursiveTask<Request>() {
            @Override
            protected Request compute() {
                try {
                    return serverIOHandler.readFrom(channel);
                } catch (IOException | ClassNotFoundException e) {
                    return null;
                }
            }
        });
    }

    public void sendResponse(Response response, DatagramChannel channel, SocketAddress address) {
        if (response == null) {
            response = new Response(ResponseType.ERROR, "Сегодня ты без ответа");
        }
        Response finalResponse = response;
        new Thread(() -> {
            try {
                serverIOHandler.writeTo(finalResponse, channel, address);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(StringDye.yellow("Не удалось отправить ответ"));
            }
        }).start();
    }
}
