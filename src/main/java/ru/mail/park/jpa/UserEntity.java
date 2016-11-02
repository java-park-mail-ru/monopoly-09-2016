package ru.mail.park.jpa;

import ru.mail.park.model.UserProfile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Andry on 23.10.16.
 */
// '12345''

@Entity
@Table(name = "userTable")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "rank")
    private int rank;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public UserEntity(){}

    public UserEntity(String username, String password){
        this.username = username;
        this.password = password;
    }

    @SuppressWarnings("unused")
    public UserEntity(String username, String password, int rank){
        this.username = username;
        this.password = password;
        this.rank = rank;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @SuppressWarnings("unused")
    public int getRank() {

        return this.rank;
    }

    @SuppressWarnings("unused")
    public void setRank(int rank) {
        this.rank = rank;
    }

    public UserProfile toDto() {
        return new UserProfile(username, password, rank);
    }
}
