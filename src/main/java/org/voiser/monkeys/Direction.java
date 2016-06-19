package org.voiser.monkeys;

public enum Direction {
    LEFT("<"), RIGHT(">");
    
    private final String repr;
    
    private Direction(String repr) {
        this.repr = repr;
    }
    
    public String repr() {
        return this.repr;
    }
}
