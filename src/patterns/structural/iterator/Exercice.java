package patterns.structural.iterator;

import java.util.Iterator;

class Node<T> {
	public T value;
	public Node<T> left, right, parent;

	public Node(T value) {
		this.value = value;
	}

	public Node(T value, Node<T> left, Node<T> right) {
		this.value = value;
		this.left = left;
		this.right = right;

		left.parent = right.parent = this;
	}

	public Iterator<Node<T>> preOrder() {
		return new Iterator<Node<T>>() {
			private Node<T> current = null;

			public boolean hasNext() {
				if (current == null) {
					return left != null || right != null;
				} else if (current.left != null || current.right != null) {
					return true;
				} else if (current.parent != null && current.parent.right != null) {
					return true;
				}
				return false;
			}

			public Node<T> next() {
				if (current == null) {
					current = Node.this;
					return current;
				}
				
				if (current.left != null) {
					current = current.left;
				} else if (current.right != null) {
					current = current.right;
				} else {
					current = getNextRightNode();
				}

				return current;
			}

			private Node<T> getNextRightNode() {
				Node<T> node = current;
				while(node.parent != null) {
					if (node.parent.right == null || node.parent.right == node) {
						node = node.parent;
					} else {
						return node.right;
					}
				}
				return null;
			}
		};
	}
}

public class Exercice {

	public static void main(String[] args) {
		Node<Integer> left = new Node(2);
		Node<Integer> right = new Node(3);
		Node<Integer> root = new Node(1, left, right);
		Iterator<Node<Integer>> itr = root.preOrder();
		while(itr.hasNext()) {
			System.out.println(itr.next().value);
		}
	}

}
