package org.voiser.monkeys;

public class Monkey {

	private final int id;
	private final Direction direction;
	
	public Monkey(int id, Direction d) {
		this.id = id;
		this.direction = d;
	}
	
	public int getId() {
		return id;
	}
	
	public Direction getDirection() {
		return direction;
	}
}