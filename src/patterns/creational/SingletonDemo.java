package patterns.creational;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Singleton can be workaround by<br/>
 * 1. reflection<br/>
 * 2. serialization (we can prevent it with readResolve())
 */
class BasicSingleton implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final BasicSingleton INSTANCE = new BasicSingleton();

	private int value;

	private BasicSingleton() {
	}

	public static BasicSingleton get() {
		return INSTANCE;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	protected Object readResolve() {
		// force the same instance to be returned on runtime
		return INSTANCE;
	}
}

/**
 * If the constructor needs some initialization that throws an Exception
 */
class StaticBlockSingleton {

	private static StaticBlockSingleton instance;

	private StaticBlockSingleton() throws IOException {
		File.createTempFile(".", ".");
	}

	static {
		try {
			instance = new StaticBlockSingleton();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static StaticBlockSingleton get() {
		return instance;
	}
}

/**
 * This is called the initialization-on-demand holder idiom<br/>
 * This is guaranteed to be thread-safe
 */
class InnerStaticSingleton {

	private InnerStaticSingleton() {
	}

	private static class Holder {
		private static final InnerStaticSingleton INSTANCE = new InnerStaticSingleton();
	}

	public InnerStaticSingleton get() {
		return Holder.INSTANCE;
	}
}

public class SingletonDemo {
	private static void save(Object object, String filename) throws Exception {
		try (FileOutputStream fos = new FileOutputStream(filename);
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(object);
		}
	}

	private static Object load(String filename) throws Exception {
		try (FileInputStream fis = new FileInputStream(filename); ObjectInputStream ois = new ObjectInputStream(fis)) {
			return ois.readObject();
		}
	}

	public static void main(String[] args) throws Exception {
		BasicSingleton singleton = BasicSingleton.get();
		singleton.setValue(111);

		final String filename = "singleton.bin";
		save(singleton, filename);

		singleton.setValue(222);

		BasicSingleton singleton2 = (BasicSingleton) load(filename);

		// readResolve will force to return the same instance
		System.out.println(singleton == singleton2);
		System.out.print("value: " + singleton.getValue() + " > ");
		System.out.println(singleton.getValue() == singleton2.getValue());
	}
}
