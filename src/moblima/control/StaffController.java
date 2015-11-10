package moblima.control;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import moblima.entity.User;
import moblima.entity.User.TypeOfUser;
import moblima.entity.Review;
import moblima.entity.Booking;
import moblima.entity.Cinema;
import moblima.entity.Cineplex;
import moblima.entity.MovieInfo;
import moblima.entity.MovieShowing;
import moblima.boundary.StaffInterface;
/**
 * Represents a controller for all staff module functions
 * The staff module functions include login/logout
 * Create/Update/Remove movie listings
 * Create/Update/Remove cinema showtimes and the movies to be shown
 * Configure system settings （ticket prices and holidays)
 * @author SSP2 Team 1
 */
public class StaffController extends UserController {
    /**
     * Enumeration for different kinds of staff login feedback
     * Including WRONGUSERNAMEPASSWORD, ALREADYLOGGEDIN and LOGINSUCCESS
     */
    public enum LoginFeedback {WRONGUSERNAMEPASSWORD, ALREADYLOGGEDIN, LOGINSUCCESS}
    /**
     * The staff controller created by itself
     * Since StaffController is a singleton class
     * It can only be created by itself
     */
    private static StaffController staffController = null;
    /**
     * The staff interface
     */
    private StaffInterface staffInterface = StaffInterface.getInstance();
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
     * The list of the holiday dates
     */
    private List<Date> holidays = null;
    /**
     * The list of cineplexes
     */
    private List<Cineplex> cineplexes = null;
    /**
     * The boolean expression for loggedIn status
     * it is true when staff logins successful
     */
    private boolean loggedIn = false;
    /**
     * define the staff login username as moblima
     */
    private static final String staffUsername = "moblima";
    /**
     * define the staff login password as imStaff
     */
    private static final String staffPassword = "imStaff";
    /**
     * To avoids other classes to create a staff controller
     * It can only be created by itself
     */
    private StaffController() {}
    /**
     * Gets a staff controller
     * If staff controller has not been created yet,
     * create a new staff controller then return it
     * @return The staff controller
     */
    public static StaffController getInstance() {
        if (staffController == null) {
            staffController = new StaffController();
        }
        return staffController;
    }
    /**
     * Initrialize all the data which staff may need to use/modify
     * Including movie information, booking records
     * movies to be shown, holiday, cinema and cineplex
     * @param movies The list of all movie information
     * @param bookings The list of booking records
     * @param movieShowings The list of the information for movies to be shown
     * @param holidays The list of the holiday dates
     * @param cineplexes The list of cineplexes
     */
    public void initialize(ArrayList<MovieInfo> movies, ArrayList<Booking> bookings, ArrayList<MovieShowing> movieShowings, ArrayList<Date> holidays, ArrayList<Cineplex> cineplexes) {
        this.movies = movies;
        this.bookings = bookings;
        this.movieShowings = movieShowings;
        this.holidays = holidays;
        this.cineplexes = cineplexes;
    }
    /**
     * Creat movie listing
     */
    public void createMovieListing() {
        MovieInfo movie;
        int count, choice, reviewRating, i;
        double basePrice;
        String title, synopsis, director, reviewContent;
        String[] casts;
        MovieInfo.ShowingStatus showingStatus;
        MovieInfo.TypeOfMovie typeOfMovie;
        List<Review> pastReviews;
        MovieInfo.Rating rating;

        title = staffInterface.scanString("Input movie title: ");

        staffInterface.displayLine(
        "Showing status:\n" +
        "1. COMINGSOON\n" +
        "2. PREVIEW\n" +
        "3. NOWSHOWING\n" +
        "4. ENDOFSHOWING");
        showingStatus = MovieInfo.ShowingStatus.valueOf(staffInterface.scanString("Please enter one of the above: ").toUpperCase());

        staffInterface.displayLine(
        "Type of movie:\n" +
        "1. THREED\n" +
        "2. BLOCKBUSTER\n" +
        "3. NORMAL");
        typeOfMovie = MovieInfo.TypeOfMovie.valueOf(staffInterface.scanString("Please enter one of the above: ").toUpperCase());

        synopsis = staffInterface.scanLine("Input synopsis: ");

        director = staffInterface.scanLine("Input Director: ");

        count = staffInterface.scanInteger("Number of casts: ");
        casts = new String[count];
        for (i=0; i<count; ++i) {
            casts[i] = staffInterface.scanLine("Cast " + i + ": ");
        }

        count = staffInterface.scanInteger("Number of past reviews: ");
        pastReviews = new ArrayList<Review>();
        for (i=0; i<count; ++i) {
            staffInterface.displayLine("Review" + i + ". ");
            reviewContent = staffInterface.scanLine("Content of review: ");
            reviewRating = staffInterface.scanInteger("Rating: ");
            pastReviews.add(new Review(reviewContent, reviewRating));
        }

        basePrice = staffInterface.scanDouble("Base price: ");

        staffInterface.displayLine(
        "Movie ratings:\n" +
        "G, PG, PG13, NC16, M18, R21");
        rating = MovieInfo.Rating.valueOf(staffInterface.scanString("Please enter one the of above: ").toUpperCase());

        movie = new MovieInfo(title, showingStatus, typeOfMovie, synopsis, director, casts, 0.0, (ArrayList<Review>)pastReviews, basePrice, rating, 0);
        calculateOverallRating(movie);
        movies.add(movie);

        staffInterface.displayLine("Movie listing created");
    }
    /**
     * Update movie listing
     */
    public void updateMovieListing() {
        int choice, index;
        String synopsis;
        MovieInfo movieToUpdate, newMovieInfo;

        index = listAndSelectMovie();
        movieToUpdate = movies.get(index);
        newMovieInfo = (MovieInfo)movieToUpdate.clone();

        staffInterface.displayLine(
        "Choose the field that you want to change:\n" +
        "1. Showing status\n" +
        "2. Type of movie\n" +
        "3. Synopsis\n" +
        "4. Finish modifying");

        choice = staffInterface.scanInteger("");
        while (choice != 4) {
            switch (choice) {
                case 1:
                    staffInterface.displayLine(
                    "Showing status:\n" +
                    "1. COMINGSOON\n" +
                    "2. PREVIEW\n" +
                    "3. NOWSHOWING\n" +
                    "4. ENDOFSHOWING");
                    MovieInfo.ShowingStatus showingStatus = MovieInfo.ShowingStatus.valueOf(staffInterface.scanString("Please enter one of the above without space in between: ").toUpperCase());
                    newMovieInfo.setShowingStatus(showingStatus);
                    break;
                case 2:
                    staffInterface.displayLine(
                    "Type of movie:\n" +
                    "1. THREED\n" +
                    "2. BLOCKBUSTER\n" +
                    "3. NORMAL");
                    MovieInfo.TypeOfMovie typeOfMovie = MovieInfo.TypeOfMovie.valueOf(staffInterface.scanString("Please enter one of the above: ").toUpperCase());
                    newMovieInfo.setTypeOfMovie(typeOfMovie);
                    break;
                case 3:
                    synopsis = staffInterface.scanLine("Input new synopsis: ");
                    newMovieInfo.setSynopsis(synopsis);
                    break;
                default:
                    staffInterface.displayLine("Wrong input, please try again");
            }
        }

        movies.set(movies.indexOf(movieToUpdate), newMovieInfo);
    }
    /**
     * Remove movie listing
     */
    public void removeMovieListing() {
        int index;
        MovieInfo movieToRemove;

        index = listAndSelectMovie();
        movieToRemove = movies.get(index);

        for (MovieShowing movieshowing: movieShowings) {
            if (movieshowing.getMovie().getTitle().equals(movieToRemove.getTitle())) {
                movieShowings.remove(movieShowings.indexOf(movieshowing));
            }
        }
        movieToRemove.setShowingStatus(MovieInfo.ShowingStatus.ENDOFSHOWING);
    }
    /**
     * Creat movie Showing list
     */
    public void createMovieShowing() {
        MovieInfo movie;
        Cinema cinema;
        Cineplex cineplex;
        String showTimeString;
        Date showTime = null;
        int index;

        index = listAndSelectMovie();
        movie = movies.get(index);

        index = listAndSelectCineplex();
        cineplex = cineplexes.get(index);

        index = listAndSelectCinema(cineplex);
        cinema = cineplex.getCinemas()[index];

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        while (true) {
            showTimeString = staffInterface.scanLine("Input showtime in format DD/MM/YYYY HH:MM: ");
            try {
                showTime = fmt.parse(showTimeString);
                break;
            } catch (ParseException e) {
                staffInterface.displayLine("Wrong format, please try again");
            }
        }

        movieShowings.add(new MovieShowing(movie, cinema, cineplex, showTime));
    }
    /**
     * Update movie showing list
     */
    public void updateMovieShowing() {
        int index;
        String newShowTimeString;
        Date newShowTime = null;
        MovieShowing movieShowingToUpdate;

        index = listAndSelectMovieShowing();
        movieShowingToUpdate = movieShowings.get(index);

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        while (true) {
            newShowTimeString = staffInterface.scanLine("Input the new show time in format DD/MM/YYYY HH:MM: ");
            try {
                newShowTime = fmt.parse(newShowTimeString);
                break;
            } catch (ParseException e) {
                staffInterface.displayLine("Wrong format, please try again");
            }
        }

        movieShowingToUpdate.setShowTime(newShowTime);
    }
    /**
     * Remove movie showing
     */
    public void removeMovieShowing() {
        int index;
        MovieShowing movieShowingToRemove;

        index = listAndSelectMovieShowing();
        movieShowingToRemove = movieShowings.get(index);
        movieShowings.remove(movieShowings.indexOf(movieShowingToRemove));
    }
    /**
     * Update the base price of the movie
     */
    public void updateTicketPrice() {
        int index;
        double price;
        MovieInfo movieToUpdate;

        index = listAndSelectMovie();
        movieToUpdate = movies.get(index);

        price = staffInterface.scanDouble("Please input the new base price: ");
        movieToUpdate.setBasePrice(price);
    }
    /**
     * Add holiday
     */
    public void addHoliday() {
        String holidayString;
        Date holiday;

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        while (true) {
            holidayString = staffInterface.scanString("Input a holiday date in the format DD/MM/YYYY: ");
            try {
                holiday = fmt.parse(holidayString);
                break;
            } catch (ParseException e) {
                staffInterface.displayLine("Wrong format, please try again");
            }
        }
        holidays.add(holiday);
    }
    /**
     * Logout
     */
    public void logout() {
        if (loggedIn) {
            loggedIn = false;
            User.getInstance().setTypeOfUser(TypeOfUser.MOVIEGOER);
            staffInterface.displayLine("Logout successful");
        }
    }
    /**
     * List and select movie to modify
     * @return the movie index
     */
    public int listAndSelectMovie() {
        int index;

        for (MovieInfo movie: movies) {
            staffInterface.displayLine(movies.indexOf(movie) + ". " + movie.getTitle());
        }
        index = staffInterface.scanInteger("Please input the movie id: ");

        return index;
    }
    /**
     * List and select movie on showing to modify
     * @return the movie index
     */
    public int listAndSelectMovieShowing() {
        int index;

        for (MovieShowing movieShowing: movieShowings) {
            staffInterface.displayLine(movieShowings.indexOf(movieShowing) + ". " + movieShowing.toString());
        }
        index = staffInterface.scanInteger("Please input id of the movie showing: ");

        return index;
    }
    /**
     * List and select the cimeplex to modify
     * @return the cineplex index
     */
    private int listAndSelectCineplex() {
        int index;

        for (Cineplex cineplex: cineplexes) {
            staffInterface.displayLine(cineplexes.indexOf(cineplex) + ". " + cineplex.toString());
        }
        index = staffInterface.scanInteger("Please input id of the cineplex: ");

        return index;
    }
    /**
     * List and select the cinema to modify
     * @param cineplex the cineplex that staff chooses to showing the movie
     * @return the cinema index
     */
    private int listAndSelectCinema(Cineplex cineplex) {
        Cinema[] cinemas = cineplex.getCinemas();
        int index, i;

        for (i=0; i<cinemas.length; ++i) {
            staffInterface.displayLine(i + ". " + cinemas[i].toString());
        }
        index = staffInterface.scanInteger("Please input id of the cinema: ");

        return index;
    }
    /**
     * Admin login
     * needs to input username and password
     * returns the login feedback
     * if the user has already logined, return ALREADYLOGGEDIN
     * if the password matches with username, login success
     * else, login fails, the username or password are wrong
     * @param username The username of staff
     * @param password The password of staff
     * @return The login feedback
     */
    public LoginFeedback login(String username, String password) {
        if (!loggedIn) {
            if (username.equals(staffUsername) && password.equals(staffPassword)) {
                loggedIn = true;
                User.getInstance().setTypeOfUser(TypeOfUser.STAFF);
                return LoginFeedback.LOGINSUCCESS;
            } else {
                return LoginFeedback.WRONGUSERNAMEPASSWORD;
            }
        } else {
            return LoginFeedback.ALREADYLOGGEDIN;
        }
    }
    /**
     * Gets the sale report
     * @return The list of all movie information
     */
    public ArrayList<MovieInfo> getSaleReport() {
        return (ArrayList<MovieInfo>)movies;
    }
}
