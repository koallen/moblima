package moblima.entity;

public class Review {
    private String content;
    private int rating;

    public Review(String content, int rating) {
        this.content = content;
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public int getRating() {
        return rating;
    }

    public String toString() {
        return content;
    }
}
