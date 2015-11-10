package moblima.control;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.lang.IndexOutOfBoundsException;
import moblima.entity.User;
import moblima.entity.Seat;
import moblima.entity.Review;
import moblima.entity.Booking;
import moblima.entity.Payment;
import moblima.entity.Cinema;
import moblima.entity.MovieInfo;
import moblima.entity.MovieTicket;
import moblima.entity.MovieShowing;
import moblima.boundary.MovieGoerInterface;
import moblima.control.StaffController.LoginFeedback;

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
public class MovieGoerController extends UserController {
    /**
     * The moviegoer controller created by itself
     * Since MovieGoerController is a singleton class
     * It can only be created by itself
     */
    private static MovieGoerController movieGoerController = null;
    /**
     *
     */
    private MovieGoerInterface movieGoerInterface = MovieGoerInterface.getInstance();
    /**
     * The list of the holiday dates
     */
    private List<Date> holidays = null;
    /**
     * The list of all movie informations
     */
    private List<MovieInfo> movies = null;
    /**
     * The list of booking records
     */
    private List<Booking> bookings = null;
    /**
     * The list of the information for movies to be shown
     */
    private List<MovieShowing> movieShowings = null;
    /**
     * To avoids other classes to create a moviegoer controller
     * It can only be created by itself
     */
    private MovieGoerController() {}
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
     * Book a movie ticket
     * let user choose the movie
     * check whether the ticket is avaliable
     * If ticket is avaliable
     * choose the seats
     * and complete the booking
     * with name email and mobile phone number
     */
    public void bookAMovie() {
        String movieName, movieGoerName, bookingId, name, email, mobileNumber, transactionID;
        int index, row, col;
        double price;
        boolean discount;
        MovieInfo movieToBook;
        MovieShowing movieShowing;
        List<MovieInfo> searchResult;
        List<MovieShowing> movieShowings;
        Seat[][] seats;
        Seat seat;

        searchResult = searchMovies();
        if (searchResult.size() == 0) {
            return;
        }

        while (true) {
            index = movieGoerInterface.scanInteger("Input the movie id that you want to book: ");
            // catch array exception
            try {
                movieToBook = searchResult.get(index);
                break;
            } catch (IndexOutOfBoundsException e) {
                movieGoerInterface.displayLine("Wrong index, please try again");
            }
        }

        movieShowings = listMovieShowing(movieToBook);
        if (movieShowings.size() == 0) {
            movieGoerInterface.displayLine("The movie isn't showing now");
            return;
        }
        for (MovieShowing show: movieShowings) {
            movieGoerInterface.displayLine(movieShowings.indexOf(show) + ".\n" + show.toString());
        }

        while (true) {
            index = movieGoerInterface.scanInteger("Input the showing id that you want to book: ");
            // catch array exception
            try {
                movieShowing = movieShowings.get(index);
                break;
            } catch (IndexOutOfBoundsException e) {
                movieGoerInterface.displayLine("Wrong index, please try again");
            }
        }

        seats = getSeats(movieShowing);
        printLayout(seats);

        movieGoerInterface.displayLine("Which seat do you want?");
        while (true) {
            row = movieGoerInterface.scanInteger("Input row: ");
            col = movieGoerInterface.scanInteger("Input col: ");
            // catch array errors
            try {
                seat = selectSeat(movieShowing, row, col);
            } catch (IndexOutOfBoundsException e) {
                movieGoerInterface.displayLine("Wrong row or column number, please try again");
                continue;
            }
            if (seat != null) {
                movieGoerInterface.displayLine("Seat successfully taken");
                break;
            }
            movieGoerInterface.displayLine("Seat selection unsuccessful\nMake sure you select an empty seat");
        }

        discount = movieGoerInterface.scanBoolean("Are you a child or an elder? (true/false): ");

        price = calculatePrice(movieShowing, discount);
        movieGoerInterface.displayLine("The movie price is " + price);

        name = movieGoerInterface.scanLine("Please input your name: ");
        mobileNumber = movieGoerInterface.scanString("Please input your mobile number: ");
        email = movieGoerInterface.scanString("Please input your email address: ");

        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmm");
        transactionID = movieShowing.getCinema().getCinemaCode() + fmt.format(new Date());

        bookings.add(new Booking(new MovieTicket(movieShowing, seat, price), new Payment(transactionID, name, mobileNumber, email, price)));
        movieToBook.setSale(movieToBook.getSale()+1);

        movieGoerInterface.displayLine("Booking successful");
    }
    /**
     * Search movies
     * @return An ArrayList of MovieInfo
     */
    public ArrayList<MovieInfo> searchMovies() {
        ArrayList<MovieInfo> searchResult = new ArrayList<MovieInfo>();
        String movieName;

        movieName = movieGoerInterface.scanLine("Please input the movie name: ");

        // search by name
        for (MovieInfo movie: movies) {
            if (movie.getTitle().toLowerCase().contains(movieName.toLowerCase())){
                searchResult.add(movie);
            }
        }

        // display result
        if (searchResult.size() != 0) {
            movieGoerInterface.displayLine("Search result:");
            for (MovieInfo movie: searchResult) {
                movieGoerInterface.displayLine(searchResult.indexOf(movie) + ". " + movie.getTitle());
            }
        } else {
            movieGoerInterface.displayLine("No matching movie found!");
        }

        return searchResult;
    }
    /**
     * View booking history of the input name
     */
    public void viewBookingHistory() {
        String movieGoerName;
        ArrayList<Booking> bookingsByUser = new ArrayList<Booking>();

        movieGoerName = movieGoerInterface.scanLine("Please input your name: ");

        for (Booking booking: bookings){
            if (booking.getPayment().getName().equals(movieGoerName)){
                bookingsByUser.add(booking);
            }
        }

        if (bookingsByUser.size() != 0) {
            movieGoerInterface.displayLine("Here are your bookings:\n");
            for (Booking booking: bookingsByUser) {
                movieGoerInterface.displayLine(bookingsByUser.indexOf(booking) + ".\n" + booking.toString() + "\n");
            }
        } else {
            movieGoerInterface.displayLine("No booking record found");
        }
    }
    /**
     * Display detail movie info
     */
    public void searchAndDisplayMovieInfo() {
        List<MovieInfo> movies = searchMovies();
        if (movies.size() == 0) {
            return;
        }
        List<Review> reviews;
        int index;

        while (true) {
            index = movieGoerInterface.scanInteger("Please input the movie id: ");
            // catch array errors
            try {
                reviews = movies.get(index).getPastReviews();
                break;
            } catch (IndexOutOfBoundsException e) {
                movieGoerInterface.displayLine("Wrong index, please try again");
            }
        }

        movieGoerInterface.displayLine(movies.get(index).toString());
        movieGoerInterface.displayLine("Past reviews");
        for (Review review: reviews) {
            movieGoerInterface.displayLine(reviews.indexOf(review) + ".\nContent: " + review.getContent() + "\nRating: " + review.getRating());
        }
    }
    /**
     * Add review to the movie
     */
    public void addReview() {
        List<MovieInfo> results = searchMovies();
        if (results.size() == 0) {
            return;
        }
        int index, rating;
        String content;
        MovieInfo movie;

        while (true) {
            index = movieGoerInterface.scanInteger("Please input the movie id: ");
            // catch array error
            try {
                movie = results.get(index);
                break;
            } catch (IndexOutOfBoundsException e) {
                movieGoerInterface.displayLine("Wrong index, please try again");
            }
        }

        content = movieGoerInterface.scanLine("Please input review content:");
        while (true) {
            rating = movieGoerInterface.scanInteger("Please input review rating (0-5): ");
            if (rating > 5 || rating < 0) {
                break;
            } else {
                movieGoerInterface.displayLine("Rating out of range, please try again");
            }
        }

        movie.addReview(new Review(content, rating));
        calculateOverallRating(movie);

        movieGoerInterface.displayLine("Review successfully added");
    }
    /**
     * Log in as staff
     * Check the username and password
     * Check whether the user already logged in
     */
    public void loginAsStaff() {
        String username, password;

        username = movieGoerInterface.scanString("Input username: ");
        password = movieGoerInterface.scanString("Input password: ");

        LoginFeedback feedback = StaffController.getInstance().login(username, password);
        switch (feedback) {
            case LOGINSUCCESS:
                movieGoerInterface.displayLine("Login successful");
                break;
            case ALREADYLOGGEDIN:
                break;
            case WRONGUSERNAMEPASSWORD:
                movieGoerInterface.displayLine("Wrong username or password, please try again");
                break;
            default:
                break;
        }
    }
    /**
     * Print the seat layout of the cinema
     * that is showing the movie
     * @param seats All seats in a cinema
     */
    public void printLayout(Seat[][] seats){
        for (int i = 0; i < seats.length; i++){
            for(int j = 0; j < seats[i].length; j++){
                movieGoerInterface.display(seats[i][j].toString());
            }
            movieGoerInterface.displayLine("");
        }
    }
    /**
     * List all movies
     */
    public void listAllMovies(){
        movieGoerInterface.displayLine("This is the list of all movies");
        for (MovieInfo movie: movies){
            movieGoerInterface.displayLine(movies.indexOf(movie) + ". " + movie.getTitle());
        }
    }
    /**
     * List top 5 by sale
     */
    public void listTop5BySale() {
        List<MovieInfo> topMovies = null;

        // sort the movies by sale
        Collections.sort(movies, new Comparator<MovieInfo>() {
            @Override
            public int compare(MovieInfo movie1, MovieInfo movie2) {
                return movie2.getSale() - movie1.getSale();
            }
        });

        if (movies.size() < 5) {
            topMovies = movies;
        } else {
            topMovies = movies.subList(0, 5);
        }

        movieGoerInterface.displayLine("The top 5 movies by sale are:");
        for (MovieInfo movie: topMovies) {
            movieGoerInterface.displayLine(movies.indexOf(movie) + ". " + movie.getTitle());
        }
    }
    /**
     * List top 5 by rating
     */
    public void listTop5ByRating() {
        List<MovieInfo> topMovies = null;

        // sort the movies by sale
        Collections.sort(movies, new Comparator<MovieInfo>() {
            @Override
            public int compare(MovieInfo movie1, MovieInfo movie2) {
                if (movie2.getOverallRating() > movie1.getOverallRating()) {
                    return 1;
                }
                return -1;
            }
        });

        if (movies.size() < 5) {
            topMovies = movies;
        } else {
            topMovies = movies.subList(0, 5);
        }

        movieGoerInterface.displayLine("The top 5 movies by rating are:");
        for (MovieInfo movie: topMovies) {
            movieGoerInterface.displayLine(movies.indexOf(movie) + ". " + movie.getTitle());
        }
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
     * @return A 2D seat array
     */
    public Seat[][] getSeats(MovieShowing movieShowing) {
        return movieShowing.getCinema().getSeatLayout();
    }
    /**
     * Select seat during booking
     * @param movieShowing The movie which user wants to book
     * @param row The row of seats which user chooses to book
     * @param col The colume of seats which user chooses to book
     * @return The seat selected
     */
    public Seat selectSeat(MovieShowing movieShowing, int row, int col) throws IndexOutOfBoundsException {
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

        if (seat.getStatus() == Seat.enumSeat.NOTTAKEN) {
            seat.setStatus(Seat.enumSeat.TAKEN);
            seat.setID(row, col);
            return seat;
        } else {
            return null;
        }

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
}
