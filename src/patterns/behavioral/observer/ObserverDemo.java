package patterns.behavioral.observer;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

class Event<T> {
	public Object source;
	public String property;
	public Object value;

	public Event(Object source, String property, Object value) {
		this.source = source;
		this.property = property;
		this.value = value;
	}
}

class PropertyChangeHub<T> {
	private int count = 0;
	private Map<Integer, Consumer<T>> handlers = new HashMap<>();

	public void fire(T value) {
		handlers.values().stream().forEach(consumer -> consumer.accept(value));
	}

	public Subscription subscribe(Consumer<T> handler) {
		int i = count++;
		handlers.put(i, handler);
		return new Subscription(this, i);
	}

	public void unsubscribe(Subscription subscription) {
		handlers.remove(subscription.id);
	}
}

interface PropertyChangeListener<T> {
	public void process(Event<T> event);
}

/**
 * Memento Design Pattern with a reference to the id of the subscription
 */
class Subscription implements Closeable {
	public int id;
	private PropertyChangeHub<?> hub;

	public Subscription(PropertyChangeHub<?> hub, int id) {
		this.id = id;
		this.hub = hub;
	}

	@Override
	public void close() {
		hub.unsubscribe(this);
	}
}

class Person {
	private int age;
	private PropertyChangeListener<?> listener;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		if (this.age == age) {
			return;
		}
		this.age = age;
		listener.process(new Event<>(this, "age", age));
	}
	
	public void setPropertyChangeListener(PropertyChangeListener<?> listener) {
		this.listener = listener;
	}
}

public class ObserverDemo {
	public static void main(String[] args) {
		PropertyChangeHub<Event<?>> hub = new PropertyChangeHub<>();
		Subscription subscription = hub.subscribe(evt -> {
			System.out.println("Property " + evt.property + " changed to " + evt.value);
		});
		Person person = new Person();
		person.setPropertyChangeListener(evt -> hub.fire(evt));
		person.setAge(10);
		person.setAge(15);
		//hub.unsubscribe(subscription);
		subscription.close();
		person.setAge(20);
	}
}
