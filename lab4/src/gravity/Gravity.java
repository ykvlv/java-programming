package gravity;

import enums.MineralType;
import enums.ThingType;
import exceptions.InvalidParameterException;
import humans.Human;
import interfaces.IStorage;
import interfaces.IThing;
import things.Mineral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Gravity {
    private final ArrayList<IStorage> storages = new ArrayList<>();
    private boolean gravity;
    private final ArrayList<Human> humans = new ArrayList<>();

    public Gravity(boolean gravity, Human[] humans, IStorage[] storages) {
        if (humans == null || storages == null) {
            throw new InvalidParameterException("Налл в качестве элемента");
        } else {
            for (Human human: humans) {
                if (human == null) {
                    throw new InvalidParameterException("Налл в качестве элемента");
                }
            }
            for (IStorage storage: storages) {
                if (storage == null) {
                    throw new InvalidParameterException("Налл в качестве элемента");
                }
            }
        }
        this.gravity = gravity;
        this.storages.addAll(Arrays.asList(storages));
        this.humans.addAll(Arrays.asList(humans));
    }

    private void changeGravityForHumans(boolean gravity) {

        class HumansToStr {
            private final ArrayList<String> string;

            public HumansToStr() {
                this.string = new ArrayList<>();
                humans.forEach((Human h) -> string.add(h.translation()));
            }

        }

        System.out.print(String.join(", ", new HumansToStr().string));
        System.out.println(gravity ? " FLEW UP" : " FELL DOWN");
    }

    public void checkGravity() {
        for (IStorage storage : storages) {
            boolean moonrock = false, magnet = false;
            for (IThing thing : storage.thingList()) {
                if (thing != null && thing.type() == ThingType.MINERAL) {
                    MineralType type = ((Mineral) thing).mineralType();
                    if (type == MineralType.MOONROCK) {
                        moonrock = true;
                    } else if (type == MineralType.MAGNETICIRONORE) {
                        magnet = true;
                    }
                }
            }
            if (moonrock && magnet) {
                if (gravity) {
                    System.out.println("Внимание: На дворе невесомость");
                    changeGravityForHumans(true);
                }
                gravity = false;
                break;
            } else if ((moonrock || magnet) && !gravity) {
                System.out.println("Внимание: Действует сила тяжести");
                changeGravityForHumans(false);
                gravity = true;
            }
        }
    }

    public boolean getGravity() {
        return gravity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gravity gravity1 = (Gravity) o;
        return gravity == gravity1.gravity && Objects.equals(storages, gravity1.storages) && Objects.equals(humans, gravity1.humans);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storages, gravity, humans);
    }

    @Override
    public String toString() {
        return "Gravity{" +
                "storages=" + storages +
                ", gravity=" + gravity +
                ", humans=" + humans +
                '}';
    }
}
