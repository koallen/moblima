package moblima.boundary;

/**
 * Represent a staff interface
 * This interface allow user to
 * create/update/remove movie listing
 * creat/update/remove cinema showtime
 * list top 5 by sale/rating
 * change movie base price
 * add holiday
 * logout
 * @author SSP2 Team 1
 */

import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import moblima.entity.User;
import moblima.entity.User.TypeOfUser;
import moblima.entity.Review;
import moblima.entity.Cinema;
import moblima.entity.Cineplex;
import moblima.entity.MovieInfo;
import moblima.entity.MovieShowing;
import moblima.control.StaffController;
import moblima.control.StaffController.LogoutFeedback;

public class StaffInterface {
    /**
     * The StaffInterface is created by itself
     * Since StaffInterface is a singleton class
     * It can only be created by itself
     */
    private static StaffInterface staffInterface = null;
    /**
     * Created a moviegoer controller
     */
    private StaffController staffController;

    /**
     * To avoids other class to create a staff interface
     * It can only be created by itself
     */
    private StaffInterface() {
        staffController = StaffController.getInstance();
    }

    /**
     * Gets a staff interface
     * If saff interface has not been created yet
     * create a new staff interface then return it
     * @return the staff interface
     */
    public static StaffInterface getInstance() {
        if (staffInterface == null) {
            staffInterface = new StaffInterface();
        }
        return staffInterface;
    }

    /**
     * Interaction with the user
     * print the menu option
     * get user input
     * and then pass parameters to controller
     * display feedback to the user
     */
    protected void interact() {
        int choice, index;
        boolean logoutStatus;
        Scanner sc = new Scanner(System.in);

        print();
        choice = sc.nextInt();
        switch (choice) {
            case 1:
                createMovieListing(sc); // done
                break;
            case 2:
                index = listAndSelectMovie(sc); // done
                updateMovieListing(index, sc);
                break;
            case 3:
                index = listAndSelectMovie(sc);
                removeMovieListing(index); // done
                break;
            case 4:
                // done
                createMovieShowing(sc);
                break;
            case 5:
                // done
                updateMovieShowing(sc);
                break;
            case 6:
                index = listAndSelectMovieShowing(sc);
                removeMovieShowing(index); // done
                break;
            case 7:
                listTop5BySale(); // done
                break;
            case 8:
                listTop5ByRating(); // done
                break;
            case 9:
                // done
                index = listAndSelectMovie(sc);
                updateTicketPrice(index, sc);
                break;
            case 10:
                // done
                addHoliday(sc);
                break;
            case 11:
                // done
                logout();
                break;
            case 12:
                // done
                User.getInstance().setActive(false);
                break;
            default:
                System.out.println("Wrong input, please try again");
                break;
        }
    }

    /**
     * Print the menu option
     */
    private void print() {
        System.out.print(
        "===Menu===\n" +
        "Please select your choice below:\n" +
        "1. Create movie listing\n" +
        "2. Update movie listing\n" +
        "3. Remove movie listing\n" +
        "4. Create cinema showtime\n" +
        "5. Update cinema showtime\n" +
        "6. Remove cinema showtime\n" +
        "7. List Top 5 movies by sales\n" +
        "8. List Top 5 movies by overall rating\n" +
        "9. Change movie base price\n" +
        "10. Add holiday\n" +
        "11. Logout\n" +
        "12. Exit\n"
        );
    }

    /**
     * Creat movie listing
     */
    private void createMovieListing(Scanner sc) {
        MovieInfo movie;
        int count, choice, reviewRating, i;
        double basePrice;
        String title, synopsis, director, reviewContent;
        String[] casts;
        MovieInfo.ShowingStatus showingStatus;
        MovieInfo.TypeOfMovie typeOfMovie;
        List<Review> pastReviews;
        MovieInfo.Rating rating;

        sc.nextLine();
        System.out.print("Movie title: ");
        title = sc.nextLine();

        System.out.print(
        "Showing status:\n" +
        "1. Coming soon\n" +
        "2. Preview\n" +
        "3. Now showing\n" +
        "4. End of showing\n" +
        "Please enter one of the above without space in between: ");
        showingStatus = MovieInfo.ShowingStatus.valueOf(sc.next().toUpperCase());

        System.out.print(
        "Type of movie:\n" +
        "threeD, blockbuster\n" +
        "Please enter one of the above: ");
        typeOfMovie = MovieInfo.TypeOfMovie.valueOf(sc.next().toUpperCase());

        sc.nextLine();
        System.out.print("Synopsis: ");
        synopsis = sc.nextLine();

        System.out.print("Director: ");
        director = sc.nextLine();

        System.out.print("Number of casts: ");
        count = sc.nextInt();
        casts = new String[count];
        sc.nextLine();
        for (i=0; i<count; ++i) {
            System.out.print("Cast " + i + ": ");
            casts[i] = sc.nextLine();
        }

        System.out.print("Number of past reviews: ");
        count = sc.nextInt();
        pastReviews = new ArrayList<Review>();
        for (i=0; i<count; ++i) {
            sc.nextLine();
            System.out.println("Review" + i + ". ");
            System.out.print("Content of review: ");
            reviewContent = sc.nextLine();
            System.out.print("Rating: ");
            reviewRating = sc.nextInt();
            pastReviews.add(new Review(reviewContent, reviewRating));
        }

        System.out.print("Base price: ");
        basePrice = sc.nextDouble();

        System.out.print(
        "Movie ratings:\n" +
        "G, PG, PG13, NC16, M18, R21\n" +
        "Please enter one the of above: ");
        rating = MovieInfo.Rating.valueOf(sc.next().toUpperCase());

        movie = new MovieInfo(title, showingStatus, typeOfMovie, synopsis, director, casts, 0.0, (ArrayList<Review>)pastReviews, basePrice, rating, 0);
        staffController.calculateOverallRating(movie);
        staffController.createMovieListing(movie);

        System.out.println("Movie listing created");
    }

    /**
     *Update movie listing
     */
    private void updateMovieListing(int index, Scanner sc) {
        int choice;
        String synopsis;
        MovieInfo movieToUpdate, newMovieInfo;
        movieToUpdate = staffController.searchForMovie(index);
        newMovieInfo = (MovieInfo)movieToUpdate.clone();

        System.out.print(
        "Choose the field that you want to change:\n" +
        "1. Showing status\n" +
        "2. Type of movie\n" +
        "3. Synopsis\n" +
        "4. Finish modifying\n");
        choice = sc.nextInt();
        while (choice != 4) {
            switch (choice) {
                case 1:
                    System.out.print(
                    "Showing status:\n" +
                    "1. Coming soon\n" +
                    "2. Preview\n" +
                    "3. Now showing\n" +
                    "4. End of showing\n" +
                    "Please enter one of the above without space in between: ");
                    MovieInfo.ShowingStatus showingStatus = MovieInfo.ShowingStatus.valueOf(sc.next().toUpperCase());
                    staffController.updateShowingStatus(newMovieInfo, showingStatus);
                    break;
                case 2:
                    System.out.print(
                    "Type of movie:\n" +
                    "threeD, blockbuster\n" +
                    "Please enter one of the above: ");
                    MovieInfo.TypeOfMovie typeOfMovie = MovieInfo.TypeOfMovie.valueOf(sc.next().toUpperCase());
                    staffController.updateTypeOfMovie(newMovieInfo, typeOfMovie);
                    break;
                case 3:
                    System.out.print("Synopsis: ");
                    synopsis = sc.next();
                    staffController.updateSynopsis(newMovieInfo, synopsis);
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Wrong input, please try again");
            }
        }
        staffController.updateMovieListing(movieToUpdate, newMovieInfo);
    }

    /**
     *Remove movie listing
     */
    private void removeMovieListing(int index) {
        MovieInfo movieToRemove;
        movieToRemove = staffController.searchForMovie(index);
        staffController.removeMovieListing(movieToRemove);
    }

    /**
     *Creat movie Showing
     */
    private void createMovieShowing(Scanner sc) {
        MovieInfo movie;
        Cinema cinema;
        Cineplex cineplex;
        String showTimeString;
        Date showTime = null;
        int index;

        System.out.println("Choose a movie first");
        index = listAndSelectMovie(sc);
        movie = staffController.searchForMovie(index);

        index = listAndSelectCineplex(sc);
        cineplex = staffController.searchForCineplex(index);

        index = listAndSelectCinema(sc, cineplex);
        cinema = staffController.searchForCinema(index, cineplex);

        sc.nextLine();
        System.out.print("Input showtime in format DD/MM/YYYY HH:MM: ");
        showTimeString = sc.nextLine();
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            showTime = fmt.parse(showTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        MovieShowing movieShowingToAdd = new MovieShowing(movie, cinema, cineplex, showTime);
        staffController.createMovieShowing(movieShowingToAdd);
    }

    /**
     *Update movie showing
     */
    private void updateMovieShowing(Scanner sc) {
        String newShowTimeString;
        Date newShowTime = null;
        MovieShowing movieShowingToUpdate;

        System.out.println("Choose the movie showing to update");
        int index = listAndSelectMovieShowing(sc);
        movieShowingToUpdate = staffController.searchForMovieShowing(index);

        sc.nextLine();
        System.out.print("Input the new show time in format DD/MM/YYYY HH:MM: ");
        newShowTimeString = sc.nextLine();
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        try {
            newShowTime = fmt.parse(newShowTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        staffController.updateMovieShowing(newShowTime, movieShowingToUpdate);
    }

    /**
     * Remove movie showing
     */
    private void removeMovieShowing(int index) {
        MovieShowing movieShowingToRemove;
        movieShowingToRemove = staffController.searchForMovieShowing(index);
        staffController.removeMovieShowing(movieShowingToRemove);
    }

    /**
     * Update the base price of the movie
     */
    private void updateTicketPrice(int index, Scanner sc) {
        double price;
        MovieInfo movieToUpdate;
        movieToUpdate = staffController.searchForMovie(index);
        System.out.print("Please input the new base price: ");
        price = sc.nextDouble();
        staffController.updateTicketPrice(movieToUpdate, price);
    }

    /**
     * List and select movie to modify
     * @return the movie index
     */
    private int listAndSelectMovie(Scanner sc) {
        List<MovieInfo> movies = staffController.listAllMovies();
        int index;

        for (MovieInfo movie: movies) {
            System.out.println(movies.indexOf(movie) + ". " + movie.getTitle());
        }
        System.out.print("Please input the movie id: ");
        index = sc.nextInt();
        return index;
    }

    /**
     * List and select movie on showing to modify
     * @return the movie index
     */
    private int listAndSelectMovieShowing(Scanner sc) {
        List<MovieShowing> movieShowings = staffController.listAllShowings();
        int index;

        for (MovieShowing movieShowing: movieShowings) {
            System.out.println(movieShowings.indexOf(movieShowing) + ". " + movieShowing.toString());
        }
        System.out.print("Please input id of the movie showing: ");
        index = sc.nextInt();
        return index;
    }

    /**
     * List and select the cinema to modify
     * @return the cinema index
     */
    private int listAndSelectCinema(Scanner sc, Cineplex cineplex) {
        Cinema[] cinemas = staffController.listAllCinemas(cineplex);
        int index, i;

        for (i=0; i<cinemas.length; ++i) {
            System.out.println(i + ". " + cinemas[i].toString());
        }
        System.out.print("Please input id of the cinema: ");
        index = sc.nextInt();
        return index;
    }

    /**
     * List and select the cimeplex to modify
     * @return the cineplex index
     */
    private int listAndSelectCineplex(Scanner sc) {
        List<Cineplex> cineplexes = staffController.listAllCineplexes();
        int index;

        for (Cineplex cineplex: cineplexes) {
            System.out.println(cineplexes.indexOf(cineplex) + ". " + cineplex.toString());
        }
        System.out.print("Please input id of the cineplex: ");
        index = sc.nextInt();
        return index;
    }

    /**
     * List top 5 by sale
     */
    private void listTop5BySale() {
        List<MovieInfo> topMovies = staffController.getTop5BySale();
        System.out.println("The top 5 movies by sale are:");
        for (MovieInfo movie: topMovies) {
            System.out.println(movie.getTitle());
        }
    }

    /**
     * List top 5 by rating
     */
    private void listTop5ByRating() {
        List<MovieInfo> topMovies = staffController.getTop5ByRating();
        System.out.println("The top 5 movies by rating are:");
        for (MovieInfo movie: topMovies) {
            System.out.println(movie.getTitle());
        }
    }

    /**
     * Logout
     */
    private void logout() {
        LogoutFeedback feedback = staffController.logout();
        if (feedback == LogoutFeedback.LOGOUTSUCCESS) {
            System.out.println("Logout successful");
        }
    }

    /**
     * Add holiday
     */
    private void addHoliday(Scanner sc) {
        Date holiday;
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        System.out.print("Please input a holiday date in the format DD/MM/YYYY: ");
        try {
            holiday = fmt.parse(sc.next());
            staffController.createHoliday(holiday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
