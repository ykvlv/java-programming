package common;

import java.io.Serializable;
import java.net.SocketAddress;

public class Request implements Serializable {
    private final RequestType requestType;
    private final Serializable object;
    private final Serializable extra;
    private final String login;
    private SocketAddress address;

    public Request(RequestType requestType, Serializable object, String login) {
        this.login = login;
        this.object = object;
        this.requestType = requestType;
        this.extra = null;

    }

    public Request(RequestType requestType, Serializable object, Serializable extra, String login) {
        this.login = login;
        this.object = object;
        this.requestType = requestType;
        this.extra = extra;
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

    public Serializable getExtra() {
        return extra;
    }

    public String getLogin() {
        return login;
    }
}
