package moblima.boundary;

import java.util.*;
import moblima.entity.User;
import moblima.entity.Review;
import moblima.entity.MovieInfo;
import moblima.entity.MovieShowing;
import moblima.control.StaffController;

/**
* TODOs: implement update/removeMovieListing, create/update/removeMovieShowing, updateTicketPrice, addHoliday
*/
public class StaffInterface {
    private StaffController staffController;

    public StaffInterface(StaffController staffController) {
        this.staffController = staffController;
    }
    public User interact() {
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
                createMovieShowing(sc);
                break;
            case 5:
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
                //addHoliday();
                break;
            case 11:
                // done
                logoutStatus = logout();
                return new User();
            default:
                System.out.println("Wrong input, please try again");
                break;
        }
        return new User(User.TypeOfUser.STAFF);
    }

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
        "11. Logout\n"
        );
    }

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

        System.out.print("Movie title: ");
        title = sc.next();

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

        System.out.print("Synopsis: ");
        synopsis = sc.next();

        System.out.print("Director: ");
        director = sc.next();

        System.out.print("Number of casts: ");
        count = sc.nextInt();
        casts = new String[count];
        for (i=0; i<count; ++i) {
            System.out.print("Cast " + i + ": ");
            casts[i] = sc.next();
        }

        System.out.print("Number of past reviews: ");
        count = sc.nextInt();
        pastReviews = new ArrayList<Review>();
        for (i=0; i<count; ++i) {
            System.out.print("Content of review: ");
            reviewContent = sc.next();
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

        System.out.println("Movie listing created");
    }

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

    private void removeMovieListing(int index) {
        MovieInfo movieToRemove;
        movieToRemove = staffController.searchForMovie(index);
        staffController.removeMovieListing(movieToRemove);
    }

    private void createMovieShowing(Scanner sc) {

    }

    private void updateMovieShowing(Scanner sc) {

    }

    private void removeMovieShowing(int index) {
        MovieShowing movieShowingToRemove;
        movieShowingToRemove = staffController.searchForMovieShowing(index);
        staffController.removeMovieShowing(movieShowingToRemove);
    }

    private void updateTicketPrice(int index, Scanner sc) {
        double price;
        MovieInfo movieToUpdate;
        movieToUpdate = staffController.searchForMovie(index);
        System.out.print("Please input the new base price: ");
        price = sc.nextDouble();
        staffController.updateTicketPrice(movieToUpdate, price);
    }

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

    private void listTop5BySale() {
        List<MovieInfo> topMovies = staffController.getTop5BySale();
        System.out.println("The top 5 movies by sale are:");
        for (MovieInfo movie: topMovies) {
            System.out.println(movie.getTitle());
        }
    }

    private void listTop5ByRating() {
        List<MovieInfo> topMovies = staffController.getTop5ByRating();
        System.out.println("The top 5 movies by rating are:");
        for (MovieInfo movie: topMovies) {
            System.out.println(movie.getTitle());
        }
    }

    private boolean logout() {
        StaffController.LogoutFeedback logoutStatus = staffController.logout();
        if (logoutStatus == StaffController.LogoutFeedback.LOGOUTSUCCESS) {
            System.out.println("Logout successful");
            return true;
        } else {
            System.out.println("You are already logged out");
            return false;
        }
    }
}
