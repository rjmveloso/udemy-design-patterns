package patterns.structural.decorator;

interface Shape {
	public String info();
}

class Circle implements Shape {
	private float radius;

	public Circle(float radius) {
		this.radius = radius;
	}

	public void resize(float factor) {
		radius *= factor;
	}

	@Override
	public String info() {
		return "Circle of radius " + radius;
	}
}

class Square implements Shape {
	private float size;

	public Square(float size) {
		this.size = size;
	}

	@Override
	public String info() {
		return "Square of size " + size;
	}
}

// Can be used as a Composite pattern
class ColoredShape implements Shape {
	private Shape shape;
	private String color;

	public ColoredShape(Shape shape, String color) {
		this.shape = shape;
		this.color = color;
	}

	@Override
	public String info() {
		return shape.info() + " of color " + color;
	}
}

class TransparentShape implements Shape {
	private Shape shape;
	private int transparency;

	public TransparentShape(Shape shape, int transparency) {
		this.shape = shape;
		this.transparency = transparency;
	}

	@Override
	public String info() {
		return shape.info() + " with transparency " + transparency + "%";
	}
}

class GenericDecorator<T extends Shape> implements Shape {
	private Shape shape;
	private String attribute;

	public GenericDecorator(T shape, String attribute) {
		this.shape = shape;
		this.attribute = attribute;
	}

	@Override
	public String info() {
		return shape.info() + " with attr " + attribute;
	}
}

class RoundedSquare extends Square {
	public RoundedSquare(float size) {
		super(size);
	}

	@Override
	public String info() {
		return "Rounded Square";
	}
}

/**
 * Can be used as an extension to prevent a break in OCP
 */
public class DecoratorDemo {
	public static void main(String[] args) {
		Shape shape = new TransparentShape(
				new ColoredShape(new Circle(5f), "blue"), 10);
		System.out.println(shape.info());
		System.out.println(new GenericDecorator<Shape>(new RoundedSquare(2f), "degradé").info());
	}
}
