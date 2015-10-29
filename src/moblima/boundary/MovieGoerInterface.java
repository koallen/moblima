package moblima.boundary;

import java.util.Scanner;
import moblima.entity.User;
import moblima.entity.User.TypeOfUser;
import moblima.entity.Seat;
import moblima.entity.Payment;
import moblima.entity.MovieInfo;
import moblima.entity.MovieTicket;
import moblima.entity.MovieShowing;
import moblima.control.MovieGoerController;

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
        String movieName, movieGoerName, bookingId;
        MovieInfo moveInfo;
        MovieShowing movieShowing;


        Scanner sc = new Scanner(System.in);

        print();
        choice = sc.nextInt();
        switch (choice) {
            case 1:
                movieGoerController.list();
                break;
            case 2:
                System.out.print("Please input the movie name to search: ");
                movieName = sc.next();
                movieGoerController.search(movieName);
                break;
            case 3:
                bookingProcedure();
                break;
            case 4:
                System.out.print("Please input your name: ");
                movieGoerName = sc.next();
                movieGoerController.viewBookingHistory(movieGoerName);
                break;
            case 5:
                System.out.print("Please input the movie name: ");
                movieName = sc.next();
                MovieInfo movie = movieGoerController.search(movieName);
                movieGoerController.createMovieReview(movie);
                break;
            case 6:
                User.getInstance().setActive(false);
            default:
                break;
        }
    }
    private void bookingProcedure(){
        String movieName, movieGoerName, bookingId;
        MovieInfo moveInfo;
        MovieShowing movieShowing;


        Scanner sc = new Scanner(System.in);
        while (true){
                    System.out.println("Please input the name of the movie that you want to book");
                    movieName = sc.next();
                    movieGoerController.viewMovieDetail(movieName);
                    System.out.println("Do you want to book this movie? (y/n)");
                    char choice1 = sc.next().charAt(0);
                    if(choice1 == 'y') {
                        movieShowing = movieGoerController.listAndChooseShowing(movieName);
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
                        String transactionID = null;
                        Payment payment = new Payment(transactionID, name, mobileNumber, email, price);
                        movieGoerController.book(movieTicket, payment);
                    }
                    else {
                        continue;
                    }
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
        "6. Exit\n");
    }
}
