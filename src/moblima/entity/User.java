package moblima.entity;

public class User {
    public enum TypeOfUser {STAFF, MOVIEGOER}

    private static User user = null;
    private TypeOfUser typeOfUser;
    private boolean active;

    private User() {
        typeOfUser = TypeOfUser.MOVIEGOER;
        active = true;
    }

    public static User getInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public void setTypeOfUser(TypeOfUser typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isMovieGoer() {
        if (typeOfUser == TypeOfUser.MOVIEGOER)
            return true;
        return false;
    }

    public boolean isActive() {
        return active;
    }
}
