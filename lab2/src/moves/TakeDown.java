package moves;

import ru.ifmo.se.pokemon.*;

public class TakeDown extends PhysicalMove {
    public TakeDown() {
        super(Type.NORMAL, 90, 85);
    }
    @Override
    protected void applyOppDamage(Pokemon def, double damage) {
        def.setMod(Stat.HP, (int) Math.round(damage));
    }
}
