package thingsAndStorages;

import enums.ThingType;
import exceptions.CannotBeChangedException;
import interfaces.*;

import java.util.*;

public class Ruler implements IThing, IStorage {
    private IThing left = null;
    private IThing right = null;

    @Override
    public void give(IThing thing) {
        if (left == thing) {
            left = null;
        } else {
            right = null;
        }
    }

    @Override
    public void take(IThing thing) {
        right = left;
        left = thing;
    }

    @Override
    public IThing giveRandom() {
        boolean b = new Random().nextBoolean();
        IThing thing = b ? left : right;
        if (b) {
            left = null;
        } else {
            right = null;
        }
        return thing;
    }

    @Override
    public ArrayList<IThing> thingList() {
        ArrayList<IThing> a = new ArrayList<>();
        a.add(left);
        a.add(right);
        return a;
    }

    @Override
    public boolean have(IThing thing) {
        return left == thing || right == thing;
    }

    @Override
    public String translation() {
        return "Линейка";
    }

    @Override
    public ThingType type() {
        return ThingType.EDUCATIONAL;
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
    public boolean isEmpty() {
        return left != null && right != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ruler ruler = (Ruler) o;
        return Objects.equals(left, ruler.left) && Objects.equals(right, ruler.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return "Ruler{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
