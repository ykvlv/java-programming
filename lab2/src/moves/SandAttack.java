package moves;

import ru.ifmo.se.pokemon.*;

public class SandAttack extends StatusMove {
    public SandAttack() {
        super(Type.GROUND, 0, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        p.setMod(Stat.ACCURACY, -1);
    }
    @Override
    protected java.lang.String describe() {
        return "понижает точность атаки цели на одну ступень";
    }
}