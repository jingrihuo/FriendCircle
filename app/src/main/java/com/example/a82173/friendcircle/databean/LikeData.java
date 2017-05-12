package com.example.a82173.friendcircle.databean;

/**
 * Created by SunFly on 2016/10/29.
 */
public class LikeData {
    private String username;
    private String userAccount;

    public LikeData(String username, String userAccount) {
        this.username = username;
        this.userAccount = userAccount;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
