package patterns.structural.mediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Facilitates communication between other components without them being aware of each other.
 */
class Person {
	public String name;
	public ChatRoom room;

	public Person(String name) {
		this.name = name;
	}

	public void send(String message) {
		room.broadcast(this, message);
	}
	
	public void send(Person receiver, String message) {
		room.message(this, receiver, message);
	}

	public void receive(Person sender, String message) {
		System.out.println(sender.name + "> " + message);
	}

}

// should be singleton
class ChatRoom { //mediator
	private List<Person> people = new ArrayList<>();
	
	public void join(Person p) {
		p.room = this;
		people.add(p);
		broadcast(p, p.name + " joins the room");
	}

	public void broadcast(Person p, String message) {
		for(Person person : people) {
			if (person != p) {
				person.receive(person, message);
			}
		}
	}

	public void message(Person source, Person target, String message) {
		people.stream().filter(p -> p == source).findFirst().ifPresent(p -> p.receive(source, message));
	}

}

public class MediatorDemo {
	public static void main(String[] args) {
		ChatRoom room = new ChatRoom();
		
		Person john = new Person("John");
		Person jane = new Person("Jane");
		
		room.join(john);
		room.join(jane);
		
		john.send("Hi room");
		jane.send("Oh, hey John");
		
		Person simon = new Person("Simon");
		
		room.join(simon);
		
		simon.send("Hi everyone!");
		
		jane.send(simon, "Simon glad you could join us!");
	}
}
