package common;

import java.io.Serializable;
import java.net.SocketAddress;

public class Request implements Serializable {
    private final RequestType requestType;
    private final Serializable object;
    private SocketAddress address;

    public Request(RequestType requestType, Serializable object) {
        this.object = object;
        this.requestType = requestType;
    }

    public RequestType getRequestType() {
        return requestType;
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
