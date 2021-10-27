package storages;

import interfaces.IThing;

import java.util.Objects;

public class Drawer extends Storage {
    public Drawer(IThing... things) {
        super(things);
    }

    @Override
    public String translation() {
        return "Ящик стола";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drawer drawer = (Drawer) o;
        return Objects.equals(this.thingList(), drawer.thingList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.thingList());
    }

    @Override
    public String toString() {
        return "Drawer{" +
                "things=" + this.thingList() +
                '}';
    }
}
