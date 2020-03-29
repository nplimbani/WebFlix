package com.example.sni.webflixhub.Class;

public class CardViewModelClass {

    String id,url;

    public CardViewModelClass() {
    }

    public CardViewModelClass(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
