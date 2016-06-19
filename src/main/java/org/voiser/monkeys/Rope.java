package org.voiser.monkeys;

import java.util.Deque;
import java.util.LinkedList;

public class Rope {

    protected Deque<Monkey> waiting = new LinkedList<>();
    protected Deque<Monkey> crossing = new LinkedList<>();

    public Monkey getLast() {
    	return waiting.isEmpty() ? crossing.peekLast() : waiting.peekLast();
	}
    
    public void enterDirectly(Monkey m) {
    	crossing.addLast(m);
    }
    
    public void enter(Monkey m) {
    	waiting.remove(m);
    	crossing.addLast(m);
    }
    
    public void enqueue(Monkey m) {
    	waiting.addLast(m);
    }
    
    public void leave(Monkey m) {
    	crossing.remove(m);
    }
}