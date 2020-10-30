package solid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * SRP: One and only one responsibility
 */

class Journal {
	private final List<String> entries = new ArrayList<>();
	private static int count = 0;

	public void add(String text) {
		entries.add((++count) + ": " + text);
	}

	public void remove(int index) {
		entries.remove(index);
	}

	@Override
	public String toString() {
		return String.join(System.lineSeparator(), entries);
	}

	// INVALID SRP: Persistence is another responsibility
	public void save(String filename) throws FileNotFoundException {
		try (PrintStream out = new PrintStream(filename)) {
			out.println(toString());
		}
	}
}

class Persistence {
	public void save(Journal journal, String filename, boolean overwrite) throws FileNotFoundException {
		final File file = new File(filename);
		if (overwrite || !file.exists()) {
			try (PrintStream out = new PrintStream(filename)) {
				out.println(journal.toString());
			}
		}
	}
}

public class SOLID_1_SingleResponsibilityPrinciple {
	public static void main(String[] args) throws FileNotFoundException {
		Journal journal = new Journal();
		journal.add("Test 1");
		journal.add("Test 2");

		Persistence persistence = new Persistence();
		persistence.save(journal, "/c/test.txt", true);
	}
}
