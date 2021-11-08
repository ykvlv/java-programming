package moves;

import ru.ifmo.se.pokemon.*;

public class StruggleBug extends SpecialMove {
    public StruggleBug() {
        super(Type.BUG, 50, 100);
    }
    @Override
    protected void applyOppDamage(Pokemon def, double damage) {
        def.setMod(Stat.HP, (int) Math.round(damage));
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
            p.setMod(Stat.SPECIAL_ATTACK, -1);
    }
    @Override
    protected java.lang.String describe() {
        return "атакует и понижает специальную атаку цели на одну ступень";
    }
}