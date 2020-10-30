package patterns.structural.iterator;

import java.util.Iterator;
import java.util.stream.IntStream;

class Statistics implements Iterable<Integer> {

	private int[] stats = new int[3];
	
	public void setAgility(int value) {
		stats[1] = value;
	}

	public void setStrength(int value) {
		stats[0] = value;
	}

	public int max() {
		return IntStream.of(stats).max().getAsInt();
	}
	
	public int sum() {
		return IntStream.of(stats).sum();
	}
	
	public double avg() {
		return IntStream.of(stats).average().getAsDouble();
	}

	@Override
	public Iterator<Integer> iterator() {
		return IntStream.of(stats).iterator();
	}
	
}

public class BackedArrayDemo {
	public static void main(String[] args) {
		Statistics stats = new Statistics();
		stats.setAgility(12);
		stats.setStrength(16);
		System.out.println("Max: " + stats.max());
	}
}
