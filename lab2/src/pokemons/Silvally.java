package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Silvally extends Pokemon {
    public Silvally(String name, int level) {
        super(name, level);
        setStats(95, 95, 95, 95, 95, 95);
        setType(Type.NORMAL);
        setMove(new DoubleEdge(), new TakeDown(), new AirSlash());
    }
}
