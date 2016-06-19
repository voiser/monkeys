package org.voiser.monkeys;

import static org.junit.Assert.*;

import org.junit.Test;

public class MonkeysTest {

    @Test
    public void testEnterDirectly() throws InterruptedException {
        Rope r = new Rope();
        Events events = new Events();
        Monkey m = new Monkey(1, Direction.RIGHT, r, events);
        Thread t = new Thread(m);
        t.start();
        t.join();
        assertEquals("EnterDirectly,Enter,Crossed,Leave", events.get(m));
    }

    @Test
    public void testWaitEnter() throws InterruptedException {
        Rope r = new Rope();
        Events events = new Events();
        Monkey m1 = new Monkey(1, Direction.RIGHT, r, events);
        Thread t1 = new Thread(m1);
        Monkey m2 = new Monkey(2, Direction.RIGHT, r, events);
        Thread t2 = new Thread(m2);
        t1.start();
        Thread.sleep(200);
        t2.start();
        t1.join();
        t2.join();
        assertEquals("EnterDirectly,Enter,NotifyEnter,Crossed,Leave", events.get(m1));
        assertEquals("WillWaitEnter(1),WaitingEnter(1),Enter,Crossed,Leave", events.get(m2));
    }

    @Test
    public void testWaitEnter2() throws InterruptedException {
        Rope r = new Rope();
        Events events = new Events();
        Monkey m1 = new Monkey(1, Direction.RIGHT, r, events);
        Thread t1 = new Thread(m1);
        Monkey m2 = new Monkey(2, Direction.RIGHT, r, events);
        Thread t2 = new Thread(m2);
        Monkey m3 = new Monkey(3, Direction.RIGHT, r, events);
        Thread t3 = new Thread(m3);
        t1.start();
        Thread.sleep(200);
        t2.start();
        Thread.sleep(200);
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        assertEquals("EnterDirectly,Enter,NotifyEnter,Crossed,Leave", events.get(m1));
        assertEquals("WillWaitEnter(1),WaitingEnter(1),Enter,NotifyEnter,Crossed,Leave", events.get(m2));
        assertEquals("WillWaitEnter(2),WaitingEnter(2),Enter,Crossed,Leave", events.get(m3));
    }
    
    @Test
    public void testWaitEnterLate() throws InterruptedException {
        Rope r = new Rope();
        Events events = new Events();
        Monkey m1 = new Monkey(1, Direction.RIGHT, r, events);
        Thread t1 = new Thread(m1);
        Monkey m2 = new Monkey(2, Direction.RIGHT, r, events);
        Thread t2 = new Thread(m2);
        t1.start();
        Thread.sleep(3000);
        t2.start();
        t1.join();
        t2.join();
        assertEquals("EnterDirectly,Enter,Crossed,Leave", events.get(m1));
        assertEquals("NoNeedToWait,Enter,Crossed,Leave", events.get(m2));
    }

    @Test
    public void testWaitLeave() throws InterruptedException {
        Rope r = new Rope();
        Events events = new Events();
        Monkey m1 = new Monkey(1, Direction.RIGHT, r, events);
        Thread t1 = new Thread(m1);
        Monkey m2 = new Monkey(2, Direction.LEFT, r, events);
        Thread t2 = new Thread(m2);
        t1.start();
        Thread.sleep(200);
        t2.start();
        t1.join();
        t2.join();
        assertEquals("EnterDirectly,Enter,Crossed,Leave,NotifyLeave", events.get(m1));
        assertEquals("WillWaitLeave(1),WaitingLeave(1),Enter,Crossed,Leave", events.get(m2));
    }

    @Test
    public void testWaitEnterLeave() throws InterruptedException {
        Rope r = new Rope();
        Events events = new Events();
        Monkey m1 = new Monkey(1, Direction.RIGHT, r, events);
        Thread t1 = new Thread(m1);
        Monkey m2 = new Monkey(2, Direction.LEFT, r, events);
        Thread t2 = new Thread(m2);
        Monkey m3 = new Monkey(3, Direction.LEFT, r, events);
        Thread t3 = new Thread(m3);
        t1.start();
        Thread.sleep(200);
        t2.start();
        Thread.sleep(200);
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        assertEquals("EnterDirectly,Enter,Crossed,Leave,NotifyLeave", events.get(m1));
        assertEquals("WillWaitLeave(1),WaitingLeave(1),Enter,NotifyEnter,Crossed,Leave", events.get(m2));
        assertEquals("WillWaitEnter(2),WaitingEnter(2),Enter,Crossed,Leave", events.get(m3));
    }
}
