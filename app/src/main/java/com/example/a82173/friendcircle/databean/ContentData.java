package com.example.a82173.friendcircle.databean;

import java.util.List;

/**
 * Created by SunFly on 2016/10/27.
 */
public class ContentData {
    private List<Integer> images;
    private String username;
    private String content;
    private LinkData linkData;
    private List<LikeData> likeData;
    private List<ComentData> comentDatas;

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

    public LinkData getLinkData() {
        return linkData;
    }

    public void setLinkData(LinkData linkData) {
        this.linkData = linkData;
    }

    public ContentData(String username, String content, LinkData linkData) {
        this.username = username;
        this.content = content;
        this.linkData = linkData;
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


}
