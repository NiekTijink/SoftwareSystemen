package ss.week2.test;

import org.junit.Before;
import org.junit.Test;
import ss.week2.hotel.Guest;
import ss.week2.hotel.Room;

import static org.junit.Assert.*;

public class RoomTest {
    private Guest guest;
    private Room room;
    private String correctpass;
    private String wrongpass;

    @Before
    public void setUp() {
        guest = new Guest("Jip");
        System.out.println(guest);
        room = new Room(101);
        wrongpass = "wrong";
    	correctpass = room.getSafe().getPassword().INITIAL;
    }

    @Test
    public void testSetUp() {    	
        assertEquals(101, room.getNumber());
    }

    @Test
    public void testSetGuest() {
        room.setGuest(guest);
        assertEquals(guest, room.getGuest());
    }
    
    @Test
    public void testChangePass() {
    	assertTrue(room.getSafe().getPassword().testWord(correctpass));
    	assertFalse(room.getSafe().getPassword().setWord(correctpass, "wrong"));
    	assertTrue(room.getSafe().getPassword().setWord(correctpass, "correct"));
    }
    
    @Test
    public void testActivateDeactivateSafe() {
    	assertFalse(room.getSafe().activate(wrongpass));
    	assertTrue(room.getSafe().activate(correctpass));
    	assertFalse(room.getSafe().activate(correctpass));
    	
    	assertTrue(room.getSafe().isActive());
    	assertTrue(room.getSafe().open(correctpass));
    	assertTrue(room.getSafe().deactivate());
    	assertFalse(room.getSafe().isActive());
    	assertFalse(room.getSafe().isOpen());
    }
    
    @Test
    public void testOpenCloseSafe() {
    	assertTrue(room.getSafe().activate(correctpass));
    	assertFalse(room.getSafe().open(wrongpass));
    	assertTrue(room.getSafe().open(correctpass));

    	
    	assertTrue(room.getSafe().isOpen());
    	assertTrue(room.getSafe().close());
    	assertFalse(room.getSafe().isOpen());
    	assertTrue(room.getSafe().isActive());
    }
    
    @Test
    public void checkIn() {
    	assertEquals(guest.getRoom(), null);
    	guest.checkin(room);
    	assertEquals(guest.getRoom(), room);
    	assertEquals(room.getGuest(), guest);
    	
    	guest.checkout();
    	assertEquals(guest.getRoom(), null);
    	assertEquals(room.getGuest(), null);
    }
}
