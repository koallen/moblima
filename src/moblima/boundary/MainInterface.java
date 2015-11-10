package moblima.boundary;

/**
 * Represent the interface for user
 * This interface set the user as movie-goer
 * and then start the interacting with user
 * @author SSP2 Team 1
 */

import moblima.entity.User;

public class MainInterface {
    /**
     * The UserInterface is created by itself
     * Since UserInterface is a singleton class
     * It can only be created by itself
     */
    private static MainInterface mainInterface = null;

    /**
     * To avoids other class to create a user interface
     * It can only be created by itself
     */
    private MainInterface() {}

    /**
     * Gets a user interface
     * If user interface has not been created yet
     * create a new user interface then return it
     * @return the user interface
     */
    public static MainInterface getInstance() {
        if (mainInterface == null) {
            mainInterface = new MainInterface();
        }
        return mainInterface;
    }

    /**
     * Start the interacting with user
     * that by default is movie-goer
     */
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
