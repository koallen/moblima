package moblima.control;

import java.io.File;
import java.util.Date;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import moblima.entity.User;
import moblima.entity.Booking;
import moblima.entity.Cinema;
import moblima.entity.Cineplex;
import moblima.entity.MovieInfo;
import moblima.entity.MovieShowing;
import moblima.boundary.StaffInterface;
import moblima.boundary.MovieGoerInterface;
/**
 * Represents a controller for the whole MOBLIMA system
 * This controller loads data from json database
 * starts up the user interface
 * after all the executions in user interface
 * save the data to to json database
 * @author SSP2 Team 1
 */
public class MainController {
    // define some constants
    /**
     * define the path name of movie information file
     */
    private static final String movieInfoPath = "db" + File.separator + "movie_info.json";
    /**
     * define the path name of movie showing information file
     */
    private static final String movieShowingPath = "db" + File.separator + "movie_showing.json";
    /**
     * define the path name of booking history file
     */
    private static final String bookingPath = "db" + File.separator + "booking.json";
    /**
     * define the path name of holiday records file
     */
    private static final String holidayPath = "db" + File.separator + "holiday.json";
    /**
     * define the path name of cineplex information file
     */
    private static final String cineplexPath = "db" + File.separator + "cineplex.json";
    /**
     * loads data from json database
     * starts up the user interface
     * after all the executions in user interface
     * save the data to to json database
     */
    public static void start() {
        // load entities from json files
        FileManager fileManager = FileManager.getInstance();
        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("dd/MM/yyyy HH:mm").create();
        Gson gsonForHoliday = new GsonBuilder().setPrettyPrinting().setDateFormat("dd/MM/yyyy").create();

        ArrayList<MovieInfo> movies = fileManager.loadMovieInfo(movieInfoPath, gson);
        ArrayList<MovieShowing> movieShowings = fileManager.loadMovieShowing(movieShowingPath, gson);
        ArrayList<Booking> bookings = fileManager.loadBooking(bookingPath, gson);
        ArrayList<Date> holidays = fileManager.loadHoliday(holidayPath, gsonForHoliday);
        ArrayList<Cineplex> cineplexes = fileManager.loadCineplex(cineplexPath, gson);

        // initialize controls
        MovieGoerController.getInstance().initialize(holidays, movies, bookings, movieShowings);
        StaffController.getInstance().initialize(movies, bookings, movieShowings, holidays, cineplexes);

        // start interacting with user
        User currentUser = User.getInstance();
        while (currentUser.isActive()) {
            if (currentUser.isMovieGoer()) {
                MovieGoerInterface.getInstance().interact();
            } else {
                StaffInterface.getInstance().interact();
            }
        }

        // save entities into json files
        fileManager.saveMovieInfo(movies, movieInfoPath, gson);
        fileManager.saveMovieShowing(movieShowings, movieShowingPath, gson);
        fileManager.saveBooking(bookings, bookingPath, gson);
        fileManager.saveHoliday(holidays, holidayPath, gsonForHoliday);
    }
}
