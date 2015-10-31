package moblima.entity;
/**
 * Represent a user who is using MOBLIMA
 * Including STAFF and MOVIEGOER
 * @author SSP2 Team 1
 */

public class User {
    /**
     * Enumeration for type of users
     * Including STAFF and MOVIEGOER
     */
    public enum TypeOfUser {STAFF, MOVIEGOER}
    /**
     * The user who is using MOBLIMA
     */
    private static User user = null;
    /**
     * The type of a user
     */
    private TypeOfUser typeOfUser;
    /**
     * The active status of a user
     */
    private boolean active;
    /**
     * Creates a MOVIEGOER user as default
     * The user is active
     */
    private User() {
        typeOfUser = TypeOfUser.MOVIEGOER;
        active = true;
    }
    /**
     * Gets the instance of this user
     * @return the user
     */
    public static User getInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }
    /**
     * Changes the type of the user
     * @param typeOfUser The new type of the user
     */
    public void setTypeOfUser(TypeOfUser typeOfUser) {
        this.typeOfUser = typeOfUser;
    }
    /**
     * Changes the active status of the user
     * @param active The new active status of the user
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    /**
     * Determines whether the user is a MOVIEGOER
     * @return boolean for if the user is a MOVIEGOER
     */
    public boolean isMovieGoer() {
        if (typeOfUser == TypeOfUser.MOVIEGOER)
            return true;
        return false;
    }
    /**
     * Determines whether the user is avtive
     * @return boolean for its active status
    */
    public boolean isActive() {
        return active;
    }
}
