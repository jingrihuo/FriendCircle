package com.example.a82173.friendcircle.databean;

/**
 * Created by SunFly on 2016/10/29.
 */
public class ComentData {
    private String username;
    private String userAccount;
    private String content;
    private String replyUsername;
    private int comId;


    public ComentData(String username, String content, String replyUsername) {
        this.username = username;
        this.content = content;
        this.replyUsername = replyUsername;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyUsername() {
        return replyUsername;
    }

    public void setReplyUsername(String replyUsername) {
        this.replyUsername = replyUsername;
    }
}
