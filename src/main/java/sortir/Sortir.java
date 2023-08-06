package sortir;

import java.util.ArrayList;
import java.util.List;

import sortir.exc.BadFileException;
import sortir.exc.HowEvenException;
import sortir.exc.RageQuitException;
import sortir.io.Literate;
import sortir.io.Writer;
import sortir.rank.Merger;
import sortir.rank.RankedList;
import sortir.rank.Ranker;
import sortir.rank.Runner;

class Sortir {

    public static void main(String[] args) throws InterruptedException {
        Literate rw = new Literate();

        try {
            while (true) {
                Runner runner = makeRunner(rw);
                RankedList rankedList = run(runner, rw);
                doSaveRequest(rankedList, rw);

                boolean doRestart = rw.getRestartChoice();

                if (!doRestart) {
                    break;
                }
            }
        } catch (HowEvenException e) {
            howEven();
        } catch (RageQuitException e) {
            rageQuit();
        }

        rw.bye();
    }

    private static Runner makeRunner(Literate rw) throws HowEvenException, RageQuitException {
        int action = rw.getActionChoice();
        switch (action) {
        case 1:
            // rank
            return makeRanker(rw);
        case 2:
            // merge
            return makeMerger(rw);
        default:
            // panik
            throw new HowEvenException();
        }
    }

    private static Ranker makeRanker(Literate rw) throws HowEvenException, RageQuitException {
        List<String> names;

        while (true) {
            int choice = rw.getInputChoice();
            switch (choice) {
            case 1:
                // manual
                names = rw.manualRankerInput();
                break;
            case 2:
                // readInList
                names = rw.readFromFile();
                break;
            default:
                // panik
                throw new HowEvenException();
            }

            if (rw.confirm(names)) {
                return new Ranker(names);
            }
        }
    }

    private static Merger makeMerger(Literate rw) throws RageQuitException, HowEvenException {
        List<RankedList> rankedLists = new ArrayList<>();

        int count = rw.getInt();
        for (int i = 0; i < count; i++) {
            rankedLists.add(makeRankedList(rw));
        }

        return new Merger(rankedLists);
    }

    private static RankedList makeRankedList(Literate rw) throws RageQuitException, HowEvenException {
        List<String> formattedNames;
        RankedList rankedList;

        while (true) {
            int choice = rw.getInputChoice();
            switch (choice) {
            case 1:
                // manual
                formattedNames = rw.manualMergerInput();
                formattedNames.add(0, RankedList.HEADER);
                break;
            case 2:
                // readInList
                formattedNames = rw.readFromFile();
                break;
            default:
                // panik
                throw new HowEvenException();
            }

            try {
                rankedList = RankedList.fromTxtFormat(formattedNames);
            } catch (BadFileException e) {
                rw.sayFailedMerger(e.getMessage());
                continue;
            }

            if (rw.confirm(formattedNames)) {
                return rankedList;
            }
        }
    }

    private static RankedList run(Runner runner, Literate rw) throws HowEvenException, RageQuitException {
        RankedList attempt;

        while (true) {
            attempt = runner.run(rw);

            if (rw.confirm(attempt.toStringList())) {
                return attempt;
            }
        }
    }

    private static void doSaveRequest(RankedList rankedList, Literate rw) throws RageQuitException {
        boolean doSave = rw.getSaveChoice();

        if (!doSave) {
            return;
        }

        String fileContent = rankedList.toTxtFormat();
        rw.saveToFile(fileContent);
    }

    private static void howEven() throws InterruptedException {
        Writer.sayHowEven();
        Thread.sleep(1000);
        System.exit(0);
    }

    private static void rageQuit() throws InterruptedException {
        Writer.sayRageQuit();
        Thread.sleep(1000);
        System.exit(0);
    }
}
