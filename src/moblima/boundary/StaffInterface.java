package moblima.boundary;

import moblima.control.StaffController;

/**
 * Represent a staff interface
 * This interface allow user to
 * create/update/remove movie listing
 * creat/update/remove cinema showtime
 * list top 5 by sale/rating
 * change movie base price
 * add holiday
 * logout
 * @author SSP2 Team 1
 */
public class StaffInterface extends UserInterface {
    /**
     * The StaffInterface is created by itself
     * Since StaffInterface is a singleton class
     * It can only be created by itself
     */
    private static StaffInterface staffInterface = null;
    /**
     * To avoids other class to create a staff interface
     * It can only be created by itself
     */
    private StaffInterface() {}
    /**
     * Gets a staff interface
     * If saff interface has not been created yet
     * create a new staff interface then return it
     * @return the staff interface
     */
    public static StaffInterface getInstance() {
        if (staffInterface == null) {
            staffInterface = new StaffInterface();
        }
        return staffInterface;
    }
    /**
     * Interaction with the user
     * print the menu option
     * get user input
     * and then pass parameters to controller
     * display feedback to the user
     */
    public void interact() {
        int choice;
        StaffController staffController = StaffController.getInstance();

        print();
        choice = scanInteger("Input your choice: ");
        switch (choice) {
            case 1:
                staffController.createMovieListing();
                break;
            case 2:
                staffController.updateMovieListing();
                break;
            case 3:
                staffController.removeMovieListing();
                break;
            case 4:
                staffController.createMovieShowing();
                break;
            case 5:
                staffController.updateMovieShowing();
                break;
            case 6:
                staffController.removeMovieShowing();
                break;
            case 7:
                staffController.updateTicketPrice();
                break;
            case 8:
                staffController.addHoliday();
                break;
            case 9:
                staffController.logout();
                break;
            case 10:
                staffController.exit();
                break;
            default:
                System.out.println("Wrong input, please try again");
                break;
        }
    }
    /**
     * Print the menu option
     */
    public void print() {
        System.out.println(
        "===========Menu===========\n" +
        "1. Create movie listing\n" +
        "2. Update movie listing\n" +
        "3. Remove movie listing\n" +
        "4. Create cinema showtime\n" +
        "5. Update cinema showtime\n" +
        "6. Remove cinema showtime\n" +
        "7. Change movie base price\n" +
        "8. Add holiday\n" +
        "9. Logout\n" +
        "10. Exit");
    }
}
