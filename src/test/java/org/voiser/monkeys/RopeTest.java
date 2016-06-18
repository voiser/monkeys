package org.voiser.monkeys;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Deque;

public class RopeTest {

	/*
	 * 
	 */
	class TestRope extends Rope {
		
		private String repr(Deque<Monkey> q) {
			StringBuilder sb = new StringBuilder();
			int len = q.size();
			int i = 0;
			for (Monkey m : q) {
				sb.append(m.getId());
				sb.append(m.getDirection().repr());
				if (i++ < len - 1) {
					sb.append(",");
				}
			}
			return sb.toString();
		}
		
		public String repr() {
			return "(" + repr(waiting) + ")(" + repr(crossing) + ")(" + repr(all) + ")";
		}
	}
	
    @Test
    public void testEmptyRope() {
    	TestRope r = new TestRope();
    	assertEquals("()()()", r.repr());
    }
    
    @Test
    public void testEnterDirectly() {
    	TestRope r = new TestRope();
    	Monkey m = new Monkey(1, Direction.RIGHT);
    	r.enterDirectly(m);
    	assertEquals("()(1>)(1>)", r.repr());
    }

    @Test
    public void testEnqueue() {
    	TestRope r = new TestRope();
    	Monkey m = new Monkey(1, Direction.RIGHT);
    	r.enqueue(m);
    	assertEquals("(1>)()(1>)", r.repr());
    }

    @Test
    public void testEnqueueAndEnter() {
    	TestRope r = new TestRope();
    	Monkey m = new Monkey(1, Direction.RIGHT);
    	r.enqueue(m);
    	r.enter(m);
    	assertEquals("()(1>)(1>)", r.repr());
    }
    
    @Test
    public void testLeave() {
    	TestRope r = new TestRope();
    	Monkey m = new Monkey(1, Direction.RIGHT);
    	r.enqueue(m);
    	r.enter(m);
    	r.leave(m);
    	assertEquals("()()()", r.repr());
    }
}