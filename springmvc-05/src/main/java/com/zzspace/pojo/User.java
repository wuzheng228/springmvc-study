package com.zzspace.pojo;

import org.hibernate.validator.constraints.Length;

public class User {
    @Length(min = 2,max = 4, message = "{error.name.length}")
    String name;
    String pswd;

    public User() {
    }

    public User(String name, String pswd) {
        this.name = name;
        this.pswd = pswd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }
}
