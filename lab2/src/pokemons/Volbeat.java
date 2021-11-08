package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Volbeat extends Pokemon {
    public Volbeat(String name, int level) {
        super(name, level);
        setStats(65, 73, 75, 47, 85, 85);
        setType(Type.BUG);
        setMove(new Tackle(), new StruggleBug(), new DizzyPunch(), new QuickAttack());
    }
}
