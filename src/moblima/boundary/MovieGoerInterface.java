package moblima.boundary;

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
    private static MovieGoerInterface movieGoerInterface = null;
    private MovieGoerController movieGoerController;

    private MovieGoerInterface(){
        movieGoerController = MovieGoerController.getInstance();
    }

    public static MovieGoerInterface getInstance() {
        if (movieGoerInterface == null) {
            movieGoerInterface = new MovieGoerInterface();
        }
        return movieGoerInterface;
    }

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

    private void bookAMovie(Scanner sc){
        String movieName, movieGoerName, bookingId;
        int index;
        MovieInfo movieToBook;
        MovieShowing movieShowing;
        List<MovieInfo> searchResult;
        List<MovieShowing> movieShowings;

        while (true) {
            System.out.print("Please input the name of the movie that you want to book: ");
            movieName = sc.next();
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
        System.out.print("Input the movie id that you want to book: ");
        index = sc.nextInt();
        movieToBook = movieGoerController.searchForMovie(index);
        movieShowings = movieGoerController.listMovieShowing(movieToBook);
        for (MovieShowing show: movieShowings) {
            System.out.println(movieShowings.indexOf(show) + show.toString());
        }
        System.out.print("Input the showing id that you want to book: ");
        index = sc.nextInt();
        movieShowing = movieShowings.get(index);
        movieGoerController.printLayout(movieShowing);
        System.out.println("Which seat do you want?");
        System.out.println("Input row:");
        int row = sc.nextInt();
        System.out.println("Input col:");
        int col = sc.nextInt();
        Seat seat = movieGoerController.selectSeat(movieShowing, row, col);
        double price = movieGoerController.calculate(movieShowing);
        MovieTicket movieTicket = new MovieTicket(movieShowing, seat, price);
        System.out.println("Please input your name: ");
        String name = sc.next();
        System.out.println("Please input your mobile number: ");
        String mobileNumber = sc.next();
        System.out.println("Please input your email address: ");
        String email = sc.next();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddhhmm");
        String transactionID = movieShowing.getCinema().getCinemaCode() + fmt.format(new Date());
        Payment payment = new Payment(transactionID, name, mobileNumber, email, price);
        movieGoerController.book(movieTicket, payment);
        System.out.println("Booking is successful\n");
    }

    private void loginAsStaff(Scanner sc) {
        String username, password;

        System.out.print("Input username: ");
        username = sc.next();
        System.out.print("Input password: ");
        password = sc.next();

        LoginFeedback feedback = StaffController.getInstance().login(username, password);
        switch (feedback) {
            case LOGINSUCCESS:
                User.getInstance().setTypeOfUser(TypeOfUser.STAFF);
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

    private void viewBookingHistory(Scanner sc) {
        String movieGoerName;

        System.out.print("Please input your name: ");
        movieGoerName = sc.next();

        ArrayList<Booking> bookingsByUser = movieGoerController.getBookingHistory(movieGoerName);
        if (bookingsByUser.size() != 0) {
            System.out.println("Here are your bookings:\n");
            for (Booking booking: bookingsByUser) {
                System.out.println(bookingsByUser.indexOf(booking) + ".\n" + booking.toString());
            }
        } else {
            System.out.println("No booking record found");
        }

    }

    private void listAllMovies() {
        ArrayList<MovieInfo> movies = movieGoerController.listAllMovies();
        System.out.println("This is the list of all movies\n");
        for (MovieInfo movie: movies){
            System.out.println(movies.indexOf(movie) + ".\n" + movie.toString() + "\n");
        }
    }

    private ArrayList<MovieInfo> searchMovies(Scanner sc) {
        ArrayList<MovieInfo> searchResult;
        String movieName;

        System.out.print("Please input the movie name: ");
        movieName = sc.next();
        searchResult = movieGoerController.search(movieName);

        if (searchResult.size() != 0) {
            System.out.println("Search result:\n");
            for (MovieInfo movie: searchResult) {
                System.out.println(searchResult.indexOf(movie) + ".\n" + movie.getTitle() + "\n");
            }
        } else {
            System.out.println("No matching movie found!");
        }

        return searchResult;
    }

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

        System.out.println("Review successfully added\n");
    }
}
