package moblima.entity;

import java.util.*;

public class MovieShowing {

    private MovieInfo movie;
    private Cinema cinema;
    private Cineplex cineplex;
    private Date showTime;
    public MovieShowing() {
        this.movie = null;
        this.cinema = null;
        this.cineplex = null;
        this.showTime = null;
    }
    public MovieShowing(MovieInfo movie, Cinema cinema, Cineplex cineplex, Date date) {
        this.movie = movie;
        this.cinema = cinema;
        this.cineplex = cineplex;
        this.showTime = date;
    }

    public String toString() {
        return movie.toString() + "\n" + cinema.toString() + "\n" + cineplex.toString() + "\n" + showTime.toString();
    }
    public Cinema getCinema(){
        return cinema;
    }
    public MovieInfo getMovie() {
        return movie;
    }
    public Date getShowTime() {
        return showTime;
    }
}
