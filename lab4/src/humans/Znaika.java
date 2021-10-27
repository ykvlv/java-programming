package humans;

import interfaces.IThing;

public class Znaika extends Human {

    public Znaika(int hope, IThing... things) {
        super(hope, things);
    }

    @Override
    public String translation() {
        return "Знайка";
    }

}
