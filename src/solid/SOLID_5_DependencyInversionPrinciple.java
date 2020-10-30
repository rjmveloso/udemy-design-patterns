package solid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DIP: A. High-level modules should not depend on low-level modules. Both
 * should depend on abstractions. B. Abstractions should not depend on details.
 * Details should depend on abstractions.
 */

class Person {
	public String firstName;
	public String lastName;

	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
}

interface PersonFilter {
	public List<Person> filterByName(String name);
}

/**
 * LOW-LEVEL Related to data storage. Provides data and has no business logic
 */
class PersonData implements PersonFilter {
	private List<Person> persons = new ArrayList<>();

	public List<Person> getPersons() {
		return persons;
	}

	public void add(Person person) {
		persons.add(person);
	}

	@Override
	public List<Person> filterByName(String name) {
		return persons.stream().filter(p -> p.firstName.equals(name)).collect(Collectors.toList());
	}
}

/**
 * HIGH-LEVEL
 */
class PersonResearch {
	/**
	 * Violates the DIP Depends on PersonData and should depend on abstractions.
	 */
	public PersonResearch(PersonData data) {
		data.getPersons().stream().filter(p -> p.firstName.equals("John"))
				.forEach(p -> System.out.println(p.firstName + " " + p.firstName));
	}

	/**
	 * Does not violates DIP Depends on abstractions and also complies with SRP
	 */
	public PersonResearch(PersonFilter filter) {
		filter.filterByName("Bart").stream().forEach(p -> System.out.println(p.firstName + " " + p.lastName));
	}
}

public class SOLID_5_DependencyInversionPrinciple {
	public static void main(String[] args) {
		PersonData repository = new PersonData();
		repository.add(new Person("Bart", "A"));
		repository.add(new Person("Bart", "S"));
		repository.add(new Person("Marge", "S"));

		new PersonResearch((PersonFilter) repository);
	}
}
