package sortir.rank;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Merger extends Runner {
    private final List<RankedList> original;
    private Queue<RankedList> queue;

    public Merger(List<RankedList> rankedLists) {
        // assert non-empty?
        this.original = copyLists(rankedLists);
        List<RankedList> copy = copyLists(rankedLists);
        Collections.shuffle(copy);
        this.queue = new LinkedList<>(copy);
    }

    private static List<RankedList> copyLists(List<RankedList> rankedLists) {
        return rankedLists.stream()
                .map(RankedList::makeCopy)
                .collect(Collectors.toList());
    }

    @Override
    Queue<RankedList> getQueue() {
        return this.queue;
    }

    @Override
    void reset() {
        List<RankedList> copy = copyLists(this.original);
        Collections.shuffle(copy);
        this.queue = new LinkedList<>(copy);
    }
}
