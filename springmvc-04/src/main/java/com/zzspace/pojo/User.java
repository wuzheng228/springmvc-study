package com.zzspace.pojo;

public class User {
    private String name;
    private String pawd;

    public User() {
    }

    public User(String name, String pawd) {
        this.name = name;
        this.pawd = pawd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPawd() {
        return pawd;
    }

    public void setPawd(String pawd) {
        this.pawd = pawd;
    }
}
