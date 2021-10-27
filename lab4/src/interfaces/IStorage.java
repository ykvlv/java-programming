package interfaces;

import exceptions.CannotBeChangedException;

import java.util.ArrayList;

public interface IStorage {
    void give(IThing thing);
    void take(IThing thing);
    boolean have(IThing thing);
    String translation();
    boolean opened();
    void openClose() throws CannotBeChangedException;
    boolean isEmpty();
    IThing giveRandom();
    ArrayList<IThing> thingList();
}
