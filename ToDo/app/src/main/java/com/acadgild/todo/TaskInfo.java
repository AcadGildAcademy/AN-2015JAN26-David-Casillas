package com.acadgild.todo;

public class TaskInfo {

    private int id;
    private String title;
    private String description;
    private String date;
    private int image;

    public TaskInfo() {

    }

    public TaskInfo(int id, String date, String title, String description, int image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.image = image;
    }

    public TaskInfo(String date, String title, String description, int image) {

        this.title = title;
        this.description = description;
        this.date = date;
        this.image = image;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
