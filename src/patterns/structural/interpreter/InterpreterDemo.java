package patterns.structural.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class Token {
	enum Type {
		SIGN, LPAREN, RPAREN, INTEGER
	}

	Type type;
	String value;

	public Token(Type type, String value) {
		this.type = type;
		this.value = value;
	}

	@Override
	public String toString() {
		return "'" + value + "'";
	}
}

interface Element {
	public int eval();
}

class Operand implements Element {
	int value;

	public Operand(int value) {
		this.value = value;
	}

	@Override
	public int eval() {
		return value;
	}
}

class BinaryOperation implements Element {
	String sign;
	// composition pattern
	private Element left, right;

	@Override
	public int eval() {
		switch (sign) {
		case "+":
			return left.eval() + right.eval();
		case "-":
			return left.eval() - right.eval();
		default:
			return 0;
		}
	}

	void add(Element element) {
		if (left == null) {
			left = element;
		} else {
			right = element;
		}
	}
}

public class InterpreterDemo {

	private static List<Token> lex(String input) {
		final List<Token> tokens = new ArrayList<>();

		for (int i = 0; i < input.length(); i++) {
			final char val = input.charAt(i);
			switch (val) {
			case ' ':
				continue;
			case '+':
			case '-':
				tokens.add(new Token(Token.Type.SIGN, String.valueOf(val)));
				break;
			case '(':
				tokens.add(new Token(Token.Type.LPAREN, String.valueOf(val)));
				break;
			case ')':
				tokens.add(new Token(Token.Type.RPAREN, String.valueOf(val)));
				break;
			default:
				final StringBuilder sb = new StringBuilder();
				for (int j = i; j < input.length(); j++) {
					final char num = input.charAt(j);
					if (Character.isDigit(num)) {
						sb.append(num);
					} else {
						tokens.add(new Token(Token.Type.INTEGER, String.valueOf(sb)));
						i = j - 1;
						break;
					}
				}
				break;
			}
		}

		return tokens;
	}

	private static Element parse(List<Token> tokens) {
		final BinaryOperation result = new BinaryOperation();

		for (int i = 0; i < tokens.size(); i++) {
			final Token token = tokens.get(i);
			switch (token.type) {
			case INTEGER:
				Operand operand = new Operand(Integer.parseInt(token.value));
				result.add(operand);
				break;
			case SIGN:
				result.sign = token.value;
				break;
			case LPAREN:
				List<Token> subexpression = new ArrayList<>();
				for (int j = i + 1; j < tokens.size(); j++) {
					if (tokens.get(j).type != Token.Type.RPAREN) {
						subexpression.add(tokens.get(j));
					} else {
						break;
					}
				}

//				List<Token> subexpression = takeWhile(tokens.spliterator(), t -> t.type != Token.Type.RPAREN)
//						.skip(i + 1).collect(Collectors.toList());
				result.add(parse(subexpression));
				i += subexpression.size();
				break;
			case RPAREN:
				break;
			}
		}
		return result;
	}

	private static <T> Stream<T> takeWhile(Spliterator<T> itr, Predicate<? super T> predicate) {
		return StreamSupport.stream(new Spliterators.AbstractSpliterator<T>(itr.estimateSize(), 0) {
			boolean stop = false;

			@Override
			public boolean tryAdvance(Consumer<? super T> action) {
				return !stop && itr.tryAdvance(elem -> {
					if (predicate.test(elem)) {
						action.accept(elem);
					} else {
						stop = true;
					}
				});
			}
		}, false);
	}

	public static void main(String[] args) {
		String input ="(13+4) - (12+1)";
		List<Token> tokens = lex(input);
		Element result = parse(tokens);
		System.out.println(input + " = " + result.eval());
	}
}
