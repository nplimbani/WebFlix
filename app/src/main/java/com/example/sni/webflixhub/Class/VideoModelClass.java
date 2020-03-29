package com.example.sni.webflixhub.Class;

public class VideoModelClass {

    String thumb,id,cat_name,sub_title,url,title,d3,k4;
    int view,video_like;

    public VideoModelClass() {
    }

    public VideoModelClass(String thumb, String id, String cat_name, String sub_title, String url, String title,String d3,String k4, int view, int video_like) {
        this.thumb = thumb;
        this.id = id;
        this.cat_name = cat_name;
        this.sub_title = sub_title;
        this.url = url;
        this.title = title;
        this.view = view;
        this.video_like = video_like;
        this.d3 = d3;
        this.k4 = k4;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getVideo_like() {
        return video_like;
    }

    public void setVideo_like(int video_like) {
        this.video_like = video_like;
    }

    public String getD3() {
        return d3;
    }

    public void setD3(String d3) {
        this.d3 = d3;
    }

    public String getK4() {
        return k4;
    }

    public void setK4(String k4) {
        this.k4 = k4;
    }
}
