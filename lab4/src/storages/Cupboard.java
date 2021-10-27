package storages;

import interfaces.IThing;

import java.util.Objects;

public class Cupboard extends Storage {
    public Cupboard(boolean open, IThing... things) {
        super(open, things);
    }

    @Override
    public String translation() {
        return "Шкаф";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cupboard cupboard = (Cupboard) o;
        return Objects.equals(this.thingList(), cupboard.thingList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.thingList());
    }

    @Override
    public String toString() {
        return "Cupboard{" +
                ", things=" + this.thingList() +
                '}';
    }
}
