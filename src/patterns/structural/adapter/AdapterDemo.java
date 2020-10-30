package patterns.structural.adapter;

class Square {
	public int size;

	public Square(int size) {
		this.size = size;
	}
}

interface Rectangle {
	public int getWidth();

	public int getHeight();

	public default int getArea() {
		return getWidth() * getHeight();
	}
}

class SquareAdapter implements Rectangle {

	private Square square;

	public SquareAdapter(Square square) {
		this.square = square;
	}

	@Override
	public int getWidth() {
		return square.size;
	}

	@Override
	public int getHeight() {
		return square.size;
	}
}

public class AdapterDemo {
	public static void main(String[] args) {
		Square square = new Square(4);
		Rectangle rectangle = new SquareAdapter(square);
		System.out.println(rectangle.getArea());
	}
}
