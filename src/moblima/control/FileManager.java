package moblima.control;

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

public class FileManager {
    private static FileManager fileManager = null;

    private FileManager(){}

    public static FileManager getInstance() {
        if (fileManager == null) {
            fileManager = new FileManager();
        }
        return fileManager;
    }

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
}
