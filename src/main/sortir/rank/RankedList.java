package sortir.rank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RankedList {
    private static final String HEADER = "#ranked";
    
    private final Rankee head;
    
    RankedList(Rankee head) {
        this.head = head;
    }
    
    public Rankee getHead() {
        return this.head.makeFullCopy();
    }
    
    // temp
    private int vs(Rankee first, Rankee second, Scanner sc) {
        System.out.printf("%s vs %s%n",
                first, second);
        String input = sc.nextLine().trim();
        
        if (input.equals("1")) {
            return 1;
        } else if (input.equals("-1")) {
            return -1;
        } else {
            return 0;
        }
    }
    
    public RankedList merge(RankedList other, Scanner sc) {
        Rankee highPointer; // prev highest pointer
        Rankee nextPointer;  // below high
        Rankee otherPointer;  // other branch
        
        Rankee thisHead = getHead();
        Rankee otherHead = other.getHead();
        
        int result = vs(thisHead, otherHead, sc);
        if (result == 1) {          // this.head wins
            highPointer = thisHead;
            otherPointer = otherHead;
        } else if (result == -1) {  // other.head wins
            highPointer = otherHead;
            otherPointer = thisHead;
        } else if (result == 0) {   // tie
            highPointer = thisHead;
            thisHead.makeTie(otherHead);
            otherPointer = otherHead.getBelow();
        } else {
            // panik
            highPointer = thisHead;
            otherPointer = otherHead;
        }
        
        RankedList newList = new RankedList(highPointer);
        
        if (result == 0 && otherHead.isTail()) {
            return newList;
        }
        
        while (!highPointer.isTail()) {
            nextPointer = highPointer.getBelow();
            result = vs(nextPointer, otherPointer, sc);
            if (result == 1) {          // nextPointer wins
                highPointer.setBelow(nextPointer);
                highPointer = nextPointer;
            } else if (result == -1) {  // otherPointer wins
                highPointer.setBelow(otherPointer);
                highPointer = otherPointer;
                otherPointer = nextPointer;
            } else if (result == 0) {   // tie
                nextPointer.makeTie(otherPointer);
                highPointer = nextPointer;
                otherPointer = otherPointer.getBelow();
            } else {
                // panik
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
    
    public RankedList fromTxtFormat(String fullContent) {
        String[] fullArray = fullContent.split("\n");
        
        if (!fullArray[0].equals(HEADER)) {
            // throw error, wrong format
        } else if (fullArray.length < 2) {
            // throw error, no elements
        }
        
        String[] noHeader = Arrays.copyOfRange(fullArray, 2, fullArray.length);
        
        List<Rankee> rankees = new ArrayList<>();
        for (String line : noHeader) {
            String[] parts = line.split(Rankee.DELIMITER);
            if (parts.length != 2) {
                // throw error, wrong format
            }

            try {
                int rank = Integer.parseInt(parts[0]);
                Rankee rankee = new Rankee(parts[1]);
                rankee.setRank(rank);

                rankees.add(rankee);
            } catch (NumberFormatException e) {
                // throw error, wrong format
            }
        }
        
        Rankee pointer = rankees.get(0);
        
        if (pointer.getRank() != 1) {
            // throw error, wrong numbering
        }
        
        RankedList rankedList = new RankedList(pointer);
        
        int prev = 1;
        for (int i = 1; i < rankees.size(); i++) {
            Rankee rankee = rankees.get(i);
            int rank = rankee.getRank();
            
            if (rank < prev) {
                // throw error, wrong numbering
            } else if (rank == prev) {
                pointer.makeTie(rankee);
            } else {    // i.e. rank > prev
                if (rank != i + 1) {
                    // throw error, wrong numbering
                }
                
                pointer.setBelow(rankee);
                pointer = rankee;
                prev = rank;
            }
        }
        
        return rankedList;
    }
    
    @Override
    public String toString() {
        List<String> rankees = getRankees().stream()
                .map(Rankee::toString)
                .collect(Collectors.toList());
        return String.join("\n", rankees);
    }
}
