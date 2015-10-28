public class Seat {
    public enum enumSeat { NOTEXIST, TAKEN, NOTTAKEN }
    private String seatID;
    private enumSeat status;

    public Seat(enumSeat status, String seatID) {
        this.status = status;
        this.seatID = seatID;
    }

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

    public enumSeat getStatus() {
        return status;
    }

    public void setStatus(enumSeat status){
        this.status = status;
    }
    public void setID(int row, int col){
        char base = 'A';
        char letterRow = (char)((int)base + row);
        this.seatID = letterRow + "" + col;
    }
}
