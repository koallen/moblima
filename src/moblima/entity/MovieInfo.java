package moblima.entity;

import java.util.*;
/**
 * Represents information of a movie
* Contains title, showing status, type,
 * synopsis, directors, a list of casts,
 * overallRating, a list of past reviews,
 * base ticket price, rating, and sale about this movie
 * @author SSP2 Team1
 */

public class MovieInfo implements Cloneable {
    /**
     * Enumeration for showing statuses of movie
     * Including COMINGSOON, PREVIEW, NOWSHOWING and ENDOFSHOWING
     */
    public enum ShowingStatus {COMINGSOON, PREVIEW, NOWSHOWING, ENDOFSHOWING}
    /**
     * Enumeration for types of movie
     * Including 3D and BLOCKBUSTER
     */
    public enum TypeOfMovie {THREED, BLOCKBUSTER}
    /**
     * Enumeration for ratings of movie
     * Including G, PG, PG13, NC16, M18 and R21
     */
    public enum Rating {G, PG, PG13, NC16, M18, R21}
    /**
     * The title of this movie
     */
    private String title;
    /**
     * The Showing status of this movie
     */
    private ShowingStatus showingStatus;
    /**
     * The type of this movie
     */
    private TypeOfMovie typeOfMovie;
    /**
     * The synopsis of this movie
     */
    private String synopsis;
    /**
     * The director of this movie
     */
    private String director;
    /**
     * The list of casts of this movie
     */
    private String[] casts;
    /**
     * The overallRating of this movie
     */
    private double overallRating;
    /**
     * The list of past reviews of this movie
     */
    private List<Review> pastReviews;
    /**
     * The base ticket price of this movie
     */
    private double basePrice;
    /**
     * The rating of this movie
     */
    private Rating rating;
    /**
     * The sale of this movie
     */
    private int sale;
    /**
     * Creates new movie information
     * with given title, showing status, type,
     * synopsis, directors, a list of casts,
     * overallRating, a list of past reviews,
     * base ticket price, rating, and sale about this movie
     * @param title The title of this movie
     * @param showingStatus The showing status of this movie
     * @param typeOfMovie The type of this movie
     * @param synopsis The synopsis of this movie
     * @param director The director of this movie
     * @param casts The list of casts of this movie
     * @param overallRating The overallRating of this movie
     * @param pastReviews The list of past reviews of this movie
     * @param basePrice The base ticket price of this movie
     * @param rating The rating of this movie
     * @param sale The sale of this movie
     */
    public MovieInfo(String title, ShowingStatus showingStatus, TypeOfMovie typeOfMovie, String synopsis, String director, String[] casts, double overallRating, ArrayList<Review> pastReviews, double basePrice, Rating rating, int sale) {
        this.title = title;
        this.showingStatus = showingStatus;
        this.typeOfMovie = typeOfMovie;
        this.synopsis = synopsis;
        this.director = director;
        this.casts = casts;
        this.overallRating = overallRating;
        this.pastReviews = pastReviews;
        this.basePrice = basePrice;
        this.rating = rating;
        this.sale = sale;
    }
    /**
     * Gets the string representation of this movie information
     * @return the string representation of this movie information
     */

    public String toString() {
        if (pastReviews.size() < 2) {
            String overallRating = "NA";
        }
        return "Title: " + title + "\nShowing status: " + showingStatus + "\nRating: " + rating + "\nSynopsis: " + synopsis + "\nViewer rating: " + overallRating;
    }
    /**
     * Gets the title of this movie
     * @return The title of this movie
     */
    public String getTitle() {
        return title;
    }
    /**
     * Changes the synopsis of this movie
     * @param synopsis The new synopsis of this movie
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
    /**
     * Gets the sale of this movie
     * @return The sale of this movie
     */
    public int getSale() {
        return sale;
    }
    /**
     * Changes the sale of this movie
     * @param sale The new sale of this movie
     */
    public void setSale(int sale) {
        this.sale = sale;
    }
    /**
     * Gets the base ticket price of this movie
     * @return The base prive of this movie
     */
    public double getBasePrice() {
        return basePrice;
    }
    /**
     * Changes the base ticket price of this movie
     * @param basePrice The new base price of this movie
     */
    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
    /**
     * Gets the showing status of this movie
     * @return the showing status of this movie
     */
    public ShowingStatus getShowingStatus() {
        return showingStatus;
    }
    /**
     * Changes the showing status of this movie
     * @param showingStatus The new showing status of this movie
     */
    public void setShowingStatus(ShowingStatus showingStatus) {
        this.showingStatus = showingStatus;
    }
    /**
     * Changes the type of this movie
     * @param typeOfMovie The new type of this movie
     */
    public void setTypeOfMovie(TypeOfMovie typeOfMovie) {
        this.typeOfMovie = typeOfMovie;
    }
    /**
     * Gets overall review rating of this movie
     * @return The overall review rating of this movie
     */
    public double getOverallRating() {
        return overallRating;
    }
    /**
     * Changes the overall review rating of this movie
     * @param overallRating The new overall review rating of this movie
     */
    public void setOverallRating(double overallRating) {
        this.overallRating = overallRating;
    }
    /**
     * Gets the list of past reviews of this movie
     * @return The list of past reviews of this movie
     */
    public List<Review> getPastReviews() {
        return pastReviews;
    }

    public void addReview(Review viewerReview) {
        pastReviews.add(viewerReview);
    }
    /**
     * Gets the rating of this movie
     * @return The rating of this movie
     */
    public Rating getRating() {
        return rating;
    }
    /**
     * Copy the information of this movie
     * to a cloned information of this movie
     * @return The cloned information of this movie
     */
    public Object clone() {
        MovieInfo cloned = null;
        try {
            cloned = (MovieInfo)super.clone();
        } catch (CloneNotSupportedException e) {
            // this should never happen
        }
        return cloned;
    }
}
