package common;

import java.io.Serializable;

public enum ResponseType implements Serializable {
    DONE,
    REQUEST_ITEM,
    ERROR,
    SCRIPT,
    CONNECT
}
