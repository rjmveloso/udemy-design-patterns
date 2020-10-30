package patterns.structural.composite;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class GraphicObject {
	public String name = "Group";
	public String color;
	
	public Set<GraphicObject> children = new HashSet<>();
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		print(sb, 0);
		return sb.toString();
	}

	private void print(StringBuilder sb, int depth) {
		sb.append(String.join("", Collections.nCopies(depth, " ")));
		sb.append(name).append(color != null ? ": " + color : "");
		sb.append(System.lineSeparator());
		for (GraphicObject child : children) {
			child.print(sb, depth + 1);
		}
	}
}

class Circle extends GraphicObject {
	public Circle(String color) {
		this.name = "Circle";
		this.color = color;
	}
}

class Square extends GraphicObject {
	public Square(String color) {
		this.name = "Square";
		this.color = color;
	}
}

public class CompositeDemo {
	public static void main(String[] args) {
		GraphicObject drawing = new GraphicObject();
		drawing.name = "Canvas";
		drawing.children.add(new Circle("Blue"));
		drawing.children.add(new Square("Green"));
		
		GraphicObject group = new GraphicObject();
		group.children.add(new Square("Red"));
		drawing.children.add(group);
		
		System.out.println(drawing);
	}
}
