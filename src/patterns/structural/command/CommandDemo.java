package patterns.structural.command;

import java.util.Arrays;
import java.util.List;

import patterns.structural.command.BankAccountCommand.Action;

class BankAccount {
	private int balance;
	private int overdraft = -500;

	public boolean deposit(int amount) {
		balance += amount;
		System.out.println("Deposited " + amount + " balance=" + balance);
		return true;
	}

	public boolean withdraw(int amount) {
		if (overdraft <= balance - amount) {
			balance -= amount;
			System.out.println("Withdrew " + amount + " balance=" + balance);
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Balance=" + balance;
	}
}

interface Command {
	public void call();

	public void undo();
}

class BankAccountCommand implements Command {
	private BankAccount account;
	private Action action;
	private int amount;
	private boolean success;

	public enum Action {
		DEPOSIT, WITHDRAW
	}

	public BankAccountCommand(BankAccount account, Action action, int amount) {
		super();
		this.account = account;
		this.amount = amount;
		this.action = action;
	}

	@Override
	public void call() {
		switch (action) {
		case DEPOSIT:
			success = account.deposit(amount);
			break;
		case WITHDRAW:
			success = account.withdraw(amount);
			break;
		}
	}

	@Override
	public void undo() {
		if (success) {
			switch (action) {
			case DEPOSIT:
				account.withdraw(amount);
				break;
			case WITHDRAW:
				account.deposit(amount);
				break;
			}
		}
	}
}

public class CommandDemo {
	public static void main(String[] args) {
		BankAccount account = new BankAccount();
		System.out.println(account);
		
		List<Command> commands = Arrays.asList(
				new BankAccountCommand(account, Action.DEPOSIT, 100),
				new BankAccountCommand(account, Action.WITHDRAW, 1000));
		
		for (Command command : commands) {
			command.call();
			System.out.println(account);
		}

		//Collections.reverse(commands);

		for (Command command : commands) {
			command.undo();
			System.out.println(account);
		}
	}
}
