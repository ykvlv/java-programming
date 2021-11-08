package server;

import common.Request;
import common.RequestType;
import common.Response;

import java.io.*;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ServerIOHandler {
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

    public void writeTo(Response response, DatagramChannel channel, SocketAddress address) throws IOException {
        byteBuffer.clear();
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        ObjectOutputStream objectOS = new ObjectOutputStream(byteArrayOS);
        objectOS.writeObject(response);
        byteBuffer.put(byteArrayOS.toByteArray());
        byteBuffer.flip();
        channel.send(byteBuffer, address);
    }


}
