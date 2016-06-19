package org.voiser.monkeys;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Deque;
import java.util.StringJoiner;

public class RopeTest {

    class TestRope extends Rope {
        
        private String repr(Deque<Monkey> q) {
            StringJoiner sj = new StringJoiner(",");
            for (Monkey m : q) {
                sj.add(m.getId() + m.getDirection().repr());
            }
            return sj.toString();
        }
        
        public String repr() {
            return "(" + repr(waiting) + ")(" + repr(crossing) + ")";
        }
    }
    
    private EventLogger events = new Events();
    
    @Test
    public void testEmptyRope() {
        TestRope r = new TestRope();
        assertEquals("()()", r.repr());
    }
    
    @Test
    public void testEnterDirectly() {
        TestRope r = new TestRope();
        Monkey m = new Monkey(1, Direction.RIGHT, r, events);
        r.enterDirectly(m);
        assertEquals("()(1>)", r.repr());
    }

    @Test
    public void testEnqueue() {
        TestRope r = new TestRope();
        Monkey m = new Monkey(1, Direction.RIGHT, r, events);
        r.enqueue(m);
        assertEquals("(1>)()", r.repr());
    }

    @Test
    public void testEnqueueAndEnter() {
        TestRope r = new TestRope();
        Monkey m = new Monkey(1, Direction.RIGHT, r, events);
        r.enqueue(m);
        r.enter(m);
        assertEquals("()(1>)", r.repr());
    }
    
    @Test
    public void testLeave() {
        TestRope r = new TestRope();
        Monkey m = new Monkey(1, Direction.RIGHT, r, events);
        r.enqueue(m);
        r.enter(m);
        r.leave(m);
        assertEquals("()()", r.repr());
    }
}
