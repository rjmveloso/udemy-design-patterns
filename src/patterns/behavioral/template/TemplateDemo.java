package patterns.behavioral.template;

class Creature {
	public int attack, health;

	public Creature(int attack, int health) {
		this.attack = attack;
		this.health = health;
	}
}

abstract class CardGame {
	public Creature[] creatures;

	public CardGame(Creature[] creatures) {
		this.creatures = creatures;
	}

	// returns -1 if no clear winner (both alive or both dead)
	public int combat(int creature1, int creature2) {
		Creature first = creatures[creature1];
		Creature second = creatures[creature2];
		hit(first, second);
		hit(second, first);
		return first.health == 0 && second.health == 0 || first.health > 0 && second.health > 0 ? -1
				: (first.health > second.health) ? creature1 : creature2;
	}

	// attacker hits other creature
	// Leave the details abstract for the concrete classes
	protected abstract void hit(Creature attacker, Creature other);
}

class TemporaryCardDamageGame extends CardGame {
	public TemporaryCardDamageGame(Creature[] creatures) {
		super(creatures);
	}

	protected void hit(Creature attacker, Creature other) {
		int oldh = other.health;
		other.health -= attacker.attack;
		if (other.health > 0) {
			other.health = oldh;
		}
	}
}

class PermanentCardDamageGame extends CardGame {
	public PermanentCardDamageGame(Creature[] creatures) {
		super(creatures);
	}

	protected void hit(Creature attacker, Creature other) {
		other.health -= attacker.attack;
	}
}

public class TemplateDemo {
	public static void main(String[] args) {
		final Creature[] creatures = { new Creature(1, 2), new Creature(1, 3), new Creature(2, 2) };
		new PermanentCardDamageGame(creatures).combat(1, 2);
	}
}
