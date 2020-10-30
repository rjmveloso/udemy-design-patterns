package patterns.structural.proxy;

class Property<T> {
	private T value;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		// We can do some logging on this Proxy block
		System.out.println("Logging property: " + value);
		this.value = value;
	}
}

class Creature {
	// Delegate to property proxy
	private Property<Integer> agility = new Property<>();

	public int getAgility() {
		return agility.getValue();
	}

	public void setAgility(int value) {
		agility.setValue(value);
	}
}

public class PropertyProxy {
	public static void main(String[] args) {
		Creature creature = new Creature();
		creature.setAgility(12);
	}
}
