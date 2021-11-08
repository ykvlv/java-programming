package client;

import common.Request;
import common.Response;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

public class DeliveryHandler {
    private final SocketAddress address;
    private final DatagramSocket socket;

    public DeliveryHandler(SocketAddress address, DatagramSocket socket) {
        this.address = address;
        this.socket = socket;
    }

    public Response receiveResponse() throws IOException, ClassNotFoundException {
        byte[] b = new byte[8192];
        DatagramPacket packet = new DatagramPacket(b, b.length);
        socket.setSoTimeout(2000);
        socket.receive(packet);
        ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(b);
        ObjectInputStream objectIS = new ObjectInputStream(byteArrayIS);
        byteArrayIS.close();
        objectIS.close();

        return (Response) objectIS.readObject();
    }

    public void sendRequest(Request request) throws IOException {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        ObjectOutputStream objectOS = new ObjectOutputStream(byteArrayOS);
        objectOS.writeObject(request);
        objectOS.close();
        byteArrayOS.close();

        DatagramPacket packet = new DatagramPacket(byteArrayOS.toByteArray(), byteArrayOS.size(), address);
        socket.send(packet);
    }
}
