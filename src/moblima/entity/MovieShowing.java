package moblima.entity;

import java.util.*;
import java.text.SimpleDateFormat;
/**
 * Represents a movie which is showing in a cinema
 * Contains the intruduction information about this movie,
 * the cinema where the movie is showing at,
 * the cineplex where the movie is showing at and
 * the show time when the movie is showing
 * @authos SSP2 Team 1
 */
public class MovieShowing {
    /**
     * The intruduction information about this movie
     */
    private MovieInfo movie;
    /**
     * The cinema where the movie is showing at
     */
    private Cinema cinema;
    /**
     * The cineplex where the movie is showing at
     */
    private Cineplex cineplex;
    /**
     * The show time when the movie is showing
     */
    private Date showTime;
    /**
     * Create a new movie which is showing in a cinema
     * without any information given about this movie
     * The movie information, cinema, cineplex, and show time
     * are all set to null as default.
     */
    public MovieShowing() {
        this.movie = null;
        this.cinema = null;
        this.cineplex = null;
        this.showTime = null;
    }
    /**
     * Create a new movie which is showing in a cinema
     * with given intruduction information about this movie,
     * the cinema where the movie is showing at,
     * the cineplex where the movie is showing at and
     * the show time when the movie is showing
     * @param movie The intruduction information about this movie
     * @param cinema The cinema where the movie is showing at
     * @param cineplex The cineplex where the movie is showing at
     * @param date The show time when the movie is showing
     */
    public MovieShowing(MovieInfo movie, Cinema cinema, Cineplex cineplex, Date date) {
        this.movie = movie;
        this.cinema = cinema;
        this.cineplex = cineplex;
        this.showTime = date;
    }
    /**
     * Gets the string representation of all the information
     * about this movie showing in the cinema
     * @return the string representation of all the information about this showing movie
     */
    public String toString() {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        return "Movie title: " + movie.getTitle() + "\nCinema code: " + cinema.toString() + "\nCineplex: " + cineplex.toString() + "\nShow time: " + fmt.format(showTime);
    }
    /**
     * Gets the cinema where the movie is showing at
     * @return The cinema where the movie is showing at
     */
    public Cinema getCinema(){
        return cinema;
    }
    /**
     * Gets the intruduction information about this movie
     * @return the intruduction information about this movie
     */
    public MovieInfo getMovie() {
        return movie;
    }
    /**
     * Gets the showing time when the movie is showing
     * @return the showing time when the movie is showing
     */
    public Date getShowTime() {
        return showTime;
    }
}
