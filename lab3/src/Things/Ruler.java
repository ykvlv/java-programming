package Things;

import Storages.Storage;
import java.util.Objects;

public class Ruler implements Thing, Storage {
    private Thing left = null;
    private Thing right = null;

    @Override
    public void give(Thing thing) {
        if (left == thing) {
            left = null;
        } else {
            right = null;
        }
    }

    @Override
    public void take(Thing thing) {
        right = left;
        left = thing;
    }

    @Override
    public boolean have(Thing thing) {
        return left == thing || right == thing;
    }

    @Override
    public String translation() {
        return "Линейка";
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
