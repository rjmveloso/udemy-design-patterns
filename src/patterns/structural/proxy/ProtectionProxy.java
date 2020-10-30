package patterns.structural.proxy;

interface Drivable {
	public void drive();
}

class Car implements Drivable {
	protected Driver driver;

	public Car(Driver driver) {
		this.driver = driver;
	}

	@Override
	public void drive() {
		System.out.println("Car being driven!");
	}
}

class Driver {
	public int age;

	public Driver(int age) {
		this.age = age;
	}
}

// Using inheritance
class CarProxy extends Car {
	public CarProxy(Driver driver) {
		super(driver);
	}

	@Override
	public void drive() {
		if (driver.age >= 18) {
			super.drive();
		} else {
			System.err.println("Driver too young!");
		}
	}
}

public class ProtectionProxy {
	public static void main(String[] args) {
		// With Dependency Injection this can be done without instantiation
		Car car = new CarProxy(new Driver(12));
		car.drive();
	}
}
