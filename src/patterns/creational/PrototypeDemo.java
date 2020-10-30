package patterns.creational;

/**
 * A partially or fully initialized object to copy (clone)<br/>
 * A factory can be used to create these new instances<br/>
 * DO NOT USE CLONE java.lang.Cloneable<br/>
 * <pre>
 * clone makes a shallow copy and not a deep copy
 * clone throws java.lang.CloneNotSupportedException and return an Object which requires a cast
 * </pre>
 */
class Address {
	public String street;
	public String city;

	public Address(String street, String city) {
		this.street = street;
		this.city = city;
	}

	/**
	 * Prototype: copy constructor
	 */
	public Address(Address other) {
		street = other.street;
		city = other.city;
	}

	@Override
	public String toString() {
		return "Address{street='" + street + "', city='" + city + "'}";
	}
}

class Employee {
	public String name;
	public Address address;

	public Employee(String name, Address address) {
		this.name = name;
		this.address = address;
	}

	// Prototype
	public Employee(Employee other) {
		name = other.name;
		address = new Address(other.address);
	}

	@Override
	public String toString() {
		return "Employee{name='" + name + ", address=" + address + "}";
	}
}

public class PrototypeDemo {
	public static void main(String[] args) {
		Employee john = new Employee("John Smith", new Address("555, London Road", "London"));

		Employee jane = new Employee(john);
		jane.name = "Jane Smith";

		System.out.println(john);
		System.out.println(jane);
	}
}
