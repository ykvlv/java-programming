package Storages;

import Gravity.Gravity;
import Things.*;

public class StorageManager {
    private final Gravity subscriber;

    public StorageManager(Gravity gravity) {
        this.subscriber = gravity;
    }

    public void give(Storage sender, Thing thing, Storage receiver) {
        if (sender.have(thing)) {
            sender.give(thing);
            receiver.take(thing);
            System.out.printf("%s положил %s в %s%n", sender.translation(), thing.translation(), receiver.translation());
            updateSubscriber(thing);
        } else {
            System.out.printf("У %s нету %s или доступ %s ограничен%n", sender.translation(), thing.translation(), sender.translation());
        }
    }

    public void take(Storage receiver, Thing thing, Storage sender) {
        if (sender.have(thing)) {
            sender.give(thing);
            receiver.take(thing);
            System.out.printf("%s достал %s из %s%n", receiver.translation(), thing.translation(), sender.translation());
            updateSubscriber(thing);
        } else {
            System.out.printf("У %s нету %s или доступ %s ограничен%n", sender.translation(), thing.translation(), sender.translation());
        }
    }

    private void updateSubscriber(Thing thing) {
        if (thing == Mineral.MOONROCK || thing == Mineral.MAGNETICIRONORE) {
            subscriber.checkGravity();
        }
    }
}
