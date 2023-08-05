package sortir.rank;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import sortir.exc.HowEvenException;
import sortir.exc.RageQuitException;
import sortir.io.Literate;

public class Ranker {
    private final List<String> names;
    private Queue<RankedList> queue;
    
    Ranker(List<String> names) {
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
    
    public boolean isRanked() {
        return (queue.size() <= 1);
    }
    
    public RankedList rank(Literate rw) throws RageQuitException, HowEvenException {
        while (!isRanked()) {
            RankedList first = queue.poll();
            RankedList second = queue.poll();
            
            RankedList merged = first.merge(second, rw);
            queue.offer(merged);
        }
        
        return queue.peek();
    }
    
    public void reset() {
        this.queue = makeQueue(names);
    }
}
