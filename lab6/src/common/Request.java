package common;

import java.io.Serializable;
import java.net.SocketAddress;

public class Request implements Serializable {
    private final RequestType type;
    private final Serializable object;
    private SocketAddress address;

    public Request(RequestType type, Serializable object) {
        this.type = type;
        this.object = object;
    }

    public RequestType getType() {
        return type;
    }

    public Serializable getObject() {
        return object;
    }

    public void setAddress(SocketAddress address) {
        this.address = address;
    }

    public SocketAddress getAddress() {
        return address;
    }
}
