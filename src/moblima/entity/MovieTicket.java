package moblima.entity;
/**
 * Represents information on a movie ticket
 * Contains the movie, seat and price of this ticket
 * @author SSP2 Team 1
 */
public class MovieTicket {
    /**
     * The movie which is allowed to use this movie ticket
     */
    private MovieShowing movieShowing;
    /**
     * The seat indicated on this ticket
     */
    private Seat seat;
    /**
     * The price of this ticket
     */
    private double price;
    /**
     * Creates a new movie tickets with given
     * movie, seat and price of this ticket
     * @param movieShowing The movie which is allowed to use this movie ticket
     * @param seat The seat indicated on this ticket
     * @param price The price of this ticket
     */
    public MovieTicket(MovieShowing movieShowing, Seat seat, double price) {
        this.movieShowing = movieShowing;
        this.seat = seat;
        this.price = price;
    }
    /**
     * Gets the string representation of this movie ticket
     * @return Gets the string representation of this movie ticket
     */
    public String toString() {
        return movieShowing.toString() + "\nTicket price: " + price + "SGD" + "\nSeat: " + seat.toString();
    }
}
