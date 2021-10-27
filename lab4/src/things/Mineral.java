package things;

import enums.MineralType;
import enums.ThingType;
import exceptions.InvalidParameterException;
import interfaces.IThing;

public class Mineral implements IThing {
    private final MineralType mineralType;

    public Mineral(MineralType type) {
        if (type == null) {
            throw new InvalidParameterException("Налл в качестве типа");
        } else {
            this.mineralType = type;
        }
    }

    @Override
    public String translation() {
        return mineralType.translation();
    }

    @Override
    public ThingType type() {
        return ThingType.MINERAL;
    }

    public MineralType mineralType() {
        return mineralType;
    }
}
