package moblima.control;

import java.util.*;
import moblima.entity.Booking;
import moblima.entity.MovieInfo;
import moblima.entity.MovieShowing;

public class MovieGoerController {
    private List<Date> holidays;
    private ArrayList<MovieInfo> movies;
    private ArrayList<Booking> bookings;
    private ArrayList<MovieShowing> movieShowings;

    public MovieGoerController(ArrayList<MovieInfo> movies, ArrayList<Booking> bookings, ArrayList<MovieShowing> movieShowings) {
        this.movies = movies;
        this.bookings = bookings;
        this.movieShowings = movieShowings;
    }
    public MovieShowing getMovies(int choice){
        return movieShowings.get(choice);
    }
    public void list(){
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        for (int count = 0; count < movies.size(); count++){
            System.out.println("This is the list for all movies");
            System.out.println(count + ". " + movies.get(count).toString());
        }
    }
    public MovieShowing listAndChooseShowing(String movieName){
        List <MovieShowing> tempList = new ArrayList<MovieShowing>();
        Scanner sc = new Scanner(System.in);
        for (int count = 0; count < movieShowings.size(); count++) {
            if (movieShowings.get(count).getMovie().getTitle() == movieName){
                tempList.add(movieShowings.get(count));
                movieShowings.get(count).toString();
            }
            if(tempList.size() == 0){
                return null;
            }
            else {
                System.out.println("Please choose the no. of movie showing");
                int choice = sc.nextInt();
                return tempList.get(choice);
            }
        }
        return null;
    }
    public void viewMovieDetail(String movieName){
        MovieInfo movie = search(movieName);
        movie.toString();
    }
    public void printLayout(MovieShowing movieShowing){
        Seat[][] seats = movieShowing.getCinema().getSeatLayout();
        for (int count = 0; count <= movieShowing.getCinema().getRow(); count++){
            for(int count1 = 0; count1 <= movieShowing.getCinema().getCol(); count1++){
                seats[count][count1].toString();
            }
            System.out.print("\n");
        }
    }
    public Seat selectSeat(MovieShowing movieShowing, int row, int col){
        Seat[][] seats = movieShowing.getCinema().getSeatLayout();
        if(seats[row][col].getStatus() == Seat.enumSeat.TAKEN) {
            System.out.println("This seat is taken, please choose another one.");
            return null;
        }
        else if (seats[row][col].getStatus() == Seat.enumSeat.NOTEXIST){
            System.out.println("This seat does not exist.");
            return null;
        }
        else if (seats[row][col].getStatus() == Seat.enumSeat.NOTTAKEN){
            seats[row][col].setStatus(Seat.enumSeat.TAKEN);
            seats[row][col].setID(row, col);
            System.out.println("Seat is successfully taken");
            return seats[row][col];
        }
        else{
            System.out.println("Error");
            return null;
        }
    }
    public void book(MovieTicket movieTicket, Payment payment){
        Booking booking = new Booking(movieTicket, payment);
        bookings.add(booking);
    }
    public double calculate(MovieShowing movieShowing){
        double price = 0.0;
        System.out.println("Are you a previliged person? (y/n)");
        Scanner sc = new Scanner(System.in);
        char choice = sc.next().charAt(0);
        if(choice == 'n'){
            System.out.println("The price of the movie ticket is " + movieShowing.getMovie().getBasePrice());
            price = movieShowing.getMovie().getBasePrice();
        }
        else if (choice == 'y'){
            System.out.println("The price of the movie ticket is " + 0.5 * movieShowing.getMovie().getBasePrice());
            price = 0.5 * movieShowing.getMovie().getBasePrice();
        }
        for (int count = 0; count < holidays.size(); count++){
            if(movieShowing.getShowTime() == holidays.get(count)){
                price += 3;
            }

        }
        return price;
    }
    public void viewBookingHistory(String userName){
        for (int count = 0; count < bookings.size(); count++){
            if (bookings.get(count).getPayment().getName() == userName){
                bookings.get(count).toString();
            }
        }
    }
    public void createMovieReview(MovieInfo movie){
        System.out.println("Please input the review");
        Scanner sc = new Scanner(System.in);
        String reviewstr = sc.next();
        System.out.println("Please input the rating");
        int rating = sc.nextInt();
        Review review = new Review(reviewstr,rating);
        movie.getPastReviews().add(review);
    }
    public MovieInfo search(String movieName){
        for (int count = 0; count < movies.size(); count++) {
            if (movies.get(count).getTitle() == movieName){
                return movies.get(count);
            }
        }
        return null;
    }
}
