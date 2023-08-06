package sortir.rank;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Ranker extends Runner {
    private final List<String> names;
    private Queue<RankedList> queue;
    
    public Ranker(List<String> names) {
        // assert non-empty?
        this.names = names;
        this.queue = makeQueue(names);
    }
    
    private static Queue<RankedList> makeQueue(List<String> names) {
        List<RankedList> rankedLists = names.stream()
                .map(Rankee::new)
                .map(RankedList::new)
                .collect(Collectors.toList());
        Collections.shuffle(rankedLists);
        return new LinkedList<>(rankedLists);
    }

    @Override
    Queue<RankedList> getQueue() {
        return this.queue;
    }

    @Override
    void reset() {
        this.queue = makeQueue(names);
    }
}
