package ss.week2;


public class ThreeWayLamp {
	public enum Light { OUT, LOW, MEDIUM, HIGH }
	
	// Private components	
	private Light light; //current light
	
	//@ invariant light() <= 3 && light() >= 0;
	 
	public ThreeWayLamp() {
		light = Light.OUT; // initial state
	}

	//@ pure
	public Light light() {
		return light;
	} 
	
	public void change() {
		switch (light) {
			case OUT: 
				light = Light.LOW; 
				break;
			case LOW: 
				light = Light.MEDIUM; 
				break;
			case MEDIUM: 
				light = Light.HIGH; 
				break;
			case HIGH: 
				light = Light.OUT; 
				break;
		}
	}
} 


