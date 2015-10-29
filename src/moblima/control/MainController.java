package moblima.control;

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

        List<MovieInfo> movies = fileManager.loadMovieInfo(movieInfoPath, gson);
        List<MovieShowing> movieShowings = fileManager.loadMovieShowing(movieShowingPath, gson);
        List<Booking> bookings = fileManager.loadBooking(bookingPath, gson);

        // construct controls from entities
        MovieGoerController movieGoerController = new MovieGoerController(); // not finished
        StaffController staffController = new StaffController(); // not finished

        // construct boundaries from controls
        MovieGoerInterface movieGoerInterface = new MovieGoerInterface(movieGoerController);
        StaffInterface staffInterface = new StaffInterface(staffController);

        // construct main interface
        UserInterface userInterface = new UserInterface(); // not finished

        // start interaction
        userInterface.start();

        // save entities into json files
        fileManager.saveMovieInfo(movies, movieInfoPath, gson);
        fileManager.saveMovieShowing(movieShowings, movieShowingPath, gson);
        fileManager.saveBooking(bookings, bookingPath, gson);
    }
}
