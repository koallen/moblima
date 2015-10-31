package moblima.boundary;

import moblima.entity.User;

/**
 * Finished class
 */
public class UserInterface {
    private static UserInterface userInterface = null;

    private UserInterface() {}

    public static UserInterface getInstance() {
        if (userInterface == null) {
            userInterface = new UserInterface();
        }
        return userInterface;
    }

    public void start() {
        System.out.println("Starting MOBLIMA...");
        User currentUser = User.getInstance();
        // start interacting with user
        while (currentUser.isActive()) {
            if (currentUser.isMovieGoer()) {
                MovieGoerInterface.getInstance().interact();
            } else {
                StaffInterface.getInstance().interact();
            }
        }

        System.out.println("Terminating MOBLIMA...");
    }
}
