package milk_processing;

public class User {
    private String email;
    private String userType;

    public User(String email, String userType) {
        this.email = email;
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public String getUserType() {
        return userType;
    }
}
