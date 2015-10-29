package moblima.boundary;

import moblima.entity.User;

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

        // start interacting with user
        while (User.getInstance().isActive()) {
            if (User.getInstance().isMovieGoer()) {
                MovieGoerInterface.getInstance().interact();
            } else {
                StaffInterface.getInstance().interact();
            }
        }

        System.out.println("Terminating MOBLIMA...");
    }
}
