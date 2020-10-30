package solid;

/**
 * ISP
 */

class Document {
}

interface PrinterDevice {
	public void print(Document d);
	public void scan(Document d);
	public void fax(Document d);
}

class OldPrinterMachine implements PrinterDevice {
	@Override
	public void print(Document d) {
		// print
	}
	@Override
	public void scan(Document d) {
		// NOP
	}
	@Override
	public void fax(Document d) {
		// NOP
	}
}

interface Printer {
	public void print(Document d);
}

interface Scanner {
	public void scan(Document d);
}

class OldPrinterMachineEnhanced implements Printer {
	@Override
	public void print(Document d) {
	}
}

interface MultiFunctionalDevice extends Printer, Scanner {
}

class PhotocopierMachine implements MultiFunctionalDevice /*Printer, Scanner*/ {
	@Override
	public void scan(Document d) {
	}
	@Override
	public void print(Document d) {
	}
}

public class SOLID_4_InterfaceSegregationPrinciple {
	public static void main(String[] args) {

	}
}
