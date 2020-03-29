package com.example.sni.webflixhub.Class;

public class SearchModelClass {

    String thumb,title,cat_name,id;

    public SearchModelClass() {
    }

    public SearchModelClass(String thumb, String title, String cat_name,String id) {
        this.thumb = thumb;
        this.title = title;
        this.cat_name = cat_name;
        this.id = id;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
