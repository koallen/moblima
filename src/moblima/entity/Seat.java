package moblima.entity;

/**
 * Represents a Seat in the cinema
 * Contains information of seat ID and
 * its status
 * @author SSP2 Team 1
 */

public class Seat {
    /**
     * Enumeration for statuses of Seat
     * Including NOTEXIST, TAKEN and NOTTAKEN
     */
    public enum enumSeat { NOTEXIST, TAKEN, NOTTAKEN }
    /**
     * The seat ID of this Seat
     * Represents the row and colume of this Seat in the cinema
     */
    private String seatID;
    /**
     * The status of this Seat
     */
    private enumSeat status;
    /**
     * Creates a new Seat with given status and seatID
     * @param status The status of this Seat
     * @param seatID The seat ID of this Seat
     */
    public Seat(enumSeat status, String seatID) {
        this.status = status;
        this.seatID = seatID;
    }
    /**
     * Gets the string representation of this Seat
     * @return the string representation of this Seat
     */
    public String toString() {
        if(status == enumSeat.NOTEXIST){
            return "   ";
        }
        else if (status == enumSeat.TAKEN){
            return "[X]";
        }
        else {
            return "[ ]";
        }
    }
    /**
     * Gets the status of this Seat
     * @return status of this Seat
     */
    public enumSeat getStatus() {
        return status;
    }
    /**
     * Changes the status of this Seat
     * @param status The new status of this Seat
     */
    public void setStatus(enumSeat status) {
        this.status = status;
    }
    /**
     * Changes the seatID of this Seat
     * @param row The row number of this Seat in cinema
     * @param col The colume number of this Seat in cinema
     */
    public void setID(int row, int col) {
        char base = 'A';
        char letterRow = (char)((int)base + row);
        this.seatID = letterRow + "" + col;
    }

    public String getSeatID() {
        return seatID;
    }
}
