package patterns.structural.memento;

/**
 * Saves the state of the system allowing it to rollback arbitrarily.
 * It's better to use the Command Pattern. It only saves the action and not all the state.
 * 
 * Memento can be used to interop:
 * C/C++                           | Java
 * class Foo { void bar(); }       | Java cannot call Foo.bar directly
 *                                 |
 * int createFoo(params);          | It can call by JNI a function that returns a token for a Memento
 *                                 | Somewhere in C/C++ side a reference on a storage location is reserverd
 * void bar(Foo&);                 | Java cannot call a function with reference to Foo
 * void (int ref);                 | But it can call an overload that receives the token (Memento) for the storage location
 */
class Memento {
	private int balance;
	
	// prevent cheating with setter
	public int getBalance() {
		return balance;
	}

	public Memento(int balance) {
		this.balance = balance;
	}
}

class BankAccount {
	public int balance;

	public BankAccount(int balance) {
		this.balance = balance;
	}

	public Memento deposit(int amount) {
		balance += amount;
		return new Memento(balance);
	}

	public void restore(Memento memento) {
		balance = memento.getBalance();
	}

	@Override
	public String toString() {
		return "BankAccount{balance=" + balance + "}";
	}
}

public class MementoDemo {
	public static void main(String[] args) {
		BankAccount ba = new BankAccount(100);
		Memento m1 = ba.deposit(50); // 150
		Memento m2 = ba.deposit(25); // 175

		System.out.println(ba);

		ba.restore(m1);
		System.out.println(ba);

		ba.restore(m2);
		System.out.println(ba);
	}
}
