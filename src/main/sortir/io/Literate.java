package sortir.io;

import java.util.function.Consumer;

import sortir.exc.BadInputException;
import sortir.exc.RageQuitException;

public class Literate {
    private final Reader rd;

    public Literate() {
        this.rd = new Reader();
    }

    private int choose(Consumer<Integer> sayer, int upperBound) throws RageQuitException {
        for (int i = 0; i < 4; i++) {
            sayer.accept(i);

            try {
                return this.rd.readInt(1, upperBound);
            } catch (BadInputException e) {
                continue;
            }
        }

        throw new RageQuitException();
    }

    public int getActionChoice() throws RageQuitException {
        return choose(Writer::sayActionChoices, 2);
    }

    public int getInputChoice() throws RageQuitException {
        return choose(Writer::sayInputChoices, 2);
    }

    public int getRankChoice(String firstName, String secondName) throws RageQuitException {
        return choose(i -> Writer.sayRankChoices(firstName, secondName, i), 3);
    }
}