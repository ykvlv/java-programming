package Gravity;

import Storages.*;
import Things.*;

import java.util.Arrays;
import java.util.Objects;

public class Gravity {
    private final Storage[] storages;
    private boolean gravity;

    public Gravity(boolean gravity, Storage... storages) {
        this.gravity = gravity;
        this.storages = storages;

    }

    public void checkGravity() {
        for (Storage storage : storages) {
            if (storage.have(Mineral.MOONROCK) && storage.have(Mineral.MAGNETICIRONORE)) {
                if (gravity) {
                    System.out.println("Внимание: На дворе невесомость");
                }
                gravity = false;
                break;
            } else if (storage.have(Mineral.MOONROCK) || storage.have(Mineral.MAGNETICIRONORE)) {
                if (!gravity) {
                    System.out.println("Внимание: Действует сила тяжести");
                }
                gravity = true;
                break;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gravity gravity1 = (Gravity) o;
        return gravity == gravity1.gravity && Arrays.equals(storages, gravity1.storages);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(gravity);
        result = 31 * result + Arrays.hashCode(storages);
        return result;
    }

    @Override
    public String toString() {
        return "Gravity{" +
                "storages=" + Arrays.toString(storages) +
                ", gravity=" + gravity +
                '}';
    }
}
