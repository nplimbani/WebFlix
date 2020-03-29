package com.example.sni.webflixhub.Class;

public class WebSeriesSeasonModelClass {

    String id,v_id,title,sub_title,thumb;

    public WebSeriesSeasonModelClass() {
    }

    public WebSeriesSeasonModelClass(String id, String v_id, String title, String sub_title, String thumb) {
        this.id = id;
        this.v_id = v_id;
        this.title = title;
        this.sub_title = sub_title;
        this.thumb = thumb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getV_id() {
        return v_id;
    }

    public void setV_id(String v_id) {
        this.v_id = v_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
