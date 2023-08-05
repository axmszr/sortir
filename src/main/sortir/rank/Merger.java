package sortir.rank;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Merger {
    private final List<RankedList> original;
    private Queue<RankedList> queue;
    
    Merger(List<RankedList> rankedLists) {
        // assert non-empty?
        this.original = copyLists(rankedLists);
        List<RankedList> copy = copyLists(rankedLists);
        Collections.shuffle(copy);
        this.queue = new LinkedList<>(copy);
    }
    
    private static List<RankedList> copyLists(List<RankedList> rankedLists) {
        return rankedLists.stream()
                .map(rankedList -> rankedList.makeCopy())
                .collect(Collectors.toList());
    }
    
    public boolean isMerged() {
        return (queue.size() <= 1);
    }
    
    public RankedList merge(Scanner sc) {
        while (!isMerged()) {
            RankedList first = queue.poll();
            RankedList second = queue.poll();
            
            RankedList merged = first.merge(second, sc);
            queue.offer(merged);
        }
        
        return queue.peek();
    }
    
    public void reset() {
        List<RankedList> copy = copyLists(this.original);
        Collections.shuffle(copy);
        this.queue = new LinkedList<>(copy);
    }
}
