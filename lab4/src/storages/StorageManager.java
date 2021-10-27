package storages;

import enums.MineralType;
import enums.ThingType;
import exceptions.CannotBeChangedException;
import gravity.Gravity;
import humans.Human;
import interfaces.IStorage;
import interfaces.IThing;

public class StorageManager {
    private final Gravity subscriber;

    public StorageManager(Gravity gravity) {
        this.subscriber = gravity;
    }

    public void give(Human sender, IThing thing, IStorage receiver) {
        if (!sender.have(thing)) {
            System.out.printf("У %s нету %s%n", sender.translation(), thing.translation());
        } else if (!receiver.opened()) {
            System.out.printf("Доступ к %s ограничен%n", receiver.translation());
        } else if (!sender.opened()) {
            System.out.printf("Доступ к %s ограничен%n", sender.translation());
        } else {
            sender.give(thing);
            receiver.take(thing);
            System.out.printf("%s положил %s в %s%n", sender.translation(), thing.translation(), receiver.translation());
            updateSubscriber(thing);
        }
    }

    public void take(Human receiver, IThing thing, IStorage sender) {
        if (!sender.have(thing)) {
            System.out.printf("У %s нету %s%n", sender.translation(), thing.translation());
        } else if (!receiver.opened()) {
            System.out.printf("Доступ к %s ограничен%n", receiver.translation());
        } else if (!sender.opened()) {
            System.out.printf("Доступ к %s ограничен%n", sender.translation());
        } else {
            sender.give(thing);
            receiver.take(thing);
            System.out.printf("%s достал %s из %s%n", receiver.translation(), thing.translation(), sender.translation());
            updateSubscriber(thing);
        }
    }

    public void takeSomething(Human receiver, IStorage sender) {
        if (sender.isEmpty()) {
            System.out.printf("%s пуст%n", sender.translation());
        } else if (!receiver.opened()) {
            System.out.printf("Доступ к %s ограничен%n", receiver.translation());
        } else if (!sender.opened()) {
            System.out.printf("Доступ к %s ограничен%n", sender.translation());
        } else {
            IThing thing = sender.giveRandom();
            receiver.take(thing);
            System.out.printf("%s достал %s из %s%n", receiver.translation(), thing.translation(), sender.translation());
            updateSubscriber(thing);
        }
    }

    public void open(Human human, IStorage storage) {
        try {
            storage.openClose();
            if (storage.opened()) {
                System.out.printf("%s открыл %s%n", human.translation(), storage.translation());
            } else if (!storage.opened()) {
                System.out.printf("%s закрыл %s%n", human.translation(), storage.translation());
            }
        } catch (CannotBeChangedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateSubscriber(IThing thing) {
        if (thing.type() == ThingType.MINERAL) {
            subscriber.checkGravity();
        }
    }
}
