package com.example.a82173.friendcircle.databean;

/**
 * Created by yuritian on 2017/4/10.
 */

public class UserData {
    private String userName;//用户名
    private String userType;//用户类型
    private String classId;//用户班级
    private String userHeadBg;//用户头像
    private String userBG;//用户背景

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getUserHeadBg() {
        return userHeadBg;
    }

    public void setUserHeadBg(String userHeadBg) {
        this.userHeadBg = userHeadBg;
    }

    public String getUserBG() {
        return userBG;
    }

    public void setUserBG(String userBG) {
        this.userBG = userBG;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
