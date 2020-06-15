package com.example.atlantafalacon.model;

import com.example.atlantafalacon.UserData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("data")
    @Expose
    public UserData data;

    @SerializedName("ad")
    @Expose
    public UserData ad;
}
