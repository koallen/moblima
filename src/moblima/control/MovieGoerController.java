package moblima.control;

import java.util.*;
import java.text.SimpleDateFormat;
import moblima.entity.Seat;
import moblima.entity.Review;
import moblima.entity.Booking;
import moblima.entity.Payment;
import moblima.entity.Cinema;
import moblima.entity.MovieInfo;
import moblima.entity.MovieTicket;
import moblima.entity.MovieShowing;
/**
 * Represents a controller for all moviegoer module functions
 * The moviegoer module functions include Search/List movie
 * View movie details – including reviews and ratings
 * Check seat availability and selection of seat/s.
 * Book and purchase ticket
 * View booking history
 * List the Top 5 ranking by ticket sales OR by overall reviewers’ ratings
 * @author SSP2 Team1
 */
public class MovieGoerController extends UserController{
    /**
     * The moviegoer controller created by itself
     * Since MovieGoerController is a singleton class
     * It can only be created by itself
     */
    private static MovieGoerController movieGoerController = null;
    /**
     * The list of the holiday dates
     */
    private List<Date> holidays;
    /**
     * The list of all movie informations
     */
    private List<MovieInfo> movies;
    /**
     * The list of booking records
     */
    private List<Booking> bookings;
    /**
     * The list of the information for movies to be shown
     */
    private List<MovieShowing> movieShowings;
    /**
     * To avoids other classes to create a moviegoer controller
     * It can only be created by itself
     */
    private MovieGoerController() {
        this.holidays = null;
        this.movies = null;
        this.bookings = null;
        this.movieShowings = null;
    }
    /**
     * Gets a moviegoer controller
     * If moviegoer controller has not been created yet,
     * create a new moviegoer controller then return it
     * @return The moviegoer controller
     */
    public static MovieGoerController getInstance() {
        if (movieGoerController == null) {
            movieGoerController = new MovieGoerController();
        }
        return movieGoerController;
    }
    /**
     * Initialize all the data which moviegoer may need to use/modify
     * Including movie information, booking records,
     * movies to be shown and holidays
     * @param holidays The list of the holiday dates
     * @param movies The list of all movie information
     * @param bookings The list of booking records
     * @param movieShowings The list of the information for movies to be shown
     */
    public void initialize(ArrayList<Date> holidays, ArrayList<MovieInfo> movies, ArrayList<Booking> bookings, ArrayList<MovieShowing> movieShowings) {
        this.holidays = holidays;
        this.movies = movies;
        this.bookings = bookings;
        this.movieShowings = movieShowings;
    }
    /**
     * Gets the showing movie with given index
     * @param choice The index of the showing movie
     * @return The required showing movie
     */
    public MovieShowing getMovies(int choice){
        return movieShowings.get(choice);
    }
    /**
     * Gets the list of all movies
     * @return the list of all movies
     */
    public ArrayList<MovieInfo> listAllMovies(){
        return (ArrayList<MovieInfo>)movies;
    }
    /**
     * User searches movie information with movie index
     * Return the movie information with given index
     * @param index index of the movie
     * @return The required movie information
     */
    public MovieInfo searchForMovie(int index) {
        return movies.get(index);
    }
    /**
     * User searches showing movie with movie index
     * Return the showing movie information with given index
     * @param index index of the showing movie
     * @return The required showing movie information
     */
    public MovieShowing searchForMovieShowing(int index) {
        return movieShowings.get(index);
    }
    /**
     * Lists all the showing information of a movie
     * @param movie The movie information
     * @return The list of the showing information of that movie
     */
    public ArrayList<MovieShowing> listMovieShowing(MovieInfo movie) {
        ArrayList<MovieShowing> results = new ArrayList<MovieShowing>();
        for (MovieShowing movieShowing: movieShowings) {
            if (movieShowing.getMovie().getTitle().equals(movie.getTitle())) {
                results.add(movieShowing);
            }
        }
        return results;
    }
    /**
     * Gets the seat information of a movie showing
     * @param movieShowing The movie showing which the seat information is wanted
     */
    public Seat[][] getSeats(MovieShowing movieShowing) {
        return movieShowing.getCinema().getSeatLayout();
    }
    /**
     * Gets the movie details of a movie
     * @param movieName The name of the movie
     */
    public void viewMovieDetail(String movieName) {
        //MovieInfo movie = search(movieName);
        //movie.toString();
    }
    /**
     * Select seat during booking
     * @param movieShowing The movie which user wants to book
     * @param row The row of seats which user chooses to book
     * @param col The colume of seats which user chooses to book
     * @return The seat selected
     */
    public Seat selectSeat(MovieShowing movieShowing, int row, int col){
        Seat[][] seats = movieShowing.getCinema().getSeatLayout();
        Seat seat = null;
        int i, count = 0;
        row--;

        // ignore nonexist seats and get the correct seat according to row and col number
        for (i=0; i<seats[row].length; ++i) {
            if (seats[row][i].getStatus() != Seat.enumSeat.NOTEXIST) {
                count++;
                if (count == col) {
                    seat = seats[row][i];
                    break;
                }
            }
        }

        if (seat.getStatus() == Seat.enumSeat.TAKEN) {
            //System.out.println("This seat is taken, please choose another one.");
            return null;
        }
        else if (seat.getStatus() == Seat.enumSeat.NOTTAKEN) {
            seat.setStatus(Seat.enumSeat.TAKEN);
            seat.setID(row, col);
            //System.out.println("Seat is successfully taken");
            return seat;
        }
        else {
            //System.out.println("Error");
            return null;
        }

    }
    /**
     * Books a ticket and purchases it
     * @param movieTicket THe ticket users want to book
     * @param payment The payment information of that ticket
     */
    public void book(MovieTicket movieTicket, Payment payment, MovieInfo movie){
        Booking booking = new Booking(movieTicket, payment);
        bookings.add(booking);
        movie.setSale(movie.getSale()+1);

    }
    /**
     * Calculate the ticket price for a movie tickets
     * Ticket all has a base price
     * The final price is varied depending on
     * if they are previliged person
     * @param movieShowing The movie user wants to book
     * @param discount Whether the movie goer can enjoy discount
     * @return The final price of the ticket
     */
    public double calculatePrice(MovieShowing movieShowing, boolean discount) {
        double price;
        MovieInfo movie;
        Date showTime;

        movie = movieShowing.getMovie();
        price = movie.getBasePrice();
        showTime = movieShowing.getShowTime();
        // check type of movie
        switch (movie.getTypeOfMovie()) {
            case THREED:
            case BLOCKBUSTER:
                price = price + 2.0;
                break;
            case NORMAL:
                break;
        }
        // check class of cinema
        switch (movieShowing.getCinema().getClassOfCinema()) {
            case GOLD:
                price = price * 2;
                break;
            case MAX:
                price = price + 5.0;
                break;
            case NORMAL:
                break;
        }
        // check age of movie goer
        if (discount == true) {
            price = price * 0.5;
        }
        // check date
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        for (Date holiday: holidays) {
            if (fmt.format(holiday).equals(fmt.format(showTime))) {
                price = price + 2.0;
                break;
            }
        }

        return price;
    }
    /**
     * Gets the booking history of the user
     * @param userName The name of the user
     * @return The list of booking records of that person
     */
    public ArrayList<Booking> getBookingHistory(String userName) {
        ArrayList<Booking> bookingsByUser = new ArrayList<Booking>();
        for (Booking booking: bookings){
            if (booking.getPayment().getName().equals(userName)){
                bookingsByUser.add(booking);
            }
        }
        return bookingsByUser;
    }
    /**
     * Create movie review for a movie
     * users can fill in comments and rating
     * @param movie The movie which user wants to comment
     */
    public void createMovieReview(MovieInfo movie) {
        System.out.println("Please input the review");
        Scanner sc = new Scanner(System.in);
        String reviewstr = sc.next();
        System.out.println("Please input the rating");
        int rating = sc.nextInt();
        Review review = new Review(reviewstr,rating);
        movie.getPastReviews().add(review);
    }
    /**
     * Search a movie with given movie name keyword
     * @param movieName The movie name keyword
     * @return The information of the movie searched
     */
    public ArrayList<MovieInfo> search(String movieName){
        ArrayList<MovieInfo> searchResult = new ArrayList<MovieInfo>();
        for (MovieInfo movie: movies) {
            if (movie.getTitle().toLowerCase().contains(movieName.toLowerCase())){
                searchResult.add(movie);
            }
        }
        return searchResult;
    }
}
