package com.example.a82173.friendcircle.databean;

/**
 * Created by SunFly on 2016/10/28.
 */
public class LinkData{
    private String url;
    private int linkimg;
    private String linktitle;

    public LinkData(String url, int linkimg, String linktitle) {
        this.url = url;
        this.linkimg = linkimg;
        this.linktitle = linktitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLinkimg() {
        return linkimg;
    }

    public void setLinkimg(int linkimg) {
        this.linkimg = linkimg;
    }

    public String getLinktitle() {
        return linktitle;
    }

    public void setLinktitle(String linktitle) {
        this.linktitle = linktitle;
    }
}