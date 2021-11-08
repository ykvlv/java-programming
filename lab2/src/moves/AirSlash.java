package moves;

import ru.ifmo.se.pokemon.*;

public class AirSlash extends SpecialMove {
    public AirSlash() {
        super(Type.FLYING, 75, 95);
    }
    @Override
    protected void applyOppDamage(Pokemon def, double damage) {
        def.setMod(Stat.HP, (int) Math.round(damage));
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random() <= 0.3) {
            Effect.flinch(p);
        }
    }
    @Override
    protected java.lang.String describe() {
        return "атакует и имеет вероятность 30% заставить цель вздрогнуть";
    }
}