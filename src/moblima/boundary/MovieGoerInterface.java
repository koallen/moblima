package moblima.boundary;

import moblima.control.MovieGoerController;

/**
 * Represent the interface for movie-goer
 * This interface allow user to
 * list all movies
 * search fo movie
 * book movie ticket
 * check booking history
 * add or remove review
 * and login as a staff
 * @author SSP2 Team 1
 */
public class MovieGoerInterface extends UserInterface {
    /**
     * The MovieGoerInterface is created by itself
     * Since MovieGoerInterface is a singleton class
     * It can only be created by itself
     */
    private static MovieGoerInterface movieGoerInterface = null;
    /**
     * To avoids other class to create a movie-goer interface
     * It can only be created by itself
     */
    private MovieGoerInterface() {}
    /**
     * Gets a movie-goer interface
     * If movie-goer interface has not been created yet
     * create a new movie-goer interface then return it
     * @return the movie-goer interface
     */
    public static MovieGoerInterface getInstance() {
        if (movieGoerInterface == null) {
            movieGoerInterface = new MovieGoerInterface();
        }
        return movieGoerInterface;
    }
    /**
     * Interaction with the user
     * print the menu option
     * get user input
     * and then pass parameters to controller
     * display feedback to the user
     */
    public void interact() {
        int choice;
        MovieGoerController movieGoerController = MovieGoerController.getInstance();

        print();
        choice = scanInteger("Input your choice: ");
        switch (choice) {
            case 1:
                movieGoerController.listAllMovies();
                break;
            case 2:
                movieGoerController.searchAndDisplayMovieInfo();
                break;
            case 3:
                movieGoerController.bookAMovie();
                break;
            case 4:
                movieGoerController.viewBookingHistory();
                break;
            case 5:
                movieGoerController.addReview();
                break;
            case 6:
                movieGoerController.listTop5BySale();
                break;
            case 7:
                movieGoerController.listTop5ByRating();
                break;
            case 8:
                movieGoerController.loginAsStaff();
                break;
            case 9:
                movieGoerController.exit();
                break;
            default:
                System.out.println("Wrong input, please try again");
                break;
        }
    }
    /**
     * Print the menu option
     */
    public void print() {
        System.out.println(
        "==========Menu==========\n" +
        "1. List all movies\n" +
        "2. Search for movies\n" +
        "3. Book movie ticket(s)\n" +
        "4. Check booking history\n" +
        "5. Add movie review\n" +
        "6. List top 5 by sale\n" +
        "7. List top 5 by rating\n" +
        "8. Staff login\n" +
        "9. Exit");
    }
}
