package Humans;

import Things.Thing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Znaika extends Human {
    private final ArrayList<Thing> things = new ArrayList<>();

    public Znaika(int hope, Thing... things) {
        super(hope);
        this.things.addAll(Arrays.asList(things));
    }

    @Override
    public String translation() {
        return "Знайка";
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
        Znaika znaika = (Znaika) o;
        return Objects.equals(things, znaika.things) && Objects.equals(getHope(), znaika.getHope());
    }

    @Override
    public int hashCode() {
        return Objects.hash(things, getHope());
    }

    @Override
    public String toString() {
        return "Znaika{" +
                "hope=" + getHope() +
                ", things=" + things +
                '}';
    }
}
