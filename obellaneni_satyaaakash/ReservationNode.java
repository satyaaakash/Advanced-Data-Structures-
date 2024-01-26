import java.util.Date;

public class ReservationNode implements Comparable<ReservationNode> {
    // Store patron id
    private int patronId;
    // Store priority number
    private int priorityNumber;
   //Store Reservation Time
    private Date timeOfReservation;
// Constructor to create node with given parameters
    public ReservationNode(int patronId, int priorityNumber, Date timeOfReservation) {
        this.patronId = patronId;
        this.priorityNumber = priorityNumber;
        this.timeOfReservation = timeOfReservation;
    }
// Default constructor
    public ReservationNode() {

    }
// Getter method for patron id
    public int getPatronId() {
        return patronId;
    }
 // Getter method for priority number
    public int getPriorityNumber() {
        return priorityNumber;
    }

    public Date getTimeOfReservation() {
        return timeOfReservation;
    }
// Override toString method to print node in a readable format
    @Override
    public String toString() {
        return "(" + patronId + ", " + priorityNumber + ", " + timeOfReservation + ")";
    }
// Override compareTo method to compare nodes based on priority and reservation time
    @Override
    public int compareTo(ReservationNode o) {
        if (this.priorityNumber != o.priorityNumber)
            return this.priorityNumber - o.priorityNumber;
        else
            return this.timeOfReservation.compareTo(o.timeOfReservation);
    }

}
