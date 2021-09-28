package common;

import java.io.Serializable;

public class Response implements Serializable {
    private final ResponseType responseType;
    private final Serializable object;
    private final Serializable extra;

    public Response(ResponseType responseType, Serializable object) {
        this.responseType = responseType;
        this.object = object;
        this.extra = null;
    }

    public Response(ResponseType responseType, Serializable object, Serializable extra) {
        this.responseType = responseType;
        this.extra = extra;
        this.object = object;
    }

    public Serializable getObject() {
        return object;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public Serializable getExtra() {
        return extra;
    }
}
