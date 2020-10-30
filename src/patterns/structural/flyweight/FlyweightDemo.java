package patterns.structural.flyweight;

import java.util.ArrayList;
import java.util.List;

/**
 * Flyweight is intended for space optimization
 */
class TextFormatter {
	private String text;
	private List<FormatRange> formatters = new ArrayList<>();

	public TextFormatter(String text) {
		this.text = text;
	}

	class FormatRange {
		public int start, end;
		public boolean capitalize, bold, italic;

		private FormatRange(int start, int end) {
			this.start = start;
			this.end = end;
		}

		public boolean convers(int position) {
			return position >= start && position <= end;
		}
	}

	// This is the Flyweight pattern
	// Where we centralize some information in blocks
	public FormatRange getRange(int start, int end) {
		FormatRange format = new FormatRange(start, end);
		formatters.add(format);
		return format;
	}

//	@Override
//	public String toString() {
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < text.length(); i++) {
//			char c = text.charAt(i);
//			for (FormatRange formatter : formatters) {
//				if (formatter.convers(i)) {
//					if (formatter.capitalize) {
//						c = Character.toUpperCase(c);
//					}
//				}
//			}
//			sb.append(c);
//		}
//		return sb.toString();
//	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(text);
		for (FormatRange formatter : formatters) {
			for (int i = formatter.start; i <= formatter.end; i++) {
				if (formatter.capitalize) {
					char c = sb.charAt(i);
					sb.setCharAt(i, Character.toUpperCase(c));
				}
			}
		}
		return sb.toString();
	}
}

public class FlyweightDemo {
	public static void main(String[] args) {
		TextFormatter tf = new TextFormatter("This is a brave new world!");
		tf.getRange(10,  15).capitalize = true;
		System.out.println(tf);
	}
}
