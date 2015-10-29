package moblima.boundary;

import moblima.entity.User;

public class UserInterface {
    private User currentUser;

    public UserInterface() {
        currentUser = new User();
    }

    public void start() {
        System.out.println("Starting MOBLIMA...");

        // start interacting with user
        while (currentUser.isActive()) {
            if (currentUser.isMovieGoer()) {
                currentUser = MovieGoerInterface.interact();
            } else {
                currentUser = StaffInterface.interact();
            }
        }

        System.out.println("Terminating MOBLIMA...");
    }
}
