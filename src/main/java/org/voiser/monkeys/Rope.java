package org.voiser.monkeys;

import java.util.Deque;
import java.util.LinkedList;

public class Rope {

    protected Deque<Monkey> all = new LinkedList<>();
    protected Deque<Monkey> waiting = new LinkedList<>();
    protected Deque<Monkey> crossing = new LinkedList<>();

    public void enterDirectly(Monkey m) {
    	all.addLast(m);
    	crossing.addLast(m);
    }
    
    public void enter(Monkey m) {
    	waiting.remove(m);
    	crossing.addLast(m);
    }
    
    public void enqueue(Monkey m) {
    	waiting.addLast(m);
    	all.addLast(m);
    }
    
    public void leave(Monkey m) {
    	all.remove(m);
    	crossing.remove(m);
    }
}