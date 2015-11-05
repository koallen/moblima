package moblima.control;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import moblima.entity.Booking;
import moblima.entity.MovieInfo;
import moblima.entity.MovieShowing;
import moblima.entity.Cineplex;
/**
 * Represent a Manager to control the database
 * Including to save/load data from gson database
 * The datadase stores data for
 * booking, cinema, cineplex, holiday,
 * movie infomation and movie showing
 * @author SSP2 Team 1
 */
public class FileManager {
    /**
     * The File Manager created by itself
     * Since FileManager is a singleton class
     * It can only be created by itself
     */
    private static FileManager fileManager = null;
    /**
     * To avoids other classes to create a File Manager
     * It can only be created by itself
     */
    private FileManager(){}
    /**
     * Gets a file Manager
     * If file Manager has not been created yet,
     * create a new file Manager then return it
     * @return The file Manager
     */
    public static FileManager getInstance() {
        if (fileManager == null) {
            fileManager = new FileManager();
        }
        return fileManager;
    }
    /**
     * Load Movie Infomation from json database
     * @param filename The file name of movie information file
     * @param gson JSON serializer/deserializer
     * @return The list of movie information
     */
    protected ArrayList<MovieInfo> loadMovieInfo(String filename, Gson gson) {
        ArrayList<MovieInfo> movies = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            movies = gson.fromJson(br, new TypeToken<ArrayList<MovieInfo>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }
    /**
     * Load Movie showing information from json database
     * @param filename The file name of movie showing information file
     * @param gson JSON serializer/deserializer
     * @return The list of movie showing information
     */
    protected ArrayList<MovieShowing> loadMovieShowing(String filename, Gson gson) {
        ArrayList<MovieShowing> movieShowings = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            movieShowings = gson.fromJson(br, new TypeToken<ArrayList<MovieShowing>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieShowings;
    }
    /**
     * Load booking history from json database
     * @param filename The file name of booking history
     * @param gson JSON serializer/deserializer
     * @return The list of booking history
     */
    protected ArrayList<Booking> loadBooking(String filename, Gson gson) {
        ArrayList<Booking> bookings = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            bookings = gson.fromJson(br, new TypeToken<ArrayList<Booking>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    /**
     * Load holiday records from json database
     * @param filename The file name of holiday records
     * @param gson JSON serializer/deserializer
     * @return The list of holiday
     */
    protected ArrayList<Date> loadHoliday(String filename, Gson gson) {
        ArrayList<Date> holidays = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            holidays = gson.fromJson(br, new TypeToken<ArrayList<Date>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return holidays;
    }
    /**
     * Load information of cineplex from json database
     * @param filename The file name of cineplex information
     * @param gson JSON serializer/deserializer
     * @return The list of cineplex information
     */
    protected ArrayList<Cineplex> loadCineplex(String filename, Gson gson) {
        ArrayList<Cineplex> cineplexes = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            cineplexes = gson.fromJson(br, new TypeToken<ArrayList<Cineplex>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cineplexes;
    }
    /**
     * Save movie infomation into json database
     * @param movies The list of movie information
     * @param filename The file name of movie information
     * @param gson JSON serializer/deserializer
     */
    protected void saveMovieInfo(List<MovieInfo> movies, String filename, Gson gson) {
        String json = gson.toJson(movies);
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Save movie showing infomation into json database
     * @param movieShowings The list of movie showing information
     * @param filename The file name of movie showing information
     * @param gson JSON serializer/deserializer
     */
    protected  void saveMovieShowing(List<MovieShowing> movieShowings, String filename, Gson gson) {
        String json = gson.toJson(movieShowings);
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Save booking history into json database
     * @param bookings The list of booking history
     * @param filename The file name of booking history
     * @param gson JSON serializer/deserializer
     */
    protected void saveBooking(List<Booking> bookings, String filename, Gson gson) {
        String json = gson.toJson(bookings);
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Save holiday record into json database
     * @param holidays The list of holiday record
     * @param filename The file name of holiday record
     * @param gson JSON serializer/deserializer
     */
    protected void saveHoliday(List<Date> holidays, String filename, Gson gson) {
        String json = gson.toJson(holidays);
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
