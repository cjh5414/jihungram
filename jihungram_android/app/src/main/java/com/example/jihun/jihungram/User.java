package com.example.jihun.jihungram;

/**
 * Created by Administrator on 2016-07-24.
 */
public class User {
    String id;
    String connDate;
    String token;

    public User(String id, String connDate, String token) {
        this.id = id;
        this.connDate = connDate;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getConnDate() {
        return connDate;
    }

    public String getId() {

        return id;
    }
}
