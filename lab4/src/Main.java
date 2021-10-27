import actions.HumanTakesStones;
import enums.MineralType;
import gravity.Gravity;
import humans.*;
import interfaces.IStorage;
import interfaces.IThing;
import storages.*;
import things.Mineral;
import thingsAndStorages.*;

public class Main {
    public static void main(String[] args) {
        IThing moonRock = new Mineral(MineralType.MOONROCK);
        IThing magneticIronOre = new Mineral(MineralType.MAGNETICIRONORE);
        Human znaika = new Znaika(20, moonRock);
        Human neznaika = new Neznaika(7);
        IStorage bottomCupboard = new Cupboard(true, new Mineral(MineralType.ROCKCRYSTAL), new Mineral(MineralType.FELDSPAR), new Mineral(MineralType.MICA), new Mineral(MineralType.IRONSTONE), new Mineral(MineralType.COPPERPYRITE), new Mineral(MineralType.SULFUR), new Mineral(MineralType.PYRITE), new Mineral(MineralType.CHALCOPYRITE), new Mineral(MineralType.ZINKBLENDE), new Mineral(MineralType.GALENA), magneticIronOre);
        IStorage topCupboard = new Cupboard(true, new Mineral(MineralType.DIAMOND), new Mineral(MineralType.APATITE), new Mineral(MineralType.HALITE));
        Ruler ruler = new Ruler();
        IStorage drawer = new Drawer(ruler);
        Gravity znaikaRoomGravity = new Gravity(true, new Human[]{znaika, neznaika}, new IStorage[]{bottomCupboard, topCupboard, ruler, drawer});
        StorageManager s = new StorageManager(znaikaRoomGravity);

        znaika.say("Надо доставать из шкафчика все хранящиеся минералы. Как только будет удалено существо, с которым взаимодействует лунит, невесомость исчезнет, и мы узнаем, что это за вещество");
        s.give(znaika, moonRock, bottomCupboard);
        HumanTakesStones znaikaGetsStones = new HumanTakesStones(s, znaikaRoomGravity, znaika, topCupboard, bottomCupboard);
        znaikaGetsStones.runAction();

        s.take(znaika, ruler, drawer);
        s.give(znaika, magneticIronOre, ruler);
        s.take(znaika, moonRock, bottomCupboard);
        s.give(znaika, moonRock, ruler);
        znaika.say("Нормально так жмыхнуло.");
        neznaika.say("Еще бы");
    }
}
