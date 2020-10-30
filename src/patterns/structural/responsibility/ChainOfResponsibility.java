package patterns.structural.responsibility;

class Creature {
	public String name;
	public int attack;
	public int defense;

	public Creature(String name, int attack, int defense) {
		this.name = name;
		this.attack = attack;
		this.defense = defense;
	}

	@Override
	public String toString() {
		return "Name: " + name + " Attack: " + attack + " Defense: " + defense;
	}
}

interface Modifier {
	public void handle();
}

class CreatureModifier implements Modifier {
	protected Creature creature;
	private CreatureModifier next;

	public CreatureModifier(Creature creature) {
		this.creature = creature;
	}

	public void add(CreatureModifier cm) {
		if (next != null) {
			next.add(cm);
		} else {
			this.next = cm;
		}
	}
	@Override
	public void handle() {
		if (next != null) {
			next.handle();
		}
	}
}

class DoubleAttackModifier extends CreatureModifier implements Modifier {
	public DoubleAttackModifier(Creature creature) {
		super(creature);
	}

	@Override
	public void handle() {
		System.out.println("Doubling " + creature.name + " attack");
		creature.attack *= 2;
		super.handle();
	}
}

class IncreaseDefenseModifier extends CreatureModifier implements Modifier {
	public IncreaseDefenseModifier(Creature creature) {
		super(creature);
	}

	@Override
	public void handle() {
		System.out.println("Increasing " + creature.name + " defense");
		creature.defense += 3;
		super.handle();
	}
}

class NoBonusesModifier extends CreatureModifier implements Modifier {
	public NoBonusesModifier(Creature creature) {
		super(creature);
	}
	
	@Override
	public void handle() {
		System.out.println("You have no bonuses");
		// break the chain no calling super.handle
		//super.handle();
	}
}

public class ChainOfResponsibility {
	public static void main(String[] args) {
		Creature creature = new Creature("Goblin", 2, 2);
		CreatureModifier root = new CreatureModifier(creature);
		//root.add(new NoBonusesModifier(creature));
		root.add(new DoubleAttackModifier(creature));
		root.add(new IncreaseDefenseModifier(creature));
		root.handle();
		System.out.println(creature);
	}
}
