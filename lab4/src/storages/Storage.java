package storages;

import exceptions.InvalidParameterException;
import interfaces.IStorage;
import interfaces.IThing;

import java.util.ArrayList;
import java.util.Random;

public abstract class Storage implements IStorage {
    private boolean open = true;
    private final ArrayList<IThing> things = new ArrayList<>();

    public Storage(IThing... things) {
        if (things == null) {
            throw new InvalidParameterException("Налл в качестве элемента");
        }
        for (IThing thing : things) {
            if (thing == null) {
                throw new InvalidParameterException("Налл в качестве элемента");
            } else {
                this.things.add(thing);
            }
        }
    }

    public Storage(boolean open, IThing... things) {
        this.open = open;
        for (IThing thing : things) {
            if (thing == null) {
                throw new InvalidParameterException("Налл в качестве элемента");
            } else {
                this.things.add(thing);
            }
        }
    }
    @Override
    public String translation() {
        return "Хранилище";
    }

    @Override
    public boolean opened() {
        return open;
    }

    @Override
    public void openClose() {
        open = !open;
    }

    @Override
    public boolean isEmpty() {
        return things.isEmpty();
    }

    @Override
    public void give(IThing thing) {
        things.remove(thing);
    }

    @Override
    public void take(IThing thing) {
        things.add(thing);
    }

    @Override
    public IThing giveRandom() {
        return things.remove(new Random().nextInt(things.size()));
    }

    @Override
    public ArrayList<IThing> thingList() {
        return things;
    }

    @Override
    public boolean have(IThing thing) {
        return things.contains(thing);
    }
}
