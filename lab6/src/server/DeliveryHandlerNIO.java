package server;

import common.Request;

import java.io.*;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DeliveryHandlerNIO {
    private final ByteBuffer byteBuffer = ByteBuffer.allocate(8192);

    public Request readFrom(DatagramChannel channel) throws IOException, ClassNotFoundException {
        byteBuffer.clear();
        SocketAddress address = channel.receive(byteBuffer);
        if (address != null) {
            ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(byteBuffer.array());
            ObjectInputStream objectIS = new ObjectInputStream(byteArrayIS);
            Request request = (Request) objectIS.readObject();
            request.setAddress(address);
            objectIS.close();
            byteArrayIS.close();
            return request;
        }
        return null;
    }

    public void writeTo(Request request, DatagramChannel channel, SocketAddress address) throws IOException {
        byteBuffer.clear();
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        ObjectOutputStream objectOS = new ObjectOutputStream(byteArrayOS);
        objectOS.writeObject(request);
        byteBuffer.put(byteArrayOS.toByteArray());
        byteBuffer.flip();
        channel.send(byteBuffer, address);
    }


}
