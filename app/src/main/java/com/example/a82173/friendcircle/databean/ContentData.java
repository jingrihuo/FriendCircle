package com.example.a82173.friendcircle.databean;

import java.util.List;

import static com.example.a82173.friendcircle.activity.LoginActivity.userData;

public class ContentData {
    private List<Integer> images;
    private String username;
    private String content;
    private String createTime;
    private List<LikeData> likeData;
    private List<ComentData> comentDatas;
    private int megnumber;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getMegnumber() {
        return megnumber;
    }

    public void setMegnumber(int megnumber) {
        this.megnumber = megnumber;
    }

    public List<ComentData> getComentDatas() {
        return comentDatas;
    }

    public void setComentDatas(List<ComentData> comentDatas) {
        this.comentDatas = comentDatas;
    }

    public List<LikeData> getLikeData() {
        return likeData;
    }

    public void setLikeData(List<LikeData> likeData) {
        this.likeData = likeData;
    }

    public ContentData(String username, String content) {
        this.username = username;
        this.content = content;
    }

    public ContentData(List<Integer> images, String username, String content) {
        this.images = images;
        this.username = username;
        this.content = content;
    }

    public List<Integer> getImages() {

        return images;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
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

    public boolean isLike(){
        boolean result = false;
        if(this.likeData!=null){
            for (LikeData item : this.likeData){
                if (userData.getUserName().equals(item.getUsername()))
                    result = true;
            }
        }
        return result;
    }
}
