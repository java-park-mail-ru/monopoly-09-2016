package ru.mail.park.model;

/**
 * Created by Andry on 27.09.16.
 */
public class UserProfile {
    private String username;
    private String password;
    private int rank;

    public UserProfile(){}

    public UserProfile(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserProfile(String username, String password, int rank) {
        this.username = username;
        this.password = password;
        this.rank = rank;
    }

    public String getUsername() { return username; }

    public String getPassword() {
        return password;
    }

    public int getRank() {
        return this.rank;
    }
}
