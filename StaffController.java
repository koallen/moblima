import java.util.*;

/**
* TODOs: add a search function
*/
public class StaffController {
    public enum LoginFeedback {WRONGUSERNAMEPASSWORD, ALREADYLOGGEDIN, LOGINSUCCESS}
    public enum LogoutFeedback {ALREADYLOGGEDOUT, LOGOUTSUCCESS}

    private Cineplex[] cineplexes;
    private List<MovieInfo> movies;
    private List<Booking> bookings;
    private List<MovieShowing> movieShowings;
    private List<Date> holidays;
    private boolean loggedIn;

    public StaffController(Cineplex[] cineplexes, ArrayList<MovieInfo> movies, ArrayList<Booking> bookings, ArrayList<MovieShowing> movieShowings, ArrayList<Date> holidays) {
        this.cineplexes = cineplexes;
        this.movies = movies;
        this.bookings = bookings;
        this.movieShowings = movieShowings;
        this.holidays = holidays;
        this.loggedIn = false;
    }

    public ArrayList<MovieInfo> listAllMovies() {
        return (ArrayList<MovieInfo>)movies;
    }

    public ArrayList<MovieShowing> listAllShowings() {
        return (ArrayList<MovieShowing>)movieShowings;
    }

    public MovieInfo searchForMovie(int index) {
        return movies.get(index);
    }

    public MovieShowing searchForMovieShowing(int index) {
        return movieShowings.get(index);
    }

    public void updateShowingStatus(MovieInfo movieToUpdate, MovieInfo.ShowingStatus showingStatus) {
        movieToUpdate.setShowingStatus(showingStatus);
    }

    public void updateTypeOfMovie(MovieInfo movieToUpdate, MovieInfo.TypeOfMovie typeOfMovie) {
        movieToUpdate.setTypeOfMovie(typeOfMovie);
    }

    public void updateSynopsis(MovieInfo movieToUpdate, String synopsis) {
        movieToUpdate.setSynopsis(synopsis);
    }

    public LoginFeedback login(String username, String password) {
        if (!loggedIn) {
            if (username.equals("moblimaStaff") && password.equals("imStaff")) {
                return LoginFeedback.LOGINSUCCESS;
            } else {
                return LoginFeedback.WRONGUSERNAMEPASSWORD;
            }
        } else {
            return LoginFeedback.ALREADYLOGGEDIN;
        }
    }

    public LogoutFeedback logout() {
        if (loggedIn) {
            return LogoutFeedback.LOGOUTSUCCESS;
        } else {
            return LogoutFeedback.ALREADYLOGGEDOUT;
        }
    }

    public void createMovieListing(MovieInfo movie) {
        movies.add(movie);
    }

    public void updateMovieListing(MovieInfo oldMovie, MovieInfo newMovie) {
        int index = movies.indexOf(oldMovie);
        movies.set(index, newMovie);
    }

    public void removeMovieListing(MovieInfo movie) {
        int index;
        for (MovieShowing movieshowing: movieShowings) {
            if (movieshowing.getMovie().equals(movie)) {
                index = movieShowings.indexOf(movieshowing);
                movieShowings.remove(index);
            }
        }
        movie.setShowingStatus(MovieInfo.ShowingStatus.ENDOFSHOWING);
    }

    public void createMovieShowing(MovieShowing movieShowing) {
        movieShowings.add(movieShowing);
    }

    public void updateMovieShowing(MovieShowing oldMovieShowing, MovieShowing newMovieShowing) {
        int index = movieShowings.indexOf(oldMovieShowing);
        movieShowings.set(index, newMovieShowing);
    }

    public void removeMovieShowing(MovieShowing movieShowing) {
        int index = movieShowings.indexOf(movieShowing);
        movieShowings.remove(index);
    }

    public ArrayList<MovieInfo> getSaleReport() {
        return (ArrayList<MovieInfo>)movies;
    }

    public void updateTicketPrice(MovieInfo movie, double price) {
        movie.setBasePrice(price);
    }

    public void createHoliday(Date holiday) {
        holidays.add(holiday);
    }

    public ArrayList<MovieInfo> getTop5BySale() {
        Collections.sort(movies, new Comparator<MovieInfo>() {
            @Override
            public int compare(MovieInfo movie1, MovieInfo movie2) {
                return movie1.getSale() - movie2.getSale();
            }
        });

        return (ArrayList<MovieInfo>)movies.subList(movies.size()-5, movies.size());
    }

    public ArrayList<MovieInfo> getTop5ByRating() {
        Collections.sort(movies, new Comparator<MovieInfo>() {
            @Override
            public int compare(MovieInfo movie1, MovieInfo movie2) {
                return (movie1.getOverallRating() - movie2.getOverallRating());
            }
        });

        return (ArrayList)movies.subList(movies.size()-5, movies.size());
    }

    public void calculateOverallRating(MovieInfo movie) {
        int sum = 0;
        for (Review pastReview: movie.getPastReviews()) {
            sum += pastReview.getRating();
        }
        double overallRating = (double)sum / movie.getPastReviews().size();
        movie.setOverallRating(overallRating);
    }
}
