import pokemons.*;
import ru.ifmo.se.pokemon.*;

public class Main {

    public static void main(String[] args) {
        Battle battle = new Battle();
        battle.addAlly(new Volbeat("Сифон", 1));
        battle.addAlly(new Minior("Равшан", 1));
        battle.addAlly(new Vibrava("Славик", 1));
        battle.addFoe(new Silvally("Борода", 1));
        battle.addFoe(new Trapinch("Джамшут", 1));
        battle.addFoe(new Flygon("Димон", 1));
        battle.go();
    }
}
