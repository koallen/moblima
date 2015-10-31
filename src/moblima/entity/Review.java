package moblima.entity;
/**
 * Represents a Review for the movie
 * Contains the comment content and
 * rating of the Review 
 * @author SSP2 Team 1
 */
public class Review {
    /**
     * The comment content of the Review
     */
    private String content;
    /**
     * The rating of the Review
     */
    private int rating;
    /**
     * Creates a Review with given content and rating
     * @param content The comment content of this Review
     * @param rating The movie rating of this Review
     */
    public Review(String content, int rating) {
        this.content = content;
        this.rating = rating;
    }
    /**
     * Gets the comment content of this Review
     * @return The comment content of this Review
     */
    public String getContent() {
        return content;
    }
    /**
     * Gets the movie rating of this Review
     * @return The movie rating of this Review
     */
    public int getRating() {
        return rating;
    }
    /**
     * Gets the string representation of this Review
     * Including the comment content and rating
     * @return The string representation of this Review
     */
    public String toString() {
        return content;
        //also needs to include rating ???
    }
}
