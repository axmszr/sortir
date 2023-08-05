package sortir;

import java.util.List;

import sortir.exc.HowEvenException;
import sortir.exc.RageQuitException;
import sortir.io.Literate;
import sortir.rank.Merger;
import sortir.rank.Ranker;

class Sortir {

    public static void main(String[] args) throws InterruptedException, HowEvenException {
        Literate rw = new Literate();

        try {
            int choice = rw.getActionChoice();
            switch (choice) {
            case 1:     // rank
                Ranker ranker = makeRanker(rw);
                // read in 1 list, mentioning errors along the way
                break;
            case 2:     // merge
                Merger merger = makeMerger(rw);
                // ask for number of lists
                // read in that many lists
                break;
            default:    // panik
                throw new HowEvenException();
            }
        } catch (RageQuitException e) {
            rageQuit();
        }


    }

    private static Ranker makeRanker(Literate rw) {
        // ask for input type
        // if manual, how many inputs
        // if file, read in
    }

    private static Merger makeMerger(Literate rw) {
        // ask for how many lists
        // for each, ask for input type
        // if manual input, convert to proper form (add #ranked)
    }

    private static List<String> manualInput() {
        // ask for how many inputs
        // provide format

    }

    private static List<String> readFile() {
        // ask for file name, must be in same directory

    }

    private static void rageQuit() throws InterruptedException {
        Writer.sayRageQuit();
        Thread.sleep(2000);
        System.exit(0);
    }
}
