package moblima.entity;

/**
 * Represents a booking record.
 * Contains information about payment and movie ticket.
 * @author SSP2 Team 1
 */
public class Booking {
    /**
     * The movie ticket for a booking
     */
    private MovieTicket movieTicket;
    /**
     * The payment information for a booking
     */
    private Payment payment;

    /**
     * Create a new booking record with given movie ticket and payment
     * @param movieTicket The movie ticket for this booking
     * @param payment The payment made for this booking
     */
    public Booking(MovieTicket movieTicket, Payment payment) {
        this.movieTicket = movieTicket;
        this.payment = payment;
    }

    /**
     * Get payment information about this booking
     * @return the payment information
     */
    public Payment getPayment(){
        return payment;
    }
    /**
     * Get the string representation of this booking record
     * @return the string representation of this booking record
     */
    public String toString(){
        return movieTicket.toString() + "\n\n" + payment.toString();
    }
}
