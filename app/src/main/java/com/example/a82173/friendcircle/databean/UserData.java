package com.example.a82173.friendcircle.databean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yuritian on 2017/4/10.
 */

public class UserData {
    private String userAccount;
    private String userName;//用户名
    private String userType;//用户类型
    private String classId;//用户班级
    private String userHeadBg;//用户头像
    private String userBG;//用户背景
    private String schoolId;//学校编号
    private List<ContentData> myDatas = new ArrayList<ContentData>();//个人班级圈
    private List<ContentData> classDatas = new ArrayList<ContentData>();//班级圈

    public List<ContentData> getClassDatas() {
        return classDatas;
    }

    public void setClassDatas(List<ContentData> classDatas) {
        this.classDatas = classDatas;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public List<ContentData> getMyDatas() {
        return myDatas;
    }

    public void setMyDatas(List<ContentData> myDatas) {
        this.myDatas = myDatas;
    }

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
