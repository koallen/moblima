import java.io.*;
import java.util.*;
import com.google.gson.*; // Gson package to serialize/deserialize JSON
import com.google.gson.reflect.TypeToken;

/**
* This is the starting point of MOBLIMA, a movie booking system
*
* @author SSP? Team 1
* @since 2015-10-16
*/
public class Moblima {
    public static void main(String[] args) {
        // deserialize JSON to objects
        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("dd/MM/yyyy hh:mm:ss").create();
        List<MovieInfo> movies;
        List<MovieShowing> movieShowings;
        List<Booking> bookings;

        movies = deserializeMovieInfo("movie_info.json", gson);
        movieShowings = deserializeMovieShowing("movie_showing.json", gson);
        bookings = deserializeBooking("booking.json", gson);

        // start the application
        UserInterface.start();

        // serialize objects to JSON
        serializeMovieInfo(movies, "movie_info.json", gson);
        serializeMovieShowing(movieShowings, "movie_showing.json", gson);
        serializeBooking(bookings, "booking.json", gson);
    }

    private static void serializeMovieInfo(List<MovieInfo> movies, String filename, Gson gson) {
        String json = gson.toJson(movies);
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void serializeMovieShowing(List<MovieShowing> movieShowings, String filename, Gson gson) {
        String json = gson.toJson(movieShowings);
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void serializeBooking(List<Booking> bookings, String filename, Gson gson) {
        String json = gson.toJson(bookings);
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<MovieInfo> deserializeMovieInfo(String filename, Gson gson) {
        ArrayList<MovieInfo> movies = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            movies = gson.fromJson(br, new TypeToken<ArrayList<MovieInfo>>(){}.getType());
            System.out.println(movies);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }

    private static ArrayList<MovieShowing> deserializeMovieShowing(String filename, Gson gson) {
        ArrayList<MovieShowing> movieShowings = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            movieShowings = gson.fromJson(br, new TypeToken<ArrayList<MovieShowing>>(){}.getType());
            System.out.println(movieShowings);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieShowings;
    }

    private static ArrayList<Booking> deserializeBooking(String filename, Gson gson) {
        ArrayList<Booking> bookings = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            bookings = gson.fromJson(br, new TypeToken<ArrayList<Booking>>(){}.getType());
            System.out.println(bookings);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookings;
    }
}
