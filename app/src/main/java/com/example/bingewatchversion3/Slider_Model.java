package com.example.bingewatchversion3;

public class Slider_Model {

    Integer id;
    String movieName;
    String fileUrl;

    String imageUrl;

    public Slider_Model(Integer id, String movieName, String fileUrl, String imageUrl) {
        this.id = id;
        this.movieName = movieName;
        this.fileUrl = fileUrl;
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
