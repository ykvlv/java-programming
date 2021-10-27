package client;

import common.Request;
import common.RequestType;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.Arrays;

public class DeliveryHandlerIO {
    private final SocketAddress address;
    private final DatagramSocket socket;

    public DeliveryHandlerIO(SocketAddress address, DatagramSocket socket) {
        this.address = address;
        this.socket = socket;
    }

    public Request read() throws IOException, ClassNotFoundException {
        byte[] b = new byte[8192];
        DatagramPacket packet = new DatagramPacket(b, b.length);
        socket.receive(packet);

        ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(b);
        ObjectInputStream objectIS = new ObjectInputStream(byteArrayIS);

        return (Request) objectIS.readObject();
    }

    public void sendCommand(String args) throws IOException {
        Request request = new Request(RequestType.EXECUTION, args);
        sendPacket(serialize(request));
    }

    public void connectTo(String fileName) throws IOException, ClassNotFoundException {
        Request request = new Request(RequestType.CONNECTION, fileName);
        sendPacket(serialize(request));
        RequestType requestType = (RequestType) read().getObject();
        if (requestType == RequestType.BAD) {
            System.out.println("Подключение к серверу не удалось.");
        } else if (requestType == RequestType.COOL) {
            System.out.println("Успешно подключено.");
        } else if (requestType == RequestType.NOPE) {
            System.out.println("Коллекция не обнаружена.");
        }
    }

    public void sendPacket(byte[] bytes) throws IOException {
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address);
        socket.send(packet);
    }

    public byte[] serialize(Serializable object) throws IOException {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        ObjectOutputStream objectOS = new ObjectOutputStream(byteArrayOS);
        objectOS.writeObject(object);
        objectOS.close();
        byteArrayOS.close();
        return byteArrayOS.toByteArray();
    }
}
