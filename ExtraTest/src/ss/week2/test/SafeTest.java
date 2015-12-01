package ss.week2.test;

import static org.junit.Assert.*;

import ss.week2.hotel.Password;
import ss.week2.hotel.Safe;
import ss.week3.pw.*;
import org.junit.Before;
import org.junit.Test;

public class SafeTest {
	private Safe safe;
	private String correctPassword;
	private String wrongPassword;
	private Password pass;
	
	@Before
	public void setUp() throws Exception {
		safe = new Safe();
		// initialisation of password-variable
		pass = new Password(new BasicChecker());
        correctPassword = pass.checker().generatePassword();
        wrongPassword = "invalid";
	}

	@Test
	public void testActivateCorrectPassword() {
		//Test aanzetten wanneer hij uit staat
		assertFalse(safe.isActive());
		assertTrue(safe.activate(correctPassword));
		//Test aanzetten wanneer hij al aan staat
		assertFalse(safe.activate(correctPassword));
	}
	
	@Test
	public void testActivateWrongPassword() {
		//Test aanzetten met verkeerde wachtwoord
		assertFalse(safe.isActive());
		assertFalse(safe.activate(wrongPassword));
	}
	
	@Test
	public void testOpenCorectPassword() {
		//Testen openen correct wachtwoord, nog niet open en wel actief
		assertTrue(safe.activate(correctPassword));
		assertFalse(safe.isOpen());
		assertTrue(safe.open(correctPassword));
		//Testen openen correct wachtwoord, nog niet open, niet actief
		safe.close();
		safe.deactivate();
		assertFalse(safe.open(correctPassword));
	}
	
	@Test
	public void testOpenWrongPassword() {
		assertTrue(safe.activate(correctPassword));
		assertFalse(safe.isOpen());
		assertFalse(safe.open(wrongPassword));
	}
	
	
}
