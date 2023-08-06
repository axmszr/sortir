package sortir.rank;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import sortir.exc.BadFileException;
import sortir.exc.HowEvenException;
import sortir.exc.RageQuitException;
import sortir.io.Literate;

public class RankedList {
    public static final String HEADER = "#ranked";

    private final Rankee head;

    RankedList(Rankee head) {
        this.head = head;
    }

    public Rankee getHead() {
        return this.head.makeFullCopy();
    }

    private static int inputToVs(int input) throws HowEvenException {
        switch (input) {
        case 1:
            // first wins
            return 1;
        case 2:
            // second wins
            return -1;
        case 3:
            // tie
            return 0;
        default:
            // bad input
            throw new HowEvenException();
        }
    }

    private static int vs(Rankee first, Rankee second, Literate rw) throws RageQuitException, HowEvenException {
        int input = rw.getRankChoice(first.getName(), second.getName());
        return inputToVs(input);
    }

    public RankedList merge(RankedList other, Literate rw) throws RageQuitException, HowEvenException {
        Rankee highPointer; // prev highest pointer
        Rankee nextPointer; // below high
        Rankee otherPointer; // other branch

        Rankee thisHead = getHead();
        Rankee otherHead = other.getHead();

        int result = vs(thisHead, otherHead, rw);
        switch (result) {
        case 1:
            // thisHead wins
            highPointer = thisHead;
            otherPointer = otherHead;
            break;
        case -1:
            // otherHead wins
            highPointer = otherHead;
            otherPointer = thisHead;
            break;
        case 0:
            // tie
            highPointer = thisHead;
            thisHead.makeTie(otherHead);
            otherPointer = otherHead.getBelow();
            break;
        default:
            // panik
            throw new HowEvenException();
        }

        RankedList newList = new RankedList(highPointer);

        if (result == 0 && otherHead.isTail()) {
            return newList;
        }

        while (!highPointer.isTail()) {
            nextPointer = highPointer.getBelow();
            result = vs(nextPointer, otherPointer, rw);
            switch (result) {
            case 1:
                // nextPointer wins
                highPointer.setBelow(nextPointer);
                highPointer = nextPointer;
                break;
            case -1:
                // otherPointer wins
                highPointer.setBelow(otherPointer);
                highPointer = otherPointer;
                otherPointer = nextPointer;
                break;
            case 0:
                // tie
                nextPointer.makeTie(otherPointer);
                highPointer = nextPointer;
                otherPointer = otherPointer.getBelow();
                break;
            default:
                // panik
                throw new HowEvenException();
            }
        }

        if (!(otherPointer.isTail() && highPointer.getTies().contains(otherPointer))) {
            highPointer.setBelow(otherPointer);
        }

        // works for all 2 + 2. try for 1 + 1, 1 + 2, 2 + 3, 3 + 3

        return newList;
    }

    public List<Rankee> getRankees() {
        Rankee pointer = getHead();

        for (Rankee tie : pointer.getTies()) {
            tie.setRank(1);
        }

        List<Rankee> rankees = pointer.getTies();

        while (!pointer.isTail()) {
            pointer = pointer.getBelow();

            for (Rankee tie : pointer.getTies()) {
                tie.setRank(rankees.size() + 1);
            }

            rankees.addAll(pointer.getTies());
        }

        return rankees;
    }

    public RankedList makeCopy() {
        return new RankedList(getHead());
    }

    public String toTxtFormat() {
        return HEADER + "\n" + toString();
    }

    public static RankedList fromTxtFormat(List<String> fileContent) throws BadFileException {
        if (!fileContent.get(0).equals(HEADER)) {
            throw new BadFileException("Wrong file header. Should begin with: " + HEADER);
        } else if (fileContent.size() < 2) {
            throw new BadFileException("File has no Rankees.");
        }

        List<String> noHeader = fileContent.subList(1, fileContent.size());

        List<Rankee> rankees = new ArrayList<>();
        for (String line : noHeader) {
            String[] parts = line.split(Rankee.DELIMITER);
            if (parts.length != 2) {
                throw new BadFileException("Incorrect format in line: " + line);
            }

            try {
                int rank = Integer.parseInt(parts[0].trim());
                Rankee rankee = new Rankee(parts[1].trim());
                rankee.setRank(rank);

                rankees.add(rankee);
            } catch (NumberFormatException e) {
                throw new BadFileException("Invalid rank in line: " + line);
            }
        }

        Rankee pointer = rankees.get(0);

        if (pointer.getRank() != 1) {
            throw new BadFileException("First Rankee should be Rank 1.");
        }

        RankedList rankedList = new RankedList(pointer);

        int prev = 1;
        for (int i = 1; i < rankees.size(); i++) {
            Rankee rankee = rankees.get(i);
            int rank = rankee.getRank();

            if (rank < prev) {
                throw new BadFileException("Improper rank order in line: " + noHeader.get(i));
            } else if (rank == prev) {
                pointer.makeTie(rankee);
            } else {
                // i.e. rank > prev
                if (rank != i + 1) {
                    throw new BadFileException("Incorrect rank in line: " + noHeader.get(i));
                }

                pointer.setBelow(rankee);
                pointer = rankee;
                prev = rank;
            }
        }

        return rankedList;
    }

    public List<String> toStringList() {
        List<String> rankees = getRankees().stream()
                .map(Rankee::toString)
                .collect(Collectors.toList());
        return rankees;
    }

    @Override
    public String toString() {
        List<String> rankees = toStringList();
        return String.join("\n", rankees);
    }
}
