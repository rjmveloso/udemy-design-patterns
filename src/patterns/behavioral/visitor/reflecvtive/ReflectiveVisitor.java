package patterns.behavioral.visitor.reflecvtive;

abstract class Expression {
}

class DoubleExpression extends Expression {
	double value;

	public DoubleExpression(double value) {
		this.value = value;
	}
}

class AdditionExpression extends Expression {
	Expression lhs, rhs;

	public AdditionExpression(Expression left, Expression right) {
		this.lhs = left;
		this.rhs = right;
	}
}

// Problem if we forgot any branch if conditional clauses
class ExpressionPrinter {
	public static void print(Expression e, StringBuilder sb) {
		if (e.getClass() == DoubleExpression.class) {
			sb.append(((DoubleExpression) e).value);
		} else if (e.getClass() == AdditionExpression.class) {
			AdditionExpression ae = (AdditionExpression) e;
			sb.append("(");
			print(ae.lhs, sb);
			sb.append("+");
			print(ae.rhs, sb);
			sb.append(")");
		}
	}

//	public static void print(DoubleExpression e, StringBuilder sb) {
//	}

//	public static void print(AdditionExpression e, StringBuilder sb) {
//	}
}

public class ReflectiveVisitor {
	public static void main(String[] args) {
		Expression expression = new AdditionExpression(
				new DoubleExpression(1),
				new AdditionExpression(
						new DoubleExpression(2),
						new DoubleExpression(3)));

		StringBuilder sb = new StringBuilder();
		ExpressionPrinter.print(expression, sb);
		System.out.println(sb);
	}
}
