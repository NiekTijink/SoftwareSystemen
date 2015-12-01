package ss.week2;

class ThreeWayLampTest {
	private ThreeWayLamp signal; //the object to test
	
	public ThreeWayLampTest() {
		// create a signal to test
		signal = new ThreeWayLamp();
	}
	
	public void runTest() {
		testInitialState();
		testChange();
	}
	
	
	private void testInitialState() {
		System.out.println("testInitialState:");
		System.out.println("Initial light: " + signal.light());
	}
	
	private void testChange() {
		System.out.println("testChange:");
		System.out.println("Starting light: " + signal.light());
		signal.change();
		System.out.println("After 1 change: " + signal.light());
		signal.change();
		System.out.println("After 2 changes: " + signal.light());
		signal.change();
		System.out.println("After 3 changes: " + signal.light());
		signal.change();
		System.out.println("After 4 changes: " + signal.light());

	}
}
