package patterns.behavioral.nullobject;

import java.lang.reflect.Proxy;

/**
 * A no-op object that conforms to the required interface, satisfying a
 * dependency requirement of some other object.
 */
interface Log {
	public void info(String message);

	public void warn(String message);
}

class NullLog implements Log {
	@Override
	public void info(String message) {
	}

	@Override
	public void warn(String message) {
	}
}

class ConsoleLog implements Log {
	@Override
	public void info(String message) {
		System.out.println(message);
	}

	@Override
	public void warn(String message) {
		System.out.println("WARNING " + message);
	}
}

class DynamicProxy {
	@SuppressWarnings("unchecked")
	public static <T> T create(Class<T> itf) {
		return (T) Proxy.newProxyInstance(itf.getClassLoader(), new Class<?>[] { itf },
				(proxy, method, args) -> {
					final Class<?> rt = method.getReturnType();
					if (rt.equals(Void.TYPE)) {
						return null;
					} else {
						return rt.getConstructor().newInstance();
					}
				});
	}
}

class BankAccount {
	private Log log;
	private int balance;

	public BankAccount(Log log) {
		this.log = log;
	}

	public void deposit(int amount) {
		balance += amount;
		log.info("Deposited " + amount);
	}

	@Override
	public String toString() {
		return "BankAccount{balance=" + balance + "}";
	}
}

public class NullObjectDemo {
	public static void main(String[] args) {
		//Log log = new ConsoleLog();
		Log log = DynamicProxy.create(Log.class);
		BankAccount account = new BankAccount(log);
		account.deposit(100);
	}
}
