package moves;

import ru.ifmo.se.pokemon.*;

public class FlashCannon extends SpecialMove {
    public FlashCannon() {
        super(Type.STEEL, 80, 100);
    }
    @Override
    protected void applyOppDamage(Pokemon def, double damage) {
        def.setMod(Stat.HP, (int) Math.round(damage));
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random() <= 0.1) {
            p.setMod(Stat.SPECIAL_DEFENSE, -1);
        }
    }
    @Override
    protected java.lang.String describe() {
        return "атакует и имеет вероятность 10% понизить специальную защиту цели";
    }
}