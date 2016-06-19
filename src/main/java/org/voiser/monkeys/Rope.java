package org.voiser.monkeys;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.voiser.monkeys.Monkey.State;

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
    
    public void show() {
    	Map<Direction, List<Monkey>> monkeysWaiting = waiting.stream().collect(Collectors.groupingBy(Monkey::getDirection));
    	StringJoiner left = new StringJoiner(" ");
    	StringJoiner right = new StringJoiner(" ");
    	StringJoiner rope = new StringJoiner("-");
    	{
    		List<Monkey> l = monkeysWaiting.get(Direction.RIGHT);
    		if (l != null) { 
    			for (Monkey m : l) {
    				left.add(String.format("%02d>", m.getId()));
    			}
    		}
    	}
    	{
    		List<Monkey> l = monkeysWaiting.get(Direction.LEFT);
    		if (l != null) {
    			for (Monkey m : l) {
    				right.add(String.format("<%02d", m.getId()));
    			}
    		}
    	}
    	Monkey[] monkeysInRope = new Monkey[crossing.size() < 5 ? 5 : crossing.size()];
    	for (Monkey m : crossing) {
    		int i = m.getStep();
    		if (m.getState() == State.ENTERING) {
    			if (m.getDirection() == Direction.RIGHT) monkeysInRope[0] = m;
    			else monkeysInRope[monkeysInRope.length - 1] = m;
    		} 
    		else {
    			if (m.getDirection() == Direction.RIGHT) monkeysInRope[i] = m;
    			else monkeysInRope[monkeysInRope.length - i - 1] = m;
    		}
    	}
    	for (Monkey m : monkeysInRope) {
    		if (m == null) rope.add("----");
    		else if (m.getState() == State.ENTERING) rope.add(String.format("^%02d", m.getId()));
    		else if (m.getDirection() == Direction.RIGHT) rope.add(String.format("%02d>", m.getId()));
    		else rope.add(String.format("<%02d", m.getId()));
    	}
    	System.out.println(left + "  |" + rope + "|  " + right);
    }
}
