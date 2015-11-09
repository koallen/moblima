package moblima.boundary;
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

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import moblima.entity.User;
import moblima.entity.User.TypeOfUser;
import moblima.entity.Seat;
import moblima.entity.Review;
import moblima.entity.Booking;
import moblima.entity.Payment;
import moblima.entity.MovieInfo;
import moblima.entity.MovieTicket;
import moblima.entity.MovieShowing;
import moblima.control.MovieGoerController;
import moblima.control.StaffController;
import moblima.control.StaffController.LoginFeedback;


public class MovieGoerInterface {
    /**
     * The MovieGoerInterface is created by itself
     * Since MovieGoerInterface is a singleton class
     * It can only be created by itself
     */
    private static MovieGoerInterface movieGoerInterface = null;
    /**
     * Created a moviegoer controller
     */
    private MovieGoerController movieGoerController;
    /**
     * To avoids other class to create a movie-goer interface
     * It can only be created by itself
     */
    private MovieGoerInterface(){
        movieGoerController = MovieGoerController.getInstance();
    }
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
    protected void interact() {
        int choice;
        String movieName, bookingId;
        MovieInfo movieInfo;
        MovieShowing movieShowing;


        Scanner sc = new Scanner(System.in);

        print();
        choice = sc.nextInt();
        switch (choice) {
            case 1:
                // done
                listAllMovies();
                break;
            case 2:
                // done
                searchMovies(sc);
                break;
            case 3:
                bookAMovie(sc);
                break;
            case 4:
                // done
                viewBookingHistory(sc);
                break;
            case 5:
                // done
                addReview(sc);
                break;
            case 6:
                // done
                loginAsStaff(sc);
                return;
            case 7:
                // done
                User.getInstance().setActive(false);
                return;
            default:
                break;
        }
    }

    /**
     * Print the menu option
     */
    private void print() {
        System.out.print(
        "===============Menu=============\n" +
        "Please select your choice below:\n" +
        "1. List all movies\n" +
        "2. Search for movies\n" +
        "3. Book movie ticket(s)\n" +
        "4. Check booking history\n" +
        "5. Add movie review\n"+
        "6. Staff login\n" +
        "7. Exit\n");
    }

    /**
     * Book a movie ticket
     * let user choose the movie
     * check whether the ticket is avaliable
     * If ticket is avaliable
     * choose the seats
     * and complete the booking
     * with name email and mobile phone number
     */
    private void bookAMovie(Scanner sc){
        String movieName, movieGoerName, bookingId;
        int index;
        MovieInfo movieToBook;
        MovieShowing movieShowing;
        List<MovieInfo> searchResult;
        List<MovieShowing> movieShowings;
        Seat[][] seats;
        Seat seat;

        sc.nextLine();
        while (true) {
            System.out.print("Please input the name of the movie that you want to book: ");
            movieName = sc.nextLine();
            searchResult = movieGoerController.search(movieName);
            if (searchResult.size() != 0) {
                for (MovieInfo movie: searchResult) {
                    System.out.println(searchResult.indexOf(movie) + ". " + movie.getTitle());
                }
                break;
            } else {
                System.out.println("No matching movie found!");
                continue;
            }
        }

        while (true) {
            System.out.print("Input the movie id that you want to book: ");
            index = sc.nextInt();
            if (index >= searchResult.size()) {
                System.out.println("Wrong index!");
            } else {
                break;
            }
        }

        movieToBook = searchResult.get(index);
        movieShowings = movieGoerController.listMovieShowing(movieToBook);
        if (movieShowings.size() == 0) {
            System.out.println("The movie isn't showing now");
            return;
        }
        for (MovieShowing show: movieShowings) {
            System.out.println(movieShowings.indexOf(show) + ".\n" + show.toString());
        }

        while (true) {
            System.out.print("Input the showing id that you want to book: ");
            index = sc.nextInt();
            if (index >= movieShowings.size()) {
                System.out.println("Wrong index!");
            } else {
                break;
            }
        }

        movieShowing = movieShowings.get(index);
        seats = movieGoerController.getSeats(movieShowing);
        printLayout(seats);

        while (true) {
            System.out.println("Which seat do you want?");
            System.out.println("Input row:");
            int row = sc.nextInt();
            System.out.println("Input col:");
            int col = sc.nextInt();
            seat = movieGoerController.selectSeat(movieShowing, row, col);
            if (seat != null) {
                System.out.println("Seat successfully taken");
                break;
            }
            System.out.println("Seat selection unsuccessful\nMake sure you select an empty seat");
        }

        double price = movieGoerController.calculate(movieShowing);
        MovieTicket movieTicket = new MovieTicket(movieShowing, seat, price);
        sc.nextLine();
        System.out.print("Please input your name: ");
        String name = sc.nextLine();
        System.out.print("Please input your mobile number: ");
        String mobileNumber = sc.next();
        System.out.print("Please input your email address: ");
        String email = sc.next();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddhhmm");
        String transactionID = movieShowing.getCinema().getCinemaCode() + fmt.format(new Date());
        Payment payment = new Payment(transactionID, name, mobileNumber, email, price);
        movieGoerController.book(movieTicket, payment, movieToBook);
        System.out.println("Booking is successful\n");
    }

    /**
     * Log in as staff
     * Check the username and password
     * Check whether the user already logged in
     */
    private void loginAsStaff(Scanner sc) {
        String username, password;

        System.out.print("Input username: ");
        username = sc.next();
        System.out.print("Input password: ");
        password = sc.next();

        LoginFeedback feedback = StaffController.getInstance().login(username, password);
        switch (feedback) {
            case LOGINSUCCESS:
                System.out.println("Login successful");
                break;
            case ALREADYLOGGEDIN:
                break;
            case WRONGUSERNAMEPASSWORD:
                System.out.println("Wrong username or password, please try again");
                break;
            default:
                break;
        }
    }

    /**
     * View booking history of the input name
     */
    private void viewBookingHistory(Scanner sc) {
        String movieGoerName;

        System.out.print("Please input your name: ");
        movieGoerName = sc.next();

        ArrayList<Booking> bookingsByUser = movieGoerController.getBookingHistory(movieGoerName);
        if (bookingsByUser.size() != 0) {
            System.out.println("Here are your bookings:\n");
            for (Booking booking: bookingsByUser) {
                System.out.println(bookingsByUser.indexOf(booking) + ".\n" + booking.toString() + "\n");
            }
        } else {
            System.out.println("No booking record found");
        }

    }

    /**
     * List all movies
     */
    private void listAllMovies() {
        ArrayList<MovieInfo> movies = movieGoerController.listAllMovies();
        System.out.println("This is the list of all movies\n");
        for (MovieInfo movie: movies){
            System.out.println(movies.indexOf(movie) + ".\n" + movie.toString() + "\n");
        }
    }

    /**
     * Search movies
     */
    private ArrayList<MovieInfo> searchMovies(Scanner sc) {
        ArrayList<MovieInfo> searchResult;
        String movieName;

        sc.nextLine();
        System.out.print("Please input the movie name: ");
        movieName = sc.nextLine();
        searchResult = movieGoerController.search(movieName);

        if (searchResult.size() != 0) {
            System.out.println("Search result:\n");
            for (MovieInfo movie: searchResult) {
                System.out.println(searchResult.indexOf(movie) + ". " + movie.getTitle() + "\n");
            }
        } else {
            System.out.println("No matching movie found!");
        }

        return searchResult;
    }

    /**
     *
     */
    private void addReview(Scanner sc) {
        List<MovieInfo> results = searchMovies(sc);
        int index, rating;
        String content;

        System.out.print("Please input the movie id: ");
        index = sc.nextInt();
        System.out.println("Please input review content:");
        content = sc.next();
        sc.nextLine();
        System.out.print("Please input review rating: ");
        rating = sc.nextInt();

        Review viewerReview = new Review(content, rating);
        results.get(index).addReview(viewerReview);

        // design consideration
        StaffController.getInstance().calculateOverallRating(results.get(index));

        System.out.println("Review successfully added\n");
    }

    /**
     * Print the seat layout of the cinema
     * that is showing the movie
     * @param seats All seats in a cinema
     */
    private void printLayout(Seat[][] seats){
        for (int i = 0; i < seats.length; i++){
            for(int j = 0; j < seats[i].length; j++){
                System.out.print(seats[i][j].toString());
            }
            System.out.println();
        }
    }
}
