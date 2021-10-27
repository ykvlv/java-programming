package Storages;

import Things.Thing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Cupboard implements Storage {
    private final ArrayList<Thing> things = new ArrayList<>();
    private boolean doors = false;

    public Cupboard( boolean doors, Thing... things) {
        this.things.addAll(Arrays.asList(things));
        this.doors = doors;
    }

    public void changeDoors() {
        doors = !doors;
        System.out.println(doors ? "Дверцы шкафчика открылись" : "Дверцы шкафчика закрылись");
    }

    public void checkDoors() {
        System.out.println(doors ? "Дверцы шкафчика открыты" : "Дверцы шкафчика закрыты");
    }

    public boolean getDoors() {
        return doors;
    }

    @Override
    public String translation() {
        return "Шкаф";
    }

    @Override
    public void give(Thing thing) {
        things.remove(thing);
    }

    @Override
    public void take(Thing thing) {
        things.add(thing);
    }

    @Override
    public boolean have(Thing thing) {
        return doors && things.contains(thing);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cupboard cupboard = (Cupboard) o;
        return Objects.equals(things, cupboard.things);
    }

    @Override
    public int hashCode() {
        return Objects.hash(things);
    }

    @Override
    public String toString() {
        return "Cupboard{" +
                "doors=" + doors +
                ", things=" + things +
                '}';
    }
}
