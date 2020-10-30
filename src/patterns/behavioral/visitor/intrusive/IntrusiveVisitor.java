package patterns.behavioral.visitor.intrusive;

/**
 * This behavior violetes the SRP and the OCP
 */
abstract class Expression {
	public abstract void print(StringBuilder sb);
}

class DoubleExpression extends Expression {
	private double value;

	public DoubleExpression(double value) {
		this.value = value;
	}
	
	@Override
	public void print(StringBuilder sb) {
		sb.append(value);	
	}
}

class AdditionExpression extends Expression {
	private Expression lhs, rhs;

	public AdditionExpression(Expression left, Expression right) {
		this.lhs = left;
		this.rhs = right;
	}

	@Override
	public void print(StringBuilder sb) {
		sb.append("(");
		lhs.print(sb);
		sb.append("+");
		rhs.print(sb);
		sb.append(")");
	}
}

public class IntrusiveVisitor {
	public static void main(String[] args) {
		Expression expression = new AdditionExpression(
				new DoubleExpression(1),
				new AdditionExpression(
						new DoubleExpression(2),
						new DoubleExpression(3)));

		StringBuilder sb = new StringBuilder();
		expression.print(sb);
		System.out.println(sb);
	}
}
