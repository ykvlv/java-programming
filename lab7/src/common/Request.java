package common;

import java.io.Serializable;
import java.net.SocketAddress;

public class Request implements Serializable {
    private final RequestType requestType;
    private Serializable object;
    private Serializable extra;
    private final String login;
    private String password;
    private SocketAddress address;

    public Request(RequestType requestType, String login) {
        this.login = login;
        this.requestType = requestType;
    }

    public Request(RequestType requestType, String login, String password) {
        this.login = login;
        this.password = password;
        this.requestType = requestType;
    }

    public Request(RequestType requestType, String login, String password, Serializable object) {
        this.password = password;
        this.login = login;
        this.object = object;
        this.requestType = requestType;
    }

    public Request(RequestType requestType, String login, String password, Serializable object, Serializable extra) {
        this.password = password;
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

    public String getPassword() {
        return password;
    }
}
