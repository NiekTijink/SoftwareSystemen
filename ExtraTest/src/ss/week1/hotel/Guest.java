package ss.week1.hotel;

/**
 * Create a guest.
 * @author Thomas Kolner
 * @version $Revision: 1.0 $
 */

public class Guest {
	// ------------------ Instance variables ----------------
	
	private Room number;
	private String guest;
	
	 /**
     * Creates a <code>Guest</code> with the given name.
     * @param n - name of the new <code>Guest</code>
     */
	public Guest(java.lang.String n) {
		guest = n;
	}
	
	/**
     * Returns the name of this <code>Guest</code>.
     */
	public java.lang.String getName() {
		return guest;
	}
	
	/**
     * Returns the Room that is rented by this Guest.
     * @return Room rented by thus Guest; <code>null</code> if this Guest does not rent a room
     */
	public Room getRoom() {
		return number;
	}
	
	/**
     * Rents a Room to this Guest. This is only possible if this Guest does not already have a Room. and the Room to be assigned is not already rented. Also adapts the Guest-reference of the Room.
     * @param r - Room to be rented to this Guest; may not be null
     * @return true if checkin succeeded; false if this Guest already had a Room, or r already had a Guest.
     */
	public boolean checkin(Room r) {
		if (r.getGuest() == null && this.getRoom() == null) {
			r.setGuest(this);
			this.number = r;
			return true;
		} else {
			return false;
		}
	}

	/**
     * Sets the Room of this Guest to null. Also resets the Guest-reference of the (current) Room.
     * @return true if this action succeeded; false if Guest does not have a Room when this method is called.
     */
	public boolean checkout() {
		if (this.getRoom() == null) {
			return false;
		} else {
			number.setGuest(null);
			this.number = null; // gast heeft geen kamer meer
			return true;
		} 
	}


	public String toString() {
		return "Guest = " + guest;
	}
}

