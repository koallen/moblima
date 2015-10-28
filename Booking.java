public class Booking {
    private MovieTicket movieTicket;
    private Payment payment;

    public Booking(MovieTicket movieTicket, Payment payment) {
        this.movieTicket = movieTicket;
        this.payment = payment;
    }
    public Payment getPayment(){
        return payment;
    }
    public String toString(){
        return movieTicket.toString() + "\n" + payment.toString();
    }
}
