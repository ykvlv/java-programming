package server;

import common.Request;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Server {
    private final Selector selector;
    private final DeliveryHandlerNIO deliveryHandlerNIO;
    private final RequestExecutor requestExecutor;
    private final BufferedReader reader;

    public Server(Selector selector, DeliveryHandlerNIO deliveryHandlerNIO, RequestExecutor requestExecutor, BufferedReader reader) {
        this.selector = selector;
        this.reader = reader;
        this.deliveryHandlerNIO = deliveryHandlerNIO;
        this.requestExecutor = requestExecutor;
    }

    public void run() throws Exception {



        while (true) {
            if (selector.select(1000) == 0) {
                if (reader.ready()) {
                    String str = reader.readLine();
                    if (str.equals("save")) {
                        requestExecutor.saveAll();
                        return;
                    } else if (str.equals("exit")) {
                        return;
                    }
                }
            }
            Set<SelectionKey> keys = selector.selectedKeys();
            for (Iterator<SelectionKey> it = keys.iterator(); it.hasNext();) {
                SelectionKey key = it.next();
                if (key.isValid()) {
                    if (key.isReadable()) {
                        System.out.println("READ");
                        Request request = deliveryHandlerNIO.readFrom((DatagramChannel) key.channel());
                        key.attach(request);
                        key.interestOps(SelectionKey.OP_WRITE);
                    } else if (key.isWritable()) {
                        System.out.println("WRITE");
                        Request request = (Request) key.attachment();
                        Request requestButResponse = requestExecutor.execute(request);

                        deliveryHandlerNIO.writeTo(requestButResponse, (DatagramChannel) key.channel(), request.getAddress());
                        key.interestOps(SelectionKey.OP_READ);
                    }
                } else {
                    System.out.println("key is not valid. (oT-T)尸");
                }
                it.remove();
            }
        }
    }
}
