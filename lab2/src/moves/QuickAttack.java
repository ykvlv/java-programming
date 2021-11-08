package moves;

import ru.ifmo.se.pokemon.*;

public class QuickAttack extends PhysicalMove {
    public QuickAttack() {
        super(Type.NORMAL, 40, 100, 1, 1);
    }
    @Override
    protected void applyOppDamage(Pokemon def, double damage) {
        def.setMod(Stat.HP, (int) Math.round(damage));
    }
}