package actions;

import exceptions.InvalidParameterException;
import gravity.Gravity;
import humans.Human;
import interfaces.Action;
import interfaces.IStorage;
import interfaces.IThing;
import storages.StorageManager;

public class HumanTakesStones implements Action {
    StorageManager s;
    Gravity g;
    Human human;
    IStorage[] storages;
    public HumanTakesStones(StorageManager s, Gravity g, Human human, IStorage... storages) {
        if (s == null) {
            throw new InvalidParameterException("Налл в качестве элемента");
        } else if (g == null) {
            throw new InvalidParameterException("Налл в качестве элемента");
        } else if (human == null) {
            throw new InvalidParameterException("Налл в качестве элемента");
        }
        for (IStorage storage: storages) {
            if (storage == null) {
                throw new InvalidParameterException("Налл в качестве элемента");
            }
            for (IThing thing : storage.thingList()) {
                if (thing == null) {
                    throw new InvalidParameterException("Налл в качестве элемента");
                }
            }
        }
        this.s = s;
        this.g = g;
        this.human = human;
        this.storages = storages;
    }

    @Override
    public void runAction() {
        for (IStorage storage: storages) {
            while (!storage.isEmpty()) {
                s.takeSomething(human, storage);
                if (g.getGravity()) {
                    System.out.println("Ах вот кто ты!");
                    break;
                } else {
                    human.changeHope(-1);
                }
            }
        }
    }
}
