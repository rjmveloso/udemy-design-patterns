package patterns.structural.bridge;

/**
 * Decouple abstraction from implementation Prevents "Cartesian product"
 * complexity explosion
 * 
 * Both abstraction and implementation can have inheritance
 */
abstract class Shape {
	protected Renderer renderer;

	public Shape(Renderer renderer) {
		this.renderer = renderer;
	}

	public abstract void draw();
}

class Circle extends Shape {
	public Circle(Renderer renderer) {
		super(renderer);
	}

	public void draw() {
		renderer.render("Circle");
	}
}

class Square extends Shape {
	public Square(Renderer renderer) {
		super(renderer);
	}

	@Override
	public void draw() {
		renderer.render("Square");
	}
}

interface Renderer {
	public void render(String type);
}


class VectorRenderer implements Renderer {
	@Override
	public void render(String type) {
		System.out.println("Drawing a vector of " + type);
	}
}

class RasterRenderer implements Renderer {
	@Override
	public void render(String type) {
		System.out.println("Drawing a raster of " + type);
	}
}

public class BridgeDemo {
	public static void main(String[] args) {
		Renderer renderer = new VectorRenderer();
		Shape circle = new Circle(renderer);
		circle.draw();
	}
}
