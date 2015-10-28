import java.util.*;

public class MovieInfo implements Cloneable {
    public enum ShowingStatus {COMINGSOON, PREVIEW, NOWSHOWING, ENDOFSHOWING}
    public enum TypeOfMovie {THREED, BLOCKBUSTER}
    public enum Rating {G, PG, PG13, NC16, M18, R21}

    private String title;
    private ShowingStatus showingStatus;
    private TypeOfMovie typeOfMovie;
    private String synopsis;
    private String director;
    private String[] casts;
    private double overallRating;
    private List<Review> pastReviews;
    private double basePrice;
    private Rating rating;
    private int sale;

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

    public String toString() {
        return "Title: " + title + "\nShowing status: " + showingStatus + "\nDirector: " + director + "\nBase price: " + basePrice + "\n";
    }

    public String getTitle() {
        return title;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public double getBasePrice() {
        return basePrice;
    }
    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public ShowingStatus getShowingStatus() {
        return showingStatus;
    }

    public void setShowingStatus(ShowingStatus showingStatus) {
        this.showingStatus = showingStatus;
    }

    public void setTypeOfMovie(TypeOfMovie typeOfMovie) {
        this.typeOfMovie = typeOfMovie;
    }

    public double getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(double overallRating) {
        this.overallRating = overallRating;
    }

    public List<Review> getPastReviews() {
        return pastReviews;
    }

    public Rating getRating() {
        return rating;
    }

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
