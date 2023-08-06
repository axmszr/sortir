package sortir.rank;

import java.util.ArrayList;
import java.util.List;

public class Rankee {
    protected static final String DELIMITER = "#;";
    
    private final String name;
    private final List<Rankee> ties;
    private Rankee below;
    private int rank;
    
    Rankee(String name) {
        this.name = name;
        this.ties = new ArrayList<>();
        this.ties.add(this);
        this.below = this;
        this.rank = 0;
    }
    
    protected Rankee makeCopy() {
        return new Rankee(getName());
    }
    
    protected Rankee makeCopyWithTies() {
        Rankee copy = makeCopy();
        
        for (Rankee tie : getTies()) {
            Rankee newTie = tie.makeCopy();
            copy.makeTie(newTie);
        }
        
        return copy;
    }
    
    protected Rankee makeFullCopy() {
        Rankee copy = makeCopyWithTies();
        
        if (!isTail()) {
            Rankee belowCopy = getBelow().makeFullCopy();
            copy.setBelow(belowCopy);
        }
        
        return copy;
    }
    
    protected String getName() {
        return this.name;
    }
    
    protected Rankee getBelow() {
        return this.below;
    }
    
    protected void setBelow(Rankee other) {
        this.below = other;
    }
    
    protected List<Rankee> getTies() {
        return this.ties;
    }
    
    protected int getRank() {
        return this.rank;
    }
    
    protected void setRank(int rank) {
        this.rank = rank;
    }
    
    protected void makeTie(Rankee other) {
        this.ties.addAll(other.ties);
        // can ignore the below and ties of other, since they won't be accessed again
    }
    
    protected boolean isTail() {
        return this.below == this;
    }
    
    @Override
    public String toString() {
        return String.format("%d %s %s",
                this.rank, DELIMITER, this.name);
    }
}
