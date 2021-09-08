package common;

import java.io.Serializable;

public class Response implements Serializable {
    private final ResponseType responseType;
    private final Serializable object;

    public Response(ResponseType responseType, Serializable object) {
        this.responseType = responseType;
        this.object = object;
    }

    public Serializable getObject() {
        return object;
    }

    public ResponseType getResponseType() {
        return responseType;
    }
}
