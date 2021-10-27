package Humans;

import Storages.Storage;

public abstract class Human implements Storage {
    private int hope;

    public Human(int hope) {
        this.hope = hope;
    }

    public void changeHope(int hope) {
        if (this.hope + hope >= 0 && this.hope + hope <= 10) {
            this.hope += hope;
        } else if (hope < 0) {
            this.hope = 0;
        } else if (hope > 0) {
            this.hope = 10;
        }
    }

    public void printHope() {
        System.out.printf("Уровень надежды %s %d/10%n", this.translation(), hope);
    }

    public int getHope() {
        return hope;
    }
}
