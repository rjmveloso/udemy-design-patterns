package patterns.behavioral.visitor.acyclic;

/**
 * This type of visitor gives flexibility in cost of performance due to class validation
 */

interface Visitor {} // marker interface

abstract class Expression {
	public void accept(Visitor visitor) {
		if (visitor instanceof ExpressionVisitor) {
			((ExpressionVisitor) visitor).visit(this);
		}
	}
}


class DoubleExpression extends Expression {
	double value;

	public DoubleExpression(double value) {
		this.value = value;
	}

	@Override
	public void accept(Visitor visitor) {
		if (visitor instanceof DoubleExpressionVisitor) {
			((DoubleExpressionVisitor) visitor).visit(this);
		}
	}
}

class AdditionExpression extends Expression {
	Expression lhs, rhs;

	public AdditionExpression(Expression left, Expression right) {
		this.lhs = left;
		this.rhs = right;
	}

	@Override
	public void accept(Visitor visitor) {
		if (visitor instanceof AdditionExpressionVisitor) {
			((AdditionExpressionVisitor) visitor).visit(this);
		}
	}
}

interface ExpressionVisitor extends Visitor {
	void visit(Expression obj);
}

interface DoubleExpressionVisitor extends Visitor {
	void visit(DoubleExpression obj);
}

interface AdditionExpressionVisitor extends Visitor {
	void visit(AdditionExpression obj);
}

/**
 *  This approach allows to suppress some hierarchy types
 */
class ExpressionPrinter implements DoubleExpressionVisitor, AdditionExpressionVisitor {
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

public class AcyclicVisitor {
	public static void main(String[] args) {
		AdditionExpression expression = new AdditionExpression(
				new DoubleExpression(1),
				new AdditionExpression(
						new DoubleExpression(2),
						new DoubleExpression(3)));

		ExpressionPrinter printer = new ExpressionPrinter();
		printer.visit(expression);
		System.out.println(printer);
	}
}
