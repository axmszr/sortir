package sortir.rank;

import java.util.Queue;

import sortir.exc.HowEvenException;
import sortir.exc.RageQuitException;
import sortir.io.Literate;

public abstract class Runner {

    abstract Queue<RankedList> getQueue();

    abstract void reset();

    boolean isDone() {
        return (getQueue().size() <= 1);
    }

    public RankedList run(Literate rw) throws HowEvenException, RageQuitException {
        while (!isDone()) {
            RankedList first = getQueue().poll();
            RankedList second = getQueue().poll();

            RankedList merged = first.merge(second, rw);
            getQueue().offer(merged);
        }

        RankedList finalList = getQueue().poll();
        reset();
        return finalList;
    }
}
