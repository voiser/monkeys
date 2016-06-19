package org.voiser.monkeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Monkeys {

	public static final int N_MONKEYS = 100;

	static class View implements Runnable {
		
		private Rope rope;
		private boolean shouldStop;
		
		public View(Rope rope) {
			this.rope = rope;
			this.shouldStop = false;
		}
		
		public void stop() {
			this.shouldStop = true;
		}
		
		@Override
		public void run() {
			try {
				while(! shouldStop) {
					rope.show();
					Thread.sleep(500);
				}
			}
			catch (InterruptedException e) {
				// ignore
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		Rope rope = new Rope();
		EventLogger logger = new EventLogger() {
			@Override
			public void log(Monkey m, String event) {
			}
		};

		View view = new View(rope);
		Thread tview = new Thread(view);
		tview.start();
		
		Random rand = new Random();
		
		List<Thread> threads = new ArrayList<>(N_MONKEYS);
		
		for (int i = 0; i < N_MONKEYS; i++) {
			Direction d = rand.nextInt(2) < 1 ? Direction.LEFT : Direction.RIGHT;
			Monkey m = new Monkey(i, d, rope, logger);
			Thread t = new Thread(m);
			threads.add(t);
			t.start();
			Thread.sleep((1 + rand.nextInt(7)) * 1000);
		}
		
		for (Thread t : threads) {
			t.join();
		}
		
		view.stop();
		tview.join();
	}
}
