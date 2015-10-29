package moblima.boundary;

import moblima.entity.User;

public class UserInterface {
    private User currentUser;
    private MovieGoerInterface movieGoerInterface;
    private StaffInterface staffInterface;

    public UserInterface(MovieGoerInterface movieGoerInterface, StaffInterface staffInterface) {
        this.movieGoerInterface = movieGoerInterface;
        this.staffInterface = staffInterface;
        this.currentUser = new User();
    }

    public void start() {
        System.out.println("Starting MOBLIMA...");

        // start interacting with user
        while (currentUser.isActive()) {
            if (currentUser.isMovieGoer()) {
                currentUser = movieGoerInterface.interact();
            } else {
                currentUser = staffInterface.interact();
            }
        }

        System.out.println("Terminating MOBLIMA...");
    }
}
