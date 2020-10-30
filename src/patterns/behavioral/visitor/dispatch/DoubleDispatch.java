package patterns.behavioral.visitor.dispatch;

/**
 *  The problem of this approach is the tight coupling between Visitor and Expression
 */

interface ExpressionVisitor {
	public void visit(DoubleExpression e);
	public void visit(AdditionExpression e);
}

abstract class Expression {
	abstract void accept(ExpressionVisitor visitor);
}

class DoubleExpression extends Expression {
	double value;

	public DoubleExpression(double value) {
		this.value = value;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}
}

class AdditionExpression extends Expression {
	Expression lhs, rhs;

	public AdditionExpression(Expression left, Expression right) {
		this.lhs = left;
		this.rhs = right;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}
}

class ExpressionPrinter implements ExpressionVisitor {
	private StringBuilder sb = new StringBuilder();

	@Override
	public void visit(DoubleExpression e) {
		sb.append(e.value);
	}

	@Override
	public void visit(AdditionExpression e) {
		sb.append("(");
		e.lhs.accept(this);
		sb.append("+");
		e.rhs.accept(this);
		sb.append(")");
	}

	@Override
	public String toString() {
		return sb.toString();
	}
}

class ExpressionCalculator implements ExpressionVisitor {
	private double result;

	@Override
	public void visit(DoubleExpression e) {
		result = e.value;
	}
	
	@Override
	public void visit(AdditionExpression e) {
		e.lhs.accept(this);
		double a = result;
		e.rhs.accept(this);
		double b = result;
		result = a + b;
	}

	@Override
	public String toString() {
		return String.valueOf(result);
	}
}

public class DoubleDispatch {
	public static void main(String[] args) {
		AdditionExpression expression = new AdditionExpression(
				new DoubleExpression(1),
				new AdditionExpression(
						new DoubleExpression(2),
						new DoubleExpression(3)));

		ExpressionPrinter printer = new ExpressionPrinter();
		printer.visit(expression);
		System.out.println(printer);

		ExpressionCalculator calculator = new ExpressionCalculator();
		calculator.visit(expression);
		System.out.println(calculator);
	}
}
