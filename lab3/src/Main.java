import Gravity.Gravity;
import Humans.Znaika;
import Storages.*;
import Things.*;

public class Main {
    public static void main(String[] args) {
        Znaika znaika = new Znaika(10, Mineral.MOONROCK);
        Cupboard bottomCupboard = new Cupboard(false, Mineral.ROCKCRYSTAL, Mineral.FELDSPAR, Mineral.MICA, Mineral.IRONSTONE, Mineral.COPPERPYRITE, Mineral.SULFUR, Mineral.PYRITE, Mineral.CHALCOPYRITE, Mineral.ZINKBLENDE, Mineral.GALENA, Mineral.MAGNETICIRONORE);
        Cupboard topCupboard = new Cupboard(true, Mineral.DIAMOND, Mineral.APATITE, Mineral.HALITE);
        Ruler ruler = new Ruler();
        Drawer drawer = new Drawer(ruler);
        Gravity znaikaRoomGravity = new Gravity(true, znaika, bottomCupboard, topCupboard, ruler, drawer);
        StorageManager s = new StorageManager(znaikaRoomGravity);

        bottomCupboard.checkDoors();
        bottomCupboard.changeDoors();
        s.give(znaika, Mineral.MOONROCK, bottomCupboard);
        s.take(znaika, Mineral.ROCKCRYSTAL, bottomCupboard);
        s.take(znaika, Mineral.FELDSPAR, bottomCupboard);
        s.take(znaika, Mineral.MICA, bottomCupboard);
        s.take(znaika, Mineral.IRONSTONE, bottomCupboard);
        s.take(znaika, Mineral.COPPERPYRITE, bottomCupboard);
        s.take(znaika, Mineral.SULFUR, bottomCupboard);
        s.take(znaika, Mineral.PYRITE, bottomCupboard);
        s.take(znaika, Mineral.CHALCOPYRITE, bottomCupboard);
        znaika.changeHope(-2);
        s.take(znaika, Mineral.ZINKBLENDE, bottomCupboard);
        s.take(znaika, Mineral.GALENA, bottomCupboard);
        s.take(znaika, Mineral.DIAMOND, topCupboard);
        s.take(znaika, Mineral.APATITE, topCupboard);
        znaika.changeHope(-2);
        s.take(znaika, Mineral.HALITE, topCupboard);
        znaika.changeHope(-5);
        znaika.printHope();
        s.take(znaika, Mineral.MAGNETICIRONORE, bottomCupboard);
        znaika.changeHope(7);

        s.take(znaika, ruler, drawer);
        s.give(znaika, Mineral.MAGNETICIRONORE, ruler);
        s.take(znaika, Mineral.MOONROCK, bottomCupboard);
        s.give(znaika, Mineral.MOONROCK, ruler);

    }
}
