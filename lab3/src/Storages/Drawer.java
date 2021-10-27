package Storages;

import Things.Thing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Drawer implements Storage {
    private final ArrayList<Thing> things = new ArrayList<>();

    public Drawer(Thing... things) {
        this.things.addAll(Arrays.asList(things));
    }

    @Override
    public String translation() {
        return "Ящик стола";
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
        return things.contains(thing);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drawer drawer = (Drawer) o;
        return Objects.equals(things, drawer.things);
    }

    @Override
    public int hashCode() {
        return Objects.hash(things);
    }

    @Override
    public String toString() {
        return "Drawer{" +
                "things=" + things +
                '}';
    }
}
