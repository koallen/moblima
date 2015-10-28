public class User {
    public enum TypeOfUser {STAFF, MOVIEGOER}

    private TypeOfUser typeOfUser;
    private boolean active;

    public User() {
        typeOfUser = MOVIEGOER;
        active = true;
    }

    public User(TypeOfUser typeOfUser) {
        this.typeOfUser = typeOfUser;
        this.active = true;
    }

    public User(TypeOfUser typeOfUser, boolean active){
        this.typeOfUser = typeOfUser;
        this.active = active;
    }

    public boolean isMovieGoer() {
        if (typeOfUser == MOVIEGOER)
            return true;
        return false;
    }

    public boolean isActive() {
        return active;
    }
}
