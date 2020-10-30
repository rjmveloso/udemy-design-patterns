package patterns.creational;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

interface HotDrink {
	public void consume();
}

class Tea implements HotDrink {
	@Override
	public void consume() {
		System.out.println("This tea is delicious!");
	}
}

class Coffee implements HotDrink {
	@Override
	public void consume() {
		System.out.println("This coffee is delicious!");
	}
}

// Provides a common interface for factories
interface HotDrinkFactory {
	public HotDrink prepare(int amount);
}

class TeaHotDrinkFactory implements HotDrinkFactory {
	@Override
	public HotDrink prepare(int amount) {
		System.out.println("Put in tea bag, boil water, pour " + amount + "ml, add lemon. Enjoy!");
		return new Tea();
	}
}

class CoffeeDrinkFactory implements HotDrinkFactory {
	@Override
	public HotDrink prepare(int amount) {
		System.out.println("Grind some beans, boil water, pour " + amount + "ml, add cream. Enjoy!");
		return new Coffee();
	}
}

// Our abstract factory
class HotDrinkMachine {

	static final Map<Integer, HotDrinkFactory> FACTORIES = new HashMap<>();

	static {
		FACTORIES.put(0, new TeaHotDrinkFactory());
		FACTORIES.put(1, new CoffeeDrinkFactory());
	}

	public HotDrink make(int option, int amount) {
		HotDrinkFactory factory = FACTORIES.get(option);
		return factory != null ? factory.prepare(amount) : null;
	}
}

public class AbstractFactory {
	public static void main(String[] args) throws Exception {
		HotDrinkMachine machine = new HotDrinkMachine();

		System.out.println("Available Drinks:");
		for (Entry<Integer, HotDrinkFactory> entry : HotDrinkMachine.FACTORIES.entrySet()) {
			System.out.println(entry.getKey() + ": "
					+ entry.getValue().getClass().getSimpleName().replace("HotDrinkFactory", ""));
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String s;
			int option, amount;
			if ((s = reader.readLine()) != null && (option = Integer.parseInt(s)) >= 0 && option < HotDrinkMachine.FACTORIES.size()) {
				System.out.println("Specify amount:");
				if ((s = reader.readLine()) != null && (amount = Integer.parseInt(s)) > 0) {
					HotDrink drink = machine.make(option, amount);
					drink.consume();
				}
			}
		}
	}
}