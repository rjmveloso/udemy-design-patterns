package solid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * OCP + Specification EIP pattern:
 * Open for extension, closed for modification.
 */

enum Color {
	RED, GREEN, BLUE;
}

enum Size {
	SMALL, MEDIUM, LARGE, XLARGE;
}

class Product {
	public Color color;
	public Size size;
	public String name;

	public Product(String name, Color color, Size size) {
		this.name = name;
		this.color = color;
		this.size = size;
	}
}

// Specification pattern
interface Filter<T> {
	public Stream<T> filter(List<T> items, Specification<T> spec);
}

interface Specification<T> {
	public boolean verify(T item);
}

class ColorSpecification implements Specification<Product> {
	public Color color;

	public ColorSpecification(Color color) {
		this.color = color;
	}

	@Override
	public boolean verify(Product item) {
		return item.color == color;
	}
}

class SizeSpecification implements Specification<Product> {
	public Size size;

	@Override
	public boolean verify(Product item) {
		return item.size == size;
	}
}

// And even a combinator specification
class CombinatorSpecification<T> implements Specification<T> {

	private Specification<T>[] specs;

	public CombinatorSpecification(Specification<T>[] specs) {
		this.specs = specs;
	}

	@Override
	public boolean verify(T item) {
		for (Specification<T> spec : specs) {
			if (!spec.verify(item)) {
				return false;
			}
		}
		return true;
	}
}

public class SOLID_2_OpenClosedPrinciple {

	// INVALID OCP: For each filter required, this class needs to be changed
	static class ProductFilter {
		public Stream<Product> filterByColor(List<Product> products, Color color) {
			return products.stream().filter(p -> p.color == color);
		}

		public Stream<Product> filterBySize(List<Product> products, Size size) {
			return products.stream().filter(p -> p.size == size);
		}
	}

	// VALID OCP: New filter for each necessity based on Specification pattern
	static class BaseProductFilter implements Filter<Product> {
		@Override
		public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
			return items.stream().filter(i -> spec.verify(i));
		}
	}

	public static void main(String[] args) {
		Product apple = new Product("Apple", Color.GREEN, Size.SMALL);
		Product tree = new Product("Tree", Color.GREEN, Size.LARGE);
		Product house = new Product("House", Color.BLUE, Size.LARGE);

		final List<Product> products = Arrays.asList(apple, tree, house);

		Stream<Product> result = new BaseProductFilter().filter(products, new ColorSpecification(Color.GREEN));
		result.forEach(p -> System.out.println(" - " + p.name));
	}
}
