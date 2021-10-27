package Storages;

import Things.Thing;

public interface Storage {
    void give(Thing thing);
    void take(Thing thing);
    boolean have(Thing thing);
    String translation();
}
