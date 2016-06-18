package org.voiser.monkeys;

public class Monkey implements Runnable {

	public enum State {
		NEW,
		WAITING_ENTER,
		WAITING_LEAVE,
		ENTERING,
		CROSSING, 
		BYEBYE
	}

	private static long start = -1;
	
	private void log(String text) {
		long now = System.currentTimeMillis();
		if (start == -1) start = now;
		System.out.format("%d monkey %d %s : %s\n", 
				(now - start), 
				id, 
				direction.repr(), 
				text);
	}
	
	private final int id;
	private final Direction direction;
	private final Rope rope;
	private State state;
	private final EventLogger events;
	private Monkey notifyEnter;
	private Monkey notifyLeave;
	
	public Monkey(int id, Direction d, Rope rope, EventLogger events) {
		this.id = id;
		this.direction = d;
		this.rope = rope;
		this.state = State.NEW;
		this.events = events;
		this.notifyEnter = null;
		this.notifyLeave = null;
	}
	
	public int getId() {
		return id;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public synchronized void notifyWhenEnter(Monkey other) {
		this.notifyEnter = other;
	}
	
	public synchronized void notifyWhenLeave(Monkey other) {
		this.notifyLeave = other;
	}
	
	public synchronized void notifyEnter() {
		if (this.notifyEnter != null) {
			events.log(this, "NotifyEnter");
			notify();
		}
		this.notifyEnter = null;
	}
	
	public synchronized void notifyLeave() {
		if (this.notifyLeave != null) {
			events.log(this, "NotifyLeave");
			notify();
		}
		this.notifyLeave = null;
	}
	
	public void enter() throws InterruptedException {
		Monkey last = null;
		synchronized(rope) {
			log("Will enter");
			last = rope.getLast();
			if (last == null) {
				log("Enter directly");
				state = State.ENTERING;
				rope.enterDirectly(this);
				events.log(this, "EnterDirectly");
			}
			else {
				if (last.getDirection() == this.direction) {
					log("Wait to enter");
					state = State.WAITING_ENTER;
					rope.enqueue(this);
					events.log(this, "WillWaitEnter(" + last.getId() + ")");
				}
				else {
					log("Wait to leave");
					state = State.WAITING_LEAVE;
					rope.enqueue(this);
					events.log(this, "WillWaitLeave(" + last.getId() + ")");
				}
			}
		}
		if (state == State.WAITING_ENTER) {
			synchronized(last) {
				log("Waiting for " + last.getId() + " to enter...");
				events.log(this, "WaitingEnter(" + last.getId() + ")");
				last.notifyWhenEnter(this);
				last.wait();
				synchronized(rope) {
					rope.enter(this);
				}
			}
		}
		else if (state == State.WAITING_LEAVE) {
			synchronized(last) {
				log("Waiting for " + last.getId() + " to leave...");
				events.log(this, "WaitingLeave(" + last.getId() + ")");
				last.notifyWhenLeave(this);
				last.wait();
				synchronized (rope) {
					rope.enter(this);
				}
			}
		}
		Thread.sleep(1000);
		log("Entered!");
		state = State.CROSSING;
		events.log(this, "Enter");
	}
	
	public void cross() throws InterruptedException {
		for (int i = 0; i < 4; i++) {
			notifyEnter();
			Thread.sleep(1000);
			log("...advancing...");
		}
		events.log(this, "Crossed");
	}
	
	public void leave() throws InterruptedException {
		notifyEnter();
		synchronized(rope) {
			rope.leave(this);
			events.log(this, "Leave");
		}
		notifyLeave();
		log("Leave!");
	}
	
	@Override
	public void run() {
		try {
			enter();
			
			cross();
			
			leave();
		} 
		catch (InterruptedException e) {
			System.exit(-1);
		}
	}
}