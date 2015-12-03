package ss.week4.test;

import static org.junit.Assert.*;

import ss.week4.math.*;
import org.junit.Before;
import org.junit.Test;

public class PolynomialTest {

	private static final double DELTA = 1e-15;
	private Polynomial polynomial;
	
	@Before
	public void setUp() throws Exception {
		double[] array = {3, 6, 7, 8, 3};
		polynomial = new Polynomial(array);
	}

	@Test
	public void testApply() {
        assertEquals(3.0, polynomial.apply(0), DELTA);
        assertEquals(27, polynomial.apply(1), DELTA);
        assertEquals(155, polynomial.apply(2), DELTA);
        assertEquals(-1, polynomial.apply(-1), DELTA);        
    }
	
	@Test
	public void testDerivative() {
		assertTrue(polynomial.derivative() instanceof Polynomial);
	    assertEquals(6.0, polynomial.derivative().apply(0), DELTA);
	    assertEquals(56.0, polynomial.derivative().apply(1), DELTA);
	    assertEquals(226.0, polynomial.derivative().apply(2), DELTA);
	    assertEquals(4.0, polynomial.derivative().apply(-1), DELTA);
	}
	
	@Test
	public void testIntegrand() {
		assertTrue(polynomial.integrand() instanceof Polynomial);
	    assertEquals(0.0, polynomial.integrand().apply(0), DELTA);
	    assertEquals(164.0 / 15.0, polynomial.integrand().apply(1), DELTA);
	    assertEquals(1318.0 / 15.0, polynomial.integrand().apply(2), 0.000001);
	    assertEquals(-14.0 / 15.0, polynomial.integrand().apply(-1), DELTA);
	}

}

