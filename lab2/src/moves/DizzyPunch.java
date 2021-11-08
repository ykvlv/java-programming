package moves;

import ru.ifmo.se.pokemon.*;

public class DizzyPunch extends PhysicalMove {
    public DizzyPunch() {
        super(Type.NORMAL, 70, 100);
    }
    @Override
    protected void applyOppDamage(Pokemon def, double damage) {
        def.setMod(Stat.HP, (int) Math.round(damage));
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random() <= 0.2) {
            Effect.confuse(p);
        }
    }
    @Override
    protected java.lang.String describe() {
        return "атакует и имеет вероятность 20% вызвать растерянность у цели";
    }
}