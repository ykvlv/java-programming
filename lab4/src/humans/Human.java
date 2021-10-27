package humans;

import exceptions.*;
import interfaces.IStorage;
import interfaces.IThing;

import java.util.ArrayList;
import java.util.Random;

public abstract class Human implements IStorage {
    private final Characteristics ch = new Characteristics();
    private int hope;
    private final ArrayList<IThing> things = new ArrayList<>();

    public Human(int hope) {
        if (hope >= ch.minHope && hope <= ch.maxHope) {
            this.hope = hope;
        } else {
            throw new InvalidParameterException("Надежда должна быть в промежутке [" + ch.minHope + ", " + ch.maxHope + "]");
        }
    }

    public Human(int hope, IThing... things) {
        if (hope >= ch.minHope && hope <= ch.maxHope) {
            this.hope = hope;
        } else {
            throw new InvalidParameterException("Надежда должна быть в промежутке [" + ch.minHope + ", " + ch.maxHope + "]");
        }
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

    @Override
    public ArrayList<IThing> thingList() {
        return things;
    }

    @Override
    public String translation() {
        return "Человек";
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
    public boolean opened() {
        return true;
    }

    @Override
    public void openClose() throws CannotBeChangedException {
        throw new CannotBeChangedException(translation() + " нельзя " + (opened() ? "закрыть" : "открыть") + " для обмена");
    }

    @Override
    public boolean have(IThing thing) {
        return things.contains(thing);
    }

    @Override
    public IThing giveRandom() {
        return things.remove(new Random().nextInt(things.size()));
    }

    @Override
    public boolean isEmpty() {
        return things.isEmpty();
    }

    public void changeHope(int hope) {
        if (this.hope + hope >= ch.minHope && this.hope + hope <= ch.maxHope) {
            this.hope += hope;
        }
    }

    public void printHope() {
        System.out.printf("Уровень надежды %s %d/10%n", this.translation(), hope);
    }

    public int getHope() {
        return hope;
    }

    public void say(String str) {
        System.out.printf("%s говорит: %s%n", translation(), str);
    }

    public static class Characteristics {
        public int iq = 100;
        public int maxHope = 20;
        public int minHope = 0;

        public Characteristics() {}
    }
}
