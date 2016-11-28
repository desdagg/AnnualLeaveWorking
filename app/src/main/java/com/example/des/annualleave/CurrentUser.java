package com.example.des.annualleave;



/**
 * Created by Des on 18/11/2016.
 */

public class CurrentUser {

    private static CurrentUser userInstance;

    private String name;
    private String id;

    public static CurrentUser getInstance(String name){
        if (userInstance == null) {
            userInstance = new CurrentUser(name);
        }
        return userInstance;
    }

    private CurrentUser(String n){
        name = n;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public String getId() {
        return id;
    }
}
