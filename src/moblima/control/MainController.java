package moblima.control;
/**
 * Represents a controller for the whole MOBLIMA system
 * This controller loads data 
 */
import java.io.File;
import java.util.Date;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import moblima.entity.Booking;
import moblima.entity.Cinema;
import moblima.entity.Cineplex;
import moblima.entity.MovieInfo;
import moblima.entity.MovieShowing;
import moblima.boundary.UserInterface;
import moblima.boundary.StaffInterface;
import moblima.boundary.MovieGoerInterface;

public class MainController {
    // define some constants
    private static final String movieInfoPath = "db" + File.separator + "movie_info.json";
    private static final String movieShowingPath = "db" + File.separator + "movie_showing.json";
    private static final String bookingPath = "db" + File.separator + "booking.json";
    private static final String holidayPath = "db" + File.separator + "holiday.json";
    private static final String cinemaPath = "db" + File.separator + "cinema.json";
    private static final String cineplexPath = "db" + File.separator + "cineplex.json";

    public static void start() {
        // load entities from json files
        FileManager fileManager = FileManager.getInstance();
        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("dd/MM/yyyy hh:mm").create();
        Gson gsonForHoliday = new GsonBuilder().setPrettyPrinting().setDateFormat("dd/MM/yyyy").create();

        ArrayList<MovieInfo> movies = fileManager.loadMovieInfo(movieInfoPath, gson);
        ArrayList<MovieShowing> movieShowings = fileManager.loadMovieShowing(movieShowingPath, gson);
        ArrayList<Booking> bookings = fileManager.loadBooking(bookingPath, gson);
        ArrayList<Date> holidays = fileManager.loadHoliday(holidayPath, gsonForHoliday);
        ArrayList<Cinema> cinemas = fileManager.loadCinema(cinemaPath, gson);
        ArrayList<Cineplex> cineplexes = fileManager.loadCineplex(cineplexPath, gson);

        // initialize controls
        MovieGoerController.getInstance().initialize(holidays, movies, bookings, movieShowings);
        StaffController.getInstance().initialize(movies, bookings, movieShowings, holidays, cinemas, cineplexes);

        // start interaction
        UserInterface.getInstance().start();

        // save entities into json files
        fileManager.saveMovieInfo(movies, movieInfoPath, gson);
        fileManager.saveMovieShowing(movieShowings, movieShowingPath, gson);
        fileManager.saveBooking(bookings, bookingPath, gson);
        fileManager.saveHoliday(holidays, holidayPath, gsonForHoliday);
    }
}
