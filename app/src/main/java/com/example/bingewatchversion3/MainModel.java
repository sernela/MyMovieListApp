package com.example.bingewatchversion3;

public class MainModel {

    String lastseen, name, turl;

    MainModel()
    {

    }

    public MainModel(String lastseen, String name, String turl) {
        this.lastseen = lastseen;
        this.name = name;
        this.turl = turl;
    }

    public String getLastseen() {
        return lastseen;
    }

    public void setLastseen(String lastseen) {
        this.lastseen = lastseen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTurl() {
        return turl;
    }

    public void setTurl(String turl) {
        this.turl = turl;
    }
}
