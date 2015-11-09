package moblima.control;

import java.util.*;
import moblima.entity.User;
import moblima.entity.User.TypeOfUser;
import moblima.entity.Review;
import moblima.entity.Booking;
import moblima.entity.Cinema;
import moblima.entity.Cineplex;
import moblima.entity.MovieInfo;
import moblima.entity.MovieShowing;
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
     * Enumeration for different kinds of staff logout feedback
     * Including ALREADYLOGGEDOUT and LOGOUTSUCCESS
     */
    public enum LogoutFeedback {ALREADYLOGGEDOUT, LOGOUTSUCCESS}
    /**
     * The staff controller created by itself
     * Since StaffController is a singleton class
     * It can only be created by itself
     */
    private static StaffController staffController = null;
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
     * The list of the holiday dates
     */
    private List<Date> holidays;
    /**
     * The list of cineplexes
     */
    private List<Cineplex> cineplexes;
    /**
     * The boolean expression for loggedIn status
     * it is true when staff logins successful
     */
    private boolean loggedIn;
    // define some constants
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
    private StaffController() {
        this.movies = null;
        this.bookings = null;
        this.movieShowings = null;
        this.holidays = null;
        this.cineplexes = null;
        this.loggedIn = false;
    }
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
     * Gets the list of all movie information
     * @return the list of all movie information
     */
    public ArrayList<MovieInfo> listAllMovies() {
        return (ArrayList<MovieInfo>)movies;
    }
    /**
     * Gets the list of the information for movies to be shown
     * @return the list of the information for movies to be shown
     */
    public ArrayList<MovieShowing> listAllShowings() {
        return (ArrayList<MovieShowing>)movieShowings;
    }
    /**
     * Gets the list of cinemas in a cineplex
     * @return the list of cinemas in a cineplex
     */
    public Cinema[] listAllCinemas(Cineplex cineplex) {
        return cineplex.getCinemas();
    }
    /**
     * Gets the list of all cineplexes
     * @return the list of all cineplexes
     */
    public ArrayList<Cineplex> listAllCineplexes() {
        return (ArrayList<Cineplex>)cineplexes;
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
     * User searches cinema with movie index
     * return the cinema with given index
     * @param index The index of the cinema
     * @return The required cinema
     */
    public Cinema searchForCinema(int index, Cineplex cineplex) {
        return cineplex.getCinemas()[index];
    }
    /**
     * User searches cineplex with cineplex index
     * return the cineplex with given index
     * @param index The index of cineplex
     * @return The required cineplex
     */
    public Cineplex searchForCineplex(int index) {
        return cineplexes.get(index);
    }
    /**
     * Updates the showing status of the movie
     * @param movieToUpdate The movie needs to be updated
     * @param showingStatus The new showing status of the movie
     */
    public void updateShowingStatus(MovieInfo movieToUpdate, MovieInfo.ShowingStatus showingStatus) {
        movieToUpdate.setShowingStatus(showingStatus);
    }
    /**
     * Updates the type of the movie
     * @param movieToUpdate The movie needs to be updated
     * @param typeOfMovie The new type of the movie
     */
    public void updateTypeOfMovie(MovieInfo movieToUpdate, MovieInfo.TypeOfMovie typeOfMovie) {
        movieToUpdate.setTypeOfMovie(typeOfMovie);
    }
    /**
     * Updates the synopsis of the movie
     * @param movieToUpdate The movie needs to be updated
     * @param synopsis The new synopsis of the movie
     */
    public void updateSynopsis(MovieInfo movieToUpdate, String synopsis) {
        movieToUpdate.setSynopsis(synopsis);
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
     * Admin logout
     * @return The logout feedback
     */
    public LogoutFeedback logout() {
        if (loggedIn) {
            loggedIn = false;
            User.getInstance().setTypeOfUser(TypeOfUser.MOVIEGOER);
            return LogoutFeedback.LOGOUTSUCCESS;
        } else {
            return LogoutFeedback.ALREADYLOGGEDOUT;
        }
    }
    /**
     * Creates the new movie into the movie list
     * @param movie The new movie
     */
    public void createMovieListing(MovieInfo movie) {
        movies.add(movie);
    }
    /**
     * Updates the movie information in the movie list
     * @param oldMovie The movie needs to be updated
     * @param newMovie The new movie information
     */
    public void updateMovieListing(MovieInfo oldMovie, MovieInfo newMovie) {
        int index = movies.indexOf(oldMovie);
        movies.set(index, newMovie);
    }
    /**
     * Removes the movie from the movie list
     * @param movie The movie needs to be removed
     */
    public void removeMovieListing(MovieInfo movie) {
        int index;
        for (MovieShowing movieshowing: movieShowings) {
            if (movieshowing.getMovie().equals(movie)) {
                index = movieShowings.indexOf(movieshowing);
                movieShowings.remove(index);
            }
        }
        movie.setShowingStatus(MovieInfo.ShowingStatus.ENDOFSHOWING);
    }
    /**
     * Creates the new showing movie into the movie showing list
     * @param movieShowing The new showing movie
     */
    public void createMovieShowing(MovieShowing movieShowing) {
        movieShowings.add(movieShowing);
    }
    /**
     * Updates the show time of the movie to be shown
     * @param newShowTime The new show time
     * @param movieShowingToUpdate The showing movie needs to be updated
     */
    public void updateMovieShowing(Date newShowTime, MovieShowing movieShowingToUpdate) {
        movieShowingToUpdate.setShowTime(newShowTime);
    }
    /**
     * Removes the showing movie from the list
     * @param movieShowing The showing movie needs to be removed
     */
    public void removeMovieShowing(MovieShowing movieShowing) {
        int index = movieShowings.indexOf(movieShowing);
        movieShowings.remove(index);
    }
    /**
     * Gets the sale report
     * @return The list of all movie information
     */
    public ArrayList<MovieInfo> getSaleReport() {
        return (ArrayList<MovieInfo>)movies;
    }
    /**
     * Updates the ticket price for the movie in the movie list
     * @param movie The movie needs to be updated
     * @param price The new ticket price
     */
    public void updateTicketPrice(MovieInfo movie, double price) {
        movie.setBasePrice(price);
    }
    /**
     * Adds new holiday date into the holiday list
     * @param holiday The new holiday date
     */
    public void createHoliday(Date holiday) {
        holidays.add(holiday);
    }
    /**
     * Gets the top 5 movies according to their sales
     * @return The top 5 movies
     */
    public List<MovieInfo> getTop5BySale() {
        Collections.sort(movies, new Comparator<MovieInfo>() {
            @Override
            public int compare(MovieInfo movie1, MovieInfo movie2) {
                return movie2.getSale() - movie1.getSale();
            }
        });

        if (movies.size() < 5) {
            return movies;
        } else {
            return movies.subList(0, 5);
        }
    }
    /**
     * Gets the top 5 movies according to their rating
     * @return The top 5 movies
     */
    public List<MovieInfo> getTop5ByRating() {
        Collections.sort(movies, new Comparator<MovieInfo>() {
            @Override
            public int compare(MovieInfo movie1, MovieInfo movie2) {
                if (movie2.getOverallRating() > movie1.getOverallRating()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        if (movies.size() < 5) {
            return movies;
        } else {
            return movies.subList(0, 5);
        }
    }
}
