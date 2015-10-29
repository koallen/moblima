package moblima.control;

import java.util.ArrayList;
import java.util.Date;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import moblima.entity.Booking;
import moblima.entity.MovieInfo;
import moblima.entity.MovieShowing;
import moblima.boundary.UserInterface;
import moblima.boundary.StaffInterface;
import moblima.boundary.MovieGoerInterface;

public class MainController {
    // define some constants
    private static final String movieInfoPath = "movie_info.json";
    private static final String movieShowingPath = "movie_showing.json";
    private static final String bookingPath = "booking.json";

    public static void start() {
        // load entities from json files
        FileManager fileManager = FileManager.getInstance();
        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("dd/MM/yyyy hh:mm").create();

        ArrayList<MovieInfo> movies = fileManager.loadMovieInfo(movieInfoPath, gson);
        ArrayList<MovieShowing> movieShowings = fileManager.loadMovieShowing(movieShowingPath, gson);
        ArrayList<Booking> bookings = fileManager.loadBooking(bookingPath, gson);
        ArrayList<Date> holidays = null;

        // construct controls from entities
        MovieGoerController movieGoerController = new MovieGoerController(holidays, movies, bookings, movieShowings);
        StaffController staffController = new StaffController(movies, bookings, movieShowings, holidays); // not finished

        // construct boundaries from controls
        MovieGoerInterface movieGoerInterface = new MovieGoerInterface(movieGoerController);
        StaffInterface staffInterface = new StaffInterface(staffController);

        // construct main interface
        UserInterface userInterface = new UserInterface(movieGoerInterface, staffInterface);

        // start interaction
        userInterface.start();

        // save entities into json files
        fileManager.saveMovieInfo(movies, movieInfoPath, gson);
        fileManager.saveMovieShowing(movieShowings, movieShowingPath, gson);
        fileManager.saveBooking(bookings, bookingPath, gson);
    }
}
