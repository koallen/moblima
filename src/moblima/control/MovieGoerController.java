package moblima.control;

import java.util.*;
import moblima.entity.Seat;
import moblima.entity.Review;
import moblima.entity.Booking;
import moblima.entity.Payment;
import moblima.entity.MovieInfo;
import moblima.entity.MovieTicket;
import moblima.entity.MovieShowing;

public class MovieGoerController {
    private static MovieGoerController movieGoerController = null;
    private List<Date> holidays;
    private List<MovieInfo> movies;
    private List<Booking> bookings;
    private List<MovieShowing> movieShowings;

    private MovieGoerController() {
        this.holidays = null;
        this.movies = null;
        this.bookings = null;
        this.movieShowings = null;
    }

    public static MovieGoerController getInstance() {
        if (movieGoerController == null) {
            movieGoerController = new MovieGoerController();
        }
        return movieGoerController;
    }

    public void initialize(ArrayList<Date> holidays, ArrayList<MovieInfo> movies, ArrayList<Booking> bookings, ArrayList<MovieShowing> movieShowings) {
        this.holidays = holidays;
        this.movies = movies;
        this.bookings = bookings;
        this.movieShowings = movieShowings;
    }

    public MovieShowing getMovies(int choice){
        return movieShowings.get(choice);
    }

    public ArrayList<MovieInfo> listAllMovies(){
        return (ArrayList<MovieInfo>)movies;
    }

    public MovieInfo searchForMovie(int index) {
        return movies.get(index);
    }

    public ArrayList<MovieShowing> listMovieShowing(MovieInfo movie){
        ArrayList<MovieShowing> results = new ArrayList<MovieShowing>();
        for (MovieShowing movieShowing: movieShowings) {
            if (movieShowing.getMovie().getTitle().equals(movie.getTitle())) {
                results.add(movieShowing);
            }
        }
        return results;
    }

    public void viewMovieDetail(String movieName){
        //MovieInfo movie = search(movieName);
        //movie.toString();
    }

    public void printLayout(MovieShowing movieShowing){
        Seat[][] seats = movieShowing.getCinema().getSeatLayout();
        for (int i = 0; i < movieShowing.getCinema().getRow(); i++){
            for(int j = 0; j < movieShowing.getCinema().getCol(); j++){
                System.out.print(seats[i][j].toString());
            }
            System.out.println();
        }
    }

    public Seat selectSeat(MovieShowing movieShowing, int row, int col){
        Seat[][] seats = movieShowing.getCinema().getSeatLayout();
        row--;
        col--;
        if(seats[row][col].getStatus() == Seat.enumSeat.TAKEN) {
            System.out.println("This seat is taken, please choose another one.");
            return null;
        }
        else if (seats[row][col].getStatus() == Seat.enumSeat.NOTEXIST){
            System.out.println("This seat does not exist.");
            return null;
        }
        else if (seats[row][col].getStatus() == Seat.enumSeat.NOTTAKEN){
            seats[row][col].setStatus(Seat.enumSeat.TAKEN);
            seats[row][col].setID(row, col);
            System.out.println("Seat is successfully taken");
            return seats[row][col];
        }
        else{
            System.out.println("Error");
            return null;
        }
    }

    public void book(MovieTicket movieTicket, Payment payment){
        Booking booking = new Booking(movieTicket, payment);
        bookings.add(booking);
    }

    public double calculate(MovieShowing movieShowing){
        double price = 0.0;
        System.out.println("Are you a previliged person? (y/n)");
        Scanner sc = new Scanner(System.in);
        char choice = sc.next().charAt(0);
        if(choice == 'n'){
            System.out.println("The price of the movie ticket is " + movieShowing.getMovie().getBasePrice());
            price = movieShowing.getMovie().getBasePrice();
        }
        else if (choice == 'y'){
            System.out.println("The price of the movie ticket is " + 0.5 * movieShowing.getMovie().getBasePrice());
            price = 0.5 * movieShowing.getMovie().getBasePrice();
        }
        for (int count = 0; count < holidays.size(); count++){
            if(movieShowing.getShowTime() == holidays.get(count)){
                price += 3;
            }

        }
        return price;
    }

    public ArrayList<Booking> getBookingHistory(String userName) {
        ArrayList<Booking> bookingsByUser = new ArrayList<Booking>();
        for (Booking booking: bookings){
            if (booking.getPayment().getName().equals(userName)){
                bookingsByUser.add(booking);
            }
        }
        return bookingsByUser;
    }

    public void createMovieReview(MovieInfo movie) {
        System.out.println("Please input the review");
        Scanner sc = new Scanner(System.in);
        String reviewstr = sc.next();
        System.out.println("Please input the rating");
        int rating = sc.nextInt();
        Review review = new Review(reviewstr,rating);
        movie.getPastReviews().add(review);
    }

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
