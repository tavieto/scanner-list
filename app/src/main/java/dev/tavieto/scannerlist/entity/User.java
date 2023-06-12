package dev.tavieto.scannerlist.entity;

import androidx.annotation.NonNull;

public class User {
    @NonNull
    private final String uid;
    @NonNull
    private final String email;
    private String name = null;

    public User(@NonNull String uid, @NonNull String email) {
        this.uid = uid;
        this.email = email;
    }

    public User(@NonNull String uid, @NonNull String email, String name) {
        this.uid = uid;
        this.email = email;
        this.name = name;
    }

    @NonNull
    public String getUid() {
        return uid;
    }


    public String getName() {
        return name;
    }

    @NonNull
    public String getEmail() {
        return email;
    }
}
