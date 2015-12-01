package ss.week2.hotel;

public class Hotel {
	public String hotelName;
	private Room room1; // kan dit anders ?
	private Room room2;
	private Guest guest;
	private Password pass;
	
	// creates a new hotel with 2 rooms and a general password
	public Hotel(String name) {
		hotelName = name;
		room1 = new Room(1);
		room2 = new Room(2);
		pass = new Password();
	}
	
	// searches for a free room
	//@ pure;
	public Room getFreeRoom() {
		if (room1.getGuest() == null) {
			return room1;
		} else if (room2.getGuest() == null) {
			return room2;
		} else {
			return null;
		}
	}
	
	// gets the room of a guest (null if the guest doesn't have a room)
	//@ pure;
	public Room getRoom(String name) {
		if (room1.getGuest() != null && room1.getGuest().getName() == name) {
			return room1;
		} else if (room2.getGuest() != null && room2.getGuest().getName() == name) {
			return room2;
		} else { 

			return null;
		}
	}
	
	// returns the password object
	//@ pure
	public Password getPassword() {
		return pass;
	}
	
	// returns the status of the hotel
	public String toString() {
		String statusSafe1;
		if (room1.getGuest() != null && room2.getGuest() != null) {
			if (room1.getSafe().isActive() == true) {
				if (room1.getSafe().isOpen() == true) {
					statusSafe1 = "open";
				}
				statusSafe1 = "gesloten";
			} else {
				statusSafe1 = "inactief";
			}
			
			String statusSafe2;
			if (room2.getSafe().isActive() == true) {
				if (room2.getSafe().isOpen() == true) {
					statusSafe2 = "open";
				}
				statusSafe2 = "gesloten";
			} else {
				statusSafe2 = "inactief";
			}
			return "Kamer 1 : " + room1.getGuest().getName() + ", status safe : " + statusSafe1 + ". Kamer 2 : " + room2.getGuest().getName() + ", status safe : " + statusSafe2;
	
		} else if (room1.getGuest() != null) {
			if (room1.getSafe().isActive() == true) {
				if (room1.getSafe().isOpen() == true) {
					statusSafe1 = "open";
				}
				statusSafe1 = "gesloten";
			} else {
				statusSafe1 = "inactief";
			}
			return "Kamer 1 : " + room1.getGuest().getName() + ", status safe : " + statusSafe1 + ". Kamer 2: Leeg.";
		} else if (room2.getGuest() != null) {
			String statusSafe2;
			if (room2.getSafe().isActive() == true) {
				if (room2.getSafe().isOpen() == true) {
					statusSafe2 = "open";
				}
				statusSafe2 = "gesloten";
			} else {
				statusSafe2 = "inactief";
			}
			return "Kamer 1: Leeg. Kamer 2 : " + room2.getGuest().getName() + ", status safe : " + statusSafe2;
		} else {
			return "Kamer 1: Leeg. Kamer 2: Leeg";
		}
	}
	
	// checks in a guest
	//@ requires getPassword().testWord(password) == true;
	//@ requires getFreeRoom() != null;
	public Room checkIn(String password, String name) {
		if (getRoom(name) == null) { 
			guest = new Guest(name);
			if (pass.testWord(password) == true && getFreeRoom() != null) {
				Room freeRoom = getFreeRoom();
				guest.checkin(freeRoom);
				return freeRoom;
			}
			
		}
		return null;
	}
	
	// checks out a guest
	//@ requires getRoom(name) != null;
	public void checkOut(String name) {
		if (getRoom(name) != null) {
			getRoom(name).getSafe().deactivate();
			getRoom(name).getGuest().checkout();
		}
	}
	
}
