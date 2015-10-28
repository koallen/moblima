public class MovieTicket {

    private MovieShowing movie;
    private Seat seat;
    private double price;

    public MovieTicket(MovieShowing movie, Seat seat, double price) {
        this.movie = movie;
        this.seat = seat;
        this.price = price;
    }

    public String toString() {
        return (movie.toString() + "\n" + seat.toString());
    }
}
